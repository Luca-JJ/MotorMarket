package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import motormarket.data.OrderItem;
import motormarket.data.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckoutPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;
    private List<OrderItem> itemsToBuy;

    private JTextArea txtSummary;
    private JTextField txtAddress;
    private JPanel servicesPanel;
    
    private List<JCheckBox> dynamicServiceCheckBoxes = new ArrayList<>();

    private JButton btnConfirm;
    private JButton btnCancel;

    public CheckoutPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Checkout - Completa il tuo acquisto", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;

        // Row 0: Summary Panel
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Riassunto Oggetti"));
        txtSummary = new JTextArea();
        txtSummary.setEditable(false);
        txtSummary.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane summaryScroll = new JScrollPane(txtSummary);
        summaryPanel.add(summaryScroll, BorderLayout.CENTER);
        
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4; // 40% of available height
        centerPanel.add(summaryPanel, gbc);

        // Row 1: Address Panel (Fixed height container)
        JPanel addressPanel = new JPanel(new GridBagLayout());
        addressPanel.setBorder(BorderFactory.createTitledBorder("Indirizzo di spedizione"));
        GridBagConstraints gbcAddress = new GridBagConstraints();
        gbcAddress.insets = new Insets(5, 10, 5, 10);
        
        gbcAddress.gridx = 0;
        gbcAddress.gridy = 0;
        gbcAddress.weightx = 0.0;
        gbcAddress.fill = GridBagConstraints.NONE;
        addressPanel.add(new JLabel("Indirizzo completo:"), gbcAddress);
        
        txtAddress = new JTextField();
        gbcAddress.gridx = 1;
        gbcAddress.weightx = 1.0;
        gbcAddress.fill = GridBagConstraints.HORIZONTAL;
        addressPanel.add(txtAddress, gbcAddress);
        
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // Keep fixed height based on content
        centerPanel.add(addressPanel, gbc);

        // Row 2: Services Panel
        servicesPanel = new JPanel();
        servicesPanel.setLayout(new BoxLayout(servicesPanel, BoxLayout.Y_AXIS));
        
        JPanel servicesWrapper = new JPanel(new BorderLayout());
        servicesWrapper.setBorder(BorderFactory.createTitledBorder("Servizi aggiuntivi disponibili"));
        
        JScrollPane servicesScroll = new JScrollPane(servicesPanel);
        servicesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        servicesScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        servicesWrapper.add(servicesScroll, BorderLayout.CENTER);
        
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6; // 60% of available height
        centerPanel.add(servicesWrapper, gbc);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel leftNavigation = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnCancel = new JButton("Indietro");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 12));
        leftNavigation.add(btnCancel);

        JPanel rightActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnConfirm = new JButton("Conferma e paga");
        btnConfirm.setFont(new Font("Arial", Font.BOLD, 13));
        btnConfirm.setBackground(new Color(46, 139, 87));
        btnConfirm.setForeground(Color.WHITE);
        rightActions.add(btnConfirm);

        bottomPanel.add(leftNavigation, BorderLayout.WEST);
        bottomPanel.add(rightActions, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD"));
        btnConfirm.addActionListener(e -> processFinalOrder());
    }
    
    public void prepareCheckout(List<OrderItem> items) {
        this.itemsToBuy = items;
        this.txtAddress.setText("");
        String type = items.get(0).getListingType();
        servicesPanel.removeAll();
        dynamicServiceCheckBoxes.clear();

        try {
            List<Service> availableServices = controller.getServices(type); 

            if (availableServices != null) {
                for (Service service : availableServices) {
                    // Replace with actual getters from your model if necessary (e.g., service.getName())
                    String name = service.getName(); 
                    BigDecimal price = service.getPrice();               
                    
                    String checkboxLabel = String.format("%s (+ €%.2f)", name, price);
                    JCheckBox chk = new JCheckBox(checkboxLabel);
                    
                    chk.putClientProperty("serviceObject", service);
                    
                    dynamicServiceCheckBoxes.add(chk);
                    servicesPanel.add(chk);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error loading dynamic services: " + ex.getMessage());
        }

        servicesPanel.revalidate();
        servicesPanel.repaint();

        StringBuilder sb = new StringBuilder();
        Double total = 0.0;
        
        sb.append(String.format("%-30s %10s\n", "Prodotto", "Prezzo"));
        sb.append("==============================================\n");
        for (OrderItem item : items) {
            sb.append(String.format("%-30s €%10s\n", 
                item.getBrand() + " " + item.getItemName(), 
                (item.getUnitPrice().doubleValue()*item.getQuantity())));
            total = total + item.getUnitPrice().doubleValue()*item.getQuantity();
        }
        sb.append("==============================================\n");
        sb.append(String.format("%-30s €%10s\n", "Subtotale:", total.toString()));
        
        txtSummary.setText(sb.toString());
    }

    private void processFinalOrder() {
        String address = txtAddress.getText().trim();
        
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Inserisci un indirizzo di spedizione valido!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Service> chosenServices = new ArrayList<>();
        for (JCheckBox chk : dynamicServiceCheckBoxes) {
            if (chk.isSelected()) {
                Service service = (Service) chk.getClientProperty("serviceObject");
                chosenServices.add(service);
            }
        }

        try {
            controller.addOrder(address, itemsToBuy, chosenServices);

            JOptionPane.showMessageDialog(this, 
                "Acquisto completato con successo.", 
                "Order Complete", 
                JOptionPane.INFORMATION_MESSAGE);
            
            frame.switchPanel("DASHBOARD");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "è stato riscontrato un errore durante l'acquisto: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
