package motormarket.view;

import javax.swing.*;

import motormarket.controller.Controller;

import java.awt.*;
public class MotorMarketFrame extends JFrame implements View {
    
    private Controller controller;

    private CardLayout cardLayout;
    private JPanel mainContainer;
    
    private int screenWidth;
    private int screenHeight;

    public MotorMarketFrame(Controller controller) {
        setTitle("MotorMarket - Compravendita Veicoli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Calcola la risoluzione dello schermo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Inizializza e aggiunge le classi esterne al contenitore
        mainContainer.add(new LoginPanel(this, controller), "LOGIN");
        mainContainer.add(new ClientDashboardPanel(this, controller), "DASHBOARD");
        mainContainer.add(new RegisterPanel(this, controller), "REGISTER");
        // mainContainer.add(new DashboardPanel(this), "DASHBOARD");

        // Mostra il login all'avvio
        switchPanel("LOGIN");

        add(mainContainer);
        this.setVisible(true);
    }

    public void switchPanel(String panelName) {
        cardLayout.show(mainContainer, panelName);
        adaptSizeFor(panelName);
    }

    private void adaptSizeFor(String panelName) {
        if (panelName.equals("DASHBOARD")) {
            // La dashboard prende il 75% dello schermo
            setSize((int) (screenWidth * 0.75), (int) (screenHeight * 0.75));
        } else {
            // Form di login/registrazione prendono circa il 25% in larghezza e 45% in altezza
            int width = Math.max((int) (screenWidth * 0.25), 380);
            int height = Math.max((int) (screenHeight * 0.45), 480);
            setSize(width, height);
        }
        setLocationRelativeTo(null); // Ricentra la finestra
    }

}