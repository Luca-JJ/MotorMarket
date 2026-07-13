package motormarket.controller;

import motormarket.data.User;
import motormarket.data.User.UserType;

public interface Controller {

    /**
     * This is the method for the login
     * 
     * @param email
     * @param password
     * @return
     */
    UserType authenticate(String email, String password);

    User registrate(String firstName, String lastName, String email, String password);
}
