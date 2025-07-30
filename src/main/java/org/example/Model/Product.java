package org.example.Model;

public class Product {
    private int productId;
    private String productName;
    private String productPrice;

    public Product(int productId,String productName, String productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
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
