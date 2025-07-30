package org.example.DAO;

import org.example.Model.Product;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class ProductDAO {

    private static Connection connect() throws SQLException {
        Connection myConn = null;
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productDB");
            System.out.println("Database connection successful!\n");
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            if (myConn != null) {
                myConn.close();
            }
        }
        return myConn;
    }

    public void insertProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (id, name, price) VALUES (?, ?, ?)";
        try (Connection myConn = connect();
             PreparedStatement myStmt = myConn.prepareStatement(query)) {
            myStmt.setInt(1, product.getProductId());
            myStmt.setString(2, product.getProductName());
            myStmt.setString(3, product.getProductPrice());
            myStmt.executeUpdate();
        }
    }

    public void viewAllProducts() {
        String query = "SELECT * FROM products";
        try {
            Connection myConn = connect();
            PreparedStatement myStmt = myConn.prepareStatement(query);
            ResultSet rs = myStmt.executeQuery();

            System.out.println("\n--- Product List ---");
            while (rs.next()) {
                System.out.printf("ID: %s | Name: %s | Price: %d\n",
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("price"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing products: " + e.getMessage());
        }
    }

}
