package org.example.Model;

import javax.annotation.processing.Generated;
import java.util.UUID;

public class Product {
    private UUID productId;
    private String productName;
    private String productPrice;

    public Product(String productName, String productPrice) {
        this.productId = UUID.randomUUID();
        this.productName = productName;
        this.productPrice = productPrice;
    }
    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

}
