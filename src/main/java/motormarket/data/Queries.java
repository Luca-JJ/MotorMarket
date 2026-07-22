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
    public static final String INSERT_USER =
        """
        INSERT INTO utenti (Email, Nome, Cognome, Password, TipoUtente) 
        VALUES (?, ?, ?, ?, ?)
        """;
    
    public static final String SEARCH_CAR_LISTING = 
        """
        SELECT a.idAnnuncio, a.Prezzo, a.DataInserimento, 
        a.Stato, a.IdUtente, au.IdAuto, au.VIN, 
        au.Targa, au.AnnoPrimaImmatricolazione, 
        au.Potenza, au.TipoCambio, au.Disponibilita,
        m.IdModello, m.NomeModello, 
        p.IdProduttore, 
        p.NomeProduttore AS NomeMarca,
        p.Nazionalita, p.AutoVendute,
        al.IdAlimentazione, al.NomeAlimentazione,
        c_est.IdColore AS IdColoreEsterno, 
        c_est.NomeColore AS NomeColoreEsterno,
        c_int.IdColore AS IdColoreInterno,
        c_int.NomeColore AS NomeColoreInterno
        FROM annunci a, automobili au, modelli m, produttori p, 
        tipi_alimentazione al, colori c_est, colori c_int
        WHERE a.IdAuto = au.IdAuto
        AND au.IdModello = m.IdModello
        AND m.IdProduttore = p.IdProduttore
        AND au.IdAlimentazione = al.IdAlimentazione
        AND au.IdColoreEsterni = c_est.IdColore
        AND au.IdColoreInterni = c_int.IdColore
        AND a.Stato = 'Attivo' 
        AND a.TipoAnnuncio = 'Auto'
        AND au.Disponibilita = 'Disponibile'
        AND (? IS NULL OR m.IdModello = ?)
        AND (? IS NULL OR au.IdAlimentazione = ?)
        AND (? IS NULL OR au.TipoCambio = ?)
        AND (? IS NULL OR au.IdColoreEsterni = ?)
        AND (? IS NULL OR au.IdColoreInterni = ?)
        AND (? IS NULL OR a.Prezzo >= ?)
        AND (? IS NULL OR a.Prezzo <= ?)
        AND (? IS NULL OR au.AnnoPrimaImmatricolazione >= ?)
        AND (? IS NULL OR au.AnnoPrimaImmatricolazione <= ?)
        AND (? IS NULL OR au.Potenza >= ?)
        AND (? IS NULL OR au.Potenza <= ?)
        ORDER BY a.DataInserimento DESC
        LIMIT 20 OFFSET ?;
        """;

    public static final String SEARCH_SPAREPART_LISTING =
        """
        SELECT a.idAnnuncio, a.DataInserimento, a.Prezzo, 
        a.Stato, a.IdUtente, p.IdPezzo, 
        p.NomePezzo, p.Tipologia, p.QuantitàMagazzino, 
        p.PezziVenduti, pr.IdProduttore, 
        pr.NomeProduttore AS NomeMarca,
        pr.Nazionalita, pr.AutoVendute
        FROM annunci a
        JOIN pezzi_di_ricambio p ON a.IdPezzo = p.IdPezzo
        JOIN produttori pr ON p.IdProduttore = pr.IdProduttore
        LEFT JOIN compatibilita c ON p.IdPezzo = c.IdPezzo
        WHERE a.TipoAnnuncio = 'Pezzo' 
        AND a.Stato = 'Attivo'
        AND (? IS NULL OR c.IdModello = ?)
        AND (? IS NULL OR a.Prezzo >= ?)
        AND (? IS NULL OR a.Prezzo <= ?)
        GROUP BY a.idAnnuncio
        ORDER BY a.DataInserimento DESC
        LIMIT 20 OFFSET ?;
        """;

    public static final String GET_USER =
        """
        SELECT     *
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

    public static final String GET_COMPATIBILITY =
        """
        SELECT m.*,p.*
        FROM compatibilita c, modelli m, produttori p
        WHERE c.IdModello = m.IdModello
        AND m.IdProduttore = p.IdProduttore
        AND c.IdPezzo = ?;
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

    public static final String  SEARCH_ORDERS =
        """
        SELECT o.IdOrdine, o.Data AS DataOrdine, 
        o.Indirizzo AS IndirizzoSpedizione, v.Quantità, 
        v.PrezzoUnitario, (v.Quantità * v.PrezzoUnitario) AS PrezzoTotaleRiga, 
        a.idAnnuncio, a.TipoAnnuncio, 
        a.IdAuto, a.IdPezzo, 
        m.NomeModello AS NomeModelloAuto, 
        pr_auto.NomeProduttore AS MarcaAuto, 
        p.NomePezzo, pr_pezzi.NomeProduttore AS MarcaPezzo
        FROM ordini o
        JOIN vendite v ON o.IdOrdine = v.IdOrdine
        JOIN annunci a ON v.idAnnuncio = a.idAnnuncio
        LEFT JOIN automobili auto ON a.IdAuto = auto.IdAuto
        LEFT JOIN modelli m ON auto.IdModello = m.IdModello
        LEFT JOIN produttori pr_auto ON m.IdProduttore = pr_auto.IdProduttore
        LEFT JOIN pezzi_di_ricambio p ON a.IdPezzo = p.IdPezzo
        LEFT JOIN produttori pr_pezzi ON p.IdProduttore = pr_pezzi.IdProduttore
        WHERE o.IdUtente = ?
        ORDER BY o.Data DESC, o.IdOrdine DESC;
        """;

    public static final String GET_ADDITION =
        """
        SELECT s.*
        FROM servizi s, aggiunte a
        WHERE s.IdServizio = a.IdServizio
        AND a.IdOrdine = ?   
        """;

    public static final String INSERT_ORDER =
        """
        INSERT INTO ordini (Indirizzo, SpesaTotale, Data, IdUtente)
        VALUES (?, ?, CURDATE(), ?)
        """;

    public static final String INSERT_SALE =
        """
        INSERT INTO vendite (idAnnuncio, IdOrdine, Quantità, PrezzoUnitario)
        SELECT a.idAnnuncio, ?, ?, ?
        FROM annunci a
        WHERE a.idAnnuncio = ? AND a.Stato = 'Attivo'
        """;

    public static final String UPDATE_LISTING_STATUS =
        """
        UPDATE annunci a
        SET a.Stato = 'Venduto'
        WHERE a.idAnnuncio = ?
        """;

    public static final String UPDATE_CAR_AVAILABILITY =
        """
        UPDATE automobili auto, annunci a
        SET auto.Disponibilita = 'NonDisponibile'
        WHERE auto.IdAuto = a.IdAuto AND a.idAnnuncio = ?
        """;

    public static final String UPDATE_SPARE_PART_STOCK =
        """
        UPDATE pezzi_di_ricambio p, annunci a
        SET p.QuantitàMagazzino = p.QuantitàMagazzino - ?,
            p.PezziVenduti = p.PezziVenduti + ?
        WHERE p.IdPezzo = a.IdPezzo AND a.idAnnuncio = ?
        """;

    public static final String CLOSE_EMPTY_SPARE_PART_LISTING =
        """
        UPDATE annunci a, pezzi_di_ricambio p
        SET a.Stato = 'Disattivato'
        WHERE a.IdPezzo = p.IdPezzo AND a.idAnnuncio = ? AND p.QuantitàMagazzino = 0
        """; 

    public static final String INSERT_ADDITION =
        """
        INSERT INTO aggiunte (IdOrdine, IdServizio) VALUES (?, ?)
        """;

    public static final String UPDATE_CARS_SOLD =
        """
        UPDATE automobili a, modelli m, produttori p
        SET p.AutoVendute = p.AutoVendute + 1
        WHERE a.IdModello = m.IdModello AND a.IdAuto = ? AND m.IdProduttore = p.IdProduttore
        """; 

    public static final String GET_MOST_SOLD_PRODUCER =
        """
        SELECT IdProduttore, NomeProduttore, Nazionalita, AutoVendute
        FROM produttori
        ORDER BY AutoVendute DESC
        LIMIT 1
        """;

    public static final String GET_MOST_SOLD_SPARE_PART =
        """
        SELECT p.IdPezzo, p.NomePezzo, p.QuantitàMagazzino, p.Tipologia, p.PezziVenduti, pr.IdProduttore, pr.NomeProduttore, pr.Nazionalita, pr.AutoVendute
        FROM pezzi_di_ricambio p, produttori pr
        WHERE p.IdProduttore = pr.IdProduttore
        ORDER BY p.PezziVenduti DESC
        LIMIT 1
        """;

    public static final String ADD_SPARE_PART_STOCK =
        """
        UPDATE pezzi_di_ricambio
        SET QuantitàMagazzino = QuantitàMagazzino + ?
        WHERE IdPezzo = ?
        """;
    
    public static final String GET_SPARE_PART_STOCK =
        """
        SELECT QuantitàMagazzino
        FROM pezzi_di_ricambio
        WHERE IdPezzo = ?
        """;

    public static final String INSERT_SPARE_PART_REVIEW =
        """
        INSERT INTO recensioni (IdUtente, IdPezzo, Voto, Titolo, Testo, TipoRecensione, DataRecensione)
        SELECT ?, ?, ?, ?, ?, 'Pezzo', CURRENT_DATE()
        FROM ordini o, vendite v, annunci a
        WHERE o.IdOrdine = v.IdOrdine 
        AND v.IdAnnuncio = a.IdAnnuncio
        AND o.IdUtente = ? 
        AND a.IdPezzo = ?
        """;

    public static final String INSERT_MODEL_REVIEW =
        """
        INSERT INTO recensioni (IdUtente, IdModello, Voto, Titolo, Testo, TipoRecensione, DataRecensione)
        SELECT ?, ?, ?, ?, ?, 'Modello', CURRENT_DATE()
        FROM ordini o, vendite v, annunci a, automobili auto
        WHERE o.IdOrdine = v.IdOrdine 
        AND v.idAnnuncio = a.idAnnuncio
        AND a.IdAuto = auto.IdAuto
        AND o.IdUtente = ? 
        AND auto.IdModello = ?
        """;

    public static final String GET_MODEL_REVIEWS =
        """
        SELECT r.Titolo, r.Testo, r.Voto, r.DataRecensione, u.Nome, u.Cognome,
               AVG(r.Voto) OVER() AS MediaVoti
        FROM recensioni r, utenti u
        WHERE r.IdUtente = u.IdUtente
        AND r.IdModello = ? 
        AND r.TipoRecensione = 'Modello'
        ORDER BY r.DataRecensione DESC
        """;


    public static final String GET_SPARE_PART_REVIEWS =
        """
        SELECT r.Titolo, r.Testo, r.Voto, r.DataRecensione, u.Nome, u.Cognome,
               AVG(r.Voto) OVER() AS MediaVoti
        FROM recensioni r, utenti u
        WHERE r.IdUtente = u.IdUtente
        AND r.IdPezzo = ? 
        AND r.TipoRecensione = 'Pezzo'
        ORDER BY r.DataRecensione DESC
        """;

    public static final String INSERT_CAR =
        """
        INSERT INTO automobili (
            VIN, Targa, AnnoPrimaImmatricolazione, Potenza, TipoCambio, 
            IdModello, IdAlimentazione, IdColoreInterni, IdColoreEsterni, IdUtente
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
    
    public static final String INSERT_CAR_ACCESSORY =
        """
        INSERT INTO Equipaggiamenti (IdAuto, IdAccessorio)
        VALUES (?, ?)
        """;
    
    public static final String INSERT_SPARE_PART =
        """
        INSERT INTO pezzi_di_ricambio (NomePezzo, QuantitàMagazzino, Tipologia, IdProduttore)
        VALUES (?, ?, ?, ?)
        """;

    public static final String INSERT_PART_COMPATIBILITY =
        """
        INSERT INTO compatibilita (IdPezzo, IdModello)
        VALUES (?, ?)
        """;

    public static final String INSERT_ACCESSORY =
        """
        INSERT INTO accessori (NomeAccessorio)
        VALUES (?)
        """;
    
    public static final String INSERT_EXTRA_SERVICE =
        """
        INSERT INTO servizi (Nome, TipoServizio, Prezzo)
        VALUES (?, ?, ?)
        """;

    public static final String INSERT_ADVERTISEMENT =
        """
        INSERT INTO annunci (IdAuto, IdPezzo, DataInserimento, Stato, Prezzo, TipoAnnuncio, IdUtente)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String UPDATE_ADVERTISEMENT_STATUS =
        """
        UPDATE annunci SET Stato = ? WHERE idAnnuncio = ?
        """;
    
        
    public static final String GET_MONTHLY_REVENUE =
        """
        SELECT COALESCE(SUM(SpesaTotale), 0.0) AS RicavoTotale
        FROM ordini
        WHERE MONTH(Data) = ?
        AND YEAR(Data) = ?
        """;
    
    public static final String GET_USER_TYPE_AUTHENTICATE =
        """
        SELECT * 
        FROM utenti 
        WHERE Email = ? 
        AND Password = ?
        """;

    public static final String GET_BRANDS_MODELS =
        """
        SELECT p.NomeProduttore, m.NomeModello, m.IdModello
        FROM modelli m, produttori p
        WHERE p.IdProduttore  = m.IdProduttore
        ORDER BY p.NomeProduttore, m.NomeModello;
        """;

    public static final String GET_PRODUCERS =
        """
        SELECT p.*
        FROM produttori p
        ORDER BY p.NomeProduttore
        """;

    public static final String GET_COLORS =
        """
        SELECT c.*
        FROM colori c
        ORDER BY c.NomeColore
        """;

    public static final String GET_FUELS =
        """
        SELECT a.*
        FROM tipi_alimentazione a
        ORDER BY a.NomeAlimentazione
        """;

    public static final String GET_ACCESSORIES =
        """
        SELECT a.*
        FROM accessori a
        ORDER BY a.NomeAccessorio
        """;

    public static final String GET_SPARE_PARTS = 
        """
        SELECT p.*
        FROM pezzi_di_ricambio p
        """;

    public static final String GET_SERVICES = 
        """
        SELECT s.*
        FROM servizi s
        """;

    public static final String GET_SERVICES_BY_TYPE = 
        """
        SELECT s.*
        FROM servizi s
        WHERE s.TipoServizio = ?
        """;

    public static final String GET_CORPORATE_USERS = 
        """
        SELECT u.*
        FROM utenti u
        WHERE u.TipoUtente = 'Aziendale'
        """;
    
    public static final String DELETE_USER =
        """
        DELETE 
        FROM utenti u 
        WHERE u.IdUtente = ? 
        AND u.TipoUtente = ?
        """;

    public static final String GET_LISTINGS =
        """
        SELECT 
        a.*,
        m.NomeModello,
        pr.NomePezzo
        FROM annunci a
        LEFT JOIN automobili c ON a.IdAuto = c.IdAuto
        LEFT JOIN modelli m ON c.IdModello = m.IdModello
        LEFT JOIN pezzi_di_ricambio pr ON a.IdPezzo = pr.IdPezzo 
        WHERE a.stato <> 'Venduto'
        """;
    
    public static final String GET_CARS_WITHOUT_LISTING =
        """
        SELECT p.NomeProduttore, m.NomeModello, a.*
        FROM modelli m, produttori p, automobili a
        WHERE a.IdModello = m.IdModello
        AND p.IdProduttore  = m.IdProduttore
        AND a.IdAuto NOT IN ( SELECT an.IdAuto
                              FROM annunci an
                              WHERE an.IdAuto IS NOT NULL)
        ORDER BY p.NomeProduttore, m.NomeModello
        """;

    public static final String GET_SPARE_PARTS_WITHOUT_LISTING = 
        """
        SELECT pr.*, p.NomeProduttore
        FROM pezzi_di_ricambio pr, produttori p
        WHERE pr.IdProduttore = p.IdProduttore
        AND pr.IdPezzo NOT IN ( SELECT an.IdPezzo
                              FROM annunci an
                              WHERE an.IdPezzo IS NOT NULL)
        ORDER BY p.nomeProduttore, pr.NomePezzo
        """;


    public static final String GET_BROUGHT_MODELS =
        """
        SELECT p.NomeProduttore, m.NomeModello, m.IdModello
        FROM annunci an, automobili a, modelli m, produttori p, vendite v, ordini o
        WHERE an.Idauto = a.IdAuto
        AND a.IdModello = m.IdModello
        AND m.IdProduttore = p.idProduttore
        AND an.IdAnnuncio = v.IdAnnuncio
        AND v.IdOrdine = o.IdOrdine
        AND o.IdUtente = ?
        """;

    public static final String GET_BROUGHT_SPARE_PARTS = 
        """
        SELECT p.*
        FROM annunci an, pezzi_di_ricambio p, vendite v, ordini o
        WHERE an.IdPezzo = p.IdPezzo
        AND an.IdAnnuncio = v.IdAnnuncio
        AND v.IdOrdine = o.IdOrdine
        AND o.IdUtente = ?
        """;
    }
