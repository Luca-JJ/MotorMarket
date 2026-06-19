package motormarket.data;

/**
 * Represents an Addition record, mapping a premium service to a specific order.
 * This class corresponds to the aggiunte table, handling the many-to-many 
 * relationship between Orders and Services.
 */
public class Addition {
    private Service service;
    private Order order;

    /**
     * Constructs a new Addition instance linking a service object to an order object.
     * @param service The {@link Service} instance associated with this addition.
     * @param order   The {@link Order} instance associated with this addition.
     */
    public Addition(Service service, Order order) {
        this.service = service;
        this.order = order;
    }

    public Service getService() {
        return service;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Addition{" +
                "service=" + (service != null ? service.getServiceId() : "null") +
                ", order=" + (order != null ? order.getOrderId() : "null") +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for Addition entities.
     * Handles database interactions related to services attached to orders.
     */
    public static final class DAO {
    
    }
}
