package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;
import java.util.Map;

public class CreatePartListingPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    private JComboBox<String> cbParts;
    private JTextField txtPrice;
    private Map<String, Integer> partsMap;

    private JButton btnSave;
    private JButton btnCancel;

    public CreatePartListingPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Create Spare Part Listing", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Listing Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        cbParts = new JComboBox<>();
        refreshData();

        txtPrice = new JTextField(10);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Select Spare Part:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(cbParts, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Price (€):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtPrice, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Cancel");
        btnSave = new JButton("Publish Listing 📢");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_CORPORATE"));

        btnSave.addActionListener(e -> {
            if (cbParts.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No spare part selected!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String priceText = txtPrice.getText().trim();
            if (priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Price is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText.replace(",", "."));
                if (price <= 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than zero!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid decimal number for price!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedPart = cbParts.getSelectedItem().toString();
            int partId = partsMap.get(selectedPart);

            try {
                boolean success = controller.addSparePartListing(partId, price);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Spare part listing published successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    txtPrice.setText("");
                    frame.switchPanel("DASHBOARD_CORPORATE");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to publish listing.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void refreshData() {
        partsMap = controller.getSparePartsWithoutListing(); 
        cbParts.removeAllItems();
        if (partsMap != null) {
            for (String partDetails : partsMap.keySet()) {
                cbParts.addItem(partDetails);
            }
        }
        cbParts.revalidate();
        cbParts.repaint();
    }
}
