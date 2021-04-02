
public class Etudiant {
	private int ID;
	private String Utilisateur;
	private String Prenom;
	private String Nom;
	private String Telephone;
	private String Email;
	private String AdresseDeMaison;
	private String Ville;
	private String Province;
	private String CodePostal;
	private String FullName;
	private String FullAdresse;
	private int IdProgramme;
	private String Langue;
	
	public Etudiant(int ID, String Utilisateur, String Prenom, String Nom, String Telephone,
			String Email, String AdresseDeMaison, String Ville, String Province, String CodePostal, int IdProgramme, String Langue) {
		this.ID = ID;
		this.Utilisateur = Utilisateur;
		this.FullName = Prenom + " " + Nom;
		this.FullAdresse = AdresseDeMaison + ", " + Ville + ", " + CodePostal;
		this.Prenom = Prenom;
		this.Nom = Nom;
		this.Telephone = Telephone;
		this.Email = Email;
		this.AdresseDeMaison = AdresseDeMaison;
		this.Ville = Ville;
		this.Province = Province;
		this.CodePostal = CodePostal;
		this.IdProgramme = IdProgramme;
		this.Langue = Langue;
	}
	
	public Etudiant(int ID, String Prenom, String Nom, String Telephone, String Email) {
		this.ID = ID;
		this.FullName = Prenom + " " + Nom;
		this.Telephone = Telephone;
		this.Email = Email;
	}
	
	public int getProgramme() {
		return IdProgramme;
	}
	
	public String getUtilisateur() {
		return Utilisateur;
	}
	
	public int getID() {
		return ID;
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
	
	public String getTelephone() {
		return Telephone;
	}
	
	public String getEmail() {
		return Email;
	}
	
	public String getFullAdresse() {
		return FullAdresse;
	}
	
	public String getAdresse() {
		return AdresseDeMaison;
	}
	
	public String getVille() {
		return Ville;
	}
	
	public String getProvince() {
		return Province;
	}
	
	public String getCodePostal() {
		return CodePostal;
	}
	
	public String getLangue() {
		return Langue;
	}
}
