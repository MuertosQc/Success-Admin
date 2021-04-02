

public class Enseignant {
	private int ID;
	private String NomUtilisateur;
	private String Prenom;
	private String Nom;
	private String FullName;

	
	public Enseignant(int ID, String NomUtilisateur, String Prenom, String Nom) {
		this.ID = ID;
		this.NomUtilisateur = NomUtilisateur;
		this.Prenom = Prenom;
		this.Nom = Nom;
		this.FullName = Prenom + " " + Nom;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getNomUtilisateur() {
		return NomUtilisateur;
	}
	
	public String getFullName() {
		return FullName;
	}
	
	public String getPrenom() {
		return Prenom;
	}
	
	public String getNom() {
		return Nom;
	}
}
