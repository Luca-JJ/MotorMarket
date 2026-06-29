package motormarket.data;

import java.math.BigDecimal;
import java.sql.Connection;
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
    }
}
