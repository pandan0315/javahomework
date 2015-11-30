package server;



import bank.BankAccount;
import clientmain.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

/**
 * Created by danpan on 22/11/15.
 */

//remote server interface allows clients to register ,unregister ....
public interface MarketService extends Remote{
  
    public ClientAccount register(String name, char[] password,BankAccount bankAccount) throws RemoteException;
  //  public void unRegister(String name) throws RemoteException;
    public void unRegister(String name,char[] password) throws RemoteException;
    public ClientAccount login(String name, char[] password) throws RemoteException;
    public void sellItem(String itemName,float price,ClientAccount sellerAccount) throws RemoteException;
    
    public boolean buyItem(Item item,ClientAccount buyerAccount) throws RemoteException;
   // public boolean updateWishItemList(Item item)throws RemoteException;
     public ArrayList<WishItem> getWishedList(ClientAccount client)throws RemoteException;
     public void updateWishItemList(ClientAccount client,WishItem item) throws RemoteException;

    /**
     *
     * @param itemName
     * @param price
     * @param buyerAccount
     * @return
     * @throws RemoteException
     */
    public WishItem wishItem(String itemName,float price,ClientAccount buyerAccount) throws RemoteException;
    
   // public ArrayList<Item> getAllWishItem() throws RemoteException;
    public HashMap<String,ClientAccount>getAllClients() throws RemoteException;
    public HashMap<UUID,Item> getAllItem() throws RemoteException;
    public void addClientNotifyObject(ClientInterface clientObj, ClientAccount client) throws RemoteException;
    public HashMap<String, ClientInterface> getNotifiableClientTable() throws RemoteException;
    
   // public void setClientTable();
     public ClientAccount getMatchedClient(String name,char[] password) throws RemoteException;

    public void logout(ClientAccount client)throws RemoteException;

    public int getBoughtAmount(String name)throws RemoteException;

    public int getSelledAmount(String userName)throws RemoteException;

    
}
