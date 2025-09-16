# JVM Performance Demo

This is a simple Java application for demonstrating JVM performance optimization techniques. It loads product data from a CSV file and processes it using multiple threads.

## How to Run

1. Make sure you have Java (JDK 8+) installed.
2. Compile:
   ```sh
   javac -d out src/Main.java
   ```
3. Run:
   ```sh
   java -cp out jvmperf.Main
   ```

## What It Does
- Loads product data from `../data-files/products.csv`
- Splits the data among available CPU cores
- Processes each chunk in a separate thread
- Simulates CPU work for each product

## Use Cases
- Demonstrate JVM flags (e.g., -Xmx, -Xms, -XX:+UseG1GC)
- Observe thread behavior and CPU utilization
- Experiment with memory and GC tuning

---

Feel free to modify the workload or add more complex processing for advanced demos.
