package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;
import java.util.Map;

public class DeleteCorporateUserPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    private JComboBox<String> cbCorporateUsers;
    private Map<String, Integer> corporateUsersMap;

    private JButton btnDelete;
    private JButton btnCancel;

    public DeleteCorporateUserPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Remove Corporate Account", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Account Selection"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        corporateUsersMap = controller.getCorporateUsers();

        cbCorporateUsers = new JComboBox<>();
        for (String info : corporateUsersMap.keySet()) {
            cbCorporateUsers.addItem(info);
        }

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Select Corporate User:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(cbCorporateUsers, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Cancel");
        btnDelete = new JButton("Delete User 👤❌");
        btnDelete.setBackground(new Color(178, 34, 34));
        btnDelete.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnDelete);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_ADMIN"));

        btnDelete.addActionListener(e -> {
            if (cbCorporateUsers.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No corporate user selected!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedItem = cbCorporateUsers.getSelectedItem().toString();
            int userId = corporateUsersMap.get(selectedItem);

            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Are you sure you want to permanently delete:\n" + selectedItem + "?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean success = controller.deleteCorporateUser(userId);

                    if (success) {
                        JOptionPane.showMessageDialog(this, "Corporate account removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.switchPanel("DASHBOARD_ADMIN");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to remove the user. Verify database constraints.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void refreshData() {
        corporateUsersMap = controller.getCorporateUsers();
        cbCorporateUsers.removeAllItems();
        if (corporateUsersMap != null) {
            for (String info : corporateUsersMap.keySet()) {
                cbCorporateUsers.addItem(info);
            }
        }
        cbCorporateUsers.revalidate();
        cbCorporateUsers.repaint();
    }
}
