import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class JdbcInsertDemo extends JFrame {
    private JTextField urlField;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton connectButton;
    
    private JTextField nameField;
    private JTextField emailField;
    private JTextField ageField;
    private JComboBox<String> departmentCombo;
    private JButton insertButton;
    private JTextArea logArea;
    
    private Connection connection;
    
    public JdbcInsertDemo() {
        setTitle("JDBC Insert Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        
        initUI();
        setVisible(true);
    }
    
    private void initUI() {
        // Create main panel with tabbed layout
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Connection panel
        JPanel connectionPanel = createConnectionPanel();
        tabbedPane.addTab("Database Connection", connectionPanel);
        
        // Insert panel
        JPanel insertPanel = createInsertPanel();
        tabbedPane.addTab("Insert Data", insertPanel);
        
        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Add components to frame
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);
    }
    
    private JPanel createConnectionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Database URL
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Database URL:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        urlField = new JTextField("jdbc:mysql://localhost:3306/testdb", 30);
        panel.add(urlField, gbc);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        userField = new JTextField("root", 20);
        panel.add(userField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);
        
        // Connect button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        connectButton = new JButton("Connect to Database");
        connectButton.addActionListener(new ConnectButtonListener());
        panel.add(connectButton, gbc);
        
        return panel;
    }
    
    private JPanel createInsertPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);
        
        // Email field
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);
        
        // Age field
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Age:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        ageField = new JTextField(3);
        panel.add(ageField, gbc);
        
        // Department combo
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Department:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        String[] departments = {"Engineering", "Marketing", "Sales", "HR", "Finance"};
        departmentCombo = new JComboBox<>(departments);
        panel.add(departmentCombo, gbc);
        
        // Insert button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        insertButton = new JButton("Insert Record");
        insertButton.setEnabled(false);
        insertButton.addActionListener(new InsertButtonListener());
        panel.add(insertButton, gbc);
        
        return panel;
    }
    
    private class ConnectButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String url = urlField.getText();
            String user = userField.getText();
            String password = new String(passwordField.getPassword());
            
            try {
                // Load JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establish connection
                connection = DriverManager.getConnection(url, user, password);
                
                // Create table if it doesn't exist
                createTableIfNotExists();
                
                log("Connected to database successfully!");
                insertButton.setEnabled(true);
                connectButton.setEnabled(false);
                
            } catch (ClassNotFoundException ex) {
                log("Error: MySQL JDBC Driver not found.");
                JOptionPane.showMessageDialog(JdbcInsertDemo.this, 
                    "MySQL JDBC Driver not found. Please add the driver to your classpath.",
                    "Driver Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                log("Connection failed: " + ex.getMessage());
                JOptionPane.showMessageDialog(JdbcInsertDemo.this, 
                    "Connection failed: " + ex.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private class InsertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String ageText = ageField.getText().trim();
            String department = (String) departmentCombo.getSelectedItem();
            
            // Validate input
            if (name.isEmpty() || email.isEmpty() || ageText.isEmpty()) {
                JOptionPane.showMessageDialog(JdbcInsertDemo.this,
                    "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(JdbcInsertDemo.this,
                    "Age must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Insert the record
            try {
                String sql = "INSERT INTO employees (name, email, age, department) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setInt(3, age);
                pstmt.setString(4, department);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    log("Inserted record successfully: " + name + ", " + email + ", " + age + ", " + department);
                    JOptionPane.showMessageDialog(JdbcInsertDemo.this,
                        "Record inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Clear fields
                    nameField.setText("");
                    emailField.setText("");
                    ageField.setText("");
                }
                
                pstmt.close();
            } catch (SQLException ex) {
                log("Insert failed: " + ex.getMessage());
                JOptionPane.showMessageDialog(JdbcInsertDemo.this,
                    "Insert failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL, " +
                "age INT, " +
                "department VARCHAR(50), " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            log("Checked/created employees table successfully");
        }
    }
    
    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        // Set a nicer look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new JdbcInsertDemo());
    }
}