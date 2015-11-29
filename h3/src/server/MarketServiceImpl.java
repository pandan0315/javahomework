package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import bank.*;
import clientmain.ClientInterface;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class MarketServiceImpl extends UnicastRemoteObject implements MarketService {

    private ArrayList<ClientAccount> clientAccountsList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();
    private ArrayList<Item> wishItemList = new ArrayList<>();
    private Hashtable<String, ClientInterface> notifiableClientTable = new Hashtable<String, ClientInterface>();
    
    private String marketName = null;
    private ServerMain marketServer;
    private DataHandler dataHandler=null;
 
    
    public MarketServiceImpl(ServerMain server, String marketName) throws RemoteException {
        super();
        this.marketName = marketName;
        this.marketServer= server;
    }

    @Override
    public synchronized ClientAccount register(String name, char[] password,BankAccount bankAccount) throws RemoteException {
        //Check if the account is already registered
        for (ClientAccount registerClient : clientAccountsList) {

            if (registerClient.getUserName().equals(name)) {
                throw new RemoteException("You account has been already registered");
            }
            // If it is not registered then register it
            
        }
        ClientAccount account = new ClientAccount(name, password,bankAccount);
         clientAccountsList.add(account);
         marketServer.getMainView().updateGUI();
        return account;
        
    }

    @Override
    public synchronized void unRegister(String name) throws RemoteException {

        Iterator<Item> iter = itemList.iterator();

        while (iter.hasNext()) {
            Item existItem = iter.next();

            if (existItem.getOwner().equals(name)) {
                iter.remove();
            }
        }
  
        for (ClientAccount registerClient : clientAccountsList) {
            if (registerClient.getUserName().equals(name)) {
                clientAccountsList.remove(registerClient);
                  
                
                break;
            }

        }
        notifiableClientTable.remove(name);
       
        
        marketServer.getMainView().updateGUI();

    }

    @Override
    public synchronized void sellItem(String itemName, float price, ClientAccount sellerAccount) throws RemoteException {

        String sellerName = sellerAccount.getUserName();

        Item itemForSell = new Item(itemName, price, sellerName);

        itemList.add(itemForSell);
        if(null!=wishItemList){
        
        for(Item wish:wishItemList){
            if(itemName.equals(wish.getItemName())&& (price<=wish.getItemPrice())){
                ClientInterface client = notifiableClientTable.get(wish.getOwner());  
                client.notifyItemAvailable(itemName, price);
            }
            
            
        }}
        marketServer.getMainView().updateGUI();
            

    }
   
    
    
    @Override
    public synchronized boolean buyItem(Item item, ClientAccount buyerAccount) throws RemoteException {
        float itemPrice = item.getItemPrice();
        BankAccount buyerBankAccount = buyerAccount.getBankAccount();

        //check if the buyer has enough money to buy the item
        if (buyerBankAccount.getBalance() < itemPrice) {
            return false;

        } else {

            for (ClientAccount accounts : clientAccountsList) {
                if (item.getOwner().equals(accounts.getUserName())) {
                    try {
                        BankAccount sellerBankAccount = accounts.getBankAccount();
                        sellerBankAccount.deposit(itemPrice);
                        buyerBankAccount.withdraw(itemPrice);
                        for (Item existItem : itemList) {
                            if (existItem.getItemID().equals(item.getItemID())) {
                                itemList.remove(existItem);
                                //wishItemList.remove(existItem);
                                ClientInterface client=this.notifiableClientTable.get(existItem.getOwner());
                                client.notifyItemSoldout(item.getItemName(), itemPrice);
                                break;
                            }
                            
                        }   
                    } catch (RejectedException ex) {
                        Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            
            marketServer.getMainView().updateGUI();
            return true;
        }

    }

    @Override
    public synchronized Item wishItem(String itemName, float price, ClientAccount wishBuyerAccount) throws RemoteException {
        String wishBuyerName = wishBuyerAccount.getUserName();
        Item wishedItem = new Item(itemName, price, wishBuyerName);
        wishItemList.add(wishedItem);
        return wishedItem;
        

    }

    @Override
    public synchronized ArrayList<Item> getAllItem() throws RemoteException {

        return itemList;

    }
     @Override
    public synchronized ArrayList<Item> getAllWishItem() throws RemoteException {

        return wishItemList;

    }
    
    @Override
    public synchronized boolean updateWishItemList(Item item)throws RemoteException{
        
        for(Item wishedItem:wishItemList){
            if(wishedItem.getItemID().equals(item.getItemID())){
                this.wishItemList.remove(wishedItem);
                break;
                
            }
        }
        return false;
        
    }
    

    @Override
    public synchronized void addClientNotifyObject(ClientInterface clientInterfaceObj, ClientAccount client) throws RemoteException {
      
         Enumeration<String> clientNames = notifiableClientTable.keys();
         while(clientNames.hasMoreElements()){
             String name = clientNames.nextElement();
             if(name.equals(client.getUserName())){
                 throw new RemoteException("You have already existed in the server's notify lists");
                 
             }
         }
         
        notifiableClientTable.put(client.getUserName(), clientInterfaceObj);
    }

    public synchronized ArrayList<ClientAccount>getAllClients() throws RemoteException {
        
       return clientAccountsList;
    }
    
    /**
     *
     *
     */
    
    public synchronized Hashtable<String, ClientInterface> getNotifiableClientTable()throws RemoteException{
        return notifiableClientTable;
    }

    void getDataHandler(DataHandler dataHandler) {
       
        this.dataHandler=dataHandler;


    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
