package motormarket.model;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import motormarket.data.CarFilter;
import motormarket.data.Listing;
import motormarket.data.Order;
import motormarket.data.OrderItem;
import motormarket.data.OrderList;
import motormarket.data.SparePart;
import motormarket.data.SparePartFilter;
import motormarket.data.User;
import motormarket.data.User.UserType;

/**
 * The main interface representing the Data Model layer of the application.
 * <p>
 * It defines the contract for all data operations, abstracting the underlying 
 * persistence mechanism (e.g., a live database connection or a mocked in-memory store) 
 * from the rest of the application components.
 * </p>
 */
public interface Model {
    
    /**
     * Registers and persists a new user into the system.
     * @param email     The unique email address of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param password  The account password.
     * @param userType  The category/role assigned to the user (e.g., Privato, Aziendale).
     * @return The fully populated {@link User} object containing its unique system ID,
     * or {@code null} if the registration failed.
     */
    User addUser(String email, String firstName, String lastName, String password, String userType);

    //difference between car and sparepart is managed by the gui(app)

    /**
     * Retrieves a paginated list of active car listings based on user-defined search criteria.
     *
     * @param filter the active search criteria and pagination rules applied by the user
     * @return       a list of matching {@link Listing} business entities
     */
    List<Listing> showCarListings(CarFilter filter);

    /**
     * Retrieves a paginated list of active spare part listings based on user-defined search criteria.
     *
     * @param filter the active search criteria and pagination rules applied by the user
     * @return       a list of matching {@link Listing} business entities
     */
    List<Listing> showSparePartListings(SparePartFilter filter);

    /**
     * Retrieves a list of the orders of an user with the items inside
     *
     * @param UserId the id of the user
     * @return       a list of matching {@link OrderList} business entities
     */
    List<OrderList> showOrders(int userId);

    /**
     * Add a new order in the database for a specific user
     * 
     * @param userId the user of the order
     * @param address the address of the order
     * @param orderItems the list with the specific of all items to add
     * @return the specific order added
     */
    OrderList addOrder(int userId, String address, List<OrderItem> orderItems); 

    // Create a mocked version of the model.
    //
    /*static Model mock() {
        return new MockedModel();
    }*/

    /**
     * Factory method to create a concrete {@code Model} implementation backed 
     * by a relational database connection.
     * @param connection The active {@link Connection} to the database.
     * @return A new instance of {@link DBModel} initialized with the provided connection.
     */
    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
