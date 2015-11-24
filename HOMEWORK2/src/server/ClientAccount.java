package server;

import bank.BankAccount;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by danpan on 22/11/15.
 */
public interface ClientAccount extends Remote {

    public String getUserName() throws RemoteException;

    public BankAccount getBankAccount()throws RemoteException;



}
