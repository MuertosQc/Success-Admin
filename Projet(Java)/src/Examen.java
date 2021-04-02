
public class Examen {
	private int IdProjet;
	private int IdEnseignant;
	private int IdCours;
	private String Titre;
	private String Description;
	private int Ponctuation;
	private String NomComplet;
	private String Prenom;
	private String Nom;
	private String TitreEvaluation;
	private int Resultat;
	
	public Examen(int IdProjet, int IdEnseignant, int IdCours, String Titre, String Description, int Ponctuation, String Prenom, String Nom, String TitreEvaluation, int Resultat) {
		this.IdProjet = IdProjet;
		this.IdEnseignant = IdEnseignant;
		this.IdCours = IdCours;
		this.Titre = Titre;
		this.Description = Description;
		this.Ponctuation = Ponctuation;
		this.NomComplet = Prenom + " " + Nom;
		this.TitreEvaluation = TitreEvaluation;
		this.Resultat = Resultat;
	}
	
	public int getResultat() {
		return Resultat;
	}
	
	public String getTitreEvaluation() {
		return TitreEvaluation;
	}
	
	public String getNomComplet() {
		return NomComplet;
	}
	
	public int getIdProjet() {
		return IdProjet;
	}
	
	public int getIdEnseignant() {
		return IdEnseignant;
	}
	
	public int getIdCours() {
		return IdCours;
	}
	
	public String getTitre() {
		return Titre;
	}
	
	public String getDescription() {
		return Description;
	}
	
	public int getPonctuation() {
		return Ponctuation;
	}
	
	public String getPrenom() {
		return Prenom;
	}
	
	public String getNom() {
		return Nom;
	}
}
