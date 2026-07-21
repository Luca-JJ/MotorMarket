package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;

public class AdminDashboardPanel extends JPanel {
    
    private MotorMarketFrame frame;
    private Controller controller;
    
    private JButton btnAddCorporate;
    private JButton btnDeleteCorporate;
    private JButton logoutBtn;

    public AdminDashboardPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. TOP HEADER
        JLabel welcomeLabel = new JLabel("MotorMarket — Admin Area", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.NORTH);

        // 2. FEATURE MENU
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Admin Dashboard"));

        // Buttons for Op. 16
        btnAddCorporate = new JButton("Registra utente aziendale");
        btnDeleteCorporate = new JButton("Rimuovi utente aziendale ");
        logoutBtn = new JButton("Esci / Logout");

        Dimension btnSize = new Dimension(280, 32);

        JButton[] buttons = {btnAddCorporate, btnDeleteCorporate, logoutBtn};
        for (JButton btn : buttons) {
            btn.setMaximumSize(btnSize);
            btn.setPreferredSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(btnAddCorporate);
        menuPanel.add(Box.createVerticalStrut(12));
        menuPanel.add(btnDeleteCorporate);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(logoutBtn);
        menuPanel.add(Box.createVerticalStrut(20));

        add(menuPanel, BorderLayout.CENTER);

        // Navigation Action Listeners
        btnAddCorporate.addActionListener(e -> frame.switchPanel("ADD_CORPORATE"));
        btnDeleteCorporate.addActionListener(e -> frame.switchPanel("DELETE_CORPORATE"));
        
        logoutBtn.addActionListener(e -> {
            controller.logout();
            frame.switchPanel("LOGIN");
        });
    }
}
