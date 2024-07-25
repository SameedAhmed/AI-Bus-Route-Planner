import javax.swing.*;
import java.awt.*;
public class BusRoutePlanner extends JFrame {
    private LoginPanel loginPanel;
    private DashboardPanel dashboardPanel;
    private MapPanel mapPanel;
    private enum State {
        LOGIN, DASHBOARD, MAP
    }
    private State currentState;
    public BusRoutePlanner() {
        initComponents();
        showLoginPanel();
        currentState = State.LOGIN;
    }
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        loginPanel = new LoginPanel(this);
        dashboardPanel = new DashboardPanel(this);
        mapPanel = new MapPanel();
    }
    public void showLoginPanel() {
        setContentPane(loginPanel);
        revalidate();
        repaint();
        currentState = State.LOGIN;
    }
    public void showDashboardPanel() {
        setContentPane(dashboardPanel);
        revalidate();
        repaint();
        currentState = State.DASHBOARD;
    }
    public void showMap(String location) {
        mapPanel.openGoogleMaps(location);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new BusRoutePlanner().setVisible(true);
        });
    }
}