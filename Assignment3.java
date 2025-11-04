import java.util.*;

class ReliefItem {
    String name;
    double weight;
    double value;
    boolean divisible; // true if the item can be divided (e.g., food, water)

    public ReliefItem(String name, double weight, double value, boolean divisible) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.divisible = divisible;
    }

    public double getValuePerKg() {
        return value / weight;
    }
}

public class EmergencyReliefDistribution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Emergency Relief Supply Distribution System");
        System.out.print("Enter maximum boat capacity (in kg): ");
        double maxCapacity = sc.nextDouble();

        System.out.print("Enter number of relief items: ");
        int n = sc.nextInt();

        List<ReliefItem> items = new ArrayList<>();

        
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for item " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = sc.next();

            System.out.print("Weight (kg): ");
            double weight = sc.nextDouble();

            System.out.print("Utility value: ");
            double value = sc.nextDouble();

            System.out.print("Is it divisible? (true for yes / false for no): ");
            boolean divisible = sc.nextBoolean();

            items.add(new ReliefItem(name, weight, value, divisible));
        }

        double totalValue = maximizeUtility(items, maxCapacity);

        System.out.printf("\n maximum total utility value that can be transported: %.2f\n", totalValue);
        sc.close();
    }

   
    public static double maximizeUtility(List<ReliefItem> items, double capacity) {
        // Sort items by value/weight ratio (descending order)
        items.sort((a, b) -> Double.compare(b.getValuePerKg(), a.getValuePerKg()));

        double totalValue = 0.0;
        double remainingCapacity = capacity;

        System.out.println("\n===  Selected Items for Transport ===");

        for (ReliefItem item : items) {
            if (remainingCapacity == 0)
                break;

            if (item.weight <= remainingCapacity) {
                // Take the full item
                totalValue += item.value;
                remainingCapacity -= item.weight;

                System.out.printf(" %s (Full) — Weight: %.2f kg | Value: %.2f\n",
                        item.name, item.weight, item.value);
            } else if (item.divisible) {
                // Take a fraction of the item
                double fraction = remainingCapacity / item.weight;
                double fractionValue = item.value * fraction;
                totalValue += fractionValue;

                System.out.printf(" %s (Partial %.2f%%) — Weight: %.2f kg | Value: %.2f\n",
                        item.name, fraction * 100, remainingCapacity, fractionValue);

                remainingCapacity = 0;
            }
        }

        System.out.printf("\n Remaining Boat Capacity: %.2f kg\n", remainingCapacity);
        return totalValue;
    }
}
