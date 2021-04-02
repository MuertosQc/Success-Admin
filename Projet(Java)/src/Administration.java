
public class Administration {
	private int ID;
	private String NomUtilisateur;
	private String Prenom;
	private String Nom;
	private String Telephone;
	private String Email;
	private String AdresseDeMaison;
	private String Ville;
	private String CodePostal;
	private String Province;
	private String Poste;
	private String FullAdresse;
	
	public Administration(int ID, String NomUtilisateur, String Prenom,
			String Nom, String Telephone, String Email, String AdresseDeMaison, String Ville, String CodePostal, String Province, String Poste) {
		this.ID =ID;
		this.NomUtilisateur = NomUtilisateur; 
		this.Prenom = Prenom;
		this.Nom = Nom;
		this.Telephone = Telephone;
		this.Email = Email;
		this.Poste = Poste;
		this.AdresseDeMaison = AdresseDeMaison; 
		this.Ville = Ville;
		this.CodePostal = CodePostal;
		this.Province = Province;
		this.FullAdresse = AdresseDeMaison + ", " + Ville + " | " + CodePostal; 
	}
	
	public String getAdresseDeMaison() {
		return AdresseDeMaison;
	}
	
	public String getPrenom() {
		return Prenom;
	}
	
	public String getNom() {
		return Nom;
	}
	
	public String getVille() {
		return Ville;
	}
	
	public String getCodePostal() {
		return CodePostal;
	}
	
	public String getProvince() {
		return Province;
	}
	
	public String getAdressDeMaison() {
		return AdresseDeMaison;
	}
	
	public String getFullAdresse() {
		return FullAdresse;
	}
	
	public String getPoste() {
		return Poste;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getNomUtilisateur() {
		return NomUtilisateur;
	}
	
	public String getFullName() {
		return Prenom + " " + Nom;
	}
	
	public String getTelephone() {
		return Telephone;
	}
	
	public String getEmail() {
		return Email;
	}
}
