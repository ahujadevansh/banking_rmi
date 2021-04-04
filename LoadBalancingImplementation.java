import java.rmi.*; 
import java.rmi.server.*;
import java.util.ArrayList;
import java.net.MalformedURLException; 
import java.rmi.NotBoundException; 
import java.rmi.RemoteException;

class LoadBalancingImplementation extends UnicastRemoteObject implements LoadBalancingInterface {

    // HashMap<String, ServerInterface> servers;
    // String server;
    ArrayList<String> servers;
    LoadBalancingImplementation()throws RemoteException{  
		super();
        servers = new ArrayList();
	}  
    public void register(ServerInterface server, String serverName) throws RemoteException {
        try { 
			// Bind the localhost with the service
            servers.add(serverName);
            Naming.rebind("rmi://localhost/" + serverName, server); 
		} 
		catch (MalformedURLException murle) { 
			System.out.println("\nMalformedURLException: "
							+ murle); 
		} 
		catch (RemoteException re) { 
			System.out.println("\nRemoteException: "
							+ re); 
		}
        System.out.println(serverName + " Registered");
    }
    public void unregister(String serverName) throws RemoteException {
        try {
            Naming.unbind("rmi://localhost/" + serverName);
            if (this.servers.indexOf(serverName) != -1)
            {
                this.servers.remove(serverName);
            }
        } 
        catch (MalformedURLException murle) { 
			System.out.println("\nMalformedURLException: "
							+ murle); 
		} 
		catch (RemoteException re) { 
			System.out.println("\nRemoteException: "
							+ re); 
		} 
		catch (NotBoundException nbe) { 
			System.out.println("\nNotBoundException: "
							+ nbe); 
		}
        System.out.println(serverName + " Unregistered");
    }

    public String getActiveServer() throws RemoteException {

        LoadBalancingInterface lb = null;
        int i = 0;
        while(this.servers.size() > 0) {
            try {
                ServerInterface s = (ServerInterface) Naming.lookup("rmi://localhost/" + this.servers.get(0));
                if (s.ping()){
                    return this.servers.get(0);
                }
            } 
            catch (MalformedURLException murle) { 
                System.out.println("\nMalformedURLException: " + murle); 
            } 
            catch (RemoteException re) { 
                System.out.println("\nRemoteException: " + re);
                try {
                    lb = (LoadBalancingInterface) Naming.lookup("rmi://localhost/LoadBalancing");
                    lb.unregister(this.servers.get(0));
                }
                catch(Exception e) {
                    System.out.println("\nException: " + e);
                }
            } 
            catch (NotBoundException nbe) { 
                System.out.println("\nNotBoundException: " + nbe); 
                try {
                    lb = (LoadBalancingInterface) Naming.lookup("rmi://localhost/LoadBalancing");
                    lb.unregister(this.servers.get(0));
                }
                catch(Exception e) {
                    System.out.println("\nException: " + e);
                }
            }
        }
        return null;
    }

}