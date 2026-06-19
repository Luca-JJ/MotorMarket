package motormarket.data;

import java.io.Serial;

/**
 * A custom runtime exception used to wrap low-level persistence or database errors 
 * (such as {@link java.sql.SQLException}).
 * <p>
 * By converting checked database exceptions into this unchecked exception, it prevents 
 * SQL-specific details from leaking into higher architectural layers (like controllers or views), 
 * leading to cleaner and more maintainable method signatures across the application.
 * </p>
 */
public final class DAOException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DAOException with the specified detail message.
     * @param message The detail message explaining the reason for the exception.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new DAOException with the specified underlying cause.
     * @param cause The root cause of the exception (typically a {@code SQLException}).
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new DAOException with the specified detail message and 
     * underlying cause.
     * @param message The detail message explaining the reason for the exception.
     * @param cause   The root cause of the exception.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
