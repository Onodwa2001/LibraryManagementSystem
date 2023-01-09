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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Numata
 */
public class OpeningFrame extends JFrame {
    
    JPanel panelCenter, panelWest, rightPanelWrap;
    JLabel lblTitle;
    JButton btnLogIn;
    
    
    public OpeningFrame() {
        super("Library Management System");
        
        // panel instances
        panelCenter = new JPanel();
        panelWest = new JPanel();
        
        // label instances
        lblTitle = new JLabel("Library System", SwingConstants.CENTER);
        lblTitle.setForeground(Color.white);
        
        
        // button instances
        btnLogIn = new JButton("Log in");
        
        
        // inner panel instances
        
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
        
        
        // style inner panels
        rightPanelWrap.setPreferredSize(new Dimension(300, 150));
        rightPanelWrap.setBorder(new EmptyBorder(57, 30, 20, 30));
        rightPanelWrap.setBackground(new Color(104, 151, 237));
        
        
//        rightPanelWrap.setLayout(new GridLayout(1, 2));
        
        // adding panels to panelCenter
        panelCenter.add(rightPanelWrap, BorderLayout.CENTER);
        
        
        lblTitle.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 30));
        
        panelWest.setLayout(new GridLayout(3, 1));
        
        // add image
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(new File("Black_open_book.png"));
            ImageIcon icon = new ImageIcon(new ImageIcon("Black_open_book.png").getImage().getScaledInstance(150, 90,Image.SCALE_DEFAULT));
//            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("Black_open_book.png"));


            JLabel picLabel = new JLabel("", SwingConstants.CENTER);
            picLabel.setIcon(icon);
            panelWest.add(picLabel);
        } catch (IOException ex) {
            Logger.getLogger(StaffLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btnLogIn.setBackground(Color.WHITE);
        btnLogIn.setBorder(new EmptyBorder(10, 30, 10, 30));

        rightPanelWrap.add(btnLogIn);
        
        
        
        // add panels to frame
        add(panelWest, BorderLayout.WEST);
        add(panelCenter, BorderLayout.CENTER);
        
        
        setVisible(true);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // send to log in page when clicked
        btnLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StaffLogin().setGUI();
                OpeningFrame.this.dispose();
            }
        });
        
        
    }
    
    public static void main(String[] args) {
        new OpeningFrame().setGUI();
    }
    
}
