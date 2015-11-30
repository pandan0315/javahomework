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

    public void setClientTable() {
        this.clientAccountTable = this.dataHandler.getALLClient();
    }

    public void setItemTable() {
        this.itemTable = this.dataHandler.getAllItem();
    }

    public ArrayList<WishItem> getWishItems(ClientAccount client) throws RemoteException {
        return dataHandler.getWishItems(client);
    }

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
  /*  public synchronized void unRegister(String name) throws RemoteException {

     Iterator<Item> iter = itemList.iterator();

     while (iter.hasNext()) {
     Item existItem = iter.next();

     if (existItem.getOwner().equals(name)) {
     iter.remove();
     }
     }
        

     }
     notifiableClientTable.remove(name);
       
        
     marketServer.getMainView().updateGUI();

     }*/
    @Override
    public synchronized void sellItem(String itemName, float price, ClientAccount sellerAccount) throws RemoteException {

        String sellerName = sellerAccount.getUserName();

        Item itemForSell = new Item(itemName, price, sellerName);
        this.itemTable.put(itemForSell.getItemID(), itemForSell);

        this.dataHandler.storeItem(itemForSell);

        ArrayList<WishItem> wantedItems = dataHandler.getMatchedWishItems(itemForSell);
        if (wantedItems == null) {
            return;
        }
        for (WishItem wanted : wantedItems) {
            // We have notify the notifiable clients
            ClientInterface client = notifiableClientTable.get(wanted.getWisher());
            try {
                client.notifyItemAvailable(itemName, price);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }

        marketServer.getMainView().updateGUI();

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

                sellerBankAccount.deposit(itemPrice);
                buyerBankAccount.withdraw(itemPrice);
                Item findItem = this.itemTable.get(item.getItemID());

                if (findItem != null) {
                    findItem.setSold();
                    findItem.setBuyer(buyerAccount.getUserName());
                    this.dataHandler.updateItem(findItem);
                    ClientInterface client = this.notifiableClientTable.get(findItem.getOwner());
                    client.notifyItemSoldout(findItem.getItemName(), itemPrice);
                    //itemTable.remove(item.getItemID(), findItem);
                }

            } catch (RejectedException ex) {
                Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;

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

                sellerBankAccount.deposit(itemPrice);
                buyerBankAccount.withdraw(itemPrice);
                Item findItem = this.itemTable.get(item.getItemID());

                if (findItem != null) {
                    findItem.setSold();
                    findItem.setBuyer(buyerAccount.getUserName());
                    this.dataHandler.updateItem(findItem);
                    ClientInterface client = this.notifiableClientTable.get(findItem.getOwner());
                    client.notifyItemSoldout(item.getItemName(), itemPrice);
                    itemTable.remove(item.getItemID(), findItem);
                }

            } catch (RejectedException ex) {
                Logger.getLogger(MarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;

        }

        marketServer.getMainView().updateGUI();
        return false;

    }

    @Override
    public synchronized WishItem wishItem(String itemName, float price, ClientAccount wishBuyerAccount) throws RemoteException {
        String wishBuyerName = wishBuyerAccount.getUserName();
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

        return itemTable;

    }

   // @Override
   /* public synchronized ArrayList<Item> getAllWishItem() throws RemoteException {

        return wishItemList;

    }*/

    @Override
    public synchronized ArrayList<WishItem> updateWishItemList(ClientAccount client,WishItem item) throws RemoteException {
          ArrayList<WishItem> wishedItem=this.dataHandler.getWishItems(client);
        for (WishItem itemWish : wishedItem) {
            if(itemWish.getWishItemName().equals(item.getWishItemName())){
                wishedItem.remove(item);
                
           
                break;

            }
        }
        return wishedItem;

    }
    
    @Override
    public synchronized ArrayList<WishItem> getWishedList(ClientAccount client)throws RemoteException{
        return this.dataHandler.getWishItems(client);
        
    }
    @Override
    public synchronized void addClientNotifyObject(ClientInterface clientInterfaceObj, ClientAccount client) throws RemoteException {

       Set <String> clientNames = notifiableClientTable.keySet();
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

        return this.clientAccountTable;
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

}
