package motormarket.data;

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
     * * @param fuelTypeId The unique auto-incremented database ID (IdAlimentazione).
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
    
    }
}
