package server;

import bank.bankAccount;

import java.rmi.RemoteException;

/**
 * Created by danpan on 22/11/15.
 */
public class clientAccountImpl implements clientAccount {

    private String name;
    private bankAccount bankaccount;



    public clientAccountImpl(String name,bankAccount bankaccount){
        this.name=name;
        this.bankaccount=bankaccount;
    }
    @Override
    public String getUserName() throws RemoteException {
        return name;
    }

    @Override
    public bankAccount getBankAccount() throws RemoteException {
        return bankaccount;
    }


    ;
    }
}
