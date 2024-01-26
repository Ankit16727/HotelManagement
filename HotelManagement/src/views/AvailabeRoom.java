package views;





import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Room;





@SuppressWarnings("rawtypes")
public class AvailabeRoom extends Dialog{
	private DatePicker startDate;
	private DatePicker endDate;
	private TableView <Room> availableRoomsTabel;
	private TableColumn<Room, Integer> roomIDColumn;
	private TableColumn<Room, String> roomDescColumn;
	private TableColumn<Room, String> roomTypeColumn;
	private TableColumn<Room, String> roomRateColumn;
	
	
	public TableView<Room> getAvailableRoomsTabel() {
		return availableRoomsTabel;
	}
	public TableColumn<Room, Integer> getRoomIDColumn() {
		return roomIDColumn;
	}
	public TableColumn<Room, String> getRoomDescColumn() {
		return roomDescColumn;
	}
	public TableColumn<Room, String> getRoomTypeColumn() {
		return roomTypeColumn;
	}
	public TableColumn<Room, String> getRoomRateColumn() {
		return roomRateColumn;
	}
	public DatePicker getStartDate() {
		return startDate;
	}
	public DatePicker getEndDate() {
		return endDate;
	}
	@SuppressWarnings("unchecked")
	public AvailabeRoom(String title) {
		setTitle(title);
		// Ok
		ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(okButtonType);
		// Creating a hbox
		Label startDateLabel = new Label("From:");
		startDate = new DatePicker();
		HBox start = new HBox(5);
		start.getChildren().addAll(startDateLabel, startDate);
		Label endDateLabel = new Label("To:");
		endDate = new DatePicker();
		HBox end = new HBox(5);
		end.getChildren().addAll(endDateLabel, endDate);
		
		HBox upperSection = new HBox(10);
		upperSection.getChildren().addAll(start, end);
		
		
		// Creating a table view
		availableRoomsTabel = new TableView<>();
		
		roomIDColumn = new TableColumn("Room ID"); 
		roomIDColumn.setPrefWidth(120);
		roomDescColumn = new TableColumn("Description"); 
		roomDescColumn.setPrefWidth(120);
		roomTypeColumn = new TableColumn("Room Type");
		roomTypeColumn.setPrefWidth(120);
		roomRateColumn = new TableColumn("Rate/day"); 
		roomRateColumn.setPrefWidth(120);
		
		availableRoomsTabel.setPlaceholder(new javafx.scene.control.Label("Select the time range to see the available rooms"));
		availableRoomsTabel.getColumns().addAll(roomIDColumn, roomTypeColumn, roomDescColumn, roomRateColumn);
		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(upperSection, availableRoomsTabel);
		
		getDialogPane().setContent(new VBox(vbox));
		
		
		
		/*
		setResultConverter(new Callback<ButtonType, CartOrder>() {
			public CartOrder call(ButtonType b) {
				if (b == okButtonType) {
					int selectedIndex = listView.getSelectionModel().getSelectedIndex();
					if(selectedIndex != -1) {
						return carts.get(selectedIndex);
					}else {
						// No item selected
						return null;
					}
				}
				return null;
			}
		});
		*/
		
		
	}
}
