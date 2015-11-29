package server;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
public class Item implements Serializable {

    private static final long serialVersionUID = 8139546886405031234L;
    private final String name;
    private final float price;
    private final String owner;
    private final UUID itemID;

    public Item(String name, float price, String owner) {
        this.name = name;
        this.price = price;
        this.owner = owner;
        this.itemID = UUID.randomUUID();

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

    @Override
    public String toString() {

        return name + " : " + price;
    }

}
