import java.util.*;
class Edge {
    int to;
    double cost;
    Edge(int to, double cost) {
        this.to = to;
        this.cost = cost;
    }
}
public class Assignment_5 {
    static Map<Integer, List<Edge>> graph = new HashMap<>();
    static int stages;
    static Map<Integer, Double> minCost = new HashMap<>();
    static Map<Integer, Integer> nextNode = new HashMap<>();
    public static void computeMinCost(int destination) {
        minCost.put(destination, 0.0);
        for (int stage = stages - 1; stage >= 1; stage--) {
            for (int node : getNodesInStage(stage)) {
                double min = Double.MAX_VALUE;
                int bestNext = -1;
                for (Edge edge : graph.getOrDefault(node, new ArrayList<>())) {
                    double c = edge.cost + minCost.getOrDefault(edge.to, Double.MAX_VALUE);
                    if (c < min) {
                        min = c;
                        bestNext = edge.to;
                    }
                }
                if (bestNext != -1) {
                    minCost.put(node, min);
                    nextNode.put(node, bestNext);
                    System.out.printf("🔹 Stage %d: Node %d → Next Node %d | Edge Cost: %.2f | Total Min Cost to Destination: %.2f%n",
                            stage, node, bestNext, getEdgeCost(node, bestNext), min);
                }
            }
        }
    }
    public static double getEdgeCost(int from, int to) {
        for (Edge edge : graph.getOrDefault(from, new ArrayList<>())) {
            if (edge.to == to) return edge.cost;
        }
        return 0;
    }
    public static List<Integer> reconstructPath(int source, int destination) {
        List<Integer> path = new ArrayList<>();
        int current = source;
        while (current != destination) {
            path.add(current);
            current = nextNode.get(current);
        }
        path.add(destination);
        return path;
    }
    public static List<Integer> getNodesInStage(int stage) {
        Map<Integer, List<Integer>> stageMap = new HashMap<>();
        stageMap.put(1, Arrays.asList(1));         // Source stage
        stageMap.put(2, Arrays.asList(2, 3));      // Transit hubs
        stageMap.put(3, Arrays.asList(4, 5));      // Warehouses
        stageMap.put(4, Arrays.asList(6));         // Destination stage
        return stageMap.getOrDefault(stage, new ArrayList<>());
    }
    public static void main(String[] args) {
        stages = 4;
        graph.put(1, Arrays.asList(new Edge(2, 2), new Edge(3, 3)));
        graph.put(2, Arrays.asList(new Edge(4, 3), new Edge(5, 2)));
        graph.put(3, Arrays.asList(new Edge(4, 2), new Edge(5, 4)));
        graph.put(4, Arrays.asList(new Edge(6, 2)));
        graph.put(5, Arrays.asList(new Edge(6, 3)));
        int source = 1;
        int destination = 6;
        System.out.println("\nComputing Optimal Delivery Route for SwiftCargo...\n");
        computeMinCost(destination);
        List<Integer> optimalPath = reconstructPath(source, destination);
        System.out.println("\nOptimal Delivery Path (Stage-wise):");
        for (int i = 0; i < optimalPath.size() - 1; i++) {
            int from = optimalPath.get(i);
            int to = optimalPath.get(i + 1);
            double cost = getEdgeCost(from, to);
            System.out.printf("Stage %d: Node %d → Node %d | Cost: %.2f%n", i + 1, from, to, cost);
        }
        System.out.printf("\nTotal Minimum Delivery Cost/Time: %.2f\n", minCost.get(source));
    }
}
