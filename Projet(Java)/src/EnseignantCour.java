

public class EnseignantCour {
	private int IdEnseignant;
	private int IdCour;
	
	public EnseignantCour(int IdEnseignant, int IdCour) {
		this.IdEnseignant = IdEnseignant;
		this.IdCour = IdCour;
	}
	
	public int getIdEnseignant() {
		return IdEnseignant;
	}
	
	public int getIdCour() {
		return IdCour;
	}
}
