/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author danpan
 */
public interface Bank extends Remote{

    /**
  
     */
    public BankAccount createAccount(String name) throws RemoteException,RejectedException;
    
    
}
