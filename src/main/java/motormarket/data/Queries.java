package motormarket.data;

/**
 * Repository class containing all the centralized SQL query constants used by the 
 * application's Data Access Objects (DAOs).
 * <p>
 * Keeping queries isolated within this class improves maintainability and ensures 
 * that changes to the database schema only require updates in a single location.
 * </p>
 */
public final class Queries {

    /**
     * SQL statement to insert a new user record into the utenti table.
     * <p>
     * Expects 5 parameters in the following exact order:
     * </p>
     * <ol>
     * <li>Email (VARCHAR)</li>
     * <li>Nome / First Name (VARCHAR)</li>
     * <li>Cognome / Last Name (VARCHAR)</li>
     * <li>Password (VARCHAR)</li>
     * <li>TipoUtente / User Type (ENUM/VARCHAR)</li>
     * </ol>
     */
    public static final String USER_INSERT =
        """
        INSERT INTO utenti (Email, Nome, Cognome, Password, TipoUtente) VALUES (?, ?, ?, ?, ?)
        """;

}
