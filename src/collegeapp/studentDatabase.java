/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Maksym Vavilov 16856
 */
public abstract class studentDatabase extends javax.swing.JFrame implements Idatabase {
    
    
    protected PreparedStatement PrepStatement;
    protected boolean connected;
    protected String connectionDetails;
    protected static ObjectInputStream input;
    
    studentDatabase(){
    
    }
    
    
    
    
    protected void logout(){
        this.setVisible(false);
        new login().setVisible(true);
    }
    
    
    
    
    protected void readConnectionDetails(){
        try {
            input = new ObjectInputStream(
                    Files.newInputStream(Paths.get("db.ser")));
            
            while (true){ // loop until there is an EOFException
                file db = (file) input.readObject();
                connectionDetails =  db.getDBurl()+db.getDBname()+"?useSSL=false&user="
                        +db.getDBuser()+"&password="+db.getDBpassword();
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
    
}
