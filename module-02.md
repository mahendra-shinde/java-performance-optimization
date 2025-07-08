# Module 02: Key Performance Metrics

Understanding key performance metrics is foundational to optimizing Java applications. These metrics help you **identify bottlenecks**, **measure improvements**, and **set performance baselines**.

### 1. **Latency**

* **Definition**: Time taken to begin responding to a request.
* **Example**: If an HTTP request is sent at 12:00:00 and the server starts responding at 12:00:01, the latency is 1 second.
* **Why it matters**: High latency leads to user-perceived slowness.

> **Measure using**: `System.nanoTime()`, JMeter, VisualVM, application logs.


### 2. **Throughput**

* **Definition**: Number of transactions/operations handled per unit time (e.g., requests/sec).
* **Example**: An API serving 500 requests per second has a throughput of 500 RPS.
* **Why it matters**: Indicates system capacity and scalability.

> **Measure using**: JMeter, Prometheus, custom counters.


### 3. **Response Time**

* **Definition**: Total time taken to process and complete a request.
* **Includes**: Latency + Execution Time + I/O + Network overhead.
* **Why it matters**: A key metric for Service Level Agreements (SLAs).

> **Measure using**: HTTP client timers, APM tools, logs with timestamps.


### 4. **CPU Usage**

* **Definition**: The percentage of processor capacity being used.
* **High CPU usage**: May indicate tight loops, excessive object creation, or inefficient algorithms.
* **Low CPU usage**: May indicate I/O wait or under-utilization.

> **Tools**: `top`, `htop`, VisualVM, JConsole, JFR (Java Flight Recorder)

### 5. **Memory Usage**

* **Heap Memory**: Used for object allocation.
* **Non-Heap (Metaspace)**: Class metadata, interned strings, etc.
* **Signs of issues**:

  * **Memory leak**: Gradual memory increase.
  * **GC thrashing**: Frequent minor/major collections affecting performance.

> **Tools**: `jstat`, VisualVM, Eclipse MAT, GC logs, `Runtime.getRuntime()`

### Summary Table

| Metric        | Measures                  | Tools Used                  | Common Bottlenecks                  |
| ------------- | ------------------------- | --------------------------- | ----------------------------------- |
| Latency       | Delay to start processing | Logs, APMs, nanoTime()      | Network, slow startups              |
| Throughput    | Ops/sec                   | JMeter, Prometheus, Grafana | Thread limitations, DB locks        |
| Response Time | Total time for a response | Logs, timers, APMs          | GC pauses, slow I/O                 |
| CPU Usage     | Processor load            | VisualVM, JFR, OS tools     | Infinite loops, bad algorithms      |
| Memory Usage  | RAM consumption           | MAT, VisualVM, jstat        | Memory leaks, unbounded collections |


### Why Use These Metrics?

* Helps identify whether an app is **CPU-bound**, **memory-bound**, or **I/O-bound**.
* Enables **data-driven tuning** instead of guesswork.
* Validates improvements after optimization.