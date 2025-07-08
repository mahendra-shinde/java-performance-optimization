# Module 14: Telemetry & Observability

## Introduction to Telemetry

**Telemetry** is the automated collection and transmission of data from your application to monitoring systems. The three pillars are:

1. **Metrics:** Numeric measurements (e.g., request count, latency, error rate) that provide insight into system health and performance.
2. **Traces:** End-to-end tracking of requests as they flow through distributed systems, helping to pinpoint bottlenecks and failures.
3. **Logs:** Timestamped records of events, errors, and informational messages for debugging and auditing.

Popular tools: **OpenTelemetry** (standardized instrumentation), **Prometheus** (metrics collection), **Micrometer** (metrics facade for JVM apps).

## Implementing Telemetry

**1. Instrument Code Using Java SDKs:**
  - Use libraries like OpenTelemetry Java SDK or Micrometer to add custom metrics, traces, and logs to your code.
  - Example: Track method execution time, database query duration, or custom business events.

**2. Auto-instrumentation Agents:**
  - Many frameworks (e.g., Spring Boot) support auto-instrumentation via Java agents, requiring no code changes.
