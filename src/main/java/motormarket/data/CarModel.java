package motormarket.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a specific Car Model cataloged within the marketplace system.
 * This class maps directly to the modelli table and establishes a direct 
 * Object-Oriented relationship reference to its parent manufacturing {@link Producer}.
 */
public class CarModel {

    private int modelId;
    private String modelName;
    private Producer producer;

    /**
     * Complete Object-Oriented Constructor containing all database mapped fields.
     * Primarily used by the DAO layer to instantiate the entity from database records.
     * @param modelId   The unique auto-incremented database ID (IdModello).
     * @param modelName The commercial name label of the specific model (NomeModello).
     * @param producer  The {@link Producer} object instance representing the manufacturer brand.
     */
    public CarModel(int modelId, String modelName, Producer producer) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.producer = producer;
    }

    public int getModelId() {
        return modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public Producer getProducer() {
        return producer;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "modelId=" + modelId +
                ", modelName='" + modelName + '\'' +
                ", producer=" + (producer != null ? producer : "null") +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link CarModel} entities.
     * Handles database CRUD interactions related to car model taxonomy definitions.
     */
    public static final class DAO {

        /**
         * Finds all accessories associeted to a {@link car}
         * 
         * @param connection the active database connection
         * @param sparePartId the unique system identifier of the spare part
         * @return a list of all compatibility of the spare part
         * @throws DAOException if a database access error occurs or parsing fails
         */
        public static List<CarModel> listCompatibility(Connection connection, int sparePartId){
            List<CarModel> compatibility = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_COMPATIBILITY, sparePartId);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var modelId = resultSet.getInt("IdModello");
                    var modelName = resultSet.getString("NomeModello");
                    Producer producer = new Producer(
                        resultSet.getInt("IdProduttore"),
                        resultSet.getString("NomeProduttore"),
                        resultSet.getString("Nazionalita"),
                        resultSet.getInt("AutoVendute")
                    );
                    compatibility.add(new CarModel(modelId, modelName, producer));
                }
                
            } catch (Exception e) {
                throw new DAOException(e);
            }
            
            return compatibility;
        }

        /**
         * Finds all brand and model combinations
         * 
         * @param connection
         * @return the brand model with the associated modelId
         */
        public static Map<String,Integer> brandsModels(Connection connection) {
            Map<String,Integer> map = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_BRANDS_MODELS);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var modelId = resultSet.getInt("IdModello");
                    var modelName = resultSet.getString("NomeModello");
                    var productorName = resultSet.getString("NomeProduttore");
                    String brandModel = productorName + " " + modelName;
                    map.put(brandModel,modelId);
                }
                
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return map;
        }
    }
}
