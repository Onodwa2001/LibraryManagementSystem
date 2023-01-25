package server;

import domain.Book;
import domain.Loan;
import domain.Staff;
import domain.User;
import model.BookDAO;
import model.LoanDAO;
import model.StaffDAO;
import model.UserDAO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    static String error;

    public static void updateAmounts() {
        ArrayList<Loan> loans = LoanDAO.getLoans();

        assert loans != null;

        for (Loan loan : loans) {
            LoanDAO.setAmountOwing(loan);
        }
    }

    public static void main(String[] args) throws IOException {

        updateAmounts();

//        UserDAO.

//        if (UserDAO)

        ServerSocket serverSocket;
        int port = 300;

        Socket socket;

        serverSocket = new ServerSocket(port);


        while (true) {
            try {

                System.out.println("Listening for requests at port " + port + "...");
                socket = serverSocket.accept(); // listens for requests here

                InputStream in = socket.getInputStream();
                ObjectInputStream obj = new ObjectInputStream(in);


                OutputStream out;
                ObjectOutputStream objOut;

                try {

                    switch (obj.readObject().toString()) {
                        case "give me books":

                            out = socket.getOutputStream();
                            objOut = new ObjectOutputStream(out);

                            System.out.println(BookDAO.getAllBooks());
                            objOut.writeObject(BookDAO.getAllBooks());
                            break;

                        case "give me users":

                            out = socket.getOutputStream();
                            objOut = new ObjectOutputStream(out);

                            System.out.println(UserDAO.getAllUsers());
                            objOut.writeObject(UserDAO.getAllUsers());
                            break;

                        case "add book":

                            Book bookToBeAdded = (Book) obj.readObject();
                            Book addedBook = addBook(bookToBeAdded);

                            out = socket.getOutputStream();
                            objOut = new ObjectOutputStream(out);

                            objOut.writeObject(addedBook);
                            break;

                        case "add user":

                            User userToBeAdded = (User) obj.readObject();
                            User addedUser = UserDAO.addUser(userToBeAdded);

                            out = socket.getOutputStream();
                            objOut = new ObjectOutputStream(out);

                            objOut.writeObject(addedUser);
                            break;

                        case "login request":

                            Staff staff = (Staff) obj.readObject();
                            Staff loggedIn = login(staff.getUsername(), staff.getPassword());

                            out = socket.getOutputStream();
                            objOut = new ObjectOutputStream(out);

                            if (loggedIn != null) {
                                objOut.writeObject(loggedIn);
                            } else {
                                objOut.writeObject("That staff member does not exist");
                            }

                            break;

                        case "borrow book":

                            // 1. Receive loan object
                            Loan loanRequest = (Loan) obj.readObject();

                            // 2. Go fetch me a book
                            Book bookFromDB = BookDAO.getBookByISBN(loanRequest.getIsbn());

                            // 3. Go fetch me a user
                            User userFromDB = UserDAO.getUserByID(loanRequest.getStudentID());

                            // 4. Grab those book and user object ids and isbn and make loan using loan instance
                            out = socket.getOutputStream();
                            objOut = new ObjectOutputStream(out);


                            if (bookFromDB == null) {
                                // tell client there is no book
                                objOut.writeObject("There is no such book");
                            } else if (userFromDB == null) {
                                // tell client there is no user
                                objOut.writeObject("Cannot find user");
                            } else {
                                Loan loanedBook = loanBook(bookFromDB, userFromDB, loanRequest);

                                if (loanedBook != null) {
                                    // send it back to the client
                                    objOut.writeObject(loanedBook);
                                } else {
                                    objOut.writeObject(error);
                                }
                            }

                            break;

                        case "return book":
                            // 1. Receive loan object
                            Loan returnRequest = (Loan) obj.readObject();

                            // 2. Go fetch me a book
                            Book bookFromDBReturn = BookDAO.getBookByISBN(returnRequest.getIsbn());

                            // 3. Go fetch me a user
                            User userFromDBReturn = UserDAO.getUserByID(returnRequest.getStudentID());

                            // 4. Grab those book and user object ids and isbn and make loan using loan instance
                            out = socket.getOutputStream();
                            objOut = new ObjectOutputStream(out);


                            if (bookFromDBReturn == null) {
                                // tell client there is no book
                                objOut.writeObject("There is no such book");
                            } else if (userFromDBReturn == null) {
                                // tell client there is no user
                                objOut.writeObject("Cannot find user");
                            } else {
                                Loan returnedBook = returnBook(bookFromDBReturn, userFromDBReturn, returnRequest);

                                // send it back to the client
                                objOut.writeObject(returnedBook);
                            }

                            break;

                        case "update book":
                            Book book = (Book) obj.readObject();

                            Book updatedBook = BookDAO.updateBook(book);

                            out = socket.getOutputStream();
                            objOut = new ObjectOutputStream(out);

                            objOut.writeObject(updatedBook);

                            break;

                        case "delete book":
                            Book bookUpdate = (Book) obj.readObject();
                            Book deletedBook = BookDAO.deleteBook(bookUpdate);

                            out = socket.getOutputStream();
                            objOut = new ObjectOutputStream(out);

                            if (deletedBook != null) {
                                objOut.writeObject(deletedBook);
                            } else {
                                objOut.writeObject("This book is still loaned out");
                            }

                            break;

                        default:
                            System.out.println("No request");

                    }

                } catch (ClassNotFoundException cls) {
                    cls.printStackTrace();
                }

//                OutputStream out = socket.getOutputStream();
//                ObjectOutputStream obOut = new ObjectOutputStream(out);
//
//                obOut.writeObject("Hey");


            } catch (IOException io) {
                io.printStackTrace();
            }
        }

    }


    /**
     *
     * @param book - book object holding input values
     * @param user - user object holding input values
     * @param loan - loan object holding input values
     * @return loan object
     */
    public static Loan loanBook(Book book, User user, Loan loan) {
        int count = UserDAO.numberOfBooksBorrowed(user);
        double amount = LoanDAO.amountOwing(user);

        loan.setAmountOwing(amount);

        // if count < 3 -> true else false
        boolean canBorrow = count < 3 || amount > 3;

        user.setCanBorrow(canBorrow);
        // update in database
        UserDAO.setCanBorrowDAO(user.getStudentID(), canBorrow);

        if (!book.isAvailableForLoan()) {
            error = "Book is not available";
        } else if (!user.canBorrow()) {
            error = "You have borrowed too many books";

            // handle overdue
            if (loan.getAmountOwing() >= 3)
                error = "You still have R " + loan.getAmountOwing() + " outstanding";

        } else {
            BookDAO.disableAvailability(book);
            return LoanDAO.addLoan(loan); // returns a loan object
        }
        return null;
    }

    public static Loan returnBook(Book book, User user, Loan loan) {
        int count = UserDAO.numberOfBooksBorrowed(user);
        double amount = LoanDAO.amountOwing(user);

        if (count <= 0) {
            error = "You didn't borrow any book";
            return null;
        } else {
            user.setCanBorrow(false);
        }

        if (book.isAvailableForLoan()) {
            error = "This book is already available for loan";
        } else {
            UserDAO.setCanBorrowDAO(user.getStudentID(), true);
            BookDAO.enableAvailability(book);
            return LoanDAO.removeLoan(loan); // returns a loan object
        }

        return loan;
    }


    public static Book addBook(Book book) {

        ArrayList<Book> booksList = BookDAO.getAllBooks();

        for (int i = 0; i < Objects.requireNonNull(booksList).size(); i++) {
            String isbn = booksList.get(i).getIsbn();
            if (isbn.equals(book.getIsbn())) {
                System.out.println("Book already exists");
                break;
            } else if (i >= booksList.size() -1) {
                System.out.println("Book successfully added");
                return BookDAO.addBook(book);
            }
        }

        return null;
    }

    public static Staff login(String username, String password) {
        ArrayList<Staff> staff = StaffDAO.getAllStaff();

        for (int i = 0; i < Objects.requireNonNull(staff).size(); i++) {
            String staffUsername = staff.get(i).getUsername();
            String staffPassword = staff.get(i).getPassword();

            if (staffUsername.equals(username) && staffPassword.equals(password)) {
                return new Staff(username, password);
            } else if (i >= staff.size() -1) {
                System.out.println("User does not exist");
            }
        }

        return null;
    }

}
