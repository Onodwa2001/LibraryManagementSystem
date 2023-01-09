package model;

import domain.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {

    private static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/library";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "graphic4";

    public BookDAO() {

    }

    public static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Book addBook(Book book) {
        loadDriver();

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.prepareStatement("INSERT INTO BOOK VALUES (?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, book.getName());
            statement.setString(2, book.getIsbn());
            statement.setBoolean(3, book.isAvailableForLoan());
            statement.setString(4, book.getAuthor());
            statement.setString(5, book.getCategory());
            statement.setString(6, book.getShelfNumber());
            statement.setString(7, book.getImage());

            int ok = statement.executeUpdate();

            if (ok > 0) {
                return book;
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        return null;
    }

    public static Book deleteBook(Book book) {
        loadDriver();

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);

            statement = conn.prepareStatement("DELETE FROM book WHERE isbn = ?");

            statement.setString(1, book.getIsbn());

            int ok = statement.executeUpdate();

            if (ok > 0) {
                System.out.println("Deleted");
                return book;
            } else {
                System.out.println("Not updated");
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        return null;
    }

    public static Book updateBook(Book book) {
        loadDriver();

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.prepareStatement("UPDATE book SET title = ?, author = ?, category = ?, shelf_number = ? "
                    + "WHERE isbn = ?");

            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getCategory());
            statement.setString(4, book.getShelfNumber());
            statement.setString(5, book.getIsbn());

            int ok = statement.executeUpdate();

            if (ok > 0) {
                System.out.println("Updated");
                return book;
            } else {
                System.out.println("Not updated");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        return null;
    }

    public static void disableAvailability(Book book) {
        loadDriver();

        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.createStatement();
            int ok = statement.executeUpdate("UPDATE book SET is_available_for_loan = " + false + " WHERE isbn = '" + book.getIsbn() + "'");

            if (ok > 0) {
                System.out.println("Updated");
            } else {
                System.out.println("Not updated");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }

    }

    public static void enableAvailability(Book book) {
        loadDriver();

        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.createStatement();
            int ok = statement.executeUpdate("UPDATE book SET is_available_for_loan = " + true + " WHERE isbn = '" + book.getIsbn() + "'");

            if (ok > 0) {
                System.out.println("Updated");
                book.setIsAvailableForLoan(true);
            } else {
                System.out.println("Not updated");
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }

    }

    public static ArrayList<Book> getAllBooks() {
        ArrayList<Book> booksList = new ArrayList<>();

        loadDriver();

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM book");

            Book book;

            if (rs != null) {

                while (rs.next()) {
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String isbn = rs.getString("isbn");
                    String category = rs.getString("category");
                    String shelfNumber = rs.getString("shelf_number");
                    String image = rs.getString("image");

                    book = new Book(title, author, isbn, category, shelfNumber, image);
                    book.setIsAvailableForLoan(rs.getBoolean("is_available_for_loan"));
                    booksList.add(book);

                }

                return booksList;
            }


        } catch (SQLException sql) {
            System.out.println("Error while fetching books: " + sql.getMessage());
            sql.printStackTrace();
        }

        return null;

    }

    public static Book getBookByISBN(String inIsbn) {

        loadDriver();

        Connection conn;
        Statement statement;
        ResultSet rs;

        Book book;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM book WHERE isbn = '" + inIsbn + "'");

            if (rs.next()) {
                String name = rs.getString("title");
                String author = rs.getString("author");
                String isbn = rs.getString("isbn");
                String category = rs.getString("category");
                String shelfNumber = rs.getString("shelf_number");
                boolean isAvailableForLoan = rs.getBoolean("is_available_for_loan");

                book = new Book(name, author, isbn, category, shelfNumber);
                book.setIsAvailableForLoan(isAvailableForLoan);

                return book;
            }

        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
        }

        return null;

    }


}
