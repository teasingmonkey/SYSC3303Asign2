package Asign23;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/*
 * THIS IS THE TEST CLASS FOR NEXY MILESTONE 3
 * FOR FURTHER READING OF THIS CLASS READ THE 
 * TEST PLAN PROVIDED IN PDF FILE.
 * 
 * 1. TYPES OF TESTING :-
 * 		- Stress Test
 *      - Concurrency Test
 *      - Database Test
 *      - Misc. Test
 *      - Maximum Connection Test
 *      - Buffer Empty Test
 *      - Buffer Filling Test
 *  Right now this file contains few test out of the tests mentioned above.
 *  Testing will be a focused in Assignment 3.
 *  Also I am not going to use JUNIT Test. Instead I will be output the values
 *  on the console / file if necessary. 
 */

public class TestClass {

	private static TCPClient client;
	private static ArrayList<TCPClient> clientList;
	
	private static MultiThreadServer server;
	private static ArrayList<MultiThreadServer> serverList;
	
	private static Initializer initialize;
	
	public TestClass() throws UnknownHostException, IOException{
		Setup();
	}
	
	public void Setup() throws UnknownHostException, IOException
	{
		client = new TCPClient("localhost", 6788);
		clientList = new ArrayList<TCPClient>();
		
		initialize = new Initializer();
	}
	
	
}
