# Key Areas for Enterprise Performance Tuning

## Overview
Enterprise Java applications face unique performance challenges due to scale, complexity, and integration requirements. This module covers critical optimization areas that can dramatically improve application performance and scalability.

## 1. Database Optimization

### Connection Pooling
**Why it matters:** Database connections are expensive to create and destroy. Poor connection management can become a major bottleneck.

#### HikariCP Configuration
```java
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:postgresql://localhost/mydb");
config.setUsername("user");
config.setPassword("password");
config.setMaximumPoolSize(20);           // Max connections
config.setMinimumIdle(5);                // Minimum idle connections
config.setConnectionTimeout(30000);      // 30 seconds
config.setIdleTimeout(600000);           // 10 minutes
config.setMaxLifetime(1800000);          // 30 minutes
config.setLeakDetectionThreshold(60000); // 1 minute
```

**Best Practices:**
- Monitor connection pool metrics (active, idle, waiting)
- Size pools based on CPU cores and workload: `pool_size = cores * 2 + disk_count`
- Use connection validation queries sparingly
- Set appropriate timeouts to prevent resource exhaustion

### Prepared Statements
**Performance Impact:** Up to 50% improvement in query execution time

```java
// BAD: Statement concatenation (SQL injection risk + poor performance)
String sql = "SELECT * FROM users WHERE id = " + userId;

// GOOD: Prepared statement (secure + cached execution plan)
String sql = "SELECT * FROM users WHERE id = ?";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setLong(1, userId);
```

**Benefits:**
- Query plan caching reduces parsing overhead
- Parameter binding prevents SQL injection
- Reduced network traffic with batch operations

### Query Optimization Strategies

#### Index Optimization
```sql
-- Create composite indexes for common query patterns
CREATE INDEX idx_user_status_created ON users(status, created_at);

-- Use partial indexes for filtered queries
CREATE INDEX idx_active_users ON users(email) WHERE status = 'ACTIVE';

-- Monitor index usage
EXPLAIN (ANALYZE, BUFFERS) SELECT * FROM users WHERE status = 'ACTIVE';
```

#### Join Optimization
- Use appropriate join types (INNER vs LEFT JOIN)
- Ensure join conditions are indexed
- Consider denormalization for read-heavy scenarios
- Use `EXPLAIN PLAN` to analyze query execution

#### Pagination Best Practices
```java
// BAD: OFFSET-based pagination (performance degrades with large offsets)
SELECT * FROM orders ORDER BY created_at OFFSET 10000 LIMIT 20;

// GOOD: Cursor-based pagination
SELECT * FROM orders WHERE created_at > ? ORDER BY created_at LIMIT 20;
```

## 2. Caching Strategies

### In-Memory Caching

#### Caffeine Cache (High Performance)
```java
Cache<String, User> userCache = Caffeine.newBuilder()
    .maximumSize(10000)
    .expireAfterWrite(Duration.ofMinutes(30))
    .recordStats()
    .build();

// Asynchronous loading
AsyncLoadingCache<String, User> asyncCache = Caffeine.newBuilder()
    .maximumSize(10000)
    .expireAfterWrite(Duration.ofMinutes(30))
    .buildAsync(key -> userService.findById(key));
```

**Configuration Best Practices:**
- Set appropriate max size based on heap memory
- Use time-based eviction for data freshness
- Enable statistics monitoring
- Consider refresh-ahead pattern for hot data

#### EhCache Integration
```xml
<cache name="userCache"
       maxEntriesLocalHeap="10000"
       timeToLiveSeconds="1800"
       timeToIdleSeconds="600"
       memoryStoreEvictionPolicy="LRU">
</cache>
```

### Distributed Caching

#### Redis Configuration
```java
@Configuration
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
    
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(30))
            .serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        
        return RedisCacheManager.builder(jedisConnectionFactory())
            .cacheDefaults(config)
            .build();
    }
}
```

#### Hazelcast for In-Memory Data Grid
```java
Config config = new Config();
config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true)
    .addMember("192.168.1.100").addMember("192.168.1.101");

HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(config);
IMap<String, User> userMap = hazelcast.getMap("users");
```

**Cache Patterns:**
- **Cache-Aside:** Application manages cache
- **Write-Through:** Write to cache and database simultaneously
- **Write-Behind:** Asynchronous database writes
- **Refresh-Ahead:** Proactive cache warming

## 3. Web Application Tuning

### HTTP Connection Management

#### Connection Reuse (HTTP Keep-Alive)
```java
// Configure HttpClient for connection reuse
HttpClient client = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(10))
    .build();

// Connection pooling with Apache HttpClient
PoolingHttpClientConnectionManager connectionManager = 
    new PoolingHttpClientConnectionManager();
connectionManager.setMaxTotal(200);
connectionManager.setDefaultMaxPerRoute(50);

CloseableHttpClient httpClient = HttpClients.custom()
    .setConnectionManager(connectionManager)
    .build();
```

**Benefits:**
- Reduces TCP handshake overhead
- Improves response times by 20-50%
- Better resource utilization

### Compression Strategies

#### Gzip Compression
```java
// Spring Boot configuration
server.compression.enabled=true
server.compression.mime-types=text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json
server.compression.min-response-size=1024
```

#### Brotli Compression (Better than Gzip)
```java
@Configuration
public class CompressionConfig {
    
    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(connector -> {
            connector.setProperty("compression", "on");
            connector.setProperty("compressionMinSize", "1024");
            connector.setProperty("compressibleMimeType", 
                "text/html,text/xml,text/plain,text/css,application/json,application/javascript");
        });
        return tomcat;
    }
}
```

### Lazy Loading Optimization

#### JPA Lazy Loading Best Practices
```java
// Use @BatchSize to reduce N+1 queries
@Entity
public class User {
    @OneToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 25)
    private Set<Order> orders;
}

// Use JOIN FETCH for known associations
@Query("SELECT u FROM User u JOIN FETCH u.orders WHERE u.status = :status")
List<User> findActiveUsersWithOrders(@Param("status") UserStatus status);

// Use Entity Graphs for complex scenarios
@EntityGraph(attributePaths = {"orders", "address"})
@Query("SELECT u FROM User u WHERE u.id = :id")
User findUserWithDetails(@Param("id") Long id);
```

#### Frontend Lazy Loading
```javascript
// Image lazy loading
const imageObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            const img = entry.target;
            img.src = img.dataset.src;
            imageObserver.unobserve(img);
        }
    });
});

document.querySelectorAll('img[data-src]').forEach(img => {
    imageObserver.observe(img);
});
```

## 4. Asynchronous Processing

### CompletableFuture Patterns

#### Parallel Processing
```java
public CompletableFuture<OrderSummary> processOrder(Order order) {
    CompletableFuture<Void> validatePayment = CompletableFuture
        .runAsync(() -> paymentService.validate(order.getPayment()), executorService);
    
    CompletableFuture<Void> checkInventory = CompletableFuture
        .runAsync(() -> inventoryService.reserve(order.getItems()), executorService);
    
    CompletableFuture<Void> notifyCustomer = CompletableFuture
        .runAsync(() -> notificationService.sendConfirmation(order), executorService);
    
    return CompletableFuture.allOf(validatePayment, checkInventory, notifyCustomer)
        .thenApply(v -> orderSummaryService.generateSummary(order));
}
```

#### Error Handling and Timeouts
```java
public CompletableFuture<String> fetchDataWithTimeout() {
    return CompletableFuture
        .supplyAsync(() -> externalService.getData(), executorService)
        .orTimeout(5, TimeUnit.SECONDS)
        .handle((result, throwable) -> {
            if (throwable != null) {
                if (throwable instanceof TimeoutException) {
                    return "DEFAULT_VALUE";
                }
                throw new RuntimeException("Service unavailable", throwable);
            }
            return result;
        });
}
```

### ExecutorService Configuration

#### Thread Pool Sizing
```java
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // Core pool size: Number of threads to keep alive
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        
        // Max pool size: Maximum number of threads
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        
        // Queue capacity: Tasks waiting for execution
        executor.setQueueCapacity(500);
        
        // Thread name prefix for debugging
        executor.setThreadNamePrefix("async-executor-");
        
        // Rejection policy when queue is full
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        
        executor.initialize();
        return executor;
    }
}
```

#### Custom Thread Pools
```java
// CPU-intensive tasks
ExecutorService cpuBoundExecutor = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors());

// I/O-intensive tasks
ExecutorService ioBoundExecutor = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors() * 4);

// Scheduled tasks
ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
```

### Message Queue Integration

#### Apache Kafka Producer
```java
@Service
public class OrderEventProducer {
    
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    
    public void publishOrderEvent(OrderEvent event) {
        ListenableFuture<SendResult<String, OrderEvent>> future = 
            kafkaTemplate.send("order-events", event.getOrderId(), event);
        
        future.addCallback(
            result -> log.info("Order event sent successfully: {}", event.getOrderId()),
            failure -> log.error("Failed to send order event", failure)
        );
    }
}
```

#### RabbitMQ Configuration
```java
@Configuration
@EnableRabbit
public class RabbitConfig {
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("Message delivery failed: {}", cause);
            }
        });
        return template;
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(50);
        return factory;
    }
}
```

## 5. Microservices & Scalability

### API Gateway Implementation

#### Spring Cloud Gateway
```java
@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r.path("/api/users/**")
                .filters(f -> f.circuitBreaker(c -> c.setName("userServiceCB"))
                    .retry(retryConfig -> retryConfig.setRetries(3))
                    .requestRateLimiter(rl -> rl.setRateLimiter(redisRateLimiter())))
                .uri("lb://user-service"))
            .route("order-service", r -> r.path("/api/orders/**")
                .filters(f -> f.circuitBreaker(c -> c.setName("orderServiceCB")))
                .uri("lb://order-service"))
            .build();
    }
    
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 20); // 10 requests per second, burst of 20
    }
}
```

#### Rate Limiting
```java
@Component
public class RateLimitingFilter implements GatewayFilter {
    
    private final RedisTemplate<String, String> redisTemplate;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String clientId = getClientId(exchange.getRequest());
        String key = "rate_limit:" + clientId;
        
        return redisTemplate.opsForValue()
            .increment(key)
            .flatMap(count -> {
                if (count == 1) {
                    redisTemplate.expire(key, Duration.ofMinutes(1));
                }
                
                if (count > 100) { // 100 requests per minute
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    return exchange.getResponse().setComplete();
                }
                
                return chain.filter(exchange);
            });
    }
}
```

### Load Balancing Strategies

#### Client-Side Load Balancing (Spring Cloud LoadBalancer)
```java
@Configuration
public class LoadBalancerConfig {
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public ReactorLoadBalancer<ServiceInstance> userServiceLoadBalancer(
            Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RoundRobinLoadBalancer(
            loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),
            name);
    }
}
```

#### Health Check Configuration
```java
@Component
public class HealthCheckService {
    
    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;
    
    @Scheduled(fixedDelay = 30000)
    public void performHealthCheck() {
        List<String> services = Arrays.asList("user-service", "order-service", "payment-service");
        
        services.parallelStream().forEach(service -> {
            try {
                circuitBreaker.executeSupplier(() -> {
                    ResponseEntity<String> response = restTemplate.getForEntity(
                        "http://" + service + "/health", String.class);
                    return response.getStatusCode() == HttpStatus.OK;
                });
            } catch (Exception e) {
                log.warn("Health check failed for service: {}", service, e);
                // Mark service as unhealthy
            }
        });
    }
}
```

### Service Mesh with Istio

#### Traffic Management
```yaml
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: user-service
spec:
  http:
  - match:
    - headers:
        canary:
          exact: "true"
    route:
    - destination:
        host: user-service
        subset: v2
      weight: 100
  - route:
    - destination:
        host: user-service
        subset: v1
      weight: 90
    - destination:
        host: user-service
        subset: v2
      weight: 10
```

#### Circuit Breaker Configuration
```yaml
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: user-service
spec:
  host: user-service
  trafficPolicy:
    circuitBreaker:
      consecutiveErrors: 3
      interval: 30s
      baseEjectionTime: 30s
      maxEjectionPercent: 50
    loadBalancer:
      simple: LEAST_CONN
```

## Performance Monitoring and Metrics

### Application Performance Monitoring (APM)
```java
// Micrometer metrics integration
@Component
public class PerformanceMetrics {
    
    private final MeterRegistry meterRegistry;
    private final Timer.Sample sample;
    
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        Timer.Sample.start(meterRegistry)
            .stop(Timer.builder("order.processing.time")
                .description("Time taken to process an order")
                .tag("type", event.getOrderType())
                .register(meterRegistry));
        
        Counter.builder("orders.created")
            .description("Number of orders created")
            .tag("type", event.getOrderType())
            .register(meterRegistry)
            .increment();
    }
}
```

### Key Performance Indicators (KPIs)
- **Response Time:** 95th percentile < 200ms
- **Throughput:** Requests per second
- **Error Rate:** < 0.1%
- **Resource Utilization:** CPU < 70%, Memory < 80%
- **Database Connection Pool:** Active connections < 80% of max

## Best Practices Summary

1. **Database Optimization**
   - Use connection pooling with proper sizing
   - Implement prepared statements and query optimization
   - Monitor slow queries and optimize indexes

2. **Caching Strategy**
   - Implement multi-level caching (L1: local, L2: distributed)
   - Use appropriate eviction policies
   - Monitor cache hit ratios

3. **Asynchronous Processing**
   - Use CompletableFuture for non-blocking operations
   - Size thread pools appropriately
   - Implement proper error handling and timeouts

4. **Microservices Architecture**
   - Implement circuit breakers and bulkheads
   - Use service mesh for traffic management
   - Monitor service health and performance

5. **Monitoring and Observability**
   - Implement comprehensive logging and metrics
   - Use distributed tracing
   - Set up proper alerting and monitoring dashboards
