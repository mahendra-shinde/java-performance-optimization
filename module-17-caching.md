# Module 17: Caching for Java Performance Optimization

## 1. Introduction to Caching

Caching is a technique to store frequently accessed data in a fast storage layer, reducing the need to recompute or refetch data. It is a key strategy for improving application performance, scalability, and responsiveness.

---

## 2. Why Use Caching?
- **Reduce Latency:** Serve data faster by avoiding expensive computations or slow I/O.
- **Decrease Load:** Lower the load on databases, APIs, or other backend systems.
- **Improve Scalability:** Handle more requests with the same resources.
- **Enhance User Experience:** Faster response times for end-users.

---

## 3. Types of Caching in Java

### a. In-Memory Caching
- **Local Cache:** Data is stored in the memory of a single JVM instance (e.g., using `HashMap`, `ConcurrentHashMap`, or libraries like Caffeine, Guava).
- **Distributed Cache:** Data is shared across multiple JVMs (e.g., Redis, Memcached, Hazelcast, Ehcache).

### b. Persistent Caching
- Data is cached on disk for durability (e.g., Ehcache with disk store).

---

## 4. Common Caching Patterns
- **Read-Through Cache:** Application reads from cache; on miss, loads from source and updates cache.
- **Write-Through Cache:** Writes go to cache and source simultaneously.
- **Write-Behind Cache:** Writes go to cache first, then asynchronously to source.
- **Cache-Aside (Lazy Loading):** Application manages cache population and invalidation.

---

## 5. Java Caching Libraries & Frameworks
- **Caffeine:** High-performance, in-memory caching library for Java.
- **Guava Cache:** Simple in-memory cache from Google.
- **Ehcache:** Widely used, supports in-memory and disk caching, clustering.
- **Hazelcast:** Distributed in-memory data grid.
- **Redis/Memcached:** Popular distributed caches (accessed via Java clients like Jedis, Lettuce).

---

## 6. Example: Using Caffeine Cache
```java
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

Cache<String, String> cache = Caffeine.newBuilder()
    .maximumSize(10_000)
    .expireAfterWrite(10, java.util.concurrent.TimeUnit.MINUTES)
    .build();

// Put and get
cache.put("key", "value");
String value = cache.getIfPresent("key");
```


## 7. Best Practices
- **Choose the right cache size and eviction policy.**
- **Set appropriate TTL (Time-To-Live) for cache entries.**
- **Monitor cache hit/miss rates.**
- **Avoid stale data: implement proper invalidation.**
- **Be careful with distributed caches: handle serialization, consistency, and network partitions.**

---

## 8. When Not to Use Caching
- Data changes very frequently.
- Strong consistency is required.
- Memory constraints are tight.

---

## 9. Further Reading
- [Caffeine GitHub](https://github.com/ben-manes/caffeine)
- [Ehcache Documentation](https://www.ehcache.org/documentation/)
- [Hazelcast Documentation](https://docs.hazelcast.com/)
- [Redis Java Clients](https://redis.io/docs/clients/java/)

---

Caching, when used wisely, can dramatically improve Java application performance. Always measure and monitor to ensure it delivers the expected benefits!
