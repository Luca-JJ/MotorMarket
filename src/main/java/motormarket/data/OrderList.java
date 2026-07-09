package motormarket.data;

import java.util.ArrayList;
import java.util.List;

/**
* This class rapresent an order with his elements
*/
public class OrderList {
    private Order order; // La tua classe originale con i 5 campi
    private List<OrderItem> items = new ArrayList<>();
    private List<Service> services = new ArrayList<>();
    
    /**
     * the normal constructor with empty list
     * @param order an order
     */
    public OrderList(Order order) {
        this.order = order;
    }

    /**
     * the normal constructor
     * @param order an order
     */
    public OrderList(Order order, List<OrderItem> items, List<Service> services) {
        this.order = order;
        this.items = items;
        this.services = services;
    }

    public Order getOrder() {
        return order;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public List<Service> getServices() {
        return services;
    }

    public void addItem(OrderItem item) {
        this.items.add(item); 
    }

    public void addService(Service service) {
        this.services.add(service); 
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        if (order != null) {
            sb.append(String.format("ORDER #%d - Date: %s\n", order.getOrderId(), order.getDate()));
            sb.append(String.format("Shipping Address: %s\n", order.getAddress()));
            if (order.getUser() != null) {
                sb.append(String.format("Customer ID: %d\n", order.getUser().getUserId()));
            }
        } else {
            sb.append("ORDER HEADER: null\n");
        }
        sb.append("--------------------------------------------------\n");
        sb.append("Items Purchased:\n");
        
        if (items == null || items.isEmpty()) {
            sb.append("  (No items in this order)\n");
        } else {
            for (OrderItem item : items) {
                sb.append(item.toString()).append("\n");
            }
        }
        sb.append("==================================================\n");
        if (services == null || services.isEmpty()) {
            sb.append("  (No Services in this order)\n");
        } else {
            for (Service service : services) {
                sb.append(service.toString()).append("\n");
            }
        }
        sb.append("==================================================");
        return sb.toString();
    }

}
