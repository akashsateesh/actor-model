package samplePackage.proxy;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteImpl implements MyRemote, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String sayHello() throws RemoteException {
        System.out.println("I am server");
        Client2 client2 = (Client2) new Client();
        client2.get();
        return "Server says, Hi!!";
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        MyRemote service = new MyRemoteImpl();
        MyRemote remote = (MyRemote) UnicastRemoteObject.exportObject(service,0);

        //Bind the remote object stub in registry

        Registry registry = LocateRegistry.getRegistry(1099);
        registry.bind("RPC",remote);
    }
}
