# Demo 4: Simulate a Memory Leak in Java

This walkthrough demonstrates how a memory leak can occur in Java and how to observe its effects.

## Step 1: Write a Memory Leak Program

Create a file `MemoryLeakDemo.java`:
```java
import java.util.*;
public class MemoryLeakDemo {
    public static void main(String[] args) {
        List<Object> memoryLeakList = new ArrayList<>();
        while (true) {
            memoryLeakList.add(new int[100000]);
        }
    }
}
```

## Step 2: Run the Program
```powershell
javac MemoryLeakDemo.java
java MemoryLeakDemo
```

## Step 3: Observe Memory Usage
- Use Task Manager, VisualVM, or `jcmd` to monitor memory usage.
- The program will eventually throw `OutOfMemoryError`.

## Step 4: Analyze the Heap Dump (Optional)
- Run with `-XX:+HeapDumpOnOutOfMemoryError` to get a heap dump for analysis:
```powershell
java -XX:+HeapDumpOnOutOfMemoryError MemoryLeakDemo
```
- Open the `.hprof` file in VisualVM or Eclipse MAT.

## Explanation
Memory leaks in Java happen when objects are unintentionally kept alive, preventing garbage collection. This demo shows how holding references in a growing list leads to an eventual crash.
