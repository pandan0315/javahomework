package server;

import bank.BankAccount;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by danpan on 22/11/15.
 */
public interface MarketService extends Remote{
    public void register(String name, BankAccount bankAccount) throws RemoteException;
    public void unRegister(String name) throws RemoteException;
    public void sellItem(String itemName,float price,ClientAccount sellerAccount) throws RemoteException;
    public boolean buyItem(Item item,ClientAccount buyerAccount) throws RemoteException;
    public void wishItem(String itemName,float price,ClientAccount buyerAccount) throws RemoteException;
    public List<Item> getAllItem( ) throws RemoteException;

}
