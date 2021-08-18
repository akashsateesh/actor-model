package samplePackage.proxy;

import javax.naming.Name;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String [] args) throws RemoteException, NotBoundException, MalformedURLException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1");
        MyRemote remote = (MyRemote) registry.lookup("RPC");
        String response = remote.sayHello();
        System.out.println(response);
    }
}
