package server;

import bank.BankAccount;
import java.io.Serializable;

/**
 * Created by danpan on 22/11/15.
 */

public class ClientAccount implements Serializable {

    private String name;
    private BankAccount bankaccount;

    public ClientAccount(String name, BankAccount bankaccount) {
        this.name = name;
        this.bankaccount = bankaccount;
    }

    public synchronized String getUserName() {
        return name;
    }

    public synchronized BankAccount getBankAccount() {
        return bankaccount;
    }

}
