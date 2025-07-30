package org.example.DAO;

import org.example.Model.Product;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class ProductDAO {

    private static Connection connect() throws SQLException {
        Connection myConn = null;
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productDB", "product", "password");
            System.out.println("Database connection successful!\n");
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return myConn;
    }

    public void insertProduct(Product product) throws SQLException {
        String result="alo";
        String query = "INSERT INTO products (id, productName, price) VALUES (?, ?, ?)";
        try (Connection myConn = connect();
             PreparedStatement myStmt = myConn.prepareStatement(query)) {
            myStmt.setInt(1, product.getProductId());
            myStmt.setString(2, product.getProductName());
            myStmt.setString(3, product.getProductPrice());
            myStmt.executeUpdate();
            myStmt.executeQuery();

        }catch (SQLException exc) {
            exc.printStackTrace();
        }

    }

    public String viewAllProducts() {
        String result="";
        String query = "SELECT * FROM products";
        try {
            Connection myConn = connect();
            PreparedStatement myStmt = myConn.prepareStatement(query);
            ResultSet rs = myStmt.executeQuery();

            result+= "\n--- Product List ---\n";
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("productName");
                String price = rs.getString("price");

                result+= id+"  "+name+"  "+price+"\n";
                System.out.printf("ID: %s | Name: %s | Price: %s\n", id, name, price);

            }
        } catch (SQLException e) {
            System.out.println("Error viewing products: " + e.getMessage());
        }
        return result;
    }
//    public static void main(String[] args) throws SQLException {
//        connect();
//
//    }

}
