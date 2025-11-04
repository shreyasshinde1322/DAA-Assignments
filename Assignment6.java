//PRN - 123B1F063
//Name - Pratik Rajkumar Mulik
//Date - 

/*
Scenario: Disaster Relief Resource Allocation 
A massive earthquake has struck a remote region, and a relief organization is transporting 
essential supplies to the affected area. The organization has a limited-capacity relief truck that 
can carry a maximum weight of W kg. They have N different types of essential items, each 
with a specific weight and an associated utility value (importance in saving lives and meeting 
urgent needs). 
Since the truck has limited capacity, you must decide which items to include to maximize the 
total utility value while ensuring the total weight does not exceed the truck's limit. 
Your Task as a Logistics Coordinator: 
1. Model this problem using the 0/1 Knapsack approach, where each item can either be 
included in the truck (1) or not (0). 
2. Implement an algorithm to find the optimal set of items that maximizes utility while 
staying within the weight constraint. 
3. Analyze the performance of different approaches (e.g., Brute Force, Dynamic 
Programming, and Greedy Algorithms) for solving this problem efficiently. 
4. Optimize for real-world constraints, such as perishable items (medicines, food) having 
priority over less critical supplies. 
Extend the model to consider multiple trucks or real-time decision-making for dynamic supply 
chain management. 
*/


import java.util.*;

class ReliefItem {
    String name;
    int weight;
    int utility;
    boolean perishable;

    ReliefItem(String name, int weight, int utility, boolean perishable) {
        this.name = name;
        this.weight = weight;
        this.utility = utility;
        this.perishable = perishable;
    }
}

public class DisasterReliefResourceAllocation {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Disaster Relief Resource Allocation System ===");

        System.out.print("Enter number of relief items: ");
        int n = sc.nextInt();

        System.out.print("Enter truck capacity (in kg): ");
        int W = sc.nextInt();

        ReliefItem[] items = new ReliefItem[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for item " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = sc.next();

            System.out.print("Weight (kg): ");
            int weight = sc.nextInt();

            System.out.print("Utility value: ");
            int utility = sc.nextInt();

            System.out.print("Is it perishable? (true/false): ");
            boolean perishable = sc.nextBoolean();

            // Apply priority boost for perishable items (+20%)
            if (perishable) {
                utility += (int) (0.2 * utility);
            }

            items[i] = new ReliefItem(name, weight, utility, perishable);
        }

        System.out.println("\nSelect algorithm:");
        System.out.println("1. Brute Force (Exhaustive)");
        System.out.println("2. Dynamic Programming (Optimal)");
        System.out.println("3. Greedy Approximation (Fast)");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.println("\nUsing Brute Force Algorithm...");
                System.out.println("Max Utility: " + bruteForceKnapsack(items, n, W));
                break;

            case 2:
                System.out.println("\nUsing Dynamic Programming Algorithm...");
                solveKnapsackDP(items, n, W);
                break;

            case 3:
                System.out.println("\nUsing Greedy Approximation Algorithm...");
                greedyKnapsack(items, W);
                break;

            default:
                System.out.println("Invalid choice!");
        }

        sc.close();
    }

    // ---------------- Brute Force ----------------
    static int bruteForceKnapsack(ReliefItem[] items, int n, int W) {
        return bruteForceRecursive(items, n - 1, W);
    }

    static int bruteForceRecursive(ReliefItem[] items, int i, int remainingWeight) {
        if (i < 0 || remainingWeight == 0) return 0;

        if (items[i].weight > remainingWeight)
            return bruteForceRecursive(items, i - 1, remainingWeight);

        int exclude = bruteForceRecursive(items, i - 1, remainingWeight);
        int include = items[i].utility + bruteForceRecursive(items, i - 1, remainingWeight - items[i].weight);

        return Math.max(include, exclude);
    }

    // ---------------- Dynamic Programming ----------------
    static void solveKnapsackDP(ReliefItem[] items, int n, int W) {
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                if (items[i - 1].weight <= w) {
                    dp[i][w] = Math.max(items[i - 1].utility + dp[i - 1][w - items[i - 1].weight], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        System.out.println("Maximum Utility Value: " + dp[n][W]);
        System.out.println("Selected Items:");

        int w = W;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                System.out.println("  - " + items[i - 1].name + " (Weight: " + items[i - 1].weight +
                        " | Value: " + items[i - 1].utility + ")");
                w -= items[i - 1].weight;
            }
        }
    }

    // ---------------- Greedy (Approximation) ----------------
    static void greedyKnapsack(ReliefItem[] items, int W) {
        Arrays.sort(items, (a, b) -> Double.compare(
                (double) b.utility / b.weight,
                (double) a.utility / a.weight
        ));

        int totalWeight = 0;
        int totalValue = 0;

        System.out.println("Selected Items (Greedy):");
        for (ReliefItem item : items) {
            if (totalWeight + item.weight <= W) {
                totalWeight += item.weight;
                totalValue += item.utility;
                System.out.println("  - " + item.name + " (Weight: " + item.weight + " | Value: " + item.utility + ")");
            }
        }
        System.out.println("Approx. Total Utility: " + totalValue);
    }
}
