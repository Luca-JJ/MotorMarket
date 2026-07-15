package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCarPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    // Form Components
    private JTextField txtVin;
    private JTextField txtLicensePlate;
    private JTextField txtYear;
    private JTextField txtPower;
    private JComboBox<String> cbTransmission;
    private JComboBox<String> cbModel;
    private JComboBox<String> cbFuel;
    private JComboBox<String> cbIntColor;
    private JComboBox<String> cbExtColor;
    
    // Multi-selection list for Accessories
    private JList<String> listAccessories;
    private DefaultListModel<String> accessoriesListModel;

    // Database ID Mappings
    private Map<String, Integer> modelsMap;
    private Map<String, Integer> fuelsMap;
    private Map<String, Integer> colorsMap;
    private Map<String, Integer> accessoriesMap;

    private JButton btnSave;
    private JButton btnCancel;

    public AddCarPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel lblTitle = new JLabel("Register New Car", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        // Form Layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Vehicle Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Retrieve Maps from Controller
        modelsMap = controller.brandsModels(); // Map of "Brand Model" -> ID
        colorsMap = controller.getColors();     // Map of "Color Name" -> ID
        fuelsMap = controller.getFuels();       // Map of "Fuel Name" -> ID
        
        // Fetch accessories from database (assuming Map<String, Integer> from controller)
        accessoriesMap = controller.getAccessories(); 

        // Initialize Form Fields
        txtVin = new JTextField(17);
        txtLicensePlate = new JTextField(10);
        txtYear = new JTextField(5);
        txtPower = new JTextField(5);

        String[] transmissions = {"Manuale", "Automatico"};
        cbTransmission = new JComboBox<>(transmissions);

        cbModel = new JComboBox<>();
        for (String modelName : modelsMap.keySet()) {
            cbModel.addItem(modelName);
        }

        cbFuel = new JComboBox<>();
        for (String fuelName : fuelsMap.keySet()) {
            cbFuel.addItem(fuelName);
        }

        cbIntColor = new JComboBox<>();
        cbExtColor = new JComboBox<>();
        for (String colorName : colorsMap.keySet()) {
            cbIntColor.addItem(colorName);
            cbExtColor.addItem(colorName);
        }

        // Multiple Selection List Setup
        accessoriesListModel = new DefaultListModel<>();
        for (String accName : accessoriesMap.keySet()) {
            accessoriesListModel.addElement(accName);
        }
        listAccessories = new JList<>(accessoriesListModel);
        listAccessories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane accessoriesScroll = new JScrollPane(listAccessories);
        accessoriesScroll.setPreferredSize(new Dimension(150, 80));

        // Add components to the grid layout
        int row = 0;

        addFormField(formPanel, "VIN Code:", txtVin, gbc, row++);
        addFormField(formPanel, "License Plate:", txtLicensePlate, gbc, row++);
        addFormField(formPanel, "Registration Year:", txtYear, gbc, row++);
        addFormField(formPanel, "Horsepower (HP):", txtPower, gbc, row++);
        addFormField(formPanel, "Transmission Type:", cbTransmission, gbc, row++);
        addFormField(formPanel, "Car Model:", cbModel, gbc, row++);
        addFormField(formPanel, "Fuel Type:", cbFuel, gbc, row++);
        addFormField(formPanel, "Interior Color:", cbIntColor, gbc, row++);
        addFormField(formPanel, "Exterior Color:", cbExtColor, gbc, row++);
        addFormField(formPanel, "Accessories (Hold Ctrl to multi-select):", accessoriesScroll, gbc, row++);

        add(formPanel, BorderLayout.CENTER);

        // Bottom Actions Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Cancel");
        btnSave = new JButton("Save Car 💾");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_CORPORATE"));
        btnSave.addActionListener(e -> saveCarAction());
    }

    private void addFormField(JPanel panel, String label, Component component, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(component, gbc);
    }

    private void saveCarAction() {
        String vin = txtVin.getText().trim();
        String licensePlate = txtLicensePlate.getText().trim();
        String yearText = txtYear.getText().trim();
        String powerText = txtPower.getText().trim();

        // Validation checks
        if (vin.isEmpty() || licensePlate.isEmpty() || yearText.isEmpty() || powerText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All basic text fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int registrationYear;
        int power;
        try {
            registrationYear = Integer.parseInt(yearText);
            power = Integer.parseInt(powerText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Year and Power must be valid integer numbers!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Fetch Selection Keys and map to Database IDs
        String transmissionType = cbTransmission.getSelectedItem().toString();
        int modelId = modelsMap.get(cbModel.getSelectedItem().toString());
        int fuelTypeId = fuelsMap.get(cbFuel.getSelectedItem().toString());
        int interiorColorId = colorsMap.get(cbIntColor.getSelectedItem().toString());
        int exteriorColorId = colorsMap.get(cbExtColor.getSelectedItem().toString());
        

        // Map selected Accessories Strings to their DB integer IDs
        List<Integer> selectedAccessoryIds = new ArrayList<>();
        List<String> selectedNames = listAccessories.getSelectedValuesList();
        for (String name : selectedNames) {
            selectedAccessoryIds.add(accessoriesMap.get(name));
        }

        // Database insertion call
        try {
            boolean success = controller.addCar(
                vin, 
                licensePlate, 
                registrationYear, 
                power, 
                transmissionType, 
                modelId, 
                fuelTypeId, 
                interiorColorId, 
                exteriorColorId,  
                selectedAccessoryIds
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Vehicle registered successfully inside the database!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.switchPanel("DASHBOARD_CORPORATE");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to insert the vehicle. Check connection or data restrictions.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
        }
    }
}
