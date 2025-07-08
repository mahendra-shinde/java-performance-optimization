# Module 05: Garbage Collection (GC)

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
