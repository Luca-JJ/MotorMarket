package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;
import java.util.Map;

public class UpdateSpareStockPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    private JComboBox<String> cbSparePart;
    private JTextField txtDeltaQuantity;

    private Map<String, Integer> sparePartsMap;

    private JButton btnSave;
    private JButton btnCancel;

    public UpdateSpareStockPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Update Spare Part Stock", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Stock Adjustment"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        sparePartsMap = controller.getSpareParts();

        cbSparePart = new JComboBox<>();
        for (String partName : sparePartsMap.keySet()) {
            cbSparePart.addItem(partName);
        }

        txtDeltaQuantity = new JTextField(10);
        txtDeltaQuantity.setToolTipText("Enter a positive number to add stock, or negative to reduce it");

        int row = 0;
        addFormField(formPanel, "Select Spare Part:", cbSparePart, gbc, row++);
        addFormField(formPanel, "Quantity Delta (e.g. +10 or -5):", txtDeltaQuantity, gbc, row++);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Cancel");
        btnSave = new JButton("Update Stock 💾");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD"));
        btnSave.addActionListener(e -> updateStockAction());
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

    private void updateStockAction() {
        if (cbSparePart.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid spare part!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String deltaText = txtDeltaQuantity.getText().trim();
        if (deltaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a quantity delta!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int deltaQuantity;
        try {
            deltaQuantity = Integer.parseInt(deltaText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Delta quantity must be a valid integer (e.g., 5 or -3)!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int partId = sparePartsMap.get(cbSparePart.getSelectedItem().toString());

        try {
            Integer newQuantity = controller.addStockQuantity(partId, deltaQuantity);

            if (newQuantity != null) {
                JOptionPane.showMessageDialog(this, 
                    "Stock updated successfully!\nNew total quantity: " + newQuantity, 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                frame.switchPanel("DASHBOARD_CORPORATE");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update stock. Check connection or part details.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
        }
    }
}
