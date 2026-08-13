(function () {
  const API = "";

  function $(sel, root) { return (root || document).querySelector(sel); }
  function $all(sel, root) { return Array.prototype.slice.call((root || document).querySelectorAll(sel)); }

  function currentPage() {
    const path = window.location.pathname || "/";
    const name = path.split("/").pop() || "index.html";
    return name === "" || name === "/" ? "index.html" : name;
  }

  function markActiveNav() {
    const page = currentPage();
    $all(".nav a").forEach(function (a) {
      const href = (a.getAttribute("href") || "").split("/").pop();
      if (href === page || (page === "index.html" && href === "index.html")) {
        a.classList.add("active");
      }
    });
  }

  function showFlash(message, ok) {
    const el = $("#flash");
    if (!el) return;
    el.textContent = message;
    el.className = "flash show " + (ok ? "ok" : "err");
  }

  async function api(path, options) {
    const opts = options || {};
    const headers = Object.assign({ "Accept": "application/json" }, opts.headers || {});
    if (opts.body && !headers["Content-Type"]) {
      headers["Content-Type"] = "application/json";
    }
    const res = await fetch(API + path, Object.assign({}, opts, { headers: headers }));
    const text = await res.text();
    let data = null;
    if (text) {
      try { data = JSON.parse(text); } catch (e) { data = text; }
    }
    if (!res.ok) {
      var msg = "Request failed (" + res.status + ")";
      if (data && data.message) msg = data.message;
      else if (data && data.error) msg = data.error;
      else if (typeof data === "string" && data.length < 240) msg = data;
      throw new Error(msg);
    }
    return data;
  }

  function money(n) {
    var v = Number(n || 0);
    return v.toLocaleString(undefined, { style: "currency", currency: "USD" });
  }

  function escapeHtml(s) {
    return String(s == null ? "" : s)
      .replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;")
      .replace(/"/g, "&quot;");
  }

  function card(label, value) {
    return '<div class="card"><div class="stat-label">' + label + '</div><div class="stat-value">' + value + "</div></div>";
  }

  function serviceRow(name, detail, up) {
    return '<div class="service-row"><div><strong>' + escapeHtml(name) +
      '</strong><span>' + escapeHtml(detail) + '</span></div><span class="pill ' +
      (up ? "up" : "down") + '">' + (up ? "UP" : "DOWN") + "</span></div>";
  }

  async function probe(path) {
    try {
      await api(path);
      return true;
    } catch (e) {
      return false;
    }
  }

  async function loadHealthBadge() {
    const badge = $("#runtime-badge");
    if (!badge) return;
    try {
      const h = await api("/actuator/health");
      badge.textContent = "Order module " + (h.status || "UP");
    } catch (e) {
      badge.textContent = "Order module unreachable";
    }
  }

  async function loadDashboard() {
    const box = $("#dashboard-stats");
    if (!box) return;
    try {
      const products = await api("/api/products");
      const orders = await api("/api/orders");
      var catalogValue = (products || []).reduce(function (sum, p) {
        return sum + (Number(p.price || 0) * Number(p.stock || 0));
      }, 0);
      box.innerHTML =
        card("Products", products.length) +
        card("Orders", orders.length) +
        card("Catalog value", money(catalogValue));
    } catch (e) {
      box.innerHTML = '<div class="empty">' + escapeHtml(e.message) + "</div>";
    }

    const probes = $("#service-probes");
    if (!probes) return;
    const productUp = await probe("/api/products");
    const orderUp = await probe("/api/orders");
    const healthUp = await probe("/actuator/health");
    probes.innerHTML =
      serviceRow("product-service", "GET /api/products (proxied)", productUp) +
      serviceRow("order-service", "GET /api/orders", orderUp) +
      serviceRow("actuator", "GET /actuator/health", healthUp);
  }

  async function loadTopology() {
    const gw = $("#topology-gateway");
    const routes = $("#topology-routes");
    if (!gw && !routes) return;

    if (gw) {
      try {
        const h = await api("/actuator/health");
        gw.innerHTML =
          "<p><strong>Status:</strong> " + escapeHtml(h.status || "UNKNOWN") + "</p>" +
          '<p class="footer-note">Actuator endpoint <span class="mono">/actuator/health</span> on order-service.</p>';
      } catch (e) {
        gw.innerHTML = '<div class="empty">' + escapeHtml(e.message) + "</div>";
      }
    }

    if (routes) {
      const productUp = await probe("/api/products");
      const orderUp = await probe("/api/orders");
      routes.innerHTML =
        serviceRow("Product facade", "order-service → product-service · /api/products", productUp) +
        serviceRow("Order API", "order-service · /api/orders", orderUp);
    }
  }

  async function loadProducts() {
    const tbody = $("#products-body");
    if (!tbody) return;
    try {
      const rows = await api("/api/products");
      if (!rows.length) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty">No products yet. Publish a SKU before placing orders.</td></tr>';
        return;
      }
      tbody.innerHTML = rows.map(function (p) {
        return "<tr>" +
          "<td class=\"mono\">" + escapeHtml(p.id) + "</td>" +
          "<td>" + escapeHtml(p.name) + "</td>" +
          "<td class=\"mono\">" + escapeHtml(p.sku) + "</td>" +
          "<td>" + money(p.price) + "</td>" +
          "<td>" + escapeHtml(p.stock) + "</td>" +
          "</tr>";
      }).join("");
    } catch (e) {
      tbody.innerHTML = '<tr><td colspan="5" class="empty">' + escapeHtml(e.message) + "</td></tr>";
    }
  }

  async function loadOrders() {
    const tbody = $("#orders-body");
    if (!tbody) return;
    try {
      const rows = await api("/api/orders");
      if (!rows.length) {
        tbody.innerHTML = '<tr><td colspan="4" class="empty">No orders yet. Publish a product, then place an order.</td></tr>';
        return;
      }
      tbody.innerHTML = rows.map(function (o) {
        var lines = (o.lines || []).length;
        return "<tr>" +
          "<td class=\"mono\">" + escapeHtml(o.id) + "</td>" +
          "<td>" + escapeHtml(o.customerRef) + "</td>" +
          "<td>" + escapeHtml(lines) + "</td>" +
          "<td>" + money(o.total) + "</td>" +
          "</tr>";
      }).join("");
    } catch (e) {
      tbody.innerHTML = '<tr><td colspan="4" class="empty">' + escapeHtml(e.message) + "</td></tr>";
    }
  }

  function bindProductForm() {
    const form = $("#product-form");
    if (!form) return;
    form.addEventListener("submit", async function (ev) {
      ev.preventDefault();
      try {
        const body = {
          name: $("#product-name").value.trim(),
          sku: $("#product-sku").value.trim().toUpperCase(),
          price: Number($("#product-price").value),
          stock: Number($("#product-stock").value)
        };
        await api("/api/products", { method: "POST", body: JSON.stringify(body) });
        form.reset();
        showFlash("Product published through product-service.", true);
        await loadProducts();
      } catch (e) { showFlash(e.message, false); }
    });
  }

  function bindOrderForm() {
    const form = $("#order-form");
    if (!form) return;
    form.addEventListener("submit", async function (ev) {
      ev.preventDefault();
      try {
        const body = {
          customerRef: $("#order-customer").value.trim(),
          lines: [{
            productId: Number($("#order-product").value),
            quantity: Number($("#order-qty").value)
          }]
        };
        await api("/api/orders", { method: "POST", body: JSON.stringify(body) });
        form.reset();
        $("#order-qty").value = "1";
        showFlash("Order placed through order-service.", true);
        await loadOrders();
      } catch (e) { showFlash(e.message, false); }
    });
  }

  document.addEventListener("DOMContentLoaded", function () {
    markActiveNav();
    loadHealthBadge();
    loadDashboard();
    loadTopology();
    loadProducts();
    loadOrders();
    bindProductForm();
    bindOrderForm();
  });
})();
