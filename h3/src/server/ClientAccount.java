package server;

import bank.BankAccount;
import java.io.Serializable;

/**
 * Created by danpan on 22/11/15.
 */

public class ClientAccount implements Serializable {

    private String name;
    private char[] password;
    private BankAccount bankaccount;

    /*public ClientAccount(String name,  char[] password,BankAccount bankaccount) {
        this.name = name;
        this.password=password;
        this.bankaccount = bankaccount;
    }*/

    ClientAccount(String name, char[] password,BankAccount bankAccount) {
        this.name=name;
        this.password=password;
        this.bankaccount=bankAccount;
    }

    public synchronized String getUserName() {
        return name;
    }

    public synchronized BankAccount getBankAccount() {
        return bankaccount;
    }
    
    public synchronized char[] getPassword(){
        return password;
    }

}
