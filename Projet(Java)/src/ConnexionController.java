import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ConnexionController {
	Fonction f = new Fonction(this);
    @FXML
    public TextField TbMDP;
    @FXML public Pane PaneErreur;
    @FXML public Label LblMsgErreur;
    @FXML
    public Button BtnQuitter;
    
    @FXML
    public Button BtnConnexion;

    @FXML
    public TextField TbUser;
    @FXML public ImageView ImgBackground;
    @FXML public ImageView ImgLogo;
    @FXML public ImageView ImgUser;
    @FXML public ImageView ImgPass;
    File file;
	Image icn;
    public void initialize() {
		//Image signe dollars
	file = new File("src\\Photos\\bg.jpg");
	icn = new Image(file.toURI().toString());
	ImgBackground.setImage(icn);
	
	file = new File("src\\Photos\\S.png");
	icn = new Image(file.toURI().toString());
	ImgLogo.setImage(icn);
	
	file = new File("src\\Photos\\User2.png");
	icn = new Image(file.toURI().toString());
	ImgUser.setImage(icn);
	
	file = new File("src\\Photos\\Lock.png");
	icn = new Image(file.toURI().toString());
	ImgPass.setImage(icn);
}   
    
    @FXML
    void ActionPrincipal(ActionEvent event) {
    	 Button btn =(Button)event.getSource();
    	 String ID = "";
  	   	 ID = btn.getId();
  	   	 switch(ID) {
  	   	 		case "BtnConnexion":
  	   	 			f.Connexion();
  	   	 			break;
  	   	 		case "BtnQuitter":
  	   	 			System.exit(0);
  	   	 			break;
  	   	 }
    }

}
