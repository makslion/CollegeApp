/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collegeapp;

/**
 *
 * @author Maksym Vavilov 16856
 */
public abstract class studentDatabase extends javax.swing.JFrame implements Idatabase {
    
    studentDatabase(){
    
    }
    
    protected void logout(){
        this.setVisible(false);
        new login().setVisible(true);
    }
    
}
