package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Login extends Pane{
	
	
	private TextField emailInput;
	private PasswordField passwordInput;
	private Button submit;
	
	
	
	
	public TextField getEmailInput() {
		return emailInput;
	}


	public PasswordField getPasswordInput() {
		return passwordInput;
	}


	public Button getSubmit() {
		return submit;
	}


	


	public BorderPane setUpScene() {
		BorderPane bp = new BorderPane();
		bp.setCenter(registerGp());
		return bp;
	}
	
	
	GridPane registerGp() {
		
		
		
		GridPane gp = new GridPane();
		gp.setHgap(20);
		gp.setVgap(7);
		gp.setAlignment(Pos.CENTER);
		
		Label headingLabel = new Label("Admin LogIn");
        headingLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");

        // Create an HBox to hold the heading label
        HBox headingBox = new HBox(headingLabel);
        headingBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        headingBox.setAlignment(javafx.geometry.Pos.CENTER);
		
        gp.add(headingBox, 0, 0,2,1);
		// 3 row (email)
		Label email = new Label("Username");
		gp.add(email, 0, 1, 2,1 );
		
		// 4 row (text)
		emailInput = new TextField();
		emailInput.setPrefWidth(200);
		gp.add(emailInput, 0, 2, 2,1);
		// 5 row (password)
		Label password = new Label("Password");
		gp.add(password, 0, 3, 2,1 );
		// 6 row (password text)
		passwordInput = new PasswordField();
		gp.add(passwordInput, 0, 4, 2,1);
		
		// 7 row (submit)
		HBox submitHbox = new HBox(10);
		submit = new Button("Log In");
		submitHbox.getChildren().addAll(submit);
		submitHbox.setAlignment(javafx.geometry.Pos.CENTER);
		gp.add(submitHbox, 0, 6,2,1);
		
		
		return gp;
		
	}

}
