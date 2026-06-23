package motormarket.data;

/**
 * Represents a Spare Part cataloged within the marketplace inventory.
 * This class maps directly to the pezzi_di_ricambio table and holds a direct 
 * Object-Oriented relationship reference to its manufacturing {@link Producer}.
 */
public class SparePart {

    private int partId;
    private String partName;
    private int stockQuantity;
    private String category;
    private int partsSold;
    private Producer producer;

    /**
     * Complete Object-Oriented Constructor containing all database mapped fields.
     * @param partId        The unique auto-incremented database ID (IdPezzo).
     * @param partName      The descriptive name of the spare part (NomePezzo).
     * @param stockQuantity The current volume available in warehouse stock (QuantitàMagazzino).
     * @param category      The classification type or category group (Tipologia).
     * @param partsSold     The aggregated volume tracking total units sold (PezziVenduti).
     * @param producer      The {@link Producer} object instance representing the component manufacturer.
     */
    public SparePart(int partId, String partName, int stockQuantity, String category, int partsSold, Producer producer) {
        this.partId = partId;
        this.partName = partName;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.partsSold = partsSold;
        this.producer = producer;
    }

    public int getPartId() {
        return partId;
    }

    public String getPartName() {
        return partName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCategory() {
        return category;
    }

    public int getPartsSold() {
        return partsSold;
    }

    public Producer getProducer() {
        return producer;
    }

    @Override
    public String toString() {
        return "SparePart{" +
                "partId=" + partId +
                ", partName='" + partName + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", category='" + category + '\'' +
                ", partsSold=" + partsSold +
                ", producer=" + (producer != null ? producer.getProducerId() : "null") +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link SparePart} entities.
     * Handles database CRUD interactions related to spare parts tracking.
     */
    public static final class DAO {
    
    }
}


