package estrutura;

import java.io.FileInputStream;
import java.io.IOException;

import database.Database;
import log.Log;
import rest.RepetidorRest;
import servidorHttp.ServidorHTTP;
import entidades.SecurityUpdate;

public class Main
{
	public static void main(String[] args) throws IOException
	{

		// Inicializar propriedades
		FileInputStream file = new FileInputStream("./propriedades/configs.properties");
		Parametros.getProps().load(file);
		
		// Inicializar Log
		Log log = new Log();
		Parametros.setLog(log);
		
		// Verifica se existe a tabela no banco, se não houver, tenta criar
		if(!Database.existeTabela())
		{
			Database.criaTabela();
		}
		
		// Inicializar Hashmap de controle
		SecurityUpdate.inicializaHashMapControle();
		
		// Inicializar repetidor de consumo do REST
		RepetidorRest.inicializaRepetidor();
		
		// Inicializar servidor HTTP
		ServidorHTTP.inicializa();
		
	}
}
