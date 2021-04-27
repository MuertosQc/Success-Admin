
public class Email {
	private int IdMessage;
	private String NomComplet;
	private int IdEnvoyeur;
	private int IdReceveur;
	private String Sujet;
	private String Message;
	private String Date;
	private int Etat;
	private int IdReponse;
	
	
	public Email(int IdMessage, String NomComplet, int IdEnvoyeur, int IdReceveur, String Sujet, String Message, String Date, int Etat, int IdReponse) {
		this.IdMessage = IdMessage;
		this.NomComplet = NomComplet;
		this.IdEnvoyeur = IdEnvoyeur;
		this.IdReceveur = IdReceveur;
		this.Sujet = Sujet;
		this.Message = Message;
		this.Date = Date;
		this.Etat = Etat;
		this.IdReponse = IdReponse;
	}
	public Email(String NomComplet, int IdEnvoyeur) {
		this.NomComplet = NomComplet;
		this.IdEnvoyeur = IdEnvoyeur;
	}
	
	public int getIdMessage() {
		return IdMessage;
	}
	
	public String getNomComplet() {
		return NomComplet;
	}
	
	public int getIdEnvoyeur() {
		return IdEnvoyeur;
	}
	
	public int getIdReceveur() {
		return IdReceveur;
	}
	
	public String getSujet() {
		return Sujet;
	}
	
	public String getMessage() {
		return Message;
	}
	
	public String getDate() {
		return Date;
	}
	
	public int getEtat() {
		return Etat;
	}
	
	public int getIdReponse() {
		return IdReponse;
	}
}
