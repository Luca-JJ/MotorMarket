package motormarket.data;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a manufacturing Brand or Company within the marketplace catalog.
 * This class maps directly to the produttori table, managing manufacturing corporate entities, 
 * origin regions, and high-level platform statistics.
 */
public class Producer {

    private int producerId;
    private String producerName;
    private String nationality;
    private int carsSold;

    /**
     * Complete constructor containing all database mapped fields.
     * Primarily used by the DAO layer to instantiate the entity from database records.
     * @param producerId   The unique auto-incremented database ID (IdProduttore).
     * @param producerName The commercial trade name of the manufacturer (NomeProduttore).
     * @param nationality  The country of corporate registration or origin (Nazionalità).
     * @param carsSold     The aggregated volume tracking total vehicles sold (AutoVendute).
     */
    public Producer(int producerId, String producerName, String nationality, int carsSold) {
        this.producerId = producerId;
        this.producerName = producerName;
        this.nationality = nationality;
        this.carsSold = carsSold;
    }

    public int getProducerId() {
        return producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public String getNationality() {
        return nationality;
    }

    public int getCarsSold() {
        return carsSold;
    }

   @Override
    public String toString() {
        return "Producer{" +
                "producerId=" + producerId +
                ", producerName='" + producerName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", carsSold=" + carsSold +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Producer} entities.
     * Handles database CRUD interactions related to manufacturer registry data.
     */
    public static final class DAO {

        /**
         * Identifies the car manufacturer that recorded the highest number 
         * of sales on the platform by leveraging the redundant counter attribute.
         * * @param connection the active database connection
         * @return the Producer object representing the top seller, or null if no data is found
         * @throws DAOException if any database access error occurs
         */
        public static Producer getMostSoldProducer(Connection connection) {
            Producer topProducer = null;
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_MOST_SOLD_PRODUCER);
                var resultSet = statement.executeQuery();
            ) {
                if(resultSet.next()) {
                    int id = resultSet.getInt("IdProduttore");
                    String name = resultSet.getString("NomeProduttore");
                    String nationality = resultSet.getString("Nazionalita");
                    int carsSold = resultSet.getInt("AutoVendute");
                    topProducer = new Producer(id, name, nationality, carsSold);
                }

            } catch (Exception e) {
                throw new DAOException(e);
            }

            return topProducer;
        }

        /**
         * Finds all producers
         * 
         * @param connection
         * @return the producer with the associated producerId
         */
        public static Map<String,Integer> getProducers(Connection connection) {
            Map<String,Integer> map = new LinkedHashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_PRODUCERS);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var producerId = resultSet.getInt("IdProduttore");
                    var producerName = resultSet.getString("NomeProduttore");
                    map.put(producerName,producerId);
                }
                
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return map;
        }

    }
}
