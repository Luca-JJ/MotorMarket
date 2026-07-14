package motormarket.data;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Fuel Type category used within the marketplace catalog.
 * This class maps directly to the {@code tipi_alimentazione} table, serving as a 
 * lookup entity for defining vehicle engine fuel configurations.
 */
public class FuelType {

    private int fuelTypeId;
    private String fuelName;

    /**
     * Complete constructor containing all database mapped fields.
     * @param fuelTypeId The unique auto-incremented database ID (IdAlimentazione).
     * @param fuelName   The display name text of the fuel type (NomeAlimentazione).
     */
    public FuelType(int fuelTypeId, String fuelName) {
        this.fuelTypeId = fuelTypeId;
        this.fuelName = fuelName;
    }

    public int getFuelTypeId() {
        return fuelTypeId;
    }

    public String getFuelName() {
        return fuelName;
    }

    @Override
    public String toString() {
        return "FuelType{" +
                "fuelTypeId=" + fuelTypeId +
                ", fuelName='" + fuelName + '\'' +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link FuelType} entities.
     * Handles database CRUD interactions related to fuel taxonomy.
     */
    public static final class DAO {
        
        /**
         * Finds all fuel types
         * 
         * @param connection
         * @return the fuel types with the associated fuelTypeId
         */
        public static Map<String,Integer> getFuels(Connection connection) {
            Map<String,Integer> map = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_FUELS);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var fuelTypeId = resultSet.getInt("IdAlimentazione");
                    var fuelName = resultSet.getString("NomeAlimentazione");
                    map.put(fuelName,fuelTypeId);
                }
                
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return map;
        }
    }
}
