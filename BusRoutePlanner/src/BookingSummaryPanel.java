import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BookingSummaryPanel extends JPanel {

    private JTextArea summaryTextArea;
    private JComboBox<String> nameComboBox;

    private Set<String> allNames;

    public BookingSummaryPanel() {
        initComponents();
        loadAllNames();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(243, 109, 38)); // Set background color

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        allNames = new HashSet<>();

        // Add Logo
        ImageIcon logoIcon = new ImageIcon("login.png"); // Replace "logo.png" with your logo file
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setPreferredSize(new Dimension(200, 300));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(logoLabel, constraints);

        // Add Name ComboBox
        nameComboBox = new JComboBox<>();
        nameComboBox.addActionListener(this::nameComboBoxActionPerformed);
        Font customFont = new Font("Georgia", Font.BOLD, 18);
        nameComboBox.setFont(customFont);
        nameComboBox.setBackground(Color.BLACK);
        nameComboBox.setForeground(new Color(128, 39, 6));
        nameComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        add(nameComboBox, constraints);

        // Add Summary TextArea
        summaryTextArea = new JTextArea();
        summaryTextArea.setEditable(false);
        summaryTextArea.setFont(new Font("Helvetica", Font.BOLD, 16)); // Increased font size
        summaryTextArea.setBackground(new Color(255, 255, 240)); // Set background color
        summaryTextArea.setForeground(Color.BLACK);
        summaryTextArea.setLineWrap(true);
        summaryTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(summaryTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(scrollPane, constraints);
    }

    private void loadAllNames() {
        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                if (userData.length > 1) {
                    allNames.add(userData[1]);
                }
            }
            updateNameComboBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateNameComboBox() {
        nameComboBox.removeAllItems();
        for (String name : allNames) {
            nameComboBox.addItem(name);
        }
    }

    private void nameComboBoxActionPerformed(ActionEvent evt) {
        String selectedName = (String) nameComboBox.getSelectedItem();
        if (selectedName != null) {
            displayBookedRoutes(selectedName);
        }
    }

    private void displayBookedRoutes(String name) {
        StringBuilder routes = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                if (userData.length > 1 && userData[1].equals(name)) {
                    routes.append(line).append("\n");
                }
            }
            summaryTextArea.setText(routes.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the look and feel to the system's default
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Booking Summary");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            BookingSummaryPanel summaryPanel = new BookingSummaryPanel();
            frame.add(summaryPanel);

            frame.setVisible(true);
        });
    }
}
