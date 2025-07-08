# Demo 6: JVM Diagnostic Dumps (Threads, Classes, CPU)

This walkthrough provides a detailed guide to capturing and analyzing various JVM diagnostic dumps for troubleshooting and performance optimization.

## Prerequisites

Before starting, ensure you have the following:

- **Java Development Kit (JDK)** installed (version 8 or higher recommended)
- Access to the target Java application's process (PID)
- Tools available in your `PATH`:
  - `jps` (Java Process Status tool)
  - `jcmd` (Java Diagnostic Command tool)
  - `jstack` (Java Stack Trace tool)
- (Optional) Heap/CPU analysis tools:
  - [VisualVM](https://visualvm.github.io/)
  - [Java Mission Control (JMC)](https://www.oracle.com/java/technologies/javamc.html)
  - [Eclipse Memory Analyzer (MAT)](https://www.eclipse.org/mat/)

## Step 1: Identify the Java Process

First, find the process ID (PID) of your Java application:

```powershell
jps -l
```

## Step 2: Generate a Thread Dump

A thread dump shows the state of all threads in the JVM. This is useful for diagnosing deadlocks, thread contention, and high CPU usage.

**Using `jcmd`:**
```powershell
jcmd <pid> Thread.print
```

**Using `jstack`:**
```powershell
jstack <pid>
```

**What to look for:**
- Threads stuck in `BLOCKED` or `WAITING` state
- Deadlocks (look for `Found one Java-level deadlock`)
- Threads consuming high CPU (look for repeated stack traces)

## Step 3: Create a Class Histogram

A class histogram shows the number of objects per class and their memory usage. This helps identify memory leaks or excessive object creation.

**Command:**
```powershell
jcmd <pid> GC.class_histogram
```

**What to look for:**
- Classes with unexpectedly high object counts
- Large memory usage by specific classes

## Step 4: Capture a Heap Dump (Optional)

A heap dump is a snapshot of all objects in memory. Analyze it with tools like Eclipse MAT or VisualVM.

**Command:**
```powershell
jcmd <pid> GC.heap_dump <path-to-heap-dump.hprof>
```

## Step 5: Capture a CPU Profiling Snapshot

CPU profiling helps identify methods consuming the most CPU time.

**With VisualVM or JProfiler:**
- Attach to the running JVM and start CPU profiling.

**With `jcmd` (native memory summary):**
```powershell
jcmd <pid> VM.native_memory summary
```

## Step 6: Analyze the Results

- **Thread Dump:** Look for deadlocks, blocked threads, or threads in tight loops.
- **Class Histogram:** Identify classes with high object counts or memory usage.
- **Heap Dump:** Use a heap analyzer to find memory leaks or large object graphs.
- **CPU Profiling:** Find methods or threads consuming excessive CPU.

## Tips & Best Practices

- Always replace `<pid>` with your Java process ID (use `jps` to find it).
- Take multiple thread dumps a few seconds apart to spot patterns.
- Use graphical tools (VisualVM, Java Mission Control, JProfiler) for deeper analysis.
- Heap dumps can be large; ensure enough disk space.
- Never take heap dumps on production without considering impact (can pause the JVM).

## Further Reading

- [VisualVM Documentation](https://visualvm.github.io/)
- [Java Mission Control](https://www.oracle.com/java/technologies/javamc.html)
- [Eclipse Memory Analyzer (MAT)](https://www.eclipse.org/mat/)

## Explanation

JVM diagnostic dumps are essential for troubleshooting deadlocks, memory leaks, and performance bottlenecks. By combining thread dumps, class histograms, heap dumps, and CPU profiling, you can systematically identify and resolve issues in both development and production environments.