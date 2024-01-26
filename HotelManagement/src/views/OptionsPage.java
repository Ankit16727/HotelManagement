package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class OptionsPage {
	
	private Button submit;
	public Button getSubmit() {
		return submit;
	}
	public ListView<String> getOptionsListView() {
		return optionsListView;
	}
	ListView<String> optionsListView;
	public GridPane setUpView() {
		
		
		// Creating a heading...
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(5);
		gp.setVgap(10);
		Label headingLabel = new Label("ABC Hotels Management");
        headingLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22px;");

        // Create an HBox to hold the heading label
        HBox headingBox = new HBox(headingLabel);
        headingBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        headingBox.setAlignment(javafx.geometry.Pos.CENTER);
        gp.add(headingBox, 0, 0,2,1);
		
		// Creating a list view
		ObservableList<String> options = FXCollections.observableArrayList(
                "Book a room",
                "Bill service",
                "Current bookings",
                "Available rooms",
                "Exit"
        );

        // Create a ListView and set the options
        optionsListView = new ListView<>(options);
        optionsListView.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        gp.add(optionsListView, 0, 1,2,1);
        
        submit = new Button("Select");
        HBox hbox = new HBox();
        hbox.setAlignment(javafx.geometry.Pos.CENTER);
        hbox.getChildren().addAll(submit);
        gp.add(hbox, 0, 2, 2,1);
        
        return gp;
	};
}
