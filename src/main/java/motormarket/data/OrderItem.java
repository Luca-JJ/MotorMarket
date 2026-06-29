package motormarket.data;

import java.math.BigDecimal;

/**
 * rappresent an item in an order
 */
public class OrderItem {
    private int listingId;         // ID dell'annuncio sul database (idAnnuncio)
    private String listingType;     // Tipo di annuncio ("Auto" o "Pezzo")
    private int itemId;            // ID nativo dell'oggetto acquistato (IdAuto o IdPezzo)
    private int quantity;          // Quantità acquistata
    private BigDecimal unitPrice;  // Prezzo unitario al momento dell'acquisto
    private String brand;          // Marca del prodotto (NomeProduttore)
    private String itemName;       // Nome del pezzo o modello dell'auto

    /**
     * Costruttore completo con tutti i parametri.
     */
    public OrderItem(int listingId, String listingType, int itemId, int quantity, BigDecimal unitPrice, String brand, String itemName) {
        this.listingId = listingId;
        this.listingType = listingType;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.brand = brand;
        this.itemName = itemName;
    }

    public int getListingId() {
        return listingId;
    }

    public String getListingType() {
        return listingType;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getBrand() {
        return brand;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String toString() {
        return String.format("  - [%s] (ID: %d) %s %s | Qty: %d | Price: EUR %s",
                listingType,
                itemId,
                brand,
                itemName,
                quantity,
                unitPrice != null ? unitPrice.toString() : "0.00"
        );
    }
}
