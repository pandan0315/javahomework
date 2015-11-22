package server;

import bank.bankAccount;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by danpan on 22/11/15.
 */
public interface clientAccount extends Remote {

    public String getUserName() throws RemoteException;

    public bankAccount getBankAccount()throws RemoteException;



}
