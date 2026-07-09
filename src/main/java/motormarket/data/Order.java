package motormarket.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents an Order placed by a user within the marketplace.
 */
public class Order {

    private int orderId;
    private String address;
    private BigDecimal totalAmount;
    private LocalDate date;
    private User user;

    /**
     * Constructs a new Order instance with all mandatory database fields.
     * @param orderId     The unique identifier of the order (Auto-incremented in DB).
     * @param address     The shipping or billing address for the order.
     * @param totalAmount The total cost of the order (mapped from SQL DECIMAL).
     * @param date        The placement date of the order.
     * @param user        The {@link User} object representing the buyer who placed the order.
     */
    public Order(int orderId, String address, BigDecimal totalAmount, LocalDate date, User user) {
        this.orderId = orderId;
        this.address = address;
        this.totalAmount = totalAmount;
        this.date = date;
        this.user = user;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + date +
                ", totalAmount=" + totalAmount +
                ", user=" + (user != null ? user.getUserId() : "null") +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Order} entities.
     * Handles database CRUD interactions related to users.
     */
    public static final class DAO {

        /**
         * Retrieves the full order history for a specific user, grouping multiple 
         * purchased items under their respective main order.
         * @param connection Active JDBC connection to the database.
         * @param userId     The unique identifier of the user making the request.
         * @return A list of {@link OrderList} order with items.
         * @throws DAOException If any database access error or failure occurs.
         */
        public static List<OrderList> showOrders(Connection connection, int userId) {
            Map<Integer, OrderList> orderMap = new LinkedHashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.SEARCH_ORDERS,userId);
                var resultSet = statement.executeQuery();
            ) {
                
                User user = User.DAO.findUser(connection, userId);

                while (resultSet.next()) {
                    int orderId = resultSet.getInt("IdOrdine");
                    
                    // 1. ORDER HEADER HANDLING (Using constructor if it doesn't exist yet)
                    OrderList orderList = orderMap.get(orderId);
                    if (orderList == null) {
                        // Creating the Order object directly via a full constructor
                        Order order = new Order(
                            orderId,
                            resultSet.getString("IndirizzoSpedizione"),
                            resultSet.getBigDecimal("PrezzoTotaleRiga"), // or SpesaTotale based on your class
                            resultSet.getDate("DataOrdine").toLocalDate(),
                            user
                        );
                        
                        orderList = new OrderList(order);
                        orderMap.put(orderId, orderList);

                        List<Service> services = Service.DAO.listService(connection, orderId);
                        orderList.setServices(services);
                    }
                    
                    // 2. ORDER ROW HANDLING (Extracting common fields first)
                    String type = resultSet.getString("TipoAnnuncio");
                    int listingId = resultSet.getInt("idAnnuncio");
                    int quantity = resultSet.getInt("Quantità");
                    var unitPrice = resultSet.getBigDecimal("PrezzoUnitario");
                    
                    int itemId = 0;
                    String brand = null;
                    String itemName = null;
                    
                    // Dynamically assign variables based on the type
                    if ("Auto".equals(type)) {
                        itemId = resultSet.getInt("IdAuto");
                        brand = resultSet.getString("MarcaAuto");
                        itemName = resultSet.getString("NomeModelloAuto");
                    } else if ("Pezzo".equals(type)) {
                        itemId = resultSet.getInt("IdPezzo");
                        brand = resultSet.getString("MarcaPezzo");
                        itemName = resultSet.getString("NomePezzo");
                    }
                    
                    // Instantiate OrderItem directly using its full constructor
                    OrderItem item = new OrderItem(listingId, type, itemId, quantity, unitPrice, brand, itemName);
                    
                    // 3. AGGREGATION
                    orderList.addItem(item);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }

            return new ArrayList<OrderList>(orderMap.values());
        }

        /**
         * Processes the purchase of multiple items under a single order transaction,
         * using the application's native OrderItem model.
         * * @param connection  Active JDBC connection to the database.
         * @param userId      The unique identifier of the user making the purchase.
         * @param address     The shipping address for the entire order.
         * @param orderItems  The list of OrderItem objects representing the cart.
         * @throws DAOException If any database access error or transaction rollback occurs.
         */
        public static OrderList purchaseOrder(Connection connection, int userId, String address, List<OrderItem> orderItems, List<Service> services) {
            
            if (orderItems == null || orderItems.isEmpty()) {
                throw new IllegalArgumentException("Cannot process an empty order.");
            }

            Order order = null;

            // Dynamically sums up (unitPrice * quantity) for each item in the list
            BigDecimal totalCost = orderItems.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            if (services != null && !services.isEmpty()) {
                BigDecimal servicesCost = services.stream()
                    .map(Service::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                totalCost = totalCost.add(servicesCost);
            }

            try {
                connection.setAutoCommit(false);

                int generatedOrderId = 0;
                try (var statement1 = DAOUtils.prepare(connection, Queries.INSERT_ORDER, address, totalCost, userId )) {
                    int rowsAffected = statement1.executeUpdate();
                    if (rowsAffected > 0) {
                        String sqlId = "SELECT LAST_INSERT_ID()";
                        try (var idStatement = DAOUtils.prepare(connection, sqlId);
                            ResultSet rs = idStatement.executeQuery()) {
                            
                            if (rs.next()) {
                                generatedOrderId = rs.getInt(1);
                                order = new Order(generatedOrderId, address, totalCost, LocalDate.now(), User.DAO.findUser(connection, userId));
                            }
                        }
                    }
                    
                    for (OrderItem item : orderItems) {
                       try (var statement2 = DAOUtils.prepare(connection, Queries.INSERT_SALE, generatedOrderId, item.getQuantity(), item.getUnitPrice(), item.getListingId())) {
                            int rows = statement2.executeUpdate();
                            if (rows == 0) {
                            throw new DAOException("Inserimento vendita fallito: annuncio non attivo o inesistente (ID=" + item.getListingId() + ")");
}
                       }

                       String type = item.getListingType();

                       if ("Auto".equals(type)) {
                            // Update car status to unavailable and close the specific listing
                            try (var statement3a = DAOUtils.prepare(connection, Queries.UPDATE_LISTING_STATUS, item.getListingId());
                                var statement4a = DAOUtils.prepare(connection, Queries.UPDATE_CAR_AVAILABILITY, item.getListingId());
                                var statement5a = DAOUtils.prepare(connection, Queries.UPDATE_CARS_SOLD, item.getItemId())) {
                                statement3a.executeUpdate();
                                statement4a.executeUpdate();
                                statement5a.executeUpdate();
                            }
                        } else if ("Pezzo".equals(type)) {
                            // Deduct warehouse stock and increase total items sold
                            try (var statement3b = DAOUtils.prepare(connection, Queries.UPDATE_SPARE_PART_STOCK, item.getQuantity(), item.getQuantity(), item.getListingId());
                                var statement4b = DAOUtils.prepare(connection, Queries.CLOSE_EMPTY_SPARE_PART_LISTING, item.getListingId())) {
                                statement3b.executeUpdate();
                                statement4b.executeUpdate();
                            }
                        }
                    }

                    if (services != null && !services.isEmpty()) {
                        for (Service service : services) {
                            try (var statementService = DAOUtils.prepare(connection, Queries.INSERT_ADDITION, generatedOrderId, service.getServiceId())) {
                                statementService.executeUpdate();
                            }
                        }
                    }
                                
                } catch (Exception e) {
                    throw new DAOException(e);
                }
                connection.commit();
                
            } catch (Exception e) {
                // TRANSACTION ROLLBACK HANDLER
                try {
                    if (connection != null) {
                        connection.rollback();
                    }
                } catch (Exception rollbackEx) {
                    // Suppress or log rollback exceptions to prioritize the original failure root cause
                    e.addSuppressed(rollbackEx);
                }
                throw new DAOException(e);
            } finally {
                // CLEANUP & RESTORE CONNECTION STATE
                try {
                    if (connection != null) {
                        connection.setAutoCommit(true);
                    }
                } catch (Exception restoreEx) {
                    // Managed silently to avoid overriding method outcomes
                }
            }
            return new OrderList(order, orderItems, services);
        }

        /**
         * Fetches the total revenue generated during a specific month and year.
         * @param connection the active database connection
         * @param month the month number (1 to 12)
         * @param year the year (e.g., 2026)
         * @return the total revenue as a double (returns 0.0 if no orders were placed)
         * @throws DAOException if a database access error occurs
         */
        public static double getMonthlyRevenue(Connection connection, int month, int year) {
            
            try (var statement = DAOUtils.prepare(connection, Queries.GET_MONTHLY_REVENUE, month, year);
                var resultSet = statement.executeQuery()) {
                
                if (resultSet.next()) {
                    return resultSet.getDouble("RicavoTotale");
                }
                return 0.0;
                
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

    }
    
}
