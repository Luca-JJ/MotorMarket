package motormarket.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Accessory entity used within the marketplace catalog.
 * This class maps directly to the {@code accessori} table, serving as a standardized 
 * lookup option for optional features or packages equipped on vehicles.
 */
public class Accessory {

    private int accessoryId;
    private String accessoryName;

    /**
     * Complete constructor containing all database mapped fields.
     * Primarily used by the DAO layer to instantiate the entity from database records.
     * * @param accessoryId   The unique auto-incremented database ID (IdAccessorio).
     * @param accessoryName The display text label of the accessory feature (NomeAccessorio).
     */
    public Accessory(int accessoryId, String accessoryName) {
        this.accessoryId = accessoryId;
        this.accessoryName = accessoryName;
    }

    public int getAccessoryId() {
        return accessoryId;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    @Override
    public String toString() {
        return "Accessory{" +
                "accessoryId=" + accessoryId +
                ", accessoryName='" + accessoryName + '\'' +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Accessory} entities.
     * Handles database CRUD interactions related to accessory taxonomy.
     */
    public static final class DAO {
        
        /**
         * Finds all accessories associeted to a {@link car}
         * 
         * @param connection the active database connection
         * @param carId the unique system identifier of the car
         * @return a list of all accesories of the car
         * @throws DAOException if a database access error occurs or parsing fails
         */
        public static List<Accessory> listAccesory(Connection connection, int carId){
            List<Accessory> accessories = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_EQUIPMENT, carId);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var accessoryId = resultSet.getInt("IdAccessorio");
                    var accessoryName = resultSet.getString("NomeAccessorio");
                    accessories.add(new Accessory(accessoryId, accessoryName));
                }
                
            } catch (Exception e) {
                throw new DAOException(e);
            }
            
            return accessories;
        }
    }
}
