package Asign22;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Game {

public static ArrayList<String> Player1;
public static ArrayList<String> Player2;
public static ArrayList<Game> gameCreated;
public static ArrayList<Socket> totalPlayers; //--List of all subscribed players--//
public static ArrayList<Socket> gamePlayers; 
public static ArrayList<GameStart> totalGames; //--List of all the games with score--//
public static GameServe g;

	public Game(GameServe g)
	{	totalGames = new ArrayList<GameStart>();
		totalPlayers = new ArrayList<Socket>();
		this.g = g;
	}
	
	public void createGame(Socket p1, Socket p2) throws IOException
	{
		GameStart gs = new GameStart(p1, p2);
		if(totalGames.contains(gs)){
			g.sendMessage("You are already in game : " + totalGames.indexOf(gs) , p1);
			g.sendMessage("You are already in game : " + totalGames.indexOf(gs), p2);
		}
		else
		{
			totalGames.add(gs);
			g.sendMessage("New Game Established " + totalGames.indexOf(gs) , p1);
			g.sendMessage("gnumbe" + totalGames.indexOf(gs) , p1);
			g.sendMessage("New Game Established " + totalGames.indexOf(gs) , p2);
			g.sendMessage("gnumbe" + totalGames.indexOf(gs) , p2);
			System.out.print(totalGames);
		}
	}
	
	public ArrayList<GameStart> getGames()
	{
		return totalGames;
	}
	
	public void addWord(Socket p1, String wordP1)
	{
		for(GameStart gse: totalGames)
		{
			if(gse.s1.equals(p1))
			{
				gse.player1Words.add(wordP1);
			}
			if(gse.s2.equals(p1))
			{
				gse.player2Words.add(wordP1);
			}
		}
	}
	
	public void getScore(Socket p1, int i) throws IOException
	{
		if(totalGames.get(i).s1.equals(p1))
		{
			g.sendMessage("Your Score : " + totalGames.get(i).player1Words.size(), p1);
		}
		if(totalGames.get(i).s2.equals(p1))
		{
			g.sendMessage("Your Score : " + totalGames.get(i).player2Words.size(), p1);
		}
	}
}
