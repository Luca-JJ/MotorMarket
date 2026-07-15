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
import motormarket.data.Service;
import motormarket.data.SparePart;
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
}
