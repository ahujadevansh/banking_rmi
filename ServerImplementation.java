import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
class ServerImplementation extends UnicastRemoteObject implements ServerInterface {

    HashMap<String, Customer> customers;
	ServerImplementation()throws RemoteException{  
		super();
		customers = new HashMap<String, Customer>();
	}  
	public boolean createCustomer(String username, String name, int mpin) throws RemoteException{
		
		if(this.customers.get(username) != null) {
			return false;
		}

		Customer customer = new Customer(username, name, mpin);
		this.customers.put(username, customer);
		System.out.println(this.customers);
		System.out.println("New Account Created with username: " + username);
		return true;
	}  

	public boolean login(String username ,int mpin) throws RemoteException{
		
		Customer customer = this.customers.get(username);
		if(customer != null) {
			if (mpin == customer.mpin) {
				System.out.println(customer);
				
				// login part
				// System.out.println("ThankYou");
				System.out.println("New Login for " + username);
				return true;
			}
			else {
				System.out.println("Failed Login Attempted with incorrect mpin, username: " + username);
				return false;
			}
		}
		else {
			System.out.println("Failed Login Attempted with incorrect username, username: " + username);
			return false;
		}
	}
	public String printBalance(String username) throws RemoteException{
		Customer customer = this.customers.get(username);
		return customer.toString();
	}
	public float deposit(String username, float amount) throws RemoteException{
		Customer customer = this.customers.get(username);
		return customer.deposit(amount);
	}
	public boolean withdraw(String username, float amount) throws RemoteException{
		Customer customer = this.customers.get(username);
		return customer.withdraw(amount);
	}
	
	public boolean ping() throws RemoteException{
		return true;
	}

}