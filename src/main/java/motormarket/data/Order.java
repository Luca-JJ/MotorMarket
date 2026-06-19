package motormarket.data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an Order placed by a user within the marketplace.
 */
public class Order {

    private int orderId;
    private String address;
    private BigDecimal totalAmount;
    private LocalDate date;
    private User user;

    /**
     * Constructs a new Order instance with all mandatory database fields.
     * @param orderId     The unique identifier of the order (Auto-incremented in DB).
     * @param address     The shipping or billing address for the order.
     * @param totalAmount The total cost of the order (mapped from SQL DECIMAL).
     * @param date        The placement date of the order.
     * @param user        The {@link User} object representing the buyer who placed the order.
     */
    public Order(int orderId, String address, BigDecimal totalAmount, LocalDate date, User user) {
        this.orderId = orderId;
        this.address = address;
        this.totalAmount = totalAmount;
        this.date = date;
        this.user = user;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + date +
                ", totalAmount=" + totalAmount +
                ", user=" + (user != null ? user.getUserId() : "null") +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Order} entities.
     * Handles database CRUD interactions related to users.
     */
    public static final class DAO {

    }
}
