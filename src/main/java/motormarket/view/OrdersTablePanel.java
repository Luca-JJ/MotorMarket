package motormarket.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import motormarket.controller.Controller;
import motormarket.data.Order;
import motormarket.data.OrderItem;
import motormarket.data.OrderList;
import motormarket.data.Service;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class OrdersTablePanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    private JTable ordersTable;
    private JButton btnBack;

    public OrdersTablePanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Tabella degli Ordini (Configurata con un modello non modificabile)
        // Puoi personalizzare i nomi delle colonne in base ai dati del tuo DB
        String[] colonne = {"Order ID", "Date", "Item / Description", "Type", "Unit Price (€)"};
        
        DefaultTableModel tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impedisce all'utente di modificare le celle
            }
        };
        
        ordersTable = new JTable(loadOrdersFromDAO());
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        add(scrollPane, BorderLayout.CENTER);

        // 2. Pannello Inferiore con il solo pulsante "Indietro"
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnBack = new JButton("◀ Back");
        btnBack.setFont(new Font("Arial", Font.BOLD, 12));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> frame.switchPanel("DASHBOARD"));
    }

    public DefaultTableModel loadOrdersFromDAO() {
        // 1. Define table columns
        String[] columns = {"Order ID", "Date", "Item / Description", "Type", "Unit Price (€)"};
        
        DefaultTableModel table = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            // 2. Fetch the data using your DAO layer filtered by the logged-in user
            List<OrderList> userOrders = controller.showOrders();

            if (userOrders == null || userOrders.isEmpty()) {
                return table; // Returns the empty model if there are no records
            }

            // 3. Loop through each OrderList block
            for (OrderList ol : userOrders) {
                Order o = ol.getOrder(); // Extract core order details
                
                // --- A) APPEND ORDER ITEMS ---
                for (OrderItem item : ol.getItems()) {
                    table.addRow(new Object[]{
                        o.getOrderId(),
                        o.getDate().toString(),
                        item.getItemName(),        // e.g., "Fiat 500 Hybrid" or "Brembo Brake Pads"
                        item.getListingType(),            // e.g., "Car" or "Spare Part"
                        String.format("%.2f", item.getUnitPrice())
                    });
                }

                // --- B) APPEND ASSOCIATED SERVICES ---
                for (Service s : ol.getServices()) {
                    table.addRow(new Object[]{
                        o.getOrderId(),
                        o.getDate().toString(),
                        "[Service] " + s.getName(), // e.g., "[Service] Professional Assembly"
                        "Service",
                        String.format("%.2f", s.getPrice())
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "An error occurred while loading your order history from the database.", 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }

        return table;
    }

    public void refreshOrdersData() {
        // 1. Generi il modello aggiornato leggendo dal DAO
        DefaultTableModel nuovoModello = loadOrdersFromDAO();
        
        // 2. Dici alla JTable di usare questo NUOVO modello pieno di dati
        this.ordersTable.setModel(nuovoModello);
        
        // 3. (Opzionale ma sicuro) Forzi la tabella a rinfrescarsi graficamente a video
        nuovoModello.fireTableDataChanged();
    }

}
