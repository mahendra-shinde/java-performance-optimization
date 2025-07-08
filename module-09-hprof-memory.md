# Module 09: HPROF Files and In-Depth Memory Analysis

Memory analysis is crucial for diagnosing memory leaks, high memory usage, and understanding object retention in Java applications. The JVM can generate heap dump files (usually with a `.hprof` extension) that capture the entire state of the heap at a point in time.

## What is a Heap Dump (`.hprof` file)?
A heap dump is a snapshot of all objects in the JVM heap. It includes information about object types, references, sizes, and the relationships between objects.

## How to Analyze Heap Dumps

### 1. Using Eclipse MAT (Memory Analyzer Tool)
- **Open the `.hprof` file** in Eclipse MAT.
- **Find memory leaks**: Use the "Leak Suspects" report to identify objects that are retaining large amounts of memory.
- **Analyze dominator trees**: See which objects are preventing others from being garbage collected.
- **Trace reference chains**: Understand why certain objects are still reachable.

### 2. Using VisualVM
- **Open the heap dump** in VisualVM.
- **Browse objects by class**: See which classes consume the most memory.
- **Find GC roots**: Identify why objects are not being collected.
- **Compare heap dumps**: Track memory usage over time.
