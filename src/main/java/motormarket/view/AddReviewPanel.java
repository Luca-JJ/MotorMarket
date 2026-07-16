package motormarket.view;
import javax.swing.*;
import motormarket.controller.Controller;
import java.awt.*;
import java.util.Map;

public class AddReviewPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;

    // UI Form Components
    private JRadioButton rbtnCarModel;
    private JRadioButton rbtnSparePart;
    private JComboBox<String> cbCarModels;
    private JComboBox<String> cbSpareParts;
    private JTextField txtTitle;
    private JTextArea txtComment;
    private JComboBox<Integer> cbRating;
    private JButton btnSubmit;
    private JButton btnCancel;

    // Maps loaded from controller containing ONLY items bought by the current active user
    private Map<String, Integer> boughtCarModelsMap;
    private Map<String, Integer> boughtSparePartsMap;

    public AddReviewPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;

        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 1. HEADER TITLE ---
        JLabel lblHeader = new JLabel("Write a Review", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblHeader, BorderLayout.NORTH);

        // --- 2. FORM PANEL (GridBagLayout for neat alignments) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Select Product Type
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Product Type:"), gbc);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        rbtnCarModel = new JRadioButton("Car Model", true);
        rbtnSparePart = new JRadioButton("Spare Part");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(rbtnCarModel);
        typeGroup.add(rbtnSparePart);
        radioPanel.add(rbtnCarModel);
        radioPanel.add(rbtnSparePart);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        formPanel.add(radioPanel, gbc);

        // Row 1: Select Purchased Item
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Your Purchases:"), gbc);

        JPanel comboPanel = new JPanel(new CardLayout());
        cbCarModels = new JComboBox<>();
        cbSpareParts = new JComboBox<>();
        cbSpareParts.setVisible(false); // Hidden initially

        comboPanel.add(cbCarModels);
        comboPanel.add(cbSpareParts);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        formPanel.add(comboPanel, gbc);

        // Row 2: Review Title
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Review Title:"), gbc);

        txtTitle = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        formPanel.add(txtTitle, gbc);

        // Row 3: Rating (Score from 1 to 5)
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        formPanel.add(new JLabel("Rating Score:"), gbc);

        cbRating = new JComboBox<>(new Integer[]{5, 4, 3, 2, 1});
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        ratingPanel.add(cbRating);
        ratingPanel.add(new JLabel("  ★ (5 is Excellent, 1 is Poor)"));
        
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        formPanel.add(ratingPanel, gbc);

        // Row 4: Review Body/Comment
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Your Feedback:"), gbc);

        txtComment = new JTextArea(6, 20);
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        JScrollPane scrollComment = new JScrollPane(txtComment);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollComment, gbc);

        add(formPanel, BorderLayout.CENTER);

        // --- 3. BOTTOM PANEL: ACTIONS ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnCancel = new JButton("Cancel");
        btnSubmit = new JButton("Submit Review ✉");
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 12));
        
        bottomPanel.add(btnCancel);
        bottomPanel.add(btnSubmit);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. EVENT LISTENERS ---
        
        // Handle dropdown views toggling when switching product types
        rbtnCarModel.addActionListener(e -> {
            cbCarModels.setVisible(true);
            cbSpareParts.setVisible(false);
            comboPanel.revalidate();
            comboPanel.repaint();
        });

        rbtnSparePart.addActionListener(e -> {
            cbCarModels.setVisible(false);
            cbSpareParts.setVisible(true);
            comboPanel.revalidate();
            comboPanel.repaint();
        });

        btnCancel.addActionListener(e -> {
            clearForm();
            frame.switchPanel("DASHBOARD");
        });

        btnSubmit.addActionListener(e -> submitForm());
    }

    /**
     * Re-fetches the user's purchased items from the controller 
     * every time the user enters this view.
     */
    public void refreshData() {
        clearForm();
        
        // Retrieve ONLY items that the active user has already paid for/purchased
        this.boughtCarModelsMap = controller.getBoughtModels();
        this.boughtSparePartsMap = controller.getBoughtSpareParts();

        // Populate Car Models dropdown
        cbCarModels.removeAllItems();
        if (boughtCarModelsMap != null && !boughtCarModelsMap.isEmpty()) {
            for (String car : boughtCarModelsMap.keySet()) {
                cbCarModels.addItem(car);
            }
        } else {
            cbCarModels.addItem("No car models purchased yet");
        }

        // Populate Spare Parts dropdown
        cbSpareParts.removeAllItems();
        if (boughtSparePartsMap != null && !boughtSparePartsMap.isEmpty()) {
            for (String part : boughtSparePartsMap.keySet()) {
                cbSpareParts.addItem(part);
            }
        } else {
            cbSpareParts.addItem("No spare parts purchased yet");
        }
    }

    /**
     * Validates and submits the new review structure back into the controller stack.
     */
    private void submitForm() {
        String title = txtTitle.getText().trim();
        String comment = txtComment.getText().trim();
        int rating = (int) cbRating.getSelectedItem();

        // 1. Validation checks
        if (title.isEmpty() || comment.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out both the Title and Feedback sections.", "Missing Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean successfullyAdded = false;

        // 2. Dispatch data based on selection context
        if (rbtnCarModel.isSelected()) {
            String selectedItem = (String) cbCarModels.getSelectedItem();
            if (selectedItem == null || selectedItem.equals("No car models purchased yet")) {
                JOptionPane.showMessageDialog(this, "You haven't bought any car model to review.", "Action Forbidden", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int modelId = boughtCarModelsMap.get(selectedItem);
            
            // Invoke controller callback for car model review insertion
            // Esempio: controller.addCarModelReview(modelId, title, comment, rating);
            successfullyAdded = controller.addModelReview(modelId, rating, title, comment);

        } else {
            String selectedItem = (String) cbSpareParts.getSelectedItem();
            if (selectedItem == null || selectedItem.equals("No spare parts purchased yet")) {
                JOptionPane.showMessageDialog(this, "You haven't bought any spare parts to review.", "Action Forbidden", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int partId = boughtSparePartsMap.get(selectedItem);
            
            // Invoke controller callback for spare part review insertion
            // Esempio: controller.addSparePartReview(partId, title, comment, rating);
            successfullyAdded = controller.addSparePartReview(partId, rating, title, comment);
        }

        // 3. Feedback notification and redirection
        if (successfullyAdded) {
            JOptionPane.showMessageDialog(this, "Thank you! Your review has been submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            frame.switchPanel("DASHBOARD");
        } else {
            JOptionPane.showMessageDialog(this, "An error occurred while uploading your review. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Resets field components back to default empty values.
     */
    private void clearForm() {
        txtTitle.setText("");
        txtComment.setText("");
        cbRating.setSelectedIndex(0);
        rbtnCarModel.setSelected(true);
        cbCarModels.setVisible(true);
        cbSpareParts.setVisible(false);
    }
}
