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

        if ("GET".equals(exchange.getRequestMethod())) {
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

        } else if ("POST".equals(exchange.getRequestMethod())) {
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
