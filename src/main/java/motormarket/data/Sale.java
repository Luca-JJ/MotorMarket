package motormarket.data;

import java.math.BigDecimal;

   /**
 * Represents a Sale record, mapping an item from a specific listing to an order.
 * This class corresponds to the vendite table, handling the order line items.
 */
public class Sale {

    private int listingId;
    private int orderId;
    private int quantity;
    private BigDecimal unitPrice;

    /**
     * Constructs a new Sale instance representing an item line within an order.
     * @param listingId  The foreign key identifier of the associated listing (idAnnuncio).
     * @param orderId    The foreign key identifier of the associated order (IdOrdine).
     * @param quantity   The quantity of items purchased.
     * @param unitPrice  The price per single unit at the time of purchase.
     */
    public Sale(int listingId, int orderId, int quantity, BigDecimal unitPrice) {
        this.listingId = listingId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getListingId() {
        return listingId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "listingId=" + listingId +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Sale} entities.
     * Handles database CRUD interactions related to order line items.
     */
    public static final class DAO {

    }
}
