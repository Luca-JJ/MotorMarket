package motormarket.data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an Order placed by a user within the marketplace.
 * Maps directly to the `ordini` table in the database.
 */
public class Order {

    private int orderId;
    private String address;
    private BigDecimal totalAmount;
    private LocalDate date;
    private int userId;

    /**
     * Constructs a new Order instance with all mandatory database fields.
     * @param orderId     The unique identifier of the order (Auto-incremented in DB).
     * @param address     The shipping or billing address for the order.
     * @param totalAmount The total cost of the order (mapped from SQL DECIMAL).
     * @param date        The placement date of the order.
     * @param userId      The foreign key identifier of the user who placed the order.
     */
    public Order(int orderId, String address, BigDecimal totalAmount, LocalDate date, int userId) {
        this.orderId = orderId;
        this.address = address;
        this.totalAmount = totalAmount;
        this.date = date;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", address='" + address + '\'' +
                ", totalAmount=" + totalAmount +
                ", date=" + date +
                ", userId=" + userId +
                '}';
    }

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link Order} entities.
     * Handles database CRUD interactions related to users.
     */
    public static final class DAO {

    }
}
