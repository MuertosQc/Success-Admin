
public class Frais {
	private int IdFrais;
	private int IdEtudiant;
	private int Cout;
	private String Type;
	private String NomComplet;
	private String Etat;
	
	public Frais(int IdFrais, int IdEtudiant, int Cout, String Type, String NomComplet, String Etat) {
		this.IdFrais = IdFrais;
		this.IdEtudiant = IdEtudiant;
		this.Cout = Cout;
		this.Type = Type;
		this.NomComplet = NomComplet;
		this.Etat = Etat;
	}
	
	public int getIdFrais(){
		return IdFrais;
	}
	
	public int getIdEtudiant() {
		return IdEtudiant;
	}
	
	public int getCout() {
		return Cout;
	}
	
	public String getType() {
		return Type;
	}
	
	public String getNomComplet() {
		return NomComplet;
	}
	
	public String getEtat() {
		return Etat;
	}
	
}
