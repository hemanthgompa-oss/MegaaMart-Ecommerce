
package com.megamart.megamart.Entity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.megamart.megamart.repository.ProductRepository;

@Component
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository repository;

    public ProductDataLoader(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        if (repository.count() > 1) {
            return;
        }

        add("Rice 5kg", "Groceries", 350, 30,
            "https://images.unsplash.com/photo-1586201375761-83865001e31c?w=500");
        add("Toor Dal 1kg", "Groceries", 160, 25,
            "https://images.unsplash.com/photo-1515543904379-3d757afe72e4?w=500");
        add("Sugar 1kg", "Groceries", 50, 40,
            "https://images.unsplash.com/photo-1581441363689-1f3c3c414635?w=500");
        add("Wheat Flour 5kg", "Groceries", 220, 20,
            "https://images.unsplash.com/photo-1574323347407-f5e1ad6d020b?w=500");
        add("Sunflower Oil 1L", "Groceries", 140, 30,
            "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=500");
        add("Salt 1kg", "Groceries", 25, 50,
            "https://images.unsplash.com/photo-1518110925495-5fe2fda0442b?w=500");
        add("Tea Powder", "Groceries", 120, 20,
            "https://images.unsplash.com/photo-1544787219-7f47ccb76574?w=500");
        add("Biscuits Pack", "Groceries", 30, 50,
            "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=500");

        add("Men T-Shirt", "Clothing", 499, 20,
            "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500");
        add("Casual Shirt", "Clothing", 699, 15,
            "https://images.unsplash.com/photo-1598033129183-c4f50c736f10?w=500");
        add("Formal Shirt", "Clothing", 899, 15,
            "https://images.unsplash.com/photo-1603252109303-2751441dd157?w=500");
        add("Women T-Shirt", "Clothing", 449, 20,
            "https://images.unsplash.com/photo-1503341504253-dff4815485f1?w=500");
        add("Men Jeans", "Clothing", 999, 15,
            "https://images.unsplash.com/photo-1542272604-787c3835535d?w=500");
        add("Track Pants", "Clothing", 599, 20,
            "https://images.unsplash.com/photo-1552902865-b72c031ac5ea?w=500");

        add("Casual Shoes", "Footwear", 999, 15,
            "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500");
        add("Sports Shoes", "Footwear", 1299, 15,
            "https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=500");
        add("Men Slippers", "Footwear", 199, 30,
            "https://images.unsplash.com/photo-1562273138-f46be4ebdf33?w=500");
        add("Women Sandals", "Footwear", 499, 15,
            "https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=500");
        add("Flip Flops", "Footwear", 149, 25,
            "https://images.unsplash.com/photo-1603487742131-4160ec999306?w=500");

        add("Bluetooth Headset", "Electronics", 999, 15,
            "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500");
        add("Wired Earphones", "Electronics", 299, 20,
            "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=500");
        add("Power Bank", "Electronics", 799, 15,
            "https://images.unsplash.com/photo-1609091839311-d5365f9ff1c5?w=500");
        add("LED Bulb", "Electronics", 99, 50,
            "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=500");
        add("Electric Fan", "Electronics", 1999, 10,
            "https://images.unsplash.com/photo-1581276160099-3a4a1a7b8b9b?w=500");
        add("Mobile Charger", "Electronics", 399, 20,
            "https://images.unsplash.com/photo-1583394838336-acd977736f90?w=500");
        add("USB Cable", "Electronics", 149, 30,
            "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=500");

        add("Travel Bag", "Bags", 1299, 10,
            "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500");
        add("Laptop Bag", "Bags", 999, 10,
            "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500");
        add("School Bag", "Bags", 599, 15,
            "https://images.unsplash.com/photo-1581605405669-fcdf81165afa?w=500");
        add("Trolley Suitcase", "Bags", 2499, 8,
            "https://images.unsplash.com/photo-1565026057447-bc90a3dceb87?w=500");

        System.out.println("MEGAMART SAMPLE PRODUCTS LOADED");
    }

    private void add(String name, String category,
                     double price, int stock, String imageUrl) {

        product product = new product();
        product.setName(name);
        product.setCategory(category);
        product.setDescription(name + " - MegaMart product");
        product.setPrice(price);
        product.setStock(stock);
        product.setImageUrl(imageUrl);

        repository.save(product);
    }
}