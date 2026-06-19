package motormarket.data;

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

    }
}
