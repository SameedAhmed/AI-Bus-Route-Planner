import java.util.*;

public class Graph {
    private Map<Location, List<Location>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addLocation(Location location) {
        adjacencyList.putIfAbsent(location, new ArrayList<>());
    }

    public void addEdge(Location source, Location destination) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source); // Assuming an undirected graph
    }

    public List<Location> findShortestPath(Location start, Location end) {
        Set<Location> visited = new HashSet<>();
        Map<Location, Location> previousNodes = new HashMap<>();
        Map<Location, Double> distance = new HashMap<>();

        PriorityQueue<Location> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));

        distance.put(start, 0.0);
        priorityQueue.offer(start);

        while (!priorityQueue.isEmpty()) {
            Location current = priorityQueue.poll();
            visited.add(current);

            for (Location neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    double newDist = distance.get(current) + calculateDistance(current, neighbor);
                    if (newDist < distance.getOrDefault(neighbor, Double.MAX_VALUE)) {
                        distance.put(neighbor, newDist);
                        previousNodes.put(neighbor, current);
                        priorityQueue.offer(neighbor);
                    }
                }
            }
        }

        // Reconstruct the path
        List<Location> path = new ArrayList<>();
        for (Location at = end; at != null; at = previousNodes.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        return path;
    }

    private double calculateDistance(Location source, Location destination) {
        // Simplified distance calculation, you might want to use a more accurate formula
        double lat1 = Math.toRadians(source.getLatitude());
        double lon1 = Math.toRadians(source.getLongitude());
        double lat2 = Math.toRadians(destination.getLatitude());
        double lon2 = Math.toRadians(destination.getLongitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Radius of the Earth (in kilometers)
        double radius = 6371;

        return radius * c;
    }
}
