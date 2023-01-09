/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Numata
 */
public class RegisterUser extends JFrame implements ActionListener {
    
    JPanel panelCenter, panelWest, rightPanelWrap, innerPanelCenter,innerPanelSouth;
    JLabel lblFirstName, lblLastName, lblUserID, lblTitle;
    JTextField txtFirstName, txtLastName, txtUserID;
    JButton btnRegister, btnMenu, btn;

    static String ip = "192.168.18.11";
    static int port = 300;
    static String username;

    public RegisterUser() {
        super("Register User");
        
        // panel instances
        panelCenter = new JPanel();
        panelWest = new JPanel();
        
        // label instances
        lblFirstName = new JLabel("First Name");
        
        lblLastName = new JLabel("Last Name");
        
        lblUserID = new JLabel("User ID");
        
        
        lblTitle = new JLabel("Library System", SwingConstants.CENTER);
        lblTitle.setForeground(Color.white);
        
        // textfield instances
        txtUserID = new JTextField();
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        
        // button instances
        btnRegister = new JButton("Register");
        btnMenu = new JButton("Menu");
        
        
        // inner panel instances
        innerPanelCenter = new JPanel();
        innerPanelSouth = new JPanel();
        
        rightPanelWrap = new JPanel();

        btnMenu.addActionListener(this);
        btnRegister.addActionListener(this);
        
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
        innerPanelCenter.add(lblFirstName);
        innerPanelCenter.add(txtFirstName);
        innerPanelCenter.add(lblLastName);
        innerPanelCenter.add(txtLastName);
        innerPanelCenter.add(lblUserID);
        innerPanelCenter.add(txtUserID);
        
        innerPanelSouth.add(btnRegister);
        innerPanelSouth.add(btnMenu);
        
        // style inner panels
        rightPanelWrap.setPreferredSize(new Dimension(320, 200));
        rightPanelWrap.setBorder(new EmptyBorder(30, 30, 20, 30));
        rightPanelWrap.setBackground(Color.white);
        innerPanelCenter.setBackground(Color.white);
        innerPanelSouth.setBackground(Color.white);
        
        innerPanelSouth.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        rightPanelWrap.setLayout(new GridLayout(2, 1));
        innerPanelCenter.setLayout(new GridLayout(3, 2));
        
        // adding panels to panelCenter
        panelCenter.add(rightPanelWrap, BorderLayout.CENTER);
        
        rightPanelWrap.add(innerPanelCenter, BorderLayout.CENTER);
        rightPanelWrap.add(innerPanelSouth, BorderLayout.SOUTH);
        
        lblTitle.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 30));
        lblFirstName.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblLastName.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblUserID.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        
        btnMenu.setBackground(new Color(112, 198, 230));
        btnMenu.setForeground(Color.white);
        btnRegister.setBackground(new Color(104, 151, 237));
        btnRegister.setForeground(Color.white);
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
        
        
        setVisible(true);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
    }


//    public static Object getUsers() {
//
//        try {
//            Socket socket = new Socket(ip, port);
//
//            OutputStream out = socket.getOutputStream();
//            ObjectOutputStream objOut = new ObjectOutputStream(out);
//
//            objOut.writeObject("give me users");
//
//            InputStream in = socket.getInputStream();
//            ObjectInputStream obj = new ObjectInputStream(in);
//
//            try {
//                allUsersFromDatabase = (ArrayList<User>) obj.readObject();
//                return allUsersFromDatabase;
//            } catch (ClassNotFoundException cls) {
//                cls.printStackTrace();
//            }
//
//        } catch (Exception cls) {
//            cls.printStackTrace();
//        }
//
//        return null;
//    }


    public static User addUserRequest(User user) {

        try {
            Socket socket = new Socket(ip, port);

            OutputStream out = socket.getOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(out);

            obj.writeObject("add user");
            obj.writeObject(user);

            InputStream in = socket.getInputStream();
            ObjectInputStream objIn = new ObjectInputStream(in);

            User addedUser = (User) objIn.readObject();
            return addedUser;

        } catch (Exception cls) {
            cls.printStackTrace();
        }

        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnRegister) {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String id = txtUserID.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter all fields");
            } else {

                User user = new User(firstName, lastName, id);
                User addedUser = addUserRequest(user);

                if (addedUser != null) {
                    JOptionPane.showMessageDialog(null, "User account successfully created");
                } else {
                    JOptionPane.showMessageDialog(null, "User id already exists");
                }
            }
        } else if (e.getSource() == btnMenu) {
            this.dispose();
            Menu m = new Menu();
            m.username = username;
            m.getBooks();
            m.setGUI();
        }
    }
    
    public static void main(String[] args) {
        new RegisterUser().setGUI();
    }


}
