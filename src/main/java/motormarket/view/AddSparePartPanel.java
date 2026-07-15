package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddSparePartPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    private JTextField txtName;
    private JTextField txtStockQuantity;
    private JTextField txtType;
    private JComboBox<String> cbProducer;
    
    private JList<String> listCompatibleModels;
    private DefaultListModel<String> modelsListModel;

    private Map<String, Integer> producersMap;
    private Map<String, Integer> modelsMap;

    private JButton btnSave;
    private JButton btnCancel;

    public AddSparePartPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Register New Spare Part", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Spare Part Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        producersMap = controller.getProducers(); 
        modelsMap = controller.brandsModels();    

        txtName = new JTextField(20);
        txtStockQuantity = new JTextField(5);
        txtType = new JTextField(15);

        cbProducer = new JComboBox<>();
        for (String producerName : producersMap.keySet()) {
            cbProducer.addItem(producerName);
        }

        modelsListModel = new DefaultListModel<>();
        for (String modelName : modelsMap.keySet()) {
            modelsListModel.addElement(modelName);
        }
        listCompatibleModels = new JList<>(modelsListModel);
        listCompatibleModels.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane modelsScroll = new JScrollPane(listCompatibleModels);
        modelsScroll.setPreferredSize(new Dimension(150, 100));

        int row = 0;
        addFormField(formPanel, "Part Name:", txtName, gbc, row++);
        addFormField(formPanel, "Stock Quantity:", txtStockQuantity, gbc, row++);
        addFormField(formPanel, "Part Type / Category:", txtType, gbc, row++);
        addFormField(formPanel, "Producer:", cbProducer, gbc, row++);
        addFormField(formPanel, "Compatible Models (Hold Ctrl to multi-select):", modelsScroll, gbc, row++);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Cancel");
        btnSave = new JButton("Save Spare Part 💾");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_CORPORATE"));
        btnSave.addActionListener(e -> saveSparePartAction());
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

    private void saveSparePartAction() {
        String name = txtName.getText().trim();
        String stockText = txtStockQuantity.getText().trim();
        String type = txtType.getText().trim();

        if (name.isEmpty() || stockText.isEmpty() || type.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All basic text fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int stockQuantity;
        try {
            stockQuantity = Integer.parseInt(stockText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Stock Quantity must be a valid integer number!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int producerId = producersMap.get(cbProducer.getSelectedItem().toString());

        List<Integer> compatibleModelIds = new ArrayList<>();
        List<String> selectedNames = listCompatibleModels.getSelectedValuesList();
        for (String modelName : selectedNames) {
            compatibleModelIds.add(modelsMap.get(modelName));
        }

        try {
            boolean success = controller.addSparePartWithCompatibilities(
                name, 
                stockQuantity, 
                type, 
                producerId, 
                compatibleModelIds
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Spare part registered successfully inside the database!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.switchPanel("DASHBOARD_CORPORATE");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to insert the spare part. Check connection or constraints.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
        }
    }
}