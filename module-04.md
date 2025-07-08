# Module 04: JVM Options and Tuning Flags for Performance

Java offers **many command-line options and tuning flags** to optimize the performance of the JVM (Java Virtual Machine). These options control memory allocation, garbage collection behavior, JIT compilation, and runtime diagnostics.

##  1. **Why JVM Tuning Matters**

* Helps reduce **GC pauses** and memory thrashing.
* Improves **application startup time** and **runtime performance**.
* Adjusts behavior based on **application size**, **workload type**, and **latency requirements**.


##  2. **Memory-Related Options**

| Option                        | Description                             |
| ----------------------------- | --------------------------------------- |
| `-Xms<size>`                  | Set **initial heap size**               |
| `-Xmx<size>`                  | Set **maximum heap size**               |
| `-Xmn<size>`                  | Set size of the **young generation**    |
| `-XX:MaxMetaspaceSize=<size>` | Limit metaspace used for class metadata |
| `-Xss<size>`                  | Set **thread stack size**               |

> Example:
> `java -Xms512m -Xmx2g -XX:MaxMetaspaceSize=256m MyApp`

##  3. **Garbage Collection (GC) Options**

| Option                                 | Description                                       |
| -------------------------------------- | ------------------------------------------------- |
| `-XX:+UseG1GC`                         | Use **G1 Garbage Collector** (default in Java 9+) |
| `-XX:+UseParallelGC`                   | Use multi-threaded **parallel collector**         |
| `-XX:+UseZGC` / `-XX:+UseShenandoahGC` | Use **low-latency GC algorithms**                 |
| `-XX:+PrintGCDetails`                  | Print GC activity logs                            |
| `-Xlog:gc*` (Java 9+)                  | Unified logging for GC and memory events          |

> Example:
> `java -XX:+UseG1GC -Xlog:gc* MyApp`

##  4. **JIT Compilation & Performance**

| Option                       | Purpose                                                      |
| ---------------------------- | ------------------------------------------------------------ |
| `-XX:+TieredCompilation`     | Enables a mix of interpreted + JIT code                      |
| `-XX:CompileThreshold=10000` | Changes the number of method invocations before JIT triggers |
| `-XX:+PrintCompilation`      | Prints which methods are being compiled                      |

## 5. **Monitoring & Debugging Options**

| Option                            | Purpose                          |
| --------------------------------- | -------------------------------- |
| `-XX:+PrintGC`                    | Basic GC summary                 |
| `-XX:+HeapDumpOnOutOfMemoryError` | Dumps heap on OOM (for analysis) |
| `-Xdebug -Xrunjdwp`               | Enables remote debugging         |
| `-XX:+UnlockDiagnosticVMOptions`  | Access to experimental options   |

##  6. **Best Practices**

* Always start with **baseline metrics** using default JVM settings.
* Profile your application under **realistic load** to find bottlenecks.
* Apply JVM flags **incrementally**, observing the impact of each.
* Use tools like **VisualVM**, **GCViewer**, or **JFR** to verify results.
* Tune heap size and GC algorithm according to **application type**:

  * **Low-latency API**: Consider ZGC or Shenandoah
  * **Batch processing**: G1GC or ParallelGC may be optimal


## Sample Production-Ready JVM Flags (Java 11+)

```bash
java -Xms512m -Xmx2g \
     -XX:+UseG1GC \
     -XX:+UnlockExperimentalVMOptions \
     -XX:+HeapDumpOnOutOfMemoryError \
     -Xlog:gc* \
     -XX:+PrintGCDetails \
     -XX:+PrintGCTimeStamps \
     -jar myapp.jar
```

# Performance Bottlenecks in Java Applications

Performance bottlenecks are **parts of your application that limit its speed or scalability**. Identifying and resolving these bottlenecks is one of the most critical steps in performance optimization.

##  What is a Bottleneck?

A **bottleneck** is the slowest component in a system that restricts the overall performance.

>  **Think of it as a narrow section in a pipe**—no matter how wide the rest is, everything slows down at that choke point.

### 1. **CPU-bound Bottlenecks**

These occur when the CPU is **fully utilized** by your application, often due to:

* Complex algorithms or calculations
* Excessive object creation
* Inefficient loops
* Unoptimized recursive functions

**Symptoms**:

* High CPU usage
* Low thread wait time (everything’s running)
* Minimal I/O activity

**Tools to detect**:

* `top`, `htop`, `VisualVM`, `JFR`, `JProfiler`


### 2. **IO-bound Bottlenecks**

These occur when the application is **waiting for disk, database, or network** resources.

**Common Causes**:

* Slow database queries
* File or socket read/write delays
* Blocking HTTP requests

**Symptoms**:

* Low CPU usage
* High thread wait/sleep/block times
* Threads waiting on I/O resources

**Tools to detect**:

* Thread dumps (look for `WAITING`, `BLOCKED`)
* JConsole / VisualVM thread monitor
* APM tools (New Relic, AppDynamics)

### 3. **Common Bottleneck Areas in Java**

| Area               | Potential Bottleneck                   | Solution Example                          |
| ------------------ | -------------------------------------- | ----------------------------------------- |
| Garbage Collection | Long GC pause times                    | Tune GC settings, choose right collector  |
| Collections        | Unbounded size or inefficient use      | Use appropriate data structures           |
| Logging            | Synchronous logging blocking threads   | Use async logging (e.g., Logback async)   |
| Database Access    | N+1 queries, unindexed lookups         | Optimize SQL, use batching and indexing   |
| Multithreading     | Deadlocks or contention                | Use thread-safe structures, proper locks  |
| HTTP Calls         | Blocking external API calls            | Use timeouts, retries, and async patterns |
| File I/O           | Large file processing on single thread | Use NIO, buffering, or background threads |


### 4. **Identifying Bottlenecks**

Use a **systematic approach**:

1. Start with real performance issues (logs, SLAs, user reports).
2. Profile the application under realistic load.
3. Use tools like:

   * **JProfiler**, **VisualVM**
   * **GC logs** for memory/GC issues
   * **Thread dumps** for concurrency issues
   * **JMeter** or **wrk** to simulate load


### Summary

* **Not all slowdowns are bugs**—some are architectural design flaws.
* Always **profile before optimizing**.
* Address the **right type of bottleneck** (CPU vs IO).
* Use **multiple tools together** for full visibility.
