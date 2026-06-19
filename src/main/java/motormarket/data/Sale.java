package motormarket.data;

import java.math.BigDecimal;

   /**
 * Represents a Sale record, mapping an item from a specific listing to an order.
 * This class corresponds to the vendite table.
 */
public class Sale {

    private Listing listing;
    private Order order;
    private int quantity;
    private BigDecimal unitPrice;

    /**
     * Constructs a new Sale instance representing an item line within an order.
     * @param listing   The {@link Listing} instance representing the item put up for sale.
     * @param order     The {@link Order} instance to which this line item belongs.
     * @param quantity  The quantity of items purchased.
     * @param unitPrice The price per single unit locked at the time of purchase.
     */
    public Sale(Listing listing, Order order, int quantity, BigDecimal unitPrice) {
        this.listing = listing;
        this.order = order;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Listing getListing() {
        return listing;
    }

    public Order getOrder() {
        return order;
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
                "listing=" + (listing != null ? listing.getListingId() : "null") +
                ", order=" + (order != null ? order.getOrderId() : "null") +
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
