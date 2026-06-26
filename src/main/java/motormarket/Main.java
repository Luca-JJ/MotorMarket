package motormarket;

import motormarket.model.Model;

import java.util.List;

import motormarket.data.CarFilter;
import motormarket.data.DAOUtils;
import motormarket.data.Listing;
import motormarket.data.SparePartFilter;
import motormarket.data.User;
import motormarket.model.DBModel;

public class Main {
    public static void main(String[] args) {
        var connection = DAOUtils.localMySQLConnection("ecommerce", "root", "SqualoBO_26Luca");
        var model = Model.fromConnection(connection);
        SparePartFilter filter = new SparePartFilter();
        List<Listing> sparePartListings = model.showSparePartListings(filter);
        System.out.println(sparePartListings);
    }
}
