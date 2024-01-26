package views;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;


public class RoomPage {
	
	private TextField noGuests; 
	private DatePicker checkInDate;
	private DatePicker checkOutDate;
	private ComboBox <String> roomType;
	private ListView<CheckBox> roomsList;
	private Button submitButton; 
	private Label total;
	
	
	public Label getTotal() {
		return total;
	}

	public TextField getNoGuests() {
		return noGuests;
	}

	public DatePicker getCheckInDate() {
		return checkInDate;
	}

	public DatePicker getCheckOutDate() {
		return checkOutDate;
	}

	public ComboBox<String> getRoomType() {
		return roomType;
	}

	public ListView<CheckBox> getRoomsList() {
		return roomsList;
	}

	public Button getSubmitButton() {
		return submitButton;
	}

	public GridPane setUpView () {
		
		
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		// Heading (1st row)
		Label heading = new Label("ABC Hotels");
		//heading.setFont(headingFont);
		heading.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");
		
		HBox headingBox = new HBox(heading);
        headingBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        headingBox.setAlignment(javafx.geometry.Pos.CENTER);
        
        gp.add(headingBox, 0, 0,2,1);
        
        // Number of guests (2nd row)
        Label noGuestLabel = new Label("Number of guests:");
        noGuests = new TextField();
        gp.add(noGuestLabel, 0, 1);
        gp.add(noGuests, 1, 1);
        
        // Check in Date: (3rd row)
        Label checkInDateLabel = new Label("CheckIn:");
        checkInDate = new DatePicker();
        
        gp.add(checkInDateLabel, 0, 2);
        gp.add(checkInDate, 1, 2);
        
        
        // Check out date (4th row)
        Label checkOutDateLabel = new Label("CheckOut:");
        checkOutDate = new DatePicker();
        
        gp.add(checkOutDateLabel, 0, 3);
        gp.add(checkOutDate, 1, 3);
        
        // Select the room type (5th row)
        Label roomTypeLabel = new Label("Room Type:");
        roomType = new ComboBox<>();
        ArrayList<String> items = new ArrayList<>();
        items.add("Single");
        items.add("Double");
        items.add("Delux");
        items.add("Pent House");
        roomType.setItems(FXCollections.observableArrayList(items));
        roomType.setPromptText("Select Type");
        
        gp.add(roomTypeLabel, 0, 4);
        gp.add(roomType, 1,4);
        
        
        // 6th row
        roomsList = new ListView<>();
        roomsList.setPlaceholder(new javafx.scene.control.Label("Select the above options to see the rooms available."));
        roomsList.setPrefHeight(200);
        gp.add(roomsList, 0, 5,2,1);
        
        // 7th row
        Label totalLabel = new Label("Total:  $");
        total = new Label("");
        Label rate = new Label("/day");
        HBox totalHbox = new HBox();
        totalHbox.getChildren().addAll(totalLabel, total, rate);
        gp.add(totalHbox, 0,6,2,1);
        
        // 8th row
        submitButton = new Button("Submit");
        HBox hbox = new HBox();
        hbox.getChildren().addAll(submitButton);
        hbox.setAlignment(Pos.CENTER);
        
        gp.add(hbox,0, 7,2,1);
        
        
		return gp;
		
	}
	
	
	
}
