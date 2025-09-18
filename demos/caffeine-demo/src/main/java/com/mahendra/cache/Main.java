package com.mahendra.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create a Caffeine cache with max size 100 and expire after 10 seconds
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a key (or 'exit' to quit): ");
            String key = scanner.nextLine();
            if (key.equalsIgnoreCase("exit")) break;

            String value = cache.getIfPresent(key);
            if (value == null) {
                value = expensiveOperation(key);
                cache.put(key, value);
                System.out.println("Cache MISS. Computed and stored: " + value);
            } else {
                System.out.println("Cache HIT. Value: " + value);
            }
        }
        scanner.close();
    }

    // Simulate an expensive operation
    private static String expensiveOperation(String key) {
        try {
            Thread.sleep(1000); // Simulate delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Value_for_" + key;
    }
}
