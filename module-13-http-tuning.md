# Module 13: HTTP and Web Application Tuning

**1. Connection Pooling in HTTP Clients:**
  - Use HTTP clients (like Apache HttpClient, OkHttp) that support connection pooling to reuse TCP connections, reducing latency and resource usage.
  - Tune pool size based on expected concurrency.

**2. Caching Headers:**
  - Set appropriate HTTP caching headers (`Cache-Control`, `ETag`, `Last-Modified`) to enable client and proxy caching, reducing server load and response times.

**3. GZIP Compression:**
  - Enable GZIP or Brotli compression for HTTP responses to reduce payload size and improve transfer speed, especially for text-based content (HTML, CSS, JS, JSON).

**4. Avoid Blocking Operations in Web Endpoints:**
  - Use asynchronous request handling (e.g., Servlet 3.1 async, Spring WebFlux) to prevent thread starvation.
  - Offload long-running or blocking tasks to background workers or message queues.

**5. Minimize Payloads:**
  - Only send necessary data in responses. Use pagination and filtering for large datasets.


**6. Monitor and Profile:**
  - Use tools like Prometheus, Grafana, or New Relic to monitor HTTP metrics (latency, throughput, error rates).
  - Profile endpoints using tools like JProfiler, VisualVM, or Spring Boot Actuator to identify bottlenecks.

**7. Use HTTP/2 or HTTP/3:**
  - Upgrade to HTTP/2 or HTTP/3 to benefit from multiplexing, header compression, and reduced latency.

**8. Tune Thread Pools:**
  - Configure web server thread pools (Tomcat, Jetty, Undertow) based on expected load. Avoid too many or too few threads.

**9. Optimize Database Access:**
  - Use connection pooling (e.g., HikariCP) and batch queries to reduce HTTP endpoint response times.

**10. Secure and Validate Inputs:**
  - Always validate and sanitize HTTP request data to prevent security vulnerabilities and reduce unnecessary processing.

---

# Example Code Snippets

## 1. Connection Pooling with Apache HttpClient
```java
PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
cm.setMaxTotal(100);
cm.setDefaultMaxPerRoute(20);
CloseableHttpClient httpClient = HttpClients.custom()
    .setConnectionManager(cm)
    .build();
```

## 2. Setting Caching Headers in a Servlet
```java
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setHeader("Cache-Control", "max-age=3600, must-revalidate");
    resp.setHeader("ETag", "\"v1.0\"");
    // ...existing code...
}
```

## 3. Enabling GZIP Compression in Spring Boot
```properties
# application.properties
server.compression.enabled=true
server.compression.mime-types=application/json,application/xml,text/html,text/xml,text/plain
server.compression.min-response-size=1024
```

## 4. Asynchronous Endpoint with Spring WebFlux
```java
@RestController
public class DemoController {
    @GetMapping("/async-data")
    public Mono<String> getAsyncData() {
        return Mono.fromSupplier(() -> fetchData());
    }
    // ...existing code...
}
```

## 5. Pagination Example
```java
@GetMapping("/products")
public List<Product> getProducts(@RequestParam int page, @RequestParam int size) {
    return productService.getProducts(page, size);
}
```

---

**Summary:**

HTTP and web application tuning involves optimizing connection management, payload size, server configuration, and monitoring. Use modern protocols, efficient thread and connection pools, and always monitor your endpoints for performance and errors. Apply caching and compression to reduce load and improve user experience.
