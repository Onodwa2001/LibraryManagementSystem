package domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan implements Serializable {

    private int loanNumber;
    private String studentID, isbn, dateBorrowed;
    private double amountOwing;

    @Serial
    private static final long serialVersionUID = 1L;

    public Loan(String studentID, String isbn, double amountOwing) {
        this.studentID = studentID;
        this.isbn = isbn;
        this.amountOwing = amountOwing;
    }

    public int getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(int loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(String dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public double getAmountOwing() {
        return amountOwing;
    }

    public void setAmountOwing(double amountOwing) {
        this.amountOwing = amountOwing;
    }

    public long getNumberOfDaysExceeded() {
        LocalDate dateBorrowed = LocalDate.parse(this.dateBorrowed); // turn string to date
        LocalDate now = LocalDate.now();
        LocalDate returnDate = dateBorrowed.plusDays(7);

        long noOfDaysBetween = ChronoUnit.DAYS.between(now, returnDate);

        if (noOfDaysBetween > 0) {
            return 0;
        } else {
            return Math.abs(noOfDaysBetween);
        }
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loan_number=" + loanNumber +
                ", student_id='" + studentID + '\'' +
                ", isbn='" + isbn + '\'' +
                ", date_borrowed='" + dateBorrowed + '\'' +
                ", amount_owing=" + amountOwing +
                '}';
    }
}
