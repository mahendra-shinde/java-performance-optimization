## **Hands-On Demo: JVM Flags in Action**

### 🧾 Step 1: Create a Sample Java App

Create a file named `MemoryTestApp.java`:

```java
public class MemoryTestApp {
    public static void main(String[] args) {
        System.out.println("Starting memory stress test...");

        try {
            for (int i = 0; i < 100; i++) {
                int[] array = new int[10_000_000]; // Allocate memory
                System.out.println("Allocated " + ((i + 1) * 10) + "MB");
                Thread.sleep(1000);
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError occurred!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Test complete.");
    }
}
```

Compile:

```bash
javac MemoryTestApp.java
```


### 🧪 Step 2: Run With Default JVM Settings

```bash
java MemoryTestApp
```

Observe:

* It may crash with `OutOfMemoryError`
* Limited GC visibility

###  Step 3: Run With Custom JVM Flags

```bash
java \
  -Xms128m -Xmx512m \
  -XX:+UseG1GC \
  -XX:+PrintGCDetails \
  -XX:+PrintGCTimeStamps \
  -XX:+HeapDumpOnOutOfMemoryError \
  -Xlog:gc* \
  MemoryTestApp
```

🔍 **Explanation**:

* `-Xms` / `-Xmx`: Control initial and max heap size
* `-XX:+UseG1GC`: Use G1 garbage collector
* `-XX:+PrintGCDetails`: Print detailed GC activity
* `-Xlog:gc*`: (Java 9+) Unified GC logging
* `-XX:+HeapDumpOnOutOfMemoryError`: Generate `.hprof` file if app crashes

### Step 4: Analyze Heap Dump (Optional)

When the app crashes:

* It will generate `java_pid*.hprof`
* Open it using [Eclipse MAT](https://www.eclipse.org/mat/) or **VisualVM**

Look for:

* Memory usage per class
* Retained sizes
* Dominator trees (memory retention paths)


### Bonus: Try Different GC Algorithms

```bash
# Parallel GC (Throughput-oriented)
java -Xmx512m -XX:+UseParallelGC MemoryTestApp

# ZGC (Low-pause GC, Java 15+)
java -Xmx512m -XX:+UseZGC MemoryTestApp

# Shenandoah GC (RedHat builds)
java -Xmx512m -XX:+UseShenandoahGC MemoryTestApp
```

### Optional Tools for Visualization

Use one of these for real-time monitoring:

* **VisualVM** (`jvisualvm` from JDK/bin)
* **JConsole**
* **JFR (Java Flight Recorder)**

## What You’ll Learn

* How GC logs change based on algorithm
* When and how `OutOfMemoryError` occurs
* How to interpret heap usage patterns
* How tuning heap size changes behavior

