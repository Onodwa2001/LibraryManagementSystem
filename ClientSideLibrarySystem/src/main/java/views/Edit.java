package views;

import domain.Book;
import domain.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Numata
 */
public class Edit extends JFrame implements ActionListener, KeyListener {

    JPanel panelCenter, panelWest, rightPanelWrap, innerPanelCenter,innerPanelSouth;
    JLabel lblBookName, lblISBN, lblTitle, lblShelfNumber, lblCategory, lblAuthor, lblInstruction;
    JTextField txtISBN;
    JTextField txtBookName, txtShelfNumber, txtCategory, txtAuthor;
    JButton btnUpdateBook, btnDelete, btnMenu;

    static ArrayList<Book> allBooksFromDatabase = getBooks();

    public Edit() {
        super("More Options");

        // panel instances
        panelCenter = new JPanel();
        panelWest = new JPanel();

        // label instances
        lblBookName = new JLabel("Book Name");

        lblISBN = new JLabel("ISBN");

        lblShelfNumber = new JLabel("Shelf Number");

        lblCategory = new JLabel("Category");

        lblAuthor = new JLabel("Author");

        lblInstruction = new JLabel("Enter the ISBN and then the <ENTER> button to autofill");

        lblTitle = new JLabel("Library System", SwingConstants.CENTER);
        lblTitle.setForeground(Color.white);

        txtISBN = new JTextField();

        // textfield instances
        txtShelfNumber = new JTextField();
        txtCategory = new JTextField();
        txtBookName = new JTextField();
        txtAuthor = new JTextField();

        // button instances
        btnUpdateBook = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnMenu = new JButton("Menu");


        // inner panel instances
        innerPanelCenter = new JPanel();
        innerPanelSouth = new JPanel();

        rightPanelWrap = new JPanel();

    }

    public void setGUI() {

        // set bg image
//        ImageIcon background=new ImageIcon("background.jpg");
//        Image img=background.getImage();
//        Image temp=img.getScaledInstance(800,500,Image.SCALE_SMOOTH);
//        background=new ImageIcon(temp);
//        JLabel back=new JLabel(background);
//        back.setLayout(null);
//        back.setBounds(0,0,800,500);


        // add components to center gui
        panelCenter.setBackground(new Color(104, 151, 237));
        panelCenter.setBorder(new EmptyBorder(120, 0, 0, 0));

        panelWest.setBackground(new Color(112, 198, 230));
        panelWest.add(lblTitle);
        panelWest.setPreferredSize(new Dimension(380, 480));

        // add components to inner panels
        innerPanelCenter.add(lblISBN);
        innerPanelCenter.add(txtISBN);
        innerPanelCenter.add(lblBookName);
        innerPanelCenter.add(txtBookName);
        innerPanelCenter.add(lblAuthor);
        innerPanelCenter.add(txtAuthor);
        innerPanelCenter.add(lblShelfNumber);
        innerPanelCenter.add(txtShelfNumber);
        innerPanelCenter.add(lblCategory);
        innerPanelCenter.add(txtCategory);

        innerPanelSouth.add(btnUpdateBook);
        innerPanelSouth.add(btnDelete);
        innerPanelSouth.add(btnMenu);

        // style inner panels
        rightPanelWrap.setPreferredSize(new Dimension(320, 200));
        rightPanelWrap.setBorder(new EmptyBorder(30, 30, 10, 30));
        rightPanelWrap.setBackground(Color.white);
        innerPanelCenter.setBackground(Color.white);
        innerPanelSouth.setBackground(Color.white);

        innerPanelSouth.setBorder(new EmptyBorder(30, 0, 0, 0));

        rightPanelWrap.setLayout(new GridLayout(2, 1));
        innerPanelCenter.setLayout(new GridLayout(5, 2));

        // adding panels to panelCenter
        lblInstruction.setForeground(Color.WHITE);
        panelCenter.add(lblInstruction);
        panelCenter.add(rightPanelWrap, BorderLayout.CENTER);

        rightPanelWrap.add(innerPanelCenter, BorderLayout.CENTER);
        rightPanelWrap.add(innerPanelSouth, BorderLayout.SOUTH);

        lblTitle.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 30));
        lblBookName.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblISBN.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblAuthor.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblShelfNumber.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblCategory.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));

        btnDelete.setBackground(new Color(112, 198, 230));
        btnDelete.setForeground(Color.white);
        btnUpdateBook.setBackground(new Color(104, 151, 237));
        btnUpdateBook.setForeground(Color.white);
        btnMenu.setBackground(new Color(30, 83, 170));
        btnMenu.setForeground(Color.white);
        panelWest.setLayout(new GridLayout(3, 1));

        // add image
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(new File("Black_open_book.png"));
            ImageIcon icon = new ImageIcon(new ImageIcon("Black_open_book.png").getImage().getScaledInstance(150, 90,Image.SCALE_DEFAULT));

            JLabel picLabel = new JLabel("", SwingConstants.CENTER);
            picLabel.setIcon(icon);
            panelWest.add(picLabel);
        } catch (IOException ex) {
            Logger.getLogger(StaffLogin.class.getName()).log(Level.SEVERE, null, ex);
        }


        // add panels to frame
        add(panelWest, BorderLayout.WEST);
        add(panelCenter, BorderLayout.CENTER);

        btnUpdateBook.addActionListener(this);
        btnDelete.addActionListener(this);
        btnMenu.addActionListener(this);

        setVisible(true);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtISBN.addKeyListener(this);

    }

    public static Object editBookRequest(Book book, String requestType) {

        int port = 300;
        String ip = "192.168.18.11";

        try {
            Socket socket = new Socket(ip, port);

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(out);

            obj.writeObject(requestType);
            obj.writeObject(book);

            InputStream in = socket.getInputStream();
            ObjectInputStream objIn = new ObjectInputStream(in);

            return objIn.readObject();

        } catch (Exception cls) {
            cls.printStackTrace();
        }


        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String isbn = txtISBN.getText();
        String category = txtCategory.getText();
        String author = txtAuthor.getText();
        String title = txtBookName.getText();
        String shelfNo = txtShelfNumber.getText();

        boolean isEmpty = isbn.isEmpty() || category.isEmpty() || author.isEmpty()
                || title.isEmpty() || shelfNo.isEmpty();

        if (e.getSource() == btnDelete) {
            // handle delete
            if (isEmpty) {
                JOptionPane.showMessageDialog(null, "Please enter all fields");
            } else {
                Book book = new Book(title, author, isbn, category, shelfNo);
                Object deletedBook = editBookRequest(book, "delete book");

                if (deletedBook instanceof Book) {
                    JOptionPane.showMessageDialog(null, "Thank you for using the system\n\"" + ((Book) deletedBook).getName()
                            + "\" has been successfully deleted");
                } else {
                    JOptionPane.showMessageDialog(null, deletedBook);
                }
            }
        } else {

            if (e.getSource() == btnUpdateBook) {

                if (isEmpty) {
                    JOptionPane.showMessageDialog(null, "Please enter all fields");
                } else {
                    Book book = new Book(title, author, isbn, category, shelfNo);
                    Object updatedBook = editBookRequest(book, "update book");

                    if (updatedBook instanceof Book) {
                        JOptionPane.showMessageDialog(null, "Thank you for using the system\n\"" + ((Book) updatedBook).getName()
                                + "\" has been successfully updated");
                    } else {
                        JOptionPane.showMessageDialog(null, "There's no such book");
                    }
                }

            } else if (e.getSource() == btnMenu) {
                this.dispose();
                Menu m = new Menu();
                m.username = "Admin@Lib";
                m.getBooks();
                m.setGUI();
            }
        }
    }

    public static ArrayList<Book> getBooks() {

        String ip = "192.168.18.11";
        int port = 300;

        try {
            Socket socket = new Socket(ip, port);

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out);

            objOut.writeObject("give me books");

            InputStream in = socket.getInputStream();
            ObjectInputStream obj = new ObjectInputStream(in);

            try {
                return (ArrayList<Book>) obj.readObject();
            } catch (ClassNotFoundException cls) {
                cls.printStackTrace();
            }

        } catch (Exception cls) {
            cls.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        new Edit().setGUI();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            String isbn = txtISBN.getText();

            // get book based on id but first check if id exists
            for (int i = 0; i < allBooksFromDatabase.size(); i++) {
                String value = allBooksFromDatabase.get(i).getIsbn();

                String bookName = allBooksFromDatabase.get(i).getName();
                String author = allBooksFromDatabase.get(i).getAuthor();
                String shelfNumber = allBooksFromDatabase.get(i).getShelfNumber();
                String category = allBooksFromDatabase.get(i).getCategory();

                if (value.equals(isbn)) {
                    txtBookName.setText(bookName);
                    txtAuthor.setText(author);
                    txtShelfNumber.setText(shelfNumber);
                    txtCategory.setText(category);
                    break;
                } else if (i >= allBooksFromDatabase.size() -1) {
                    JOptionPane.showMessageDialog(null, "No such isbn");
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
