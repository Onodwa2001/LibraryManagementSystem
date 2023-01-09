/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Numata
 */
public class AddBook extends JFrame implements ActionListener {
    
    JPanel panelCenter, panelWest, rightPanelWrap, innerPanelCenter,innerPanelSouth;
    JLabel lblImageName, lblBookName, lblISBN, lblTitle, lblShelfNumber, lblCategory, lblAuthor;
    JTextField txtBookName, txtISBN, txtShelfNumber, txtCategory, txtAuthor;
    JButton btnAddBook, btnCancel, btnMenu, btnFile;

    static String ip = "192.168.18.11";
    static int port = 300;
    
    public AddBook() {
        super("Add Book");

        // panel instances
        panelCenter = new JPanel();
        panelWest = new JPanel();

        // label instances
        lblImageName = new JLabel("Image");

        lblBookName = new JLabel("Book Name");
        
        lblISBN = new JLabel("ISBN");
        
        lblShelfNumber = new JLabel("Shelf Number");
        
        lblCategory = new JLabel("Category");

        lblAuthor = new JLabel("Author");
        
        lblTitle = new JLabel("Library System", SwingConstants.CENTER);
        lblTitle.setForeground(Color.white);


        // textfield instances
        txtShelfNumber = new JTextField();
        txtCategory = new JTextField();
        txtBookName = new JTextField();
        txtISBN = new JTextField();
        txtAuthor = new JTextField();
        
        // button instances
        btnAddBook = new JButton("Add Book");
        btnCancel = new JButton("Cancel");
        btnMenu = new JButton("Menu");
        btnFile = new JButton("Select File");
        
        
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
        innerPanelCenter.add(lblBookName);
        innerPanelCenter.add(txtBookName);
        innerPanelCenter.add(lblISBN);
        innerPanelCenter.add(txtISBN);
        innerPanelCenter.add(lblAuthor);
        innerPanelCenter.add(txtAuthor);
        innerPanelCenter.add(lblShelfNumber);
        innerPanelCenter.add(txtShelfNumber);
        innerPanelCenter.add(lblCategory);
        innerPanelCenter.add(txtCategory);
        innerPanelCenter.add(lblImageName);
        innerPanelCenter.add(btnFile);

        
        innerPanelSouth.add(btnAddBook);
        innerPanelSouth.add(btnCancel);
        innerPanelSouth.add(btnMenu);
        
        // style inner panels
        rightPanelWrap.setPreferredSize(new Dimension(320, 200));
        rightPanelWrap.setBorder(new EmptyBorder(30, 30, 10, 30));
        rightPanelWrap.setBackground(Color.white);
        innerPanelCenter.setBackground(Color.white);
        innerPanelSouth.setBackground(Color.white);
        
        innerPanelSouth.setBorder(new EmptyBorder(30, 0, 0, 0));
        
        rightPanelWrap.setLayout(new GridLayout(2, 1));
        innerPanelCenter.setLayout(new GridLayout(6, 2));
        
        // adding panels to panelCenter
        panelCenter.add(rightPanelWrap, BorderLayout.CENTER);
        
        rightPanelWrap.add(innerPanelCenter, BorderLayout.CENTER);
        rightPanelWrap.add(innerPanelSouth, BorderLayout.SOUTH);
        
        lblTitle.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 30));
        lblBookName.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblISBN.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblAuthor.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblShelfNumber.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblCategory.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        
        btnCancel.setBackground(new Color(112, 198, 230));
        btnCancel.setForeground(Color.white);
        btnAddBook.setBackground(new Color(104, 151, 237));
        btnAddBook.setForeground(Color.white);
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
        
        btnAddBook.addActionListener(this);
        btnCancel.addActionListener(this);
        btnMenu.addActionListener(this);

        setVisible(true);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();

                chooser.setCurrentDirectory(new File("C:\\Users\\Numata\\Downloads"));

                int response = chooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(chooser.getSelectedFile().getAbsolutePath());
                    System.out.println(file); // file we want to save

                    String newPath = "images";
                    File directory = new File(newPath);

                    File source = null;
                    File dest = null;
                    String extension = file.toString().substring(file.toString().lastIndexOf('.') + 1);
                    source = new File(file.toString());
                    dest = new File(newPath + "/" + file.getName());
                    lblImageName.setText(file.getName());

                    try {
                        Files.copy(source.toPath(), dest.toPath());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        
    }

    public static Book addBookRequest(Book book) {

        try {
            Socket socket = new Socket(ip, port);

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(out);

            obj.writeObject("add book");
            obj.writeObject(book);

            InputStream in = socket.getInputStream();
            ObjectInputStream objIn = new ObjectInputStream(in);

            Book addedBook = (Book) objIn.readObject();
            return addedBook;

        } catch (Exception cls) {
            cls.printStackTrace();
        }

        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancel) {
            System.exit(0);
        } else if (e.getSource() == btnAddBook) {
            String isbn = txtISBN.getText();
            String category = txtCategory.getText();
            String author = txtAuthor.getText();
            String title = txtBookName.getText();
            String shelfNo = txtShelfNumber.getText();
            String image = lblImageName.getText();

            if (isbn.isEmpty() || category.isEmpty() || author.isEmpty()
                    || title.isEmpty() || shelfNo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter all fields");
            } else {
                Book book = new Book(title, author, isbn, category, shelfNo, image);
                Book addedBook = addBookRequest(book);

                if (addedBook != null) {
                    JOptionPane.showMessageDialog(null, "Thank you for using the system\n\"" + addedBook.getName()
                            + "\" has been added successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "That book's isbn is already taken");
                }
            }
        } else if (e.getSource() == btnMenu) {
            this.dispose();
            Menu m = new Menu();
            m.getBooks();
            m.setGUI();
        }
    }

    public static void main(String[] args) {
        new AddBook().setGUI();
    }


}
