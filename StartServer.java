import java.rmi.Naming; 
import java.util.regex.Pattern;
import java.util.Locale;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.io.*;
public class StartServer { 

    String name;
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
  	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	// Implement the constructor of the class 
	public StartServer() throws IOException
	{ 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter Server Name: ");
        this.name = StartServer.toSlug(br.readLine());
		try { 
			// Create a object reference for the interface 
			ServerInterface s = new ServerImplementation(); 
            LoadBalancingInterface lb = (LoadBalancingInterface) Naming.lookup("rmi://10.0.2.9/LoadBalancing");
			lb.register(s, this.name); 
		} 
		catch (Exception e) { 
			// If any error occur 
			System.out.println("ERR: " + e); 
		} 
	} 
	protected void finalize() throws Throwable  
	{  
		LoadBalancingInterface lb = (LoadBalancingInterface) Naming.lookup("rmi://10.0.2.9/LoadBalancing");
		lb.unregister(this.name);
	}  

	public static String toSlug(String input) {
		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}

	public static void main(String[] args) throws IOException
	{ 
		// Create an object 
		new StartServer(); 
	} 
} 


// java -Djava.rmi.server.hostname=3.22.222.201 AdderServer