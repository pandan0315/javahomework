package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by danpan on 21/11/15.
 */
public class bankAccountImpl extends UnicastRemoteObject implements bankAccount {

    private float balance = 0;
    private String name;

    public bankAccountImpl(String name) throws RemoteException {
        super();
        this.name = name;
    }

    @Override
    public synchronized float getBalance() throws RemoteException {
        return 0;
    }

    @Override
    public synchronized void deposit(float value) throws RemoteException, RejectedException {

        if (value<0){
            throw new RejectedException("Rejected: Account \" + name + \": Illegal value: "+ value);
        }
        balance += value;
        System.out.println("Transaction: Account " + name + ": deposit: $" + value + ", balance: $"
                + balance);

    }
}
