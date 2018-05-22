/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;

import java.awt.Color;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Maksym Vavilov 16856
 */
public abstract class studentDatabase extends javax.swing.JFrame implements Idatabase {
    
    
    protected PreparedStatement PrepStatement;
    protected String connectionDetails;
    protected static ObjectInputStream input;
    
    int xMouse;
    int yMouse;
    
    
    protected static String Sid;
    
    protected String pattern = "{8,45}[a-z|A-Z]+[0-9]+";
    
    protected String assignmentQuery = "SELECT assignments.Aid AS 'Assignment',\n" +
                                    "        subjects.Subject,\n" +
                                    "        assignments.DueDate AS 'Due date',\n" +
                                    "        assignment_grades.Grade\n" +
                                    "FROM ((assignments\n" +
                                    "	INNER JOIN subjects ON assignments.Subject = subjects.SBid)\n" +
                                    "   INNER JOIN assignment_grades ON assignments.Aid = assignment_grades.Aid)\n" +
                                    "   WHERE Sid = ? AND Visible = 'yes';";
    
    protected String gradesQuery = "SELECT subjects.Subject,\n" +
                                "		grades.Grade,\n" +
                                "        grades.ExamGrade AS 'Exam Grade'\n" +
                                "FROM (grades\n" +
                                "	INNER JOIN subjects ON grades.SBid = subjects.SBid)\n" +
                                "    WHERE Sid = ?;";
    
    protected String timetableQuery = "SELECT timetable.Day,\n" +
                                "		timetable.Subject,\n" +
                                "        timetable.Start,\n" +
                                "        timetable.End\n" +
                                "FROM (timetable\n" +
                                "	INNER JOIN groups ON timetable.Gid = groups.Gid)\n" +
                                "    WHERE Sid = ?"+ 
                                "    ORDER BY Subject;";
    
    protected String pswdQuery = "UPDATE login SET `Password`=? WHERE `Username`=?;";
    protected String pswdCheck = "SELECT login.Password FROM login WHERE login.Username = ?;";
    
    studentDatabase(){
    
    }
    
    protected final void designTable(JTable t, JScrollPane s){
        s.setOpaque(false);
        s.getViewport().setOpaque(false);
        //s.setBorder(null);
        t.setOpaque(false);
        
        
        JTableHeader header =  t.getTableHeader();
        
        header.setBackground(new Color(255,255,255));
        header.setForeground(new Color (0,0,0));
    }
    
    
    
    protected final void logout(){
        this.setVisible(false);
        new login().setVisible(true);
    }
    
    
    @Override
    public final void openFile(){
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
    
    @Override
     public final void closeFile(){
        try{
            if (input != null)
                input.close();
        }
        catch(IOException ioException){
            System.err.println("Error closing file. Terminating.");
            System.exit(1);
        }
    }
    
    protected final ResultSet prepareQuery (String query, String id){
        ResultSet resultSet = null;
        
        try {
            Connection conn = DriverManager.getConnection(connectionDetails);
            
            PrepStatement = conn.prepareStatement(query);
            PrepStatement.setString(1, id);
            
            resultSet = PrepStatement.executeQuery();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this , e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return resultSet;
    }
    
    
    
}
