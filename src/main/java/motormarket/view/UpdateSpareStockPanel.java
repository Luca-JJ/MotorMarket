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

        JLabel lblTitle = new JLabel("Aggiorna quantità pezzo di ricambio", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Aggiustamento quantità"));
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
        txtDeltaQuantity.setToolTipText("Inserisci un numero positivo per aumentarla ivece un numero negativo per diminuirla");

        int row = 0;
        addFormField(formPanel, "Seleziona il pezzo di ricambio:", cbSparePart, gbc, row++);
        addFormField(formPanel, "Quantità Delta (esempio +10 or -5):", txtDeltaQuantity, gbc, row++);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Indietro");
        btnSave = new JButton("Aggiorna quantità");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_CORPORATE"));
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
            JOptionPane.showMessageDialog(this, "Seleziona parte di ricambio corretta!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String deltaText = txtDeltaQuantity.getText().trim();
        if (deltaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci il delta sulla quantità!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int deltaQuantity;
        try {
            deltaQuantity = Integer.parseInt(deltaText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantita delta intero (esempio 5 or -3)!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int partId = sparePartsMap.get(cbSparePart.getSelectedItem().toString());

        try {
            Integer newQuantity = controller.addStockQuantity(partId, deltaQuantity);

            if (newQuantity != null) {
                JOptionPane.showMessageDialog(this, 
                    "Quantità aggiornata con successo!\n nuova quantità totale: " + newQuantity, 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                frame.switchPanel("DASHBOARD_CORPORATE");
            } else {
                JOptionPane.showMessageDialog(this, "Fallito l'inserimento del pezzo di ricambio.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
        }
    }
}
