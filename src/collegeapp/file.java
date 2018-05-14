package collegeapp;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maksym Vavilov 16856
 */
public class file implements Serializable {
    private String DBurl;
    private String DBname;
    private String DBuser;
    private String DBpassword;
    
    
    public file(){
        
    }
    
    public file(String DBurl, String DBname, String DBuser, String DBpassword){
        this.DBurl = DBurl;
        this.DBname = DBname;
        this.DBuser = DBuser;
        this.DBpassword = DBpassword;
    }
    
    public void setDBurl (String DBurl){
        this.DBurl = DBurl;    
    }
    
    public String getDBurl (){
        return DBurl;
    }
    
    public void setDBname (String DBname){
        this.DBname = DBname;
    }
    
    public String getDBname (){
        return DBname;
    }
    
    public void setDBuser (String DBuser){
    this.DBuser = DBuser;
    }
    
    public String getDBuser (){
        return DBuser;
    }
    
    public void setDBpassword (String DBpassword){
    this.DBpassword = DBpassword;
    }
    
    public String getDBpassword (){
        return DBpassword;
    }
    
}
