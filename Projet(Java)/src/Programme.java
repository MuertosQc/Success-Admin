
public class Programme {
	private int ID;
	private String Titre;
	private String TitreAnglais;
	private int Temps;
	private int Cout;
	public int Etat;
	
	public Programme(int ID, String Titre, String TitreAnglais, int Temps, int Cout, int Etat) {
		this.ID = ID;
		this.Titre = Titre;
		this.TitreAnglais = TitreAnglais;
		this.Temps = Temps;
		this.Cout = Cout;
		this.Etat = Etat;
	}
	
	public String getTitreAnglais() {
		return TitreAnglais;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getTitre() {
		return Titre;
	}
	
	public int getTemps() {
		return Temps;
	}
	
	public int getEtat() {
		return Etat;
	}
	
	public int getCout() {
		return Cout;
	}
}
