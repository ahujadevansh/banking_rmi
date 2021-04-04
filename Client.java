import java.net.MalformedURLException; 
import java.rmi.Naming; 
import java.rmi.NotBoundException; 
import java.rmi.RemoteException; 
import java.io.*;

public class Client {

    public static void main(String args[]) throws IOException, InterruptedException{
        String serverName = null;
        LoadBalancingInterface lb = null;
        try 
        {
            try
            { 
                lb = (LoadBalancingInterface) Naming.lookup("rmi://localhost/LoadBalancing");

                serverName = lb.getActiveServer();
                if (serverName == null) {
                    System.out.println("No Server Found");
                    throw new NotBoundException();
                }
            }
            catch (RemoteException re) 
            { 
                System.out.println("Main Server Down");
                // System.out.println("\nRemoteException: " + re); 
            }
            while (serverName != null) 
            {
                try 
                {
                    System.out.println(ProgressBar.ANSI_YELLOW);
                    ServerInterface s = (ServerInterface) Naming.lookup("rmi://localhost/" + serverName);
                    ProgressBar.progress();
                    
                    // Call the method for the results 
                    System.out.println("Welcome to Rmi Banking services                         ");
                    System.out.println(ProgressBar.ANSI_RESET);
                    int option;
                    do {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        System.out.println("1.Create Account\n2.login\n3.exit");
                        option = Integer.parseInt(br.readLine());
                        switch(option) {
                                case 1: 
                                System.out.print("Enter Your name: ");
                                String name = br.readLine();
                                System.out.print("Enter Your username: ");
                                String username = br.readLine();
                                System.out.print("Enter Your mpin: ");
                                int mpin = Integer.parseInt(br.readLine());

                                if(s.createCustomer(username, name, mpin)) {
                                    System.out.println(ProgressBar.ANSI_GREEN);
                                    System.out.println("Account Created Successfully!");
                                    System.out.println(ProgressBar.ANSI_RESET);
                                }
                                else {
                                    System.out.println(ProgressBar.ANSI_RED);
                                    System.out.println("Username Already Exists...Account Creation Failed!");
                                    System.out.println(ProgressBar.ANSI_RESET);
                                }

                                break;

                                case 2: 
                                System.out.print("Enter Your username: ");
                                username = br.readLine();
                                System.out.print("Enter Your mpin: ");
                                mpin = Integer.parseInt(br.readLine());
                                ProgressBar.progress();
                                if(s.login(username, mpin)) {
                                    System.out.println(ProgressBar.ANSI_GREEN);
                                    System.out.println("Log In Successful!                           ");
                                    System.out.println(ProgressBar.ANSI_RESET);
                                    // After Logging in---------------

                                    int logInOption;
                                    do {
                                        System.out.println("1.Check Balance\n2.Deposit\n3.Withdraw\n4.logout");
                                        logInOption = Integer.parseInt(br.readLine());
                                        switch(logInOption) {
                                            case 1: 
                                            System.out.println(ProgressBar.ANSI_CYAN);
                                            System.out.println(s.printBalance(username));
                                            System.out.println(ProgressBar.ANSI_RESET);
                                            break;
                                            case 2: 
                                            
                                            System.out.print("Enter amount: ");
                                            float amount = Float.parseFloat(br.readLine());
                                                
                                            ProgressBar.progress();
                                            System.out.println(ProgressBar.ANSI_YELLOW);
                                            System.out.println("Transaction Successful, Your updated balance is : " + s.deposit(username, amount));
                                            System.out.println(ProgressBar.ANSI_RESET);

                                            break;
                                            case 3: 
                                            System.out.print("Enter amount: ");
                                            float withDrawAmount = Float.parseFloat(br.readLine());
                                            ProgressBar.progress();
                                            if(s.withdraw(username, withDrawAmount)) {
                                                System.out.println(ProgressBar.ANSI_GREEN);
                                                System.out.println("Transaction Successful                   ");
                                                System.out.println(ProgressBar.ANSI_RESET);
                                            }
                                            else {
                                                System.out.println(ProgressBar.ANSI_RED);
                                                System.err.println("Transaction Failed!                       ");
                                                System.out.println(ProgressBar.ANSI_RESET);
                                            }



                                            break;
                                            case 4: System.out.println(ProgressBar.ANSI_CYAN); 
                                            System.out.println("Logging out"); 
                                            System.out.println(ProgressBar.ANSI_RESET);
                                            break;
                                            default: 
                                            System.out.println(ProgressBar.ANSI_RED);
                                            System.out.println("Invalid Option");
                                            System.out.println(ProgressBar.ANSI_RESET);
                                            break;
                                        }
                                    }	while(logInOption != 4);


                                    // End log in------------------
                                }
                                else {
                                    System.out.println(ProgressBar.ANSI_RED);
                                    System.err.println("Login Failed...Username or Mpin Incorrect");
                                    System.out.println(ProgressBar.ANSI_RESET);
                                }

                                break;
                                case 3: break;
                                default: 
                                System.out.println(ProgressBar.ANSI_RED);
                                System.out.println("Invalid Option"); 
                                System.out.println(ProgressBar.ANSI_RESET);
                                break;
                            }
                    } while (option != 3);
                    if(option == 3) {
                        break;
                    }
                }
                catch (RemoteException re) 
                { 
                    // System.out.println("\nRemoteException: "+ re);
                    System.out.println("Connection error.Please try again");
                    try
                    { 
                        serverName = lb.getActiveServer();
                        if (serverName == null) {
                            System.out.println("No Server Found");
                            throw new NotBoundException();
                        }
                    }
                    catch (RemoteException reInner) 
                    { 
                        System.out.println("Main Server Down");
                        // System.out.println("\nRemoteException: " + re); 
                    } 
                }
            }
        }
        
        catch (MalformedURLException murle) 
        { 
            System.out.println("Server Error");
            // System.out.println("\nMalformedURLException: " + murle); 
        } 
        catch (NotBoundException nbe) 
        { 
            System.out.println("Services Down \nPlease Try again later");
            // System.out.println("\nNotBoundException: " + nbe); 

        } 
    }
}