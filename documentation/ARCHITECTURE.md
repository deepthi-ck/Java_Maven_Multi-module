# Architecture (Java 11)

Maven multi-module layout:

- **common** — shared value types (`Money`, `SkuUtils`) used by both services
- **product-service** — REST API for products (depends on `common`)
- **order-service** — REST API for orders (depends on `common`, calls product-service via `ProductClient`)

Modules are connected through the parent POM dependency management and inter-module Maven dependencies.