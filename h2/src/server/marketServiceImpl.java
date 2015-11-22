package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import bank.*;

/**
 * Created by danpan on 22/11/15.
 */
public class MarketServiceImpl implements MarketService {

    private ArrayList<ClientAccount> clientAccountsList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();
    private ArrayList<Item> wishItemList = new ArrayList<>();


    @Override
    public void register(String name, BankAccount bankAccount) throws RemoteException {
        //Check if the account is already registered
        for (ClientAccount registerClient:clientAccountsList){

            if (registerClient.getUserName().equals(name)){
                throw new RemoteException("You account is already registered");
            }
            // If it is not registered then register it
            ClientAccountImpl account = new ClientAccountImpl(name,bankAccount);
            clientAccountsList.add(account);


        }

    }

    @Override
    public void unRegister(String name) throws RemoteException {
        for (ClientAccount registerClient:clientAccountsList){
            if(registerClient.getUserName().equals(name)){
                clientAccountsList.remove(registerClient);
                break;
            }

        }


    }

    @Override
    public void sellItem(String itemName,float price,ClientAccount sellerAccount) throws RemoteException {

        String sellerName=sellerAccount.getUserName();

        Item itemForSell=new Item(itemName,price,sellerName);

        itemList.add(itemForSell);


    }

    @Override
    public boolean buyItem(Item item,ClientAccount buyerAccount) throws RemoteException {
        float itemPrice = item.getItemPrice();
        BankAccount buyerBankAccount = buyerAccount.getBankAccount();

        //check if the buyer has enough money to buy the item
        if (buyerBankAccount.getBalance() < itemPrice) {
            return false;

        } else {

            for (ClientAccount accounts : clientAccountsList) {
                if (item.getOwner().equals(accounts.getUserName())) {
                    BankAccount sellerBankAccount = accounts.getBankAccount();
                    sellerBankAccount.deposit(itemPrice);
                    buyerBankAccount.withdraw(itemPrice);
                    for(Item existItem:itemList){
                        if( existItem.getItemID().equals(item.getItemID())){
                            itemList.remove(item);
                            break;
                        }

                    }



                }
            }
            return true;
        }


    }





    @Override
    public void wishItem(String itemName,float price,ClientAccount wishBuyerAccount) throws RemoteException {
        String wishBuyerName= wishBuyerAccount.getUserName();
        Item wishedItem=new Item(itemName,price,wishBuyerName);
        wishItemList.add(wishedItem);


    }

    @Override
    public List<Item> getAllItem() throws RemoteException {

        return itemList;


    }


}

