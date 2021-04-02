
public class Notification {
	private int IdNotification;
	private String Auteur; 
	private int IdLog;
	private int IdAdmin;
	private int Etat;
	private String Type;
	private String Date;
	private String Table;
	
	public Notification(int IdNotification, String Auteur, int IdLog, int IdAdmin, int Etat, String Type, String Date, String Table) {
		this.IdNotification = IdNotification;
		this.Auteur = Auteur;
		this.IdLog = IdLog;
		this.IdAdmin = IdAdmin;
		this.Etat = Etat;
		this.Type = Type;
		this.Date = Date;
		this.Table = Table;
	}
	
	public int getIdNotification() {
		return IdNotification;
	}
	
	public String getAuteur() {
		return Auteur;
	}
	
	public int getIdLog() {
		return IdLog;
	}
	
	public int getIdAdmin() {
		return IdAdmin;
	}
	
	public int getEtat() {
		return Etat;
	}
	
	public String getType() {
		return Type;
	}
	
	public String getDate() {
		return Date;
	}
	
	public String getTable() {
		return Table;
	}
}
