package bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by danpan on 21/11/15.
 */
public interface BankAccount extends Remote {

    public float getBalance() throws RemoteException;

    public void deposit(float value) throws RemoteException;

    public void withdraw(float value) throws RemoteException;

}
