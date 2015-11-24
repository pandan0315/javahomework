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


/**
 *
 * @author danpan
 */
public class serverMain {
    
    private MarketService service;
    private Bank bank;
    private serverMainView view;
    
    public serverMain(){
        try{
        service = new MarketServiceImpl(this,"Blocket");
        bank = new BankImpl();
         
        //rmi registry
       try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
       Naming.rebind("ICABANK", bank);
       Naming.rebind("BlocketServer", service);
        }catch(Exception e){
          System.err.println(e.getMessage());  
        }
       
        /* Create and display the GUI*/
         javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new serverMainView(this).setVisible(true);
                createGUI();

       
            }
        });
        
    }
    
    public serverMainView getMainView(){
        return view;
        
    }
    
    public MarketService getServer(){
        return service;
    }
     public static void main(String args[]) {
         new serverMain();
       
       
    }
     
     private void createGUI(){
         view=new serverMainView(this);
        view.setVisible(true);
     }
    
}
