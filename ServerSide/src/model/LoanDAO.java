package model;

import domain.Loan;
import domain.User;

import java.sql.*;
import java.util.Objects;

public class LoanDAO {

    private static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/library";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "graphic4";

    public LoanDAO() {

    }

    public static Loan addLoan(Loan loan) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.prepareStatement("insert into loan(`student_id`, `isbn`, `amount_owing`) values (?, ?, ?);");

            statement.setString(1, loan.getStudentID());
            statement.setString(2, loan.getIsbn());
            statement.setDouble(3, loan.getAmountOwing());

            int ok = statement.executeUpdate();

            if (ok > 0) {
                return loan;
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        return null;
    }

    public static Loan removeLoan(Loan loan) {
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
            ok = statement.executeUpdate("DELETE FROM loan WHERE isbn = '" + loan.getIsbn() + "'");

            if (ok > 0) {
                return loan;
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

        return null;

    }

    public static double amountOwing(User user) {
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
            statement.executeQuery("SELECT loan_number FROM loan");

            rs = statement.executeQuery("SELECT amount_owing FROM loan WHERE student_id = '" + user.getStudentID() + "'");

            if (rs.next()) {
                return rs.getDouble(1);
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

        return 0;
    }

    public static void setAmountOwing(Loan loan) {
        String loanDate = Timestamp.valueOf(Objects.requireNonNull(getDate(loan))).toLocalDateTime().toLocalDate().toString();
        loan.setDateBorrowed(loanDate);
        double amount = loan.getNumberOfDaysExceeded() * 2;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.prepareStatement("SELECT * FROM loan");
            rs = statement.executeQuery();

            while (rs.next()) {

            }

            statement = conn.prepareStatement("UPDATE loan SET amount_owing = ? WHERE student_id = ?");
            statement.setDouble(1, amount);
            statement.setString(2, loan.getStudentID());


            int ok = statement.executeUpdate();

            if (ok > 0) {
                // stuff
                System.out.println("Amount is set");
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

    public static String getDate(Loan loan) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            statement = conn.prepareStatement("SELECT date_borrowed FROM loan WHERE student_id = ?");
            statement.setString(1, loan.getStudentID());

            rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
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

        return null;
    }



}
