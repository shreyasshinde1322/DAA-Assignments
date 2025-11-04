import java.util.*;
class CustomerOrder {
    long timestamp;
    String orderId;
    String customerName;
    public CustomerOrder(long timestamp, String orderId, String customerName) {
        this.timestamp = timestamp;
        this.orderId = orderId;
        this.customerName = customerName;
    }
    @Override
    public String toString() {
        return String.format("OrderID: %-5s | Timestamp: %-8d | Customer: %s",
                orderId, timestamp, customerName);
    }
}
public class Assignment_1 {
    public static void mergeSort(CustomerOrder[] orders, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(orders, left, mid);
            mergeSort(orders, mid + 1, right);
            merge(orders, left, mid, right);
        }
    }
    private static void merge(CustomerOrder[] orders, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        CustomerOrder[] leftArr = new CustomerOrder[n1];
        CustomerOrder[] rightArr = new CustomerOrder[n2];
        for (int i = 0; i < n1; i++) leftArr[i] = orders[left + i];
        for (int j = 0; j < n2; j++) rightArr[j] = orders[mid + 1 + j];
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i].timestamp <= rightArr[j].timestamp) {
                orders[k++] = leftArr[i++];
            } else {
                orders[k++] = rightArr[j++];
            }
        }
        while (i < n1) orders[k++] = leftArr[i++];
        while (j < n2) orders[k++] = rightArr[j++];
    }
    public static void main(String[] args) {
        CustomerOrder[] orders = {
                new CustomerOrder(1726829300L, "ORD101", "Aditya"),
                new CustomerOrder(1726829200L, "ORD102", "Riya"),
                new CustomerOrder(1726829400L, "ORD103", "Karan"),
                new CustomerOrder(1726829100L, "ORD104", "Sakshi"),
                new CustomerOrder(1726829500L, "ORD105", "Neha")
        };
        System.out.println("------------- Before Sorting (By Timestamp) ---------------");
        for (CustomerOrder o : orders) System.out.println(o);
        long start = System.nanoTime();
        mergeSort(orders, 0, orders.length - 1);
        long end = System.nanoTime();
        System.out.println("\n----------- After Sorting (By Timestamp) ----------------");
        for (CustomerOrder o : orders) System.out.println(o);
        System.out.printf("\n Time Taken: %.3f ms%n", (end - start) / 1e6);
    }
}
