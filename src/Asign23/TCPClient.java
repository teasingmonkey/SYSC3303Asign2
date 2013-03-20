package Asign23;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.imageio.IIOException;

import org.omg.CORBA.DataOutputStream;



class TCPClient 
{
	public static Socket client;
	public static PrintStream writer; //Writing to the server
	public static BufferedReader reader; //Read from server
	public static OutputStream out;
	public static InputStreamReader in;
	public static OutputStreamWriter wr;
	
	public static int gameOn = 10000;
	public static Socket secondPlayer;
	
	
	public TCPClient(String s , int port) throws UnknownHostException, IOException
	{
		client = new Socket(s,port);
		in = new InputStreamReader(client.getInputStream());
		reader = new BufferedReader(in);
		Thread t1 = new ClientFromServer(client);
		Thread t2 = new ClientToServer(client);
		//t1.start();
		//t1.start();
	}
	
	public static void turnOn(int num)
	{
		gameOn = num;
	}

	public void run1() throws IOException
	{	
		
		
		
	}
	
	//----------------------------------------------------------------------------------//
	public static class ClientFromServer extends Thread{

		Socket socket;	
		ClientFromServer(Socket s){
			socket = s;
			start();
		}

		public void run(){
			int p;
			try{
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String s;		
				while(( s = in.readLine())!=null)
				{
					if(s.startsWith("number"))
					{	p = Integer.parseInt(s.substring(5))+20000;
						turnOn(p);
					}
					if(s.startsWith("g"))
					{
						
						p = Integer.parseInt(s.substring(6))+30000;
						turnOn(p);
					}
					else
					System.out.println("Server says:"+s);
				}
			}catch(IOException e){}
		}

	}

	public static class ClientToServer extends Thread{
	Socket clientSocket;

	ClientToServer(Socket socket){
		clientSocket = socket;
		start();
	}

	public void run(){
	
		try{
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));	
		String s; String n;
		n = Integer.toString(gameOn) + Integer.toString(gameOn);
		while((s = in.readLine())!=null)
		{
			
			out.println(gameOn + s);

		}
		}catch(IOException e){}		
	}
	}
	
	public static class InnerRead extends Thread{
		Socket inClientRead;
		public InnerRead(Socket s)
		{
			inClientRead = s;
		}
		public void run(){
		
			
			
			while(true)
			{
				try {
					if(reader.ready())
					{
						System.out.println(reader.readLine());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}
	}
	
	/*
	 * Sends Message to the clientSocket (could be a server)
	 */
	public void sendMessage(String s, Socket clientSocket) throws IOException
	{
		writer = new PrintStream(clientSocket.getOutputStream());
		writer.print(s);
		writer.close();
	}
	
	/*
	 * Main Function
	 */
	public static void main(String argv[]) throws Exception
	{
		TCPClient myClient = new TCPClient("localhost", 6799);

 	}
 }
