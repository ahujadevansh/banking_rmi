import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
class DatabaseImplementation extends UnicastRemoteObject implements DatabaseInterface {

    HashMap<String, Customer> customers = null;
	DatabaseImplementation()throws RemoteException{  
		super();
		// customers = new HashMap<String, Customer>();
	}

    public Customer getCustomer(String username) throws RemoteException {
        return this.customers.get(username);
    }

    
}