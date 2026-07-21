package motormarket.view;

import javax.swing.*;

import motormarket.controller.Controller;
import motormarket.data.Producer;
import motormarket.data.SparePart;

import java.awt.*;
import java.awt.event.ActionListener;

public class ClientDashboardPanel extends JPanel {
    
    private MotorMarketFrame frame;
    private Controller controller;
    
    // --- SIDEBAR OPERATION MENU BUTTONS ---
    private JButton btnCarListings;              // Op. 2 (Car Filter)
    private JButton btnSparePartsListings;       // Op. 2 (Spare Parts Filter)
    private JButton btnMyPurchases;              // Op. 4
    private JButton btnTopSellingCar;            // Op. 5
    private JButton btnTopSellingPart;           // Op. 6
    private JButton btnLeaveReview;              // Op. 7
    private JButton btnAverageRating;            // Op. 8
    private JButton logoutBtn;

    private JTextField txtTopCarResult;
    private JTextField txtTopPartResult;

    public ClientDashboardPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. TOP HEADER
        JLabel welcomeLabel = new JLabel("MotorMarket — Area Utente", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.NORTH);

        // 2. LEFT SIDEBAR (Features Panel)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createTitledBorder("Menu funzioni"));
        sidebar.setPreferredSize(new Dimension(240, 0));

        // Initialize operation buttons
        btnCarListings = new JButton("Visualizza annunci auto");
        btnSparePartsListings = new JButton("Visualizza annunci pezzi");
        btnMyPurchases = new JButton("I miei acquisti");
        btnTopSellingCar = new JButton("Marca di auto più venduta");
        btnTopSellingPart = new JButton("Pezzo di ricambio più venduto");
        btnLeaveReview = new JButton("Lascia una recensione");
        btnAverageRating = new JButton("Visualizza recensioni");
        logoutBtn = new JButton("Esci / Logout");

        txtTopCarResult = new JTextField();
        txtTopCarResult.setEditable(false);
        txtTopCarResult.setFont(new Font("Arial", Font.ITALIC, 11));
        txtTopCarResult.setHorizontalAlignment(JTextField.CENTER);
        txtTopCarResult.setVisible(false);

        txtTopPartResult = new JTextField();
        txtTopPartResult.setEditable(false);
        txtTopPartResult.setFont(new Font("Arial", Font.ITALIC, 11));
        txtTopPartResult.setHorizontalAlignment(JTextField.CENTER);
        txtTopPartResult.setVisible(false);

        // Harmonize button sizes for sidebar aesthetics
        Dimension txtSize = new Dimension(210, 24);
        Dimension btnSize = new Dimension(210, 32);
        JButton[] sidebarButtons = {
            btnCarListings, btnSparePartsListings, btnMyPurchases, 
            btnTopSellingCar, btnTopSellingPart, btnLeaveReview, btnAverageRating, logoutBtn
        };
        for (JButton btn : sidebarButtons) {
            btn.setMaximumSize(btnSize);
            btn.setPreferredSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        txtTopCarResult.setMaximumSize(txtSize);
        txtTopCarResult.setPreferredSize(txtSize);
        txtTopCarResult.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtTopPartResult.setMaximumSize(txtSize);
        txtTopPartResult.setPreferredSize(txtSize);
        txtTopPartResult.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add spaced components to the Sidebar
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnCarListings);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(btnSparePartsListings);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(btnMyPurchases);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(btnTopSellingCar);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(txtTopCarResult);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(btnTopSellingPart);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(txtTopPartResult);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(btnLeaveReview);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(btnAverageRating);
        sidebar.add(Box.createVerticalGlue()); // Pushes logout to the bottom
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(10));

        add(sidebar, BorderLayout.CENTER);

        // Action listener to switch to the orders panel
        btnMyPurchases.addActionListener(e -> {
            frame.switchPanel("ORDERS");
        });

        btnCarListings.addActionListener(e -> {
            frame.switchPanel("CAR_LISTINGS");
        });

        btnSparePartsListings.addActionListener(e -> {
            frame.switchPanel("SPARE_PART_LISTINGS");
        });

        btnLeaveReview.addActionListener(e -> {
            frame.switchPanel("ADD_REVIEW");
        });

        btnAverageRating.addActionListener(e -> {
            frame.switchPanel("REVIEWS_RESULT");
        });

        btnTopSellingCar.addActionListener(e -> {
            try {
                Producer producer = controller.getMostSoldProducer();
                String result = producer.getProducerName() + " " + producer.getCarsSold();
                txtTopCarResult.setText(result);
                txtTopCarResult.setVisible(true);
                sidebar.revalidate();
                sidebar.repaint();
            } catch (Exception ex) {
                txtTopCarResult.setText("Error: " + ex.getMessage());
                txtTopCarResult.setVisible(true);
                sidebar.revalidate();
                sidebar.repaint();
            }
        });

        btnTopSellingPart.addActionListener(e -> {
            try {
                SparePart sparePart = controller.getMostSoldSparePart();
                String result = sparePart.getProducer().getProducerName() + " " + sparePart.getPartName() + " " + sparePart.getPartId();
                txtTopPartResult.setText(result);
                txtTopPartResult.setVisible(true);
                sidebar.revalidate();
                sidebar.repaint();
            } catch (Exception ex) {
                txtTopPartResult.setText("Error: " + ex.getMessage());
                txtTopPartResult.setVisible(true);
                sidebar.revalidate();
                sidebar.repaint();
            }
        });

        logoutBtn.addActionListener(e -> {
            controller.logout();
            frame.switchPanel("LOGIN");
        });

    }
}
