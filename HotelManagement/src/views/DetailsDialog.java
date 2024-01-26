package views;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import models.Room;
import models.User;

@SuppressWarnings("rawtypes")
public class DetailsDialog extends Dialog{
	@SuppressWarnings("unchecked")
	public DetailsDialog(String title, User user, ArrayList <Room> singleRoom, ArrayList <Room> doubleRoom, ArrayList <Room> deluxRoom, ArrayList <Room> pentHouse, double total) {
		setTitle(title);
		// Ok
		ButtonType okButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
		// Creating a list view
		GridPane gp = new GridPane();
		gp.setHgap(15);
		gp.setVgap(10);
		// 1st row
		Label heading = new Label("Booking Details");
		//heading.setFont(headingFont);
		heading.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");
		
		HBox headingBox = new HBox(heading);
        headingBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        headingBox.setAlignment(javafx.geometry.Pos.CENTER);
        gp.add(headingBox, 0, 0,2,1);
		
        // 2nd row
        Label fullNameLabel = new Label("Full Name:");
        Label fullName = new Label(user.getTitle() + " " + user.getFirstName() + " " + user.getLastName());
        gp.add(fullNameLabel, 0, 1);
        gp.add(fullName, 1, 1);
        
        
        // 3rd row
        Label emailLabel = new Label("Email:");
        Label email = new Label(user.getEmail());
		gp.add(emailLabel, 0, 2);
		gp.add(email, 1, 2);
		
        // Room details
        int curr_row = 3 ; 
		
        if(singleRoom.size() > 0) {
        	Label singleRoomLabel = new Label("Single Rooms");
        	singleRoomLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
        	HBox hbox = new HBox(singleRoomLabel);
        	hbox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        	hbox.setAlignment(javafx.geometry.Pos.CENTER);
        	gp.add(hbox, 0, curr_row,2,1);
        	curr_row++;
        	for(int i = 0; i < singleRoom.size() ; i++) {
        		Label room = new Label(singleRoom.get(i).getDesc() + " " + singleRoom.get(i).getRate());
        		gp.add(room, 0, curr_row, 2,1);
        		curr_row++;
        	} ; 
        	
        } ;  
        
        if(doubleRoom.size() > 0) {
        	Label doubleRoomLabel = new Label("Double Rooms");
        	doubleRoomLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
        	HBox hbox = new HBox(doubleRoomLabel);
        	hbox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        	hbox.setAlignment(javafx.geometry.Pos.CENTER);
        	gp.add(hbox, 0, curr_row,2,1);
        	curr_row++;
        	for(int i = 0; i < doubleRoom.size() ; i++) {
        		Label room = new Label(doubleRoom.get(i).getDesc() + " " + doubleRoom.get(i).getRate());
        		gp.add(room, 0, curr_row, 2,1);
        		curr_row++;
        	} ; 
        	
        } ; 
        
        if(deluxRoom.size() > 0) {
        	
        	Label deluxRoomLabel = new Label("Delux Rooms");
        	deluxRoomLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
        	HBox hbox = new HBox(deluxRoomLabel);
        	hbox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        	hbox.setAlignment(javafx.geometry.Pos.CENTER);
        	gp.add(hbox, 0, curr_row,2,1);
        	curr_row++;
        	for(int i = 0; i < deluxRoom.size() ; i++) {
        		Label room = new Label(deluxRoom.get(i).getDesc() + " " + deluxRoom.get(i).getRate());
        		gp.add(room, 0, curr_row, 2,1);
        		curr_row++;
        	} ; 
        	
        } ;  
        
        if(pentHouse.size() > 0) {
        	Label pentRoomLabel = new Label("Pent Houses");
        	pentRoomLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
        	HBox hbox = new HBox(pentRoomLabel);
        	hbox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        	hbox.setAlignment(javafx.geometry.Pos.CENTER);
        	gp.add(hbox, 0, curr_row,2,1);
        	curr_row++;
        	for(int i = 0; i < pentHouse.size() ; i++) {
        		Label room = new Label(pentHouse.get(i).getDesc() + " " + pentHouse.get(i).getRate());
        		gp.add(room, 0, curr_row, 2,1);
        		curr_row++;
        	} ; 
        	
        } ; 
        
        Label totalLabel = new Label("Total Price: $");
        Label totalPrice = new Label(String.valueOf(total));
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(totalLabel, totalPrice);
        gp.add(hbox, 0, curr_row, 2,1);
        
        getDialogPane().setContent(gp);
        
		
		
		setResultConverter(new Callback<ButtonType, User>() {
			public User call(ButtonType b) {
				if (b == okButtonType) {
					
					return user; 
				}
				return null;
			}
		});
		
		
	}
}
