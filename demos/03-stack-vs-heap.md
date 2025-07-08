# Demo 3: Stack vs. Heap Memory Visualization

This walkthrough helps you understand the difference between stack and heap memory in Java.

## Step 1: Write a Simple Java Program

Create a file `StackHeapDemo.java`:
```java
public class StackHeapDemo {
    public static void main(String[] args) {
        int localVar = 42; // Stack
        String heapObj = new String("Hello, Heap!"); // Heap
        System.out.println(localVar + ", " + heapObj);
    }
}
```

## Step 2: Run the Program
```powershell
javac StackHeapDemo.java
java StackHeapDemo
```

## Step 3: Inspect Memory with a Debugger or VisualVM
- Set a breakpoint in `main` and inspect variables:
  - `localVar` is a primitive on the stack.
  - `heapObj` is a reference on the stack, but the actual `String` object is on the heap.
- Use VisualVM or another profiler to see heap allocations.

## Step 4: Experiment
- Create more objects and arrays in your code.
- Observe which variables are on the stack and which are on the heap.

## Explanation
- **Stack:** Stores local variables and method call frames. Fast access, but limited size.
- **Heap:** Stores objects and arrays. Managed by the garbage collector.

Understanding this distinction helps with debugging and optimizing memory usage.
