import java.rmi.*;
import java.rmi.registry.*;

class MasterServer {
    String name;
	// Implement the constructor of the class 
	public MasterServer()
	{ 
        try { 
			LocateRegistry.createRegistry(1099);
            LoadBalancingInterface lb = new LoadBalancingImplementation();
			// Bind the localhost with the service 
			Naming.rebind("rmi://localhost/LoadBalancing", lb);
			// DatabaseInterface db = new DatabaseImplementation();
			// Naming.rebind("rmi://localhost/db", db);
		} 
		catch (Exception e) { 
			// If any error occur 
			System.out.println("ERR: " + e); 
		} 
		
	} 

	public static void main(String[] args) 
	{ 
		// Create an object 
		new MasterServer(); 
	} 
}