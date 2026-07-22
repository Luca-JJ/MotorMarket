package motormarket.controller;

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
import motormarket.model.Model;
import motormarket.view.View;

public class MotorMarketController implements Controller {

    private Model model; 
    private View view;

    

    public MotorMarketController(Model model) {
        this.model = model;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public UserType authenticate(String email, String password){
        return model.userTypeAuthentication(email, password);
    }

    @Override
    public User registrate(String firstName, String lastName, String email, String password) {
        return model.addUser(email, firstName, lastName, password, "Privato");
    }

    @Override
    public List<OrderList> showOrders() {
        return model.showOrders(model.getUserId());
    }

    @Override
    public Map<String,Integer> brandsModels() {
        return model.brandsModels();
    }

    @Override
    public Map<String,Integer> getProducers() {
        return model.getProducers();
    }

    @Override
    public Map<String, Integer> getColors() {
        return model.getColors();
    }

    @Override
    public Map<String, Integer> getFuels() {
        return model.getFuels();
    }

    @Override
    public Map<String, Integer> getAccessories() {
        return model.getAccessories();
    }

    @Override
    public Map<String, Integer> getSpareParts() {
        return model.getSpareParts();
    }

    @Override
    public List<Listing> showCarListings(CarFilter filter) {
        return model.showCarListings(filter);
    }

    @Override
    public List<Service> getServices(String type) {
        return model.getServices(type);
    }

    @Override
    public OrderList addOrder(String address, List<OrderItem> orderItems, List<Service> services) {
        return model.addOrder(model.getUserId(), address, orderItems, services);
    }

    @Override
    public Producer getMostSoldProducer() {
        return model.getMostSoldProducer();
    }

    @Override
    public SparePart getMostSoldSparePart() {
        return model.getMostSoldSparePart();
    }

    @Override
    public void logout() {
        model.logout();
        return;
    }

    @Override
    public double getMonthlyRevenue(int month, int year) {
        return model.getMonthlyRevenue(month, year);
    }

    @Override
    public boolean addCar(String vin, String licensePlate, int registrationYear, int power, String transmissionType,
            int modelId, int fuelTypeId, int interiorColorId, int exteriorColorId, List<Integer> accessoryIds) {
        return model.addCar(vin, licensePlate, registrationYear, power, transmissionType, modelId, fuelTypeId, interiorColorId, exteriorColorId, model.getUserId(), accessoryIds);
    }

    @Override
    public boolean addSparePartWithCompatibilities(String name, int stockQuantity, String type, int producerId,
            List<Integer> compatibleModelIds) {
        return model.addSparePartWithCompatibilities(name, stockQuantity, type, producerId, compatibleModelIds);
    }

    @Override
    public Integer addStockQuantity(int partId, int deltaQuantity) {
        return model.addStockQuantity(partId, deltaQuantity);
    }

    @Override
    public boolean addAccessory(String name) {
        return model.addAccessory(name);
    }

    @Override
    public boolean addExtraService(String name, String serviceType, double price) {
        return model.addExtraService(name, serviceType, price);
    }
    
    @Override
    public User addCorporateUser(String firstName, String lastName, String email, String password) {
        return model.addUser(email, firstName, lastName, password, "Aziendale");
    }

    @Override
    public boolean deleteCorporateUser(int userId) {
        return model.deleteUser(userId, "Aziendale");
    }

    @Override
    public Map<String, Integer> getCorporateUsers() {
        return model.getCorporateUsers();
    }

    @Override
    public Map<String, Integer> getListings() {
        return model.getListings();
    }

    @Override
    public boolean updateListingStatus(int advertisementId, String newStatus) {
        return model.updateListingStatus(advertisementId, newStatus);
    }

    @Override
    public Map<String, Integer> getCarsWithoutListing() {
        return model.getCarsWithoutListing();
    }

    @Override
    public boolean addCarListing(int carId, double price) {
        return model.addCarListing(carId, price, model.getUserId());
    }

    @Override
    public boolean addSparePartListing(int partId, double price) {
        return model.addSparePartListing(partId, price, model.getUserId());
    }

    @Override
    public Map<String, Integer> getSparePartsWithoutListing() {
        return model.getSparePartsWithoutListing();
    }

    @Override
    public List<Listing> showSparePartListings(SparePartFilter filter) {
        return model.showSparePartListings(filter);
    }

    @Override
    public List<ReviewDetail> getModelReviews(int modelId) {
        return model.getModelReviews(modelId);
    }

    @Override
    public List<ReviewDetail> getSparePartReviews(int partId) {
        return model.getSparePartReviews(partId);
    }

    @Override
    public boolean addModelReview(int modelId, int rating, String title, String text) {
        return model.addModelReview(model.getUserId(), modelId, rating, title, text);
    }

    @Override
    public boolean addSparePartReview(int partId, int rating, String title, String text) {
        return model.addSparePartReview(model.getUserId(), partId, rating, title, text);
    }

    @Override
    public Map<String, Integer> getBoughtModels() {
        return model.getBoughtModels(model.getUserId());
    }

    @Override
    public Map<String, Integer> getBoughtSpareParts() {
        return model.getBoughtSpareParts(model.getUserId());
    }
}
