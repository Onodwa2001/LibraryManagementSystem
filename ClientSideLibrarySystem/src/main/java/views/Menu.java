/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Book;
import domain.Loan;
import domain.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Numata
 */
public class Menu extends JFrame implements ActionListener {
    
    JPanel panelCenter, panelWest, northWestPanel, centerWestPanel, southWestPanel, northCenterPanel, centerCenterPanel;
    JLabel lblUsername, lblAddBook, lblRegisterUser, lblBorroBook, lblReturnBook, lblInfo;
    JButton btnLogout, btnSearch, btnMore;
    JTextField txtSearch;
    JTable jt;
    
    String id, isbn, username;

    static ArrayList<Book> allBooksFromDatabase = new ArrayList<>();
    static ArrayList<User> allUsersFromDatabase = new ArrayList<>();

    static String ip = "192.168.18.11";
    static int port = 300;

    String[][] booksArray = null;
    
    public Menu() {
        super("Main menu");

        allUsersFromDatabase = (ArrayList<User>) getUsers();

        System.out.println("Main Menu Frame Running...");
        
        // panel instances
        panelCenter = new JPanel();
        panelWest = new JPanel();
        northWestPanel = new JPanel();
        centerWestPanel = new JPanel();
        southWestPanel = new JPanel();
        northCenterPanel = new JPanel();
        centerCenterPanel = new JPanel();
        
        // label instances
        lblUsername = new JLabel(username, SwingConstants.CENTER);
        lblAddBook = new JLabel("Add Book");
        lblRegisterUser = new JLabel("Register User");
        lblBorroBook = new JLabel("Borrow Book");
        lblReturnBook = new JLabel("Return Book");
        lblInfo = new JLabel("?");
        
        // button instances
        btnLogout = new JButton("Log out");
        btnSearch = new JButton("Search");
        btnMore = new JButton("More Options");
        
        // text field instance
        txtSearch = new JTextField("Search");
        
    }
    
    public void setGUI() {
        lblUsername = new JLabel(username, SwingConstants.CENTER);
        
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
        txtSearch.setPreferredSize(new Dimension(200, 30));
        btnSearch.setPreferredSize(new Dimension(80, 30));
        btnSearch.setBackground(new Color(112, 198, 230));
        btnSearch.setForeground(Color.WHITE);
        
        
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

        // if logged-in user is admin
        if (username != null && username.equals("Admin@Lib")) {
            centerWestPanel.add(btnMore);
            centerWestPanel.setLayout(new GridLayout(5, 1));
            btnMore.setBackground(new Color(112, 198, 230));
            btnMore.setForeground(Color.WHITE);

            btnMore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Menu.this.dispose();
                    new Edit().setGUI();
                }
            });
        } else {
            centerWestPanel.setLayout(new GridLayout(4, 1));
        }

        centerWestPanel.setBorder(new EmptyBorder(0, 20, 0, 40));
        southWestPanel.add(btnLogout);
        southWestPanel.setBorder(new EmptyBorder(40, 0, 0, 0));
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
        northCenterPanel.setPreferredSize(new Dimension(400, 100));
        northCenterPanel.setBackground(Color.WHITE);
        northCenterPanel.setBorder(new EmptyBorder(24, 0, 0, 0));
        centerCenterPanel.setPreferredSize(new Dimension(400, 400));
        
        centerCenterPanel.add(lblInfo);
        // add table to center panel
//        String booksArray[][]={ {"123-244-334-3455", "Amit", "Yes", "E3", "Science Fiction"},
//                          {"102-483-838-3982", "Jai", "No", "D12", "Novel"},
//                          {"101-293-383-4893", "Sachin", "Yes", "A9", "Comics"}};

        System.out.println(allBooksFromDatabase.get(allBooksFromDatabase.size() -1));
        booksArray = new String[allBooksFromDatabase.size()][6];

        for (int i = 0; i < allBooksFromDatabase.size(); i++) {
            Book book = allBooksFromDatabase.get(i);
            booksArray[i][0] = book.getIsbn();
            booksArray[i][1] = book.getName();
            booksArray[i][2] = book.getAuthor();
            booksArray[i][3] = book.getShelfNumber();
            booksArray[i][4] = book.getCategory();
            booksArray[i][5] = book.getImage();
        }

        String[] column = {"ISBN", "TITLE", "AUTHOR", "SHELF NO.", "CATEGORY"};
        jt = new JTable(booksArray, column);
        jt.setBounds(30,40,200,230);    
        jt.setDefaultEditor(Object.class, null);
        JScrollPane sp = new JScrollPane(jt);
    
        centerCenterPanel.add(sp);
        centerCenterPanel.setLayout(new BoxLayout(centerCenterPanel, BoxLayout.Y_AXIS));
        centerCenterPanel.setBorder(new EmptyBorder(20, 40, 30, 40));
        
        // add to center panel > north
        northCenterPanel.add(txtSearch);
        northCenterPanel.add(btnSearch);
        
        // add panels to west panel
        panelWest.add(northWestPanel);
        panelWest.add(centerWestPanel);
        panelWest.add(southWestPanel);
        
        // add panels to center panel
        panelCenter.add(northCenterPanel);
        panelCenter.add(centerCenterPanel);
    
        // add panels to frame
        add(panelWest, BorderLayout.WEST);
        add(panelCenter, BorderLayout.CENTER);
        
        setVisible(true);
        setSize(820, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        // table events
        ListSelectionModel model = jt.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!model.getValueIsAdjusting()) {
                    String isbn2 = jt.getValueAt(model.getAnchorSelectionIndex(), 0).toString();
                    id = JOptionPane.showInputDialog("Enter student ID: ");


                    System.out.println(allUsersFromDatabase);
                    if (id != null) {
                        if (id.equals("")) {
                            JOptionPane.showMessageDialog(null, "No value entered");
                        } else {

                            String searchValueOfUser = "";

                            for (int i = 0; i < allUsersFromDatabase.size(); i++) {
                                if (id.equals(allUsersFromDatabase.get(i).getStudentID())) {
                                    searchValueOfUser = "Found";
                                    break;
                                } else if (i >= allUsersFromDatabase.size() -1) {
                                    searchValueOfUser = "Not found";
                                }
                            }

                            if (searchValueOfUser.equals("Found")) {
                                String title = jt.getValueAt(model.getAnchorSelectionIndex(), 1).toString();
                                String author = jt.getValueAt(model.getAnchorSelectionIndex(), 2).toString();
                                String shelf_number = jt.getValueAt(model.getAnchorSelectionIndex(), 3).toString();
                                String category = jt.getValueAt(model.getAnchorSelectionIndex(), 4).toString();

                                String image = booksArray[model.getAnchorSelectionIndex()][5];

                                int index = model.getAnchorSelectionIndex();
                                Book selectedBookObject = allBooksFromDatabase.get(index);

                                Book book = new Book(title, author, isbn2, category, shelf_number, image);
                                book.setIsAvailableForLoan(selectedBookObject.isAvailableForLoan());

                                Menu.this.dispose();
                                Details details = new Details();
                                details.username = Menu.this.username;
                                details.setID(id);
                                details.setBook(book);
                                details.setGUI();
                            } else {
                                JOptionPane.showMessageDialog(null, "This user does not exist");
                            }
                        }
                    }

                }
            }
        });

        // search functionality
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = txtSearch.getText();

                if (!search.equals("Search")) {
                    Menu.this.dispose();

                    Menu m = new Menu();
                    m.username = Menu.this.username;
                    m.getBooks();

                    System.out.println("Executing code");
                    for (int i = 0; i < allBooksFromDatabase.size(); i++) {

                        String isbn = allBooksFromDatabase.get(i).getIsbn();
                        String title = allBooksFromDatabase.get(i).getName();
                        String author = allBooksFromDatabase.get(i).getAuthor();
                        String shelfNumber = allBooksFromDatabase.get(i).getShelfNumber();
                        String category = allBooksFromDatabase.get(i).getCategory();

                        if (isbn.contains(search) || title.contains(search) || author.contains(search) ||
                                shelfNumber.contains(search) || category.contains(search)) {
                            allBooksFromDatabase = new ArrayList<>();
                            allBooksFromDatabase.add(new Book(title, author, isbn, category, shelfNumber));
                            System.out.println(i);
                        } else if (i >= allBooksFromDatabase.size() - 1) {
                            allBooksFromDatabase = new ArrayList<>();
                        }
                    }

                    m.setGUI();
                } else {
                    Menu.this.dispose();

                    Menu m = new Menu();
                    m.username = Menu.this.username;
                    m.getBooks();
                    m.setGUI();
                }

            }
        });
    
        // label event
        lblInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Click on row to see details");
                System.out.println(id);
            }
        });


        // add book event
        lblAddBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Menu.this.dispose();
                new AddBook().setGUI();
            }
        });

        // register user event
        lblRegisterUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Menu.this.dispose();
                RegisterUser r = new RegisterUser();
                r.username = Menu.this.username;
                r.setGUI();
            }
        });

        // borrow event
        lblBorroBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                id = JOptionPane.showInputDialog("Enter student ID");
                isbn = JOptionPane.showInputDialog("Enter ISBN");
                
                // process loan and update database
                Loan loan = new Loan(id, isbn, 0.0);

                JOptionPane.showMessageDialog(null, loanBookConnection(loan, "borrow book"));
            }
        });

        // return event
        lblReturnBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                id = JOptionPane.showInputDialog("Enter student ID");
                isbn = JOptionPane.showInputDialog("Enter ISBN");
                
                // process loan and update database
                Loan loan = new Loan(id, isbn, 0.0);

                JOptionPane.showMessageDialog(null, loanBookConnection(loan, "return book"));
            }
        });

        btnMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(isbn);
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu.this.dispose();
                new OpeningFrame().setGUI();
            }
        });
        
        
        // text field listener
        txtSearch.setForeground(Color.GRAY);
            txtSearch.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (txtSearch.getText().equals("Search")) {
                        txtSearch.setText("");
                        txtSearch.setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (txtSearch.getText().isEmpty()) {
                        txtSearch.setForeground(Color.GRAY);
                        txtSearch.setText("Search");
                    }
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

    public static Object getUsers() {

        try {
            Socket socket = new Socket(ip, port);

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out);

            objOut.writeObject("give me users");

            InputStream in = socket.getInputStream();
            ObjectInputStream obj = new ObjectInputStream(in);

            try {
                allUsersFromDatabase = (ArrayList<User>) obj.readObject();
                return allUsersFromDatabase;
            } catch (ClassNotFoundException cls) {
                cls.printStackTrace();
            }

        } catch (Exception cls) {
            cls.printStackTrace();
        }

        return null;
    }

    public void getBooks() {

        try {
            Socket socket = new Socket(ip, port);

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out);

            objOut.writeObject("give me books");

            InputStream in = socket.getInputStream();
            ObjectInputStream obj = new ObjectInputStream(in);

            try {
                allBooksFromDatabase = (ArrayList<Book>) obj.readObject();
                System.out.println(allBooksFromDatabase);

            } catch (ClassNotFoundException cls) {
                cls.printStackTrace();
            }

        } catch (Exception cls) {
            cls.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public static void main(String[] args) throws IOException {

        Menu m = new Menu();
        m.getBooks();
        m.setGUI();

    }

}
