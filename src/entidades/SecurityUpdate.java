package entidades;

import java.util.*;

import database.Database;
import estrutura.Parametros;

public class SecurityUpdate
{
	private String id;
	private String alias;
	private String documentTitle;
	private String severity;
	private Date initialReleaseDate;
	private Date currentReleaseDate;
	private String cvrfURL;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public Date getInitialReleaseDate() {
		return initialReleaseDate;
	}
	public void setInitialReleaseDate(Date initialReleaseDate) {
		this.initialReleaseDate = initialReleaseDate;
	}
	public Date getCurrentReleaseDate() {
		return currentReleaseDate;
	}
	public void setCurrentReleaseDate(Date currentReleaseDate) {
		this.currentReleaseDate = currentReleaseDate;
	}
	public String getCvrfURL() {
		return cvrfURL;
	}
	public void setCvrfURL(String cvrfURL) {
		this.cvrfURL = cvrfURL;
	}
	
	public static ArrayList<SecurityUpdate> carregaListaSecurityUpdates()
	{
		Database.inicializaConexaoBanco();
		ArrayList<SecurityUpdate> retorno = Database.carregaListaSecurityUpdates();
		Database.encerraConexaoBanco();
		return retorno;
	}
	
	public boolean gravaSecurityUpdate()
	{
		return Database.gravaSecurityUpdate(this);
	}
	
	public static boolean inicializaHashMapControle()
	{
		Database.inicializaConexaoBanco();
		Database.inicializaHashmap(Parametros.getConnBanco(), Parametros.getControleGravadosBanco());
		Database.encerraConexaoBanco();
		return true;
	}
}
