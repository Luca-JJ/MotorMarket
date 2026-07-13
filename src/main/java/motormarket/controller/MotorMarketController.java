package motormarket.controller;

import java.util.List;

import motormarket.data.OrderItem;
import motormarket.data.OrderList;
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
        System.out.println(model.getUserId());
        return model.showOrders(model.getUserId());
    }
}
