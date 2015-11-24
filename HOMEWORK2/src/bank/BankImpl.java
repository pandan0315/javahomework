package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by danpan on 21/11/15.
 */
public class BankImpl extends UnicastRemoteObject implements Bank {
    private String bankName;
    private Map<String, BankAccount> accounts = new HashMap<>();

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
            System.out.println("Account [" + name + "] exists!!!");
            throw new RejectedException("Rejected: Bank: " + bankName
                    + " Account for: " + name + " already exists: " + account);
        }
        account = new BankAccountImpl(name);
        accounts.put(name, account);
        System.out.println("Bank: " + bankName + " Account: " + account
                + " has been created for " + name);
        return account;


    }

    
}

