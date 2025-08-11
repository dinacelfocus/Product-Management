package org.example.API;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.Exception.DatabaseException;
import org.example.Exception.InvalidProductException;
import org.example.Exception.ProductNotFoundException;
import org.example.Model.Product;
import org.example.Service.ProductService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class ProductHandler implements HttpHandler {
    Logger logger = Logger.getLogger(ProductHandler.class.getName());
    Gson gson = new Gson();
    ProductService productService;


    public ProductHandler(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response= "";
        int statusCode = 200;
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if ("GET".equalsIgnoreCase(method)) {
                if (path.matches("/products/\\d+")) {
                    int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                    Product product = productService.getProductById(id);
                    response = product.getProductName();
                    logger.info("Response from server is: " + response);
                } else if (path.matches("/products/sorted")) {
                    List<Product> products = productService.sortByPrice();
                    for (Product p : products) {
                        response += p.getProductName() + " : " + p.getProductPrice() + "\n";
                    }
                    logger.info("Response from server is: " + response);
                } else if (path.matches("/products/[^/]+")) {
                    String name = path.substring(path.lastIndexOf("/") + 1);
                    List<Product> products = productService.filterByName(name);
                    if (!products.isEmpty()) {
                        for (Product p : products) {
                            response += p.getProductId() + "\n";
                        }
                    } else {
                        statusCode = 404;
                        response = "No products available with the name: " + name;
                    }
                    logger.info("Response from server is: " + response);
                } else if ("/products".equals(path)) {
                    try {
                        response = productService.viewAProducts();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    logger.info("Response from server is: " + response);
                }
            } else if ("POST".equals(method)) {
                Product newProduct = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Product.class);
                logger.info("Read product from server is: " + newProduct.getProductName());
                productService.addProduct(newProduct);
                logger.info("Added product from server is: " + newProduct);
                response = "Product " + newProduct.getProductName() + " added successfully!";
                logger.info("Product added: " + response);
            } else {
                response = "Error adding product :(";
                statusCode = 404;
            }
        }catch (ProductNotFoundException e) {
            statusCode = 404;
            response = " " + e.getMessage();
        }catch (InvalidProductException e){
            statusCode = 400;
            response = " " + e.getMessage();
        }catch (DatabaseException e){
            statusCode = 500;
            response = "Database error: " + e.getMessage();
        }

        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        if (os != null) {
            os.close();
        }
    }
}
