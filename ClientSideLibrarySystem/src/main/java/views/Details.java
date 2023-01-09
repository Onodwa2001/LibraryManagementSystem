/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Book;
import domain.Loan;
import domain.Staff;
import domain.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Numata
 */
public class Details extends JFrame {
    
    JPanel panelCenter, panelWest, northWestPanel, centerWestPanel, southWestPanel, panelForButtons;
    JLabel lblUsername, lblAddBook, lblRegisterUser, lblBorroBook, lblReturnBook, lblTitle, 
            lblAuthor, lblISBN, lblIsAvailableForLoan, lblCategory, lblImage, lblShelfNo;
    JButton btnLogout, btnBorrow, btnBack;
    
    String id, isbn, username;

    static String ip = "192.168.18.11";
    static int port = 300;

    Book book = new Book();

    public Details() {
        super("Main menu");
        
        // panel instances
        panelCenter = new JPanel();
        panelWest = new JPanel();
        northWestPanel = new JPanel();
        centerWestPanel = new JPanel();
        southWestPanel = new JPanel();
        panelForButtons = new JPanel();
        
        // label instances
        lblUsername = new JLabel("Username", SwingConstants.CENTER);
        lblAddBook = new JLabel("Add Book");
        lblRegisterUser = new JLabel("Register User");
        lblBorroBook = new JLabel("Borrow Book");
        lblReturnBook = new JLabel("Return Book");
        
        lblTitle = new JLabel("Lord of the Rings");
        lblImage = new JLabel("Image");
        lblAuthor = new JLabel("Author: John Doe");
        lblISBN = new JLabel("ISBN: 122-958-228-4938");
        lblIsAvailableForLoan = new JLabel("Available For Loan: Yes");
        lblCategory = new JLabel("Category: Science Fiction");
        lblShelfNo = new JLabel("Shelf Number: E3");
        
        // button instances
        btnLogout = new JButton("Log out");
        btnBorrow = new JButton("Borrow Book");
        btnBack = new JButton("Back");
        
        // text field instance
        
    }

    public void setID(String id) {
        this.id = id;
    }
    
    public void setGUI() {
        lblUsername = new JLabel(username, SwingConstants.CENTER);

        // set values to chosen book object
        lblTitle = new JLabel(book.getName());
        lblImage = new JLabel("Image");
        lblAuthor = new JLabel("Author: " + book.getAuthor());
        lblISBN = new JLabel("ISBN: " + book.getIsbn());
        lblIsAvailableForLoan = new JLabel("Available For Loan: " + (book.isAvailableForLoan() ? "Yes" : "No"));
        lblCategory = new JLabel("Category: " + book.getCategory());
        lblShelfNo = new JLabel("Shelf Number: " + book.getShelfNumber());


        // style panels
        panelWest.setBackground(new Color(104, 151, 237));
        panelWest.setPreferredSize(new Dimension(200, 500));
        panelCenter.setBackground(Color.WHITE);
        northWestPanel.setLayout(new GridLayout(2, 1));
        panelWest.setLayout(new GridLayout(3, 1));
        northWestPanel.setBackground(new Color(104, 151, 237));
        centerWestPanel.setBackground(new Color(104, 151, 237));
        southWestPanel.setBackground(new Color(104, 151, 237));
        
        // style components
        lblUsername.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 18));
        lblUsername.setForeground(Color.WHITE);
        lblAddBook.setForeground(Color.WHITE);
        lblBorroBook.setForeground(Color.WHITE);
        lblRegisterUser.setForeground(Color.WHITE);
        lblReturnBook.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(112, 198, 230));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBorder(new EmptyBorder(10, 30, 10, 30));
        btnBorrow.setForeground(Color.WHITE);
        btnBorrow.setBackground(new Color(112, 198, 230));
        btnBorrow.setBorder(new EmptyBorder(10, 40, 10, 40));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(new Color(112, 198, 230));


        lblTitle.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 30));
        
        
        // add components to west panel
        // add image
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(new File("Black_open_book.png"));
            ImageIcon icon = new ImageIcon(new ImageIcon("Black_open_book.png").getImage().getScaledInstance(120, 60,Image.SCALE_DEFAULT));
            
            JLabel picLabel = new JLabel("", SwingConstants.CENTER);
            picLabel.setIcon(icon);
            northWestPanel.add(picLabel);
            northWestPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        } catch (IOException ex) {
            Logger.getLogger(StaffLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        northWestPanel.add(lblUsername);
        centerWestPanel.add(lblAddBook);
        centerWestPanel.add(lblRegisterUser);
        centerWestPanel.add(lblBorroBook);
        centerWestPanel.add(lblReturnBook);
        centerWestPanel.setLayout(new GridLayout(4, 1));
        centerWestPanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        southWestPanel.add(btnLogout);
        southWestPanel.setBorder(new EmptyBorder(40, 0, 0, 0));
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
        
        
        // add components to center
        panelCenter.add(btnBack);
        panelCenter.add(lblTitle);
        
        BufferedImage myPicture2;
        try {
            myPicture2 = ImageIO.read(new File("thera-unifinalteal.png"));
            ImageIcon icon = new ImageIcon(new ImageIcon("images/" + book.getImage()).getImage().getScaledInstance(160, 200,Image.SCALE_DEFAULT));
            
            JLabel picLabel = new JLabel("", SwingConstants.CENTER);
            picLabel.setIcon(icon);
            panelCenter.add(picLabel);
            northWestPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        } catch (IOException ex) {
            Logger.getLogger(StaffLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        panelCenter.add(lblAuthor);
        panelCenter.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCenter.add(lblISBN);
        panelCenter.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCenter.add(lblIsAvailableForLoan);
        panelCenter.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCenter.add(lblCategory);
        panelCenter.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCenter.add(lblShelfNo);
        panelCenter.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCenter.add(btnBorrow);

        panelCenter.setBorder(new EmptyBorder(20, 30, 0, 0));
        
        
        // add panels to west panel
        panelWest.add(northWestPanel);
        panelWest.add(centerWestPanel);
        panelWest.add(southWestPanel);
        
    
        // add panels to frame
        add(panelWest, BorderLayout.WEST);
        add(panelCenter, BorderLayout.CENTER);
        
        setVisible(true);
        setSize(820, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Details.this.dispose();

                Menu menu = new Menu();
                menu.username = Details.this.username;
                menu.getBooks();
                menu.setGUI();
            }
        });

        // add book event
        lblAddBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Details.this.dispose();
                new AddBook().setGUI();
            }
        });

        // register user event
        lblRegisterUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Details.this.dispose();
                new RegisterUser().setGUI();
            }
        });

        btnBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        
        lblBorroBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                id = JOptionPane.showInputDialog("Enter student ID");
                isbn = JOptionPane.showInputDialog("Enter ISBN");
                
                // process loan and update database
                System.out.println(isbn);
                System.out.println(id);
            }
        });
        
        lblReturnBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                id = JOptionPane.showInputDialog("Enter student ID");
                isbn = JOptionPane.showInputDialog("Enter ISBN");
                
                // process loan and update database
                System.out.println(isbn);
                System.out.println(id);
            }
        });

        btnBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Loan loan = new Loan(id, book.getIsbn(), 0.0);

                JOptionPane.showMessageDialog(null, loanBookConnection(loan, "borrow book"));
            }
        });


        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Details.this.dispose();
                new OpeningFrame().setGUI();
            }
        });
    }

    public static Object loanBookConnection(Loan loan, String requestType) {

        try {
            Socket socket = new Socket(ip, port);

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(out);

            obj.writeObject(requestType);
            obj.writeObject(loan);

            // loan (borrow or return) book response
            InputStream in = socket.getInputStream();
            ObjectInputStream onIn = new ObjectInputStream(in);

            return onIn.readObject();

        } catch (Exception cls) {
            cls.printStackTrace();
        }

        return null;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    
    
    public static void main(String[] args) {
        new Details().setGUI();
        
    }
    
}
