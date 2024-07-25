import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private BusRoutePlanner busRoutePlanner;

    public LoginPanel(BusRoutePlanner busRoutePlanner) {
        this.busRoutePlanner = busRoutePlanner;

        setLayout(new GridBagLayout());
        setBackground(new Color(243, 109, 38)); //
        setBorder(new EmptyBorder(10, 20, 20, 20));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Logo
        ImageIcon logoIcon = new ImageIcon("login.png"); // Replace "logo.png" with your logo file
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setPreferredSize(new Dimension(300, 300)); // Adjust the preferred size*/

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Georgia", Font.BOLD, 18));
        usernameLabel.setForeground(Color.black);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Georgia", Font.BOLD, 18));
        passwordLabel.setForeground(Color.black);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(logoLabel, constraints);

        // Username label
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(usernameLabel, constraints);

        constraints.gridx = 1;
        add(usernameField, constraints);

        // Password label
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(passwordLabel, constraints);

        constraints.gridx = 1;
        add(passwordField, constraints);

        // Login button
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2; // Full width
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Georgia", Font.BOLD, 18));
        loginButton.setBackground(Color.black);
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(true);
        loginButton.setFocusPainted(true);
        loginButton.addActionListener(this::loginButtonActionPerformed);
        loginButton.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(loginButton, constraints);

    }

    private void loginButtonActionPerformed(ActionEvent evt) {
        String username = usernameField.getText().toLowerCase(); // Convert to lowercase
        char[] password = passwordField.getPassword();

// Validate credentials and perform additional checks
        if (username.trim().isEmpty() || password.length == 0) {
            // Empty username or password, show an error message
            JOptionPane.showMessageDialog(this, "Please enter both username and password", "Login Error", JOptionPane.ERROR_MESSAGE);
        } else if ("admin".equals(username) && "admin".equals(String.valueOf(password))) {
            // Successful login, show the dashboard
            busRoutePlanner.showDashboardPanel();
        } else {
            // Invalid credentials, show an error message
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
