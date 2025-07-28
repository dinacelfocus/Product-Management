package org.example.Service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.Model.Product;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements HttpHandler {
    List<Product> products= new ArrayList<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response= "";
        int statusCode = 200;

        if ("GET".equals(exchange.getRequestMethod())) {
            List<String> allP=getAllProducts();
            for(String product : allP){
                response=" "+product+"\n";
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            Product newProduct = new Product( "Shoes", "EGP1500");
            products.add(newProduct);
            response = "Product added successfully!";
        } else {
            response = "Error adding product :(";
            statusCode = 404;
        }

        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public List<String> getAllProducts() {
        List<String> allProducts = new ArrayList<>();
        for (Product product : products) {
            allProducts.add(product.getProductName());
        }
        return allProducts;
    }

}

