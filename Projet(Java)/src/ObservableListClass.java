import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class ObservableListClass {
	Fonction f;
	public ObservableListClass(Fonction f) {
		this.f = f;
	}
	//Oblist utilis� pour les listview pour assigner des programmes et des cours 
	@FXML public ObservableList<String> obListDroit = FXCollections.observableArrayList();
	@FXML public ObservableList<String> obListGauche = FXCollections.observableArrayList();
	
	// ObservableList qui r�cup�re tout les objets Etudiant
	public ObservableList<Etudiant> obListEtudiant = FXCollections.observableArrayList();
	
	// ObservableList qui r�cup�re tout les objets Enseignant
	public ObservableList<Administration> obListEnseignant = FXCollections.observableArrayList();
	// ObservableList qui r�cup�re tout les objets Cours
	public ObservableList<Cour> obListCours = FXCollections.observableArrayList();
	// ObservableList qui r�cup�re tout les objets Programme
	public ObservableList<Programme> obListProgramme = FXCollections.observableArrayList();
	
	// ObservableList qui r�cup�re tout les programme pour les afficher dans un
	// combobox
	public ObservableList<String> ObListCbProgramme = FXCollections.observableArrayList();
	
	// Choisir le langage qu'un �tudiant parle
	public ObservableList<String> ObListCbLangue = FXCollections.observableArrayList();
	
	public ObservableList<Frais> ObListFrais = FXCollections.observableArrayList();
	public ObservableList<Etudiant> obListNomEtudiantFrais = FXCollections.observableArrayList();
	public ObservableList<String> ObListEtat = FXCollections.observableArrayList();
	public ObservableList<Etudiant> obListNomEtudiantResultat = FXCollections.observableArrayList();
	public ObservableList<EtudiantCour> obListCourResultat = FXCollections.observableArrayList();
	public ObservableList<String> obListPoste = FXCollections.observableArrayList();
	public ObservableList<Examen> obListExamen = FXCollections.observableArrayList();
	public ObservableList<Notification> obListNotif = FXCollections.observableArrayList();
	public ObservableList<Logs> obListNotifDetails = FXCollections.observableArrayList();
	public ObservableList<Logs> obListLogs = FXCollections.observableArrayList();
	public ObservableList<Email> obListEmail = FXCollections.observableArrayList();
	
}
