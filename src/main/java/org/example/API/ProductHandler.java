package org.example.API;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
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

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if ("GET".equalsIgnoreCase(method)) {
            if (path.matches("/products/\\d+")) {
                int id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                Product product = productService.getProductById(id);
                if (product != null) {
                    response = product.getProductName();
                } else {
                    statusCode = 404;
                    response = "Product not found with id: " + id;
                }
                logger.info("Response from server is: "+response);
            } else if ("/products".equals(path)) {
                try {
                    response = productService.viewAProducts();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
//            List<String> allP=productService.getAllProductNames();
//            for(String productName : allP){
//                response = response+ " " +productName + "\n";
//            }
                logger.info("Response from server is: "+response);
            }
        } else if ("POST".equals(method)) {
            Product newProduct = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Product.class);
            logger.info("Read product from server is: "+newProduct.getProductName());
            productService.addProduct(newProduct);
            logger.info("Added product from server is: "+newProduct);
            response = "Product " + newProduct.getProductName()+" added successfully!";
            logger.info("Product added: " + response);
        } else {
            response = "Error adding product :(";
            statusCode = 404;
        }

        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        if (os != null) {
            os.close();
        }
    }
}
