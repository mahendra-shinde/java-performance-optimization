# Demo 7: Exploring New JVM Features (Java 8 to 23)

This walkthrough demonstrates how to use advanced JVM collectors and diagnostics available in newer Java versions.

## Step 1: Try ZGC and Shenandoah Collectors
- Run your app with ZGC (Java 11+):
```powershell
java -XX:+UseZGC -jar MemoryTestApp.jar
```
- Or with Shenandoah (Java 12+):
```powershell
java -XX:+UseShenandoahGC -jar MemoryTestApp.jar
```

## Step 2: Enable Native Memory Tracking (NMT)
- Run with NMT enabled:
```powershell
java -XX:NativeMemoryTracking=summary -jar MemoryTestApp.jar
```
- Use `jcmd <pid> VM.native_memory summary` to view native memory usage.

## Step 3: Compare Results
- Observe GC pause times, memory usage, and logs.
- Compare with G1GC or ParallelGC for your workload.

## Tips
- ZGC and Shenandoah are designed for low-latency, large-heap applications.
- NMT helps diagnose native memory leaks and off-heap usage.
- Always check your Java version supports these options.

## Explanation
New JVM versions offer advanced collectors and diagnostics for low-latency and large-scale applications. Experimenting with these features helps you choose the best options for your needs.
