package motormarket.data;

import java.sql.Connection;
import java.sql.ResultSet;


/**
 * Represents a registered user within the motor market system.
 * This entity class manages general user profile information and credentials.
 */
public class User {

    /**
     * Defines the account categories available for users in the system.
     * Maps the application logic types to their respective database string values.
     */
    public enum UserType {
        /** Represents a standard private individual user account. */
        PRIVATO("Privato"),
        
        /** Represents a corporate company account. */
        AZIENDALE("Aziendale"),
        
        /** Represents a platform administrator with elevated management privileges. */
        AMMINISTRATORE("Amministratore");
        private final String dbValue;

        
        UserType(String dbValue) {
            this.dbValue = dbValue;
        }

        /**
         * Gets the raw string representation stored in the database.
         * @return The database string representation of the user type.
         */
        public String getDbValue() {
            return dbValue;
        }

        /**
         * Converts a raw input string into its corresponding UserType enum constant.
         * <p>
         * The resolution matches against the enum names (e.g., "PRIVATO") in a 
         * case-insensitive manner to ensure flexible input parsing.
         * </p>
         * @param text The string name of the user type to look up.
         * @return The matching {@link UserType} enum constant.
         * @throws IllegalArgumentException If the provided text does not match any existing user type.
         */
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

    /**
     * Constructs a new User instance with all mandatory profile details.
     * @param userId    The unique identifier assigned to the user by the database.
     * @param email     The unique email address of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param password  The encrypted or plain text password of the user.
     * @param userType  The specific account role/category of the user.
     */
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

    /**
     * Data Access Object (DAO) providing persistent storage capabilities for {@link User} entities.
     * Handles database CRUD interactions related to users.
     */
    public static final class DAO {

        /**
         * Inserts a new user record into the database and returns the fully populated model instance.
         * @param connection The active JDBC database connection.
         * @param email      The email address for the new user.
         * @param firstName  The first name of the user.
         * @param lastName   The last name of the user.
         * @param password   The password to be stored.
         * @param userType   The string representation of the account category (must match a valid type name).
         * @return A newly instantiated {@link User} object containing the auto-generated database ID, 
         * or {@code null} if the insertion failed.
         * @throws DAOException If any database error or unexpected exception occurs during execution.
         */
        public static User addUser(Connection connection, String email, String firstName, String lastName, String password, String userType) {
            User user = null;
            try(
                var statement = DAOUtils.prepare(connection, Queries.INSERT_USER, email, firstName, lastName, password, userType);
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

        /**
         * Finds and instantiates a {@link User} entity from the database by its unique ID.
         *
         * @param connection the active database connection
         * @param userId     the unique system identifier of the user (IdUtente)
         * @return           the fully populated {@link User} object, or {@code null} if not found
         * @throws DAOException if a database access error occurs or parsing fails
         */
        public static User findUser(Connection connection, int userId) {
            User user = null;
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_USER, userId);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var email = resultSet.getString("Email");
                    var firstName = resultSet.getString("Nome");
                    var lastName = resultSet.getString("Cognome");
                    var password = resultSet.getString("Password");
                    var userType = UserType.fromString(resultSet.getString("TipoUtente"));
                    user = new User(userId, email, firstName, lastName, password, userType);
                }
                
            } catch (Exception e) {
                throw new DAOException(e);
            }

            return user;
        }

        /**
         * Finds the usertype from his email and password
         * 
         * @param connection
         * @param email
         * @param password
         * @return if exist return the userType associated
         */
        public static User userTypeAuthentication(Connection connection, String email, String password) {
            User user = null;
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_USER_TYPE_AUTHENTICATE, email, password);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    var userId = resultSet.getInt("IdUtente");
                    var firstName = resultSet.getString("Nome");
                    var lastName = resultSet.getString("Cognome");
                    var userType = UserType.fromString(resultSet.getString("TipoUtente"));
                    user = new User(userId, email, firstName, lastName, password, userType);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return user;
        }
    }
}
