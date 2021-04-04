import java.rmi.*;  
import java.io.IOException;

public interface ServerInterface extends Remote
{
    public boolean login(String username, int mpin) throws RemoteException;
    public boolean createCustomer(String username, String name, int mpin) throws RemoteException;
    public String printBalance(String username) throws RemoteException;
    public float deposit(String username, float amount) throws RemoteException;
    public boolean withdraw(String username, float amount) throws RemoteException;
    public boolean ping() throws RemoteException;

    // int getFileCount() throws RemoteException;
}