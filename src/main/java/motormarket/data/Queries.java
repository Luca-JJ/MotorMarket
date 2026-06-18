package motormarket.data;

public final class Queries {

    public static final String USER_INSERT =
        """
        INSERT INTO utenti (Email, Nome, Cognome, Password, TipoUtente) VALUES (?, ?, ?, ?, ?)
        """;

}
