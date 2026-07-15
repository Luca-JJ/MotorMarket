package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;

public class AddAccessoryPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    private JTextField txtName;
    private JButton btnSave;
    private JButton btnCancel;

    public AddAccessoryPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Add New Accessory", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Accessory Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtName = new JTextField(20);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Accessory Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtName, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Cancel");
        btnSave = new JButton("Save Accessory 💾");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_CORPORATE"));
        btnSave.addActionListener(e -> saveAccessoryAction());
    }

    private void saveAccessoryAction() {
        String name = txtName.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Accessory name is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean success = controller.addAccessory(name);

            if (success) {
                JOptionPane.showMessageDialog(this, "Accessory added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                txtName.setText("");
                frame.switchPanel("DASHBOARD_CORPORATE");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add the accessory. It might already exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
        }
    }
}
