package motormarket.data;

import java.sql.Connection;
import java.sql.ResultSet;

public class User {

    public enum UserType {
        PRIVATO("Privato"),
        AZIENDALE("Aziendale"),
        AMMINISTRATORE("Amministratore");

        private final String dbValue;

        UserType(String dbValue) {
            this.dbValue = dbValue;
        }

        public String getDbValue() {
            return dbValue;
        }

        public static UserType fromString(String text) {
            for (UserType type : UserType.values()) {
                if (type.name().equalsIgnoreCase(text)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid user type: " + text);
        }
    }

    private int userId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private UserType userType;

    public User(int userId, String email, String firstName, String lastName, String password, UserType userType) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userType=" + userType +
                '}';
    }

    public static final class DAO {

        public static User addUser(Connection connection, String email, String firstName, String lastName, String password, String userType) {
            User user = null;
            try(
                var statement = DAOUtils.prepare(connection, Queries.USER_INSERT, email, firstName, lastName, password, userType);
            )    
            {
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    String sqlId = "SELECT LAST_INSERT_ID()";
                    try (var idStatement = DAOUtils.prepare(connection, sqlId);
                        ResultSet rs = idStatement.executeQuery()) {
                        
                        if (rs.next()) {
                            int generatedId = rs.getInt(1);
                            user = new User(generatedId, email, firstName, lastName, password, UserType.fromString(userType));
                        }
                    }
                }
                

            } catch (Exception e) {
                throw new DAOException(e);
            }

            return user;
        }
    }
}
