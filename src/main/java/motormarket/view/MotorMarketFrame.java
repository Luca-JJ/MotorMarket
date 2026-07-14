package motormarket.view;

import javax.swing.*;

import motormarket.controller.Controller;
import motormarket.data.OrderItem;

import java.awt.*;
import java.util.List;

public class MotorMarketFrame extends JFrame implements View {
    
    private Controller controller;

    private CardLayout cardLayout;
    private JPanel mainContainer;
    
    private int screenWidth;
    private int screenHeight;

    private OrdersTablePanel ordersTablePanel;
    private CheckoutPanel checkoutPanel;

    public MotorMarketFrame(Controller controller) {
        setTitle("MotorMarket - Compravendita Veicoli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Calcola la risoluzione dello schermo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        ordersTablePanel = new OrdersTablePanel(this, controller);
        checkoutPanel = new CheckoutPanel(this, controller);

        // Inizializza e aggiunge le classi esterne al contenitore
        mainContainer.add(new LoginPanel(this, controller), "LOGIN");
        mainContainer.add(new ClientDashboardPanel(this, controller), "DASHBOARD");
        mainContainer.add(new RegisterPanel(this, controller), "REGISTER");
        mainContainer.add(new CarListingsPanel(this, controller), "CAR_LISTINGS");
        mainContainer.add(checkoutPanel, "CHECKOUT");
        mainContainer.add(ordersTablePanel, "ORDERS");
        // mainContainer.add(new DashboardPanel(this), "DASHBOARD");

        // Mostra il login all'avvio
        switchPanel("LOGIN");

        add(mainContainer);
        this.setVisible(true);
    }

    public void switchPanel(String panelName) {

        if (panelName.equals("ORDERS") && ordersTablePanel != null) {
            ordersTablePanel.refreshOrdersData(); 
        }
        cardLayout.show(mainContainer, panelName);
        adaptSizeFor(panelName);
    }

    private void adaptSizeFor(String panelName) {
        if (panelName.equals("CAR_LISTINGS")) { 
            setSize((int) (screenWidth * 0.75), (int) (screenHeight * 0.75));
        } else {
            int width = Math.max((int) (screenWidth * 0.25), 380);
            int height = Math.max((int) (screenHeight * 0.45), 480);
            setSize(width, height);
        }
        setLocationRelativeTo(null);
    }

    public void showCheckoutScreen(List<OrderItem> items) {
    // Passiamo gli elementi al pannello di checkout per prepararli a video
    checkoutPanel.prepareCheckout(items);
    
    // Cambiamo pannello (usa la stringa che hai assegnato al CheckoutPanel nel CardLayout)
    switchPanel("CHECKOUT"); 
}
}