package views;



import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import models.User;




@SuppressWarnings("rawtypes")
public class UserInfoDialog extends Dialog{
	
	@SuppressWarnings("unchecked")
	public UserInfoDialog(String title, int userID) {
		setTitle(title);
		// Ok
		ButtonType okButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
		// Creating a list view
		GridPane gp = new GridPane();
		gp.setHgap(5);
		gp.setVgap(10);
		// 1st row
		Label heading = new Label("User Details");
		//heading.setFont(headingFont);
		heading.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");
		
		HBox headingBox = new HBox(heading);
        headingBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        headingBox.setAlignment(javafx.geometry.Pos.CENTER);
        gp.add(headingBox, 0, 0,2,1);
		
        // 2nd rpw
        Label titleLabel = new Label("Title:");
        TextField titleText = new TextField();
        gp.add(titleLabel, 0, 1);
        gp.add(titleText, 1, 1);
        // 3rd row
        Label firstNameLabel = new Label("First Name:");
        TextField firstName = new TextField();
        gp.add(firstNameLabel, 0, 2);
        gp.add(firstName, 1, 2);
        
        
        // 4th row
        Label lastNameLabel = new Label("Last Name:");
        TextField lastName = new TextField();
        gp.add(lastNameLabel, 0, 3);
        gp.add(lastName, 1, 3);
        
        // 5th row
        Label addressLabel = new Label("Address:");
        TextField address = new TextField();
        gp.add(addressLabel, 0, 4);
        gp.add(address, 1, 4);
        // 6th row
        Label emailLabel = new Label("Email:");
        TextField email = new TextField();
        gp.add(emailLabel, 0, 5);
        gp.add(email, 1, 5);
        // 7th
        Label phoneLabel = new Label("Phone:");
        TextField phone = new TextField();
		gp.add(phoneLabel, 0, 6);
		gp.add(phone, 1, 6);
		getDialogPane().setContent(gp);
		
		
		
		setResultConverter(new Callback<ButtonType, User>() {
			public User call(ButtonType b) {
				if (b == okButtonType) {
					// Creating the user
					// (int id,String firstName, String lastName, String phone, String address, String email)
					User user = new User(userID, firstName.getText(), lastName.getText(), phone.getText(), address.getText(), email.getText(), titleText.getText());
					return user; 
				}
				return null;
			}
		});
		
		
	}

}
