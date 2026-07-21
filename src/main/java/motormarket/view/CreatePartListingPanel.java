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

        JLabel lblTitle = new JLabel("Creazione nuovo annuncio pezzo di ricambio", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Dettaglio annuncio"));
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
        formPanel.add(new JLabel("Seleziona parte di ricambio:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(cbParts, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Prezzo (€):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtPrice, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Indietro");
        btnSave = new JButton("Pubblica annuncio");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_CORPORATE"));

        btnSave.addActionListener(e -> {
            if (cbParts.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Nessuna parte selezionata!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String priceText = txtPrice.getText().trim();
            if (priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Prezzo richiesto!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText.replace(",", "."));
                if (price <= 0) {
                    JOptionPane.showMessageDialog(this, "Prezzo maggiore di zero!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Inserire valore valido per il prezzo!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedPart = cbParts.getSelectedItem().toString();
            int partId = partsMap.get(selectedPart);

            try {
                boolean success = controller.addSparePartListing(partId, price);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Annuncio pubblicato con successo!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    txtPrice.setText("");
                    frame.switchPanel("DASHBOARD_CORPORATE");
                } else {
                    JOptionPane.showMessageDialog(this, "Fallimento pubblicazione annuncio.", "Database Error", JOptionPane.ERROR_MESSAGE);
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
