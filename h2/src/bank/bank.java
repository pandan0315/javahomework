package bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by danpan on 21/11/15.
 */
public interface bank extends Remote {
    public bankAccount newAccount(String name) throws RemoteException, RejectedException;

    public bankAccount getAccount(String name) throws RemoteException;

    public boolean deleteAccount(String name) throws RemoteException;

    public String[] listAccounts() throws RemoteException;
}
