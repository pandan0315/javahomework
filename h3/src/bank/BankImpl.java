package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.DataHandler;

/**
 * Created by danpan on 21/11/15.
 */
public class BankImpl extends UnicastRemoteObject implements Bank {
    private String bankName;
    private Map<String, BankAccount> accounts = new HashMap<>();
    private DataHandler dataHandler=null;

    public BankImpl(String bankName) throws RemoteException {
        super();
        this.bankName = bankName;
    }

    public BankImpl() throws RemoteException{
        super();
    }
  
    

    @Override
    public synchronized BankAccount createAccount(String name) throws RemoteException, RejectedException {
        
       BankAccountImpl account = (BankAccountImpl) accounts.get(name);
       
        if (account != null) {
            return null;
           
        }
        account = this.dataHandler.getBankAccountByname(name);
         if (account != null){
             
            this.accounts.put(name, account);
              return null;
           
         }
        try {
            
            this.dataHandler.storeBankAccount(name);
            account = new BankAccountImpl(name,this.dataHandler.getConnection());
            
            
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BankImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        accounts.put(name, account);
        System.out.println("Bank: " + bankName + " Account: " + account
                + " has been created for " + name);
        return account;


    }

    @Override
    public synchronized BankAccount getAccount(String name) throws RemoteException {
        
         BankAccount acct = accounts.get(name);
        if (acct == null) {
             try {
                 acct= this.dataHandler.getBankAccountByname(name);
             } catch (RejectedException ex) {
                 Logger.getLogger(BankImpl.class.getName()).log(Level.SEVERE, null, ex);
             }
            if (acct!=null) {
               
               this.accounts.put(name, acct);
            } else {
                return null;
            }
        }
        return acct;
        
      
       
        
        
    }

    
    public void getDataHandler(DataHandler dataHandler) {
        this.dataHandler=dataHandler;
    }

    
}

