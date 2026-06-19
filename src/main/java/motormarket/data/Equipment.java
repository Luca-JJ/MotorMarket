package motormarket.data;

/**
 * Represents an Equipment record, mapping an accessory to a specific car.
 * This class corresponds to the equipaggiamenti table, handling the many-to-many 
 * relationship between Cars and Accessories using direct object references.
 */
public class Equipment {

    private Accessory accessory;
    private Car car;

    /**
     * Constructs a new Equipment instance linking an accessory object to a car object.
     * * @param accessory The {@link Accessory} instance equipped on the vehicle.
     * @param car       The {@link Car} instance that owns this piece of accessory equipment.
     */
    public Equipment(Accessory accessory, Car car) {
        this.accessory = accessory;
        this.car = car;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "accessory=" + (accessory != null ? accessory.getAccessoryId() : "null") +
                ", car=" + (car != null ? car.getCarId() : "null") +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Equipment} entities.
     * Handles database interactions related to optional accessories equipped on physical cars.
     */
    public static final class DAO {
    
    }
}
