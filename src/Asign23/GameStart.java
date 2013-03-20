package Asign23;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class GameStart {
	
	public Socket s1;
	public Socket s2;
	
	public ArrayList<String> player1Words;
	public ArrayList<String> player2Words;
	public int player1counter;
	public int player2counter;
	public Map<Socket, ArrayList<String>> playerData;
	
	
	public GameStart(Socket s1, Socket s2) {
		player1Words = new ArrayList<String>();
		player2Words = new ArrayList<String>();
		playerData = new HashMap<Socket, ArrayList<String>>();
		this.s1 = s1;
		this.s2 = s2;
		playerData.put(this.s1, player1Words);
		playerData.put(this.s2, player2Words);
		player1counter = 0;
		player2counter = 0;
	}
	
	public int getPlayer1Score()
	{
		return player1counter;
	}
	
	public int getPlayer2Score()
	{
		return player2counter;
	}
	
	public void incrementPlayer1Counter()
	{
		player1counter++;
	}
	
	public void incrementPlayer2Counter()
	{
		player2counter++;
	}
	
	public void sortPlayer1()
	{
		Collections.sort(player1Words);
	}
	
	public void sortPlayer2()
	{
		Collections.sort(player2Words);
	}
	
	public void emptyPlayer1Buffer()
	{
		if(player2Words.size()>=3)
		{
			for(int i = 0; i<3; i++){
				player2Words.remove(0);
			}
		}
	}
	
	public void emptyPlayer2Buffer()
	{
		if(player2Words.size()>=3)
		{
			for(int i = 0; i<3; i++){
			
			player2Words.remove(0);
			}
		}
	}
	
}
