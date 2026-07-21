package motormarket.view;

import javax.swing.*;

import motormarket.controller.Controller;
import motormarket.data.DAOException;
import motormarket.data.User;

import java.awt.*;

public class RegisterPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    public RegisterPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        Dimension fieldSize = new Dimension(250, 30);

        JLabel titleLabel = new JLabel("Crea Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        add(Box.createVerticalStrut(30));

        JLabel firstNameLabel = new JLabel("Nome:");
        firstNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(firstNameLabel);
        
        JTextField firstNameField = new JTextField();
        firstNameField.setMaximumSize(fieldSize);
        firstNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(firstNameField);

        add(Box.createVerticalStrut(30));

        JLabel lastNameLabel = new JLabel("Cognome:");
        lastNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lastNameLabel);
        
        JTextField lastNameField = new JTextField();
        lastNameField.setMaximumSize(fieldSize);
        lastNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lastNameField);

        add(Box.createVerticalStrut(15));

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

        add(Box.createVerticalStrut(30));

        JButton regBtn = new JButton("Registra");
        regBtn.setMaximumSize(fieldSize);
        regBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        regBtn.setBackground(new Color(46, 139, 87)); 
        regBtn.setForeground(Color.WHITE);
        add(regBtn);

        add(Box.createVerticalStrut(10));

        JButton backLink = new JButton("Hai già un account? Login");
        backLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        backLink.setBorderPainted(false);
        backLink.setContentAreaFilled(false);
        backLink.setForeground(Color.BLUE);
        backLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(backLink);

        regBtn.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            char[] passwordArray = passField.getPassword();
            String password = new String(passwordArray);
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Riempi tutti i campi!", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            try {
                User user = controller.registrate(firstName, lastName, email, password);
                if(user != null) {
                    String messaggio = "🎉 Utente registrato con successo!\n" +
                        "----------------------------------------\n" +
                        "• ID Utente:      " + user.getUserId() + "\n" +
                        "• Nome:           " + user.getFirstName() + "\n" +
                        "• Cognome:        " + user.getLastName() + "\n" +
                        "• Email:          " + user.getEmail() + "\n" +
                        "• Tipo utente:   " + user.getUserType().getDbValue() + "\n" +
                        "----------------------------------------";

                    // Mostriamo il popup informativo
                    JOptionPane.showMessageDialog(
                            this, 
                            messaggio, 
                            "Utente - MotorMarket", 
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    frame.switchPanel("LOGIN");
                }
            } catch (DAOException ex) {
                // Gestione di altri errori generici del database
                JOptionPane.showMessageDialog(this, "Errore durante la registrazione, forse email già usata.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        backLink.addActionListener(e -> frame.switchPanel("LOGIN"));
    }
}
