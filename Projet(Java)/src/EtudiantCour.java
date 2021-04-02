
public class EtudiantCour {
	private int IdEtudiant;
	private int IdCour;
	private int Resultat;
	private String Titre;
	
	public EtudiantCour(int IdEtudiant, int IdCour, int Resultat, String Titre) {
		this.IdEtudiant = IdEtudiant;
		this.IdCour = IdCour;
		this.Resultat = Resultat;
		this.Titre = Titre;
	}
	
	public int getIdEtudiant() {
		return IdEtudiant;
	}
	
	public int getIdCour() {
		return IdCour;
	}
	
	public int getResultat() {
		return Resultat;
	}
	
	public String getTitre() {
		return Titre;
	}
}
