package motormarket.data;

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
    
    }
}
