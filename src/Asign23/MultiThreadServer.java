package Asign23;

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



public class MultiThreadServer extends Thread {
   private static Socket csocket;
   private static PrintStream writer;
   private static GameServe gamePlayer;
   private static Game game;
   private static DBWriter write;
   MultiThreadServer(Socket csocket, GameServe g) throws IOException {
      this.csocket = csocket;
      gamePlayer = g;
      game = new Game(g); 
      write = new DBWriter("Database.txt");
   }

   public void run() {
	   
	   
	   new Thread(new ServerFromClient(csocket));
	   new Thread(new ServerToClient(csocket));

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
							if(Integer.parseInt(game.getScoreForFile(socket, p))%3==0)
							{
								
								write.writeFile("Words Written By you :-" +
										"\n" +
										game.getWords(socket, p) + "\n" + "Your Score ->" + game.getScoreForFile(socket, p));
								write.closeFile();
								game.emptyBuffer(socket, p);
							}
							
						}
													
					}
					}
					else
					{
						
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
      ServerSocket ssock = new ServerSocket(6799);
      System.out.println("Listening");
      GameServe g;
      int Counter = 0;
      
      g = new GameServe(); 
      while (true) {
    	 if(Counter ==0)
    	 {
    		 Socket sock = ssock.accept();
             System.out.println("Connected");
             g.addSocket(sock);
             new Thread(new MultiThreadServer(sock, g)).start();
    	 }
    	 else
    	 {
    		 Socket sock = ssock.accept();
             System.out.println("Connected");
             g.addSocket(sock);
             g.addQueue(sock);
             new Thread(new MultiThreadServer(sock, g)).start();
    	 }
         
         }

      }
   
}


