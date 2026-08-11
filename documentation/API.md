# API (Java 17)

## product-service (`http://localhost:8082`)

- `POST /api/products` — create product
- `GET /api/products` — list products
- `GET /api/products/{id}` — get product

## order-service (`http://localhost:8083`)

- `POST /api/orders` — create order (resolves prices from product-service)
- `GET /api/orders` — list orders
- `GET /api/orders/{id}` — get order