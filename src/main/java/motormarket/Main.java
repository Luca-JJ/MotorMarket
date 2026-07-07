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
import motormarket.data.Producer;
import motormarket.data.SparePart;
import motormarket.data.SparePartFilter;
import motormarket.data.User;
import motormarket.model.DBModel;

public class Main {
    public static void main(String[] args) {
        var connection = DAOUtils.localMySQLConnection("ecommerce", "root", "SqualoBO_26Luca");
        var model = Model.fromConnection(connection);
        Producer producer = model.getMostSoldProducer();
        System.out.println(producer);
        SparePart sparePart = model.getMostSoldSparePart();
        System.out.println(sparePart);
        Integer quantity = model.addStockQuantity(3, 2);
        System.out.println(quantity);
    }
}
