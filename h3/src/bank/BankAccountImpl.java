package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class BankAccountImpl extends UnicastRemoteObject implements BankAccount {

    private float balance = 0;
    private String name;
    private PreparedStatement updateStatement;
    private PreparedStatement queryStatement;
    

    public BankAccountImpl(String name, float balance,Connection conn) throws RemoteException, RejectedException{
        super();
        this.name=name;
        this.balance=balance;
         try {
            updateStatement = conn.prepareStatement("UPDATE "
                                                                  + "market.bankaccounts"
                                                          + " SET balance = ? WHERE name= ? ");
            updateStatement.setString(2, name);
            queryStatement = conn.prepareStatement("SELECT balance FROM market.bankaccounts WHERE name = '" + name + "'");
        } catch (SQLException sqle) {
            throw new RejectedException("Unable to instantiate account", sqle);
        }
        
        
    }
    
    public BankAccountImpl(String name,Connection conn)throws RemoteException, RejectedException{
        this(name,0,conn);
      
        
        
    }

    @Override
    public synchronized float getBalance() throws RemoteException {
        return balance;
    }
    
    private float getBalanceFromDB() {
        try {
            ResultSet rs = queryStatement.executeQuery();
            while(rs.next()){
                return rs.getFloat("balance");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    

    @Override
    public synchronized void deposit(float value) throws RemoteException,RejectedException{


        if (value < 0) {
            throw new RejectedException("Rejected: Account " + name
                                                + ": Illegal value: " + value);
        }
        
        balance = this.getBalanceFromDB();
        boolean success = false;
        try {
            balance += value;
            updateStatement.setFloat(1, balance);
            int rows = updateStatement.executeUpdate();
            if (rows != 1) {
                throw new RejectedException("Unable to deposit into account: " + name);
            } else {
                success = true;
            }
            System.out.println("Transaction: Account " + name + ": deposit: $"
                                       + value + ", balance: $" + balance);
        } catch (SQLException sqle) {
            throw new RejectedException("Unable to deposit into account: " + name, sqle);
        } finally {
            if (!success) {
                balance -= value;
            }
        }

    }
    @Override
    public synchronized void withdraw(float value) throws RemoteException,
                                                                     RejectedException{

        if (value < 0) {
            throw new RejectedException("Rejected: Account " + name
                                                + ": Illegal value: " + value);
        }
        balance = this.getBalanceFromDB();
        if ((balance - value) < 0) {
            throw new RejectedException("Rejected: Account " + name
                                                + ": Negative balance on withdraw: "
                                                + (balance - value));
        }

        boolean success = false;
        try {
            balance -= value;
            updateStatement.setFloat(1, balance);
            int rows = updateStatement.executeUpdate();
            if (rows != 1) {
                throw new RejectedException("Unable to deposit into account: " + name);
            } else {
                success = true;
            }
            System.out.println("Transaction: Account " + name + ": withdraw: $"
                                       + value + ", balance: $" + balance);
        } catch (SQLException sqle) {
            throw new RejectedException("Unable to deposit into account: " + name, sqle);
        } finally {
            if (!success) {
                balance += value;
            }
        }
        
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }
}
