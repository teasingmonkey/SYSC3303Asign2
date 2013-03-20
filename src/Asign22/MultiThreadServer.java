package Asign22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Number;



public class MultiThreadServer {
   public static Socket csocket;
   public static PrintStream writer;
   public static GameServe gamePlayer;
   public static Game game;
   private static boolean available;
   
   MultiThreadServer(Socket csocket, GameServe g) {
      this.csocket = csocket;
      gamePlayer = g;
      game = new Game(g); 
      available = true;
   }
   
   MultiThreadServer()
   {
	   available = true;
   }
   
   public boolean getAvailable(){
	   return available;
   }
   
   
   public static void AvailableOn()
   {
	   if(available)
	   {
		   available = false;
	   }
	   else
	   {
		   available = true;
	   }
   }
   
   public void run1() {
	   
	   new Thread(new ServerFromClient(csocket));
	   new Thread(new ServerToClient(csocket));
	  AvailableOn();
   }
   
   public void addrun(Socket cst, GameServe g) {
	      csocket = cst;
	      gamePlayer = g;
	      game = new Game(g); 
	      run1();
	   }
   
   public static void changeSockRun(Socket ss)
   {
	   new Thread(new ServerFromClient(ss));
	   new Thread(new ServerToClient(ss));
	  AvailableOn();
   }

	//----------------------------------------------------------------------------------//
   	public static void changeSocket (Socket ss)
   	{
   		csocket = ss;
   		new Thread(new ServerToClient(ss));
   		new Thread(new ServerFromClient(ss));
   	}
	//----------------------------------------------------------------------------------//
	public static class ServerToClient extends Thread{
		
		Socket clientSocket;
		ServerToClient(Socket cs){
			clientSocket = cs;
			start();
		}

		public void run(){

			try{
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));	
			String s;

			while((s = in.readLine())!=null)
			{
				out.println(s);
			}
			}
			catch(IOException e){}		


		}
	}

	//----------------------------------------------------------------------------------//
	public static class ServerFromClient extends Thread{
			
			Socket socket;		
			ServerFromClient(Socket ss){
				socket = ss;
				start();
			}
		
			public void run(){
				try{
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String s; int gameNumber;
				String n;
				while(( s = in.readLine())!=null)
				{
					
					if(!socket.isClosed()){
						
					
					if(Integer.parseInt(s.substring(0, 5))==10000 && s.startsWith("1"))
							{
								s = s.substring(5);
								if(s.equalsIgnoreCase(".root")) //root is out loby
								{
									
									printList();
									gamePlayer.sendMessage(gamePlayer.getList().toString(), socket);
									//sendList();
									
								}
								if(s.startsWith("create"))
								{
								
									System.out.println(gamePlayer.getList().get(Integer.parseInt(s.split("-")[1].toString())));
									JoinGame(socket,gamePlayer.getList().get(Integer.parseInt(s.split("-")[1].toString())));
									gamePlayer.sendMessage("Game created " +
											"\n Enter join followed" +
											" by hyphen (-) and number" +
											" to play a game" +
											" with another player " +
											"\n or .root to see " +
											"which players are free " +
											"to join your game",
											gamePlayer.getList().get(Integer.parseInt(s.split("-")[1].toString())));
									System.out.println("-->" + s.split("-")[1].toString());
								}
								if(s.startsWith(".sock"))
								{
									int numInGameServe = gamePlayer.getList().indexOf(socket);
									gamePlayer.sendMessage("Your Sock -> " + socket.toString(), socket);
									gamePlayer.sendMessage("Your gameServe number -> " + numInGameServe, socket);
								}
								else
								{
									System.out.println("Client says:"+s);
								}
							
						
							}
					
					if(s.startsWith("3"))
					{	
						int p = Integer.parseInt(s.substring(1, 5));
						System.out.println(""+p);
						s = s.substring(5);						
						if(s.equalsIgnoreCase(".score")) //root is out loby
						{
							game.getScore(socket, p);
						}
						if(s.startsWith(".sock"))
						{
							int numInGameServe = gamePlayer.getList().indexOf(socket);
							gamePlayer.sendMessage("Your Sock -> " + socket.toString(), socket);
							gamePlayer.sendMessage("Your gameServe number -> " + numInGameServe, socket);
						}						
						if(s.equalsIgnoreCase(".root")) //root is out loby
						{
							
							printList();

							try {
								sendList(socket);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
						{
							game.addWord(socket, s);
						}
													
					}
				}else{
					AvailableOn();
				}
			}
				
				}
			catch(IOException e){}	
				
			}
		
	}
	
	//----------------------------------------------------------------------------------//
	public static void JoinGame(Socket s, Socket other) throws IOException
	{
		
		game.createGame(s, other);
		System.out.println(s.toString() + " " + "joined the game with -> " + other);
		

	}
	
	public static void printList()
	{
		for(int i = 0; i<gamePlayer.getList().size(); i++)
		{
			System.out.println(i + " " + gamePlayer.getList().get(i));
		}
	}
	
	public static void sendList(Socket s) throws IOException
	{
		for(int i = 0; i<gamePlayer.getList().size(); i++)
		{
			gamePlayer.sendMessage(i + " " + gamePlayer.getList().get(i), s);
			System.out.println(i + " " + gamePlayer.getList().get(i));
		}
	}
	
	//----------------------------------------------------------------------------------//
	
   /*
    * Main Method
    */
   public static void main(String args[]) throws Exception {
	   GameInitializer game = new GameInitializer();
	   game.initialize();
      
      }
   
   	
   
}


