package motormarket;

import motormarket.model.Model;

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

public class Main {
    public static void main(String[] args) {
        var connection = DAOUtils.localMySQLConnection("ecommerce", "root", "SqualoBO_26Luca");
        var model = Model.fromConnection(connection);
        List<OrderItem> list = new ArrayList<>();
        list.add(new OrderItem(1, "Auto", 1, 1, new BigDecimal(11900),"test", "test"));
        list.add(new OrderItem(18, "Pezzo", 20, 1, new BigDecimal(1000), "test", "test"));
        List<Service> services = new ArrayList<>();
        services.add(new Service(2, "Spedizione Veloce", new BigDecimal(10), ServiceType.PEZZO));
        services.add(new Service(3, "Lavaggio", new BigDecimal(80), ServiceType.AUTO));
        OrderList order = model.addOrder(4, "Via Garbaldi 89, Bologna", list, services);
        System.out.println(order);
        
    }
}
