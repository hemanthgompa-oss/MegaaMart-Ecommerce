import React, { useEffect, useState } from "react";
import "./App.css";

function App() {
  const API = "http://localhost:8080";

  const [page, setPage] = useState("home");
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const [customer, setCustomer] = useState(
    JSON.parse(localStorage.getItem("customer") || "null")
  );

  const [form, setForm] = useState({
    fullName: "",
    email: "",
    mobileNumber: "",
    password: "",
    address: "",
    identifier: ""
  });

  const customerId = customer?.id ?? customer?.customerId;

  // Load products
  const loadProducts = async () => {
    try {
      const response = await fetch(`${API}/api/products`);

      if (!response.ok) {
        throw new Error("Failed to load products");
      }

      const data = await response.json();
      setProducts(data);
    } catch (error) {
      console.error(error);
      alert("Unable to load products. Check backend connection.");
    }
  };

  // Load cart
  const loadCart = async () => {
    if (!customerId) return;

    try {
      const response = await fetch(
        `${API}/api/cart/${customerId}`
      );

      if (!response.ok) {
        throw new Error("Cart loading failed");
      }

      const data = await response.json();
      setCart(data);
    } catch (error) {
      console.error(error);
      alert("Unable to load cart.");
    }
  };

  useEffect(() => {
    loadProducts();
  }, []);

  useEffect(() => {
    if (customerId) {
      loadCart();
    } else {
      setCart([]);
    }
  }, [customerId]);

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  // Register
  const register = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(
        `${API}/api/customers/register`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            fullName: form.fullName,
            email: form.email,
            mobileNumber: form.mobileNumber,
            password: form.password,
            address: form.address
          })
        }
      );

      if (!response.ok) {
        alert("Registration failed. Email or mobile may already be registered.");
        return;
      }

      alert("Account created successfully!");
      setPage("login");
    } catch (error) {
      console.error(error);
      alert("Backend connection failed.");
    }
  };

  // Login
  const login = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(
        `${API}/api/customers/login`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            identifier: form.identifier,
            password: form.password
          })
        }
      );

      if (!response.ok) {
        alert("Invalid email/mobile or password.");
        return;
      }

      const data = await response.json();

      setCustomer(data);
      localStorage.setItem("customer", JSON.stringify(data));

      alert("Login successful!");

      if (selectedProduct) {
        setPage("checkout");
      } else {
        setPage("home");
      }

    } catch (error) {
      console.error(error);
      alert("Backend connection failed.");
    }
  };

  // Add to Cart
  const addToCart = async (product) => {
    if (!customer) {
      alert("Please login to continue.");
      setPage("login");
      return;
    }

    if (!customerId) {
      alert("Customer ID not found. Please login again.");
      return;
    }

    try {
      const response = await fetch(
        `${API}/api/cart/add?customerId=${customerId}&productId=${product.id}&quantity=1`,
        {
          method: "POST"
        }
      );

      if (!response.ok) {
        alert("Unable to add product to cart.");
        return;
      }

      alert("Product added to cart successfully!");
      await loadCart();

    } catch (error) {
      console.error(error);
      alert("Backend connection failed.");
    }
  };

  // Buy Now
  const buyNow = (product) => {
    setSelectedProduct(product);

    if (!customer) {
      alert("Please login to continue.");
      setPage("login");
      return;
    }

    setPage("checkout");
  };

  // Remove from Cart
  const removeFromCart = async (productId) => {
    if (!customerId) return;

    try {
      const response = await fetch(
        `${API}/api/cart/remove?customerId=${customerId}&productId=${productId}`,
        {
          method: "DELETE"
        }
      );

      if (!response.ok) {
        alert("Unable to remove product.");
        return;
      }

      alert("Product removed from cart!");
      await loadCart();

    } catch (error) {
      console.error(error);
      alert("Backend connection failed.");
    }
  };

  // Logout
  const logout = () => {
    localStorage.removeItem("customer");
    setCustomer(null);
    setCart([]);
    setSelectedProduct(null);
    setPage("home");

    alert("Logged out successfully.");
  };

  // Cart total
  const totalPrice = cart.reduce(
    (total, item) => total + Number(item.totalPrice || 0),
    0
  );

  // Checkout total
  const checkoutPrice = Number(selectedProduct?.price || 0);

  // Confirm checkout (Order API not connected yet)
  const confirmOrder = () => {
    alert(
      "Checkout page is ready! Order placement and payment are not connected yet."
    );
  };

  return (
    <div className="app">

      {/* Header */}
      <header className="header">
        <h1 onClick={() => setPage("home")}>
          MegaMart
        </h1>

        <nav>
          <button onClick={() => setPage("home")}>
            Home
          </button>

          <button onClick={() => setPage("home")}>
            Products
          </button>

          {customer && (
            <button onClick={() => setPage("cart")}>
              Cart ({cart.length})
            </button>
          )}

          {customer ? (
            <>
              <span>
                Welcome, {customer.fullName}
              </span>

              <button onClick={logout}>
                Logout
              </button>
            </>
          ) : (
            <>
              <button onClick={() => setPage("login")}>
                Login
              </button>

              <button onClick={() => setPage("register")}>
                Sign Up
              </button>
            </>
          )}
        </nav>
      </header>

      {/* Login Page */}
      {page === "login" && (
        <div className="form-box">
          <h2>Customer Login</h2>

          <form onSubmit={login}>
            <input
              name="identifier"
              placeholder="Email or Mobile Number"
              value={form.identifier}
              onChange={handleChange}
              required
            />

            <input
              name="password"
              type="password"
              placeholder="Password"
              value={form.password}
              onChange={handleChange}
              required
            />

            <button type="submit">
              Login
            </button>
          </form>

          <p>
            New customer?{" "}
            <span onClick={() => setPage("register")}>
              Create Account
            </span>
          </p>
        </div>
      )}

      {/* Register Page */}
      {page === "register" && (
        <div className="form-box">
          <h2>Create Account</h2>

          <form onSubmit={register}>
            <input
              name="fullName"
              placeholder="Full Name"
              value={form.fullName}
              onChange={handleChange}
              required
            />

            <input
              name="email"
              type="email"
              placeholder="Email"
              value={form.email}
              onChange={handleChange}
              required
            />

            <input
              name="mobileNumber"
              placeholder="Mobile Number"
              value={form.mobileNumber}
              onChange={handleChange}
              required
            />

            <input
              name="password"
              type="password"
              placeholder="Password"
              value={form.password}
              onChange={handleChange}
              required
            />

            <input
              name="address"
              placeholder="Delivery Address"
              value={form.address}
              onChange={handleChange}
              required
            />

            <button type="submit">
              Create Account
            </button>
          </form>

          <p>
            Already registered?{" "}
            <span onClick={() => setPage("login")}>
              Login
            </span>
          </p>
        </div>
      )}

      {/* Home and Products */}
      {page === "home" && (
        <main>
          <section className="hero">
            <h2>Welcome to MegaMart</h2>
            <p>Everything you need, all in one place.</p>

            <button
              onClick={() => {
                document
                  .getElementById("products")
                  ?.scrollIntoView({ behavior: "smooth" });
              }}
            >
              Shop Now
            </button>
          </section>

          <h2 id="products">
            Our Products ({products.length})
          </h2>

          <div className="products">
            {products.map((product) => (
              <div className="product-card" key={product.id}>
                <img
                  src={product.imageUrl}
                  alt={product.name}
                  onError={(e) => {
                    e.currentTarget.style.display = "none";
                  }}
                />

                <h3>{product.name}</h3>

                <p>{product.category}</p>

                <h3>₹{product.price}</h3>

                <p>Stock: {product.stock}</p>

                <button
                  onClick={() => addToCart(product)}
                >
                  Add to Cart
                </button>

                <button
                  onClick={() => buyNow(product)}
                >
                  Buy Now
                </button>
              </div>
            ))}
          </div>
        </main>
      )}

      {/* Cart Page */}
      {page === "cart" && (
        <main className="cart-page">
          <h2>My Shopping Cart</h2>

          {cart.length === 0 ? (
            <p>Your cart is empty.</p>
          ) : (
            <>
              {cart.map((item, index) => {
                const product = products.find(
                  (p) =>
                    Number(p.id) === Number(item.productId)
                );

                return (
                  <div
                    className="product-card"
                    key={item.id || index}
                  >
                    <h3>
                      {product?.name ||
                        `Product #${item.productId}`}
                    </h3>

                    <p>Quantity: {item.quantity}</p>

                    <p>Price: ₹{item.totalPrice}</p>

                    <button
                      onClick={() =>
                        removeFromCart(item.productId)
                      }
                    >
                      Remove from Cart
                    </button>
                  </div>
                );
              })}

              <h2>Total: ₹{totalPrice}</h2>

              <button
                onClick={() => {
                  alert("Select Buy Now on a product to continue to checkout.");
                  setPage("home");
                }}
              >
                Continue Shopping
              </button>
            </>
          )}
        </main>
      )}

      {/* Checkout Page */}
      {page === "checkout" && selectedProduct && (
        <main className="cart-page">
          <h2>Checkout</h2>

          <div className="product-card">

            <img
              src={selectedProduct.imageUrl}
              alt={selectedProduct.name}
              onError={(e) => {
                e.currentTarget.style.display = "none";
              }}
            />

            <h3>{selectedProduct.name}</h3>

            <p>Category: {selectedProduct.category}</p>

            <p>Quantity: 1</p>

            <h3>
              Price: ₹{checkoutPrice}
            </h3>

            <hr />

            <h3>Delivery Details</h3>

            <p>
              Customer: {customer?.fullName}
            </p>

            <p>
              Email: {customer?.email}
            </p>

            <p>
              Mobile: {customer?.mobileNumber}
            </p>

            <p>
              Address: {customer?.address || "Address not available"}
            </p>

            <hr />

            <h2>
              Total Amount: ₹{checkoutPrice}
            </h2>

            <button onClick={confirmOrder}>
              Place Order
            </button>

            <button
              onClick={() => setPage("home")}
            >
              Continue Shopping
            </button>

          </div>
        </main>
      )}

      {/* Footer */}
      <footer className="footer">
        <h2>MegaMart</h2>

        <p>Your one-stop online shopping store.</p>

        <p>
          © 2026 MegaMart. All rights reserved.
        </p>
      </footer>

    </div>
  );
}

export default App;