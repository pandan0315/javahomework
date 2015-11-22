package server;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by danpan on 22/11/15.
 */
public class item implements Serializable {

    private String name;
    private float price;
    private String owner;

    public item(String name, float price, String owner ){
        this.name=name;
        this.price=price;
        this.owner=owner;
    }


    public String getItemName(String name) throws RemoteException {
        return name;
    }


    public float getItemPrice(float price) throws RemoteException {
        return price;
    }


    public String getOwner(String owner) throws RemoteException {
        return owner;
    }
}
