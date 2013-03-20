package Asign23;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Initializer {
	
	private static GameServe g;
	private static ServerSocket ssock;
	private static int counter;
	
	public Initializer() throws IOException {
		ssock = new ServerSocket(6799);
	      g = new GameServe(); 
	      initialize();

	   
	}
	
	public void initialize()
	{	System.out.println("Listening");
		
		  Thread t1 = new ConnectionListener();
		     Thread t2 = new ConnectionsHandler();
		     
		     t1.start();
		     t2.start();
		    
	}
	
	
	public class ConnectionListener extends Thread
	{		
		public void run(){
			while (true) {
		         Socket sock;
				try {
					sock = ssock.accept();
					System.out.println("Connected");
			         g.addSocket(sock);
			         new Thread(new MultiThreadServer(sock,g)).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         
		         }
		}
	}
	
	public class ConnectionsHandler extends Thread
	{
		public void run(){
			Socket s;
			
			}
		
	}
	
}
