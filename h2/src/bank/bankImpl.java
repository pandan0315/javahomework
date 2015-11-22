package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by danpan on 21/11/15.
 */
public class bankImpl extends UnicastRemoteObject implements bank {
    private String bankName;
    private Map<String, bankAccount> accounts = new HashMap<>();

    public bankImpl(String bankName) throws RemoteException {
        super();
        this.bankName = bankName;
    }


    @Override
    public synchronized bankAccount newAccount(String name) throws RemoteException, RejectedException {
        bankAccountImpl account = (bankAccountImpl) accounts.get(name);
        if (account != null) {
            System.out.println("Account [" + name + "] exists!!!");
            throw new RejectedException("Rejected: Bank: " + bankName
                    + " Account for: " + name + " already exists: " + account);
        }
        account = new bankAccountImpl(name);
        accounts.put(name, account);
        System.out.println("Bank: " + bankName + " Account: " + account
                + " has been created for " + name);
        return account;


    }

    @Override
    public synchronized bankAccount getAccount(String name) throws RemoteException {
        return null;
    }

    @Override
    public synchronized boolean deleteAccount(String name) throws RemoteException {
        if (!hasAccount(name)) {
            return false;
        }
        accounts.remove(name);
        System.out.println("se.kth.id2212.ex2.Bank: " + bankName + " Account for " + name
                + " has been deleted");
        return true;

    }

    private boolean hasAccount(String name) {
        return accounts.get(name) != null;
    }

    @Override
    public synchronized String[] listAccounts() throws RemoteException {
        return accounts.keySet().toArray(new String[1]);
    }
}
