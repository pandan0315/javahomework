package server;

import bank.BankAccount;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by danpan on 22/11/15.
 */
public class ClientAccountImpl extends UnicastRemoteObject implements ClientAccount {

    private String name;
    private BankAccount bankaccount;



    public ClientAccountImpl(String name, BankAccount bankaccount) throws RemoteException{
        this.name=name;
        this.bankaccount=bankaccount;
    }
    @Override
    public String getUserName() throws RemoteException {
        return name;
    }

    @Override
    public BankAccount getBankAccount() throws RemoteException {
        return bankaccount;
    }



    }

