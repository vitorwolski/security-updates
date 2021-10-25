package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import entidades.SecurityUpdate;
import estrutura.Parametros;

public class Database
{
	public static boolean inicializaConexaoBanco()
	{
		try
		{
			// Inicializa parâmetros de banco
			String url = Parametros.getProps().getProperty("jdbc");
			Properties propsBanco = new Properties();
			propsBanco.setProperty("user", Parametros.getProps().getProperty("user"));
			propsBanco.setProperty("password", Parametros.getProps().getProperty("password"));
			Parametros.setConnBanco(DriverManager.getConnection(url, propsBanco));
			
			if(Parametros.getConnBanco().isValid(5000))
			{
				Parametros.getLog().escreveLog("Database", "inicializaConexaoBanco()", "Conectado com sucesso ao banco de dados.");
			}
			else
			{
				Parametros.getLog().escreveLog("Database", "inicializaConexaoBanco()", "Falha ao tentar se conectar com o banco de dados.");
			}
		}
		catch(SQLException e)
		{
			Parametros.getLog().escreveLog("Database", "inicializaConexaoBanco()", "Erro ao tentar inicializar a conexão com o banco de dados.");
			return false;
		}
		
		return true;
	}
	
	public static boolean isConexaoAberta()
	{
		try
		{
			return !Parametros.getConnBanco().isClosed();
		}
		catch(SQLException s)
		{
			Parametros.getLog().escreveLog("Database", "isConexaoAberta()", "Erro ao tentar verificar se a conexão com o banco de dados está aberta.");
			return false;
		}
	}
	
	public static boolean encerraConexaoBanco()
	{
		try
		{
			if(!Parametros.getConnBanco().isClosed())
			{
				Parametros.getConnBanco().close();
			}
		}
		catch(SQLException s)
		{
			Parametros.getLog().escreveLog("Database", "encerraConexaoBanco()", "Erro ao tentar encerrar a conexão com o banco de dados.");
		}
		
		return true;
	}
	
	public static String getNull(String linha)
	{
		if(linha != null && !"".equals(linha.trim()))
		{
			return "'" + linha + "'";
		}
		
		return "NULL";
	}
	
	public static boolean inicializaHashmap(Connection conn, HashMap<String, String> controle)
	{
		StringBuilder query = new StringBuilder();
		ResultSet rs = null;
		
		try
		{
			query.append("SELECT ID\n");
			query.append("FROM MS_SEC_UPD\n");
			
			Statement stmt = Parametros.getConnBanco().createStatement();
			rs = stmt.executeQuery(query.toString());
			
			while(rs.next())
			{
				controle.put(rs.getString("ID"), "");
			}
		}
		catch(SQLException s)
		{
			Parametros.getLog().escreveLog("Database", "inicializaHashMap()", "Erro ao tentar inicializar o HashMap de controle com os dados gravados no banco de dados.");
		}
		finally
		{
			try{rs.close();}catch(SQLException s1){};
		}
		
		return true;
	}
	
	public static ArrayList<SecurityUpdate> carregaListaSecurityUpdates()
	{
		StringBuilder query = new StringBuilder();
		ArrayList<SecurityUpdate> retorno = new ArrayList<SecurityUpdate>();
		ResultSet rs = null;
		
		try
		{
			query.append("SELECT ID, ALIAS, DOCUMENT_TITLE, SEVERITY,\n");
			query.append("INI_RLS_DATE, CUR_RLS_DATE, CVRF_URL\n");
			query.append("FROM MS_SEC_UPD\n");
			
			Statement stmt = Parametros.getConnBanco().createStatement();
			rs = stmt.executeQuery(query.toString());
			
			while(rs.next())
			{
				SecurityUpdate sec = new SecurityUpdate();
				
				sec.setId(rs.getString("ID"));
				sec.setAlias(rs.getString("ALIAS"));
				sec.setDocumentTitle(rs.getString("DOCUMENT_TITLE"));
				sec.setSeverity(!"null".equalsIgnoreCase(rs.getString("SEVERITY")) ? rs.getString("SEVERITY") : "");
				sec.setInitialReleaseDate(rs.getDate("INI_RLS_DATE"));
				sec.setCurrentReleaseDate(rs.getDate("CUR_RLS_DATE"));
				sec.setCvrfURL(rs.getString("CVRF_URL"));
				
				retorno.add(sec);
			}
		}
		catch(SQLException s)
		{
			Parametros.getLog().escreveLog("Database", "carregaListaSecurityUpdates()", "Erro ao tentar carregar a lista dos Security Updates gravados no banco de dados.");
		}
		finally
		{
			try{rs.close();}catch(SQLException s){}
		}
		
		
		return retorno;
	}
	
	public static boolean gravaSecurityUpdate(SecurityUpdate sec)
	{
		StringBuilder query = new StringBuilder();
		
		try
		{
			query.append("INSERT INTO MS_SEC_UPD (ID, ALIAS, DOCUMENT_TITLE, SEVERITY,\n");
			query.append("INI_RLS_DATE, CUR_RLS_DATE, CVRF_URL)\n");
			query.append("VALUES (" + getNull(sec.getId()) + ", " + getNull(sec.getAlias()) + ", " + getNull(sec.getDocumentTitle()) + ", " + getNull(sec.getSeverity()) + ",\n");
			query.append(getNull(sec.getInitialReleaseDate().toString()) + ", " + getNull(sec.getCurrentReleaseDate().toString()) + ", " + getNull(sec.getCvrfURL()) + ")\n");
			
			Statement stmt = Parametros.getConnBanco().createStatement();
			stmt.executeUpdate(query.toString());
			
			Parametros.getControleGravadosBanco().put(sec.getId(), "");
		}
		catch(SQLException s)
		{
			Parametros.getLog().escreveLog("Database", "gravaSecurityUpdate()", "Erro ao tentar gravar o Security Update no banco de dados.");
			return false;
		}
		
		return true;
	}
	
	public static boolean existeTabela()
	{
		inicializaConexaoBanco();
		
		StringBuilder query = new StringBuilder();
		
		ResultSet rs = null;
		
		try
		{
			query.append("SELECT DISTINCT 1 FROM MS_SEC_UPD\n");
			
			Statement stmt = Parametros.getConnBanco().createStatement();
			rs = stmt.executeQuery(query.toString());
		}
		catch(SQLException s)
		{
			Parametros.getLog().escreveLog("Database", "existeTabela()", "Erro ao tentar verificar se existe a tabela no banco de dados.");
			return false;
		}
		finally
		{
			try{rs.close();}catch(SQLException s){}
			encerraConexaoBanco();
		}
		
		return true;
	}
	
	public static boolean criaTabela()
	{
		inicializaConexaoBanco();
		
		StringBuilder query = new StringBuilder();
		
		try
		{
			query.append("CREATE TABLE MS_SEC_UPD\n");
			query.append("(\n");
			query.append("   ID VARCHAR(30) PRIMARY KEY,\n");
			query.append("   ALIAS VARCHAR(30) NULL,\n");
			query.append("   DOCUMENT_TITLE VARCHAR(255) NULL,\n");
			query.append("   SEVERITY VARCHAR(255) NULL,\n");
			query.append("   INI_RLS_DATE TIMESTAMP NULL,\n");
			query.append("   CUR_RLS_DATE TIMESTAMP NULL,\n");
			query.append("   CVRF_URL VARCHAR(255)\n");
			query.append(")\n");
			
			Statement stmt = Parametros.getConnBanco().createStatement();
			stmt.executeUpdate(query.toString());
		}
		catch(SQLException s)
		{
			Parametros.getLog().escreveLog("Database", "criaTabela()", "Erro ao tentar criar a tabela no banco de dados.");
			return false;
		}
		finally
		{
			encerraConexaoBanco();
		}
		
		return true;
	}
}
