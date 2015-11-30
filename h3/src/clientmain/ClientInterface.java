/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmain;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author danpan
 */
//remote client interface allows server to call back a client
public interface ClientInterface extends Remote { 
    
    //notify client wishitem is available
    public void notifyItemAvailable(String itemName, float itemPrice) throws RemoteException;
    
    //notify client his item is sold
    
    public void notifyItemSoldout(String itemName, float itemPrice, float balance) throws RemoteException;
    
    public void notifyItemBought(String itemName, float itemPrice, float balance) throws RemoteException;
    
}
