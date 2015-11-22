package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by danpan on 22/11/15.
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 8139546886405031234L;
    private String name;
    private float price;
    private String owner;
    private UUID itemID;

    public Item(String name, float price, String owner ){
        this.name=name;
        this.price=price;
        this.owner=owner;
        this.itemID=UUID.randomUUID();

    }


    public String getItemName()  {
        return name;
    }


    public float getItemPrice()  {
        return price;
    }


    public String getOwner()  {
        return owner;
    }

    public UUID getItemID() {
        return itemID;

    }

}
