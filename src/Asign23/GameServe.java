package Asign23;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class GameServe {
	
	   public static ArrayList<Socket> connectionTable;
	   public ArrayBlockingQueue<Socket> connectionQueue;
	   
	   
	   public GameServe(){
		   connectionTable = new ArrayList<Socket>();
		   connectionQueue = new ArrayBlockingQueue<Socket>(5);
	   }
	   
	   public ArrayBlockingQueue<Socket> getQueue()
	   {
		   return connectionQueue;
	   }
	   
	   public void addQueue(Socket s) throws IOException
	   {
		   
		   if(connectionQueue.add(s))
		   {
			   sendMessage("You have been added to the queue",s);
		   }
	   }
	   
	   public void addSocket(Socket s)
	   {
		   connectionTable.add(s);
	   }
	   
	   public ArrayList<Socket> getList()
	   {
		   return connectionTable;
	   }
	   
	   public void sendMessage(String s, Socket clientSocket) throws IOException
		{
			
			PrintStream writer = new PrintStream(clientSocket.getOutputStream());
			writer.println(s);
		}
	   
	  

}
	