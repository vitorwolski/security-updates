package rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import database.Database;
import entidades.SecurityUpdate;
import estrutura.Parametros;

public class RestSecurityUpdates implements Job
{
	private static HttpURLConnection conn;
	StringBuilder response = new StringBuilder();
	ArrayList<SecurityUpdate> lista = new ArrayList<SecurityUpdate>();
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		BufferedReader reader;
		String line;
		
		try
		{
			// Abrir a conexão com o Rest
			URL url = new URL("https://api.msrc.microsoft.com/cvrf/v2.0/updates");
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			
			int status = conn.getResponseCode();
			
			if(status >= 300)
			{
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while((line = reader.readLine()) != null)
				{
					response.append(line+"\n");
				}
				reader.close();
				Parametros.getLog().escreveLog("RestSecurityUpdates", "execute()", "Falha ao tentar se comunicar com o rest da Microsoft Security Updates.");
			}
			else
			{
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((line = reader.readLine()) != null)
				{
					response.append(line+"\n");
				}
				reader.close();
				Parametros.getLog().escreveLog("RestSecurityUpdates", "execute()", "Sucesso ao tentar se comunicar com o rest da Microsoft Security Updates.");
			}
			
			// Tradução do JSON para o ArrayList
			JSONObject json = new JSONObject(response.toString());
			JSONArray array = (JSONArray) json.get("value");
			
			for(int i = 0; i < array.length(); i++)
			{
				json = (JSONObject) array.get(i);
				
				// Verifico no HashMap de controle se o Security Update já está cadastrado, se estiver, não preciso ir ao banco para verificar
				if(!Parametros.getControleGravadosBanco().containsKey(json.get("ID")))
				{
					// Abrir a conexão apenas uma vez para todos os Security Updates que forem ser gravados no banco nesse momento
					if(!Database.isConexaoAberta())
					{
						Database.inicializaConexaoBanco();
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
					SecurityUpdate sec = new SecurityUpdate();
					
					sec.setId(json.get("ID") != null ? String.valueOf(json.get("ID")) : null);
					sec.setAlias(json.get("Alias") != null ? String.valueOf(json.get("Alias")) : null);
					sec.setDocumentTitle(json.get("DocumentTitle") != null ? String.valueOf(json.get("DocumentTitle")) : null);
					sec.setSeverity(json.get("Severity") != null ? String.valueOf(json.get("Severity")) : null);
					sec.setInitialReleaseDate(json.get("InitialReleaseDate") != null ? sdf.parse(String.valueOf(json.get("InitialReleaseDate")).replace('T', ' ')) : null);
					sec.setCurrentReleaseDate(json.get("CurrentReleaseDate") != null ? sdf.parse(String.valueOf(json.get("CurrentReleaseDate")).replace('T', ' ')) : null);
					sec.setCvrfURL(json.get("CvrfUrl") != null ? String.valueOf(json.get("CvrfUrl")) : null);
					
					sec.gravaSecurityUpdate();
				}
			}
			
			// Fechar a conexão de banco se a mesma foi aberta
			if(Database.isConexaoAberta())
			{
				Database.encerraConexaoBanco();
			}
		}
		catch(Exception e)
		{
			Parametros.getLog().escreveLog("RestSecurityUpdates", "execute()", "Erro ao tentar consumir o rest do Microsoft Security Updates.");
		}
		finally
		{
			// Fechar a conexão com o Rest
			conn.disconnect();
		}
	}
	
}
