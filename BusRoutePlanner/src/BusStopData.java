import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BusStopData {
    private Map<String, Location> busStops;

    public BusStopData() {
        this.busStops = new HashMap<>();
        initializeBusStops();
    }

    private void initializeBusStops() {
        // Add bus stop data as needed
        Location stopA = new Location("Stop A", 37.7749, -122.4194);
        Location stopB = new Location("Stop B", 37.7749, -122.4095);

        busStops.put("Stop A", stopA);
        busStops.put("Stop B", stopB);

        // Add more bus stops as needed
    }

    public Map<String, Location> getBusStops() {
        return Collections.unmodifiableMap(busStops);
    }

    public Location getLocation(String stopName) {
        return busStops.get(stopName);
    }
}
