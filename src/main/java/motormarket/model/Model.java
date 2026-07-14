package motormarket.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import motormarket.data.CarFilter;
import motormarket.data.Listing;
import motormarket.data.Order;
import motormarket.data.OrderItem;
import motormarket.data.OrderList;
import motormarket.data.Producer;
import motormarket.data.ReviewDetail;
import motormarket.data.Service;
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
     * 
     * @return the user that use the application
     */
    int getUserId();

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
     * @param services the list with all services present in the order
     * @return the specific order added
     */
    OrderList addOrder(int userId, String address, List<OrderItem> orderItems, List<Service> services); 

    /**
     * Searches the database for the top-performing car manufacturer.
     *  
     * @return the Producer that has sold the highest number of cars.
     */
    Producer getMostSoldProducer();

    /**
     * Searches the database for the top-performing spare part component.
     * 
     * @return the SparePart that has the highest sales volume.
     */
    SparePart getMostSoldSparePart();

    /**
     * Update the quantity of a spare part
     * 
     * @param partId the id of the spare part
     * @param deltaQuantity the delta of the quantity
     * @return the new quantity
     */
    Integer addStockQuantity(int partId, int deltaQuantity);
    
    /**
     * Insert a new review on a specific model of car
     * 
     * @param userId the Id of the user
     * @param modelId the Id of the car model
     * @param rating the rating
     * @param title the title
     * @param text the review
     * @return true if the review was inserted, false if the user never purchased this spare part
     */
    boolean addModelReview(int userId, int modelId, int rating, String title, String text);

    /**
     * Insert a new review on a specific spare part
     * 
     * @param userId the Id of the user
     * @param partId the Id of the spare part
     * @param rating the rating
     * @param title the title
     * @param text the review
     * @return true if the review was inserted, false if the user never purchased this spare part
     */
    boolean addSparePartReview(int userId, int partId, int rating, String title, String text);

    /**
     * Get all reviews about a specific car model
     * 
     * @param modelId the id
     * @return the reviews with the average vote
     */
    List<ReviewDetail> getModelReviews (int modelId);

    /**
     * Get all reviews about a specific spare part
     * 
     * @param partId the id
     * @return the reviews with the average vote
     */
    List<ReviewDetail> getSparePartReviews (int partId);

    /**
     * Insert a new car in the database
     * 
     * @param vin
     * @param licensePlate
     * @param registrationYear
     * @param power
     * @param transmissionType
     * @param modelId
     * @param fuelTypeId
     * @param interiorColorId
     * @param exteriorColorId
     * @param userId
     * @param accessoryIds
     * @return true if the car was inserted
     */
    boolean addCar(String vin, String licensePlate, int registrationYear, int power, String transmissionType, 
        int modelId, int fuelTypeId, int interiorColorId, int exteriorColorId, int userId, List<Integer> accessoryIds);

    /**
     * Insert a new spare part in the database
     * 
     * @param name
     * @param stockQuantity
     * @param type
     * @param producerId
     * @param compatibleModelIds
     * @return true if the spare part was inserted
     */
    boolean addSparePartWithCompatibilities( String name, int stockQuantity, String type, int producerId, List<Integer> compatibleModelIds);

    /**
     * Insert a new accessory in the database
     * 
     * @param name
     * @return true if the accessory was inserted
     */
    boolean addAccessory(String name);

    /**
     * Insert a new service in the database
     * 
     * @param name
     * @param serviceType
     * @param price
     * @return true if the service was inserted
     */
    boolean addExtraService(String name, String serviceType, double price);
    
    /**
     * Insert a new car listing in the database
     * 
     * @param carId
     * @param price
     * @param userId
     * @return true if the listing was insert
     */
    boolean addCarListing(int carId, double price, int userId);

    /**
     * Insert a new spare part listing in the database
     * 
     * @param partId
     * @param price
     * @param userId
     * @return true if the listing was insert
     */
    boolean addSparePartListing(int partId, double price, int userId);

    /**
     * Change the status of a listing in the database
     * 
     * @param advertisementId
     * @param newStatus
     * @return true if the status was changed 
     */
    boolean updateListingStatus(int advertisementId, String newStatus);

    /**
     * Get the monthly revenue
     * 
     * @param month
     * @param year
     * @return the revenue in a specific month and year
     */
    double getMonthlyRevenue(int month, int year);

    /**
     * Get the userType from the authentication
     * 
     * @param email
     * @param password
     * @return the userType
     */
    UserType userTypeAuthentication(String email, String password);
    
    /**
     * the string with brand and model
     * 
     * @return the map with string and associated Id
     */
    Map<String,Integer> brandsModels();

    /**
     * the string with colors
     * 
     * @return the map with string and associated Id
     */
    Map<String,Integer> getColors();

    /**
     * the string with fuel types
     * 
     * @return the map with string and associated Id
     */
    Map<String,Integer> getFuels();
    
    /**
     * 
     * @return
     */
    List<Service> getServices();

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
