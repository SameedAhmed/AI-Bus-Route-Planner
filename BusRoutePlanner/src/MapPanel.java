import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MapPanel extends JPanel {

    private JTextField locationField;
    private JButton showMapButton;

    public MapPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel mainHeadingLabel = new JLabel("Bus Route Planner");
        mainHeadingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainHeadingLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        locationField = new JTextField();
        locationField.setMaximumSize(new Dimension(200, 30));
        centerPanel.add(new JLabel("Enter Location:"));
        centerPanel.add(locationField);

        showMapButton = new JButton("Show Map");
        showMapButton.addActionListener(this::showMapButtonActionPerformed);
        centerPanel.add(showMapButton);

        add(mainHeadingLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void showMapButtonActionPerformed(ActionEvent evt) {
        String location = locationField.getText();
        openGoogleMaps(location);
    }

    public void openGoogleMaps(String location) {
        try {
            String url = "https://www.google.com/maps?q=" + location;
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void showMap(String location) {
    }
}
