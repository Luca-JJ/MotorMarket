package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;

public class AddExtraServicePanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    private JTextField txtName;
    private JComboBox<String> cbServiceType;
    private JTextField txtPrice;

    private JButton btnSave;
    private JButton btnCancel;

    public AddExtraServicePanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Add New Extra Service", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Service Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtName = new JTextField(20);

        // Selettore limitato solo ad "Auto" e "Pezzo"
        String[] serviceTypes = {"Auto", "Pezzo"};
        cbServiceType = new JComboBox<>(serviceTypes);

        txtPrice = new JTextField(10);
        txtPrice.setToolTipText("e.g. 49.99");

        int row = 0;
        addFormField(formPanel, "Service Name:", txtName, gbc, row++);
        addFormField(formPanel, "Service Target Type:", cbServiceType, gbc, row++);
        addFormField(formPanel, "Price (€):", txtPrice, gbc, row++);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Cancel");
        btnSave = new JButton("Save Service 💾");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_CORPORATE"));
        btnSave.addActionListener(e -> saveServiceAction());
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

    private void saveServiceAction() {
        String name = txtName.getText().trim();
        String priceText = txtPrice.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double price;
        try {
            // Sostituisce eventuali virgole con punti per evitare NumberFormatException
            price = Double.parseDouble(priceText.replace(",", "."));
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid decimal number for Price!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Recupera il valore selezionato ("Auto" o "Pezzo")
        String selectedType = cbServiceType.getSelectedItem().toString();
        
        // Se sul database usi stringhe specifiche (es. "CAR" / "SPARE_PART"), 
        // puoi mappare l'input della GUI qui sotto, altrimenti passa direttamente la stringa
        String dbServiceType = selectedType.equals("Auto") ? "Auto" : "Pezzo";

        try {
            boolean success = controller.addExtraService(name, dbServiceType, price);

            if (success) {
                JOptionPane.showMessageDialog(this, "Extra service added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                txtName.setText("");
                txtPrice.setText("");
                frame.switchPanel("DASHBOARD_CORPORATE");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add the extra service.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
        }
    }
}
