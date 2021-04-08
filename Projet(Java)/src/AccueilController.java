import java.io.File;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AccueilController {
	Administration administration;
	Etudiant etudiant;
	
	Fonction f = new Fonction(this);
	@FXML public int EtatNotif = 1;

	@FXML public String VariableGlobal = ""; //Variable qui sert a savoir qu'elle fonction on utilise si on a plusieurs fonction differente sur un bouton
	
	@FXML public Label LblMsgBienvenue;
	@FXML public Pane PaneBienvenue;
	@FXML public String NomAdministrateur = "";
	@FXML public String TelAdmin = "";
	@FXML public String EmailAdmin = "";
    @FXML private Button BtnAcueil;
    @FXML private Button BtnCour;
    @FXML public Label LblNom;
    @FXML public Label LblCompteurEtudiants;
    @FXML public Label LblCompteurCours;
    @FXML public Label LblCompteurEnseignants;
    @FXML public Label LblMsgErreurEtudiant;    
    @FXML public Label lblTitreInscription;
    
    //Section notification
  
    @FXML public Button BtnNotifLu;
    @FXML public Button BtnNotifNonLu;
    @FXML public Button BtnNotifSup;
    @FXML public Button BtnNotifAfficher;
    @FXML public TableView<Notification> TableViewNotif;
    @FXML public TableColumn<Notification,String> TableColumnNotifAuteur;
    @FXML public TableColumn<Notification,String> TableColumnNotifType;
    @FXML public TableColumn<Notification,String> TableColumnNotifDate;
    @FXML public TableColumn<Notification,String> TableColumnNotifTable;
    @FXML public Pane PaneNotification;
    @FXML public ImageView ImgCloseNotif;
    @FXML public Circle RondNotif;
    @FXML public ImageView ImgSetting;
    @FXML public Pane PaneSettingNotif;
    @FXML public Label LblFermerSetting;
    @FXML public RadioButton CheckBoxNotifNL;
    @FXML public RadioButton CheckBoxNotifL;
    @FXML public Label LblMsgNotif;
    
    //Section notification detail
    @FXML public Button BtnPrecedentNotif;
    @FXML public Pane PaneNotifDetail;
    @FXML public TableColumn<Logs, String> TcCreateurNotifDetail;
    @FXML public TableColumn<Logs, String> TcAvNotifDetail;
    @FXML public TableColumn<Logs, String> TcNvNotifDetail;
    @FXML public TableColumn<Logs, String> TcTableNotifDetail;
    @FXML public TableColumn<Logs, String> TcTypeNotifDetail;
    @FXML public TableColumn<Logs, String> TcDateNotifDetail;
    @FXML public TableView<Logs> TableViewNotifDetail;
    @FXML public CheckBox CheckBoxNotifDetail;
    
    
    //Section commentaire
    @FXML public Pane PaneCommentaire;
    @FXML public Label TitreCommentaire;
    @FXML public TextArea TbCommentaire;
    @FXML public Button BtnSupprimerComEtudiant;
    @FXML public Button BtnAnnulerComEtudiant;
    
    @FXML public Button BtnProgramme;
    @FXML public Button BtnSupCours;
    @FXML public Button BtnAjouterCours;
    @FXML public Button BtnModifierCours;
    @FXML public Pane PaneGrandCour;    
    @FXML public TableView<Cour> TableViewCours;
    @FXML public TableColumn<Cour, String>  TcTitreCours;
    @FXML public TableColumn<Cour, String>  TcTempsCours;
    @FXML public TableColumn<Cour, String>  TcCoutCours;
    @FXML public TableColumn<Cour, String>  TcCreditCours; 
    @FXML public Pane PaneAjoutCours;
    @FXML public TextField TbTitreCours;
    @FXML public TextField TbCoutCours;
    @FXML public TextField TbTempsCours;
    @FXML public TextField TbCreditCours;
    @FXML public Button BtnCancelCours;
    @FXML public Button BtnAjoutCours; 
    
    @FXML public TableView<Examen> TableViewNoteExam;
    @FXML public TableColumn<Examen, String> TcCourNoteExam;
    @FXML public TableColumn<Examen, String> TcEvalNoteExam;
    @FXML public TableColumn<Examen, Integer> TcResultNoteExam;
    @FXML public Pane PaneAjouterNote; //Pane pour ajouter la note 
    @FXML public TextField TbAjouterNote; //TextField pour ajouter la note
    @FXML public Button BtnAjoutNoteExam;
    @FXML public Button BtnPrecedentExam;
    @FXML public Pane PaneAjouterNoteExam;
    @FXML public Label LblNomNoteExam; //Nom de l'étudiant qui sera afficher en titre à l'ajout de la note d'un évaluation
    @FXML public Label LblTitreEvalNote; //Le titre de l'examen choisi
    @FXML public Label LblNoteAjouter; //Label qui affiche un message "Note ajouté"
    @FXML public Button BtnAfficherEvalutation;
    @FXML public Label LblMsgErreurExam;
    @FXML public Button BtnAfficherExamenCours;
    @FXML public Button BtnContinueExam; //Bouton message milieu écran
    @FXML public Pane PaneModificationExamen;
    @FXML public TextField TbModifTitreExam;
    @FXML public TextField TbModifDescriptionExam;
    @FXML public TextField TbModifPonctuationExam;
    @FXML public Button BtnModifExam;
    @FXML public Button BtnAjouterExam;
    @FXML public Button BtnModifierExam;
    @FXML public Pane PaneModifExam;
    @FXML public Button BtnSupprimerExam;
    @FXML public Pane PaneAfficherExamen;
    @FXML public Button BtnAfficherExamen;
    @FXML public Pane PaneAjoutExam;
    @FXML public Label LblTitreCoursExam;
    @FXML public TextField TbTitreExamen; //Ajouter le titre
    @FXML public TextField TbDescriptionExamen; //Ajouter la description
    @FXML public TextField TbPonctuationExamen; //Ajouter la ponctuation
    @FXML public Button BtnAjouterExamen;
    @FXML public Button BtnRetourExamen;
    @FXML public TableView<Examen> TableViewExamen;
    @FXML public TableColumn<Examen, String> TableColumnTitreExam;
    @FXML public TableColumn<Examen, String> TableColumDescExam;
    @FXML public TableColumn<Examen, String> TableColumnPoncExam;
    @FXML public TableColumn<Examen, String> TableColumnProfExam;
    @FXML public Pane PaneTableViewExam;
    
  
    @FXML public Pane PaneCour;
    @FXML public Pane PaneEnseignant;
    @FXML public Pane PaneAccueil;
    @FXML public Button BtnAssignerProgramme;
    @FXML public Button BtnInscription;
    @FXML public ComboBox<String> ComboBoxProgramme;
    @FXML public CheckBox CbLangueProgramme;
    //-----------------------------------------------------//
    //---ETUDIANT CONTROL---//
   
    @FXML public Button BtnModifierEtudiant;
    @FXML public Button BtnCancelEtudiant;
    @FXML public Button BtnSupEtudiant;
    @FXML public Button BtnModifEtudiant;
    @FXML public Button BtnAjouterEtudiantCour;
    @FXML public Button BtnProgEtuAjout;
    @FXML public Button BtnRetourEtuProg; //Retour en arrière Etudiant-prog
    @FXML public Button BtnRetourEtuCour;
    @FXML public Button BtnEtudiant;
    @FXML public Button BtnAjouterEtudiant; 
    @FXML public Button BtnAjoutEtudiant;
    
    @FXML public Pane PaneEtudiant;
    @FXML public Pane PaneAssigneCourEtudiant;
    @FXML public Pane PaneEtuProgAssigne;
    @FXML public Pane GrandePaneEtudiant;
    @FXML public Pane PaneInscriptionEtudiant;
    
    @FXML public Label LblNomEtudiant;
    @FXML public Label LblTelEtudiant;
    @FXML public Label LblEmailEtudiant;
    @FXML public Label LblProgrammeEtudiant;
    @FXML public Label LblNomEtudiantProg;   
    
    @FXML public ListView<String> ListViewProgEtuAttente; //Assigner un programme a un etudiant (liste de gauche)
    @FXML public ListView<String> ListViewProgEtuAssinger; //Assigner un programme a un etudiant (liste de droite)
    @FXML public ListView<String> ListViewEtudiantGauche;
    @FXML public ListView<String> ListViewEtudiantDroite;
    
    @FXML public ComboBox<String> CbLangueEtudiant; 

    @FXML public TableView<Etudiant> TableViewEtudiant;
    @FXML public TableColumn<Etudiant, String> TcUser; 
    @FXML public TableColumn<Etudiant, String> TcNomComplet;
    @FXML public TableColumn<Etudiant, String> TcTel;
    @FXML public TableColumn<Etudiant, String> TcEmail;
    @FXML public TableColumn<Etudiant, String> TcAdresse;
    @FXML public TableColumn<Etudiant, String> TcProvince;
    
    @FXML public TextField TbPrenom;
    @FXML public TextField TbNom;
    @FXML public TextField TbAdresse;
    @FXML public TextField TbVille;
    @FXML public TextField TbCodePostal;
    @FXML public TextField TbProvince;
    @FXML public TextField TbTel;    
    @FXML public TextField TbEmail;   
    @FXML public TextField TbUser;
   
    //----------------------------------//
   
    @FXML public Pane PaneMsg;
    @FXML public Button BtnContinueMsg;
    @FXML public Label LblMsg;
    @FXML public Label LblTitreMsg;
    @FXML public Pane PaneBlack;
    
    @FXML public Label LblCompteurProgramme;
    @FXML public Pane PaneProgramme;
    @FXML public TextField TbDescriptionCours;
    @FXML public TextField TbTitreAngCours;
    @FXML public TextField TbDescriptionAngCours;
    
    @FXML private CategoryAxis xAxis = new CategoryAxis();
    @FXML private NumberAxis yAxis = new NumberAxis();
    @FXML public XYChart.Data<String, Number> data =  new XYChart.Data<String, Number>();    
    @FXML public BarChart<String, Number> BarChart = new BarChart<>(xAxis, yAxis);
    
    @FXML public BarChart<String, Number> BarChartInactif = new BarChart<>(xAxis, yAxis);
    @FXML public XYChart.Data<String, Number> dataInactif =  new XYChart.Data<String, Number>(); 
    
    @FXML public CheckBox CheckBoxEtat;
    @FXML public Button BtnModifCours; 
    @FXML public CheckBox CbCoursActifs;
    @FXML public Label LblTitreCoursActifs;
    @FXML public Circle RondPage1;
    @FXML public Circle RondPage2;
    
    //---------------------------------------------------------------------------//
    //---ENSEIGNANT CONTROL---//
    @FXML public TableView<Administration> TableViewEnseignant;
   // @FXML public TableColumn<Administration, String>  TcUtilisateurEnseignant;
    @FXML public TableColumn<Administration, String>  TcNomCompletEnseignant;
    @FXML public TableColumn<Administration, String>  TcTelEnseignant;
    @FXML public TableColumn<Administration, String>  TcEmailEnseignant;
    @FXML public TableColumn<Administration, String>  TcAdresseEnseignant;
    @FXML public TableColumn<Administration, String>  TcProvinceEnseignant;
    @FXML public Button BtnModifierEnseignant;
    @FXML public Button BtnAjouterEnseignant;
    @FXML public Button BtnSupEnseignant;
    @FXML public Pane GrandePaneEnseignant;
    @FXML public Button BtnAssignerProgEns; //Boutton pour assigner un programme-enseignant
    @FXML public Label LblNomEnseignant;
    @FXML public Label LblEmailEnseignant;
    @FXML public Label LblTelEnseignant;
    @FXML public Pane PaneAssignerProgEns;
    @FXML public ListView<String> ListViewProgEnsGauche;
    @FXML public ListView<String> ListViewProgEnsDroite;
    @FXML public Button BtnSupCoursEns;
    @FXML public Button BtnAjoutCourEns;
    @FXML private Button BtnEnseignant;
    @FXML public Button BtnAjoutEnseignant;
    @FXML public Button BtnCancelEnseignant;
    @FXML public Button BtnModifEnseignant;
    
    //---------------------------------------------------------------------------//
    //---PROGRAMME CONTROL---//
    
    @FXML public TableView<Programme> TableViewProgramme;
    @FXML public TableColumn<Programme, String> TvCProgrammeTitre;
    @FXML public TableColumn<Programme, String> TvCProgrammeTemps;
    @FXML public TableColumn<Programme, String> TvCProgrammeCout;
    
    @FXML public Button BtnModifierProgramme;
    @FXML public Button BtnAjouterProgramme;
    @FXML public Button BtnSupProgramme;
    @FXML public Pane PanePrincipaleProgramme;
    
    @FXML public Pane PaneAjoutProgramme;
    @FXML public Button BtnCancelProgramme;
    @FXML public Button BtnAjoutProgramme;
    @FXML public TextField TbTitreProgrammeFr;
    @FXML public TextField TbTitreProgrammeEN;
    @FXML public CheckBox CbProgramme; //Cb pour ajouter le cour et mettre actif ou inactif
    @FXML public CheckBox CbAfficherProgramme; //Cb pour afficher dans la tableview les programmes actif/inactif
    @FXML public Button BtnModifProgramme;
    @FXML public Button BtnAssignerCoursProgramme;
    
    @FXML public ListView<String> ListViewCourAssigner;
    @FXML public ListView<String> ListViewCourAttente;
    
    @FXML public Label LblProgrammeSelectionner;
    @FXML public Button BtnAjouterCoursAuProgramme; //Assigner les cours au programme (button confirm) 
    @FXML public Pane PaneAssignerCourProgramme;
    
    @FXML public Label LblMessageProg; //Afficher les messages des cours assigner ou non-assigner.
  
    @FXML public Label LblMsgPrincProgCour; //Afficher le nom du programme ou cour dans la pane de notification 
    @FXML public ComboBox<String> CbProg;
    @FXML public ComboBox<String> CbPosteAdmin;
    
    /*
    	===IMAGES VIEW===
    */
    File file;
    Image icn;
    @FXML public ImageView ImgLogo;
    @FXML public ImageView ImageViewSearch;
    @FXML public ImageView ImageViewChat;
    @FXML public ImageView ImageViewNotif;
    @FXML public ImageView ImageViewPPicture;
    @FXML public ImageView ImgPrenom;
    @FXML public ImageView ImgNom;
    @FXML public ImageView ImgAdresse;
    @FXML public ImageView ImgVille;
    @FXML public ImageView ImgCodePostal;
    @FXML public ImageView ImgProvince;
    @FXML public ImageView ImgTel;
    @FXML public ImageView ImgEmail;
    @FXML public ImageView ImgUser;
    @FXML public ImageView ImgFlecheGauche;
    @FXML public ImageView ImgFlecheDroite;
    @FXML public ImageView FlecheDroiteOrange;//Fleche droit pour les Programme cour
    @FXML public ImageView FlecheGaucheOrange;//Fleche gauche pour les programme cour
    @FXML public ImageView FlecheGaucheOrange1;//Fleche gauche pour les Etudiant cour
    @FXML public ImageView FlecheDroiteOrange1;//Fleche droit pour les Etudiant cour
    @FXML public ImageView ImgClose;
    @FXML public ImageView ImgMini;
    @FXML public ImageView FlecheEnsProg;
    //--------------------------//
    //FRAIS//
    @FXML public Pane PaneFrais;
    @FXML public Button BtnFrais;
    @FXML public Button BtnAfficherFrais;
    @FXML public TableView<Etudiant> TableViewEtudiantFrais;
    @FXML public TableColumn<Etudiant, String> TcNomCompletEtudiant;
    @FXML public Label LblNomFrais;
    @FXML public Label LblTelephoneFrais;
    @FXML public Label LblEmailFrais;
    @FXML public TableView<Frais> TableViewFrais;
    @FXML public TableColumn<Frais, String> TcNCFrais;
    @FXML public TableColumn<Frais, String> TcFFRais;
    @FXML public TableColumn<Frais, String> TcDescriptionFrais;
    @FXML public TableColumn<Frais, String> TcEtatFrais;
    @FXML public Pane PaneAfficherFrais;
    @FXML public Pane PaneFonctionFrais; //Afficher/retirer tableview/btn ajout, modif, annuler de la paneAfficherFrais
    @FXML public Pane PaneAjoutFrais; //affiche les textbox pour ajouter des frais
    @FXML public Button BtnAjouterFrais; //Ouvre la pane PaneAjoutFrais
    @FXML public Button BtnAjoutFrais; //Ajout les nouveaux frais dans la db
    @FXML public Button BtnAnnulerFrais; //Ferme la pane PaneAjoutFrais et ouvre la pane PaneFonctionFrais
    @FXML public TextField TbType;
    @FXML public TextField TbCout;
    @FXML public ComboBox<String> CbEtatFrais;
    @FXML public Button BtnModifFrais;
    @FXML public Button BtnRetourFrais;
    @FXML public Button BtnSupprimerFrais;
    @FXML public Button BtnModifierFrais;
    @FXML public Separator LigneDessousBtnFrais;
    //--------------------------------//
    //***RESULTAT***//
    @FXML public Button BtnResultat;
    @FXML public Pane PaneResultat;
    @FXML public TableColumn<Etudiant, String> TcNomResult; //TableColumn Nom complet des etudiants
    @FXML public TableView<Etudiant> TvNomResult; //TableView Afficher les noms des resultat
    @FXML public Button BtnAfficherResult;
    @FXML public TableColumn<EtudiantCour, Integer> TcCoursResult;
    @FXML public TableColumn<EtudiantCour, Integer> TcResultat;
    @FXML public TableView<EtudiantCour> TvResultatCours;
    @FXML public Button BtnModifierResultat;
    @FXML public Button BtnSupprimerResultat;
    @FXML public Button BtnModifReusltat;
    @FXML public Pane PaneModifResult;
    @FXML public Label LblCourChoisi;
    @FXML public TextField TbChangeNote;
    @FXML public Label LblMsgErreurResult;
    
    public int Etat = 0;
    public int CourActif = 1;
    public int ProgrammeActif = 1;
    @FXML public Label LblShowCours;
    @FXML public String Poste = "";
    @FXML public int IDAdministrateur = 0;
   
    @FXML
    void OnClickCloseSetting(MouseEvent event) {
    	PaneSettingNotif.setVisible(false);
    }
    
    @FXML
    void OnClickSetting(MouseEvent event) {
    	PaneSettingNotif.setVisible(true);
    	OnActionRBNotif(null);
    }
    
    @FXML
    void OnCloseNotif(MouseEvent event) {
    	PaneNotification.setVisible(false);
    }
    
    @FXML
    void OnClickNotif(MouseEvent event) {
    		PaneNotification.setVisible(true);		
    }
    
    int IdProg = 0;
    @FXML
    void OnActionCbProgEnseignant(ActionEvent event) {
    	IdProg = Integer.parseInt(CbProg.getValue().substring(0,2).trim());
    	f.ListViewProgrammeEnseignantGauche(IdProg);
    }
    
    @FXML
    void OnClickListViewEtuProg(MouseEvent event) {
    	f.ListViewCoursProgramme();
    }
    
    @FXML
    void OnClickListViewProgEns(MouseEvent event) {
    	f.ListViewProgrammeEnseignantDroite();
    }
    
    @FXML
    void OnActionCheckBoxProgramme(ActionEvent event) {
    	f.ComboBoxProgramme();
    }
    
    @FXML
    void onMouseClickLeave(MouseEvent event) {
 	   System.exit(0); 
    }
    
    @FXML 
    void onMouseClickReduce(MouseEvent event) {
 	   Stage stage = (Stage) ImgMini.getScene().getWindow(); 
 	   stage.setIconified(true);
    }
    
    @FXML 
    void OnMouseClickFlecheList(MouseEvent event) {
    	ImageView img = (ImageView)event.getSource();
    	String ID = "";
    	
    	ID = img.getId();
    	switch(ID) {
    	case "FlecheDroiteOrange":
    		f.FlecheDroite();
    		break;
    	case "FlecheGaucheOrange":
    		f.FlecheGauche();
    		break;
    	case "FlecheGaucheOrange1":
    		f.FlecheEnvoieGauche();
    		break;
    	case "FlecheDroiteOrange1":
    		f.FlecheEnvoieDroite();
    		break;
    	case "FlecheEnsProg":
    		f.FlecheDroiteProgEns();
    		break;
    	}
    }
    
    @FXML 
    void OnActionFleche(MouseEvent event) {
    	ImageView img =(ImageView)event.getSource();
   	   
  	   	String ID = "";
  	  
  	   	ID = img.getId();
  	   	switch(ID) {
  	   	case "ImgFlecheDroite":
  	   	BarChartInactif.setVisible(true);
    	BarChart.setVisible(false);
    	ImgFlecheDroite.setOpacity(0.5);
    	ImgFlecheGauche.setOpacity(1);
    	ImgFlecheDroite.setDisable(true);
    	ImgFlecheGauche.setDisable(false);
    	LblTitreCoursActifs.setText("Données inactifs");
    	LblCompteurCours.setText(""+f.RecupNbCoursInactif());
    	LblCompteurEnseignants.setText(""+f.RecupNbEnseignantsInactif());
    	LblCompteurEtudiants.setText(""+f.RecupNbEtudiantsInactif());
    	LblCompteurProgramme.setText(""+f.RecupNbProgrammesInactif());
    	
  	   		break;
  	   	case "ImgFlecheGauche":
  	   	BarChartInactif.setVisible(false);
    	BarChart.setVisible(true);
    	ImgFlecheDroite.setDisable(false);
    	ImgFlecheDroite.setOpacity(1);
    	ImgFlecheGauche.setOpacity(0.5);
    	ImgFlecheGauche.setDisable(true);
    	LblTitreCoursActifs.setText("Données actifs");
    	LblCompteurCours.setText(""+f.RecupNbCoursActif());
    	LblCompteurEnseignants.setText(""+f.RecupNbEnseignantsActif());
    	LblCompteurEtudiants.setText(""+f.RecupNbEtudiantsActif());
    	LblCompteurProgramme.setText(""+f.RecupNbProgrammesActif());
    	
  	   		break;
  	   	}
    }
    
    @FXML
    void OnActionRBNotif(ActionEvent event) {

    	//RadioButton Notif non lu
    	if(CheckBoxNotifNL.isSelected()) {
    		EtatNotif = 1;		   		
    	}
    	//RadioButton Notif lu
    	if(CheckBoxNotifL.isSelected()){
    		EtatNotif = 0;   		
    	}
    	f.AfficherNotification();
    }
    
    @FXML
    void OnActionCBNotifDetail(ActionEvent event) {
    	
    	if(CheckBoxNotifDetail.isSelected()) {
    		EtatNotif = 1;
    		CheckBoxNotifDetail.setText("Afficher les notifications lu");
    	}
    	else {
    		EtatNotif = 0;
    		CheckBoxNotifDetail.setText("Afficher les notifications non-lu");
    	}
    	f.AfficherNotifDetail();
    }
    
    @FXML
    void CbProgrammeActifProgramme(ActionEvent event) {
    	if(CbProgramme.isSelected()) {
    		Etat = 1;
    	}
    	else {
    		Etat = 0;
    	}
    }
    
    @FXML
    void CbEtatOnAction(ActionEvent event) {
    	if(CheckBoxEtat.isSelected()) {
    		Etat = 1;    			
    	}
    	else {
    		Etat = 0;
    	}
    }
    
    @FXML
    void OnActionProgramme(ActionEvent event) {
    	if(CbAfficherProgramme.isSelected()) {
    		ProgrammeActif = 1;
    		CbAfficherProgramme.setText("Afficher les cours inactifs");
    		f.AfficherProgramme();
    	}
    	else {
    		ProgrammeActif = 0;
    		CbAfficherProgramme.setText("Afficher les cours actifs");
    		f.AfficherProgramme();
    	}
    }
    
    @FXML
    void CbCoursActifsOnAction(ActionEvent event) {
    	if(CbCoursActifs.isSelected()) {
    		CourActif = 1;
    		CbCoursActifs.setText("Afficher les cours inactifs");
    		LblShowCours.setText("Cours actifs");
    		f.AfficherCour();
    	}
    	else {
    		CourActif = 0;
    		LblShowCours.setText("Cours inactifs");
    		CbCoursActifs.setText("Afficher les cours actifs");
    		f.AfficherCour();
    	}
    }
    
    public void setInfo(Administration administration) {
 
    	file = new File("src\\Photos\\XMauve.png");
    	icn = new Image(file.toURI().toString());
    	ImgClose.setImage(icn);
    	
    	file = new File("src\\Photos\\XNoir50.png");
    	icn = new Image(file.toURI().toString());
    	ImgClose.setImage(icn);
    	
    	file = new File("src\\Photos\\moins50.png");
    	icn = new Image(file.toURI().toString());
    	ImgMini.setImage(icn);
    	
    	file = new File("src\\Photos\\RightArrow24.png");
    	icn = new Image(file.toURI().toString());
    	ImgFlecheDroite.setImage(icn);
    	
    	file = new File("src\\Photos\\LeftArrow24.png");
    	icn = new Image(file.toURI().toString());
    	ImgFlecheGauche.setImage(icn);
    	
    	file = new File("src\\Photos\\S.png");
    	icn = new Image(file.toURI().toString());
    	ImgLogo.setImage(icn);
    	
    	file = new File("src\\Photos\\icons8-chercher-24.png");
    	icn = new Image(file.toURI().toString());
    	ImageViewSearch.setImage(icn);
    	
    	file = new File("src\\Photos\\icons8-bavarder-24.png");
    	icn = new Image(file.toURI().toString());
    	ImageViewChat.setImage(icn);
    	
    	file = new File("src\\Photos\\Notif (2).png");
    	icn = new Image(file.toURI().toString());
    	ImageViewNotif.setImage(icn);
    	
    	file = new File("src\\Photos\\user.png");
    	icn = new Image(file.toURI().toString());
    	ImageViewPPicture.setImage(icn);
    	
    	file = new File("src\\Photos\\User2.png");
    	icn = new Image(file.toURI().toString());
    	ImgPrenom.setImage(icn);
    	
    	file = new File("src\\Photos\\User2.png");
    	icn = new Image(file.toURI().toString());
    	ImgNom.setImage(icn);
    	
    	file = new File("src\\Photos\\Adresse.png");
    	icn = new Image(file.toURI().toString());
    	ImgAdresse.setImage(icn);
    	
    	file = new File("src\\Photos\\ville.png");
    	icn = new Image(file.toURI().toString());
    	ImgVille.setImage(icn);
    	
    	file = new File("src\\Photos\\icons8-code-postal-24.png");
    	icn = new Image(file.toURI().toString());
    	ImgCodePostal.setImage(icn);
    	
    	file = new File("src\\Photos\\icons8-carte-du-canada-24.png");
    	icn = new Image(file.toURI().toString());
    	ImgProvince.setImage(icn);
    	
    	file = new File("src\\Photos\\tel.png");
    	icn = new Image(file.toURI().toString());
    	ImgTel.setImage(icn);
    	
    	file = new File("src\\Photos\\mail.png");
    	icn = new Image(file.toURI().toString());
    	ImgEmail.setImage(icn);
    	
    	file = new File("src\\Photos\\User2.png");
    	icn = new Image(file.toURI().toString());
    	ImgUser.setImage(icn);
    	
    	this.administration = administration;  
    	
    	
    	//switch case permission
    	switch(administration.getPoste()) {
    		//permission professeur
    		case "Professeur":
    				TableViewEtudiant.setPrefHeight(750);
    				TableViewEnseignant.setPrefHeight(750);
    				BtnFrais.setDisable(true);
    				LigneDessousBtnFrais.setDisable(true);
    				/*
    				 * Bouton de la section cours, le prof ne peut modifier/supprimer/ajouter des cours
    				 */
    				//Section Bouton cours
    				BtnModifierCours.setVisible(false);
    				BtnAjouterCours.setVisible(false);
    				BtnSupCours.setVisible(false);
    				//Section Bouton étudiant
    				BtnAjouterEtudiant.setVisible(false);
    				BtnModifierEtudiant.setVisible(false);
    				BtnSupEtudiant.setVisible(false);
    				BtnAssignerProgramme.setVisible(false);
    				//Section Bouton Enseignant
    				BtnAjouterEnseignant.setVisible(false);
    				BtnModifierEnseignant.setVisible(false);
    				BtnSupEnseignant.setVisible(false);
    				BtnAssignerProgEns.setVisible(false);
    				/*
    				 * Bouton qu'on setVisible a true pour ajouter des examens/projets
    				 */
    				BtnAjouterExam.setVisible(true);
    				BtnAfficherExamenCours.setVisible(true);
    			break;
    			//permission Admin
    		case "Administrateur":
    			BtnAfficherExamen.setVisible(false);
    			BtnModifierCours.setVisible(true);
				BtnAjouterCours.setVisible(true);
				BtnSupCours.setVisible(true);
				BtnAjouterExam.setVisible(false);
				BtnAfficherExamenCours.setVisible(false);
    			break;
    	}
    	
    	NomAdministrateur = administration.getFullName();
    	LblNom.setText(administration.getFullName());  	
    	Poste = administration.getPoste();
    	IDAdministrateur = administration.getID();
    	TelAdmin = administration.getTelephone();
    	EmailAdmin = administration.getEmail();
    	
    	LblCompteurCours.setText(Integer.toString(f.RecupNbCoursActif()));
    	LblCompteurEnseignants.setText(Integer.toString(f.RecupNbEnseignantsActif()));
    	LblCompteurEtudiants.setText(Integer.toString(f.RecupNbEtudiantsActif()));
    	LblCompteurProgramme.setText(Integer.toString(f.RecupNbProgrammesActif()));
    	
    	f.BarGraphActifs(); 
    	f.BarGraphInactifs();
    	f.AfficherEtudiant();
    	f.AfficherEnseignant();
    	f.AfficherProgramme();
    	f.SelectRowEtudiant();
    	f.SelectRowNotif();
    	f.SelectRowEnseignant();
    	f.SelectRowProgramme();
    	f.SelectRowFrais();
    	f.SelectRowCour();
    	f.formatTextfield();
    	f.ComboBoxProgramme();
    	f.AfficherNotification();
    
      }
    
    int CouleurBut = 1;
    @FXML
    void OnActionPrincipal(ActionEvent event) {
    	Button btn =(Button)event.getSource();
  	   
  	   	String ID = "";
  	  
  	   	ID = btn.getId();
  	   	switch(ID) {
  	   	
  	  case "BtnContinueExam":
  		  		PaneCommentaire.setVisible(false);
  		  		PaneBlack.setVisible(false);
  		  		PaneMsg.setVisible(false);
  	   		break;
  	   		
  	  case "BtnContinueMsg":
  		  	PaneCommentaire.setVisible(false);
  		  	PaneAfficherExamen.setVisible(false);
  		  	PaneAjoutExam.setVisible(false);
  		  	TvNomResult.setVisible(true);
	   		BtnAfficherResult.setVisible(true);
  		  	PaneResultat.setVisible(false);
  		 	PaneAssignerProgEns.setVisible(false);
  		  	PaneAfficherFrais.setVisible(false);
  		  	PaneFrais.setVisible(false);
  		  	PaneEtuProgAssigne.setVisible(false);
  		  	PaneAssigneCourEtudiant.setVisible(false);
  		  	PaneAssignerCourProgramme.setVisible(false);
  		  	PaneAjoutProgramme.setVisible(false);
	   		PaneBlack.setVisible(false);
	   		PaneMsg.setVisible(false);
	   		PaneInscriptionEtudiant.setVisible(false);
	   		PaneAccueil.setVisible(true);
	   		GrandePaneEnseignant.setVisible(false);
	   		GrandePaneEtudiant.setVisible(false);
	   		PaneGrandCour.setVisible(false);
	   		PanePrincipaleProgramme.setVisible(false);
	   		LblMsgPrincProgCour.setVisible(false);
	   		break;
	
  	   	case "BtnAcueil":
  	   		PaneNotifDetail.setVisible(false);
  	   		PaneNotification.setVisible(false);
  	   		f.BarGraphActifs();
  	   		PaneCommentaire.setVisible(false);
  	   		PaneAfficherExamen.setVisible(false);
  	   		PaneAjoutExam.setVisible(false);
  	   		PaneResultat.setVisible(false);
  	   		PaneAssignerProgEns.setVisible(false);
  	   		PaneAfficherFrais.setVisible(false);
  	   		PaneFrais.setVisible(false);
  	   		PaneEtuProgAssigne.setVisible(false);
  	   		PaneAssigneCourEtudiant.setVisible(false);
  	   		PaneAssignerCourProgramme.setVisible(false);
  	   		PaneAjoutProgramme.setVisible(false);
  	   		PaneAjoutCours.setVisible(false);
  	   		PaneAccueil.setVisible(true);
  	   		GrandePaneEtudiant.setVisible(false);
  	   		GrandePaneEnseignant.setVisible(false);
  	   		PaneInscriptionEtudiant.setVisible(false);
  	   		PaneGrandCour.setVisible(false);
  	   		PanePrincipaleProgramme.setVisible(false);
  	   		TvNomResult.setVisible(true);
	   		BtnAfficherResult.setVisible(true);
  	   		LblCompteurCours.setText(""+f.RecupNbCoursActif());
  	   		LblCompteurEnseignants.setText(""+f.RecupNbEnseignantsActif());
  	   		LblCompteurEtudiants.setText(""+f.RecupNbEtudiantsActif());
  	   		LblCompteurProgramme.setText(""+f.RecupNbProgrammesActif());
  	   		break;
  	   		
  	   		//SECTION ETUDIANT
  	   		//Bouton qui affiche la première section des étudiants
  	   	case "BtnEtudiant":
  	   		PaneNotifDetail.setVisible(false);
	   		PaneNotification.setVisible(false);
  	   		VariableGlobal = "Etudiant";
  	   		PaneCommentaire.setVisible(false);	
  	   		PaneAfficherExamen.setVisible(false);
  	   		PaneAjoutExam.setVisible(false);
  	   		TvNomResult.setVisible(true);
	   		BtnAfficherResult.setVisible(true);
  	   		PaneResultat.setVisible(false);
  	   		PaneAssignerProgEns.setVisible(false);
  	   		PaneAfficherFrais.setVisible(false);
  	   		PaneFrais.setVisible(false);
  	   		PaneEtuProgAssigne.setVisible(false);
  	   		PaneAssigneCourEtudiant.setVisible(false);
  	   		PaneAssignerCourProgramme.setVisible(false);
  	   		PaneAjoutProgramme.setVisible(false);
  	   		PanePrincipaleProgramme.setVisible(false);
  	   		PaneAjoutCours.setVisible(false);
  	   		PaneInscriptionEtudiant.setVisible(false);
  	   		PaneAccueil.setVisible(false);
  	   		GrandePaneEtudiant.setVisible(true);
  	   		GrandePaneEnseignant.setVisible(false);
  	   		PaneGrandCour.setVisible(false);
   	   		break;
   	   		
   	   		//Ici on affiche le menu pour ajouter un étudiant
  	   	case "BtnAjouterEtudiant":
  	   		f.ComboBoxProgramme();
  	   		ComboBoxProgramme.setVisible(true);
	   		CbLangueEtudiant.setVisible(true);
	   		CbLangueProgramme.setVisible(true);
	   		CbPosteAdmin.setVisible(false);
  	   		PaneInscriptionEtudiant.setVisible(true);
  	   		GrandePaneEtudiant.setVisible(false);
  	   		BtnCancelEtudiant.setVisible(true);
	   		BtnCancelEnseignant.setVisible(false);
	   		BtnAjoutEnseignant.setVisible(false);
	   		BtnAjoutEtudiant.setVisible(true);
	   		BtnModifEtudiant.setVisible(false);
	   		lblTitreInscription.setText("Inscription étudiant");
	   		f.ComboBoxLangage();
  	   		f.ClearInfo();
  	   		break;
  	   		//Ici on insert l'étudiant dans la base de donnée
  	   	case "BtnAjoutEtudiant":
  	   		f.InsertEtudiant();
  	   		break;
  	   		
  	   		//Si on cancel, on retourne au menu GrandePaneEtudiant
  	   	case "BtnCancelEtudiant":
  	   		PaneInscriptionEtudiant.setVisible(false);
	   		GrandePaneEtudiant.setVisible(true);
  	   		break;
  	   		
  	   		//On selectionne un étudiant dans la tableview, puis on le supprime
  	   	case "BtnSupEtudiant":
  	   		PaneBlack.setVisible(true);
  	   		GrandePaneEtudiant.setVisible(false);
  	   		BtnSupprimerComEtudiant.setVisible(true);
  	   		PaneCommentaire.setVisible(true);
  	   		TitreCommentaire.setText("Vous êtes sur le point de supprimer l'étudiant suivant " + f.NomEtudiant);
  	   		break;
  	   		
  	   	case "BtnSupprimerComEtudiant":
  	   		if(VariableGlobal.equals("Etudiant")) {
  	   			
  	   			f.SupprimerEtudiant();
  	   		}
  	   		else if(VariableGlobal.equals("Enseignant")) {
  	   			System.out.println("Enseignant");
  	   			f.SupprimerEnseignant();
  	   		}
  	   		else if(VariableGlobal.equals("Programme")) {
  	   			
  	   			f.SupprimerProgramme();
  	   		}
  	   		else if(VariableGlobal.equals("Cours")) {
  	   			
  	   			f.SupprimerCours();
  	   		}
  	   		else if(VariableGlobal.equals("Frais")) {
  	   			
  	   			f.AnnulerFrais();
  	   		}
  	   		else if(VariableGlobal.equals("Resultat")) {
  	   			
  	   			f.SupprimerResultat();
  	   		}
  	   		else if(VariableGlobal.equals("Examen")) {
  	   			f.SupprimerExamen();
  	   		}
  	   		break;
  	   		
  	   	case "BtnAnnulerComEtudiant":
  	   		if(VariableGlobal.equals("Etudiant")) {
  	   			PaneBlack.setVisible(false);
	   			PaneCommentaire.setVisible(false);
	   			GrandePaneEtudiant.setVisible(true);
	   		}
	   		else if(VariableGlobal.equals("Enseignant")) {  			
	   			PaneBlack.setVisible(false);
  	   			PaneCommentaire.setVisible(false);
  	   			GrandePaneEnseignant.setVisible(true);
	   		}
	   		else if(VariableGlobal.equals("Programme")) {   			
	   			PaneBlack.setVisible(false);
  	   			PaneCommentaire.setVisible(false);
  	   			PanePrincipaleProgramme.setVisible(true);
	   		}
	   		else if(VariableGlobal.equals("Cours")) {	   			
	   			PaneBlack.setVisible(false);
  	   			PaneCommentaire.setVisible(false);
  	   			PaneGrandCour.setVisible(true);
	   		}
	   		else if(VariableGlobal.equals("Frais")) {
	   			PaneBlack.setVisible(false);
  	   			PaneCommentaire.setVisible(false);
  	   			PaneFrais.setVisible(true); 			
	   		}
	   		else if(VariableGlobal.equals("Resultat")) {
	   			PaneBlack.setVisible(false);
  	   			PaneCommentaire.setVisible(false);
  	   			PaneResultat.setVisible(true);
	   		}  
	   		else if(VariableGlobal.equals("Examen")) {
	   			PaneBlack.setVisible(false);
  	   			PaneCommentaire.setVisible(false);
  	   			PaneAfficherExamen.setVisible(true);
	   		}
  	   		break;
  	   		//Ici on selectionne un étudiant dans la tableview, 
  	   		//puis les données de l'étudiant s'affiche dans le menu d'ajout étudiant
  	   	case "BtnModifierEtudiant":
  	   			f.ComboBoxLangage();
  	   			f.ComboBoxProgramme();
  	   			PaneInscriptionEtudiant.setVisible(true);
  	   			GrandePaneEtudiant.setVisible(false);
  	   			BtnAjoutEtudiant.setVisible(false);
  	   			BtnAjoutEnseignant.setVisible(false);
  	   			BtnModifEtudiant.setVisible(true);
  	   			BtnCancelEtudiant.setVisible(true);
  	   			BtnInscription.setVisible(true);
  	   			f.ShowInfoEtudiant();
  	   		break;
  	   		
  	   		//ici on confirme la modification
  	   	case "BtnModifEtudiant":
  	   		f.ModifierEtudiant();
  	   		break;
  	  case "BtnInscription":
  		  	GrandePaneEtudiant.setVisible(false);
  		  	PaneInscriptionEtudiant.setVisible(false);
  		  	PaneAssigneCourEtudiant.setVisible(true);  
  		  	f.ListViewEtudiantCour();
	   		break;
  	  case "BtnAssignerProgramme":
  		  PaneEtuProgAssigne.setVisible(true);
  		  GrandePaneEtudiant.setVisible(false);
  		  f.ListViewProgramme();
  		  f.ComboBoxProgramme();
  		  break;
  	  case "BtnSupProgEtu":
  		  	f.SupprimerItemsListView();
  		  	break;
  	  case "BtnProgEtuAjout":
  		  f.AjouterCours();
  		  break;
  	  case "BtnRetourEtuCour":
  		  PaneAssigneCourEtudiant.setVisible(false);
  		  PaneInscriptionEtudiant.setVisible(true);
  		  break;
  	  case "BtnRetourEtuProg":
  		  GrandePaneEtudiant.setVisible(true);
  		  PaneEtuProgAssigne.setVisible(false);
  		  break;
  		  
  	   		//SECTION ENSEIGNANT
  	   	case "BtnEnseignant":
  	   		PaneNotifDetail.setVisible(false);
	   		PaneNotification.setVisible(false);
  	   		VariableGlobal = "Enseignant";
  	   		PaneCommentaire.setVisible(false);
  	   		PaneAfficherExamen.setVisible(false);
  	   		PaneAjoutExam.setVisible(false);
  	   		TvNomResult.setVisible(true);
	   		BtnAfficherResult.setVisible(true);
  	   		PaneResultat.setVisible(false);
  	   		PaneAssignerProgEns.setVisible(false);
  	   		PaneAfficherFrais.setVisible(false);
  	   		PaneFrais.setVisible(false);
  	   		PaneEtuProgAssigne.setVisible(false);
  	   		PaneAssigneCourEtudiant.setVisible(false);
  	   		PaneAssignerCourProgramme.setVisible(false);
  	   		PanePrincipaleProgramme.setVisible(false);
  	   		PaneAjoutCours.setVisible(false);
  	   		PaneInscriptionEtudiant.setVisible(false);
  	   		PaneAccueil.setVisible(false);
  	   		GrandePaneEtudiant.setVisible(false);
  	   		GrandePaneEnseignant.setVisible(true);
  	   		PaneGrandCour.setVisible(false);
  	   		PaneAjoutProgramme.setVisible(false);
  	   		break;	
  	   	case "BtnAjouterEnseignant":
  	   		f.ComboBoxPoste();
  	   		ComboBoxProgramme.setVisible(false);
  	   		CbLangueEtudiant.setVisible(false);
  	   		CbLangueProgramme.setVisible(false);
  	   		CbPosteAdmin.setVisible(true);	
  	   		PaneAfficherFrais.setVisible(false);
  	   		PaneInscriptionEtudiant.setVisible(true);
  	   		BtnCancelEtudiant.setVisible(false);
  	   		BtnModifEnseignant.setVisible(false);
	   		BtnCancelEnseignant.setVisible(true);
	   		BtnAjoutEnseignant.setVisible(true);
	   		BtnAjoutEtudiant.setVisible(false);
	   		lblTitreInscription.setText("Inscription enseignant");
	   		GrandePaneEnseignant.setVisible(false);
	   		f.ClearInfo();
  	   		break;
  	   	case "BtnAjoutEnseignant":
  	   		f.InsertEnseignant();
  	   		break;
  	   	case "BtnCancelEnseignant":
  	   		GrandePaneEnseignant.setVisible(true);
  	   		PaneInscriptionEtudiant.setVisible(false);  	   		
  	   		break;
  	   	case "BtnSupEnseignant":
  	   		PaneBlack.setVisible(true);
	   		GrandePaneEtudiant.setVisible(false);
	   		BtnSupprimerComEtudiant.setVisible(true);
	   		PaneCommentaire.setVisible(true);
	   		TitreCommentaire.setText("Vous êtes sur le point de supprimer l'enseignant suivant " + f.NomEnseignant);
  	   		
  	   		break;
  	   	case "BtnModifierEnseignant":
  	   		PaneInscriptionEtudiant.setVisible(true);
	   		BtnModifEnseignant.setVisible(true);
	   		BtnCancelEnseignant.setVisible(true);
	   		BtnCancelEtudiant.setVisible(false);
	   		BtnAjoutEnseignant.setVisible(false);
	   		BtnAjoutEtudiant.setVisible(false);
	   		BtnModifEtudiant.setVisible(false);
	   		GrandePaneEnseignant.setVisible(false);
	   		f.ShowInfoEnseignant();
  	   		break;
  	   	case "BtnModifEnseignant":
  	   		f.ModifierEnseignant();
  	   		break;
  	   	case "BtnAjouterEtudiantCour":
  	   		f.AssignerEtudiantCour();
  	   		break;
  	   	case "BtnAssignerProgEns":
  	   		PaneAssignerProgEns.setVisible(true);
  	   		GrandePaneEnseignant.setVisible(false);
  	   		f.ListViewProgrammeEnseignantDroite();	
  	   		
  	   		break;
  	   	case "BtnSupCoursEns":
  	   		f.SupprimerCoursEns();
  	   		break;
  	   	case "BtnAjoutCourEns":
  	   		f.AjouterCoursEnseignant();
  	   		break;
  	   		//SECTION COURS
  	   	case "BtnCour":
  	   		PaneNotifDetail.setVisible(false);
	   		PaneNotification.setVisible(false);
  	   		VariableGlobal = "Cours";
  	   		PaneCommentaire.setVisible(false);
  	   		PaneAfficherExamen.setVisible(false);
  	   		f.AfficherCour();
  	   		PaneAjoutExam.setVisible(false);
  	   		TvNomResult.setVisible(true);
	   		BtnAfficherResult.setVisible(true);
  	   		PaneResultat.setVisible(false);
  	   		PaneAssignerProgEns.setVisible(false);
  	   		PaneAfficherFrais.setVisible(false);
  	   		PaneFrais.setVisible(false);
  	   		PaneEtuProgAssigne.setVisible(false);
  	   		PaneAssigneCourEtudiant.setVisible(false);
  	   		PaneAssignerCourProgramme.setVisible(false);
  	   		PaneAjoutProgramme.setVisible(false);
  	   		PaneAccueil.setVisible(false);
	   		GrandePaneEtudiant.setVisible(false);
	   		GrandePaneEnseignant.setVisible(false);
	   		PaneInscriptionEtudiant.setVisible(false);
	   		PaneGrandCour.setVisible(true);
	   		PanePrincipaleProgramme.setVisible(false);
	   		PaneAjoutCours.setVisible(false);
	   		break;
  	   	case "BtnAjouterCours":
  	   		PaneAjoutCours.setVisible(true);
  	   		PaneGrandCour.setVisible(false);	   
	   		BtnCancelCours.setVisible(true);
	   		BtnAjoutCours.setVisible(true);
	   		BtnModifCours.setVisible(false);	   		
  	   		break;
  	   	case "BtnAjoutCours":
  	   		f.InsertCours();
  	   		break;
  	   	case "BtnSupCours":
  	   		
  	   		PaneBlack.setVisible(true);
  	   		GrandePaneEtudiant.setVisible(false);
  	   		BtnSupprimerComEtudiant.setVisible(true);
  	   		PaneCommentaire.setVisible(true);
  	   		TitreCommentaire.setText("Vous êtes sur le point de supprimer le cours suivant " + f.nomCour);
  	   		
  	   		break;
  	   	case "BtnModifierCours":
  	   		PaneAjoutCours.setVisible(true);	   		
  	   		BtnCancelCours.setVisible(true);
  	   		BtnModifCours.setVisible(true);
  	   		BtnAjoutCours.setVisible(false);
  	   		PaneGrandCour.setVisible(false);
  	   		f.ShowInfoCours();
  	   		break;
  	   	case "BtnModifCours":
  	   		f.ModifierCours();
  	   		break;
  	   	case "BtnCancelCours":
  	   		PaneAjoutCours.setVisible(false);
  	   		PaneGrandCour.setVisible(true);
  	   		break;
  	   	case "BtnAjouterExam":
  	   		PaneAjoutExam.setVisible(true);
  	   		PaneGrandCour.setVisible(false);
  	   		break;
  	   	case "BtnAjouterExamen":
  	   		f.InsertExamen(); 		
  	   		break;
  	   		
  	   	case "BtnAfficherExamen":
  	   		VariableGlobal = "Examen";
  	   		PaneTableViewExam.setVisible(true);
  	   		PaneModificationExamen.setVisible(false);
  	   		f.SelectRowExamen();
  	   		PaneGrandCour.setVisible(false);
  	   		PaneAfficherExamen.setVisible(true);
  	   		f.AfficherExamen();
  	   		PaneAjouterNoteExam.setVisible(false);
  	   		break;
  	   		
  	   	case "BtnAfficherExamenCours":
  	   		VariableGlobal = "Examen";
  	   		PaneTableViewExam.setVisible(true);
	   		PaneModificationExamen.setVisible(false);
	   		PaneAjouterNoteExam.setVisible(false);
	   		f.SelectRowExamen();
	   		PaneGrandCour.setVisible(false);
	   		PaneAfficherExamen.setVisible(true);	   	
	   		f.AfficherExamenCours();
  	   		break;
  	   		
  	   	case "BtnSupprimerExam":	   		
  	   		PaneBlack.setVisible(true);
  	   		PaneAfficherExamen.setVisible(false);
  	   		BtnSupprimerComEtudiant.setVisible(true);
  	   		PaneCommentaire.setVisible(true);
  	   		TitreCommentaire.setText("Vous êtes sur le point de supprimer l'examen suivant " + f.TitreEvaluation);
  	   		
  	   		break;
  	   		
  	   	case "BtnModifierExam":
  	   		PaneTableViewExam.setVisible(false);
  	   		PaneModificationExamen.setVisible(true);
  	   		break;
  	   		
  	   	case "BtnModifExam":
  	   		f.ModifierExamen();
  	   		break;
  	   		
  	   	case "BtnAfficherEvalutation":
  	   		PaneTableViewExam.setVisible(false);
  	   		PaneModificationExamen.setVisible(false);
  	   		PaneResultat.setVisible(false);
  	   		PaneAfficherExamen.setVisible(true);
  	   		PaneAjouterNoteExam.setVisible(true);
  	   		f.AfficherExamenEtudiant();
  	   		f.SelectRowExamenEtudiant();
  	   		PaneAjouterNote.setVisible(false);
  	   		break;
  	   		
  	   	case "BtnAjoutNoteExam":
  	   		PaneAjouterNote.setVisible(false);
  	   		f.InsertNoteExamen();
  	   		break;
  	   		
  	   		//SECTION PROGRAMME
  	   	case "BtnProgramme":
  	   		PaneNotifDetail.setVisible(false);
	   		PaneNotification.setVisible(false);
  	   		VariableGlobal = "Programme";
  	   		PaneCommentaire.setVisible(false);
  	   		PaneAfficherExamen.setVisible(false);
  	   		PaneAjoutExam.setVisible(false);
  	   		TvNomResult.setVisible(true);
	   		BtnAfficherResult.setVisible(true);
  	   		PaneResultat.setVisible(false);
  	   		PaneAssignerProgEns.setVisible(false);
  	   		PaneAfficherFrais.setVisible(false);
  	   		PaneFrais.setVisible(false);
  	   		PaneEtuProgAssigne.setVisible(false);
  	   		PaneAssigneCourEtudiant.setVisible(false);
  	   		PaneAssignerCourProgramme.setVisible(false);
  	   		PanePrincipaleProgramme.setVisible(true);
  	   		PaneAccueil.setVisible(false);
  	   		GrandePaneEtudiant.setVisible(false);
  	   		GrandePaneEnseignant.setVisible(false);
  	   		PaneInscriptionEtudiant.setVisible(false);
  	   		PaneGrandCour.setVisible(false);
   			PaneAjoutCours.setVisible(false);
   			PaneAjoutProgramme.setVisible(false);
  	   		break;
  	   		
  	   	case "BtnAjouterProgramme":
  	   		PanePrincipaleProgramme.setVisible(false);
  	   		PaneAjoutProgramme.setVisible(true);
  	   		break;
  	   	case "BtnAjoutProgramme":
  	   		f.InsertProgramme();
  	   		break;
  	   	case "BtnSupProgramme":
  	   		PaneBlack.setVisible(true);
	   		GrandePaneEtudiant.setVisible(false);
	   		BtnSupprimerComEtudiant.setVisible(true);
	   		PaneCommentaire.setVisible(true);
	   		TitreCommentaire.setText("Vous êtes sur le point de supprimer le programme suivant " + f.TitreProgramme);
  	   		break;
  	   	case "BtnModifierProgramme":
  	   		PanePrincipaleProgramme.setVisible(false);
  	   		PaneAjoutProgramme.setVisible(true);
  	   		BtnAjoutProgramme.setVisible(false);
  	   		BtnModifProgramme.setVisible(true);
  	   		f.ShowInfoProgramme();
  	   		break;
  	   	case "BtnModifProgramme":
  	   		f.ModifierProgramme();
  	   		break;
  	   		
  	   	case "BtnAssignerCoursProgramme":
  	   		PaneAssignerCourProgramme.setVisible(true);
  	   		f.ListViewProgrammeCour();
  	   		PanePrincipaleProgramme.setVisible(false);
  	   		break;
		
  	   	case "BtnAjouterCoursAuProgramme": //Bouton ajout cour-programme
  	   		f.AssignerCourProgramme();
  	   		break;
  	   		
  	   	case "BtnFrais":
  	   		PaneNotifDetail.setVisible(false);
	   		PaneNotification.setVisible(false);
  	   		VariableGlobal = "Frais";
  	   		PaneCommentaire.setVisible(false);
  	   		PaneAfficherExamen.setVisible(false);
  	   		PaneAjoutExam.setVisible(false);
  	   		f.AfficherFraisEtudiant();
  	   		TvNomResult.setVisible(true);
	   		BtnAfficherResult.setVisible(true);
  	   		PaneResultat.setVisible(false);
  	   		PaneAssignerProgEns.setVisible(false);
  	   		f.SelectRowEtudiantFrais();
  	   		PaneAfficherFrais.setVisible(false);
  	   		PaneFrais.setVisible(true);
  	   		PaneEtuProgAssigne.setVisible(false);
	   		PaneAssigneCourEtudiant.setVisible(false);
	   		PaneAssignerCourProgramme.setVisible(false);
	   		PanePrincipaleProgramme.setVisible(false);
	   		PaneAccueil.setVisible(false);
	   		GrandePaneEtudiant.setVisible(false);
	   		GrandePaneEnseignant.setVisible(false);
	   		PaneInscriptionEtudiant.setVisible(false);
	   		PaneGrandCour.setVisible(false);
			PaneAjoutCours.setVisible(false);
			PaneAjoutProgramme.setVisible(false);
  	   		break;
  	   	case "BtnAfficherFrais":
  	   		f.AfficherFrais();
  	   		PaneFrais.setVisible(false);
  	   		PaneAfficherFrais.setVisible(true);
  	   		PaneFonctionFrais.setVisible(true);
	   		PaneAjoutFrais.setVisible(false);
  	   		break;
  	   	case "BtnAjouterFrais":
  	   		f.ComboBoxEtat();
  	   		BtnRetourFrais.setVisible(false);
  	   		BtnAjoutFrais.setVisible(true);
  	   		BtnModifFrais.setVisible(false);
  	   		PaneFonctionFrais.setVisible(false);
  	   		PaneAjoutFrais.setVisible(true);
  	   		break;
  	   	case "BtnAjoutFrais":
  	   		f.InsertFrais();
  	   		break;
  	   	case "BtnAnnulerFrais":
  	   		BtnRetourFrais.setVisible(true);
  	   		PaneFonctionFrais.setVisible(true);
	   		PaneAjoutFrais.setVisible(false);
  	   		break;
  	   	case "BtnModifFrais":
  	   		f.ModifierFrais();
  	   		break;
  	   	case "BtnModifierFrais":
  	   		f.ComboBoxEtat();
  	   		f.ShowInfoFrais();
	   		BtnRetourFrais.setVisible(false);
	   		BtnAjoutFrais.setVisible(false);
	   		BtnModifFrais.setVisible(true);
	   		PaneFonctionFrais.setVisible(false);
	   		PaneAjoutFrais.setVisible(true);
  	   		break;
  	   	case "BtnSupprimerFrais":
  	   		PaneBlack.setVisible(true);
  	   		GrandePaneEtudiant.setVisible(false);
  	   		BtnSupprimerComEtudiant.setVisible(true);
  	   		PaneCommentaire.setVisible(true);
  	   		TitreCommentaire.setText("Vous êtes sur le point d'annuler les frais suivant " + f.TitreFrais);  	   		
  	   		break;
  	   		//SECTION RESULT
  	   	case "BtnResultat":
  	   		PaneNotifDetail.setVisible(false);
	   		PaneNotification.setVisible(false);
  	   		VariableGlobal = "Resultat";
  	   		PaneCommentaire.setVisible(false);
  	   		PaneAfficherExamen.setVisible(false);
  	   		PaneAjoutExam.setVisible(false);
  	   		BtnModifierResultat.setVisible(false);
  	   		BtnSupprimerResultat.setVisible(false);
  	   		TvResultatCours.setVisible(false);
  	   		f.SelectRowNomResultat();
  	   		TvNomResult.setVisible(true);
	   		BtnAfficherResult.setVisible(true);
  	   		PaneResultat.setVisible(true);
  	   		f.AfficherResultatEtudiant();
	   		PaneAssignerProgEns.setVisible(false);
	   		PaneAfficherFrais.setVisible(false);
	   		PaneFrais.setVisible(false);
	   		PaneEtuProgAssigne.setVisible(false);
	   		PaneAssigneCourEtudiant.setVisible(false);
	   		PaneAssignerCourProgramme.setVisible(false);
	   		PanePrincipaleProgramme.setVisible(false);
	   		PaneAccueil.setVisible(false);
	   		GrandePaneEtudiant.setVisible(false);
	   		GrandePaneEnseignant.setVisible(false);
	   		PaneInscriptionEtudiant.setVisible(false);
	   		PaneGrandCour.setVisible(false);
	   		PaneAjoutCours.setVisible(false);
	   		PaneAjoutProgramme.setVisible(false);
	   		PaneModifResult.setVisible(false);
	   		BtnAfficherEvalutation.setVisible(false);
  	   		break;
  	   	case "BtnAfficherResult":
  	   		LblMsgErreurResult.setVisible(false);
  	   		f.SelectRowResultat();
  	   		f.AfficherResultatCours();
  	   		TvResultatCours.setVisible(true);
  	   		BtnSupprimerResultat.setVisible(true);
  	   		BtnModifierResultat.setVisible(true);
  	   		BtnAfficherEvalutation.setVisible(true); 		
  	   		TvNomResult.setVisible(false);
  	   		BtnAfficherResult.setVisible(false);  	   		
  	   		break;
  	   		
  	   	case "BtnModifierResultat":
  	   		PaneModifResult.setVisible(true);
  	   		BtnModifierResultat.setVisible(false);
  	   		BtnSupprimerResultat.setVisible(false);
  	   		TvResultatCours.setVisible(false);
  	   		break;
  	   		
  	   	case "BtnModifReusltat":
  	   		f.ModifierResultat();
  	   		break;
  	   	case "BtnSupprimerResultat":
  	   		PaneBlack.setVisible(true);
	   		GrandePaneEtudiant.setVisible(false);
	   		BtnSupprimerComEtudiant.setVisible(true);
	   		PaneCommentaire.setVisible(true);
	   		TitreCommentaire.setText("Vous êtes sur le point de supprimer le resultat du cours suivant " + f.nomCour);
  	   		break;
  	   		
  	   	case "BtnNotifLu":
  	   		f.MarquerCommeLu();
  	   		break;
  	   	case "BtnNotifNonLu":
  	   		f.MarquerCommeNonLu();
  	   		break;
  	   	case "BtnNotifSup":
  	   		f.SupprimerNotif();
  	   		break;
  	   	case "BtnNotifAfficher":
  	   		PaneNotifDetail.setVisible(true);
  	   		PaneNotification.setVisible(false);
  	   		f.AfficherNotifDetail();
  	   		break;
  	   	case "BtnPrecedentNotif":
  	   		PaneNotifDetail.setVisible(false);
	   		PaneNotification.setVisible(true);
  	   		break;
  	   	}
    }
}