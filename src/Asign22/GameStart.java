package Asign22;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameStart {
	
	public Socket s1;
	public Socket s2;
	
	public ArrayList<String> player1Words;
	public ArrayList<String> player2Words;
	
	public Map<Socket, ArrayList<String>> playerData;
	
	public GameStart(Socket s1, Socket s2) {
		player1Words = new ArrayList<String>();
		player2Words = new ArrayList<String>();
		playerData = new HashMap<Socket, ArrayList<String>>();
		this.s1 = s1;
		this.s2 = s2;
		playerData.put(this.s1, player1Words);
		playerData.put(this.s2, player2Words);
	}

}
