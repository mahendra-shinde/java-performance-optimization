package jvmperf;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String csvPath = ".." + File.separator + "data-files" + File.separator + "products.csv";
        List<String[]> products = loadCSV(csvPath);
        System.out.println("Loaded " + products.size() + " products.");

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> results = new ArrayList<>();

        // Split work into chunks for each thread
        int chunkSize = (products.size() + numThreads - 1) / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, products.size());
            if (start >= end) break;
            List<String[]> sublist = products.subList(start, end);
            results.add(executor.submit(() -> processProducts(sublist)));
        }

        int total = 0;
        for (Future<Integer> f : results) {
            total += f.get();
        }
        executor.shutdown();
        System.out.println("Total processed: " + total);
    }

    // Simulate processing each product (e.g., sum prices, count, etc.)
    private static int processProducts(List<String[]> products) {
        int processed = 0;
        for (String[] row : products) {
            // Simulate some CPU work
            if (row.length > 1) {
                try {
                    double price = Double.parseDouble(row[1]);
                    double result = Math.sqrt(price) * Math.log(price + 1);
                    if (result > 0) processed++;
                } catch (NumberFormatException e) {
                    // skip header or bad data
                }
            }
        }
        return processed;
    }

    // Simple CSV loader
    private static List<String[]> loadCSV(String path) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        }
        return rows;
    }
}
