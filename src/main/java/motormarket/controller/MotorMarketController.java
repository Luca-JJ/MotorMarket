package motormarket.controller;

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
    public Map<String, Integer> getColors() {
        return model.getColors();
    }

    @Override
    public Map<String, Integer> getFuels() {
        return model.getFuels();
    }

    @Override
    public List<Listing> showCarListings(CarFilter filter) {
        return model.showCarListings(filter);
    }

    @Override
    public List<Service> getServices() {
        return model.getServices();
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

    
    
}
