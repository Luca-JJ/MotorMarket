package motormarket.data;

/**
 * Represents a Color entity used within the marketplace catalog.
 * This class maps directly to the colori table, serving as a standardized 
 * reference for vehicle exterior paint and interior upholstery styling.
 */
public class Color {

    private int colorId;
    private String colorName;

    /**
     * Complete constructor containing all database mapped fields.
     * @param colorId   The unique auto-incremented database ID (IdColore).
     * @param colorName The display text name of the color (NomeColore).
     */
    public Color(int colorId, String colorName) {
        this.colorId = colorId;
        this.colorName = colorName;
    }

    public int getColorId() {
        return colorId;
    }

    public String getColorName() {
        return colorName;
    }

    @Override
    public String toString() {
        return "Color{" +
                "colorId=" + colorId +
                ", colorName='" + colorName + '\'' +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Color} entities.
     * Handles database CRUD interactions related to color taxonomy.
     */
    public static final class DAO {

    }
}
