# JVM Options and Tuning Flags for Performance

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
