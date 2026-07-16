package motormarket.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import motormarket.data.CarFilter;
import motormarket.data.Listing;
import motormarket.data.OrderItem;
import motormarket.data.OrderList;
import motormarket.data.Producer;
import motormarket.data.ReviewDetail;
import motormarket.data.Service;
import motormarket.data.SparePart;
import motormarket.data.SparePartFilter;
import motormarket.data.User;
import motormarket.data.User.UserType;

public interface Controller {

    /**
     * This is the method for the login
     * 
     * @param email
     * @param password
     * @return
     */
    UserType authenticate(String email, String password);

    /**
     * This is the method for the registration
     * 
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return
     */
    User registrate(String firstName, String lastName, String email, String password);

    /**
     * This method show the orders
     * 
     * @return
     */
    List<OrderList> showOrders();

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
     * the string with accessories
     * 
     * @return the map with string and associated id
     */
    Map<String,Integer> getAccessories();

    /**
     * the string with producers
     * 
     * @return the map with string and associated id
     */
    Map<String,Integer> getProducers();

     /**
     * the string with spareparts
     * 
     * @return the map with string and associated id
     */
    Map<String, Integer> getSpareParts();

    /**
     * Show all listings of cars
     * 
     * @param filter
     * @return
     */
    List<Listing> showCarListings (CarFilter filter);

    /**
     * 
     * @return
     */
    List<Service> getServices();

    /**
     * 
     * @param address
     * @param orderItems
     * @param services
     * @return
     */
    OrderList addOrder(String address, List<OrderItem> orderItems, List<Service> services);

    /**
     * 
     * @return
     */
    Producer getMostSoldProducer();

    /**
     * 
     * @return
     */
    SparePart getMostSoldSparePart();

     /**
     * Get the monthly revenue
     * 
     * @param month
     * @param year
     * @return the revenue in a specific month and year
     */
    double getMonthlyRevenue(int month, int year);

    /**
     * 
     */
    void logout();

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
     * @param accessoryIds
     * @return true if the car was inserted
     */
    boolean addCar(String vin, String licensePlate, int registrationYear, int power, String transmissionType, 
        int modelId, int fuelTypeId, int interiorColorId, int exteriorColorId, List<Integer> accessoryIds);

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
     * Update the quantity of a spare part
     * 
     * @param partId the id of the spare part
     * @param deltaQuantity the delta of the quantity
     * @return the new quantity
     */
    Integer addStockQuantity(int partId, int deltaQuantity);
    
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
     * This is the method for the registration of a corporate user
     * 
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return
     */
    User addCorporateUser(String firstName, String lastName, String email, String password);

    /**
     * Delete the selected  corporate user
     * 
     * @param userId
     * @return
     */
    boolean deleteCorporateUser(int userId);

    /**
     * the string with corporate users
     * 
     * @return the map with string and associated id
     */
    Map<String,Integer> getCorporateUsers();

    /**
     * the string with lisitngs
     * 
     * @return the map with string and associated id
     */
    Map<String,Integer> getListings();

    /**
     * Change the status of a listing in the database
     * 
     * @param advertisementId
     * @param newStatus
     * @return true if the status was changed 
     */
    boolean updateListingStatus(int advertisementId, String newStatus);

    /**
     * the string with cars without listing
     * 
     * @return the map with string and associated id
     */
    Map<String, Integer> getCarsWithoutListing();

    /**
     * the string with spare part without listing
     * 
     * @return the map with string and associated id
     */
    Map<String, Integer> getSparePartsWithoutListing();

    /**
     * Insert a new car listing in the database
     * 
     * @param carId
     * @param price
     * @param userId
     * @return true if the listing was insert
     */
    boolean addCarListing(int carId, double price);

    /**
     * Insert a new spare part listing in the database
     * 
     * @param partId
     * @param price
     * @param userId
     * @return true if the listing was insert
     */
    boolean addSparePartListing(int partId, double price);

    /**
     * Retrieves a paginated list of active spare part listings based on user-defined search criteria.
     *
     * @param filter the active search criteria and pagination rules applied by the user
     * @return       a list of matching {@link Listing} business entities
     */
    List<Listing> showSparePartListings(SparePartFilter filter);

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
     * Insert a new review on a specific model of car
     * 
     * @param modelId the Id of the car model
     * @param rating the rating
     * @param title the title
     * @param text the review
     * @return true if the review was inserted, false if the user never purchased this spare part
     */
    boolean addModelReview(int modelId, int rating, String title, String text);

    /**
     * Insert a new review on a specific spare part
     * 
     * @param partId the Id of the spare part
     * @param rating the rating
     * @param title the title
     * @param text the review
     * @return true if the review was inserted, false if the user never purchased this spare part
     */
    boolean addSparePartReview(int partId, int rating, String title, String text);

    /**
     * the string with models bought by the user
     * 
     * @return the map with string and associated id
     */
    Map<String, Integer> getBoughtModels();

    /**
     * the string with spareparts bought by the user
     * 
     * @return the map with string and associated id
     */
    Map<String, Integer> getBoughtSpareParts();
}
