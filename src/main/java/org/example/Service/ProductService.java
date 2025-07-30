package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductService {
    List<Product> products= new ArrayList<>();
    ProductDAO dao = new ProductDAO();

    public void addProduct(Product p){
        try{
            products.add(p);
            dao.insertProduct(p);
        }catch(SQLException e){
            //throw new DatabaseException("Failed to insert product", e);
        }
    }

    public List<Product> getProductsList(){
        List<Product> productsList= new ArrayList<>();
        for(Product product:products){
            productsList.add(product);
        }
        return productsList;
    }

    public List<String> getAllProductNames() {
        List<String> allProductNames = new ArrayList<>();
        for (Product product : products) {
            allProductNames.add(product.getProductName());
        }
        return allProductNames;
    }

    public String viewAProducts() throws SQLException {
        return dao.viewAllProducts();
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getProductId()==(id)) {
                return product;
            }
        }
        return null;
    }

    public int getProductId(String name) {
        for (Product product : products) {
            if (product.getProductName().equals(name)) {
                return product.getProductId();
            }
        }
        return 0;
    }

    public List<Product> filterByName(String name) {
        return products.stream()
                .filter(p -> p.getProductName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public List<Product> sortByPrice() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getProductPrice))
                .collect(Collectors.toList());
    }

}

