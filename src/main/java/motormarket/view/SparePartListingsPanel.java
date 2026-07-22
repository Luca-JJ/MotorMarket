package motormarket.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import motormarket.controller.Controller;
import motormarket.data.Listing;
import motormarket.data.OrderItem;
import motormarket.data.SparePart;
import motormarket.data.SparePartFilter;

import java.awt.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class SparePartListingsPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    // Filter Components
    private JComboBox<String> cbProducer;
    private JTextField txtMinPrice;
    private JTextField txtMaxPrice;
    private JComboBox<Integer> cbPage;
    private JButton btnSearch;

    // Results Components
    private JTable listingsTable;
    private DefaultTableModel tableModel;
    private JButton btnBack;

    private Map<String, Integer> producersMap;
    private List<Listing> partListingsList;

    public SparePartListingsPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. TOP PANEL: SELECTORS AND FILTERS ---
        JPanel filterPanel = new JPanel(new BorderLayout(10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Parametri di ricerca"));

        JPanel gridComponents = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 10, 4, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        producersMap = controller.getProducers();

        gbc.gridy = 0;
        
        gbc.gridx = 0; gridComponents.add(new JLabel("Produttore:"), gbc);
        cbProducer = new JComboBox<>();
        cbProducer.addItem("All");
        for (String producer : producersMap.keySet()) {
            cbProducer.addItem(producer);
        }
        gbc.gridx = 1; gridComponents.add(cbProducer, gbc);

        gbc.gridy = 1;
        
        gbc.gridx = 0; gridComponents.add(new JLabel("Prezzo minimo (€):"), gbc);
        txtMinPrice = new JTextField(8);
        gbc.gridx = 1; gridComponents.add(txtMinPrice, gbc);

        gbc.gridx = 2; gridComponents.add(new JLabel("Prezzo massimo (€):"), gbc);
        txtMaxPrice = new JTextField(8);
        gbc.gridx = 3; gridComponents.add(txtMaxPrice, gbc);

        gbc.gridy = 2;
        
        gbc.gridx = 0; gridComponents.add(new JLabel("Pagina:"), gbc);
        Integer[] pages = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        cbPage = new JComboBox<>(pages);
        gbc.gridx = 1; gridComponents.add(cbPage, gbc);

        filterPanel.add(gridComponents, BorderLayout.CENTER);

        btnSearch = new JButton("Ricerca");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 13));
        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnContainer.add(btnSearch);
        filterPanel.add(btnContainer, BorderLayout.SOUTH);

        add(filterPanel, BorderLayout.NORTH);

        // --- 2. CENTER PANEL: RESULTS TABLE ---
        String[] columns = {"Selezione", "IdAnnuncio", "IdPezzo", "Nome", "Produttore", "Tipo", "Quantità", "Prezzo (€)", "Selez"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                if (columnIndex == 8) {
                    return Integer.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Rende editabili sia la checkbox (colonna 0) sia la quantità da comprare (colonna 8)
                return column == 0 || column == 8; 
            }
        };

        listingsTable = new JTable(tableModel);
        listingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listingsTable);
        add(scrollPane, BorderLayout.CENTER);

        listingsTable.getColumnModel().getColumn(3).setPreferredWidth(350);

        // --- 3. BOTTOM PANEL: NAVIGATION ---
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel leftNavigation = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnBack = new JButton("Indietro");
        btnBack.setFont(new Font("Arial", Font.BOLD, 12));
        leftNavigation.add(btnBack);

        JPanel rightActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBuySelected = new JButton("Compra selezionati");
        btnBuySelected.setFont(new Font("Arial", Font.BOLD, 13));
        btnBuySelected.setBackground(new Color(46, 139, 87));
        btnBuySelected.setForeground(Color.WHITE);
        rightActions.add(btnBuySelected);

        bottomPanel.add(leftNavigation, BorderLayout.WEST);
        bottomPanel.add(rightActions, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- EVENT LISTENERS ---
        btnBack.addActionListener(e -> frame.switchPanel("DASHBOARD"));

        btnBuySelected.addActionListener(e -> {
            if (listingsTable.isEditing()) {
                listingsTable.getCellEditor().stopCellEditing();
            }

            List<OrderItem> itemsToCheckout = new ArrayList<>();

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Boolean isChecked = (Boolean) tableModel.getValueAt(i, 0);
                if (isChecked != null && isChecked) {
                    Listing listing = partListingsList.get(i); 
                    SparePart part = listing.getSparePart();
                    
                    // Recupera e valida la quantità inserita dall'utente
                    Integer qtyToBuy = (Integer) tableModel.getValueAt(i, 8);
                    
                    if (qtyToBuy == null || qtyToBuy <= 0) {
                        JOptionPane.showMessageDialog(this, 
                            "Inserisci una giusta quantità: " + part.getPartName(), 
                            "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    if (qtyToBuy > part.getStockQuantity()) {
                        JOptionPane.showMessageDialog(this, 
                            "Quantita richiesta di '" + part.getPartName() + "' supera disponibilità (" + part.getStockQuantity() + ").", 
                            "Out of Stock", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    OrderItem item = new OrderItem(
                        listing.getListingId(),
                        "Pezzo",
                        part.getPartId(),
                        qtyToBuy, 
                        listing.getPrice(),
                        part.getProducer().getProducerName(),
                        part.getPartName()
                    );
                    itemsToCheckout.add(item);
                }
            }

            if (itemsToCheckout.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleziona almeno un pezzo.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            frame.showCheckoutScreen(itemsToCheckout);
        });
        
        btnSearch.addActionListener(e -> applyFiltersAndLoadData());
    }

    private void applyFiltersAndLoadData() {
        // Clear previous visual items to prevent row accumulation in the UI view
        tableModel.setRowCount(0);
        
        // Safety clear of the internal cached list reference
        if (this.partListingsList != null) {
            this.partListingsList.clear();
        }

        // Parse search filters
        Integer producerId = producersMap.get(cbProducer.getSelectedItem().toString());
        
        Integer page = Integer.parseInt(cbPage.getSelectedItem().toString());
        
        String minPriceText = txtMinPrice.getText().trim();
        Double minPrice = null;
        if (!minPriceText.isEmpty()) {
            try {
                minPrice = Double.parseDouble(minPriceText);
            } catch (NumberFormatException ex) {
                minPrice = null;
            }
        }

        String maxPriceText = txtMaxPrice.getText().trim();
        Double maxPrice = null;
        if (!maxPriceText.isEmpty()) {
            try {
                maxPrice = Double.parseDouble(maxPriceText);
            } catch (NumberFormatException ex) {
                maxPrice = null;
            }
        }

        // Logic error boundaries validation
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            JOptionPane.showMessageDialog(this, "Attenzione prezzo!", "Logic Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Call controller to fetch a clean, un-duplicated list from DB
        SparePartFilter filter = new SparePartFilter(producerId, minPrice, maxPrice, page);
        this.partListingsList = controller.showSparePartListings(filter);

        if (this.partListingsList == null || this.partListingsList.isEmpty()) {
            return;
        }

        // Re-populate the view model
        for (Listing partListing : this.partListingsList) {
            SparePart part = partListing.getSparePart();
            tableModel.addRow(new Object[]{
                Boolean.FALSE, 
                partListing.getListingId(), 
                part.getPartId(), 
                part.getPartName(), 
                part.getProducer().getProducerName(), 
                part.getCategory(), 
                part.getStockQuantity(), 
                partListing.getPrice(),
                1 // Default purchase quantity initially set to 1
            });
        }
    }
}
