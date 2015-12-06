/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import bank.Bank;
import bank.BankImpl;
import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class ServerMain {

    private MarketService service;
    private Bank bank;
    private ServerMainView view;
    private DataHandler dataHandler;
    
    
    
    
    public ServerMain() {
        
     
        
        try {
            service = new MarketServiceImpl(this, "Blocket");
            bank = new BankImpl();

            //Binding a name to a remote object reference at the RMI registry
            try {//get a remote RMI Regestry in the local host list
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
  
            Naming.rebind("ICABANK", bank);
            Naming.rebind("BlocketServer", service);

        } catch (RemoteException | MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        
             dataHandler = new DataHandler();
        
        try {
            dataHandler.getConnection();
            ((BankImpl)bank).getDataHandler(dataHandler);
            ((MarketServiceImpl)service).getDataHandler(dataHandler);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
          
            
            try {
                this.dataHandler.createBankAccountTable();
                this.dataHandler.createClientAccountTable();
                this.dataHandler.createItemTable();
                this.dataHandler.createWishTable();
            } catch (SQLException ex) {
                Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        ((MarketServiceImpl)service).setClientTable();
         ((MarketServiceImpl)service).setItemTable();
         
        /* Create and display the GUI*/
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new serverMainView(this).setVisible(true);
                createGUI();

            }
        });
        
        
        

    }

    public ServerMainView getMainView() {
        return view;

    }

    public MarketService getServer() {
        return service;
    }

    public static void main(String args[]) {
        new ServerMain();

    }

    private void createGUI() {
        view = new ServerMainView(this);
        view.setVisible(true);
    }
    
    
    
    

}
