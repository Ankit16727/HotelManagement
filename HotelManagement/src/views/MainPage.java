package views;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class MainPage extends Pane{
	private Button kiosk;
	private Button admin;
	
	
	public Button getKiosk() {
		return kiosk;
	}


	public Button getAdmin() {
		return admin;
	}


	public BorderPane setUpView() {
		
        
		Label heading = new Label("ABC Hotels");
		//heading.setFont(headingFont);
		heading.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");
		
		HBox headingBox = new HBox(heading);
        headingBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        headingBox.setAlignment(javafx.geometry.Pos.CENTER);
        
        
		// Proving two options to either go to kiosk or admin
		kiosk = new Button("Kiosk");
		Label seperation = new Label("/");
		admin = new Button("LogIn");
		Label desc = new Label("(Admins)");
		
		// Putting everything in Hbox
		HBox hbox = new HBox(5);
		hbox.getChildren().addAll(kiosk, seperation, admin, desc);
		hbox.setAlignment(Pos.CENTER);
		
		// Vbox for everything
		VBox allsections = new VBox(15);
		allsections.getChildren().addAll(headingBox, hbox);
		allsections.setAlignment(Pos.CENTER);
		
		BorderPane bp = new BorderPane();
		bp.setCenter(allsections);
		
		return bp;
	}

}
