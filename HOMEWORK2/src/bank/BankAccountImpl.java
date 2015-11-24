package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by danpan on 21/11/15.
 */
public class BankAccountImpl extends UnicastRemoteObject implements BankAccount {

    private float balance = 0;
    private String name;

    public BankAccountImpl(String name) throws RemoteException {
        super();
        this.name = name;
    }

    @Override
    public synchronized float getBalance() throws RemoteException {
        return balance;
    }

    @Override
    public synchronized void deposit(float value) throws RemoteException{


        balance += value;
        System.out.println("Transaction: Account " + name + ": deposit: $" + value + ", balance: $"
                + balance);

    }
    public synchronized void withdraw(float numberOfWithdraw) throws RemoteException{

        balance -= numberOfWithdraw;
        System.out.println("Transaction: Account " + name + ": withdraw: $" + numberOfWithdraw + ", balance: $" + balance);

    }
}
