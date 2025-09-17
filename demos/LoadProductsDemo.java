
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

class Product {
    int productId;
    String name;
    String description;
    String manufactoringDate;
    String expiryDate;

    public Product(int productId, String name, String description, String manufactoringDate, String expiryDate) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.manufactoringDate = manufactoringDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return productId + ", " + name + ", " + description + ", " + manufactoringDate + ", " + expiryDate;
    }
}

public class LoadProductsDemo {
    public static List<Product> loadWithBufferedReader(String filePath) throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length == 5) {
                    products.add(new Product(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4]
                    ));
                }
            }
        }
        return products;
    }

    public static List<Product> loadWithNIO(String filePath) throws IOException {
        List<Product> products = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (int i = 1; i < lines.size(); i++) { // skip header
            String[] parts = lines.get(i).split(",", 5);
            if (parts.length == 5) {
                products.add(new Product(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2],
                    parts[3],
                    parts[4]
                ));
            }
        }
        return products;
    }

    // BufferedReader vs NIO demo
    // Benefits of BufferedReader: fine control over reading, lower memory usage for very large files
    // Benefits of NIO: simpler code, better performance for large files    
    // Trade offs depend on file size and complexity of processing
    // Choose based on specific use case requirements



    public static void main(String[] args) throws IOException {
        String csvPath = "data-files/products.csv";
        testBufferedReader(csvPath);
        testNIO(csvPath);
    }

    private static void testBufferedReader(String filePath) throws IOException {
        System.out.println("Loading with BufferedReader:");
        long startMem = getUsedMemory();
        long startTime = System.nanoTime();
        List<Product> products = loadWithBufferedReader(filePath);
        long endTime = System.nanoTime();
        long endMem = getUsedMemory();
        products.forEach(System.out::println);
        System.out.printf("Time taken (BufferedReader): %.2f ms\n", (endTime - startTime) / 1_000_000.0);
        System.out.printf("Memory used (BufferedReader): %.2f MB\n", (endMem - startMem) / (1024.0 * 1024.0));
    }

    private static void testNIO(String filePath) throws IOException {
        System.out.println("\nLoading with NIO:");
        long startMem = getUsedMemory();
        long startTime = System.nanoTime();
        // Whats Special about NIO ?
        // NIO (New I/O) provides non-blocking I/O operations, memory-mapped files, and better scalability for high-performance applications.
        // It is designed for high throughput and low latency, making it suitable for applications that require
        // high concurrency and large data transfers.
        List<Product> products = loadWithNIO(filePath);
        long endTime = System.nanoTime();
        long endMem = getUsedMemory();
        products.forEach(System.out::println);
        System.out.printf("Time taken (NIO): %.2f ms\n", (endTime - startTime) / 1_000_000.0);
        System.out.printf("Memory used (NIO): %.2f MB\n", (endMem - startMem) / (1024.0 * 1024.0));
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Suggest GC to get more accurate measurement
        return runtime.totalMemory() - runtime.freeMemory();
    }

    // Method for Async loading using CompletableFuture
    // Benefits: Non-blocking, can improve responsiveness in UI applications
    public static CompletableFuture<List<Product>> loadWithNIOAsync(String filePath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return loadWithNIO(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
