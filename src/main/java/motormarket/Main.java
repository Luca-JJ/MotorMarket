package motormarket;

import motormarket.model.Model;
import motormarket.view.MotorMarketFrame;

import motormarket.data.DAOUtils;

import motormarket.controller.*;
import motormarket.view.*;

public class Main {
    public static void main(String[] args) {
        var connection = DAOUtils.localMySQLConnection("ecommerce", "root", "26Luca");
        var model = Model.fromConnection(connection);
        Controller controller = new MotorMarketController(model);
        View view = new MotorMarketFrame(controller);
        
    }
}
