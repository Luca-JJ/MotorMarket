package motormarket.view;

import javax.swing.*;

import motormarket.controller.Controller;
import motormarket.controller.MotorMarketController;
import motormarket.data.User.UserType;

import java.awt.*;

public class LoginPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    public LoginPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        Dimension fieldSize = new Dimension(250, 30);

        JLabel titleLabel = new JLabel("MotorMarket Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        add(Box.createVerticalStrut(30));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(emailLabel);
        
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(fieldSize);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(emailField);

        add(Box.createVerticalStrut(15));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(fieldSize);
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(passField);

        add(Box.createVerticalStrut(25));

        JButton loginBtn = new JButton("Login");
        loginBtn.setMaximumSize(fieldSize);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(loginBtn);
        
        add(Box.createVerticalStrut(10));

        JButton regLink = new JButton("Non hai un account? Registrati");
        regLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        regLink.setBorderPainted(false);
        regLink.setContentAreaFilled(false);
        regLink.setForeground(Color.BLUE);
        regLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(regLink);

        loginBtn.addActionListener(e -> { 
            String email = emailField.getText();
            char[] passwordArray = passField.getPassword();
            String password = new String(passwordArray);
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Riempi tutti i campi!", "Errorr", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            UserType userType = controller.authenticate(email, password);

            if (userType == UserType.PRIVATO) {
                frame.switchPanel("DASHBOARD");
            } else if (userType == UserType.AZIENDALE) {
                frame.switchPanel("DASHBOARD_CORPORATE");
            } else if (userType == UserType.AMMINISTRATORE) {
                frame.switchPanel("DASHBOARD_ADMIN");
            } else {
                JOptionPane.showMessageDialog(this, "Username o Password Errati.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
            }
        
        });
        regLink.addActionListener(e -> frame.switchPanel("REGISTER"));
    }
}