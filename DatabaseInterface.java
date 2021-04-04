import java.rmi.*;  
import java.util.HashMap;
public interface DatabaseInterface extends Remote {
    static HashMap<String, Customer> customers = new HashMap<String, Customer>();
    // public Customer getCustomer(String username) throws RemoteException;
}