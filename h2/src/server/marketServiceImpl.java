package server;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import bank.*;

/**
 * Created by danpan on 22/11/15.
 */
public class marketServiceImpl implements marketService {
    private String name;
    private bankAccount bankaccount;


    @Override
    public void register(clientAccount client) throws RemoteException {

    }

    @Override
    public void unRegister(clientAccount client) throws RemoteException {

    }

    @Override
    public void sellItem(item item) throws RemoteException {

    }

    @Override
    public void buyItem(item item) throws RemoteException {

    }

    @Override
    public void wishItem(item item) throws RemoteException {

    }

    @Override
    public List<item> getAllItem() throws RemoteException {
        return null;
    }


    }
}
