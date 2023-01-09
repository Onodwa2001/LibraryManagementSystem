package model;

import domain.Staff;

import java.sql.*;
import java.util.ArrayList;

public class StaffDAO {

    private static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/library";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "graphic4";

    public StaffDAO() {

    }

    public static ArrayList<Staff> getAllStaff() {
        ArrayList<Staff> staffList = new ArrayList<>();

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
            rs = statement.executeQuery("SELECT * FROM staff");

            if (rs != null) {

                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");

                    staffList.add(new Staff(username, password));
                }

                return staffList;
            }


        } catch (SQLException sql) {
            System.out.println("Error while fetching books: " + sql.getMessage());
            sql.printStackTrace();
        }

        return null;
    }

}
