# Demo 5: Trigger and Analyze a Heap Dump

This walkthrough shows how to programmatically trigger a heap dump and analyze it for troubleshooting memory issues.

## Step 1: Use the JVM Management API

Add this code to your Java app:
```java
import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class HeapDumpDemo {
    public static void main(String[] args) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.invoke(
            new ObjectName("com.sun.management:type=HotSpotDiagnostic"),
            "dumpHeap",
            new Object[] {"heapdump.hprof", true},
            new String[] {"java.lang.String", "boolean"}
        );
        System.out.println("Heap dump written to heapdump.hprof");
    }
}
```

## Step 2: Run the Program
```powershell
javac HeapDumpDemo.java
java HeapDumpDemo
```

## Step 3: Analyze the Heap Dump
- Open `heapdump.hprof` in VisualVM or Eclipse MAT.
- Look for memory usage, object counts, and possible leaks.

## Tips
- You can also trigger heap dumps with `jcmd <pid> GC.heap_dump heapdump.hprof`.
- Heap dumps can be large; ensure you have enough disk space.

## Explanation
Heap dumps capture the state of all objects in memory. Analyzing them helps find memory leaks and understand memory usage patterns.
