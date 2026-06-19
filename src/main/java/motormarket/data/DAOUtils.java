package motormarket.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Utility class providing helper methods for database connectivity and 
 * statement preparation.
 * <p>
 * This class centralizes low-level JDBC operations, such as establishing connections 
 * and securely binding parameters to statements to prevent security vulnerabilities.
 * </p>
 */
public final class DAOUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     * @throws AssertionError If accidentally invoked via reflection.
     */
    private DAOUtils() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    /**
     * Establishes a JDBC connection to a MySQL daemon running on localhost at the default port (3306).
     * @param database The name of the database schema to connect to.
     * @param username The database user credential.
     * @param password The password associated with the database user.
     * @return An active {@link Connection} instance to the specified MySQL database.
     * @throws DAOException If a database access error occurs during connection establishment.
     */
    public static Connection localMySQLConnection(String database, String username, String password) {
        try {
            var host = "localhost";
            var port = "3306";
            var connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;
            return DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /**
     * Prepares a SQL statement and safely binds the provided parameters to it.
     * <p>
     * Using parameterized queries via this method safeguards the application against 
     * SQL Injection vulnerabilities by ensuring user inputs are treated strictly as data.
     * </p>
     * Example usage:
     * <pre>{@code
     * PreparedStatement stmt = DAOUtils.prepare(connection, "SELECT * FROM users WHERE id = ?", userId);
     * }</pre>
     * @param connection The active JDBC database connection.
     * @param query      The raw SQL query string containing placeholders (?).
     * @param values     A variable number of objects representing the arguments to bind to the query.
     * @return A fully parameterized and ready-to-execute {@link PreparedStatement}.
     * @throws SQLException If a database access error occurs or the connection is closed during setup.
     */
    public static PreparedStatement prepare(Connection connection, String query, Object... values) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement;
        } catch (Exception e) {
            if (statement != null) {
                statement.close();
            }
            throw e;
        }
    }
}
