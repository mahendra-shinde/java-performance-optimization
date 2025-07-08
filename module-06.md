# Garbage Collection (GC)**

Garbage Collection (GC) in Java is a **process of automatic memory management**. The Java Virtual Machine (JVM) performs garbage collection to reclaim memory occupied by objects that are no longer reachable in the application.

## Why is GC Important?

* Prevents **memory leaks**.
* Eliminates the need for manual memory deallocation (unlike C/C++).
* Ensures **heap space is available** for new object allocations.
* If GC is not tuned, it can cause **latency spikes**, **OutOfMemoryErrors**, or poor performance.


##  Basic GC Process

1. **Mark**: Identify live (reachable) objects.
2. **Sweep**: Reclaim memory used by unreachable (dead) objects.
3. **Compact** (optional): Rearranging objects to reduce fragmentation.


##  JVM Memory Model and GC Impact

| Memory Region        | Collected? | Description                                   |
| -------------------- | ---------- | --------------------------------------------- |
| **Young Generation** | ✅          | Short-lived objects; frequent GC (Minor GC)   |
| **Old Generation**   | ✅          | Long-lived objects; infrequent GC (Major GC)  |
| **Metaspace**        | ✅          | Class metadata (Java 8+); GC’d based on usage |


## Types of Garbage Collectors

| GC Type                         | Use Case                        | Pros                                 | Cons                                  |
| ------------------------------- | ------------------------------- | ------------------------------------ | ------------------------------------- |
| **Serial GC**                   | Small apps, single-core systems | Low overhead                         | Pauses entire application             |
| **Parallel GC**                 | High-throughput batch jobs      | Multithreaded collection             | Longer pause times                    |
| **CMS (deprecated)**            | Low-pause interactive apps      | Concurrent collection                | Fragmentation, deprecated             |
| **G1GC (default from Java 9+)** | Balanced workloads              | Predictable pause time, region-based | More complex to tune                  |
| **ZGC** / **Shenandoah**        | Low-latency applications        | Millisecond-level pause times        | Experimental, higher memory footprint |

## GC Tuning Options

| Option                            | Description                       |
| --------------------------------- | --------------------------------- |
| `-Xms<size>` / `-Xmx<size>`       | Set min/max heap size             |
| `-XX:+UseG1GC`                    | Use G1 collector (modern default) |
| `-XX:+PrintGCDetails`             | Print detailed GC info            |
| `-Xlog:gc*`                       | Unified GC logging in Java 9+     |
| `-XX:+HeapDumpOnOutOfMemoryError` | Create heap dump when OOM occurs  |

## Monitoring GC

Use these tools:

* **VisualVM**, **JConsole**, **JFR**
* **GCViewer** for analyzing GC logs
* **Eclipse MAT** for memory dumps

## Best Practices

* **Don’t over-allocate heap** — can slow down GC.
* **Avoid object churn** (e.g., new objects in tight loops).
* Use **object pools** or reuse objects when possible.
* Choose the right GC algorithm for your workload:

  * **Low latency**: ZGC, Shenandoah
  * **Throughput**: ParallelGC
  * **Balanced**: G1GC

##  Sample JVM Flags for GC Tuning

```bash
java -Xms512m -Xmx2g \
     -XX:+UseG1GC \
     -Xlog:gc* \
     -XX:+PrintGCDetails \
     -XX:+HeapDumpOnOutOfMemoryError \
     -jar myapp.jar
```
