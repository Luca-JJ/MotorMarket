package motormarket.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import motormarket.controller.Controller;
import motormarket.data.Car;
import motormarket.data.CarFilter;
import motormarket.data.Listing;
import motormarket.data.OrderItem;

import java.awt.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CarListingsPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    // Filter Components (Selectors)
    private JComboBox<String> cbBrand;
    private JComboBox<String> cbIntColor;
    private JComboBox<String> cbExtColor;
    private JComboBox<String> cbFuel;
    private JComboBox<String> cbTransmission;
    private JTextField txtMaxPrice;
    private JTextField txtMinPrice;
    private JTextField txtMaxYear;
    private JTextField txtMinYear;
    private JTextField txtMaxPower;
    private JTextField txtMinPower;
    private JComboBox<Integer> cbPage;
    private JButton btnSearch;

    // Results Components
    private JTable listingsTable;
    private DefaultTableModel tableModel;
    private JButton btnBack;

    private Map<String, Integer> brandsModels;
    private Map<String, Integer> colorsMap;
    private Map<String, Integer> fuelsMap;
    List<Listing> carListingsList;

    public CarListingsPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. TOP PANEL: SELECTORS AND FILTERS ---
        JPanel filterPanel = new JPanel(new BorderLayout(10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Search Parameters"));

        JPanel gridComponents = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 10, 4, 10); // Spaziamento tra i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        brandsModels = controller.brandsModels();
        colorsMap = controller.getColors();
        fuelsMap = controller.getFuels();

        gbc.gridy = 0;
        
        gbc.gridx = 0; gridComponents.add(new JLabel("Brand:"), gbc);
        cbBrand = new JComboBox<>();
        cbBrand.addItem("All"); // Opzione vuota
        for (String brand : brandsModels.keySet()) {
            cbBrand.addItem(brand);
        }
        gbc.gridx = 1; gridComponents.add(cbBrand, gbc);

        gbc.gridx = 2; gridComponents.add(new JLabel("Fuel:"), gbc);
        cbFuel = new JComboBox<>();
        cbFuel.addItem("All"); // Opzione vuota
        for (String fuel : fuelsMap.keySet()) {
            cbFuel.addItem(fuel);
        }
        gbc.gridx = 3; gridComponents.add(cbFuel, gbc);

        gbc.gridy = 1;
        
        gbc.gridx = 0; gridComponents.add(new JLabel("Interior Color:"), gbc);
        cbIntColor = new JComboBox<>();
        cbIntColor.addItem("All"); // Opzione vuota
        for (String color : colorsMap.keySet()) {
            cbIntColor.addItem(color);
        }
        gbc.gridx = 1; gridComponents.add(cbIntColor, gbc);

        gbc.gridx = 2; gridComponents.add(new JLabel("Exterior Color:"), gbc);
        cbExtColor = new JComboBox<>();
        cbExtColor.addItem("All"); // Opzione vuota
        for (String color : colorsMap.keySet()) {
            cbExtColor.addItem(color);
        }
        gbc.gridx = 3; gridComponents.add(cbExtColor, gbc);

        gbc.gridy = 2;
        
        gbc.gridx = 0; gridComponents.add(new JLabel("Min Price (€):"), gbc);
        txtMinPrice = new JTextField(8);
        gbc.gridx = 1; gridComponents.add(txtMinPrice, gbc);

        gbc.gridx = 2; gridComponents.add(new JLabel("Max Price (€):"), gbc);
        txtMaxPrice = new JTextField(8);
        gbc.gridx = 3; gridComponents.add(txtMaxPrice, gbc);

        gbc.gridy = 3;
        
        gbc.gridx = 0; gridComponents.add(new JLabel("Min Year:"), gbc);
        txtMinYear = new JTextField(8);
        gbc.gridx = 1; gridComponents.add(txtMinYear, gbc);

        gbc.gridx = 2; gridComponents.add(new JLabel("Max Year:"), gbc);
        txtMaxYear = new JTextField(8);
        gbc.gridx = 3; gridComponents.add(txtMaxYear, gbc);

        gbc.gridy = 4;
        
        gbc.gridx = 0; gridComponents.add(new JLabel("Min Power:"), gbc);
        txtMinPower = new JTextField(8);
        gbc.gridx = 1; gridComponents.add(txtMinPower, gbc);

        gbc.gridx = 2; gridComponents.add(new JLabel("Max Power:"), gbc);
        txtMaxPower = new JTextField(8);
        gbc.gridx = 3; gridComponents.add(txtMaxPower, gbc);

        gbc.gridy = 5;
        
        gbc.gridx = 0; gridComponents.add(new JLabel("Page:"), gbc);
        Integer[] pages = {1,2,3,4,5,6,7,8,9,10};
        cbPage = new JComboBox<>(pages);
        gbc.gridx = 1; gridComponents.add(cbPage, gbc);

        gbc.gridx = 2; gridComponents.add(new JLabel("Trasmission:"), gbc);
        String[] trasmissions = {"All", "Manuale", "Automatico"};
        cbTransmission = new JComboBox<>(trasmissions);
        gbc.gridx = 3; gridComponents.add(cbTransmission, gbc);
        

        filterPanel.add(gridComponents, BorderLayout.CENTER);

        btnSearch = new JButton("Search 🔍");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 13));
        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnContainer.add(btnSearch);
        filterPanel.add(btnContainer, BorderLayout.SOUTH);

        add(filterPanel, BorderLayout.NORTH);

        // --- 2. CENTER PANEL: RESULTS TABLE ---
        String[] columns = {"Select", "ListingId", "CarId", "Brand", "Model", "Year", "InteriorColor", "ExteriorColor", "Power", "Fuel", "Transimission", "Price (€)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Se è la colonna 0, diciamo a Swing che è un Boolean (mostrerà la Checkbox grafica)
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Rende editabile/cliccabile SOLO la colonna 0 (la checkbox)
                return column == 0; 
            }
        };

        listingsTable = new JTable(tableModel);
        listingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listingsTable);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. BOTTOM PANEL: NAVIGATION ---
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel leftNavigation = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnBack = new JButton("◀ Back");
        btnBack.setFont(new Font("Arial", Font.BOLD, 12));
        leftNavigation.add(btnBack);

        // Nuovo pulsante Acquista
        JPanel rightActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBuySelected = new JButton("Buy Selected 🛒");
        btnBuySelected.setFont(new Font("Arial", Font.BOLD, 13));
        btnBuySelected.setBackground(new Color(46, 139, 87)); // Un bel verde foresta/successo
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
                    Listing listing = carListingsList.get(i); 
                    
                    OrderItem item = new OrderItem(
                        listing.getListingId(),
                        "Auto",
                        listing.getCar().getCarId(),
                        1,
                        listing.getPrice(),
                        listing.getCar().getCarModel().getProducer().getProducerName(),
                        listing.getCar().getCarModel().getModelName()
                    );
                    itemsToCheckout.add(item);
                }
            }

            if (itemsToCheckout.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select at least one vehicle.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            frame.showCheckoutScreen(itemsToCheckout);
        });
        
        // This button triggers the search query filtering execution!
        btnSearch.addActionListener(e -> applyFiltersAndLoadData());
    }

    /**
     * Reads the chosen UI parameters and refreshes the table model rows.
     */
    private void applyFiltersAndLoadData() {
        // Clear old rows
        tableModel.setRowCount(0);

        // Extract values from your selectors safely
        Integer modelId = brandsModels.get(cbBrand.getSelectedItem().toString());
        Integer intColorId = colorsMap.get(cbIntColor.getSelectedItem().toString());
        Integer extColorId = colorsMap.get(cbExtColor.getSelectedItem().toString());
        Integer fuelId = fuelsMap.get(cbFuel.getSelectedItem().toString());
        String trasmission = cbTransmission.getSelectedItem().toString();
        if(trasmission.equals("All")){
            trasmission = null;
        }
        Integer page = Integer.parseInt(cbPage.getSelectedItem().toString());
        String maxPriceText = txtMaxPrice.getText().trim();
        Double maxPrice = null; // Default to no limit
        if (!maxPriceText.isEmpty()) {
            try {
                maxPrice = Double.parseDouble(maxPriceText);
            } catch (NumberFormatException ex) {
                maxPrice = null;
            }
        }

        String minPriceText = txtMinPrice.getText().trim(); // Corretto: txtMinPrice
        Double minPrice = null; // Default to no limit
        if (!minPriceText.isEmpty()) {
            try {
                minPrice = Double.parseDouble(minPriceText);
            } catch (NumberFormatException ex) {
                minPrice = null;
            }
        }

        // --- 2. ANNO (YEAR) ---
        String maxYearText = txtMaxYear.getText().trim();
        Integer maxYear = null; // Default to no limit
        if (!maxYearText.isEmpty()) {
            try {
                maxYear = Integer.parseInt(maxYearText);
            } catch (NumberFormatException ex) {
                maxYear = null;
            }
        }

        String minYearText = txtMinYear.getText().trim();
        Integer minYear = null; // Default to no limit
        if (!minYearText.isEmpty()) {
            try {
                minYear = Integer.parseInt(minYearText);
            } catch (NumberFormatException ex) {
                minYear = null;
            }
        }

        // --- 3. POTENZA (POWER) ---
        String maxPowerText = txtMaxPower.getText().trim();
        Integer maxPower = null; // Default to no limit
        if (!maxPowerText.isEmpty()) {
            try {
                maxPower = Integer.parseInt(maxPowerText);
            } catch (NumberFormatException ex) {
                maxPower = null;
            }
        }

        String minPowerText = txtMinPower.getText().trim();
        Integer minPower = null; // Default to no limit
        if (!minPowerText.isEmpty()) {
            try {
                minPower = Integer.parseInt(minPowerText);
            } catch (NumberFormatException ex) {
                minPower = null;
            }
        }

        // --- CONTROLLO LOGICO DI SICUREZZA ---
        // Eseguiamo il controllo solo se entrambi i valori (min e max) sono stati convertiti con successo (non sono null)
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            JOptionPane.showMessageDialog(this, "Minimum price cannot be greater than Maximum price!", "Logic Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (minYear != null && maxYear != null && minYear > maxYear) {
            JOptionPane.showMessageDialog(this, "Minimum year cannot be greater than Maximum year!", "Logic Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (minPower != null && maxPower != null && minPower > maxPower) {
            JOptionPane.showMessageDialog(this, "Minimum power cannot be greater than Maximum power!", "Logic Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CarFilter filter = new CarFilter(modelId, fuelId, trasmission, extColorId, intColorId, minPrice, maxPrice, minYear, maxYear, minPower, maxPower, page);
        
        this.carListingsList = controller.showCarListings(filter);

        // {"Brand", "Model", "Year", "InteriorColor", "ExteriorColor", "Power", "Fuel", "Transimission", "Price (€)"}
        for(Listing carListing : carListingsList) {
            Car car = carListing.getCar();
            tableModel.addRow(new Object[]{Boolean.FALSE, carListing.getListingId(), car.getCarId(), car.getCarModel().getProducer().getProducerName(), car.getCarModel().getModelName(),
                car.getFirstRegistrationYear(), car.getInteriorColor().getColorName(), car.getExteriorColor().getColorName(),
                car.getHorsepower(), car.getFuelType().getFuelName(), car.getTransmissionType().toString(), carListing.getPrice()
            });
        }
    }
}
