import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.net.URLEncoder;

public class DashboardPanel extends JPanel {

    private BusRoutePlanner busRoutePlanner;

    private JComboBox<String> nameField;
    private JComboBox<String> locationField;
    private JComboBox<String> destinationComboBox;
    private JTextField fareField;
    private JTextField distanceField; // New field for displaying calculated distance
    private JButton showMapButton;
    private JButton useLastEntryButton;
    private JButton calculateFareButton;

    private Set<String> savedDestinations;
    private Map<String, Double> locationDistances;
    private Map<String, Double> locationFares;
    private JButton viewSummaryButton;
    private JFrame summaryFrame;

    public DashboardPanel(BusRoutePlanner busRoutePlanner) {
        this.busRoutePlanner = busRoutePlanner;
        initComponents();
        initLocationData();
        setButtonStyles(showMapButton, calculateFareButton, useLastEntryButton, viewSummaryButton);
    }

    private void setButtonStyles(AbstractButton... buttons) {
        for (AbstractButton button : buttons) {
            button.setBackground(Color.black);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Georgia", Font.BOLD, 14));
            button.setBorderPainted(false);
            button.setContentAreaFilled(true);
            button.setBorder(new EmptyBorder(7,7,7,7));
            // You can add other common styles here

        }
    }
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Georgia", Font.BOLD, 13));
        // You can add other common styles here
        return label;
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setBackground(new Color(243, 109, 38));
        setBorder(new EmptyBorder(0, 5, 5, 5));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel header = new JLabel();
        header.setForeground(Color.WHITE);
        header.setBackground(Color.BLUE);
        header.setText("Bus Route Planner");
        header.setFont(new Font("times new roman", Font.BOLD, 32));

        ImageIcon logoIcon = new ImageIcon("login.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setPreferredSize(new Dimension(300, 300));

        savedDestinations = new HashSet<>();

        nameField = new JComboBox<>(new String[]{"R-1","R-2","R-3","R-4","EV-1","EV-2","EV-3","EV-4"});
        nameField.setEditable(true);
        locationField = new JComboBox<>(new String[]{"Nipa Chowrangi Bus Stop","University Rd","Urdu University Stop","Civic Center Stop","DMC East Official Karachi","Younus Plaza","Jail Chowrangi Stop","Dawood University","Secretariat Chowrangi","Numaish"});
        locationField.setEditable(true);
        destinationComboBox = new JComboBox<>(new String[]{"Nipa Chowrangi Bus Stop","University Rd","Urdu University Stop","Civic Center Stop","DMC East Official Karachi","Younus Plaza","Jail Chowrangi Stop","Dawood University","Secretariat Chowrangi","Numaish"});
        destinationComboBox.setEditable(true);
        fareField = new JTextField(20);
        fareField.setEditable(false);
        distanceField = new JTextField(20);
        distanceField.setEditable(false); // Make the distance field non-editable


        showMapButton = new JButton("Show Map");
        showMapButton.addActionListener(this::showMapButtonActionPerformed);

        calculateFareButton = new JButton("Calculate Fare");
        calculateFareButton.addActionListener(this::calculateFareButtonActionPerformed);

        useLastEntryButton = new JButton("Use Last Entry");
        useLastEntryButton.addActionListener(this::useLastEntryButtonActionPerformed);

        viewSummaryButton = new JButton("View Summary");
        viewSummaryButton.addActionListener(this::viewSummaryButtonActionPerformed);

        setButtonStyles(showMapButton, calculateFareButton, useLastEntryButton, viewSummaryButton);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(logoLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(createStyledLabel("Select Bus:"), constraints);

        constraints.gridx = 1;
        add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(createStyledLabel("Current Stop:"), constraints);

        constraints.gridx = 1;
        add(locationField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        add(createStyledLabel("Select Destination:"), constraints);

        constraints.gridx = 1;
        add(destinationComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        add(createStyledLabel("Trip Fare:"), constraints);

        constraints.gridx = 1;
        add(fareField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        add(createStyledLabel("Calculated Distance:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        add(distanceField, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(useLastEntryButton, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 9;
        add(showMapButton, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(calculateFareButton, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 8;
        add(viewSummaryButton, constraints);

    }

    private void initLocationData() {
        double defaultFarePerKm = 20.0;

        locationDistances = new HashMap<>();
        locationDistances.put("Nipa Chowrangi Bus Stop", 0.0);
        locationDistances.put("University Rd", 0.4);
        locationDistances.put("Urdu University Stop", 1.1);
        locationDistances.put("Civic Center Stop",3.2 );
        locationDistances.put("DMC East Official Karachi", 3.5);
        locationDistances.put("Younus Plaza", 4.7);
        locationDistances.put("Jail Chowrangi Stop", 5.5);
        locationDistances.put("Dawood University", 6.7);
        locationDistances.put("Secretariat Chowrangi", 7.2);
        locationDistances.put("Numaish", 8.2);

        locationFares = new HashMap<>();
        locationFares.put("Nipa Chowrangi Bus Stop", defaultFarePerKm);
        locationFares.put("University Rd", defaultFarePerKm);
        locationFares.put("Urdu University Stop", defaultFarePerKm);
        locationFares.put("Civic Center Stop", defaultFarePerKm);
        locationFares.put("DMC East Official Karachi", defaultFarePerKm);
        locationFares.put("Younus Plaza", defaultFarePerKm);
        locationFares.put("Jail Chowrangi Stop", defaultFarePerKm);
        locationFares.put("Dawood University", defaultFarePerKm);
        locationFares.put("Secretariat Chowrangi",defaultFarePerKm);
        locationFares.put("Numaish", defaultFarePerKm);
    }

    private void showMapButtonActionPerformed(ActionEvent evt) {
        String location = (String) locationField.getSelectedItem();
        String destination = (String) destinationComboBox.getSelectedItem();
        String fare = calculateFare(location, destination);
        double distance = calculateDistance(location, destination);

        openGoogleMaps(location, destination);

        String formattedDistance = String.format("%.1f", distance);
        distanceField.setText(formattedDistance);
    }

    private void calculateFareButtonActionPerformed(ActionEvent evt) {
        String location = (String) locationField.getSelectedItem();
        String destination = (String) destinationComboBox.getSelectedItem();
        String fare = calculateFare(location, destination);
        double distance = calculateDistance(location, destination);

        String name = (String) nameField.getSelectedItem();

        savedDestinations.add(destination);
//        updateDestinationComboBox();
        updateBookingSummaryArea(name, location, destination, fare);
        saveDataToFile(name, location, destination, fare);

        fare = "Rs. " + fare;
        fareField.setText(fare);
        distanceField.setText(String.valueOf(distance));
    }
    private void viewSummaryButtonActionPerformed(ActionEvent evt) {
        SwingUtilities.invokeLater(() -> {
            if (summaryFrame == null) {
                summaryFrame = new JFrame("Booking Summary");
                summaryFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                summaryFrame.setSize(600, 700);
                summaryFrame.setLocationRelativeTo(null);

                BookingSummaryPanel summaryPanel = new BookingSummaryPanel();
                summaryFrame.add(summaryPanel);
            }
            summaryFrame.setVisible(true);
        });
    }

    private double calculateDistance(String location, String destination) {
        double distance = locationDistances.get(destination) - locationDistances.get(location);
        return roundTo1DecimalPlace(distance);
    }
    private double roundTo1DecimalPlace(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    private String calculateFare(String location, String destination) {
        double distance = locationDistances.get(destination) - locationDistances.get(location);
        double farePerKm = locationFares.get(destination);
        double totalFare =  distance * farePerKm;
        totalFare = Math.abs(totalFare);
        totalFare = Math.round(totalFare / 10.0) * 10.0;
        return String.valueOf(totalFare);
    }

    private void useLastEntryButtonActionPerformed(ActionEvent evt) {
        String lastName = getLastEntry("name");
        String lastLocation = getLastEntry("location");
        String lastDestination = getLastEntry("destination");
        String lastFare = getLastEntry("fare");

        nameField.setSelectedItem(lastName);
        locationField.setSelectedItem(lastLocation);
        destinationComboBox.setSelectedItem(lastDestination);
        fareField.setText(lastFare);
    }

    private void updateDestinationComboBox() {
        destinationComboBox.removeAllItems();
        for (String destination : savedDestinations) {
            destinationComboBox.addItem(destination);
        }
    }

    private void updateBookingSummaryArea(String name, String location, String destination, String fare) {
        // Implement booking summary area update logic here
    }

    private void openGoogleMaps(String location, String destination) {
        try {
            String encodedLocation = URLEncoder.encode(location, "UTF-8");
            String encodedDestination = URLEncoder.encode(destination, "UTF-8");
            String url = "https://www.google.com/maps/dir/" + encodedLocation + "/" + encodedDestination;
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    private void saveDataToFile(String name, String location, String destination, String fare) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("userdata.txt", true))) {
            writer.println("|Bus:" + name + "|location:" + location + "|destination:" + destination + "|fare:" + fare);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLastEntry(String field) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            String lastEntry = null;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] keyValuePairs = line.split("\\|");
                for (String pair : keyValuePairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        String currentField = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        if (field.equals(currentField)) {
                            lastEntry = value;
                        }
                    }
                }
            }
            return lastEntry;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
