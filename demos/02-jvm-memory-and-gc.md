# Demo 2: JVM Memory and GC Options

This walkthrough explores how different JVM memory and garbage collection (GC) options affect Java application performance.

## Step 1: Prepare a Sample Java Application

You can use the provided `MemoryTestApp.java` in `demos/src/` or any simple Java app that allocates memory and runs for a while.

## Step 2: Run with Default JVM Settings

Open a terminal and run:
```powershell
java -jar MemoryTestApp.jar
```
Observe the startup time, memory usage, and GC behavior (if any output).

## Step 3: Set Heap Size and GC Options

Try running with different heap sizes and garbage collectors:
```powershell
java -Xms512m -Xmx1024m -XX:+UseG1GC -XX:+PrintGCDetails -jar MemoryTestApp.jar
```
- `-Xms`/`-Xmx`: Set initial and max heap size
- `-XX:+UseG1GC`: Use the G1 garbage collector
- `-XX:+PrintGCDetails`: Print detailed GC logs

Try other collectors:
```powershell
java -Xms512m -Xmx1024m -XX:+UseParallelGC -XX:+PrintGCDetails -jar MemoryTestApp.jar
java -Xms512m -Xmx1024m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -jar MemoryTestApp.jar
```

## Step 4: Observe and Compare

- Note differences in GC pause times, memory usage, and log output.
- Try increasing or decreasing heap sizes and see the effect.

## Step 5: Tips
- Use VisualVM or JMC to visualize memory and GC activity.
- Experiment with other flags like `-XX:MaxGCPauseMillis` or `-XX:InitiatingHeapOccupancyPercent`.

## Explanation
JVM flags allow you to tune memory and garbage collection for your application's needs. Different collectors and heap sizes can have a big impact on performance and latency.
