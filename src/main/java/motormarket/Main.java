package motormarket;

import motormarket.model.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import motormarket.data.CarFilter;
import motormarket.data.DAOUtils;
import motormarket.data.Listing;
import motormarket.data.Order;
import motormarket.data.OrderItem;
import motormarket.data.OrderList;
import motormarket.data.SparePartFilter;
import motormarket.data.User;
import motormarket.model.DBModel;

public class Main {
    public static void main(String[] args) {
        var connection = DAOUtils.localMySQLConnection("ecommerce", "root", "SqualoBO_26Luca");
        var model = Model.fromConnection(connection);
        List<OrderItem> list = new ArrayList<>();
        list.add(new OrderItem(4, "Auto", 4, 1, new BigDecimal(21500),"test", "test"));
        list.add(new OrderItem(9, "Pezzo", 3, 1, new BigDecimal(180), "test", "test"));
        OrderList order = model.addOrder(7, "Via Roma 10, Napoli", list);
        System.out.println(order);
    }
}
