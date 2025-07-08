# Demo 6: JVM Diagnostic Dumps (Threads, Classes, CPU)

This walkthrough explores various JVM diagnostic dumps for troubleshooting and performance analysis.

## Step 1: Generate a Thread Dump
- Use `jcmd` or `jstack` to capture thread states:
```powershell
jcmd <pid> Thread.print
# or
jstack <pid>
```
- Analyze for deadlocks, blocked threads, or high CPU usage.

## Step 2: Create a Class Histogram
- See object counts by class:
```powershell
jcmd <pid> GC.class_histogram
```

## Step 3: Capture a CPU Profiling Snapshot
- Use VisualVM or JProfiler to record CPU usage and hotspots.
- With `jcmd`:
```powershell
jcmd <pid> VM.native_memory summary
```

## Step 4: Analyze the Results
- Look for threads stuck or waiting.
- Identify classes with high object counts.
- Find methods consuming most CPU time.

## Tips
- Replace `<pid>` with your Java process ID (find with `jps`).
- Many tools (VisualVM, JMC, JProfiler) provide graphical views.

## Explanation
JVM diagnostic dumps are essential for troubleshooting deadlocks, memory leaks, and performance bottlenecks in production and development.
