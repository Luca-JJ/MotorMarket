package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;
import java.util.Map;

public class CreateCarListingPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    private JComboBox<String> cbCars;
    private JTextField txtPrice;
    private Map<String, Integer> carsMap;

    private JButton btnSave;
    private JButton btnCancel;

    public CreateCarListingPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Create Car Listing", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Listing Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        cbCars = new JComboBox<>();
        refreshData();

        txtPrice = new JTextField(10);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Select Car:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(cbCars, gbc);

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
            if (cbCars.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No car selected!", "Validation Error", JOptionPane.WARNING_MESSAGE);
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

            String selectedCar = cbCars.getSelectedItem().toString();
            int carId = carsMap.get(selectedCar);

            try {
                boolean success = controller.addCarListing(carId, price);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Car listing published successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        carsMap = controller.getCarsWithoutListing(); 
        cbCars.removeAllItems();
        if (carsMap != null) {
            for (String carDetails : carsMap.keySet()) {
                cbCars.addItem(carDetails);
            }
        }
        cbCars.revalidate();
        cbCars.repaint();
    }
}
