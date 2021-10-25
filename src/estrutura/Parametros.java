package estrutura;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Properties;

import log.Log;

public class Parametros
{
	private static Connection connBanco;
	private static HashMap<String, String> controleGravadosBanco = new HashMap<String, String>();
	private static String urlBanco;
	private static Properties props = new Properties();
	private static Log log;
	
	public static HashMap<String, String> getControleGravadosBanco() {
		return controleGravadosBanco;
	}

	public static void setControleGravadosBanco(HashMap<String, String> controleGravadosBanco) {
		Parametros.controleGravadosBanco = controleGravadosBanco;
	}

	public static String getUrlBanco() {
		return urlBanco;
	}

	public static void setUrlBanco(String urlBanco) {
		Parametros.urlBanco = urlBanco;
	}

	public static Properties getProps() {
		return props;
	}

	public static void setProps(Properties props) {
		Parametros.props = props;
	}
	
	public static Log getLog() {
		return log;
	}

	public static void setLog(Log log) {
		Parametros.log = log;
	}

	public static Connection getConnBanco() {
		return connBanco;
	}
	
	public static void setConnBanco(Connection connBanco) {
		Parametros.connBanco = connBanco;
	}
}
