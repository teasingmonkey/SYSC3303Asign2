package Asign23;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DBWriter {
	private FileWriter fstream;
	private BufferedWriter out;
	private String fileName;
	
	public DBWriter(String name) throws IOException
	{
		fileName = name;
		
	}
	
	public void writeFile(String towrite) throws IOException
	{
		fstream = new FileWriter(fileName,true);
		out = new BufferedWriter(fstream);
		out.write(towrite);
		
	}
	
	public void closeFile() throws IOException{
		out.close();
	}
	
}
