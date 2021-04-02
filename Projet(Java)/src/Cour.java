
public class Cour {
	AccueilController AC;
	private int ID;
	private String Titre;
	private String Description;
	private String TitreAnglais;
	private String DescriptionAnglais;
	private String Temps;
	private double Cout;
	private int Credit;
	private int Etat;
	
	public Cour(int ID, String Titre, String Description, String TitreAnglais, String DescriptionAnglais, String Temps, double Cout, int Credit, int Etat) {
		this.ID =ID;
		this.Titre = Titre;
		this.Description = Description;
		this.TitreAnglais = TitreAnglais;
		this.DescriptionAnglais = DescriptionAnglais;
		this.Temps = Temps;
		this.Cout = Cout;
		this.Credit = Credit;
		this.Etat = Etat;
	}
	
	public Cour(int ID, String Titre) {
		this.ID = ID;
		this.Titre = Titre;
	}
	
	public String ToString() {
		return ID + " " + Titre;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getTitre() {
		return Titre;
	}
	
	public String getDescription() {
		return Description;
	}
	
	public String getTitreAnglais() {
		return TitreAnglais;
	}
	
	public String getDescriptionAnglais() {
		return DescriptionAnglais;
	}
	
	public String getTemps() {
		return Temps;
	}
	
	public double getCout() {
		return Cout;
	}
	
	public int getCredit() {
		return Credit;
	}
	
	public int getEtat() {
		return Etat;
	}
	
}
