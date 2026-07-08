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
        
        WHERE a.IdAuto = au.IdAuto
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

    public static final String SEARCH_SPAREPART_LISTING =
        """
        SELECT 
        a.idAnnuncio,
        a.DataInserimento,
        a.Prezzo,
        a.Stato,
        a.IdUtente,
        p.IdPezzo,
        p.NomePezzo,
        p.Tipologia,
        p.QuantitàMagazzino,
        p.PezziVenduti,
        pr.IdProduttore, 
        pr.NomeProduttore AS NomeMarca,
        pr.Nazionalita,
        pr.AutoVendute

        FROM annunci a
        JOIN pezzi_di_ricambio p ON a.IdPezzo = p.IdPezzo
        JOIN produttori pr ON p.IdProduttore = pr.IdProduttore
        LEFT JOIN compatibilita c ON p.IdPezzo = c.IdPezzo

        WHERE a.TipoAnnuncio = 'Pezzo' 
        AND a.Stato = 'Attivo'
        
        -- 3. PARAMETRI DINAMICI (Filtro Modello e Nome Pezzo)
        AND (? IS NULL OR c.IdModello = ?)
        AND (? IS NULL OR a.Prezzo >= ?)
        AND (? IS NULL OR a.Prezzo <= ?)
        ORDER BY 
        a.DataInserimento DESC
        LIMIT 20 OFFSET ?
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
        SELECT 
        o.IdOrdine,
        o.Data AS DataOrdine,
        o.Indirizzo AS IndirizzoSpedizione,
        v.Quantità,
        v.PrezzoUnitario,
        (v.Quantità * v.PrezzoUnitario) AS PrezzoTotaleRiga,
        a.idAnnuncio,
        a.TipoAnnuncio,
        a.IdAuto,
        a.IdPezzo,
        
        -- Dettagli dell'Automobile (Popolati solo se TipoAnnuncio = 'Auto')
        m.NomeModello AS NomeModelloAuto,
        pr_auto.NomeProduttore AS MarcaAuto,
        
        -- Dettagli del Pezzo di Ricambio (Popolati solo se TipoAnnuncio = 'Pezzo')
        p.NomePezzo,
        pr_pezzi.NomeProduttore AS MarcaPezzo
        
        FROM ordini o
        JOIN vendite v ON o.IdOrdine = v.IdOrdine
        JOIN annunci a ON v.idAnnuncio = a.idAnnuncio

        -- Collegamento con la controparte "Automobile" e relative tabelle di anagrafica
        LEFT JOIN automobili auto ON a.IdAuto = auto.IdAuto
        LEFT JOIN modelli m ON auto.IdModello = m.IdModello
        LEFT JOIN produttori pr_auto ON m.IdProduttore = pr_auto.IdProduttore

        -- Collegamento con la controparte "Pezzi di Ricambio" e relative tabelle di anagrafica
        LEFT JOIN pezzi_di_ricambio p ON a.IdPezzo = p.IdPezzo
        LEFT JOIN produttori pr_pezzi ON p.IdProduttore = pr_pezzi.IdProduttore

        WHERE o.IdUtente = ?
        ORDER BY o.Data DESC, o.IdOrdine DESC;
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
        SET a.Stato = 'Venduto'
        WHERE a.IdPezzo = p.IdPezzo AND a.idAnnuncio = ? AND p.QuantitàMagazzino = 0
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
        LIMIT 1
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
        LIMIT 1
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
}
