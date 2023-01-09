package model;

import domain.Book;
import domain.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

    private static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/library";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "graphic4";

    public UserDAO() {

    }

    public static User addUser(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.prepareStatement("INSERT INTO USERS VALUES (?, ?, ?, ?)");

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getStudentID());
            statement.setBoolean(4, true);

            int ok = statement.executeUpdate();

            if (ok > 0) {
                return user;
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        return null;
    }

    // if balance is over R5, you can't borrow any more books (penalty R1 per day)
    // if borrowed more than 3 books

    public static int numberOfBooksBorrowed(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM loan WHERE student_id = '" + user.getStudentID() + "'");

            while (rs.next()) {
                count++;
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            try {
                conn.close();
                statement.close();
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return count;
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> usersList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM users");

            User user;

            if (rs != null) {

                while (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String studentId = rs.getString("student_id");
                    boolean canBorrow = rs.getBoolean("can_borrow");

                    user = new User(firstName, lastName, studentId);
                    user.setCanBorrow(canBorrow);
                    usersList.add(user);
                }

                return usersList;
            }


        } catch (SQLException sql) {
            System.out.println("Error while fetching users: " + sql.getMessage());
            sql.printStackTrace();
        }

        return null;

    }

    public static void setCanBorrowDAO(String id, boolean can) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        Statement statement = null;
        int ok;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.createStatement();
            ok = statement.executeUpdate("UPDATE users SET can_borrow = " + can);

            if (ok > 0) {
                System.out.println("Successful");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        } finally {
            try {
                conn.close();
                statement.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static User getUserByID(String inUserID) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn;
        Statement statement;
        ResultSet rs;

        User user;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM users WHERE student_id = '" + inUserID + "'");

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String studentId = rs.getString("student_id");

                user = new User(firstName, lastName, studentId);

                return user;
            }

        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
        }

        return null;

    }


}
