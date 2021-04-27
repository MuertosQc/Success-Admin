import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

public class Fonction {
	// SECTION VARIABLE
	String RequeteSql; // Utilisé pour tout requête SQL
	Connection ConnexionDB; // Utilisé pour établir connexion
	ResultSet rs;
	int IdProgramme = 0; // Utilisé pour tout les IdProgramme
	public int compteur = 0; // Variable utilisé pour compteur le nb étudiant/enseignant/cours/programme
	private double xOffset = 0;
	private double yOffset = 0;

	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String currentTime = sdf.format(dt);
	
	int SelectEtudiantIndex = 0;
	int IDEtudiant = 0;
	String NomEtudiant;
	String TelEtudiant;
	String EmailEtudiant;

	String NomEnseignant;
	String TelEnseignant;
	String EmailEnseignant;
	String TitreCours;
	String nomCour = "";

	/*
	  Variable section evaluation
	*/
	int IdTitreEvaluation = 0;
	String TitreEvaluation = "";
	int ResultatEvaluation = 0;
	int SelectExamEtudiantIndex = 0;
	
	int TitreProgrammeEtudiant = 0;
	int IDEnseignant = 0;
	int IDCour = 0;

	int SelectProgrammeIndex = 0;
	int ProgrammeID = 0;
	String TitreProgramme = "";
	String SelectTitreProgramme;
	String Etat = "";
	int IdFrais = 0;
	int SelectEnseignantIndex = 0;
	String TitreFrais = "";
	int CoutFrais = 0;
	
	int SelectExamenIndex = 0;
	int IdProjet = 0;
	String TitreExamen = "";
	
	int SelectCourIndex = 0;
	int SelectFraisIndex = 0;
	
	
	int selectIdNotif = 0;
	int IdLog = 0;
	
	int selectedItemsGauche = 0;// Selectionner un item de la liste programmecour gauche
	int selectedItemsDroit = 0;// Selectionner un item de la liste programmeour droite
	int selectedIdGauche = 0; // Selectionner un item dans la liste gauche couretudiant
	int selectedIdDroit = 0; // Selectionner un item dans la liste droite couretudiant
	
	//section email
	int IdMessage = 0;
	int selectIdEmail = 0;
	int IdEnvoyeur = 0;
	String Sujet = "";
	String Message = "";
	String NomEnvoyeur = "";
	String NomReceveur = "";
	String date = "";
	int EtatEmail = 0;
	
	// SECTION CLASS
	Etudiant etudiant;
	Enseignant enseignant;
	Cour cour;
	Frais frais;
	Administration administration;
	AccueilController AC;
	ConnexionController CC;
	Programme programme;
	Email email;
	
	public Fonction(ConnexionController CC) {
		this.CC = CC;
	}

	public Fonction(AccueilController AC) {
		this.AC = AC;
	}
	
	ObservableListClass OLC = new ObservableListClass(this);
	
	public static String RandomPassword(String candidateChars, int length) {
		 StringBuilder sb = new StringBuilder();
		    Random random = new Random();
		    for (int i = 0; i < length; i++) {
		        sb.append(candidateChars.charAt(random.nextInt(candidateChars
		                .length())));
		    }

		    return sb.toString();
	}
	
	// Fonction connexion
	public void Connexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			CallableStatement st = ConnexionDB.prepareCall("{call selectConnexion(?,?)}");
			st.setString(1, CC.TbUser.getText());
			st.setString(2, CC.TbMDP.getText());
			rs = st.executeQuery();			

		
				if (rs.next()) {
					
					// Il manque le rs.getString(2) car on ne récupère pas le mot de passe
					administration = new Administration(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
							rs.getString(11), rs.getString(12));

					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Accueil.fxml"));
						Parent root1 = (Parent) fxmlLoader.load();
						AccueilController AC = fxmlLoader.getController();
						AC.setInfo(administration);
						Scene scene = new Scene(root1);
						Stage stage = new Stage();
						stage.getIcons().add(new Image("Photos/S.png"));
						scene.getStylesheets().add("Style.css");
						stage.initStyle(StageStyle.UNDECORATED);
						root1.setOnMousePressed(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								xOffset = event.getSceneX();
								yOffset = event.getSceneY();
							}
						});

						root1.setOnMouseDragged(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								stage.setX(event.getScreenX() - xOffset);
								stage.setY(event.getScreenY() - yOffset);
							}
						});
						stage.setScene(scene);
						stage.setTitle("");
						AC.LblMsgBienvenue.setText("Bienvenue " + AC.NomAdministrateur );
						TranslateTransition ts = new TranslateTransition(Duration.seconds(1), AC.PaneBienvenue);
						 ts.setFromY(0);
						 ts.setToY(-80);
						 ts.play();
						 
						 AC.PaneBienvenue.setVisible(true);
						 PauseTransition visiblePause = new PauseTransition(
							        Duration.seconds(3)
							);
							visiblePause.setOnFinished(
							        event -> AC.PaneBienvenue.setVisible(false)
							);
							visiblePause.play();
						stage.show();
						Stage stage1 = (Stage) CC.BtnConnexion.getScene().getWindow();
						stage1.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					

				} else {
					CC.LblMsgErreur.setText("Mauvais mot de passe ou Username");
					TranslateTransition ts = new TranslateTransition(Duration.seconds(0.5), CC.PaneErreur);
					 ts.setFromX(-330);
					 ts.setToX(-290);
					 ts.play();
					 
					 CC.PaneErreur.setVisible(true);
					 PauseTransition visiblePause = new PauseTransition(
						        Duration.seconds(3)
						);
						visiblePause.setOnFinished(
						        event -> CC.PaneErreur.setVisible(false)
						);
						visiblePause.play();
				}
			
			st.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// SECTION RECUP DONNÉES
	public int RecupNbCoursActif() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT COUNT(*) FROM Cour where Etat = 1";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			// pst.setInt(1, compteur);
			rs = pst.executeQuery();
			if (rs.next()) {
				compteur = rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compteur;
	}

	public int RecupNbCoursInactif() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT COUNT(*) FROM Cour where Etat = 0";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			// pst.setInt(1, compteur);
			rs = pst.executeQuery();
			if (rs.next()) {
				compteur = rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compteur;
	}

	public int RecupNbProgrammesActif() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT COUNT(*) FROM Programme where Etat = 1";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			// pst.setInt(1, compteur);
			rs = pst.executeQuery();
			if (rs.next()) {
				compteur = rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compteur;
	}

	public int RecupNbProgrammesInactif() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT COUNT(*) FROM Programme where Etat = 0";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			// pst.setInt(1, compteur);
			rs = pst.executeQuery();
			if (rs.next()) {
				compteur = rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compteur;
	}

	public int RecupNbEtudiantsActif() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT COUNT(*) FROM Etudiant";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			if (rs.next()) {

				compteur = rs.getInt(1);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return compteur;

	}

	public int RecupNbEtudiantsInactif() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT COUNT(*) FROM AncienEtudiant";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			if (rs.next()) {

				compteur = rs.getInt(1);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return compteur;

	}

	public int RecupNbEnseignantsActif() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT COUNT(*) FROM Enseignant";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			if (rs.next()) {
				compteur = rs.getInt(1);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return compteur;
	}

	public int RecupNbEnseignantsInactif() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT COUNT(*) FROM AncienEnseignant";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			if (rs.next()) {
				compteur = rs.getInt(1);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return compteur;
	}

	// SECTION BAR GRAPH
	public void BarGraphActifs() {
		
		AC.BarChart.getData().clear();
		
		XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
		dataSeries1.getData().add(new XYChart.Data<String, Number>("", 0));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("Enseigants", RecupNbEnseignantsActif()));
		dataSeries1.getData().add(new XYChart.Data<String, Number>(" ", 0));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("Cours", RecupNbCoursActif()));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("  ", 0));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("Étudiant", RecupNbEtudiantsActif()));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("   ", 0));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("Programme", RecupNbProgrammesActif()));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("    ", 0));

		AC.BarChart.getData().add(dataSeries1);

		for (Node n : AC.BarChart.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill:    #e85917;");

		}
	}

	public void BarGraphInactifs() {

		XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
		dataSeries1.getData().add(new XYChart.Data<String, Number>("", 0));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("Enseigants", RecupNbEnseignantsInactif()));
		dataSeries1.getData().add(new XYChart.Data<String, Number>(" ", 0));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("Cours", RecupNbCoursInactif()));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("  ", 0));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("Étudiant", RecupNbEtudiantsInactif()));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("   ", 0));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("Programme", RecupNbProgrammesInactif()));
		dataSeries1.getData().add(new XYChart.Data<String, Number>("    ", 0));

		AC.BarChartInactif.getData().add(dataSeries1);

		for (Node n : AC.BarChartInactif.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill:    #e85917;");

		}
	}

	// SECTION TABLEVIEWS
	// Tableview étudiant
	public void AfficherEtudiant() {
		AC.TableViewEtudiant.getItems().clear();
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

			RequeteSql = "Select * from Etudiant";
			PreparedStatement Pst = ConnexionDB.prepareStatement(RequeteSql);

			rs = Pst.executeQuery();
			while (rs.next()) {
				OLC.obListEtudiant.add(new Etudiant(rs.getInt("ID"), rs.getString("Utilisateur"), rs.getString("Prenom"),
						rs.getString("Nom"), rs.getString("Telephone"), rs.getString("Email"),
						rs.getString("AdresseDeMaison"), rs.getString("Ville"), rs.getString("Province"),
						rs.getString("CodePostal"), rs.getInt("IdProgramme"), rs.getString("Langue")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		AC.TcUser.setCellValueFactory(new PropertyValueFactory<>("Utilisateur"));
		AC.TcNomComplet.setCellValueFactory(new PropertyValueFactory<>("FullName"));
		AC.TcTel.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
		AC.TcEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		AC.TcAdresse.setCellValueFactory(new PropertyValueFactory<>("FullAdresse"));
		AC.TcProvince.setCellValueFactory(new PropertyValueFactory<>("Province"));
		AC.TableViewEtudiant.setItems(OLC.obListEtudiant);
		AC.TableViewEtudiant.refresh();
	}

	// TableView Enseignant
	public void AfficherEnseignant() {
		AC.TableViewEnseignant.getItems().clear();
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "Select * from Administration where Poste = 'Professeur'";
			PreparedStatement Pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = Pst.executeQuery();
			while (rs.next()) {
				OLC.obListEnseignant.add(new Administration(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		AC.TcNomCompletEnseignant.setCellValueFactory(new PropertyValueFactory<>("FullName"));
		AC.TcTelEnseignant.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
		AC.TcEmailEnseignant.setCellValueFactory(new PropertyValueFactory<>("Email"));
		AC.TcAdresseEnseignant.setCellValueFactory(new PropertyValueFactory<>("FullAdresse"));
		AC.TcProvinceEnseignant.setCellValueFactory(new PropertyValueFactory<>("Province"));
		AC.TableViewEnseignant.setItems(OLC.obListEnseignant);
		AC.TableViewEnseignant.refresh();
	}

	// TableView Cours
	public void AfficherCour() {
		AC.TableViewCours.getItems().clear();
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

			if (AC.Poste.equals("Administrateur")) {

				RequeteSql = "Select * from cour where Etat = " + AC.CourActif;

			} else if (AC.Poste.equals("Professeur")) {
				RequeteSql = "SELECT * FROM cour INNER JOIN enseignantcour ON enseignantcour.IdCour = cour.ID where enseignantcour.IdEnseignant ="
						+ AC.IDAdministrateur + " and cour.Etat = " + AC.CourActif;

			}

			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListCours.add(new Cour(rs.getInt("ID"), rs.getString("Titre"), rs.getString("Description"),
						rs.getString("TitreAnglais"), rs.getString("DescriptionAnglais"), rs.getString("Temps"),
						rs.getDouble("Cout"), rs.getInt("Credit"), rs.getInt("Etat")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		AC.TcTitreCours.setCellValueFactory(new PropertyValueFactory<>("Titre"));
		AC.TcTempsCours.setCellValueFactory(new PropertyValueFactory<>("Temps"));
		AC.TcCoutCours.setCellValueFactory(new PropertyValueFactory<>("Cout"));
		AC.TcCreditCours.setCellValueFactory(new PropertyValueFactory<>("Credit"));
		AC.TableViewCours.setItems(OLC.obListCours);
		AC.TableViewCours.refresh();

	}

	// TableView Programme

	public void AfficherProgramme() {
		AC.TableViewProgramme.getItems().clear();
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "Select * from programme where Etat = " + AC.ProgrammeActif;
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListProgramme.add(new Programme(rs.getInt("ID"), rs.getString("Titre"), rs.getString("TitreAnglais"),
						rs.getInt("Temps"), rs.getInt("Cout"), rs.getInt("Etat")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		AC.TvCProgrammeTitre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
		AC.TvCProgrammeTemps.setCellValueFactory(new PropertyValueFactory<>("Temps"));
		AC.TvCProgrammeCout.setCellValueFactory(new PropertyValueFactory<>("Cout"));
		AC.TableViewProgramme.setItems(OLC.obListProgramme);
		AC.TableViewProgramme.refresh();
	}

	public void AfficherFraisEtudiant() {

		AC.TableViewEtudiantFrais.getItems().clear();
		// SELECT etudiant.ID, concat(etudiant.Prenom, ' ' ,etudiant.Nom) AS
		// 'Nom_Complet', frais.Cout, "
		// + "frais.Type FROM `etudiant` INNER JOIN frais ON etudiant.ID =
		// frais.IdEtudiant

		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT ID, Prenom, Nom, Telephone, Email FROM `etudiant`";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListNomEtudiantFrais.add(new Etudiant(rs.getInt("ID"), rs.getString("Prenom"), rs.getString("Nom"),
						rs.getString("Telephone"), rs.getString("Email")));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TcNomCompletEtudiant.setCellValueFactory(new PropertyValueFactory<>("FullName"));
		AC.TableViewEtudiantFrais.setItems(OLC.obListNomEtudiantFrais);
		AC.TableViewEtudiantFrais.refresh();
	}

	public void AfficherFrais() {
		AC.TableViewFrais.getItems().clear();
		//

		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT frais.IdFrais, etudiant.ID, concat(etudiant.Prenom, ' ' ,etudiant.Nom) AS 'Nom_Complet', frais.Cout,  frais.Type, etat.Etat FROM `etudiant` "
					+ "INNER JOIN frais ON etudiant.ID = frais.IdEtudiant " + "INNER JOIN etat ON etat.ID = frais.Etat "
					+ "where etudiant.ID = " + IDEtudiant;
			// SELECT etat.Type, frais.Etat, frais.IdEtudiant FROM etat INNER JOIN frais ON
			// etat.ID = frais.Etat WHERE frais.IdEtudiant = 10
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.ObListFrais.add(new Frais(rs.getInt("IdFrais"), rs.getInt("ID"), rs.getInt("Cout"),
						rs.getString("Type"), rs.getString("Nom_Complet"), rs.getString("Etat")));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TcNCFrais.setCellValueFactory(new PropertyValueFactory<>("NomComplet"));
		AC.TcFFRais.setCellValueFactory(new PropertyValueFactory<>("Cout"));
		AC.TcDescriptionFrais.setCellValueFactory(new PropertyValueFactory<>("Type"));
		AC.TcEtatFrais.setCellValueFactory(new PropertyValueFactory<>("Etat"));
		AC.TableViewFrais.setItems(OLC.ObListFrais);
		AC.TableViewFrais.refresh();
	}

	public void AfficherResultatEtudiant() {
		AC.TvNomResult.getItems().clear();

		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT ID, Prenom, Nom, Telephone, Email FROM `etudiant`";
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListNomEtudiantResultat.add(new Etudiant(rs.getInt("ID"), rs.getString("Prenom"), rs.getString("Nom"),
						rs.getString("Telephone"), rs.getString("Email")));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TcNomResult.setCellValueFactory(new PropertyValueFactory<>("FullName"));
		AC.TvNomResult.setItems(OLC.obListNomEtudiantResultat);
		AC.TvNomResult.refresh();
	}

	public void AfficherResultatCours() {
		AC.TvResultatCours.getItems().clear();

		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT ID, Titre, etudiantcour.Resultat from cour INNER JOIN etudiantcour on etudiantcour.IdCour = cour.ID where etudiantcour.IdEtudiant = "
					+ IDEtudiant;
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListCourResultat.add(
						new EtudiantCour(IDEtudiant, rs.getInt("ID"), rs.getInt("Resultat"), rs.getString("Titre")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TcCoursResult.setCellValueFactory(new PropertyValueFactory<>("Titre"));
		AC.TcResultat.setCellValueFactory(new PropertyValueFactory<>("Resultat"));
		AC.TvResultatCours.setItems(OLC.obListCourResultat);
		AC.TvResultatCours.refresh();
	}

	public void AfficherExamen() {
		AC.TableViewExamen.getItems().clear();
		
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT * FROM `coursdetails` INNER JOIN enseignant ON coursdetails.IdEnseignant = enseignant.ID where coursdetails.IdEnseignant = " + AC.IDAdministrateur ;
					
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListExamen.add(
						new Examen(rs.getInt("IdEvalutation"), rs.getInt("IdEnseignant"), rs.getInt("IdCours"),
						rs.getString("Titre"), rs.getString("Description"), rs.getInt("Ponctuation"), rs.getString("Prenom"), rs.getString("Nom"), "", 0));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TableColumnTitreExam.setCellValueFactory(new PropertyValueFactory<>("Titre"));
		AC.TableColumDescExam.setCellValueFactory(new PropertyValueFactory<>("Description"));
		AC.TableColumnPoncExam.setCellValueFactory(new PropertyValueFactory<>("Ponctuation"));
		AC.TableColumnProfExam.setCellValueFactory(new PropertyValueFactory<>("NomComplet"));
		AC.TableViewExamen.setItems(OLC.obListExamen);
		AC.TableViewExamen.refresh();
	}
	
	/*
	 * Affiche les examens d'un etudiant selon le cour choisi
	 */
	public void AfficherExamenEtudiant() {
		AC.TableViewNoteExam.getItems().clear();
		
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT etudiantevaluation.IdEtudiant, etudiantevaluation.IdCours, etudiantevaluation.IdEvalutation, etudiantevaluation.ResultatFinal, "
					+ "etudiant.Prenom, etudiant.Nom, cour.Titre, coursdetails.Titre as TitreEvaluation, coursdetails.IdEnseignant, coursdetails.Description, coursdetails.Ponctuation"
					+ " FROM `etudiantevaluation` "
					+ "inner join etudiant on IdEtudiant = etudiant.ID INNER JOIN cour on IdCours = cour.ID inner join coursdetails on "
					+ "etudiantevaluation.IdEvalutation = coursdetails.IdEvalutation where etudiantevaluation.IdEtudiant = ? and etudiantevaluation.IdCours = ?";
					
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			pst.setInt(1, IDEtudiant);
			pst.setInt(2, IDCour);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListExamen.add(
						new Examen(rs.getInt("IdEvalutation"), rs.getInt("IdEnseignant"), rs.getInt("IdCours"),
						rs.getString("Titre"), rs.getString("Description"), rs.getInt("Ponctuation"), rs.getString("Prenom"), rs.getString("Nom"), rs.getString("TitreEvaluation"),
						rs.getInt("ResultatFinal")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TcResultNoteExam.setCellValueFactory(new PropertyValueFactory<>("Resultat"));
		AC.TcEvalNoteExam.setCellValueFactory(new PropertyValueFactory<>("TitreEvaluation"));
		AC.TcCourNoteExam.setCellValueFactory(new PropertyValueFactory<>("Titre"));
		AC.TableViewNoteExam.setItems(OLC.obListExamen);
		AC.TableViewNoteExam.refresh();
	}
	
	/*
	 * Affiche les examens d'un cours precis
	 */
	public void AfficherExamenCours() {
		AC.TableViewExamen.getItems().clear();
		
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT * FROM `coursdetails` INNER JOIN enseignant ON coursdetails.IdEnseignant = enseignant.ID where coursdetails.IdEnseignant = " + AC.IDAdministrateur + " AND IdCours = " +  IDCour;
					
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListExamen.add(
						new Examen(rs.getInt("IdEvalutation"), rs.getInt("IdEnseignant"), rs.getInt("IdCours"),
						rs.getString("Titre"), rs.getString("Description"), rs.getInt("Ponctuation"), rs.getString("Prenom"), rs.getString("Nom"), "", 0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TableColumnTitreExam.setCellValueFactory(new PropertyValueFactory<>("Titre"));
		AC.TableColumDescExam.setCellValueFactory(new PropertyValueFactory<>("Description"));
		AC.TableColumnPoncExam.setCellValueFactory(new PropertyValueFactory<>("Ponctuation"));
		AC.TableColumnProfExam.setCellValueFactory(new PropertyValueFactory<>("NomComplet"));
		AC.TableViewExamen.setItems(OLC.obListExamen);
		AC.TableViewExamen.refresh();
	}
	
	public void AfficherNotification() {
		boolean Tr = false;
		AC.TableViewNotif.getItems().clear();
		PreparedStatement pst = null;
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");			
			AC.BtnNotifLu.setDisable(false);
			AC.BtnNotifNonLu.setDisable(false);
			AC.RondNotif.setVisible(false);
			
			//Afficher les notifications lu
			if(AC.EtatNotif == 1) {
				//Notif non lu
				AC.BtnNotifNonLu.setDisable(true);
				
				RequeteSql = "select * from tablenotification INNER JOIN logupdatedeleteaccount ON IdLog = ID where IdAdmin = ? && Etat = 1" ;
				pst = ConnexionDB.prepareStatement(RequeteSql);
				pst.setInt(1, AC.IDAdministrateur);
				rs = pst.executeQuery();
				
				while (rs.next()) {
					OLC.obListNotif.add(new Notification(rs.getInt("IdNotification"), rs.getString("Auteur"), rs.getInt("IdLog"),
							rs.getInt("IdAdmin"), rs.getInt("Etat"), rs.getString("Type1"), rs.getString("Date1"), rs.getString("Table1")));
					Tr = true;
					AC.RondNotif.setVisible(false);
				}
				AC.RondNotif.setVisible(Tr);
			}
			//Afficher les notifications non lu
			else if(AC.EtatNotif == 0) {
				
				AC.BtnNotifLu.setDisable(true);
				//Notif Lu
				RequeteSql = "select * from tablenotification INNER JOIN logupdatedeleteaccount ON IdLog = ID where IdAdmin = ? && Etat = 0" ;
				pst = ConnexionDB.prepareStatement(RequeteSql);
				pst.setInt(1, AC.IDAdministrateur);
				rs = pst.executeQuery();
				
				while (rs.next()) {
					OLC.obListNotif.add(new Notification(rs.getInt("IdNotification"), rs.getString("Auteur"), rs.getInt("IdLog"),
							rs.getInt("IdAdmin"), rs.getInt("Etat"), rs.getString("Type1"), rs.getString("Date1"), rs.getString("Table1")));
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		AC.TableColumnNotifAuteur.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
		AC.TableColumnNotifType.setCellValueFactory(new PropertyValueFactory<>("Type"));
		AC.TableColumnNotifTable.setCellValueFactory(new PropertyValueFactory<>("Table"));
		AC.TableColumnNotifDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		AC.TableViewNotif.setItems(OLC.obListNotif);
		AC.TableViewNotif.refresh();
	}

	public void AfficherLogs() {
		AC.TableView_Logs.getItems().clear();
		
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT * FROM logupdatedeleteaccount";
					
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListLogs.add(
						new Logs(rs.getString("Createur"), rs.getString("AncienneValeur"), rs.getString("NouvelleValeur"),
						rs.getString("Table1"), rs.getString("Type1"), rs.getString("Date1")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TableColumn_Createur.setCellValueFactory(new PropertyValueFactory<>("Createur"));
		AC.TableColumn_AV.setCellValueFactory(new PropertyValueFactory<>("AncienneValeur"));
		AC.TableColumn_NV.setCellValueFactory(new PropertyValueFactory<>("NouvelleValeur"));
		AC.TableColumn_Table.setCellValueFactory(new PropertyValueFactory<>("Table1"));
		AC.TableColumn_Type.setCellValueFactory(new PropertyValueFactory<>("Type1"));
		AC.TableColumn_Date.setCellValueFactory(new PropertyValueFactory<>("Date1"));
		AC.TableView_Logs.setItems(OLC.obListLogs);
		AC.TableView_Logs.refresh();
	}
	
	//Affiche les notifications avec plus de details
	public void AfficherNotifDetail() {
		AC.TableViewNotifDetail.getItems().clear();
		
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "SELECT Createur, AncienneValeur, NouvelleValeur, Table1, Type1, Date1 FROM `logupdatedeleteaccount` inner join tablenotification on ID = tablenotification.IdLog "
					+ "where tablenotification.Etat = " + AC.EtatNotif + " and tablenotification.IdAdmin = " + AC.IDAdministrateur;		
			PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListNotifDetails.add(
						new Logs(rs.getString("Createur"), rs.getString("AncienneValeur"), rs.getString("NouvelleValeur"),
						rs.getString("Table1"), rs.getString("Type1"), rs.getString("Date1")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TcCreateurNotifDetail.setCellValueFactory(new PropertyValueFactory<>("Createur"));
		AC.TcAvNotifDetail.setCellValueFactory(new PropertyValueFactory<>("AncienneValeur"));
		AC.TcNvNotifDetail.setCellValueFactory(new PropertyValueFactory<>("NouvelleValeur"));
		AC.TcTableNotifDetail.setCellValueFactory(new PropertyValueFactory<>("Table1"));
		AC.TcTypeNotifDetail.setCellValueFactory(new PropertyValueFactory<>("Type1"));
		AC.TcDateNotifDetail.setCellValueFactory(new PropertyValueFactory<>("Date1"));
		AC.TableViewNotifDetail.setItems(OLC.obListNotifDetails);
		AC.TableViewNotifDetail.refresh();
	}
	
	public void AfficherNomEmail() {
		AC.TableView_Email.getItems().clear();
		
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			//iMessage = 0 (Message non lu) ou iMessageLu = 1 (Message lu)
			if(AC.iMessageLu != 2 && AC.iMessageLu != 3) {
				int MessageCount = 0;
				
				RequeteSql = "SELECT IdMessage, concat(administration.Prenom, ' ' ,administration.nom) as NomComplet, IdEnvoyeur, IdReceveur, Sujet, Message, Date, Etat, IdReponse"
						+ " FROM `clavardage` INNER JOIN administration ON administration.ID = IdEnvoyeur where IdReceveur = " + AC.IDAdministrateur + " and Etat = ?"; 
				PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
				pst.setInt(1, AC.iMessageLu);
				rs = pst.executeQuery();
				while (rs.next()) {
					
					if(AC.iMessageLu == 0) {
						MessageCount += 1;
					}
					
					OLC.obListEmail.add(
							new Email(rs.getInt("IdMessage"), rs.getString("NomComplet"), rs.getInt("IdEnvoyeur"),
							rs.getInt("IdReceveur"), rs.getString("Sujet"), rs.getString("Message"), rs.getString("Date"), rs.getInt("Etat"), rs.getInt("IdReponse")));
					
				}
				AC.LblCountMessage.setText(""+MessageCount);
			}
			//iMessageLu = 2 (Nouveau message)
			else if(AC.iMessageLu == 2){
				RequeteSql = "SELECT concat(Prenom, ' ' ,Nom) as NomComplet, ID FROM administration";
				PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
				rs = pst.executeQuery();
				while (rs.next()) {
					
					OLC.obListEmail.add(
							new Email(rs.getString("NomComplet"), rs.getInt("ID")));
				}
			}
			//iMessage = 3 (Message envoyé)
			else if(AC.iMessageLu == 3) {
				RequeteSql = "SELECT IdMessage, concat(administration.Prenom, ' ' ,administration.nom) as NomComplet, IdEnvoyeur, IdReceveur, Sujet, Message, Date, Etat, IdReponse "
						+ "FROM `clavardage` INNER JOIN administration ON administration.ID = IdReceveur where IdEnvoyeur = " + AC.IDAdministrateur; 
				PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
				rs = pst.executeQuery();
				while (rs.next()) {
				
					OLC.obListEmail.add(
							new Email(rs.getInt("IdMessage"), rs.getString("NomComplet"), rs.getInt("IdEnvoyeur"),
							rs.getInt("IdReceveur"), rs.getString("Sujet"), rs.getString("Message"), rs.getString("Date"), rs.getInt("Etat"), rs.getInt("IdReponse")));
					
				}
			}
			//iMessage = -1 (Message supprimé)
			else if(AC.iMessageLu == -1) {
				RequeteSql = "SELECT IdMessage, concat(administration.Prenom, ' ' ,administration.nom) as NomComplet, IdEnvoyeur, IdReceveur, Sujet, Message, Date, Etat, IdReponse "
						+ "FROM `clavardage` INNER JOIN administration ON administration.ID = IdEnvoyeur where IdReceveur = " + AC.IDAdministrateur + " and Etat = -1"; 
				PreparedStatement pst = ConnexionDB.prepareStatement(RequeteSql);
				rs = pst.executeQuery();
				while (rs.next()) {
				
					OLC.obListEmail.add(
							new Email(rs.getInt("IdMessage"), rs.getString("NomComplet"), rs.getInt("IdEnvoyeur"),
							rs.getInt("IdReceveur"), rs.getString("Sujet"), rs.getString("Message"), rs.getString("Date"), rs.getInt("Etat"), rs.getInt("IdReponse")));
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AC.TableColumn_Envoyeur.setCellValueFactory(new PropertyValueFactory<>("NomComplet"));		
		AC.TableView_Email.setItems(OLC.obListEmail);
		AC.TableView_Email.refresh();
	}
	
	
	// SECTION SELECT TABLE VIEW
	public void SelectRowEtudiant() {
		AC.TableViewEtudiant.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (OLC.obListEtudiant.isEmpty() == false) {
					etudiant = AC.TableViewEtudiant.getItems()
							.get(AC.TableViewEtudiant.getSelectionModel().getSelectedIndex());
					NomEtudiant = AC.TableViewEtudiant.getSelectionModel().getSelectedItem().getFullName();
					TelEtudiant = AC.TableViewEtudiant.getSelectionModel().getSelectedItem().getTelephone();
					EmailEtudiant = AC.TableViewEtudiant.getSelectionModel().getSelectedItem().getEmail();

					SelectEtudiantIndex = AC.TableViewEtudiant.getSelectionModel().getSelectedIndex();
					IDEtudiant = OLC.obListEtudiant.get(SelectEtudiantIndex).getID();

					AC.LblNomEtudiant.setText(NomEtudiant);
					AC.LblTelEtudiant
							.setText(String.valueOf(TelEtudiant).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3"));
					AC.LblEmailEtudiant.setText(EmailEtudiant);

					if (SelectEtudiantIndex > -1) {
						AC.LblNomEtudiantProg.setText(NomEtudiant);
						AC.BtnSupEtudiant.setDisable(false);
						AC.BtnModifierEtudiant.setDisable(false);
						AC.BtnInscription.setDisable(false);
						AC.BtnAssignerProgramme.setDisable(false);
					}
				}
			}
		});
	}

	public void SelectRowEtudiantFrais() {
		AC.TableViewEtudiantFrais.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (OLC.obListNomEtudiantFrais.isEmpty() == false) {
					etudiant = AC.TableViewEtudiantFrais.getItems()
							.get(AC.TableViewEtudiantFrais.getSelectionModel().getSelectedIndex());

					SelectEtudiantIndex = AC.TableViewEtudiantFrais.getSelectionModel().getSelectedIndex();
					IDEtudiant = OLC.obListNomEtudiantFrais.get(SelectEtudiantIndex).getID();
					NomEtudiant = AC.TableViewEtudiantFrais.getSelectionModel().getSelectedItem().getFullName();
					TelEtudiant = AC.TableViewEtudiantFrais.getSelectionModel().getSelectedItem().getTelephone();
					EmailEtudiant = AC.TableViewEtudiantFrais.getSelectionModel().getSelectedItem().getEmail();
					System.out.println(IDEtudiant);
					AC.LblNomFrais.setText(NomEtudiant);
					AC.LblTelephoneFrais
							.setText(String.valueOf(TelEtudiant).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3"));
					AC.LblEmailFrais.setText(EmailEtudiant);

					if (SelectEtudiantIndex > -1) {
						AC.BtnAfficherFrais.setDisable(false);
					}
				}
			}
		});
	}

	public void SelectRowEnseignant() {
		
		
			AC.TableViewEnseignant.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					try {
						if (OLC.obListEnseignant.isEmpty() == false) {
							administration = AC.TableViewEnseignant.getItems()
									.get(AC.TableViewEnseignant.getSelectionModel().getSelectedIndex());
							IDEnseignant = OLC.obListEnseignant.get(SelectEnseignantIndex).getID();

							NomEnseignant = AC.TableViewEnseignant.getSelectionModel().getSelectedItem().getFullName();
							TelEnseignant = AC.TableViewEnseignant.getSelectionModel().getSelectedItem().getTelephone();
							EmailEnseignant = AC.TableViewEnseignant.getSelectionModel().getSelectedItem().getEmail();
							AC.LblNomEnseignant.setText(NomEnseignant);
							AC.LblTelEnseignant.setText(TelEnseignant);
							AC.LblEmailEnseignant.setText(EmailEnseignant);

							SelectEnseignantIndex = AC.TableViewEnseignant.getSelectionModel().getSelectedIndex();
							if (SelectEnseignantIndex > -1) {
								AC.BtnAssignerProgEns.setDisable(false);
								AC.BtnSupEnseignant.setDisable(false);
								AC.BtnModifierEnseignant.setDisable(false);
							}
						}
					}
					catch(Exception e) {
						System.out.println("Erreur");
					}
				}
			});
		}
	
	public void SelectRowCour() {
			AC.TableViewCours.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (OLC.obListCours.isEmpty() == false) {
						cour = AC.TableViewCours.getItems().get(AC.TableViewCours.getSelectionModel().getSelectedIndex());

						SelectCourIndex = AC.TableViewCours.getSelectionModel().getSelectedIndex();
						TitreCours = AC.TableViewCours.getSelectionModel().getSelectedItem().getTitre();
						IDCour = AC.TableViewCours.getSelectionModel().getSelectedItem().getID();

						if (AC.CourActif == 1) {
							if (SelectCourIndex > -1) {
								AC.BtnSupCours.setDisable(false);
								AC.BtnModifierCours.setDisable(false);
								AC.BtnAjouterExam.setDisable(false);
								AC.BtnAfficherExamenCours.setDisable(false);
								AC.LblTitreCoursExam.setText(TitreCours);
							}
						} else if (AC.CourActif == 0) {
							AC.BtnSupCours.setDisable(true);
							AC.BtnModifierCours.setDisable(false);
							AC.BtnAjouterExam.setDisable(true);
						}
					}
				}
			});
	}

	public void SelectRowProgramme() {
		AC.TableViewProgramme.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (OLC.obListProgramme.isEmpty() == false) {
					programme = AC.TableViewProgramme.getItems()
							.get(AC.TableViewProgramme.getSelectionModel().getSelectedIndex());
					SelectProgrammeIndex = AC.TableViewProgramme.getSelectionModel().getSelectedIndex();
					ProgrammeID = OLC.obListProgramme.get(SelectProgrammeIndex).getID();
					TitreProgramme = OLC.obListProgramme.get(SelectProgrammeIndex).getTitre();
					SelectTitreProgramme = AC.TableViewProgramme.getSelectionModel().getSelectedItem().getTitre();

					if (AC.ProgrammeActif == 1) {
						if (SelectProgrammeIndex > -1) {

							AC.LblProgrammeSelectionner.setText(SelectTitreProgramme);
							AC.BtnSupProgramme.setDisable(false);
							AC.BtnModifierProgramme.setDisable(false);
							AC.BtnAssignerCoursProgramme.setDisable(false);
						}
					} else if (AC.ProgrammeActif == 0) {
						AC.BtnSupProgramme.setDisable(true);
						AC.BtnAssignerCoursProgramme.setDisable(true);
						AC.BtnModifierProgramme.setDisable(false);
					}
				}
			}
		});
	}

	public void SelectRowFrais() {
		AC.TableViewFrais.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (OLC.ObListFrais.isEmpty() == false) {

					frais = AC.TableViewFrais.getItems().get(AC.TableViewFrais.getSelectionModel().getSelectedIndex());
					Etat = AC.TableViewFrais.getSelectionModel().getSelectedItem().getEtat();
					TitreFrais = AC.TableViewFrais.getSelectionModel().getSelectedItem().getType();
					IdFrais = AC.TableViewFrais.getSelectionModel().getSelectedItem().getIdFrais();
					SelectFraisIndex = AC.TableViewFrais.getSelectionModel().getSelectedIndex();
					CoutFrais = AC.TableViewFrais.getSelectionModel().getSelectedItem().getCout();
					
					if (SelectFraisIndex > -1) {

						AC.BtnSupprimerFrais.setDisable(false);
						AC.BtnModifierFrais.setDisable(false);
					}
				}
			}
		});
	}

	public void SelectRowNomResultat() {
		AC.TvNomResult.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try{
					if (OLC.obListNomEtudiantResultat.isEmpty() == false) {

						IDEtudiant = AC.TvNomResult.getSelectionModel().getSelectedItem().getID();
						NomEtudiant = AC.TvNomResult.getSelectionModel().getSelectedItem().getFullName();
						SelectEtudiantIndex = AC.TvNomResult.getSelectionModel().getSelectedIndex();
						System.out.println(IDEtudiant);
						if (SelectEtudiantIndex > -1) {
							// R'ajouter les boutons qui sont disable
							AC.BtnAfficherResult.setDisable(false);
							AC.LblNomNoteExam.setText(NomEtudiant);							
						}
					}
				}
				catch(Exception e) {
					System.out.println("Veuillez choisir une rangé");
				}
			}
		});
	}

	public void SelectRowResultat() {
		AC.TvResultatCours.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					if (OLC.obListNomEtudiantResultat.isEmpty() == false) {
						IDCour = AC.TvResultatCours.getSelectionModel().getSelectedItem().getIdCour();
						nomCour = AC.TvResultatCours.getSelectionModel().getSelectedItem().getTitre();
						SelectCourIndex = AC.TvResultatCours.getSelectionModel().getSelectedIndex();
						System.out.println(IDCour);
						if (SelectCourIndex > -1) {
							// R'ajouter les boutons qui sont disable
							AC.LblCourChoisi.setText(nomCour);
							AC.BtnModifierResultat.setDisable(false);
							AC.BtnSupprimerResultat.setDisable(false);
							AC.BtnAfficherEvalutation.setDisable(false);
						}
					}
				}
				catch(Exception e) {
					System.out.println("Veuillez choisir une rangé");
				}
			}
		});
	}
	
	public void SelectRowExamen() {
		AC.TableViewExamen.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					if (OLC.obListExamen.isEmpty() == false) {
						
						TitreExamen = AC.TableViewExamen.getSelectionModel().getSelectedItem().getTitre();
						IdProjet = AC.TableViewExamen.getSelectionModel().getSelectedItem().getIdProjet();
						SelectExamenIndex = AC.TableViewExamen.getSelectionModel().getSelectedIndex();
						if (SelectExamenIndex > -1) {
							// R'ajouter les boutons qui sont disable
							
							AC.BtnModifierExam.setDisable(false);
							AC.BtnSupprimerExam.setDisable(false);
						
						}
					}
				}
				catch(Exception e) {
					System.out.println("Veuillez cliquer sur un examen");
					
				}
				
			}
		});
	}
 
	public void SelectRowExamenEtudiant() {
			AC.TableViewNoteExam.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					if (OLC.obListExamen.isEmpty() == false) {
						
						TitreEvaluation = AC.TableViewNoteExam.getSelectionModel().getSelectedItem().getTitre();
						IdTitreEvaluation = AC.TableViewNoteExam.getSelectionModel().getSelectedItem().getIdProjet();
						SelectExamEtudiantIndex = AC.TableViewNoteExam.getSelectionModel().getSelectedIndex();
						if (SelectExamEtudiantIndex > -1) {
							// R'ajouter les boutons qui sont disable							
							AC.BtnAjoutNoteExam.setDisable(false);
							AC.PaneAjouterNote.setVisible(true);
						}
					}
				}
				catch(Exception e) {
					System.out.println("Veuillez cliquer sur un examen");
					
				}
				
			}
		});
	}
	
	public void SelectRowNotif() {
		AC.TableViewNotif.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					if (OLC.obListNotif.isEmpty() == false) {
						
						IdLog = AC.TableViewNotif.getSelectionModel().getSelectedItem().getIdLog();
						selectIdNotif = AC.TableViewNotif.getSelectionModel().getSelectedIndex();
						System.out.println(IdLog);
						if (selectIdNotif > -1) {
							// R'ajouter les boutons qui sont disable
							if(AC.CheckBoxNotifNL.isSelected()) {
								AC.BtnNotifLu.setDisable(false);
							}
							else if(AC.CheckBoxNotifL.isSelected()) {
								AC.BtnNotifNonLu.setDisable(false);
							}
							
							AC.BtnNotifSup.setDisable(false);
						}
					}
				}
				catch(Exception e) {
					System.out.println("Veuillez cliquer sur une notification");
					
				}
				
			}
		});
	}
	
	public void SelectEmail() {
		
		AC.TableView_Email.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					if (!OLC.obListEmail.isEmpty()) {
						
						IdMessage = AC.TableView_Email.getSelectionModel().getSelectedItem().getIdMessage();
						IdEnvoyeur = AC.TableView_Email.getSelectionModel().getSelectedItem().getIdEnvoyeur();
						selectIdEmail = AC.TableView_Email.getSelectionModel().getSelectedIndex();
						Sujet = AC.TableView_Email.getSelectionModel().getSelectedItem().getSujet();
						Message = AC.TableView_Email.getSelectionModel().getSelectedItem().getMessage();
						NomEnvoyeur = AC.TableView_Email.getSelectionModel().getSelectedItem().getNomComplet();
						date = AC.TableView_Email.getSelectionModel().getSelectedItem().getDate();
						EtatEmail = AC.TableView_Email.getSelectionModel().getSelectedItem().getEtat();
						//String pattern = "dd MMMM yyyy";
						//SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("fr", "FR"));
						AC.Btn_SupEmail.setDisable(false);
						if(AC.iMessageLu == 2) {
							AC.Tb_Object.setText(Sujet);
							AC.Tb_Message.setText(Message);
							AC.Tb_Message.setEditable(true);
							AC.LblEnvoyeur.setText(AC.NomAdministrateur);
							AC.LblReceveur.setText(NomEnvoyeur);
							AC.LblDate.setText(date);
							AC.Btn_EnvoyerMessage.setText("Envoyer");
							AC.Btn_EnvoyerMessage.setLayoutY(480);
							
							AC.Btn_EnvoyerMessage.setVisible(true);
						}
						else if(AC.iMessageLu == 1){
							AC.Tb_Object.setText(Sujet);
							AC.Tb_Message.setText(Message);
							AC.Tb_Message.setEditable(false);
							AC.LblEnvoyeur.setText(NomEnvoyeur);
							AC.LblReceveur.setText(AC.NomAdministrateur);
							AC.LblDate.setText(date);
							AC.Btn_EnvoyerMessage.setText("Répondre");
							AC.Btn_EnvoyerMessage.setLayoutY(792);
							AC.Tb_MessageReponse.setVisible(true);
							AC.Btn_EnvoyerMessage.setVisible(true);
						}
						else if(AC.iMessageLu == 3) {
							AC.Tb_Object.setText(Sujet);
							AC.Tb_Message.setText(Message);
							AC.Tb_Message.setEditable(false);
							AC.LblEnvoyeur.setText(NomEnvoyeur);
							AC.LblReceveur.setText(AC.NomAdministrateur);
							AC.LblDate.setText(date);
							AC.Btn_EnvoyerMessage.setText("Répondre");
							AC.Btn_EnvoyerMessage.setLayoutY(792);
							AC.Tb_MessageReponse.setVisible(true);
							AC.Btn_EnvoyerMessage.setVisible(true);
						}
						else if(AC.iMessageLu == -1) {
							AC.Tb_Object.setText(Sujet);
							AC.Tb_Message.setText(Message);
							AC.Tb_Message.setEditable(false);
							AC.Tb_MessageReponse.setVisible(false);
							AC.LblEnvoyeur.setText(NomEnvoyeur);
							AC.LblReceveur.setText(AC.NomAdministrateur);
							AC.LblDate.setText(date);
							AC.Btn_EnvoyerMessage.setVisible(true);
							AC.Btn_EnvoyerMessage.setText("Transférer");
							AC.Btn_EnvoyerMessage.setLayoutY(480);
							
						}
					}
				}
				catch(Exception e) {
					System.out.println("Veuillez cliquer sur une notification");
				}
			}
		});
	}
	
	public void InsertNotif() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			String NomAuteur = "";
			int IdLog = 0;
			ArrayList<Integer> IdsLogs = new ArrayList<>();
			
			RequeteSql = "SELECT ID, Createur FROM `logupdatedeleteaccount` where NewAdd = 1";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				NomAuteur = rs.getString("Createur");
				
				IdsLogs.add(rs.getInt("ID"));	
				System.out.println("Nom auteur " + NomAuteur + " IdLog " + IdLog);
			}
			
			int IdAdmin = 0;
			RequeteSql = "SELECT * FROM `administration` WHERE Poste = 'Administrateur'";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			rs = preparedStatement.executeQuery();
			System.out.println("ArrayList " + IdsLogs.size());
			while(rs.next()) {
				IdAdmin = rs.getInt("ID");
				for(int i = 0; i < IdsLogs.size(); i++) {
					IdLog = IdsLogs.get(i);
					RequeteSql = "insert into tablenotification (Auteur, IdLog, IdAdmin, Etat) values (?,?,?,?)";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setString(1, NomAuteur);
					preparedStatement.setInt(2, IdLog);
					preparedStatement.setInt(3, IdAdmin);
					preparedStatement.setInt(4, 1);
					preparedStatement.executeUpdate();
					System.out.println(RequeteSql);
				}
			}
			
			RequeteSql = "Update logupdatedeleteaccount set NewAdd = 0";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// SECTION INSERT
	// Ajouter Enseignant
	public void InsertEnseignant() {
		int IdEnseignant = 0;
		if (AC.TbUser.getText().contentEquals("") || AC.TbPrenom.getText().contentEquals("")
				|| AC.TbNom.getText().contentEquals("") || AC.TbTel.getText().contentEquals("")
				|| AC.TbEmail.getText().contentEquals("") || AC.TbAdresse.getText().contentEquals("")
				|| AC.TbVille.getText().contentEquals("") || AC.TbCodePostal.getText().contentEquals("")
				|| AC.TbProvince.getText().contentEquals("")) {
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Erreur");
			AC.LblMsg.setText("Veuillez remplir tout les champs. Réessayer plus tard.");
		} else {
			try {

				PreparedStatement preparedStatement = null;
				Class.forName("com.mysql.jdbc.Driver");
				ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
				RequeteSql = "Select * from Administration where NomUtilisateur = ? or Email = ? or Telephone = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setString(1, AC.TbUser.getText());
				preparedStatement.setString(2, AC.TbEmail.getText());
				preparedStatement.setString(3, AC.TbTel.getText());
				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					AC.LblMsgErreurEtudiant.setVisible(true);
					AC.LblMsgErreurEtudiant.setText("Erreur, veuillez recommencer.");
					AC.LblMsgErreurEtudiant.setStyle("-fx-text-fill:Red");
				} else {

					RequeteSql = "Insert into administration (NomUtilisateur, MotDepasse, Prenom, Nom, Telephone,Email,"
							+ "AdresseDeMaison, Ville, CodePostal, Province, Poste) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setString(1, AC.TbUser.getText());
					preparedStatement.setString(2, "123");
					preparedStatement.setString(3, AC.TbPrenom.getText());
					preparedStatement.setString(4, AC.TbNom.getText());
					preparedStatement.setString(5, AC.TbTel.getText());
					preparedStatement.setString(6, AC.TbEmail.getText());
					preparedStatement.setString(7, AC.TbAdresse.getText());
					preparedStatement.setString(8, AC.TbVille.getText());
					preparedStatement.setString(9, AC.TbCodePostal.getText());
					preparedStatement.setString(10, AC.TbProvince.getText());
					preparedStatement.setString(11, AC.CbPosteAdmin.getValue());

					preparedStatement.executeUpdate();
					
					RequeteSql = "Select max(ID) from administration";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					rs = preparedStatement.executeQuery();
					int last_IdAdmin = 0;
					if (rs.next()) {
						last_IdAdmin = rs.getInt(1);
					}

					CallableStatement st = ConnexionDB.prepareCall("{call InsertAdminPassWord(?,?)}");
					st.setString(1, RandomPassword("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 20));
					st.setInt(2, last_IdAdmin);					
					st.execute();
					st.close();
					System.out.println(RandomPassword("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 20));
					try {
						if (AC.CbPosteAdmin.getValue().equals("Professeur")) {
							
							RequeteSql = "Select max(ID) from administration";
							preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
							rs = preparedStatement.executeQuery();
							if (rs.next()) {
								IdEnseignant = rs.getInt(1);
							}

							RequeteSql = "Insert into enseignant (ID, NomUtilisateur, Prenom, Nom) VALUES (?, ?, ?, ?)";
							preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
							preparedStatement.setInt(1, IdEnseignant);
							preparedStatement.setString(2, AC.TbUser.getText());
							preparedStatement.setString(3, AC.TbPrenom.getText());
							preparedStatement.setString(4, AC.TbNom.getText());
							preparedStatement.executeUpdate();
							
							/*Section LOG*/
							//On ajoute dans les logs, le nom de l'utilisateur ajouter, le nom du createur, typeAccount, DateAjout
						
							RequeteSql = "Insert into LogCreatedAccount (Createur, IdUtilisateur, TypeAccount, DateAjout) "
									+ "values (?,?,?,?)";
							preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
							preparedStatement.setString(1, AC.NomAdministrateur);
							preparedStatement.setInt(2, IdEnseignant);
							preparedStatement.setString(3, "Professeur");
							preparedStatement.setString(4, currentTime);
							preparedStatement.executeUpdate();
						}

						ConnexionDB.close();

						AC.PaneBlack.setVisible(true);
						AC.PaneMsg.setVisible(true);
						AC.LblTitreMsg.setText("Enseignant ajouté avec succès");
						AC.LblMsg.setText("Retour à l'accueil");
						AfficherEnseignant();
						ClearInfo();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Ajouter Etudiant
	public void InsertEtudiant() {
		if (AC.TbUser.getText().contentEquals("") || AC.TbPrenom.getText().contentEquals("")
				|| AC.TbNom.getText().contentEquals("") || AC.TbTel.getText().contentEquals("")
				|| AC.TbEmail.getText().contentEquals("") || AC.TbAdresse.getText().contentEquals("")
				|| AC.TbVille.getText().contentEquals("") || AC.TbCodePostal.getText().contentEquals("")
				|| AC.TbProvince.getText().contentEquals("")) {

			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Erreur");
			AC.LblMsg.setText("Veuillez remplir tout les champs. Réessayer plus tard.");
		} else {
			try {

				PreparedStatement preparedStatement = null;
				Class.forName("com.mysql.jdbc.Driver");
				ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
				RequeteSql = "Select * from etudiant where Utilisateur = ? or Email = ? or Telephone = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setString(1, AC.TbUser.getText());
				preparedStatement.setString(2, AC.TbEmail.getText());
				preparedStatement.setString(3, AC.TbTel.getText());
				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					AC.LblMsgErreurEtudiant.setVisible(true);
					AC.LblMsgErreurEtudiant.setText("Erreur, veuillez recommencer.");
					AC.LblMsgErreurEtudiant.setStyle("-fx-text-fill:Red");
				} else {

					RequeteSql = "Insert into etudiant (Utilisateur, Prenom, Nom, Telephone,Email,"
							+ "AdresseDeMaison, Ville, Province, CodePostal, IdProgramme, Langue) VALUES (?, ?, ?, ?, ?, ? ,?, ?,?,?,?)";
					try {
						int LastIdEtudiant = 0; // récupère le dernière ID ajouté dans la table étudiant

						//récupère le ID du programme sélectionné
						int IdProgramme = Integer.parseInt(AC.ComboBoxProgramme.getValue().substring(0, 2).trim());

						preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
						preparedStatement.setString(1, AC.TbUser.getText());
						preparedStatement.setString(2, AC.TbPrenom.getText());
						preparedStatement.setString(3, AC.TbNom.getText());
						preparedStatement.setString(4, AC.TbTel.getText());
						preparedStatement.setString(5, AC.TbEmail.getText());
						preparedStatement.setString(6, AC.TbAdresse.getText());
						preparedStatement.setString(7, AC.TbVille.getText());
						preparedStatement.setString(8, AC.TbProvince.getText());
						preparedStatement.setString(9, AC.TbCodePostal.getText());
						preparedStatement.setInt(10, IdProgramme);
						preparedStatement.setString(11, AC.CbLangueEtudiant.getValue());
						preparedStatement.executeUpdate();

						RequeteSql = "Select max(ID) from etudiant";
						preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
						rs = preparedStatement.executeQuery();
						if (rs.next()) {
							LastIdEtudiant = rs.getInt(1);
						}

						// for(int i = 0; )
						int TotalCout = 0;
						RequeteSql = "SELECT ID, Cout FROM cour INNER JOIN programmecours ON cour.ID = programmecours.IdCours where programmecours.IdProgramme = ? order by Ordre";
						preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
						preparedStatement.setInt(1, IdProgramme);
						rs = preparedStatement.executeQuery();
						while (rs.next()) {
							TotalCout += rs.getInt("Cout");

							RequeteSql = "Insert into etudiantcour (IdEtudiant, IdCour, Resultat) values (?,?,?)";
							preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
							preparedStatement.setInt(1, LastIdEtudiant);
							preparedStatement.setInt(2, rs.getInt("ID"));
							preparedStatement.setInt(3, 0);
							preparedStatement.executeUpdate();
						}

						if (IdProgramme == 0) {
							System.out.println("Aucun programme choisi, donc aucun frais");
						} else {
							RequeteSql = "Insert into frais (IdEtudiant, Cout, Type, Etat) values (?,?,?, ?)";
							preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
							preparedStatement.setInt(1, LastIdEtudiant);
							preparedStatement.setInt(2, TotalCout);
							preparedStatement.setString(3, "Paiement cours");
							preparedStatement.setInt(4, 0);
							preparedStatement.executeUpdate();
						}

						/*Section LOG*/
						//On ajoute dans les logs, le nom de l'utilisateur ajouter, le nom du createur, typeAccount, DateAjout
						try {
							PreparedStatement pst = null;
						
							RequeteSql = "Insert into LogCreatedAccount (Createur, IdUtilisateur, TypeAccount, DateAjout) "
									+ "values (?,?,?,?)";
							pst = ConnexionDB.prepareStatement(RequeteSql);
							pst.setString(1, AC.NomAdministrateur);
							pst.setInt(2, LastIdEtudiant);							
							pst.setString(3, "Etudiant");
							pst.setString(4, currentTime);
							pst.executeUpdate();
							ConnexionDB.close();						
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						
						ConnexionDB.close();
						AC.PaneBlack.setVisible(true);
						AC.PaneMsg.setVisible(true);
						AC.LblTitreMsg.setText("Étudiant ajouté avec succès");
						AC.LblMsg.setText("Retour à l'accueil");
						AfficherEtudiant();
						ClearInfo();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Ajouter Programme
	public void InsertProgramme() {
		try {

			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			RequeteSql = "Insert into programme (Titre, TitreAnglais, Temps, Cout, Etat) VALUES (?, ?, ?, ?, ?)";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			try {
				preparedStatement.setString(1, AC.TbTitreProgrammeFr.getText());
				preparedStatement.setString(2, AC.TbTitreProgrammeEN.getText());
				preparedStatement.setInt(3, 0);
				preparedStatement.setInt(4, 0);
				if (AC.CbProgramme.isSelected()) {
					preparedStatement.setInt(5, AC.Etat);
				} else {
					preparedStatement.setInt(5, AC.Etat);
				}
				preparedStatement.executeUpdate();
				
				int LastIdProg = 0;
				RequeteSql = "Select max(ID) from programme";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					LastIdProg = rs.getInt(1);
				}
				
				RequeteSql = "insert into LogCreatedProgCours (Createur, IDProgCours, Type, Date) values (?,?,?,?)";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);				
				preparedStatement.setString(1, AC.NomAdministrateur);
				preparedStatement.setInt(2, LastIdProg);
				preparedStatement.setString(3, "Programme");
				preparedStatement.setString(4, currentTime);
				preparedStatement.executeUpdate();
				ConnexionDB.close();

				AC.PaneAjoutCours.setVisible(false);
				AC.PaneBlack.setVisible(true);
				AC.PaneMsg.setVisible(true);
				AC.LblTitreMsg.setText("Programme ajouté avec succès");
				AC.LblMsg.setText("Retour à l'accueil");
				AfficherProgramme();
				ClearInfo();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Ajouter Cours
	public void InsertCours() {
		if (AC.TbTitreCours.getText().contentEquals("") || AC.TbTempsCours.getText().contentEquals("")
				|| AC.TbCreditCours.getText().contentEquals("") || AC.TbCoutCours.getText().contentEquals("")
				|| AC.TbDescriptionCours.getText().contentEquals("")
				|| AC.TbDescriptionAngCours.getText().contentEquals("")
				|| AC.TbTitreAngCours.getText().contentEquals("")) {
			// AC.PaneAjoutCours.setVisible(false);
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Erreur");
			AC.LblMsg.setText("Veuillez remplir tout les champs. Réessayer plus tard.");
		} else {
			try {

				PreparedStatement preparedStatement = null;
				Class.forName("com.mysql.jdbc.Driver");
				ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

				RequeteSql = "Insert into cour (Titre, Description, TitreAnglais, DescriptionAnglais, Temps, Cout, Credit, Etat) VALUES (?, ?, ?, ?,?,?,?, ?)";
				try {
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setString(1, AC.TbTitreCours.getText());
					preparedStatement.setString(2, AC.TbDescriptionCours.getText());
					preparedStatement.setString(3, AC.TbTitreAngCours.getText());
					preparedStatement.setString(4, AC.TbDescriptionAngCours.getText());
					preparedStatement.setString(5, AC.TbTempsCours.getText());
					preparedStatement.setString(6, AC.TbCoutCours.getText());
					preparedStatement.setString(7, AC.TbCreditCours.getText());
					if (AC.CheckBoxEtat.isSelected()) {
						preparedStatement.setInt(8, AC.Etat);
					} else {
						preparedStatement.setInt(8, AC.Etat);
					}
					preparedStatement.executeUpdate();

					int LastIdCours = 0;
					RequeteSql = "Select max(ID) from cour";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					rs = preparedStatement.executeQuery();
					if (rs.next()) {
						LastIdCours = rs.getInt(1);
					}
					
					RequeteSql = "insert into LogCreatedProgCours (Createur, IDProgCours, Type, Date) values (?,?,?,?)";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);				
					preparedStatement.setString(1, AC.NomAdministrateur);
					preparedStatement.setInt(2, LastIdCours);
					preparedStatement.setString(3, "Cours");
					preparedStatement.setString(4, currentTime);
					preparedStatement.executeUpdate();
					
					ConnexionDB.close();
					AC.PaneAjoutCours.setVisible(false);
					AC.PaneBlack.setVisible(true);
					AC.PaneMsg.setVisible(true);
					AC.LblTitreMsg.setText("Cour ajouté avec succès");
					AC.LblMsg.setText("Retour à l'accueil");
					AfficherCour();
					ClearInfo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void InsertFrais() {
		try {

			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

			RequeteSql = "Insert into frais (IdEtudiant, Cout, Type, Etat) VALUES (?, ?, ?, ?)";
			try {
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEtudiant);
				preparedStatement.setString(2, AC.TbCout.getText());
				preparedStatement.setString(3, AC.TbType.getText());
				if (AC.CbEtatFrais.getValue().equals("Non-Payer")) {
					preparedStatement.setInt(4, 0);
				} else if (AC.CbEtatFrais.getValue().equals("Payer")) {
					preparedStatement.setInt(4, 1);
				} else {
					preparedStatement.setInt(4, -1);
				}

				preparedStatement.executeUpdate();

				ConnexionDB.close();
				AC.PaneAjoutCours.setVisible(false);
				AC.PaneBlack.setVisible(true);
				AC.PaneMsg.setVisible(true);
				AC.LblTitreMsg.setText("Frais ajouté avec succès");
				AC.LblMsg.setText("Retour à l'accueil");
				AfficherFrais();
				ClearInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void InsertExamen() {
		if(Integer.parseInt(AC.TbPonctuationExamen.getText()) < 0 || Integer.parseInt(AC.TbPonctuationExamen.getText()) > 100) {
			System.out.println("Veuillez insérer une ponctuation de 0 à 100");
		}
		else {
			try {

				PreparedStatement preparedStatement = null;
				Class.forName("com.mysql.jdbc.Driver");
				ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
				//Inserer le nouveau projet/exam dans la table CoursDetails
				RequeteSql = "Insert into coursdetails (IdEnseignant, IdCours, Titre, Description, Ponctuation) VALUES (?, ?, ?, ?, ?)";
				try {
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, AC.IDAdministrateur);
					preparedStatement.setInt(2, IDCour);
					preparedStatement.setString(3, AC.TbTitreExamen.getText());
					preparedStatement.setString(4, AC.TbDescriptionExamen.getText());
					preparedStatement.setString(5, AC.TbPonctuationExamen.getText());
					preparedStatement.executeUpdate();
					
					//Recup dernier ID inserer
					RequeteSql = "Select MAX(IdEvalutation) as ID from coursdetails";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					ResultSet Rs = preparedStatement.executeQuery();
					if(Rs.next()) {
						int IdEval = Rs.getInt("ID");
						//Select le ID etudiant dans la table EtudiantEvaluation
						RequeteSql = "SELECT IdEtudiant FROM etudiantcour where IdCour = ?";
						preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
						preparedStatement.setInt(1, IDCour);
						ResultSet RS = preparedStatement.executeQuery();
						
						while (RS.next()) {	
							
							String SQL = "Insert into etudiantevaluation (IdEtudiant, IdCours, IdEvalutation, ResultatFinal) values (?,?,?,?)";
							preparedStatement = ConnexionDB.prepareStatement(SQL);
							preparedStatement.setInt(1, RS.getInt("IdEtudiant"));
							preparedStatement.setInt(2, IDCour);
							preparedStatement.setInt(3, IdEval);
							preparedStatement.setInt(4, 0);
							preparedStatement.executeUpdate();
							System.out.println(RS.getInt(1));
						}
					}
	
					ConnexionDB.close();
					AC.PaneAjoutExam.setVisible(false);
					AC.PaneBlack.setVisible(true);
					AC.PaneMsg.setVisible(true);
					AC.LblTitreMsg.setText("examen/projet ajouté avec succès");
					AC.LblMsg.setText("Retour à l'accueil");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public void InsertNoteExamen() {
		if(Double.parseDouble(AC.TbAjouterNote.getText()) < 0 || Double.parseDouble(AC.TbAjouterNote.getText()) > 100) {
			System.out.println("Veuillez insérer une note de 0 à 100");
		}
		else {
			try {
				PreparedStatement preparedStatement = null;
				Class.forName("com.mysql.jdbc.Driver");
				ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
				//Inserer le nouveau projet/exam dans la table CoursDetails
				RequeteSql = "Update etudiantevaluation set ResultatFinal = ? where IdEtudiant = ? and IdEvalutation = ? ";
				
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setString(1, AC.TbAjouterNote.getText());
				preparedStatement.setInt(2, IDEtudiant);
				preparedStatement.setInt(3, IdTitreEvaluation);
				preparedStatement.executeUpdate();
				
				RequeteSql = "select etudiantevaluation.IdEtudiant, etudiantevaluation.IdCours,Ponctuation, etudiantevaluation.IdEvalutation, etudiantevaluation.ResultatFinal from coursdetails "
						+ "INNER JOIN etudiantevaluation on coursdetails.IdEvalutation = etudiantevaluation.IdEvalutation "
						+ "where IdEtudiant = ? and coursdetails.IdCours = ? and ResultatFinal !=0";
				
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEtudiant);
				preparedStatement.setInt(2, IDCour);
				rs = preparedStatement.executeQuery();
				
				int Note = 0;
				int Ponctuation = 0;
				int Moyenne = 0;
				int NoteFinal = 0;
				
				while(rs.next()) {
					Note = rs.getInt("ResultatFinal");
					Ponctuation = rs.getInt("Ponctuation");
					Moyenne = (Note * Ponctuation) / 100;
					NoteFinal += Moyenne;

					RequeteSql = "Update etudiantcour set Resultat = ? where IdEtudiant = ? and IdCour = ?";					
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, NoteFinal);
					preparedStatement.setInt(2, IDEtudiant);
					preparedStatement.setInt(3, IDCour);
					preparedStatement.executeUpdate();
					
				}
				
				ConnexionDB.close();
				AC.PaneAjoutExam.setVisible(false);
				AC.PaneBlack.setVisible(true);
				AC.PaneMsg.setVisible(true);
				AC.LblTitreMsg.setText("Vous avez donné une note de " + AC.TbAjouterNote.getText() + "%");
				AC.LblMsg.setText("Retour à l'accueil");
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void InsertCreateur()  {
		try {
			PreparedStatement preparedStatement;
			RequeteSql = "Delete from TableUtilisateur";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.executeUpdate();
			
			//Insert Createur(NomAdmin) dans la une table temporaire pour recup le NomAdmin lors du trigger
			//Pour inserer le NomAdmin dans la table Log
			RequeteSql = "Insert into TableUtilisateur (Createur) values (?)";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.NomAdministrateur);
			preparedStatement.executeUpdate();
			
		}
		catch(Exception e) {
			
		}
	}

	public void InsertRepondreMessage() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			//Inserer le nouveau projet/exam dans la table CoursDetails
			RequeteSql = "Insert into Clavardage (IdEnvoyeur, IdReceveur, Sujet, Message, Date, Etat, IdReponse) values (?,?,?,?,?,?,?) ";
			
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, AC.IDAdministrateur);
			preparedStatement.setInt(2, IdEnvoyeur);
			preparedStatement.setString(3, AC.Tb_Object.getText());
			preparedStatement.setString(4, AC.Tb_Message.getText() + "\n\n____________________________________ \n\nDe : " + AC.NomAdministrateur + "\nEnvoyé le : " + currentTime +
					"\nSujet : " + Sujet + "\n\n"+ AC.Tb_MessageReponse.getText());
			preparedStatement.setString(5, currentTime);
			preparedStatement.setInt(6, 0);
			preparedStatement.setInt(7, IdMessage);
			preparedStatement.executeUpdate();
			
			RequeteSql = "Update Clavardage set Etat = ? where IdMessage = " + IdMessage;
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, 1);
			preparedStatement.executeUpdate();
			
			AC.Tb_MessageReponse.setVisible(false);
			AC.Btn_EnvoyerMessage.setVisible(false);
			AC.Tb_MessageReponse.setText("");
			AC.Tb_Message.setText("");
			AC.Tb_Object.setText("");
			AC.PaneEmail.setVisible(false);
			AC.PaneAccueil.setVisible(true);
			AC.PaneEmailSousMenu.setVisible(false);
		}
		catch(Exception e) {
			System.out.println("Message non-envoyer");
		}
	}

	public void InsertNouveauMessage() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			//Inserer le nouveau projet/exam dans la table CoursDetails
			RequeteSql = "Insert into Clavardage (IdEnvoyeur, IdReceveur, Sujet, Message, Date, Etat, IdReponse) values (?,?,?,?,?,?,?) ";
			
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, AC.IDAdministrateur);
			preparedStatement.setInt(2, IdEnvoyeur);
			preparedStatement.setString(3, AC.Tb_Object.getText());
			preparedStatement.setString(4, "\nDe : " + AC.NomAdministrateur + "\nEnvoyé le : " + currentTime +
					"\nSujet : " + AC.Tb_Object.getText() + "\n\n" + AC.Tb_Message.getText());
			preparedStatement.setString(5, currentTime);
			preparedStatement.setInt(6, 0);
			preparedStatement.setInt(7, 0);
			preparedStatement.executeUpdate();
			
			AC.Tb_MessageReponse.setVisible(false);
			AC.Btn_EnvoyerMessage.setVisible(false);
			AC.Tb_MessageReponse.setText("");
			AC.Tb_Message.setText("");
			AC.Tb_Object.setText("");
			AC.PaneEmail.setVisible(false);
			AC.PaneAccueil.setVisible(true);
			AC.PaneEmailSousMenu.setVisible(false);
		}
		catch(Exception e) {
			System.out.println("Message non-envoyer");
		}
	}
	
	// SECTION DELETE
	// Supprimer Programme
	public void SupprimerProgramme() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update Programme set Etat = ? where ID = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, 0);
			preparedStatement.setInt(2, programme.getID());
			preparedStatement.executeUpdate();
			
			RequeteSql = "insert into TableCommentaire (Createur, ID, Type, Commentaire, Date) values"
					+ " (?,?,?,?,?)";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.NomAdministrateur);
			preparedStatement.setInt(2, IdProgramme);
			preparedStatement.setString(3, "Programme");
			preparedStatement.setString(4, AC.TbCommentaire.getText());
			preparedStatement.setString(5, currentTime);
			preparedStatement.executeUpdate();
			
			InsertNotif();
			
			ConnexionDB.close();
			AC.PaneCommentaire.setVisible(false);
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Etat du programme modifié avec succès");
			AC.LblMsg.setText("Vous venez de modifier l'état à inactif pour le programme " + programme.getTitre());
			AfficherCour();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Supprimer Cours
	public void SupprimerCours() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update Cour set Etat = ? where ID = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, 0);
			preparedStatement.setInt(2, cour.getID());
			preparedStatement.executeUpdate();
			
			RequeteSql = "insert into TableCommentaire (Createur, ID, Type, Commentaire, Date) values"
					+ " (?,?,?,?,?)";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.NomAdministrateur);
			preparedStatement.setInt(2, IDCour);
			preparedStatement.setString(3, "Cours");
			preparedStatement.setString(4, AC.TbCommentaire.getText());
			preparedStatement.setString(5, currentTime);
			preparedStatement.executeUpdate();
			
			InsertNotif();
			
			ConnexionDB.close();
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Etat du cour modifié avec succès");
			AC.LblMsg.setText("Vous venez de modifier l'état à inactif pour le cour " + cour.getTitre());
			AfficherCour();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Supprimer Etudiant
	public void SupprimerEtudiant() {
		// INSERT INTO ancienetudiant SELECT * from etudiant where ID =1
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

			int EtatFrais = 0;
			int PossedeCour = 0;
			RequeteSql = "Select IdCour from etudiantcour where IdEtudiant = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, etudiant.getID());
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				PossedeCour = rs.getInt("IdCour");
			}
			
			RequeteSql = "Select * from frais where IdEtudiant = ? and Etat = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, IDEtudiant);
			preparedStatement.setString(2, Etat);
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				EtatFrais = rs.getInt("Etat");
			}
			
			if (PossedeCour == 0 && (EtatFrais == 1 || EtatFrais == -1)) {
				
				// Ici on vï¿½rifie que l'ï¿½tudiant supprimï¿½ n'a pas le mï¿½me ID qu'un autre
				// ï¿½tudiant dans la table AncienEtudiant
				RequeteSql = "Select * from ancienetudiant where ID = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, etudiant.getID());
				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					System.out.println("Erreur");
				} else {
					
					InsertCreateur();
					
					// Ici cest pour transferer les donnï¿½es vers la table AncienEtudiant
					RequeteSql = "INSERT INTO ancienetudiant SELECT * from etudiant where ID = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, etudiant.getID());
					preparedStatement.executeUpdate();
					
					//Ici c'est pour transferer les données vers la table AncienExamen
					RequeteSql = "Insert into ancienexamen Select * from etudiantevaluation where IdEtudiant = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IDEtudiant);
					preparedStatement.executeUpdate();
					
					//Ici c'est pour supprimer les examens de la table EtudiantEvaluation
					RequeteSql = "Delete from EtudiantEvaluation where IdEtudiant = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IDEtudiant);
					preparedStatement.executeUpdate();
					
					// Ici c'est pour supprimer l'ï¿½tudiant de la table Etudiant
					RequeteSql = "Delete from etudiant where ID = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IDEtudiant);
					preparedStatement.executeUpdate();
					
					RequeteSql = "insert into TableCommentaire (Createur, ID, Type, Commentaire, Date) values"
							+ " (?,?,?,?,?)";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setString(1, AC.NomAdministrateur);
					preparedStatement.setInt(2, IDEtudiant);
					preparedStatement.setString(3, "Etudiant");
					preparedStatement.setString(4, AC.TbCommentaire.getText());
					preparedStatement.setString(5, currentTime);
					preparedStatement.executeUpdate();
					
					InsertNotif();
					
					ConnexionDB.close();
					AC.PaneCommentaire.setVisible(false);
					AC.PaneMsg.setVisible(true);
					AC.LblTitreMsg.setText("Étudiant supprimé avec succès");
					AC.LblMsg.setText("Vous venez de supprimé l'étudiant " + etudiant.getFullName());
					AfficherEtudiant();
				}

			} else {
				if(PossedeCour > 0) {
					System.out.println("Etudiant possede un cour ou des cours");
				}
				else if(EtatFrais == 0) {
					System.out.println("L'étudiant possède des frais non-payer");
				}
				else {
					System.out.println("L'étudiant possède des frais non-payer et des cours.");
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Supprimer Enseignant
	public void SupprimerEnseignant() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			int PossedeCour = 0;
			RequeteSql = "Select * from enseignantcour where IdEnseignant = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, IDEnseignant);
			rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				PossedeCour = rs.getInt("IdCour");
			}
			
			if(PossedeCour == 0){
				
				RequeteSql = "Select * from ancienenseignant where ID = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, administration.getID());
				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					System.out.println("Erreur");
				} else {
					InsertCreateur();
					
					// Ici cest pour transferer les donnï¿½es vers la table AncienEtudiant
					RequeteSql = "INSERT INTO ancienenseignant SELECT * from administration where ID = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, administration.getID());
					preparedStatement.executeUpdate();

					// Ici c'est pour supprimer l'ï¿½tudiant de la table Etudiant
					RequeteSql = "Delete from administration where ID = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, administration.getID());
					preparedStatement.executeUpdate();

					RequeteSql = "Delete from enseignant where NomUtilisateur = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setString(1, administration.getNomUtilisateur());
					preparedStatement.executeUpdate();

					RequeteSql = "insert into TableCommentaire (Createur, ID, Type, Commentaire, Date) values"
							+ " (?,?,?,?,?)";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setString(1, AC.NomAdministrateur);
					preparedStatement.setInt(2, IDEnseignant);
					preparedStatement.setString(3, "Enseignant");
					preparedStatement.setString(4, AC.TbCommentaire.getText());
					preparedStatement.setString(5, currentTime);
					preparedStatement.executeUpdate();				
					
					InsertNotif();
					
					ConnexionDB.close();
					AC.PaneCommentaire.setVisible(false);
					AC.PaneBlack.setVisible(true);
					AC.PaneMsg.setVisible(true);
					AC.LblTitreMsg.setText("Enseignant supprimé avec succès");
					AC.LblMsg.setText("Vous venez de supprimé l'enseignant " + administration.getFullName());

					AfficherEnseignant();					
				}
			}
			else {
				System.out.println("L'enseignant suivant possède des cours");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AnnulerFrais() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update frais set Etat=?, Cout=? where IdFrais = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, -1);
			preparedStatement.setInt(2, 0);
			preparedStatement.setInt(3, IdFrais);
			preparedStatement.executeUpdate();
			
			RequeteSql = "insert into TableCommentaire (Createur, ID, Type, Commentaire, Date) values"
					+ " (?,?,?,?,?)";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.NomAdministrateur);
			preparedStatement.setInt(2, IdFrais);
			preparedStatement.setString(3, "Frais");
			preparedStatement.setString(4, AC.TbCommentaire.getText());
			preparedStatement.setString(5, currentTime);
			preparedStatement.executeUpdate();
			
			
			
			ConnexionDB.close();
			AC.PaneCommentaire.setVisible(false);
			AC.PaneAjoutCours.setVisible(false);
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Frais annulé avec succès");
			AC.LblMsg.setText("Vous venez d'annuler les frais");

			AfficherProgramme();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SupprimerResultat() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update EtudiantCour set Resultat=? where IdEtudiant = ? and IdCour = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, -2);
			preparedStatement.setInt(2, IDEtudiant);
			preparedStatement.setInt(3, IDCour);
			preparedStatement.executeUpdate();
			
			RequeteSql = "insert into TableCommentaire (Createur, ID, Type, Commentaire, Date) values"
					+ " (?,?,?,?,?)";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.NomAdministrateur);
			preparedStatement.setInt(2, 0);
			preparedStatement.setString(3, "Resultat");
			preparedStatement.setString(4, AC.TbCommentaire.getText());
			preparedStatement.setString(5, currentTime);
			preparedStatement.executeUpdate();
			
			InsertNotif();
			
			ConnexionDB.close();
			AC.PaneCommentaire.setVisible(false);
			AC.PaneResultat.setVisible(false);
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Cours supprimé avec succès");
			AC.LblMsg.setText("Vous venez de supprimé/annulé cours suivant " + nomCour);

			AfficherResultatCours();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SupprimerExamen() {
		
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			//On fait un select pour recupérer les IdEvaluation et le resultatfinal = 0
			//Si resultatfinal != 0, on ne supprime pas l'examen
			RequeteSql = "SELECT * FROM `etudiantevaluation` WHERE IdEvalutation = ? AND ResultatFinal = 0";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, IdProjet);
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				
				InsertCreateur();
				
				//Si le resultatfinal == 0, la on supprime l'examen 
				//dans les deix table
						RequeteSql = "DELETE FROM coursdetails where IdEvalutation = ?";
						preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
						preparedStatement.setInt(1, IdProjet);
						preparedStatement.executeUpdate();
						
						RequeteSql = "DELETE FROM etudiantevaluation where IdEvalutation = ?";
						preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
						preparedStatement.setInt(1, IdProjet);
						preparedStatement.executeUpdate();
						
						RequeteSql = "insert into TableCommentaire (Createur, ID, Type, Commentaire, Date) values"
								+ " (?,?,?,?,?)";
						preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
						preparedStatement.setString(1, AC.NomAdministrateur);
						preparedStatement.setInt(2, IdTitreEvaluation);
						preparedStatement.setString(3, "Examen");
						preparedStatement.setString(4, AC.TbCommentaire.getText());
						preparedStatement.setString(5, currentTime);
						preparedStatement.executeUpdate();
						
						AC.PaneResultat.setVisible(false);
						AC.PaneBlack.setVisible(true);
						AC.PaneMsg.setVisible(true);
						AC.LblTitreMsg.setText("Examen supprimé avec succès");
						AC.LblMsg.setText("Vous venez de supprimé l'examen suivant " + TitreExamen);
						AC.BtnContinueMsg.setVisible(false);
						AC.BtnContinueExam.setVisible(true);
						AC.PaneCommentaire.setVisible(false);
						AfficherExamen();
			}
			else {
				//Message d'erreur si l'examen != 0
				AC.LblMsgErreurExam.setVisible(true);
				AC.LblMsgErreurExam.setText("Vous ne pouvez pas supprimer l'examen, une note a été attribué.");
				 PauseTransition visiblePause = new PauseTransition(
					        Duration.seconds(3)
					);
					visiblePause.setOnFinished(
					        event -> AC.LblMsgErreurExam.setVisible(false)
					);
					visiblePause.play();
			}
	
			InsertNotif();
			
			ConnexionDB.close();
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	public void SupprimerNotif() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			RequeteSql = "DELETE FROM tablenotification where IdLog = ? && IdAdmin = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, IdLog);
			preparedStatement.setInt(2, AC.IDAdministrateur);
			preparedStatement.executeUpdate();
			
			InsertNotif();
			
			ConnexionDB.close();
			AfficherNotification();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SupprimerEmail() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			RequeteSql = "update Clavardage set Etat = -1 where IdMessage = " + IdMessage;
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.executeUpdate();
			ConnexionDB.close();
			AfficherNomEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// SECTION UPDATE
	// Modifier Etudiant
	public void ModifierEtudiant() {
		try {
			
			int IdProgramme = Integer.parseInt(AC.ComboBoxProgramme.getValue().substring(0, 2).trim());
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update Etudiant set Utilisateur=?, Prenom=?, Nom=?, Telephone=?, Email=?, AdresseDeMaison=?, Ville=?, Province=?, CodePostal=?, IdProgramme=?, Langue=? where ID = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.TbUser.getText());
			preparedStatement.setString(2, AC.TbPrenom.getText());
			preparedStatement.setString(3, AC.TbNom.getText());
			preparedStatement.setString(4, AC.TbTel.getText());
			preparedStatement.setString(5, AC.TbEmail.getText());
			preparedStatement.setString(6, AC.TbAdresse.getText());
			preparedStatement.setString(7, AC.TbVille.getText());
			preparedStatement.setString(8, AC.TbProvince.getText());
			preparedStatement.setString(9, AC.TbCodePostal.getText());
			preparedStatement.setInt(10, IdProgramme);
			preparedStatement.setString(11, AC.CbLangueEtudiant.getValue());
			preparedStatement.setInt(12, IDEtudiant);
			preparedStatement.executeUpdate();
			
			InsertNotif();
			
			int TotalCout = 0;
			RequeteSql = "SELECT ID, Cout FROM cour INNER JOIN programmecours ON cour.ID = programmecours.IdCours where programmecours.IdProgramme = ? order by Ordre";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, IdProgramme);
			rs = preparedStatement.executeQuery();

			RequeteSql = "DELETE FROM `etudiantcour` WHERE IdEtudiant = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, etudiant.getID());
			preparedStatement.executeUpdate();

			RequeteSql = "DELETE FROM `frais` WHERE IdEtudiant = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, etudiant.getID());
			preparedStatement.executeUpdate();

			while (rs.next()) {
				TotalCout += rs.getInt("Cout");

				RequeteSql = "Insert into etudiantcour (IdEtudiant, IdCour, Resultat) values (?,?,?)";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, etudiant.getID());
				preparedStatement.setInt(2, rs.getInt("ID"));
				preparedStatement.setInt(3, 0);
				preparedStatement.executeUpdate();
			}

			if (IdProgramme == 0) {
				System.out.println("Aucun programme choisi, donc aucun frais");
			} else {
				RequeteSql = "Insert into frais (IdEtudiant, Cout, Type, Etat) values (?,?,?,?)";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, etudiant.getID());
				preparedStatement.setInt(2, TotalCout);
				preparedStatement.setString(3, "Paiement cours");
				preparedStatement.setInt(4, 0);
				preparedStatement.executeUpdate();
			}
			
			ConnexionDB.close();
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Étudiant modifié avec succès");
			AC.LblMsg.setText("Vous venez de modifier l'étudiant " + etudiant.getFullName());

			AfficherEtudiant();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Modifier Enseignant
	public void ModifierEnseignant() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update Administration set NomUtilisateur=?, Prenom=?, Nom=?, Telephone=?, Email=?, AdresseDeMaison=?, Ville=?, Province=?, CodePostal=? where ID = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.TbUser.getText());
			preparedStatement.setString(2, AC.TbPrenom.getText());
			preparedStatement.setString(3, AC.TbNom.getText());
			preparedStatement.setString(4, AC.TbTel.getText());
			preparedStatement.setString(5, AC.TbEmail.getText());
			preparedStatement.setString(6, AC.TbAdresse.getText());
			preparedStatement.setString(7, AC.TbVille.getText());
			preparedStatement.setString(8, AC.TbProvince.getText());
			preparedStatement.setString(9, AC.TbCodePostal.getText());
			preparedStatement.setInt(10, administration.getID());
			preparedStatement.executeUpdate();
			
			InsertNotif();
			
			RequeteSql = "Update enseignant set NomUtilisateur=?, Prenom=?, Nom=? where ID = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.TbUser.getText());
			preparedStatement.setString(2, AC.TbPrenom.getText());
			preparedStatement.setString(3, AC.TbNom.getText());
			preparedStatement.setInt(4, administration.getID());
			preparedStatement.executeUpdate();
			
			ConnexionDB.close();
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Enseignant modifié avec succès");
			AC.LblMsg.setText("Vous venez de modifier l'enseignant " + administration.getFullName());

			AfficherEnseignant();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Modifier Cours
	public void ModifierCours() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update Cour set Titre=?, Description=?, TitreAnglais=?, DescriptionAnglais=?, Temps=?, Cout=?, Credit=?, Etat=? where ID = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.TbTitreCours.getText());
			preparedStatement.setString(2, AC.TbDescriptionCours.getText());
			preparedStatement.setString(3, AC.TbTitreAngCours.getText());
			preparedStatement.setString(4, AC.TbDescriptionAngCours.getText());
			preparedStatement.setString(5, AC.TbTempsCours.getText());
			preparedStatement.setString(6, AC.TbCoutCours.getText());
			preparedStatement.setString(7, AC.TbCreditCours.getText());
			if (AC.CheckBoxEtat.isSelected()) {
				preparedStatement.setInt(8, 1);
			} else {
				preparedStatement.setInt(8, 0);
			}
			preparedStatement.setInt(9, cour.getID());
			preparedStatement.executeUpdate();
			
			InsertNotif();
			
			ConnexionDB.close();
			AC.PaneAjoutCours.setVisible(false);
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Cours modifié avec succès");
			AC.LblMsg.setText("Vous venez de modifier le cours " + cour.getTitre());

			AfficherCour();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Modifier Programme
	public void ModifierProgramme() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update Programme set Titre=?, TitreAnglais=?, Etat=? where ID = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setString(1, AC.TbTitreProgrammeFr.getText());
			preparedStatement.setString(2, AC.TbTitreProgrammeEN.getText());
			if (AC.CbProgramme.isSelected()) {
				preparedStatement.setInt(3, 1);
			} else {
				preparedStatement.setInt(3, 0);
			}
			preparedStatement.setInt(4, programme.getID());
			preparedStatement.executeUpdate();
			
			InsertNotif();
			
			ConnexionDB.close();
			AC.PaneAjoutCours.setVisible(false);
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Programme modifié avec succès");
			AC.LblMsg.setText("Vous venez de modifier le programme " + programme.getTitre());

			AfficherProgramme();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ModifierFrais() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update frais set Cout=?, Type=?, Etat=? where IdFrais = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);

			if (AC.CbEtatFrais.getValue().equals("Non-Payer")) {
				preparedStatement.setString(1, AC.TbCout.getText());
				preparedStatement.setString(2, AC.TbType.getText());
				preparedStatement.setInt(3, 0);
			} else if (AC.CbEtatFrais.getValue().equals("Payer")) {
				preparedStatement.setInt(1, 0);
				preparedStatement.setString(2, AC.TbType.getText());
				preparedStatement.setInt(3, 1);
			} else {
				preparedStatement.setInt(1, 0);
				preparedStatement.setString(2, AC.TbType.getText());
				preparedStatement.setInt(3, -1);
			}
			preparedStatement.setInt(4, IdFrais);
			preparedStatement.executeUpdate();
			
			
			
			ConnexionDB.close();
			AC.PaneAjoutCours.setVisible(false);
			AC.PaneBlack.setVisible(true);
			AC.PaneMsg.setVisible(true);
			AC.LblTitreMsg.setText("Frais modifié avec succès");
			AC.LblMsg.setText("Vous venez de modifier les frais");

			AfficherProgramme();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ModifierResultat() {
		if (Integer.parseInt(AC.TbChangeNote.getText()) < -2 || Integer.parseInt(AC.TbChangeNote.getText()) > 100) {
			AC.LblMsgErreurResult.setVisible(true);
			AC.LblMsgErreurResult.setText("Veuillez insérer un nombre entre -2 et 100");
		} else {
			try {
				PreparedStatement preparedStatement = null;
				Class.forName("com.mysql.jdbc.Driver");
				ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
				
				InsertCreateur();
				
				RequeteSql = "Update EtudiantCour set Resultat=? where IdEtudiant = ? and IdCour = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setString(1, AC.TbChangeNote.getText());
				preparedStatement.setInt(2, IDEtudiant);
				preparedStatement.setInt(3, IDCour);
				preparedStatement.executeUpdate();
				
				InsertNotif();
				
				ConnexionDB.close();
				AC.PaneResultat.setVisible(false);
				AC.PaneBlack.setVisible(true);
				AC.PaneMsg.setVisible(true);
				AC.LblTitreMsg.setText("Note du cours modifié avec succès");
				AC.LblMsg.setText("Vous venez de modifier la note du cours suivant " + nomCour);

				AfficherResultatCours();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void ModifierExamen() {
	
			try {
				PreparedStatement preparedStatement = null;
				Class.forName("com.mysql.jdbc.Driver");
				ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
				
				InsertCreateur();
				
				RequeteSql = "Update coursdetails set Titre=?, Description=?, Ponctuation=? where IdEvalutation = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setString(1, AC.TbModifTitreExam.getText());
				preparedStatement.setString(2, AC.TbModifDescriptionExam.getText());
				preparedStatement.setString(3, AC.TbModifPonctuationExam.getText());
				preparedStatement.setInt(4, IdProjet);
				preparedStatement.executeUpdate();
				
				InsertNotif();
				
				ConnexionDB.close();
				AC.PaneResultat.setVisible(false);
				AC.PaneBlack.setVisible(true);
				AC.PaneMsg.setVisible(true);
				AC.LblTitreMsg.setText("Examen modifié avec succès");
				AC.LblMsg.setText("Vous venez de modifier l'examen suivant " + TitreExamen);

				AfficherExamen();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	
	// SECTION SHOW INFO
	// Afficher tout les infos dans les textbox pour la modification de données
	public void ShowInfoEtudiant() {
		int IDProg = etudiant.getProgramme();
		AC.TbUser.setText(etudiant.getUtilisateur());
		AC.TbPrenom.setText(etudiant.getPrenom());
		AC.TbNom.setText(etudiant.getNom());
		AC.TbAdresse.setText(etudiant.getAdresse());
		AC.TbVille.setText(etudiant.getVille());
		AC.TbCodePostal.setText(etudiant.getCodePostal());
		AC.TbProvince.setText(etudiant.getProvince());
		AC.TbTel.setText(etudiant.getTelephone());
		AC.TbEmail.setText(etudiant.getEmail());

		ObservableList<String> items = AC.ComboBoxProgramme.getItems();
		for (int index = 0; index < items.size(); index++) {
			int Id = Integer.parseInt(items.get(index).toString().substring(0, 2).trim());
			if (Id == IDProg) {
				AC.ComboBoxProgramme.getSelectionModel().select(index);
			}
		}

	}
	
	public void ShowInfoEnseignant() {
		AC.TbUser.setText(administration.getNomUtilisateur());
		AC.TbPrenom.setText(administration.getPrenom());
		AC.TbNom.setText(administration.getNom());
		AC.TbAdresse.setText(administration.getAdresseDeMaison());
		AC.TbVille.setText(administration.getVille());
		AC.TbCodePostal.setText(administration.getCodePostal());
		AC.TbProvince.setText(administration.getProvince());
		AC.TbTel.setText(administration.getTelephone());
		AC.TbEmail.setText(administration.getEmail());
	}

	public void ShowInfoCours() {
		AC.TbTitreCours.setText(cour.getTitre());
		AC.TbDescriptionCours.setText(cour.getDescription());
		AC.TbDescriptionAngCours.setText(cour.getDescriptionAnglais());
		AC.TbTitreAngCours.setText(cour.getTitreAnglais());
		AC.TbTempsCours.setText(cour.getTemps());
		AC.TbCoutCours.setText(String.valueOf(cour.getCout()));
		AC.TbCreditCours.setText(String.valueOf(cour.getCredit()));
		if (cour.getEtat() == 1) {
			AC.CheckBoxEtat.setSelected(true);
		} else {

			AC.CheckBoxEtat.setSelected(false);
		}
	}

	public void ShowInfoProgramme() {
		AC.TbTitreProgrammeFr.setText(programme.getTitre());
		AC.TbTitreProgrammeEN.setText(programme.getTitreAnglais());
		if (programme.getEtat() == 1) {
			AC.CbProgramme.setSelected(true);
		} else {
			AC.CbProgramme.setSelected(false);
		}
	}

	public void ShowInfoFrais() {
		AC.TbCout.setText(Integer.toString(frais.getCout()));
		AC.TbType.setText(frais.getType());
		AC.CbEtatFrais.setValue(Etat);

	}

	// SECTION LIST VIEW PROGRAMME-COUR//
	// Afficher les cours dans la première liste de droites (ListViewCourAttente)
	public void ListViewProgrammeCour() {
		OLC.obListGauche.clear();
		OLC.obListDroit.clear();
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			PreparedStatement pst;

			RequeteSql = "SELECT * FROM cour where Etat = 1 and ID not in (SELECT IdCours FROM programmecours where IdProgramme = ?) ";
			pst = ConnexionDB.prepareStatement(RequeteSql);
			pst.setInt(1, ProgrammeID);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListGauche.add(rs.getInt("ID") + " " + rs.getString("Titre"));
			}

			RequeteSql = "SELECT * FROM cour where ID in (SELECT IdCours FROM programmecours where IdProgramme = ?) ";
			pst = ConnexionDB.prepareStatement(RequeteSql);
			pst.setInt(1, ProgrammeID);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListDroit.add(rs.getInt("ID") + " " + rs.getString("Titre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		AC.ListViewCourAttente.setItems(OLC.obListGauche);
		AC.ListViewCourAssigner.setItems(OLC.obListDroit);

	}

	// Inserer le IdCour et le IdProgramme dans la table ProgrammeCour
	public void AssignerCourProgramme() {
		try {

			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

			// Si un ou plusieurs cour apparaissent déjà dans la listview
			// ils sont delete
			if (OLC.obListDroit.size() > 0) {
				RequeteSql = "delete from programmecours where IdProgramme = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, ProgrammeID);
				preparedStatement.executeUpdate();

				for (int i = 0; i < OLC.obListDroit.size(); i++) {
					// On fait un substring pour récupérer seulement le ID du cours exemple:
					// 1 Francais --> IdCour = 1
					int IdCour = Integer.parseInt(OLC.obListDroit.get(i).substring(0, 2).trim());
					RequeteSql = "Select * from programmecours where IdProgramme = ? and IdCours = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, ProgrammeID);
					preparedStatement.setInt(2, IdCour);
					rs = preparedStatement.executeQuery();

					// Ici on insert tout les cours dans la listview de droit dans la table
					// ProgrammeCours
					RequeteSql = "Insert into programmecours (IdProgramme, IdCours, Ordre) VALUES (?, ?, ?)";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, ProgrammeID);
					preparedStatement.setInt(2, IdCour);
					preparedStatement.setInt(3, i + 1);
					preparedStatement.executeUpdate();

					// Afficher les messages quand le cour est assigné
					AC.PaneAssignerCourProgramme.setVisible(false);
					AC.PaneBlack.setVisible(true);
					AC.PaneMsg.setVisible(true);
					AC.LblMsgPrincProgCour.setVisible(true);
					AC.LblMsgPrincProgCour.setText(programme.getTitre());
					AC.LblTitreMsg.setText("Cours assigné au programme suivant ");
					AC.LblMsg.setText("Retour à l'accueil");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnexionDB.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public void FlecheDroite() {
		selectedItemsGauche = AC.ListViewCourAttente.getSelectionModel().getSelectedIndex();
		if (selectedItemsGauche == -1) {
			System.out.println("Aucun cour dans la list");
		} else {
			OLC.obListDroit.add(OLC.obListGauche.get(selectedItemsGauche));
			OLC.obListGauche.remove(selectedItemsGauche);
			AC.LblMessageProg.setText("Vous venez d'ajouter un cour");
			AC.ListViewCourAssigner.refresh();
			AC.ListViewCourAttente.refresh();
		}
	}

	public void FlecheGauche() {
		selectedItemsDroit = AC.ListViewCourAssigner.getSelectionModel().getSelectedIndex();
		if (selectedItemsDroit == -1) {
			System.out.println("Aucun cour dans la list");
		} else {
			OLC.obListGauche.add(OLC.obListDroit.get(selectedItemsDroit));
			OLC.obListDroit.remove(selectedItemsDroit);
			AC.LblMessageProg.setText("Vous venez de retirer un cour");
			AC.ListViewCourAssigner.refresh();
			AC.ListViewCourAttente.refresh();
		}

	}

	// SECTION LIST VIEW ETUDIANT-COUR//
	public void ListViewEtudiantCour() {
		OLC.obListGauche.clear();
		OLC.obListDroit.clear();
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			PreparedStatement pst;

			RequeteSql = "SELECT * FROM cour where Etat = 1 and ID not in (SELECT IdCour FROM etudiantcour where IdEtudiant = ?)  ";
			pst = ConnexionDB.prepareStatement(RequeteSql);
			pst.setInt(1, IDEtudiant);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListGauche.add(rs.getInt("ID") + " " + rs.getString("Titre"));
			}

			RequeteSql = "SELECT * FROM cour where Etat = 1 and ID in (SELECT IdCour FROM etudiantcour where IdEtudiant = ?) ";
			pst = ConnexionDB.prepareStatement(RequeteSql);
			pst.setInt(1, IDEtudiant);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListDroit.add(rs.getInt("ID") + " " + rs.getString("Titre"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		AC.ListViewEtudiantGauche.setItems(OLC.obListGauche);
		AC.ListViewEtudiantDroite.setItems(OLC.obListDroit);
	}

	public void FlecheEnvoieDroite() {
		selectedIdGauche = AC.ListViewEtudiantGauche.getSelectionModel().getSelectedIndex();
		if (selectedIdGauche == -1) {
			System.out.println("Aucun cour dans la list");
		} else {
			OLC.obListDroit.add(OLC.obListGauche.get(selectedIdGauche));
			OLC.obListGauche.remove(selectedIdGauche);
			// AC.LblMessageProg.setText("Vous venez d'ajouter un cour");
			AC.ListViewEtudiantDroite.refresh();
			AC.ListViewEtudiantGauche.refresh();
			System.out.println("Cour ajouter");
		}
	}

	public void FlecheDroiteProgEns() {
		selectedIdGauche = AC.ListViewProgEnsGauche.getSelectionModel().getSelectedIndex();
		if (selectedIdGauche == -1) {
			System.out.println("Aucun cour dans la list");
		} else {
			OLC.obListDroit.add(OLC.obListGauche.get(selectedIdGauche));
			OLC.obListGauche.remove(selectedIdGauche);
			// AC.LblMessageProg.setText("Vous venez d'ajouter un cour");
			AC.ListViewProgEnsDroite.refresh();
			AC.ListViewProgEnsGauche.refresh();
			System.out.println("Cour ajouter");
		}
	}

	public void FlecheEnvoieGauche() {
		selectedIdDroit = AC.ListViewEtudiantDroite.getSelectionModel().getSelectedIndex();
		System.out.println("Fleche gauche " + selectedIdDroit);
		if (selectedIdDroit == -1) {
			System.out.println("Aucun cour dans la list");
		} else {
			OLC.obListGauche.add(OLC.obListDroit.get(selectedIdDroit));
			OLC.obListDroit.remove(selectedIdDroit);
			// AC.LblMessageProg.setText("Vous venez de retirer un cour");
			AC.ListViewEtudiantDroite.refresh();
			AC.ListViewEtudiantGauche.refresh();
			System.out.println("Cour retirer");
		}

	}

	public void AssignerEtudiantCour() {

		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

			if (OLC.obListDroit.size() > -1) {
				RequeteSql = "delete from etudiantcour where IdEtudiant = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEtudiant);
				preparedStatement.executeUpdate();

				// Delete les frais
				RequeteSql = "DELETE FROM `frais` WHERE IdEtudiant = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEtudiant);
				preparedStatement.executeUpdate();

				// Ajoute les frais

				for (int i = 0; i < OLC.obListDroit.size(); i++) {

					int IdCour = Integer.parseInt(OLC.obListDroit.get(i).substring(0, 2).trim());
					RequeteSql = "Select * from etudiantcour where IdEtudiant = ? and IdCour = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IDEtudiant);
					preparedStatement.setInt(2, IdCour);

					rs = preparedStatement.executeQuery();

					RequeteSql = "Insert into etudiantcour (IdEtudiant, IdCour, Resultat) VALUES (?, ?, ?)";

					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IDEtudiant);
					preparedStatement.setInt(2, IdCour);
					preparedStatement.setInt(3, 0);
					preparedStatement.executeUpdate();

				}

				int TotalCout = 0;
				// int IdProgramme =
				// Integer.parseInt(AC.ComboBoxProgramme.getValue().substring(0,2).trim());
				RequeteSql = "SELECT sum(Cout) as CoutTotal FROM cour INNER JOIN etudiantcour ON cour.ID = etudiantcour.IdCour where etudiantcour.IdEtudiant= ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEtudiant);
				rs = preparedStatement.executeQuery();

				if (rs.next()) {
					TotalCout = rs.getInt("CoutTotal");

				}

				RequeteSql = "Insert into frais (IdEtudiant, Cout, Type, Etat) values (?,?,?,?)";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEtudiant);
				preparedStatement.setInt(2, TotalCout);
				preparedStatement.setString(3, "Paiement cours");
				preparedStatement.setInt(4, 0);
				preparedStatement.executeUpdate();

				AC.PaneAssigneCourEtudiant.setVisible(false);
				AC.PaneBlack.setVisible(true);
				AC.PaneMsg.setVisible(true);
				AC.LblMsgPrincProgCour.setVisible(true);
				AC.LblMsgPrincProgCour.setText(etudiant.getFullName());
				AC.LblTitreMsg.setText("Cours assigné à l'étudiant suivant ");
				AC.LblMsg.setText("Retour à l'accueil");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnexionDB.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public void ClearInfo() {
		AC.TbUser.setText("");
		AC.TbPrenom.setText("");
		AC.TbNom.setText("");
		AC.TbAdresse.setText("");
		AC.TbVille.setText("");
		AC.TbCodePostal.setText("");
		AC.TbProvince.setText("");
		AC.TbTel.setText("");
		AC.TbEmail.setText("");
		AC.TbTitreCours.setText("");
		AC.TbTempsCours.setText("");
		AC.TbCoutCours.setText("");
		AC.TbCreditCours.setText("");
		AC.TbTitreAngCours.setText("");
		AC.TbDescriptionAngCours.setText("");
		AC.TbDescriptionCours.setText("");
		AC.TbTitreProgrammeFr.setText("");
		AC.TbCout.setText("");
		AC.TbType.setText("");
	}

	public void formatTextfield() {
		AC.TbTempsCours.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					AC.TbTempsCours.setText(oldValue);
				}
			}
		});

		AC.TbCoutCours.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					AC.TbCoutCours.setText(oldValue);
				}
			}
		});

		AC.TbCreditCours.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
					AC.TbCreditCours.setText(oldValue);
				}
			}
		});

		AC.TbTel.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,10}([\\.]\\d{0,4})?")) {
					AC.TbTel.setText(oldValue);
				}
			}
		});

		AC.TbChangeNote.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,3}([\\.\\-]\\d{0,3})?")) {
					AC.TbChangeNote.setText(oldValue);
				}
			}
		});
		
		AC.TbPonctuationExamen.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,3}([\\.\\-]\\d{0,3})?")) {
					AC.TbPonctuationExamen.setText(oldValue);
				}
			}
		});
		
	}

	public void ComboBoxProgramme() {

		OLC.ObListCbProgramme.clear();
		try {
			String SQL;
			Class.forName("com.mysql.jdbc.Driver");

			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

			if (AC.CbLangueProgramme.isSelected()) {
				SQL = "Select concat (ID, '   ', Titre) AS TitreProg from programme where Etat = 1 order by Titre";
				OLC.ObListCbProgramme.add("0 Aucun programme");
			} else {
				SQL = "Select concat (ID, '   ', TitreAnglais) AS TitreProg from programme where Etat = 1 order by TitreAnglais";
				OLC.ObListCbProgramme.add("0 No program");
			}
			PreparedStatement pst = ConnexionDB.prepareStatement(SQL);
			rs = pst.executeQuery();

			while (rs.next()) {
				OLC.ObListCbProgramme.add(rs.getString(1));
			}
			AC.ComboBoxProgramme.setItems(OLC.ObListCbProgramme);
			AC.CbProg.setItems(OLC.ObListCbProgramme);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ComboBoxLangage() {
		OLC.ObListCbLangue.clear();
		try {

			OLC.ObListCbLangue.add("Francais");
			OLC.ObListCbLangue.add("English");
			AC.CbLangueEtudiant.setItems(OLC.ObListCbLangue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ComboBoxEtat() {
		OLC.ObListEtat.clear();
		try {

			OLC.ObListEtat.add("Non-Payer"); // On mets 0 dans la db
			OLC.ObListEtat.add("Payer");// On mets 1 dans la db
			OLC.ObListEtat.add("Annuler");
			AC.CbEtatFrais.setItems(OLC.ObListEtat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ComboBoxPoste() {
		OLC.obListPoste.clear();
		try {

			OLC.obListPoste.add("Administrateur"); // On mets 0 dans la db
			OLC.obListPoste.add("Professeur");// On mets 1 dans la db
			OLC.obListPoste.add("Etudiant");
			AC.CbPosteAdmin.setItems(OLC.obListPoste);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// SECTION LIST VIEW ETUDIANT-PROGRAMME
	// Liste gauche
	public void ListViewProgramme() {
		OLC.obListGauche.clear();
		// obListDroit.clear();
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			PreparedStatement pst;

			RequeteSql = "SELECT * FROM programme where Etat = 1 and ID not in (SELECT IdProgramme FROM etudiant where ID = ?)";
			pst = ConnexionDB.prepareStatement(RequeteSql);
			pst.setInt(1, IDEtudiant);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListGauche.add(rs.getInt("ID") + " " + rs.getString("Titre"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		AC.ListViewProgEtuAttente.setItems(OLC.obListGauche);
	}

	// Liste droite
	public void ListViewCoursProgramme() {
		// obListGauche.clear();
		OLC.obListDroit.clear();
		try {
			// ObservableList<String> items = AC.ComboBoxProgramme.getItems();
			// int index = AC.ListViewProgEtuAttente.getSelectionModel().getSelectedIndex();
			int IdProg = Integer
					.parseInt(AC.ListViewProgEtuAttente.getSelectionModel().getSelectedItem().substring(0, 2).trim());
			
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			PreparedStatement pst;

			RequeteSql = "SELECT concat(cour.ID, ' ', cour.Titre) AS TitreCour FROM `cour` INNER JOIN "
					+ "programmecours ON cour.ID = programmecours.IdCours where programmecours.IdProgramme = ?";
			pst = ConnexionDB.prepareStatement(RequeteSql);
			pst.setInt(1, IdProg);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListDroit.add(rs.getString("TitreCour"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		AC.ListViewProgEtuAssinger.setItems(OLC.obListDroit);
	}

	public void SupprimerItemsListView() {
		selectedIdDroit = AC.ListViewProgEtuAssinger.getSelectionModel().getSelectedIndex();
		if (selectedIdDroit == -1) {
			System.out.println("Aucun cour dans la list");
		} else {
			OLC.obListDroit.remove(selectedIdDroit);
			AC.ListViewProgEtuAssinger.refresh();
			System.out.println("Cour supprimé");
		}
	}

	public void AjouterCours() {
		int IdProg = Integer
				.parseInt(AC.ListViewProgEtuAttente.getSelectionModel().getSelectedItem().substring(0, 2).trim());
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

			if (OLC.obListDroit.size() > 0) {
				RequeteSql = "delete from etudiantcour where IdEtudiant = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEtudiant);
				preparedStatement.executeUpdate();

				for (int i = 0; i < OLC.obListDroit.size(); i++) {

					int IdCour = Integer.parseInt(OLC.obListDroit.get(i).substring(0, 2).trim());
					RequeteSql = "Select * from etudiantcour where IdEtudiant = ? and IdCour = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IDEtudiant);
					preparedStatement.setInt(2, IdCour);

					rs = preparedStatement.executeQuery();

					RequeteSql = "Insert into etudiantcour (IdEtudiant, IdCour, Resultat) VALUES (?, ?, ?)";

					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IDEtudiant);
					preparedStatement.setInt(2, IdCour);
					preparedStatement.setInt(3, 0);
					preparedStatement.executeUpdate();

					RequeteSql = "UPDATE `etudiant` SET IdProgramme = ? WHERE ID = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IdProg);
					preparedStatement.setInt(2, IDEtudiant);
					preparedStatement.executeUpdate();

					AC.PaneAssigneCourEtudiant.setVisible(false);
					AC.PaneBlack.setVisible(true);
					AC.PaneMsg.setVisible(true);
					AC.LblMsgPrincProgCour.setVisible(true);
					AC.LblMsgPrincProgCour.setText(etudiant.getFullName());
					AC.LblTitreMsg.setText("Cours assigné à l'étudiant suivant ");
					AC.LblMsg.setText("Retour à l'accueil");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnexionDB.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public void ListViewProgrammeEnseignantGauche(int IdProg) {
		OLC.obListGauche.clear();
		try {

			System.out.println("ListViewProgrammeEnseignantGauche " + AC.IdProg);
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			PreparedStatement pst;

			RequeteSql = "SELECT concat(cour.ID, ' ', cour.Titre) AS TitreCour FROM `cour` INNER JOIN "
					+ "programmecours ON cour.ID = programmecours.IdCours where programmecours.IdProgramme = ? and cour.ID NOT IN "
					+ "(SELECT IdCour from enseignantcour where IdEnseignant = ?)";
			pst = ConnexionDB.prepareStatement(RequeteSql);
			pst.setInt(1, IdProg);
			pst.setInt(2, IDEnseignant);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListGauche.add(rs.getString("TitreCour"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		AC.ListViewProgEnsGauche.setItems(OLC.obListGauche);
	}

	public void ListViewProgrammeEnseignantDroite() {
		OLC.obListDroit.clear();
		try {
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			PreparedStatement pst;

			RequeteSql = "Select concat(cour.ID, ' ', cour.Titre) AS TitreCour FROM cour INNER JOIN enseignantcour ON cour.ID = enseignantcour.IdCour "
					+ "WHERE enseignantcour.IdEnseignant = ?";
			pst = ConnexionDB.prepareStatement(RequeteSql);
			pst.setInt(1, IDEnseignant);
			rs = pst.executeQuery();
			while (rs.next()) {
				OLC.obListDroit.add(rs.getString("TitreCour"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		AC.ListViewProgEnsDroite.setItems(OLC.obListDroit);

		// SELECT concat(cour.ID, ' ', cour.Titre) AS TitreCour FROM `cour` à
		// INNER JOIN programmecours ON cour.ID = programmecours.IdCours where
		// programmecours.IdProgramme = 1 and cour.ID NOT IN (SELECT IdCour from
		// enseignantcour where IdEnseignant = 2)
	}

	public void SupprimerCoursEns() {
		selectedIdDroit = AC.ListViewProgEnsDroite.getSelectionModel().getSelectedIndex();
		if (selectedIdDroit == -1) {
			System.out.println("Aucun cour dans la list");
		} else {
			OLC.obListDroit.remove(selectedIdDroit);
			AC.ListViewProgEnsDroite.refresh();
			System.out.println("Cour supprimé");
		}
	}

	public void AjouterCoursEnseignant() {
		int IdProg = Integer.parseInt(AC.CbProg.getValue().substring(0, 2).trim());
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");

			if (OLC.obListDroit.size() > 0) {

				// On delete les cours de la table EnseignantCour
				RequeteSql = "delete from EnseignantCour where IdEnseignant = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEnseignant);
				preparedStatement.executeUpdate();
				for (int i = 0; i < OLC.obListDroit.size(); i++) {
					int IdCour = Integer.parseInt(OLC.obListDroit.get(i).substring(0, 2).trim());

					// On select le IDEnseignant + le IdCour
					RequeteSql = "Select * from EnseignantCour where IdEnseignant = ? and IdCour = ?";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IDEnseignant);
					preparedStatement.setInt(2, IdCour);
					rs = preparedStatement.executeQuery();

					// Par la suite on insert les cours dans la table
					RequeteSql = "Insert into EnseignantCour (IdEnseignant, IdCour) VALUES (?, ?)";
					preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
					preparedStatement.setInt(1, IDEnseignant);
					preparedStatement.setInt(2, IdCour);
					preparedStatement.executeUpdate();

					AC.PaneAssignerProgEns.setVisible(false);
					AC.PaneBlack.setVisible(true);
					AC.PaneMsg.setVisible(true);
					AC.LblMsgPrincProgCour.setVisible(true);
					AC.LblMsgPrincProgCour.setText(administration.getFullName());
					AC.LblTitreMsg.setText("Cours assigné à l'enseignant suivant ");
					AC.LblMsg.setText("Retour à l'accueil");
				}

				// On delete les cours de la table EnseignantCour
				RequeteSql = "delete from EnseignantProgramme where IdEnseignant = ? and IdProgramme = ?";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEnseignant);
				preparedStatement.setInt(2, IdProg);
				preparedStatement.executeUpdate();

				// On insert le programme dans enseignantProgramme
				RequeteSql = "Insert into EnseignantProgramme (IdEnseignant, IdProgramme) VALUES (?, ?)";
				preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
				preparedStatement.setInt(1, IDEnseignant);
				preparedStatement.setInt(2, IdProg);
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnexionDB.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	//Section Notification
	public void MarquerCommeLu() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update tablenotification set Etat=? where IdLog = ? && IdAdmin = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, 0);
			preparedStatement.setInt(2, IdLog);
			preparedStatement.setInt(3, AC.IDAdministrateur);
			preparedStatement.executeUpdate();
			ConnexionDB.close();
			
			AfficherNotification();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void MarquerCommeNonLu() {
		try {
			PreparedStatement preparedStatement = null;
			Class.forName("com.mysql.jdbc.Driver");
			ConnexionDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root", "");
			
			InsertCreateur();
			
			RequeteSql = "Update tablenotification set Etat=? where IdLog = ? && IdAdmin = ?";
			preparedStatement = ConnexionDB.prepareStatement(RequeteSql);
			preparedStatement.setInt(1, 1);
			preparedStatement.setInt(2, IdLog);
			preparedStatement.setInt(3, AC.IDAdministrateur);
			preparedStatement.executeUpdate();
			ConnexionDB.close();
			
			AfficherNotification();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}