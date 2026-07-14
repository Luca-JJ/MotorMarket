package motormarket.data;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

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

        /**
         * Finds all colors
         * 
         * @param connection
         * @return the colors with the associated colorId
         */
        public static Map<String,Integer> getColors(Connection connection) {
            Map<String,Integer> map = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_COLORS);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var colorId = resultSet.getInt("IdColore");
                    var colorName = resultSet.getString("NomeColore");
                    map.put(colorName,colorId);
                }
                
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return map;
        }
    }
}
