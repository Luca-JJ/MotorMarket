package motormarket;

import motormarket.model.Model;
import motormarket.view.MotorMarketFrame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import motormarket.data.CarFilter;
import motormarket.data.DAOUtils;
import motormarket.data.Listing;
import motormarket.data.Order;
import motormarket.data.OrderItem;
import motormarket.data.OrderList;
import motormarket.data.Producer;
import motormarket.data.ReviewDetail;
import motormarket.data.Service;
import motormarket.data.Service.ServiceType;
import motormarket.data.SparePart;
import motormarket.data.SparePartFilter;
import motormarket.data.User;
import motormarket.model.DBModel;
import motormarket.controller.*;
import motormarket.view.*;

public class Main {
    public static void main(String[] args) {
        var connection = DAOUtils.localMySQLConnection("ecommerce", "root", "SqualoBO_26Luca");
        var model = Model.fromConnection(connection);
        Controller controller = new MotorMarketController(model);
        View view = new MotorMarketFrame(controller);
        
    }
}
