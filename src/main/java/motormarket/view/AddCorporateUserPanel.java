package motormarket.view;

import javax.swing.*;
import motormarket.controller.Controller;
import motormarket.data.User;
import java.awt.*;

public class AddCorporateUserPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    private JTextField txtEmail;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JPasswordField txtPassword;

    private JButton btnSave;
    private JButton btnCancel;

    public AddCorporateUserPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Registra utente aziendale", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Dettagli utente aziendale"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtEmail = new JTextField(20);
        txtFirstName = new JTextField(20);
        txtLastName = new JTextField(20);
        txtPassword = new JPasswordField(20);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Email (Username):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtEmail, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtFirstName, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Cognome:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtLastName, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtPassword, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Indietro");
        btnSave = new JButton("Crea Account ");
        btnSave.setBackground(new Color(46, 139, 87));
        btnSave.setForeground(Color.WHITE);

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> frame.switchPanel("DASHBOARD_ADMIN"));

        btnSave.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tutti i campi sono richiesti!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                User newUser = controller.addCorporateUser(firstName, lastName, email, password);

                if (newUser != null) {
                    JOptionPane.showMessageDialog(this, "Utente aziendale creato con succeso!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    txtEmail.setText("");
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtPassword.setText("");
                    frame.switchPanel("DASHBOARD_ADMIN");
                } else {
                    JOptionPane.showMessageDialog(this, "Fallita la creazione dell'account", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Exception thrown", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
