package motormarket.model;

import motormarket.data.CarFilter;
import motormarket.data.Listing;
import motormarket.data.SparePartFilter;
import motormarket.data.User;
import motormarket.data.User.UserType;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Concrete implementation of the {@link Model} interface that interacts directly 
 * with the underlying relational database using DAO classes.
 * <p>
 * This class serves as the central data management layer, maintaining the active 
 * database connection and routing operational requests to the respective 
 * Data Access Objects (DAOs). It may also handle local caching of loaded resources.
 * </p>
 */
public final class DBModel implements Model {

    private final Connection connection;

    /**
     * Constructs a new DBModel backed by the specified database connection.
     * @param connection The active {@link Connection} to the database. Must not be null.
     * @throws NullPointerException If the provided connection is {@code null}.
     */
    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation delegates the user insertion logic directly to 
     * {@link User.DAO#addUser(Connection, String, String, String, String, String)}.
     * </p>
     * @param email     The unique email address of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param password  The password string.
     * @param userType  The type/role designation of the user.
     * @return The newly created {@link User} object, or {@code null} if the operation failed.
     */
    @Override
    public User addUser(String email, String firstName, String lastName, String password, String userType) {
        return User.DAO.addUser(connection, email, firstName, lastName, password, userType);
    }

    @Override
    public List<Listing> showCarListings(CarFilter filter) {
        return Listing.DAO.showCarListings(connection, filter);
    }

    @Override
    public List<Listing> showSparePartListings(SparePartFilter filter) {
        return Listing.DAO.showSparePartListings(connection, filter);
    }
}
