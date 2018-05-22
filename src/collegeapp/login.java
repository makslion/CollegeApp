/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;

import java.awt.Color;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;

/**
 *
 * @author Maksym Vavilov 16856
 */
public class login extends javax.swing.JFrame implements Idatabase {

    /**
     * Creates new form login
     */
    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    private String db;
    private String connectionDetails = "";
    
    int xMouse;
    int yMouse;
    
    public login() {
        initComponents();
        this.rootPane.setDefaultButton(Login);
    
        String path = "./db.ser";
        File file = new File(path);
        
        //set values to default if file with settings not exist
        if (!file.exists() && !file.isDirectory()){
            String DBurl = "jdbc:mysql://localhost:3306/";
            String DBname = " maksym_vavilov_16856_provisional_project";
            String DBuser = "root";
            String DBpassword = "panadol95";
            db = DBurl+DBname+"?useSSL=false&user="+DBuser+"&password="+DBpassword;
        }
        else { //if they exist import setting from file and set them
            openFile();
            readConnectionDetails();
            closeFile();
            db = connectionDetails;
        }
        
        
    }
    
    
    
    @Override
    public void openFile(){
        try{ // open file
            input = new ObjectInputStream(
                    Files.newInputStream(Paths.get("db.ser")));
            
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, e,"Can't open file", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // terminate the program            
        }
    }
    
    @Override
    public final void readConnectionDetails(){
        
        try {
            while (true){ // loop until there is an EOFException
                file db = (file) input.readObject();
                
                connectionDetails =  db.getDBurl()+db.getDBname()+"?useSSL=false&user="
                        +db.getDBuser()+"&password="+db.getDBpassword();
                
                dbURLfield.setText(db.getDBurl());
                dbNamefield.setText(db.getDBname());
                dbUSERfield.setText(db.getDBuser());
                dbPSWDfield.setText(db.getDBpassword());
            }
        }
        catch(EOFException endOfFileException){
        }
        catch(ClassNotFoundException classNotFoundException){
            System.err.println("Invalid object type. Terminating.");
        }
        catch(IOException ioException){
            System.err.println("Error reading from file. Terminating.");
        }
        
    }
    
    @Override
     public void closeFile(){
        try{
            if (input != null)
                input.close();
        }
        catch(IOException ioException){
            System.err.println("Error closing file. Terminating.");
            System.exit(1);
        }
    }
     
     public static void addRecords(String DBurl, String DBname, String DBuser, String DBpassword){
        try {
            output = new ObjectOutputStream(
                    Files.newOutputStream(Paths.get("db.ser")));
            
            file db = new file(DBurl,DBname,DBuser,DBpassword);
            output.writeObject(db);
            
            if(output != null)
                output.close();
        }
        catch(NoSuchElementException | IOException e){
            JOptionPane.showMessageDialog(null, e,"Can't write to file", JOptionPane.ERROR_MESSAGE);
        }
    }
     
     public void testConnection(){
         boolean connected = false;
         db = dbURLfield.getText()+dbNamefield.getText()+"?useSSL=false&user="
                        +dbUSERfield.getText()+"&password="+dbPSWDfield.getText();
         
        Connection conn = null;
      try {
        conn =
        DriverManager.getConnection(db);
        connected = true;
        
        JOptionPane.showMessageDialog(this, "Connection established", "Everything OK" , JOptionPane.INFORMATION_MESSAGE);
        
        if(connected)
            conn.close();

    } catch (SQLException ex) {
        // handle any errors
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
        
        JOptionPane.showMessageDialog(this , ex, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
     }
     
     private void loginCheck (){
         String username = usernameField.getText();
         char [] password = passwordField.getPassword();
         String role;
         PreparedStatement getPassword; 
         ResultSet resultSet = null;
         boolean connected = false;
         
         try {
            Connection conn = DriverManager.getConnection(db);
            connected = true;
            
            getPassword = conn.prepareStatement("SELECT * FROM login WHERE Username = ?");
            getPassword.setString(1, username);
            
            resultSet = getPassword.executeQuery();
            
            if(resultSet.next()){
                if (resultSet.getString("Password").equals(new String (password))){
                   role = resultSet.getString("Role");
                   
                   switch (role){
                           case "student": 
                               new studentGUI(username).setVisible(true);
                               new policies().setVisible(true);
                               this.setVisible(false);
                               break;
                           case "faculty":
                               new facultyGUI(username).setVisible(true);
                               new policies().setVisible(true);
                               this.setVisible(false);
                               break;
                           case "admin":
                               new adminGUI().setVisible(true);
                               new policies().setVisible(true);
                               this.setVisible(false);
                               break;
                           default:
                               break;
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Wrong password", "Oops" , JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Wrong username", "Oops" , JOptionPane.ERROR_MESSAGE);
            }
            if(connected)
                conn.close();
         }
         catch(SQLException e){
             JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
         }
         
               
         
         
         
         
     }
     
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginPanel = new javax.swing.JPanel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        Login = new javax.swing.JButton();
        toConectionPanel = new javax.swing.JButton();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        loginCLoseButton = new javax.swing.JButton();
        conectionPanel = new javax.swing.JPanel();
        toLoginPanel = new javax.swing.JButton();
        testConection = new javax.swing.JButton();
        dbURLlabel = new javax.swing.JLabel();
        dbUSERlabel = new javax.swing.JLabel();
        dbPSWDlabel = new javax.swing.JLabel();
        dbNamelabel = new javax.swing.JLabel();
        dbURLfield = new javax.swing.JTextField();
        dbNamefield = new javax.swing.JTextField();
        dbUSERfield = new javax.swing.JTextField();
        dbPSWDfield = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        loginCLoseButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setLocationByPlatform(true);
        setName("loginFrame"); // NOI18N
        setUndecorated(true);
        setSize(new java.awt.Dimension(800, 400));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        loginPanel.setBackground(new java.awt.Color(255, 255, 255));
        loginPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        loginPanel.setName("loginPanel"); // NOI18N

        usernameField.setBackground(new java.awt.Color(255, 255, 255));
        usernameField.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        usernameField.setToolTipText("");
        usernameField.setBorder(null);
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        passwordField.setBackground(new java.awt.Color(255, 255, 255));
        passwordField.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        passwordField.setBorder(null);

        Login.setBackground(new java.awt.Color(102, 102, 102));
        Login.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        Login.setForeground(new java.awt.Color(255, 255, 255));
        Login.setText("Login");
        Login.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Login.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Login.setFocusPainted(false);
        Login.setMaximumSize(new java.awt.Dimension(101, 25));
        Login.setMinimumSize(new java.awt.Dimension(101, 25));
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });

        toConectionPanel.setBackground(new java.awt.Color(102, 102, 102));
        toConectionPanel.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        toConectionPanel.setForeground(new java.awt.Color(255, 255, 255));
        toConectionPanel.setText("Conection...");
        toConectionPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        toConectionPanel.setDefaultCapable(false);
        toConectionPanel.setFocusPainted(false);
        toConectionPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toConectionPanelActionPerformed(evt);
            }
        });

        usernameLabel.setBackground(new java.awt.Color(255, 255, 255));
        usernameLabel.setFont(new java.awt.Font("Comic Sans MS", 1, 16)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(102, 102, 102));
        usernameLabel.setText("Username:");

        passwordLabel.setBackground(new java.awt.Color(204, 204, 204));
        passwordLabel.setFont(new java.awt.Font("Comic Sans MS", 1, 16)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(102, 102, 102));
        passwordLabel.setText("Password:");

        logo.setIcon(new javax.swing.ImageIcon("C:\\Users\\24725\\Documents\\NetBeansProjects\\CollegeAPP\\logo.jpg")); // NOI18N

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        loginCLoseButton.setBackground(new java.awt.Color(255, 255, 255));
        loginCLoseButton.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        loginCLoseButton.setForeground(new java.awt.Color(102, 102, 102));
        loginCLoseButton.setText("X");
        loginCLoseButton.setBorder(null);
        loginCLoseButton.setFocusPainted(false);
        loginCLoseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginCLoseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addComponent(logo)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(usernameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                            .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(loginPanelLayout.createSequentialGroup()
                                    .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(toConectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(loginPanelLayout.createSequentialGroup()
                                    .addComponent(passwordLabel)
                                    .addGap(18, 18, 18)
                                    .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jSeparator2)
                                        .addGroup(loginPanelLayout.createSequentialGroup()
                                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))))))
                        .addContainerGap(45, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loginCLoseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addComponent(loginCLoseButton)
                .addGap(81, 81, 81)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2)
                .addGap(37, 37, 37)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toConectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
            .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(loginPanel, "card2");

        conectionPanel.setBackground(new java.awt.Color(255, 255, 255));
        conectionPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));

        toLoginPanel.setBackground(new java.awt.Color(102, 102, 102));
        toLoginPanel.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        toLoginPanel.setForeground(new java.awt.Color(255, 255, 255));
        toLoginPanel.setText("Login...");
        toLoginPanel.setBorder(null);
        toLoginPanel.setMaximumSize(new java.awt.Dimension(113, 25));
        toLoginPanel.setMinimumSize(new java.awt.Dimension(113, 25));
        toLoginPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toLoginPanelActionPerformed(evt);
            }
        });

        testConection.setBackground(new java.awt.Color(102, 102, 102));
        testConection.setFont(new java.awt.Font("Comic Sans MS", 0, 13)); // NOI18N
        testConection.setForeground(new java.awt.Color(255, 255, 255));
        testConection.setBorder(null);
        testConection.setLabel("Test Conection");
        testConection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testConectionActionPerformed(evt);
            }
        });

        dbURLlabel.setBackground(new java.awt.Color(255, 255, 255));
        dbURLlabel.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        dbURLlabel.setForeground(new java.awt.Color(102, 102, 102));
        dbURLlabel.setText("DB URL:");

        dbUSERlabel.setBackground(new java.awt.Color(255, 255, 255));
        dbUSERlabel.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        dbUSERlabel.setForeground(new java.awt.Color(102, 102, 102));
        dbUSERlabel.setText("User:");

        dbPSWDlabel.setBackground(new java.awt.Color(255, 255, 255));
        dbPSWDlabel.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        dbPSWDlabel.setForeground(new java.awt.Color(102, 102, 102));
        dbPSWDlabel.setText("Password:");

        dbNamelabel.setBackground(new java.awt.Color(255, 255, 255));
        dbNamelabel.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        dbNamelabel.setForeground(new java.awt.Color(102, 102, 102));
        dbNamelabel.setText("DB Name:");

        dbURLfield.setBackground(new java.awt.Color(255, 255, 255));
        dbURLfield.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        dbURLfield.setBorder(null);
        dbURLfield.setSelectionColor(new java.awt.Color(102, 102, 102));

        dbNamefield.setBackground(new java.awt.Color(255, 255, 255));
        dbNamefield.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        dbNamefield.setBorder(null);
        dbNamefield.setSelectionColor(new java.awt.Color(102, 102, 102));

        dbUSERfield.setBackground(new java.awt.Color(255, 255, 255));
        dbUSERfield.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        dbUSERfield.setBorder(null);
        dbUSERfield.setSelectionColor(new java.awt.Color(102, 102, 102));

        dbPSWDfield.setBackground(new java.awt.Color(255, 255, 255));
        dbPSWDfield.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        dbPSWDfield.setBorder(null);
        dbPSWDfield.setSelectionColor(new java.awt.Color(102, 102, 102));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        loginCLoseButton1.setBackground(new java.awt.Color(255, 255, 255));
        loginCLoseButton1.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        loginCLoseButton1.setForeground(new java.awt.Color(102, 102, 102));
        loginCLoseButton1.setText("X");
        loginCLoseButton1.setBorder(null);
        loginCLoseButton1.setFocusPainted(false);
        loginCLoseButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginCLoseButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout conectionPanelLayout = new javax.swing.GroupLayout(conectionPanel);
        conectionPanel.setLayout(conectionPanelLayout);
        conectionPanelLayout.setHorizontalGroup(
            conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(conectionPanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dbURLlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dbUSERlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dbPSWDlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(dbNamelabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6)
                    .addComponent(jSeparator5)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, conectionPanelLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(testConection, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(toLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 179, Short.MAX_VALUE))
                    .addComponent(dbURLfield, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dbUSERfield, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dbPSWDfield, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dbNamefield, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(30, 30, 30)
                .addComponent(loginCLoseButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        conectionPanelLayout.setVerticalGroup(
            conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, conectionPanelLayout.createSequentialGroup()
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(conectionPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dbURLlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbURLfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(loginCLoseButton1))
                .addGap(5, 5, 5)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbNamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbNamefield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbUSERlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbUSERfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbPSWDlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbPSWDfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(testConection, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toLoginPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        getContentPane().add(conectionPanel, "card3");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void toConectionPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toConectionPanelActionPerformed
        loginPanel.setVisible(false);
        conectionPanel.setVisible(true);
    }//GEN-LAST:event_toConectionPanelActionPerformed

    private void toLoginPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toLoginPanelActionPerformed
        conectionPanel.setVisible(false);
        loginPanel.setVisible(true);
        
        // updating .ser file to ceep it up to date
        addRecords(dbURLfield.getText(),dbNamefield.getText(),
                dbUSERfield.getText(),dbPSWDfield.getText());
    }//GEN-LAST:event_toLoginPanelActionPerformed

    private void testConectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testConectionActionPerformed
        testConnection();
    }//GEN-LAST:event_testConectionActionPerformed

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
        loginCheck();
    }//GEN-LAST:event_LoginActionPerformed

    private void loginCLoseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginCLoseButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_loginCLoseButtonActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_formMouseDragged

    private void loginCLoseButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginCLoseButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_loginCLoseButton1ActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Login;
    private javax.swing.JPanel conectionPanel;
    private javax.swing.JTextField dbNamefield;
    private javax.swing.JLabel dbNamelabel;
    private javax.swing.JTextField dbPSWDfield;
    private javax.swing.JLabel dbPSWDlabel;
    private javax.swing.JTextField dbURLfield;
    private javax.swing.JLabel dbURLlabel;
    private javax.swing.JTextField dbUSERfield;
    private javax.swing.JLabel dbUSERlabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JButton loginCLoseButton;
    private javax.swing.JButton loginCLoseButton1;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel logo;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton testConection;
    private javax.swing.JButton toConectionPanel;
    private javax.swing.JButton toLoginPanel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
