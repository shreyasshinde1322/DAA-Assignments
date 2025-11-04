import java.util.*;
class Movie {
    String title;
    double imdbRating;
    int releaseYear;
    int watchTime;
    public Movie(String title, double imdbRating, int releaseYear, int watchTime) {
        this.title = title;
        this.imdbRating = imdbRating;
        this.releaseYear = releaseYear;
        this.watchTime = watchTime;
    }
    @Override
    public String toString() {
        return String.format("%-20s | IMDB: %.1f | Year: %d | Popularity: %d",
                title, imdbRating, releaseYear, watchTime);
    }
}
public class Assignment_2 {
    public static void quickSort(Movie[] movies, int low, int high, String sortBy) {
        if (low < high) {
            int partitionIndex = partition(movies, low, high, sortBy);
            quickSort(movies, low, partitionIndex - 1, sortBy);
            quickSort(movies, partitionIndex + 1, high, sortBy);
        }
    }
    private static int partition(Movie[] movies, int low, int high, String sortBy) {
        Movie pivot = movies[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(movies[j], pivot, sortBy) > 0) { // Descending order
                i++;
                swap(movies, i, j);
            }
        }
        swap(movies, i + 1, high);
        return i + 1;
    }
    private static int compare(Movie a, Movie b, String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "rating": return Double.compare(a.imdbRating, b.imdbRating);
            case "year": return Integer.compare(a.releaseYear, b.releaseYear);
            case "popularity": return Integer.compare(a.watchTime, b.watchTime);
            default: throw new IllegalArgumentException("Invalid sort parameter: " + sortBy);
        }
    }
    private static void swap(Movie[] movies, int i, int j) {
        Movie temp = movies[i];
        movies[i] = movies[j];
        movies[j] = temp;
    }
    public static void main(String[] args) {
        Movie[] movies = {
                new Movie("Inception", 8.8, 2010, 95),
                new Movie("Avengers: Endgame", 8.4, 2019, 99),
                new Movie("The Dark Knight", 9.0, 2008, 97),
                new Movie("Dune", 8.1, 2021, 85),
                new Movie("Interstellar", 8.6, 2014, 96),
                new Movie("Oppenheimer", 8.7, 2023, 92)
        };
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n Choose Sorting Parameter: rating / year / popularity (or type 'exit' to quit)");
            String sortBy = sc.nextLine().trim().toLowerCase();
            if (sortBy.equals("exit")) {
                System.out.println(" Exiting StreamFlix Sorter...");
                break;
            }
            try {
                System.out.println("\nBefore Sorting:");
                for (Movie m : movies) System.out.println(m);
                long start = System.nanoTime();
                quickSort(movies, 0, movies.length - 1, sortBy);
                long end = System.nanoTime();
                System.out.println("\n After Sorting by " + sortBy.toUpperCase() + ":");
                for (Movie m : movies) System.out.println(m);
                System.out.printf("\n Time Taken: %.3f ms%n", (end - start) / 1e6);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice! Please select rating / year / popularity.");
            }
        }
        sc.close();
    }
}
