package org.example;

import org.example.Model.Product;
import org.example.Service.ProductService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ProductManagementTest {
    ProductService productService = new ProductService();


    @Test
    public void addProduct_newProduct_addedToProductsList() {
        Product product = new Product(40, "flower", "EGP20");
        productService.addProduct(product);
        List<Product> productsList = productService.getAllProducts();
        List<String> nameProductsList = new ArrayList<String>();
        for(Product p : productsList){
            nameProductsList.add(p.getProductName());
        }
        assertTrue(nameProductsList.contains(product.getProductName()));
    }

    @Test
    public void getProductById_productId_getProductWithId() {
        int id = 2;
        Product product = productService.getProductById(id);
        assertTrue(product.getProductId() == id);
    }

    @Test
    public void filterByName_productName_getProductsWithName() {
        String productName = "lamp";
        List<Product> filteredProducts = productService.filterByName(productName);
        for(Product p : filteredProducts){
            assertTrue(p.getProductName().equals(productName));
        }
    }

    @Test
    public void sortByPrice_productPrices_sortedProductPrices() {
        List<Product> sortedProducts = productService.sortByPrice();
        List<Integer> sortedPrices = new ArrayList<>();
        for(Product p : sortedProducts){
            sortedPrices.add(productService.extractPrice(p.getProductPrice()));
        }
        for(int i = 1; i < sortedPrices.size(); i++){
            assertTrue(sortedPrices.get(i)>=sortedPrices.get(i-1));
        }
    }

}
