
public class Logs {
	private String Createur;
	private String AncienneValeur;
	private String NouvelleValeur;
	private String Table1;
	private String Type1;
	private String Date1;
	
	public Logs(String Createur, String AncienneValeur, String NouvelleValeur, String Table1, String Type1, String Date1) {
		this.Createur = Createur;
		this.AncienneValeur = AncienneValeur;
		this.NouvelleValeur = NouvelleValeur;
		this.Table1 = Table1;
		this.Type1 = Type1;
		this.Date1 = Date1;
	}
	
	public String getCreateur() {
		return Createur;
	}
	
	public String getAncienneValeur() {
		return AncienneValeur;
	}
	
	public String getNouvelleValeur() {
		return NouvelleValeur;
	}
	
	public String getTable1() {
		return Table1;
	}
	
	public String getType1() {
		return Type1;
	}
	
	public String getDate1() {
		return Date1;
	}
}
