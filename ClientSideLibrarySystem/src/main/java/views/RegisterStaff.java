/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Numata
 */
public class RegisterStaff extends JFrame {
    
    JPanel panelCenter, panelWest, rightPanelWrap, innerPanelCenter,innerPanelSouth;
    JLabel lblUsername, lblPassword, lblTitle, lblConfirm;
    JTextField txtUsername, txtPassword, txtConfim;
    JButton btnLogin, btnCancel, btn;
    
    
    public RegisterStaff() {
        super("Register Staff");
        
        // panel instances
        panelCenter = new JPanel();
        panelWest = new JPanel();
        
        // label instances
        lblUsername = new JLabel("Staff ID");
//        lblUsername.setForeground(Color.white);
        
        lblPassword = new JLabel("Password");
//        lblPassword.setForeground(Color.white);

        lblConfirm = new JLabel("Confirm Password");
        
        lblTitle = new JLabel("Library System", SwingConstants.CENTER);
        lblTitle.setForeground(Color.white);
        
        // textfield instances
        txtUsername = new JTextField();
        txtPassword = new JTextField();
        txtConfim = new JTextField();
        
        // button instances
        btnLogin = new JButton("Log in");
        btnCancel = new JButton("Cancel");
        
        
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
        innerPanelCenter.add(lblUsername);
        innerPanelCenter.add(txtUsername);
        innerPanelCenter.add(lblPassword);
        innerPanelCenter.add(txtPassword);
        innerPanelCenter.add(lblConfirm);
        innerPanelCenter.add(txtConfim);
        
        innerPanelSouth.add(btnLogin);
        innerPanelSouth.add(btnCancel);
        
        // style inner panels
        rightPanelWrap.setPreferredSize(new Dimension(320, 180));
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
        lblPassword.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblUsername.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        lblConfirm.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
        
        btnCancel.setBackground(new Color(112, 198, 230));
        btnCancel.setForeground(Color.white);
        btnLogin.setBackground(new Color(104, 151, 237));
        btnLogin.setForeground(Color.white);
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
    
    public static void main(String[] args) {
        new RegisterStaff().setGUI();
    }
    
}
