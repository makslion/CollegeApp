/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;

/**
 *
 * @author Maksym Vavilov 16856
 */
public class CollegeAPP {

    /**
     * @param args the command line arguments
     */
    private static ObjectOutputStream output;
    
    public static void main(String[] args) {
    
    
    // defaukt db values
    String DBurl = "jdbc:mysql://localhost:3306/";
    String DBname = "programming_db";
    String DBuser = "root";
    String DBpassword = "panadol95";
    String db = DBurl+DBname+"?useSSL=false&user="+DBuser+"&password="+DBpassword;
    
    
    String path = "./db.ser";
    File file = new File(path);
    
    if (!file.exists() && !file.isDirectory()){
        
        createFile();
        addRecords(DBurl,DBname,DBuser,DBpassword);
        closeFile();
    }
    
      
    login test = new login();
    test.setVisible(true);
    }
    
    
    public static void createFile(){    
    try {
            output = new ObjectOutputStream(
                    Files.newOutputStream(Paths.get("db.ser")));
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null, e,"Can't create file", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // terminate the program
        }
    }
    
    public static void addRecords(String DBurl, String DBname, String DBuser, String DBpassword){
        try {
            
            file db = new file(DBurl,DBname,DBuser,DBpassword);
            output.writeObject(db);
        }
        catch(NoSuchElementException | IOException e){
            JOptionPane.showMessageDialog(null, e,"Can't write to file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void closeFile(){
    try{
            if (output != null)
                output.close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, e,"Can't close file file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
