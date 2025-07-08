# Module 08: Hands-on Java Performance Exercises

These exercises are designed to give you practical experience with Java performance measurement, JVM tuning, memory management, and troubleshooting. Each exercise includes a brief explanation to help you understand the underlying concepts.

## Exercise 1: Measure Performance Metrics
**Goal:** Learn to measure latency, CPU, and memory usage in Java.

**How:**
- Use `System.nanoTime()` to measure code execution time (latency).
- Use `Runtime.getRuntime()` to check memory usage before and after operations.

**Example:**
```java
long start = System.nanoTime();
// ... code to measure ...
long end = System.nanoTime();
System.out.println("Elapsed time: " + (end - start) / 1_000_000 + " ms");

long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
System.out.println("Used memory: " + usedMemory / (1024 * 1024) + " MB");
```

## Exercise 2: Setting JVM Options
**Goal:** Understand how JVM flags affect performance.

**How:**
- Run your app with different memory and GC options.
- Observe changes in startup time, memory usage, and GC logs.

**Example Command:**
```bash
java -Xms512m -Xmx1024m -XX:+UseG1GC -XX:+PrintGCDetails -jar app.jar
```
**Explanation:**
- `-Xms`/`-Xmx`: Set initial/max heap size
- `-XX:+UseG1GC`: Use G1 garbage collector
- `-XX:+PrintGCDetails`: Print detailed GC logs

## Exercise 3: HelloWorld with Memory
**Goal:** Visualize stack vs. heap memory.

**How:**
- Create objects in a simple program and use a debugger or VisualVM to inspect memory allocation.
- Discuss which variables are on the stack (local) and which are on the heap (objects).

## Exercise 4: Simulate a Memory Leak
**Goal:** Understand how memory leaks occur in Java.

**How:**
- Continuously add objects to a list without releasing references.
- Observe increasing memory usage and eventual `OutOfMemoryError`.

**Example:**
```java
List<Object> memoryLeakList = new ArrayList<>();
while (true) memoryLeakList.add(new int[100000]);
```

## Exercise 5: Trigger a Heap Dump
**Goal:** Learn to capture and analyze heap dumps for troubleshooting.

**How:**
- Use the JVM's management API to programmatically trigger a heap dump.
- Analyze the resulting `.hprof` file with Eclipse MAT or VisualVM.

**Example:**
```java
ManagementFactory.getPlatformMBeanServer().invoke(
    new ObjectName("com.sun.management:type=HotSpotDiagnostic"),
    "dumpHeap", new Object[] {"heapdump.hprof", true},
    new String[] {"java.lang.String", "boolean"});
```

## Exercise 6: Other JVM Dumps
**Goal:** Explore additional JVM diagnostics.

**How:**
- Generate and analyze thread dumps (for deadlocks, thread states)
- Create class histograms (to see object counts by class)
- Capture CPU profiling snapshots (to find hotspots)

**Tools:** `jcmd`, `jstack`, VisualVM, JProfiler

## Exercise 7: JVM Options from Java 8 to Java 23
**Goal:** Explore new JVM features and collectors.

**How:**
- Try enabling ZGC (`-XX:+UseZGC`), Shenandoah (`-XX:+UseShenandoahGC`), and native memory tracking (`-XX:NativeMemoryTracking=summary`).
- Compare their impact on pause times and memory usage.

**Explanation:**
Newer JVM versions offer advanced collectors and diagnostics for low-latency and large-scale applications.
