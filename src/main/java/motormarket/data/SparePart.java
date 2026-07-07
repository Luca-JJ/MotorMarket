package motormarket.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Spare Part cataloged within the marketplace inventory.
 * This class maps directly to the pezzi_di_ricambio table and holds a direct 
 * Object-Oriented relationship reference to its manufacturing {@link Producer}.
 */
public class SparePart {

    private int partId;
    private String partName;
    private int stockQuantity;
    private String category;
    private int partsSold;
    private Producer producer;
    private List<CarModel> compatibility = new ArrayList<>();

    /**
     * Complete Object-Oriented Constructor containing all database mapped fields.
     * @param partId        The unique auto-incremented database ID (IdPezzo).
     * @param partName      The descriptive name of the spare part (NomePezzo).
     * @param stockQuantity The current volume available in warehouse stock (QuantitàMagazzino).
     * @param category      The classification type or category group (Tipologia).
     * @param partsSold     The aggregated volume tracking total units sold (PezziVenduti).
     * @param producer      The {@link Producer} object instance representing the component manufacturer.
     * @param compatibility The list that contain all compatibility of the part
     */
    public SparePart(int partId, String partName, int stockQuantity, String category, int partsSold, Producer producer, List<CarModel> compatibility) {
        this.partId = partId;
        this.partName = partName;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.partsSold = partsSold;
        this.producer = producer;
        this.compatibility = compatibility;
    }

    public int getPartId() {
        return partId;
    }

    public String getPartName() {
        return partName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public int getPartsSold() {
        return partsSold;
    }

    public Producer getProducer() {
        return producer;
    }

    public List<CarModel> getCompatibility() {
        return compatibility;
    }

    @Override
    public String toString() {
        return "SparePart{" +
                "partId=" + partId +
                ", partName='" + partName + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", category='" + category + '\'' +
                ", partsSold=" + partsSold +
                ", producer=" + (producer != null ? producer.getProducerId() : "null") +
                ", compatibilityList=" + compatibility +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link SparePart} entities.
     * Handles database CRUD interactions related to spare parts tracking.
     */
    public static final class DAO {
    
        /**
         * Generates a statistical report to identify which spare part component
         * has recorded the highest sales volume, including its nested Producer information.
         * * @param connection the active database connection
         * @return the SparePart object containing its Producer, or null if no data is found
         * @throws DAOException if any database access error occurs
         */
        public static SparePart getMostSoldSparePart(Connection connection) throws DAOException {
            SparePart topSparePart = null;
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_MOST_SOLD_SPARE_PART);
                var resultSet = statement.executeQuery();
            ) {
                
                if(resultSet.next()) {
                    int id = resultSet.getInt("IdProduttore");
                    String name = resultSet.getString("NomeProduttore");
                    String nationality = resultSet.getString("Nazionalita");
                    int carsSold = resultSet.getInt("AutoVendute");
                    Producer Producer = new Producer(id, name, nationality, carsSold);

                    int partId = resultSet.getInt("IdPezzo");
                    String partName = resultSet.getString("NomePezzo");
                    String type = resultSet.getString("Tipologia");
                    int stock = resultSet.getInt("QuantitàMagazzino");
                    int partsSold = resultSet.getInt("PezziVenduti");
                    topSparePart = new SparePart(partId, partName, stock, type, partsSold, Producer, CarModel.DAO.listCompatibility(connection, partId));
                }

            } catch (Exception e) {
                throw new DAOException(e);
            }

            return topSparePart;
        }

        /**
         * OP-11: Updates the warehouse stock for a specific spare part by adding or 
         * subtracting a relative delta quantity (e.g., +5 for restock, -2 for adjustments).
         * @param partId the unique identifier of the spare part
         * @param deltaQuantity the relative quantity to add (positive) or remove (negative)
         * @return the new quantity
         * @throws DAOException if any database access error occurs
         */
        public static Integer addStockQuantity(Connection connection, int partId, int deltaQuantity) throws DAOException {
            Integer stockQuantity = null;
            try {
                connection.setAutoCommit(false);
                try (var statement = DAOUtils.prepare(connection, Queries.ADD_SPARE_PART_STOCK, deltaQuantity, partId)) {

                    int rowsUpdated = statement.executeUpdate();
            
                    // If no rows were affected, the spare part ID does not exist in the database
                    if (rowsUpdated == 0) {
                        throw new java.sql.SQLException("Operation failed: Spare part with ID " + partId + " not found.");
                    }

                    try (
                        var statement2 = DAOUtils.prepare(connection, Queries.GET_SPARE_PART_STOCK, partId);
                        var resultSet = statement2.executeQuery();
                    ) {
                        if (resultSet.next()) {
                            stockQuantity = resultSet.getInt("QuantitàMagazzino");
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
            return stockQuantity;
        }
    }

}


