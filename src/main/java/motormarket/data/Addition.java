package motormarket.data;

/**
 * Represents an Addition record, mapping a premium service to a specific order.
 * This class corresponds to the aggiunte table, handling the many-to-many 
 * relationship between Orders and Services.
 */
public class Addition {
    private int serviceId;
    private int orderId;

    /**
     * Constructs a new Addition instance linking a service to an order.
     * @param serviceId The foreign key identifier of the associated service (IdServizio).
     * @param orderId   The foreign key identifier of the associated order (IdOrdine).
     */
    public Addition(int serviceId, int orderId) {
        this.serviceId = serviceId;
        this.orderId = orderId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public int getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "Addition{" +
                "serviceId=" + serviceId +
                ", orderId=" + orderId +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for Addition entities.
     * Handles database interactions related to services attached to orders.
     */
    public static final class DAO {
    
    }
}
