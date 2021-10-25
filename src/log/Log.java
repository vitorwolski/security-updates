package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log
{
	File log;
	
	public Log() throws IOException
	{
		this.log = new File("./log/log.txt");
		
		if(!this.log.exists())
		{
			this.log.createNewFile();
		}
	}
	
	public void escreveLog(String classe, String funcao, String log)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			
			Writer output = new BufferedWriter(new FileWriter("./log/log.txt", true));
			output.append(sdf.format(new Date()) + " - " + classe + "." + funcao + " - " + log + "\n\n");
			output.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
