# Java Performance Optimization 

## 1.  Importance of Performance Optimization

* Efficient applications result in better **user experience**, **resource usage**, and **cost savings**.
* Performance impacts scalability, responsiveness, and maintainability.

## 2. Key Performance Metrics

| Metric        | Description                                         |
| ------------- | --------------------------------------------------- |
| Latency       | Time taken to respond to a single request.          |
| Throughput    | Number of operations/transactions per unit of time. |
| Response Time | Time from request initiation to completion.         |
| CPU Usage     | Percentage of CPU cycles used by the application.   |
| Memory Usage  | Heap and non-heap memory used by the JVM.           |

## 3. Understanding the JVM

### JVM Architecture and Execution Model

* Class Loader
* Runtime Data Areas: Method Area, Heap, Stack, PC Register, Native Method Stack
* Execution Engine: Interpreter, JIT Compiler
* Garbage Collector (GC)

### Memory Management

* **Heap**: Stores objects and class instances.
* **Stack**: Stores local variables and method call frames.
* **GC**: Automatically reclaims unused objects.


### JVM Options and Tuning Flags

Examples:

* `-Xms`, `-Xmx`: Set heap size.
* `-XX:+UseG1GC`: Use G1 garbage collector.
* `-XX:+PrintGCDetails`: Enable verbose GC logging.


## 4. Performance Bottlenecks

### Common Bottlenecks

* Poor choice of algorithms/data structures.
* Inefficient file I/O.
* Synchronous blocking calls.
* Inefficient use of memory or threads.

### CPU-Bound vs I/O-Bound

* **CPU-bound**: Optimized by improving algorithms or concurrency.
* **I/O-bound**: Optimized by async I/O, caching, or faster networks/disks.

## 5.  Garbage Collection (GC)

* **GC Architecture**: Young, Old (Tenured), and PermGen/Metaspace.
* **Algorithms**:

  * Serial
  * Parallel GC
  * CMS
  * G1 GC
  * ZGC (Java 11+)
  * Shenandoah (Java 12+)
* **Monitoring**: `jstat`, `VisualVM`, `GCViewer`, `MAT`.


## 6. Key Areas for Enterprise Tuning

### Database Optimization

* Connection pooling (HikariCP)
* Prepared statements
* Query optimization (indexes, joins)

### Caching

* In-memory: EhCache, Caffeine
* Distributed: Redis, Hazelcast

### Web App Tuning

* Connection reuse
* Compression
* Lazy loading

### Asynchronous Processing

* CompletableFuture
* ExecutorService
* Message queues (Kafka, RabbitMQ)

### Microservices & Scalability

* API Gateway
* Load balancing
* Service mesh (Istio)

## 7. General Tools for Performance Tuning

| Tool          | Purpose                               |
| ------------- | ------------------------------------- |
| **JProfiler** | CPU/memory profiling, thread analysis |
| **GCViewer**  | Analyze GC logs                       |
| **VisualVM**  | Live JVM monitoring                   |
| **JConsole**  | Runtime monitoring                    |
| **JMeter**    | Load testing                          |

## 8.  Hands-on Exercises

| Exercise No. | Title                                   | Description                                                                                   |
| ------------ | --------------------------------------- | --------------------------------------------------------------------------------------------- |
| 1            | JVM Flags and Performance               | Explore how JVM flags affect performance, memory, and GC behavior.                            |
| 2            | JVM Memory and GC Options               | Experiment with heap sizes and different garbage collectors.                                  |
| 3            | Stack vs. Heap Memory Visualization     | Visualize and understand the difference between stack and heap memory in Java.                |
| 4            | Simulate a Memory Leak                  | Create a memory leak in Java and observe its effects.                                         |
| 5            | Trigger and Analyze a Heap Dump         | Programmatically trigger a heap dump and analyze it for memory issues.                        |
| 6            | JVM Diagnostic Dumps                    | Generate and analyze thread dumps, class histograms, and CPU profiling snapshots.             |
| 7            | Exploring New JVM Features              | Try ZGC, Shenandoah, and Native Memory Tracking in recent JVM versions.                       |

_See the [Demos](./demos/) folder for detailed walkthroughs of each exercise._



## 9. HPROF and Memory Analysis

* Analyze `.hprof` files with **Eclipse MAT** or **VisualVM**
* Identify **collection-level memory leaks**
* Detect retained object chains and memory dominators

## 10. Performance Design Patterns and Java APIs

| Area           | Pattern/API Suggestions                                          |
| -------------- | ---------------------------------------------------------------- |
| Strings        | Use `StringBuilder`, avoid concatenation in loops                |
| Optional       | Use to avoid null checks                                         |
| If-Else        | Replace with `Map` or `Strategy Pattern`                         |
| Date-Time      | Use `java.time` (JSR-310) for better performance and safety      |
| Atomic Classes | `AtomicInteger`, `AtomicReference` for lock-free synchronization |
| Concurrency    | `ExecutorService`, `ForkJoinPool`, `CompletableFuture`           |
| Collections    | Prefer `ArrayList` over `LinkedList`, use immutable collections  |

## 11. Concurrency & Multithreading

### Synchronization

* `synchronized` blocks
* `ReentrantReadWriteLock`
* `java.util.concurrent` package
* Avoid contention and deadlocks

## 12. JDBC and I/O

### JDBC Tuning

* Use connection pooling
* Batch inserts
* Indexing

### File and NIO

* Use `java.nio` for non-blocking I/O
* Buffered I/O streams

## 13. HTTP and Web Tuning

* Connection pooling in HTTP clients
* Caching headers
* GZIP compression
* Avoid blocking operations in web endpoints

## 14. Telemetry & Observability

### Introduction to Telemetry

* Metrics, Traces, Logs = Telemetry pillars
* Use tools like **OpenTelemetry**, **Prometheus**, **Micrometer**

### Implementing Telemetry

* Instrument code using Java SDKs
* Auto-instrumentation agents (for Spring Boot, etc.)

## 15. Final Best Practices

* Profile before optimizing
* Avoid premature optimization
* Minimize object creation
* Reuse resources
* Monitor in production
