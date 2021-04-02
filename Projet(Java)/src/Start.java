import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application{
	
	private double xOffset = 0;
	private double yOffset = 0;
	
	@Override
	public void start(Stage stage) throws Exception{
		//On ouvre la premiere scene(le menu de depart)
		Parent root = FXMLLoader.load(getClass().getResource("Connexion.fxml"));
		stage.getIcons().add(new Image("Photos/S.png"));
		Scene scene = new Scene(root);
		stage.setTitle("");
		scene.getStylesheets().add("Style.css");
		stage.initStyle(StageStyle.UNDECORATED);
		
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}
		});		
		stage.setScene(scene);		
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
 