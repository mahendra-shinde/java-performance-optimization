# Module 12: JDBC and I/O Performance Optimization

## JDBC Tuning

**1. Use Connection Pooling:**
  - Creating a new database connection for every request is expensive. Connection pools (e.g., HikariCP, Apache DBCP) manage a pool of reusable connections, reducing overhead and improving throughput.
  - Configure pool size based on your workload and database limits.

**2. Batch Inserts and Updates:**
  - Instead of executing single insert/update statements in a loop, use JDBC batch operations (`addBatch()` and `executeBatch()`).
  - This reduces network round-trips and improves performance, especially for large data loads.

**3. Proper Indexing:**
  - Ensure that frequently queried columns are indexed. Indexes speed up SELECT queries but can slow down INSERT/UPDATE, so use them judiciously.
  - Regularly analyze and optimize indexes as data and query patterns evolve.

**4. Prepared Statements:**
  - Use prepared statements to avoid SQL injection and benefit from statement caching at the database level.


**5. Minimize Result Set Size:**
  - Only fetch the columns and rows you need. Use `SELECT column1, column2 FROM ...` instead of `SELECT *`.
  - Use pagination (`LIMIT`/`OFFSET`) for large result sets.

**6. Close Resources Properly:**
  - Always close `ResultSet`, `Statement`, and `Connection` objects in a `finally` block or use try-with-resources to avoid resource leaks.

**7. Tune Fetch Size:**
  - Adjust the JDBC fetch size (`statement.setFetchSize()`) to balance memory usage and network round-trips for large queries.

**8. Avoid N+1 Query Problem:**
  - Fetch related data in a single query using `JOIN` or `IN` clauses instead of running a query inside a loop.

## Example: Batch Insert with JDBC

```java
try (Connection conn = dataSource.getConnection();
     PreparedStatement ps = conn.prepareStatement("INSERT INTO products (name, price) VALUES (?, ?)") ) {
    for (Product p : products) {
        ps.setString(1, p.getName());
        ps.setBigDecimal(2, p.getPrice());
        ps.addBatch();
    }
    ps.executeBatch();
}
```

## Query Optimization

- **Analyze Query Plans:** Use `EXPLAIN` to understand how your queries are executed and identify bottlenecks.
- **Avoid Unnecessary Columns:** Only select the columns you need.
- **Use WHERE Clauses:** Filter data as early as possible to reduce the amount of data processed and transferred.
- **Optimize Joins:** Ensure joined columns are indexed and avoid joining large tables unnecessarily.

## I/O Optimization

- **Buffering:** Use buffered streams (`BufferedReader`, `BufferedWriter`) for file I/O to reduce disk access.
- **Bulk Operations:** Read/write data in chunks instead of byte-by-byte or line-by-line for large files.
- **Asynchronous I/O:** For high-throughput applications, consider using NIO (Non-blocking I/O) or asynchronous APIs.

## Example: Buffered File Read

```java
try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        // process line
    }
}
```

## Best Practices Checklist

- Use connection pooling
- Batch database operations
- Use prepared statements
- Minimize data transfer
- Close resources properly
- Profile and analyze queries
- Use buffered I/O for files

---
For more advanced tuning, refer to your database's documentation and profiling tools (e.g., VisualVM, YourKit, database-specific profilers).
