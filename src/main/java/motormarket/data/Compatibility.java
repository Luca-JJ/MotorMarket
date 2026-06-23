package motormarket.data;

/**
 * Represents a Compatibility record, mapping a spare part to a compatible car model.
 * This class corresponds to the compatibilita table, handling the many-to-many 
 * relationship between Spare Parts and Car Models using direct object references.
 */
public class Compatibility {

    private SparePart sparePart;
    private CarModel carModel;

    /**
     * Constructs a new Compatibility instance linking a spare part object to a car model object.
     * @param sparePart The {@link SparePart} instance that is compatible.
     * @param carModel  The {@link CarModel} instance that can host the part.
     */
    public Compatibility(SparePart sparePart, CarModel carModel) {
        this.sparePart = sparePart;
        this.carModel = carModel;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    @Override
    public String toString() {
        return "Compatibility{" +
                "sparePart=" + (sparePart != null ? sparePart.getPartId() : "null") +
                ", carModel=" + (carModel != null ? carModel.getModelId() : "null") +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Compatibility} entities.
     * Handles database interactions related to parts compatibility across car models.
     */
    public static final class DAO {

    }
}
