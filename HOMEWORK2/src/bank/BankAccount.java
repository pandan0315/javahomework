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
public interface BankAccount extends Remote{
     public float getBalance() throws RemoteException;

    public void deposit(float value) throws RemoteException;

    public void withdraw(float value) throws RemoteException;
    
}
