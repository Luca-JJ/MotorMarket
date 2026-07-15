package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;

public class CorporateDashboardPanel extends JPanel {
    
    private MotorMarketFrame frame;
    private Controller controller;
    
    // --- CORPORATE OPERATION BUTTONS ---
    private JButton btnAddCar;                  // Op. 9
    private JButton btnAddSparePart;            // Op. 10
    private JButton btnUpdateSpareQuantity;     // Op. 11
    private JButton btnAddAccessory;            // Op. 12
    private JButton btnAddExtraService;         // Op. 13
    private JButton btnManageListings;          // Op. 14
    private JButton btnViewMonthlyRevenue;      // Op. 15
    private JButton logoutBtn;

    private JComboBox<Integer> cbMonth;
    private JComboBox<Integer> cbYear;
    private JTextField txtRevenueResult;

    public CorporateDashboardPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. TOP HEADER
        JLabel welcomeLabel = new JLabel("MotorMarket — Corporate Area", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.NORTH);

        // 2. FEATURE MENU
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Corporate Console"));

        // Initialize operation buttons
        btnAddCar = new JButton("Add Single Car 🚗");
        btnAddSparePart = new JButton("Add Spare Part (with Compatibility) 🔧");
        btnUpdateSpareQuantity = new JButton("Update Spare Part Stock 📦");
        btnAddAccessory = new JButton("Add New Accessory 💎");
        btnAddExtraService = new JButton("Add New Extra Service 🛠️");
        btnManageListings = new JButton("Manage Listings (On/Off) 📢");
        btnViewMonthlyRevenue = new JButton("View Monthly Revenue Inflow 📊");
        logoutBtn = new JButton("Exit / Logout 🚪");

        txtRevenueResult = new JTextField();
        txtRevenueResult.setEditable(false);
        txtRevenueResult.setFont(new Font("Arial", Font.ITALIC, 11));
        txtRevenueResult.setHorizontalAlignment(JTextField.CENTER);
        txtRevenueResult.setVisible(false);

        Integer[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        cbMonth = new JComboBox<>(months);
        
        Integer[] years = {2020, 2021, 2022, 2023, 2024, 2025, 2026};
        cbYear = new JComboBox<>(years);

        Dimension btnSize = new Dimension(280, 32);
        Dimension txtSize = new Dimension(280, 24);

        JButton[] buttons = {
            btnAddCar, btnAddSparePart, btnUpdateSpareQuantity, btnAddAccessory, 
            btnAddExtraService, btnManageListings, btnViewMonthlyRevenue, logoutBtn
        };
        for (JButton btn : buttons) {
            btn.setMaximumSize(btnSize);
            btn.setPreferredSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        txtRevenueResult.setMaximumSize(txtSize);
        txtRevenueResult.setPreferredSize(txtSize);
        txtRevenueResult.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add spaced components to the Sidebar
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(btnAddCar);
        menuPanel.add(Box.createVerticalStrut(8));
        menuPanel.add(btnAddSparePart);
        menuPanel.add(Box.createVerticalStrut(8));
        menuPanel.add(btnUpdateSpareQuantity);
        menuPanel.add(Box.createVerticalStrut(8));
        menuPanel.add(btnAddAccessory);
        menuPanel.add(Box.createVerticalStrut(8));
        menuPanel.add(btnAddExtraService);
        menuPanel.add(Box.createVerticalStrut(8));
        menuPanel.add(btnManageListings);
        menuPanel.add(Box.createVerticalStrut(8));
        
        menuPanel.add(btnViewMonthlyRevenue);
        menuPanel.add(Box.createVerticalStrut(4));
        menuPanel.add(cbYear);
        menuPanel.add(Box.createVerticalStrut(4));
        menuPanel.add(cbMonth);
        menuPanel.add(Box.createVerticalStrut(4));
        menuPanel.add(txtRevenueResult);
        menuPanel.add(Box.createVerticalStrut(8));
        
        menuPanel.add(logoutBtn);
        menuPanel.add(Box.createVerticalStrut(10));

        add(menuPanel, BorderLayout.CENTER);

        // Navigation Action Listeners
        btnAddCar.addActionListener(e -> frame.switchPanel("ADD_CAR"));
        btnAddSparePart.addActionListener(e -> frame.switchPanel("ADD_SPARE_PART"));
        btnUpdateSpareQuantity.addActionListener(e -> frame.switchPanel("UPDATE_SPARE_STOCK"));
        btnAddAccessory.addActionListener(e -> frame.switchPanel("ADD_ACCESSORY"));
        btnAddExtraService.addActionListener(e -> frame.switchPanel("ADD_EXTRA_SERVICE"));
        btnManageListings.addActionListener(e -> frame.switchPanel("MANAGE_LISTINGS"));
        
        logoutBtn.addActionListener(e -> {
            controller.logout();
            frame.switchPanel("LOGIN");
        });

        // Revenue query inline visualization
        btnViewMonthlyRevenue.addActionListener(e -> {
            try {
                // Sostituisci getMonthlyRevenueInfo con il metodo reale del tuo controller per l'Op. 15
                int month = (int) cbMonth.getSelectedItem();
                int year = (int) cbYear.getSelectedItem();
                double result = controller.getMonthlyRevenue(month, year);
                txtRevenueResult.setText(String.format("Total Revenue: €%.2f", result));
                txtRevenueResult.setVisible(true);
                menuPanel.revalidate();
                menuPanel.repaint();
            } catch (Exception ex) {
                txtRevenueResult.setText("Error: " + ex.getMessage());
                txtRevenueResult.setVisible(true);
                menuPanel.revalidate();
                menuPanel.repaint();
            }
        });
    }
}
