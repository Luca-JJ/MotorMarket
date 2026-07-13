package motormarket.view;

import javax.swing.*;

import motormarket.controller.Controller;

import java.awt.*;
import java.awt.event.ActionListener;

public class ClientDashboardPanel extends JPanel {
    
    private MotorMarketFrame frame;
    private Controller controller;
    
    // Componenti della Sidebar (Operazioni)
    private JButton btnMieiAcquisti;             // Op. 4
    private JButton btnAutoPiuVenduta;           // Op. 5
    private JButton btnRicambioPiuVenduto;       // Op. 6
    private JButton btnLasciaRecensione;         // Op. 7
    private JButton btnVisualizzaMediaVoti;      // Op. 8
    private JButton logoutBtn;
    
    // Componenti dell'Area Centrale (Catalogo e Azioni dirette)
    private JTable carCatalogTable;               // Op. 2 (Visualizzazione Annunci)
    private JButton btnAcquista;                 // Op. 3 (Agisce sull'annuncio selezionato)

    public ClientDashboardPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. INTESTAZIONE (Top Bar)
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("MotorMarket — Area Privato", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // 2. SIDEBAR DI SINISTRA (Pannello delle Operazioni)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createTitledBorder("Menu Funzionalità"));
        sidebar.setPreferredSize(new Dimension(240, 0));

        // Inizializzazione pulsanti con icone testuali
        btnMieiAcquisti = new JButton("I Miei Acquisti 📦");
        btnAutoPiuVenduta = new JButton("Auto Più Venduta 🏆");
        btnRicambioPiuVenduto = new JButton("Ricambio Più Venduto 🔧");
        btnLasciaRecensione = new JButton("Lascia Recensione ⭐");
        btnVisualizzaMediaVoti = new JButton("Media Voti Prodotto 📊");
        logoutBtn = new JButton("Esci / Logout 🚪");

        // Uniformiamo la larghezza dei pulsanti nella sidebar
        Dimension btnSize = new Dimension(210, 35);
        JButton[] sidebarButtons = {btnMieiAcquisti, btnAutoPiuVenduta, btnRicambioPiuVenduto, btnLasciaRecensione, btnVisualizzaMediaVoti, logoutBtn};
        for (JButton btn : sidebarButtons) {
            btn.setMaximumSize(btnSize);
            btn.setPreferredSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        // Aggiungiamo i pulsanti distanziandoli
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(btnMieiAcquisti);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnAutoPiuVenduta);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnRicambioPiuVenduto);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnLasciaRecensione);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnVisualizzaMediaVoti);
        sidebar.add(Box.createVerticalGlue()); // Spinge il logout in fondo
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(10));

        add(sidebar, BorderLayout.WEST);

        // 3. AREA CENTRALE (Catalogo Annunci + Tasto Acquisto)
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Annunci Disponibili (Max 10.000/giorno)"));

        // Tabella segnaposto degli annunci (Op. 2)
        String[] columns = {"ID", "Marca", "Modello", "Prezzo (€)", "Tipo Venditore"};
        Object[][] data = {
            {"1", "Fiat", "500 Hybrid", "14.500", "Aziendale"},
            {"2", "BMW", "Series 3", "32.000", "Privato"},
            {"3", "Pasticche Freni Brembo", "Ricambio", "85", "Aziendale"}
        };
        carCatalogTable = new JTable(data, columns);
        JScrollPane tableScroll = new JScrollPane(carCatalogTable);
        centerPanel.add(tableScroll, BorderLayout.CENTER);

        // Pannello inferiore all'area centrale per l'azione di acquisto diretto (Op. 3)
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAcquista = new JButton("Procedi all'Acquisto dell'Elemento Selezionato 🛒");
        btnAcquista.setFont(new Font("Arial", Font.BOLD, 12));
        actionPanel.add(btnAcquista);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    // --- METODI UTILITY PER IL CONTROLLER ---
    
    /**
     * Ritorna l'ID dell'annuncio selezionato nella tabella.
     * Utile al controller per sapere cosa l'utente vuole acquistare o recensire.
     */
    public int getSelectedAnnuncioId() {
        int selectedRow = carCatalogTable.getSelectedRow();
        if (selectedRow != -1) {
            return Integer.parseInt(carCatalogTable.getValueAt(selectedRow, 0).toString());
        }
        return -1; // Nessuna riga selezionata
    }

    // --- ASSEGNAZIONE LISTENER (Interfacce per il ClientDashboardController) ---
    
    public void addAcquistaListener(ActionListener l) { btnAcquista.addActionListener(l); }
    public void addMieiAcquistiListener(ActionListener l) { btnMieiAcquisti.addActionListener(l); }
    public void addAutoPiuVendutaListener(ActionListener l) { btnAutoPiuVenduta.addActionListener(l); }
    public void addRicambioPiuVendutoListener(ActionListener l) { btnRicambioPiuVenduto.addActionListener(l); }
    public void addLasciaRecensioneListener(ActionListener l) { btnLasciaRecensione.addActionListener(l); }
    public void addMediaVotiListener(ActionListener l) { btnVisualizzaMediaVoti.addActionListener(l); }
    public void addLogoutListener(ActionListener l) { logoutBtn.addActionListener(l); }
}
