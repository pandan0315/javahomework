/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import bank.BankAccount;
import bank.BankAccountImpl;
import bank.RejectedException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author danpan
 */
public class DataHandler {
    
    private Connection con = null;
    
    private Statement stmt=null;
    
    
    
    public Connection getConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
     
        try {
             Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/market", "pan", "123456");
            stmt=con.createStatement();
          
        } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
           
        }
            
      return con;
        
    }

    
    
   public void createItemTable() throws SQLException {
       
       
      
       String createString="create table if not exists "+" market.items" + " (itemid VARCHAR(255) PRIMARY KEY, name VARCHAR(32),price FLOAT,owner VARCHAR(32))";
       
       stmt.executeUpdate(createString);
        
        
    }
   
   public void createBankAccountTable() throws SQLException{
       
       String createString="create table if not exists "+" market.bankaccounts" + " (name VARCHAR(32) PRIMARY KEY, balance FLOAT)"; 
       stmt.executeUpdate(createString);
   }
   
    public void createClientAccountTable() throws SQLException{
       
       String createString="create table if not exists "+" market.clientaccounts" + " (name VARCHAR(32) PRIMARY KEY, password VARCHAR(255),bankaccount VARCHAR(32)"; 
       stmt.executeUpdate(createString);
   }

   
    public void storeClientAccount(ClientAccount account) throws RemoteException {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO marketdata.clientaccounts " +
                    "(username, password, bankaccount) VALUES (?, ?, ?)");
            stmt.setString(1, account.getUserName());
            
            char[] password = account.getPassword();
            String passwordStr = new String(password);
            MessageDigest md = MessageDigest.getInstance("SHA");
            String digestedString = new String(md.digest(passwordStr.getBytes()));
            
            stmt.setString(2, digestedString);
            stmt.setString(3, account.getBankAccount().getName());
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
            // TODO: need implementation here
        } catch (NoSuchAlgorithmException | SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public BankAccountImpl getBankAccountByname(String name) throws RemoteException, RejectedException {
        try {
             // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM market.bankaccounts " + 
                    "WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                float balance = rs.getFloat("balance");              
                // Create the bank account object and return it
                stmt.close();
                
                return new BankAccountImpl(name,balance,con);
            }            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public void storeBankAccount(String name) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO market.bankaccounts (" +
                    "name, balance) VALUEs(?, ?)");
            // Execute and update the data
            stmt.setString(1, name);
            //float accountBalance = account.getBalance();
            stmt.setFloat(2, 0);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

  
  
    
}
