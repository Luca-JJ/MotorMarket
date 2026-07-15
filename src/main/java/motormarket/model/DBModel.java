package motormarket.model;

import motormarket.data.Accessory;
import motormarket.data.Car;
import motormarket.data.CarFilter;
import motormarket.data.CarModel;
import motormarket.data.Color;
import motormarket.data.FuelType;
import motormarket.data.Listing;
import motormarket.data.Order;
import motormarket.data.OrderItem;
import motormarket.data.OrderList;
import motormarket.data.Producer;
import motormarket.data.Review;
import motormarket.data.ReviewDetail;
import motormarket.data.Service;
import motormarket.data.SparePart;
import motormarket.data.SparePartFilter;
import motormarket.data.User;
import motormarket.data.User.UserType;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
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
    private int userId;

    /**
     * Constructs a new DBModel backed by the specified database connection.
     * @param connection The active {@link Connection} to the database. Must not be null.
     * @throws NullPointerException If the provided connection is {@code null}.
     */
    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public int getUserId() {
        return this.userId;
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

    @Override
    public List<OrderList> showOrders(int userId) {
        return Order.DAO.showOrders(connection, userId);
    }

    @Override
    public OrderList addOrder(int userId, String address, List<OrderItem> orderItems, List<Service> services) {
        return Order.DAO.purchaseOrder(connection, userId, address, orderItems, services);
    }

    @Override
    public Producer getMostSoldProducer() {
        return Producer.DAO.getMostSoldProducer(connection);
    }

    @Override
    public SparePart getMostSoldSparePart() {
        return SparePart.DAO.getMostSoldSparePart(connection);
    }

    @Override
    public Integer addStockQuantity(int partId, int deltaQuantity) {
        return SparePart.DAO.addStockQuantity(connection, partId, deltaQuantity);
    }

    @Override
    public boolean addModelReview(int userId, int modelId, int rating, String title, String text) {
        return Review.DAO.addModelReview(connection, userId, modelId, rating, title, text);
    }

    @Override
    public boolean addSparePartReview(int userId, int partId, int rating, String title, String text) {
        return Review.DAO.addSparePartReview(connection, userId, partId, rating, title, text);
    }

    @Override
    public List<ReviewDetail> getModelReviews(int modelId) {
        return Review.DAO.getModelReviews(connection, modelId);
    }

    @Override
    public List<ReviewDetail> getSparePartReviews(int partId) {
        return Review.DAO.getSparePartReviews(connection, partId);
    }

    @Override
    public boolean addCar(String vin, String licensePlate, int registrationYear, int power, String transmissionType,
            int modelId, int fuelTypeId, int interiorColorId, int exteriorColorId, int userId, List<Integer> accessoryIds) {
        return Car.DAO.addCar(connection, vin, licensePlate, registrationYear, power, transmissionType, modelId, fuelTypeId, interiorColorId, exteriorColorId, userId, accessoryIds);
    }

    @Override
    public boolean addSparePartWithCompatibilities(String name, int stockQuantity, String type, int producerId,
            List<Integer> compatibleModelIds) {
        return SparePart.DAO.addSparePartWithCompatibilities(connection, name, stockQuantity, type, producerId, compatibleModelIds);
    }

    @Override
    public boolean addAccessory(String name) {
        return Accessory.DAO.addAccessory(connection, name);
    }

    @Override
    public boolean addExtraService(String name, String serviceType, double price) {
        return Service.DAO.addExtraService(connection, name, serviceType, price);
    }

    @Override
    public boolean addCarListing(int carId, double price, int userId) {
        return Listing.DAO.addCarListing(connection, carId, price, userId);
    }

    @Override
    public boolean addSparePartListing(int partId, double price, int userId) {
        return Listing.DAO.addSparePartListing(connection, partId, price, userId);
    }

    @Override
    public boolean updateListingStatus(int advertisementId, String newStatus) {
        return Listing.DAO.updateListingStatus(connection, advertisementId, newStatus);
    }

    @Override
    public double getMonthlyRevenue(int month, int year) {
        return Order.DAO.getMonthlyRevenue(connection, month, year);
    }

    @Override
    public UserType userTypeAuthentication(String email, String password) {
        User user = User.DAO.userTypeAuthentication(connection, email, password);
        if(user.getUserType() == null) {
            return null;
        }
        this.userId = user.getUserId();
        return user.getUserType();
           

    }

    @Override
    public Map<String, Integer> brandsModels() {
        return CarModel.DAO.brandsModels(connection);
    }

    @Override
    public Map<String, Integer> getProducers() {
        return Producer.DAO.getProducers(connection);
    }

    @Override
    public Map<String, Integer> getColors() {
        return Color.DAO.getColors(connection);
    }

    @Override
    public Map<String, Integer> getFuels() {
        return FuelType.DAO.getFuels(connection);
    }

    @Override
    public Map<String, Integer> getAccessories() {
        return Accessory.DAO.getAccessories(connection);
    }

    @Override
    public Map<String, Integer> getSpareParts() {
        return SparePart.DAO.getSpareParts(connection);
    }

    @Override
    public Map<String, Integer> getCorporateUsers() {
        return User.DAO.getCorporateUsers(connection);
    }


    @Override
    public List<Service> getServices() {
        return Service.DAO.getServices(connection);
    }

    @Override
    public void logout() {
        this.userId = 0;
    }

    @Override
    public boolean deleteUser(int userId, String userType) {
        return User.DAO.deleteUser(connection, userId, userType);
    }
    
}
