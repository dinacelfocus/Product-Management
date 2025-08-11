package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Exception.DatabaseException;
import org.example.Exception.InvalidProductException;
import org.example.Exception.ProductNotFoundException;
import org.example.Model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class ProductService {
    ProductDAO dao = new ProductDAO();
    List<Product> products= dao.getAllProducts();

    public List<Product> getAllProducts(){
        return products;
    }

    public void addProduct(Product p){
        try{
            dao.insertProduct(p);
        }catch(SQLException e){
            throw new DatabaseException("Failed to insert product", e);
        }
    }


    public String viewAProducts() throws SQLException {
        return dao.viewAllProducts();
    }

    public Product getProductById(int id) {
        Product p = null;
        for (Product product : products) {
            if (product.getProductId()==(id)) {
                p=product;
            }
        }
        if (p==null) {
            throw new InvalidProductException("Invalid Product with ID: " + id);
        }
        return p;
    }


    public List<Product> filterByName(String name) {
        List<Product> filteredProducts = products.stream()
                .filter(p -> p.getProductName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
        if (filteredProducts.isEmpty()) {
            throw new ProductNotFoundException("Product not found with name: " + name);
        }
        return filteredProducts;
    }


    public int extractPrice(String priceString) {
        if (priceString == null) return 0;

        String number = priceString.replaceAll("[^\\d.]", "");

        try {
            return  Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public List<Product> sortByPrice() {
        return products.stream()
                .sorted(Comparator.comparing(p -> extractPrice(p.getProductPrice())))
                .collect(Collectors.toList());
    }

}

