package motormarket.data;

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
                ", producer=" + (producer != null ? producer.getProducerId() : "null") +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link CarModel} entities.
     * Handles database CRUD interactions related to car model taxonomy definitions.
     */
    public static final class DAO {

    }
}
