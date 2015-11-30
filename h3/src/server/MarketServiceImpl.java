package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import bank.*;
import clientmain.ClientInterface;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class MarketServiceImpl extends UnicastRemoteObject implements MarketService {

    private HashMap<String, ClientAccount> clientAccountTable = new HashMap<>();
    //private ArrayList<ClientAccount> clientAccountsList = new ArrayList<>();
    private HashMap<UUID, Item> itemTable = new HashMap<>();
    //private ArrayList<Item> itemList = new ArrayList<>();
    //private ArrayList<WishItem> wishItemList = new ArrayList<>();
    private HashMap<String, ClientInterface> notifiableClientTable = new HashMap<>();

    private String marketName = null;
    private ServerMain marketServer;
    private DataHandler dataHandler = null;

   

   // public ArrayList<WishItem> getWishItems(ClientAccount client) throws RemoteException {
     //   return dataHandler.getWishItems(client);
    //}

    public MarketServiceImpl(ServerMain server, String marketName) throws RemoteException {
        super();
        this.marketName = marketName;
        this.marketServer = server;

    }

    @Override
    public synchronized ClientAccount register(String name, char[] password, BankAccount bankAccount) throws RemoteException {
        //Check if the account is already registered
        ClientAccount clientaccount = clientAccountTable.get(name);

        if (clientaccount != null) {
            return null;
            // If it is not registered then register it

        }
        

        try {
            clientaccount = this.dataHandler.getClientAccount(name);

        } catch (RejectedException ex) {
            Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (clientaccount != null) {
            clientAccountTable.put(name, clientaccount);
            return null;
        }
        clientaccount = new ClientAccount(name, password, bankAccount);
        this.dataHandler.storeClientAccount(clientaccount);
        clientAccountTable.put(name, clientaccount);
        
        marketServer.getMainView().updateGUI();
        return clientaccount;

    }

    //@Override
    @Override
  public synchronized void unRegister(String name,char[] password) throws RemoteException{
      
      
      ClientAccount client= clientAccountTable.get(name);
      
      if(client!=null){
          notifiableClientTable.remove(name);
          clientAccountTable.remove(name); 
        this.dataHandler.removeAllWishItem(name);
        this.dataHandler.removeItems(name);
          try {
              this.dataHandler.removeClient(name,password);
          } catch (RejectedException ex) {
              Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
          }
         
      }
      
      
        
     marketServer.getMainView().updateGUI();
     }
     

     
    @Override
    public synchronized void sellItem(String itemName, float price, ClientAccount sellerAccount) throws RemoteException {

        String sellerName = sellerAccount.getUserName();

        Item itemForSell = new Item(itemName, price, sellerName);
        this.itemTable.put(itemForSell.getItemID(), itemForSell);

        this.dataHandler.storeItem(itemForSell);
         marketServer.getMainView().updateGUI();
        
        //ArrayList<WishItem> wantedItems = dataHandler.getMatchedWishItems(itemForSell);
        WishItem wantedItem=dataHandler.getMatchedWishItem(itemForSell);
        if (wantedItem == null) {
            return;
        }
            // We have notify the notifiable clients
            ClientInterface client = notifiableClientTable.get(wantedItem.getWisher());
            try {
                client.notifyItemAvailable(itemName, price);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        

        
      
    }

    @Override
    public synchronized boolean buyItem(Item item, ClientAccount buyerAccount) throws RemoteException {
        float itemPrice = item.getItemPrice();
        BankAccount buyerBankAccount = buyerAccount.getBankAccount();
        
        //check if the buyer has enough money to buy the item
        if (buyerBankAccount.getBalance() < itemPrice) {
            return false;

        }
        ClientAccount account = this.clientAccountTable.get(item.getOwner());
        if (account != null) {
            try {

                BankAccount sellerBankAccount = account.getBankAccount();

                item.setSold();
                item.setBuyer(buyerAccount.getUserName());
                this.dataHandler.updateItem(item);
                ClientInterface client = this.notifiableClientTable.get(item.getOwner());
                sellerBankAccount.deposit(itemPrice);
                client.notifyItemSoldout(item.getItemName(), itemPrice, sellerBankAccount.getBalance());
                buyerBankAccount.withdraw(itemPrice);
                client.notifyItemBought(item.getItemName(), itemPrice, buyerBankAccount.getBalance());
                
                
                marketServer.getMainView().updateGUI();
                
                //client.notifyItemBought(item.getItemName(), itemPrice, buyerBankAccount.getBalance());
                //client.notifyItemSoldout(item.getItemName(), itemPrice, sellerBankAccount.getBalance());
                 

                return true;

            } catch (RejectedException ex) {
                Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        }

        try {
            account = this.dataHandler.getClientAccount(item.getOwner());
        } catch (RejectedException ex) {
            Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (account != null) {
            clientAccountTable.put(item.getOwner(), account);
            try {

                BankAccount sellerBankAccount = account.getBankAccount();
                
                    item.setSold();
                    item.setBuyer(buyerAccount.getUserName());
                    this.dataHandler.updateItem(item);
                    sellerBankAccount.deposit(itemPrice);
                    buyerBankAccount.withdraw(itemPrice);
                    ClientInterface client = this.notifiableClientTable.get(item.getOwner());
                    marketServer.getMainView().updateGUI();
                    client.notifyItemSoldout(item.getItemName(), itemPrice,sellerBankAccount.getBalance());
                    client.notifyItemBought(item.getItemName(), itemPrice, buyerBankAccount.getBalance());

                    return true;
                

            } catch (RejectedException ex) {
                Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                
               return false;
            }

        }

     return false;
    }

    @Override
    public synchronized WishItem wishItem(String itemName, float price, ClientAccount wishBuyerAccount) throws RemoteException {

        String wishBuyerName = wishBuyerAccount.getUserName();
        ArrayList<WishItem> existedWishes = this.dataHandler.getWishItems(wishBuyerAccount);
        for (WishItem exist : existedWishes) {
            if (exist.getWishItemName().equals(itemName)) {
                return null;

            }

        }
        WishItem wishedItem = new WishItem(itemName, price, wishBuyerName);

        dataHandler.storeWish(wishedItem);

        //wishItemList.add(wishedItem);
        return wishedItem;

    }

    /**
     *
     * @return @throws RemoteException
     */
    @Override
    public synchronized HashMap<UUID, Item> getAllItem() throws RemoteException {
        //this.itemTable=this.dataHandler.getAllItem();
        return this.dataHandler.getAllItem();

    }

   // @Override
   /* public synchronized ArrayList<Item> getAllWishItem() throws RemoteException {

     return wishItemList;

     }*/
    @Override
    public synchronized void updateWishItemList(ClientAccount client, WishItem item) throws RemoteException {

        this.dataHandler.removeWishItem(item);

        //return this.getWishedList(client);
    }

    @Override
    public synchronized ArrayList<WishItem> getWishedList(ClientAccount client) throws RemoteException {
        return this.dataHandler.getWishItems(client);

    }

    @Override
    public synchronized void addClientNotifyObject(ClientInterface clientInterfaceObj, ClientAccount client) throws RemoteException {

        Set<String> clientNames = notifiableClientTable.keySet();
        Iterator<String> iterator = clientNames.iterator();
       
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (name.equals(client.getUserName())) {
                return;
                //throw new RemoteException("You have already existed in the server's notify lists");

            }
        }

        notifiableClientTable.put(client.getUserName(), clientInterfaceObj);
    }

    public synchronized HashMap<String, ClientAccount> getAllClients() throws RemoteException {
       //this.clientAccountTable=this.dataHandler.getALLClient();
        return this.dataHandler.getALLClient();
    }

    /**
     *
     *
     * @return 
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized HashMap<String, ClientInterface> getNotifiableClientTable() throws RemoteException {
        
        return notifiableClientTable;
    }

    void getDataHandler(DataHandler dataHandler) {

        this.dataHandler = dataHandler;

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClientAccount login(String name, char[] password) throws RemoteException {
        ClientAccount client = null;
        try {
            client = this.dataHandler.getClientAccountByNameAndPassword(name, password);
        } catch (RejectedException ex) {
            Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (client != null) {
            return client;
        }

        return null;
    }

    public synchronized void setClientTable() {
        this.clientAccountTable= this.dataHandler.getALLClient();
        
    }

    public synchronized void setItemTable() {
        this.itemTable=this.dataHandler.getAllItem();
        
    }

    @Override
    public synchronized ClientAccount getMatchedClient(String name,char[] password) throws RemoteException{
        try {
            return this.dataHandler.getClientAccountByNameAndPassword(name, password);
        } catch (RejectedException ex) {
            Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void logout(ClientAccount client) throws RemoteException {
       this.notifiableClientTable.remove(client.getUserName());
       
    }

    @Override
    public int getBoughtAmount(String name) throws RemoteException {
       
         return this.dataHandler.getBoughtAmount(name);
    }

    @Override
    public int getSelledAmount(String userName) throws RemoteException {
       return this.dataHandler.getSelledAmount(userName);
    }
    
   
}
