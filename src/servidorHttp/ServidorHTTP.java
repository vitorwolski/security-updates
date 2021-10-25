package servidorHttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import database.Database;
import entidades.SecurityUpdate;

public class ServidorHTTP
{
	public static void inicializa() throws IOException
	{
		int port = 64779;
		
		ServerSocket serverSocket = new ServerSocket(port);
		
		while(true)
		{
			Socket clientSocket = serverSocket.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String s;
			
			while((s = in.readLine()) != null)
			{
				if(s.isEmpty())
				{
					break;
				}
			}
			
			OutputStream clientOutput = clientSocket.getOutputStream();
			
			Date date = new Date();
			
			clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
			clientOutput.write("Date: ".concat(date.toString()+"\r\n").getBytes());
			clientOutput.write("Server: localhost:64779\r\n".getBytes());
			clientOutput.write("Access-Control-Allow-Origin: *\r\n".getBytes());
			clientOutput.write("Keep-Alive: timeout=2, max=100\r\n".getBytes());
			clientOutput.write("Connection: keep-alive\r\n".getBytes());
			clientOutput.write("Content-Type: application/json\r\n".getBytes());
			clientOutput.write("\r\n".getBytes());
			
			ArrayList<SecurityUpdate> lista = SecurityUpdate.carregaListaSecurityUpdates();
			
			JSONArray listaJson = new JSONArray();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			for(int i = 0; i < lista.size(); i++)
			{
				SecurityUpdate sec = lista.get(i);
				
				JSONObject json = new JSONObject();
				json.put("id", sec.getId());
				json.put("alias", sec.getAlias());
				json.put("documentTitle", sec.getDocumentTitle());
				json.put("severity", sec.getSeverity());
				json.put("iniRlsDate", sdf.format(sec.getInitialReleaseDate()));
				json.put("curRlsDate", sdf.format(sec.getCurrentReleaseDate()));
				json.put("cvrfUrl", sec.getCvrfURL());
				
				listaJson.put(json);
			}
			
			clientOutput.write(listaJson.toString().concat("\r\n").getBytes());
			
			clientOutput.flush();
			in.close();
			clientOutput.close();
		}
	}
}
