/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;

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
    
    public login() {
        initComponents();
        this.rootPane.setDefaultButton(Login);
    
        String path = "./db.ser";
        File file = new File(path);
        
        //set values to default if file with settings not exist
        if (!file.exists() && !file.isDirectory()){
            String DBurl = "jdbc:mysql://localhost:3306/";
            String DBname = "programming_db";
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setName("loginFrame"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        loginPanel.setName("loginPanel"); // NOI18N

        usernameField.setToolTipText("");

        Login.setText("Login");
        Login.setMaximumSize(new java.awt.Dimension(101, 25));
        Login.setMinimumSize(new java.awt.Dimension(101, 25));
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });

        toConectionPanel.setText("Conection...");
        toConectionPanel.setDefaultCapable(false);
        toConectionPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toConectionPanelActionPerformed(evt);
            }
        });

        usernameLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        usernameLabel.setText("Username:");

        passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        passwordLabel.setText("Password:");

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addComponent(toConectionPanel))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(passwordField))))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toConectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        getContentPane().add(loginPanel, "card2");

        toLoginPanel.setText("Login...");
        toLoginPanel.setMaximumSize(new java.awt.Dimension(113, 25));
        toLoginPanel.setMinimumSize(new java.awt.Dimension(113, 25));
        toLoginPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toLoginPanelActionPerformed(evt);
            }
        });

        testConection.setLabel("Test Conection");
        testConection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testConectionActionPerformed(evt);
            }
        });

        dbURLlabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbURLlabel.setText("DB URL:");
        dbURLlabel.setEnabled(false);

        dbUSERlabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbUSERlabel.setText("User:");
        dbUSERlabel.setEnabled(false);

        dbPSWDlabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbPSWDlabel.setText("Password:");
        dbPSWDlabel.setEnabled(false);

        dbNamelabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dbNamelabel.setText("DB Name:");
        dbNamelabel.setEnabled(false);

        dbURLfield.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        dbNamefield.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        dbUSERfield.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        dbPSWDfield.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout conectionPanelLayout = new javax.swing.GroupLayout(conectionPanel);
        conectionPanel.setLayout(conectionPanelLayout);
        conectionPanelLayout.setHorizontalGroup(
            conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(conectionPanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dbURLlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(testConection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dbUSERlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dbPSWDlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dbNamelabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(conectionPanelLayout.createSequentialGroup()
                        .addGap(0, 115, Short.MAX_VALUE)
                        .addComponent(toLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dbURLfield)
                    .addComponent(dbUSERfield)
                    .addComponent(dbPSWDfield)
                    .addComponent(dbNamefield))
                .addGap(55, 55, 55))
        );
        conectionPanelLayout.setVerticalGroup(
            conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, conectionPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbURLlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbURLfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbNamelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbNamefield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbUSERlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbUSERfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbPSWDlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbPSWDfield, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(conectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(testConection, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
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
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton testConection;
    private javax.swing.JButton toConectionPanel;
    private javax.swing.JButton toLoginPanel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
