package Asign22;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameInitializer {
	
	private static GameServe g;
	private static ServerSocket ss;
	private static MultiThreadServer s1, s2;
	private static ServerSocket ssock;
	private static int counter;
	
	public GameInitializer() throws IOException {
		 ssock = new ServerSocket(6799);
	    
	    s1 =  new MultiThreadServer();// Prethreaded Servers
	    s2 =  new MultiThreadServer();// Prethreaded Servers
	    g = new GameServe();
	    counter = g.getLinkedQueue().size();
	    //initialize();
	   
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
		         
				try {
					Socket sock = ssock.accept();
					System.out.println("Connected");
			         g.addSocket(sock);
			         g.addLinkedQueue(sock);
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
			while (true) {
		         if(s1.getAvailable()&& g.getLinkedQueue().size()!=0)
		         {
		        	 s = g.getLinkedQueue().pop();
		        	 s1.addrun(s, g);
		         }
		         if(s2.getAvailable() && g.getLinkedQueue().size()!=0)
		         {
		        	 s = g.getLinkedQueue().pop();
		        	 s2.addrun(s, g);
		         }
			}
		}
	}

}
