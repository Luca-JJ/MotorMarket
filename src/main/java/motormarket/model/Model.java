package motormarket.model;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import motormarket.data.User;
import motormarket.data.User.UserType;

public interface Model {
    
    User addUser(String email, String firstName, String lastName, String password, String userType);

    // Create a mocked version of the model.
    //
    /*static Model mock() {
        return new MockedModel();
    }*/

    // Create a model that connects to a database using the given connection.
    //
    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
