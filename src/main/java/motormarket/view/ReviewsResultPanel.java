package motormarket.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import motormarket.controller.Controller;
import motormarket.data.ReviewDetail;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ReviewsResultPanel extends JPanel {

    private MotorMarketFrame frame;
    private Controller controller;
    
    // Search UI Components
    private JRadioButton rbtnCarModel;
    private JRadioButton rbtnSparePart;
    private JComboBox<String> cbCarModels;
    private JComboBox<String> cbSpareParts;
    private JButton btnSearch;

    // Review Result UI Components
    private JLabel lblTitle;
    private JLabel lblAverageStars;
    private JTable reviewsTable;
    private DefaultTableModel tableModel;
    private JButton btnBack;

    // Maps loaded from the controller
    private Map<String, Integer> carModelsMap;   
    private Map<String, Integer> sparePartsMap;  

    public ReviewsResultPanel(MotorMarketFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        
        this.setLayout(new BorderLayout(15, 15));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Load the maps from the controller
        this.carModelsMap = controller.brandsModels();
        this.sparePartsMap = controller.getSpareParts();

        // --- 1. TOP SECTION: SEARCH PANEL & HEADER ---
        JPanel topContainer = new JPanel(new BorderLayout(10, 10));

        // 1a. Structured Search Bar (GridBagLayout prevents off-screen clipping)
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Reviews"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0, Column 0: Type Label
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.0;
        searchPanel.add(new JLabel("Type:"), gbc);

        // Row 0, Column 1: Radio Buttons Container
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        rbtnCarModel = new JRadioButton("Car Model", true);
        rbtnSparePart = new JRadioButton("Spare Part");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(rbtnCarModel);
        typeGroup.add(rbtnSparePart);
        radioPanel.add(rbtnCarModel);
        radioPanel.add(rbtnSparePart);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        searchPanel.add(radioPanel, gbc);

        // Row 1, Column 0: Product Selection Label
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        searchPanel.add(new JLabel("Selection:"), gbc);

        // Row 1, Column 1: Dropdown ComboBoxes Layered together
        JPanel comboPanel = new JPanel(new CardLayout());
        
        cbCarModels = new JComboBox<>();
        if (carModelsMap != null) {
            for (String modelName : carModelsMap.keySet()) {
                cbCarModels.addItem(modelName);
            }
        }

        cbSpareParts = new JComboBox<>();
        if (sparePartsMap != null) {
            for (String partName : sparePartsMap.keySet()) {
                cbSpareParts.addItem(partName);
            }
        }
        cbSpareParts.setVisible(false); // Hidden initially

        comboPanel.add(cbCarModels);
        comboPanel.add(cbSpareParts);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        searchPanel.add(comboPanel, gbc);

        // Row 2, Column 1: Search Button positioned directly below
        btnSearch = new JButton("Search 🔍");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 12));
        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnWrapper.add(btnSearch);
        
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.weightx = 1.0;
        searchPanel.add(btnWrapper, gbc);

        // 1b. Results Header Display
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        lblTitle = new JLabel("Product Reviews", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        lblAverageStars = new JLabel("Average Rating: N/A", SwingConstants.LEFT);
        lblAverageStars.setFont(new Font("Arial", Font.BOLD, 14));
        lblAverageStars.setForeground(new Color(218, 165, 32)); 

        headerPanel.add(lblTitle);
        headerPanel.add(lblAverageStars);

        topContainer.add(searchPanel, BorderLayout.NORTH);
        topContainer.add(headerPanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        // --- 2. CENTER PANEL: REVIEWS TABLE ---
        String[] columns = {"Author", "Rating", "Title", "Review Comment", "Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        reviewsTable = new JTable(tableModel);
        reviewsTable.setRowHeight(28);
        reviewsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        reviewsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JScrollPane scrollPane = new JScrollPane(reviewsTable);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. BOTTOM PANEL: NAVIGATION ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnBack = new JButton("◀ Back to Dashboard");
        btnBack.setFont(new Font("Arial", Font.BOLD, 12));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. EVENT LISTENERS ---
        btnBack.addActionListener(e -> frame.switchPanel("DASHBOARD"));

        // Dynamically toggle visible combo components safely
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

        btnSearch.addActionListener(e -> performSearch());
    }

    /**
     * Resolves the selected item from the correct map and triggers the controller query.
     */
    private void performSearch() {
        if (rbtnCarModel.isSelected()) {
            if (cbCarModels.getSelectedItem() == null) return;
            
            String selectedModel = cbCarModels.getSelectedItem().toString();
            Integer modelId = carModelsMap.get(selectedModel);
            
            if (modelId != null) {
                lblTitle.setText("Reviews for Car Model: " + selectedModel);
                List<ReviewDetail> reviews = controller.getModelReviews(modelId);
                populateTable(reviews);
            }
        } else {
            if (cbSpareParts.getSelectedItem() == null) return;
            
            String selectedPart = cbSpareParts.getSelectedItem().toString();
            Integer partId = sparePartsMap.get(selectedPart);
            
            if (partId != null) {
                lblTitle.setText("Reviews for Spare Part: " + selectedPart);
                List<ReviewDetail> reviews = controller.getSparePartReviews(partId);
                populateTable(reviews);
            }
        }
    }

    /**
     * Clears table rows, extracts the unified average score, and builds the visual list.
     */
    private void populateTable(List<ReviewDetail> reviews) {
        tableModel.setRowCount(0); 

        if (reviews == null || reviews.isEmpty()) {
            lblAverageStars.setText("Average Rating: No reviews found");
            tableModel.addRow(new Object[]{"-", "-", "No reviews available", "There are no reviews for this item yet.", "-"});
            return;
        }

        double avg = reviews.get(0).getAverageRating();
        
        StringBuilder stars = new StringBuilder();
        int fullStars = (int) Math.round(avg);
        for (int i = 1; i <= 5; i++) {
            if (i <= fullStars) stars.append("★");
            else stars.append("☆");
        }
        lblAverageStars.setText(String.format("Average Rating: %.1f / 5.0  %s", avg, stars.toString()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (ReviewDetail r : reviews) {
            String authorName = r.getFirstName() + " " + (r.getLastName() != null && !r.getLastName().isEmpty() ? r.getLastName().substring(0, 1) + "." : "");
            String individualStars = "★".repeat(r.getRating()) + "☆".repeat(5 - r.getRating());
            String formattedDate = (r.getDate() != null) ? r.getDate().format(formatter) : "-";

            tableModel.addRow(new Object[]{
                authorName,
                individualStars + " (" + r.getRating() + ")",
                r.getTitle(),
                r.getText(),
                formattedDate
            });
        }
    }
}
