package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;
import java.util.Map;

public class ListingStatusPanel extends JPanel {
private MotorMarketFrame frame;
    private Controller controller;

    private JComboBox<String> cbListings;
    private JComboBox<String> cbStatus;
    private Map<String, Integer> listingsMap;

    private JButton btnSave;
    private JButton btnCancel;

    public ListingStatusPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Listing Status", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Listing State Management"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        cbListings = new JComboBox<>();
        refreshData();

        String[] statuses = {"Attivo", "Disattivato"};
        cbStatus = new JComboBox<>(statuses);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Select Listing:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(cbListings, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("New Status:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(cbStatus, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Cancel");
        btnSave = new JButton("Update Status 💾");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_CORPORATE"));

        btnSave.addActionListener(e -> {
            if (cbListings.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No listing selected!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedListing = cbListings.getSelectedItem().toString();
            int advertisementId = listingsMap.get(selectedListing);
            String newStatus = cbStatus.getSelectedItem().toString();

            try {
                boolean success = controller.updateListingStatus(advertisementId, newStatus);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Listing status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.switchPanel("DASHBOARD_CORPORATE");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update listing status.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void refreshData() {
        listingsMap = controller.getListings(); 
        cbListings.removeAllItems();
        if (listingsMap != null) {
            for (String description : listingsMap.keySet()) {
                cbListings.addItem(description);
            }
        }
        cbListings.revalidate();
        cbListings.repaint();
    }
}
