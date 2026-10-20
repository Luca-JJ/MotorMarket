==============================================================================
                              MOTORMARKET
==============================================================================

DESCRIZIONE
-----------
MotorMarket e' un'applicazione Java basata su un database locale MySQL.


REQUISITI E PREREQUISITI
------------------------
* IDE Consigliato: Visual Studio Code (con estensione Java Extension Pack).
* Database: Server MySQL locale attivo.


GUIDA ALL'INSTALLAZIONE E AVVIO
-------------------------------

1. Configurazione del Database:
   - Importa ed esegui il file 'DumpMotorMarket.sql' nel tuo server MySQL locale.
     Questo creera' le tabelle necessarie e le popolera' con i dati di prova.
   - Verifica sul file 'main.java' ed eventualmente aggiorna le credenziali di connessione al DB:
       * Nome DB:  ecommerce
       * Utente:   root
       * Password: 26Luca (modificabile in base alle proprie esigenze)

2. Avvio dell'Applicazione:
   - Apri la cartella 'MotorMarket' all'interno del tuo IDE.
   - Posizionati sul file 'main.java'.
   - Esegui il file per avviare l'applicazione.


CREDENZIALI DI PROVA
--------------------
All'avvio si aprira' la schermata di login. Tramite registrazione e' possibile
creare autonomamente solo utenti di tipologia PRIVATO.

Per testare le diverse tipologie di utente sono fornite le seguenti credenziali:

1. UTENTE PRIVATO
   - Email:    mario.rossi@email.it
   - Password: pass1

2. UTENTE AZIENDALE
   - Email:    luca.bianchi@motormarket.it
   - Password: pass2

3. UTENTE AMMINISTRATORE
   - Email:    admin@motormarket.it
   - Password: pass3

==============================================================================
