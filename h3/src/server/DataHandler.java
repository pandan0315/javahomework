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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
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
       
    
      
       String createString="create table if not exists "+" market.items" + " (itemid VARCHAR(255) PRIMARY KEY, name VARCHAR(32),price FLOAT,owner VARCHAR(32),buyer VARCHAR(32),state VARCHAR(32))";
       
       stmt.executeUpdate(createString);
        
        
    }
   
   public void createBankAccountTable() throws SQLException{
       
       String createString="create table if not exists "+" market.bankaccounts" + " (name VARCHAR(32) PRIMARY KEY, balance FLOAT)"; 
       stmt.executeUpdate(createString);
   }
   
    public void createClientAccountTable() throws SQLException{
       
       String createString="create table if not exists "+" market.clientaccounts" + " (username VARCHAR(32) PRIMARY KEY, password VARCHAR(255),bankaccount VARCHAR(32))"; 
       stmt.executeUpdate(createString);
   }
  
   public void createWishTable() throws SQLException{
       
       String createString="create table if not exists "+" market.wishes" + " (name VARCHAR(32) PRIMARY KEY, price Float,owner VARCHAR(32))"; 
       stmt.executeUpdate(createString);
   }
    public void storeClientAccount(ClientAccount account) throws RemoteException {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO market.clientaccounts " +
                    "(username, password, bankaccount) VALUES (?, ?, ?)");
            stmt.setString(1, account.getUserName());
            
            char[] password = account.getPassword();
            String passwordStr = new String(password);
            //MessageDigest md = MessageDigest.getInstance("SHA");
           // String digestedString = new String(md.digest(passwordStr.getBytes()));
            
           // stmt.setString(2, digestedString);
            stmt.setString(2, passwordStr);
            stmt.setString(3, account.getBankAccount().getName());
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
            // TODO: need implementation here
        } catch (SQLException ex) {
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

    public ClientAccount getClientAccount(String name) throws RemoteException, RejectedException {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM market.clientaccounts" +
                    " WHERE username = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            // Create the client account object and return it
            if (rs.next()) {
                String clientName = rs.getString("username");
                char[] password = rs.getString("password").toCharArray();
                String bankAccountName = rs.getString("bankaccount");
                stmt.close();
                return new ClientAccount(clientName, password, this.getBankAccountByname(bankAccountName));
            }
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
       
    }

    public HashMap<String,ClientAccount>getALLClient(){
        
        HashMap<String,ClientAccount> allClient=new HashMap<>();
        
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM market.clientaccounts");
            
            // Return the client name collection with vector            
            while (rs.next()) {
                
                allClient.put(rs.getString("username"), this.getClientAccount(rs.getString("username")));
            }
            stmt.close();
            
            
        } catch (SQLException | RemoteException | RejectedException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return allClient;
    }
     public ClientAccount getClientAccountByNameAndPassword(String name, char[] password) throws RemoteException, RejectedException {
        
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM market.clientaccounts" +
                    " WHERE (username = ? AND password = ?)");
            stmt.setString(1, name);
            String passwordStr = new String(password);
           // MessageDigest md = MessageDigest.getInstance("SHA");
            //String digestedString = new String(md.digest(passwordStr.getBytes()));
            //stmt.setString(2, digestedString);
            stmt.setString(2, passwordStr);
            ResultSet rs = stmt.executeQuery();
            
            // Create the client account object and return it
            if (rs.next()) {
               
                String clientName = rs.getString("username");
                char[] passwordArray = rs.getString("password").toCharArray();
                String bankAccountName = rs.getString("bankaccount");               
                stmt.close();
                return new ClientAccount(clientName, passwordArray, this.getBankAccountByname(bankAccountName));
            }
        }  catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
       
    }

    public HashMap<UUID,Item> getAllItem() {
        HashMap<UUID,Item> items = new HashMap<>();
        try {
            PreparedStatement stmt;
            // Prepate the statement with SQL update command
            
                stmt = con.prepareStatement("SELECT * FROM market.items WHERE (" +
                        "state = ?)");
                stmt.setString(1, ItemState.OnSell.toString());
            
            ResultSet rs = stmt.executeQuery();
            //stmt.close();         
            while (rs.next()) {
                UUID itemId = UUID.fromString(rs.getString("itemid"));
                String seller = rs.getString("owner");
                String itemName = rs.getString("name");
                float price = rs.getFloat("price");
                ItemState state = ItemState.valueOf(rs.getString("state"));              
                 // Create the item object and return it
                items.put(itemId, new Item(itemName, price, seller, null, itemId, state));
                
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return items;
    }

    public void storeItem(Item item) {
        
        try {
            
            if (null == item) {
                return;
            }
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO market.items (itemid, name," +
                    "price, state, owner, buyer) VALUES(?, ?, ?, ?, ?, ?)");
            stmt.setString(1, item.getItemID().toString());
            stmt.setString(2, item.getItemName());
            stmt.setString(3, Float.toString(item.getItemPrice()));
            // Prepare for the state
            stmt.setString(4, item.getState().toString());
            stmt.setString(5, item.getOwner());
            stmt.setString(6, null);
           
            
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    public WishItem getMatchedWishItem(Item itemForSell) {
        
       WishItem item =null;
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM market.wishes WHERE (" +
                    "name = ? AND price > ?)");
            stmt.setString(1, itemForSell.getItemName());
            stmt.setFloat(2, itemForSell.getItemPrice());
            ResultSet rs = stmt.executeQuery();
            //stmt.close();
            while (rs.next()) {
                String wisher = rs.getString("owner");
                float price = rs.getFloat("price");
                 // Create the item object and return it
                item=new WishItem(itemForSell.getItemName(), price, wisher);
                if(item!=null){break;}
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return item;
        
        
    }

    public void storeWish(WishItem wishedItem) {
        try {
           
            if (null == wishedItem) {
                return;
            }
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO market.wishes (name," +
                    "price, owner) VALUES(?, ?, ?)");
            stmt.setString(1, wishedItem.getWishItemName());
            stmt.setFloat(2, wishedItem.getWishprice());
            stmt.setString(3, wishedItem.getWisher());
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    
    public ArrayList<WishItem> getWishItems(ClientAccount client) {
         ArrayList<WishItem> items = new ArrayList<>();
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM market.wishes WHERE (" +
                    "owner = ?)");   
            stmt.setString(1, client.getUserName());
            ResultSet rs = stmt.executeQuery();
            //stmt.close();
            while (rs.next()) {
                String itemName = rs.getString("name");
                float price = rs.getFloat("price");            
                 // Create the item object and return it
                items.add(new WishItem(itemName, price,client.getUserName()));
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return items;
    }

    public void updateItem(Item item) {
        try {
            // Cast the item into item implementation object
            //ItemForSellImpl itemImpl = (ItemForSellImpl)item;
            if (null == item) {
                return;
            }
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("UPDATE market.items SET " +
                    "state = ?, buyer = ? WHERE itemid = ?");         
            // Prepare for the state
            stmt.setString(1, item.getState().toString());
            if (null != item.getBuyer()) {
                stmt.setString(2, item.getBuyer());
            }
            stmt.setString(3, item.getItemID().toString());
            
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void removeWishItem(WishItem item) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("DELETE FROM market.wishes WHERE " + 
                    "name = ?");
            stmt.setString(1, item.getWishItemName());
            // Execute the delete action
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void removeClient(String name, char[] password) throws RemoteException, RejectedException {
        ClientAccount client = this.getClientAccountByNameAndPassword(name, password);
        if(client!=null){
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("DELETE FROM market.clientaccounts WHERE " + 
                    "username = ?");
            stmt.setString(1, client.getUserName());
            // Execute the delete action
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        }
    
    }

    public void removeItems(String name) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("DELETE FROM market.items WHERE " + 
                    "owner = ?");
            stmt.setString(1, name);
            // Execute the delete action
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
        
   public void removeAllWishItem(String name){
       try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("DELETE FROM market.wishes WHERE " + 
                    "owner = ?");
            stmt.setString(1, name);
            // Execute the delete action
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
       
   }

    public int getBoughtAmount(String name) {
        
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS total FROM market.items WHERE (" +
                    "buyer = ? AND state = 'Sold')");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
            return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        
    }

    public int getSelledAmount(String userName) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS total FROM market.items WHERE (" +
                    "owner = ? AND state = 'Sold')");
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
            return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }


  
    
}
