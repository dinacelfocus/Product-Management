package org.example.API;

import com.sun.net.httpserver.HttpServer;
import org.example.Service.ProductService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ProductServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/products", new ProductService());
        server.setExecutor(null); // creates a default executor

        server.start();
        System.out.println("Server started on port 8080");
    }
}
