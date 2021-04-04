import java.rmi.*;  

public interface LoadBalancingInterface extends Remote {

    void register(ServerInterface slave, String serverName) throws RemoteException;
    void unregister(ServerInterface slave, String serverName) throws RemoteException;

    String getActiveServer() throws RemoteException;
}