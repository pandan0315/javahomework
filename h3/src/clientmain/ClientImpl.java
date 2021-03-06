/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author danpan
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface{
    
    private ClientMainJframe clientGui=null;
    
    public ClientImpl(ClientMainJframe cg) throws RemoteException{
            this.clientGui = cg;
    }

    @Override
    public synchronized void notifyItemAvailable(String itemName, float itemPrice) throws RemoteException {
        clientGui.addMessage("The item " + itemName + " with your interested price $" + itemPrice + " is available.");
        
    }

    @Override
    public synchronized void notifyItemSoldout(String itemName, float itemPrice,float balance) throws RemoteException {
        clientGui.addMessage("your item "+itemName+" has been sold in $ "+itemPrice+".Your has "+balance+" left."+"\n");
        
    }
    
    @Override
    public synchronized void notifyItemBought(String itemName, float itemPrice,float balance) throws RemoteException {
        clientGui.addMessage("your bought "+itemName+" in $ "+itemPrice+".Your has "+balance+" left."+"\n");
        
    }
    
}
