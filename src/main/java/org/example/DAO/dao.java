package org.example.DAO;

import java.sql.*;

public class dao {
    private static Connection connect() throws SQLException {
        Connection myConn = null;

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db", "student" , "");
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

    private static void viewAllStudents() {
        String query = "SELECT * FROM students";
        try {
            Connection myConn = connect();
            PreparedStatement myStmt = myConn.prepareStatement(query);
            ResultSet rs = myStmt.executeQuery();

            System.out.println("\n--- Student List ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Email: %s | Grade: %s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("grade"));
            }

        } catch (SQLException e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }

    private static void deleteStudentById(int id) {
        String query = "DELETE FROM students WHERE id = ?";
        try{
            Connection myConn = connect();
            PreparedStatement myStmt = myConn.prepareStatement(query);

            myStmt.setInt(1, id);
            int rowsAffected = myStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
}
