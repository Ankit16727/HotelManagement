package views;


import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Booking;
import javafx.beans.property.*;


@SuppressWarnings("rawtypes")
public class AllBookingPage extends Dialog{
	@SuppressWarnings("unchecked")
	public AllBookingPage(String title,  ArrayList<Booking> bookings, int totalBookings) {
		setTitle(title + " " + totalBookings);
		// Ok
		ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(okButtonType);
		// Creating a table view
		TableView <Booking> bookingTable = new TableView(FXCollections.observableArrayList(bookings));
		
		TableColumn<Booking, Integer> idColumn = new TableColumn<>("BookingID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());

        TableColumn<Booking, String> nameColumn = new TableColumn<>("Full Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullName());
        
        TableColumn<Booking, Integer> noRoomsColumn = new TableColumn<>("No Rooms");
        noRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("noRooms"));
        noRoomsColumn.setCellValueFactory(cellData -> cellData.getValue().getNoRooms().asObject());
        
        TableColumn<Booking, String> checkInColumn = new TableColumn<>("Check In");
        checkInColumn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        checkInColumn.setCellValueFactory(cellData -> cellData.getValue().getCheckIn());
        
        TableColumn<Booking, String> checkOutColumn = new TableColumn<>("Check Out");
        checkOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        checkOutColumn.setCellValueFactory(cellData -> cellData.getValue().getCheckOut());

        TableColumn<Booking, String> rateColumn = new TableColumn<>("Average Rate");
        rateColumn.setCellValueFactory(cellValue -> {
            double rate = cellValue.getValue().getAvgRate().get();
            return new SimpleStringProperty("$" + String.valueOf(rate));
        });
        
        
        TableColumn<Booking, ArrayList<String>> roomTypesColumn = new TableColumn<>("Room Types");
        roomTypesColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomTypes());
        
        
        roomTypesColumn.setCellFactory(column -> {
            return new TableCell<Booking, ArrayList<String>>() {
                @Override
                protected void updateItem(ArrayList<String> roomTypes, boolean empty) {
                    super.updateItem(roomTypes, empty);

                    if (roomTypes == null || empty) {
                        setText(null);
                    } else {
                        // Create a ListView and display hobbies in a single cell
                        ListView<String> listView = new ListView<>(FXCollections.observableArrayList(roomTypes));
                        listView.setPrefHeight(100);
                        setGraphic(listView);
                    }
                }
            };
        });
        
        
       

        // Add columns to the TableView
        
        bookingTable.getColumns().addAll(idColumn, nameColumn,  roomTypesColumn,noRoomsColumn, rateColumn, checkInColumn, checkOutColumn);
		
		
		
		
		
		
		
		getDialogPane().setMinWidth(840);
		getDialogPane().setMinHeight(600);
		
		getDialogPane().setContent(bookingTable);
		
		
		
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
