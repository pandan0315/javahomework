package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 *
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 8139546886405031234L;
    private final String name;
    private final float price;
    private final String owner;
    private String buyer=null;
    private final UUID itemID;
     private ItemState state = ItemState.OnSell;
    private String wisher;

    public Item(String name, float price, String owner) {
        this.name = name;
        this.price = price;
        this.owner = owner;
        this.itemID = UUID.randomUUID();

    }
    
    public Item(String name, float price,String seller, String buyer, UUID itemID, ItemState state) {
        
        this.name = name;
        this.price = price;  
        this.owner = seller;
        this.buyer= buyer;
        this.itemID = itemID;
        this.state = state;
    }

    
    public String getItemName() {
        return name;
    }

    public float getItemPrice() {
        return price;
    }

    public String getOwner() {
        return owner;
    }

    public UUID getItemID() {
        return itemID;

    }
    public ItemState getState(){
        return this.state;
    }

    public void setSold() {
        this.state = ItemState.Sold;
    }
    public void setBuyer(String buyer){
        this.buyer=buyer;
    }
    public String getBuyer(){
        return buyer;
    }
    @Override
    public String toString() {

        return name + " : " + price + " by " + owner;
    }

}
