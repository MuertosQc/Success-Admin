
public class ProgrammeCours {
	private int IdProgramme;
	private int IdCours;
	private int Ordre;
	
	public ProgrammeCours(int IdProgramme, int IdCours, int Ordre) {
		this.IdProgramme = IdProgramme;
		this.IdCours = IdCours;
		this.Ordre = Ordre;
	}
	
	public int getIdProgramme() {
		return IdProgramme;
	}
	
	public int getIdCours() {
		return IdCours;
	}
	
	public int getOrdre() {
		return Ordre;
	}
}
