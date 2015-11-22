package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import bank.bankAccount;

/**
 * Created by danpan on 22/11/15.
 */
public interface marketService extends Remote{
    public void register(clientAccount client) throws RemoteException;
    public void unRegister(clientAccount client) throws RemoteException;
    public void sellItem(item item) throws RemoteException;
    public void buyItem(item item) throws RemoteException;
    public void wishItem(item item) throws RemoteException;
    public List<item> getAllItem( ) throws RemoteException;

}
