/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Maksym Vavilov 16856
 */
public abstract class adminDatabase extends facultyDatabase{
    
    protected String directQuery;
    
    public adminDatabase(){
    
    }
}

