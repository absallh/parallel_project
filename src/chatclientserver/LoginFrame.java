/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclientserver;

import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author sondos
 */
public class LoginFrame extends JFrame implements ActionListener  {
    
  	private JButton login, signUp ;
        private int defaultPort;
	private String defaultHost;
        private Client client;
        private boolean connected;
        private JLabel username = new JLabel ("UserName:");
        private JLabel password = new JLabel("Password:");
          private JLabel LoginLabel = new JLabel("Login");
      JTextField  UserNameTF ;
        private JPasswordField passwordTF;
         JPanel LoginPanel=new JPanel();
        // JPanel SignUpPanel=new JPanel();

        LoginFrame() {

		this.setTitle("Login/SignUp");
		//defaultPort = port;
		//defaultHost = host;
                //JPanel LoginPanel=new JPanel();
               // Container LoginPanel =getContentPane();
         ///////////////////login/Signup////////////////////////////////////////////////
                LoginPanel.setLayout(null);
                LoginPanel.setSize(600,600);
                UserNameTF =new JTextField(16);
                passwordTF =new JPasswordField(16);
                login=new JButton("Login");
                signUp =new JButton("SignUp");
                LoginPanel.add(LoginLabel);
                username.setBounds(50,150,100,30);
                LoginPanel.add(username);
                UserNameTF.setBounds(150,150,150,30);
                LoginPanel.add(UserNameTF);
                password.setBounds(50,220,100,30);
                LoginPanel.add(password);
                passwordTF.setBounds(150,220,150,30);
                LoginPanel.add(passwordTF);
                login.setBounds(50,300,100,30);
                login.addActionListener(this);
                LoginPanel.add(login);
                signUp.setBounds(200,300,100,30);
                signUp.addActionListener(this);

                LoginPanel.add(signUp);
       ///////////////////////end///////////////////////////////////////////////         
                 
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setBounds(10,10,370,600);
                add(LoginPanel);
                setLayout(null);    
                setVisible(true);
	
                
                
        }
boolean Signi,SignU;
          @Override
          public void actionPerformed(ActionEvent e) {
             Object input = e.getSource();
       if( input == login){
           String Username =UserNameTF.getText().trim();
           String Password =passwordTF.getText().trim();
         if(Username.length()==0||Password.length()==0){
               JOptionPane.showMessageDialog(this,"Please,Enter Your UserName and Password");
           }else{
          signin_handler Signin=new signin_handler();
          Signi = Signin.signin(Username, Password);
         if(Signi){
           new ClientGUI("localhost", 1500, Username);
           setVisible(false);
         }else{
          JOptionPane.showMessageDialog(this,"Sorry, you entered an invalid username or password. Please try again");
         }}
          
   
       }
       if(input == signUp){
           String Username =UserNameTF.getText().trim();
           String Password =passwordTF.getText().trim();
           
           if(Username.length()==0||Password.length()==0){
               JOptionPane.showMessageDialog(this,"Please,Enter Your UserName and Password");
           }else{
           signin_handler Signup=new signin_handler();
           SignU= Signup.add_user(Username, Password);
           if(SignU){
           JOptionPane.showMessageDialog(this,"Welcome , "+Username);
           
           new ClientGUI("localhost", 1500, Username);
           setVisible(false);
           }
           else{
                     JOptionPane.showMessageDialog(this,"This account already exists,please login");
                   }
    
  

}}}}