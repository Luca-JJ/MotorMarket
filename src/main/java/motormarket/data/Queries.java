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
    
    public static final String SEARCH_CAR_LISTING = 
    """
    SELECT 
    a.idAnnuncio, 
    a.Prezzo, 
    a.DataInserimento, 
    a.Stato,
    a.IdUtente,
    
    -- Dati dell'Automobile
    au.IdAuto, 
    au.VIN, 
    au.Targa, 
    au.AnnoPrimaImmatricolazione, 
    au.Potenza, 
    au.TipoCambio,
    au.Disponibilita,
    
    -- Dati del Modello e del Produttore (Oggetti Correlati)
    m.IdModello, 
    m.NomeModello, 
    p.IdProduttore, 
    p.NomeProduttore AS NomeMarca,
    p.Nazionalita,
    p.AutoVendute,
    
    -- Dati dell'Alimentazione
    al.IdAlimentazione, 
    al.NomeAlimentazione,
    
    -- Dati dei Colori (Interni ed Esterni)
    c_est.IdColore AS IdColoreEsterno, 
    c_est.NomeColore AS NomeColoreEsterno,
    c_int.IdColore AS IdColoreInterno,
    c_int.NomeColore AS NomeColoreInterno
FROM 
    `annunci` a, 
    `automobili` au, 
    `modelli` m, 
    `produttori` p, 
    `tipi_alimentazione` al, 
    `colori` c_est,
    `colori` c_int  -- Doppia inclusione della tabella colori per interni ed esterni
WHERE 
    a.IdAuto = au.IdAuto
    AND au.IdModello = m.IdModello
    AND m.IdProduttore = p.IdProduttore
    AND au.IdAlimentazione = al.IdAlimentazione
    AND au.IdColoreEsterni = c_est.IdColore
    AND au.IdColoreInterni = c_int.IdColore
    
    -- Filtri di Stato Fisso (Mostra solo auto attive e disponibili alla vendita)
    AND a.Stato = 'Attivo' 
    AND a.TipoAnnuncio = 'Auto'
    AND au.Disponibilita = 'Disponibile'
    
    -- Filtri Dinamici ad incastro (1 e 2: Modello, 3 e 4: Alimentazione, 5 e 6: Cambio, 7 e 8: Colore Est, 9 e 10: Colore Int)
    AND (? IS NULL OR m.IdModello = ?)
    AND (? IS NULL OR au.IdAlimentazione = ?)
    AND (? IS NULL OR au.TipoCambio = ?)
    AND (? IS NULL OR au.IdColoreEsterni = ?)
    AND (? IS NULL OR au.IdColoreInterni = ?)
    
    -- Filtri Dinamici a Range (11: Prezzo Min, 12: Prezzo Max, 13: Anno Min, 14: Anno Max, 15: Potenza Min, 16: Potenza Max)
    AND (? IS NULL OR a.Prezzo >= ?)
    AND (? IS NULL OR a.Prezzo <= ?)
    AND (? IS NULL OR au.AnnoPrimaImmatricolazione >= ?)
    AND (? IS NULL OR au.AnnoPrimaImmatricolazione <= ?)
    AND (? IS NULL OR au.Potenza >= ?)
    AND (? IS NULL OR au.Potenza <= ?)
    
    -- Ordinamento e Paginazione (17: Valore dell'OFFSET)
    ORDER BY a.DataInserimento DESC
    LIMIT 20 OFFSET ?;
    """;

    public static final String GET_USER =
        """
        SELECT *
        FROM utenti u
        WHERE u.IdUtente = ?   
        """;

    public static final String GET_COLOR =
        """
        SELECT *
        FROM colori c
        WHERE c.IdColore = ?   
        """;

    public static final String GET_FUEL =
        """
        SELECT *
        FROM tipi_alimentazione t
        WHERE t.Alimentazione = ?   
        """;

   public static final String GET_EQUIPMENT =
        """
        SELECT a.*
        FROM equipaggiamenti e, accessori a
        WHERE e.IdAccessorio = a.IdAccessorio
        AND e.IdAuto = ?   
        """;

    public static final String GET_MODEL =
        """
        SELECT *
        FROM modelli m
        WHERE m.IdModello = ?   
        """;

    public static final String GET_PRODUCER =
        """
        SELECT *
        FROM produttori p
        WHERE p.IdProduttore = ?   
        """;

    public static final String GET_SPAREPART =
        """
        SELECT *
        FROM pezzi_di_ricambio p
        WHERE p.IdPezzo = ?   
        """; 

}
