package views;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.Bill;
import models.Booking;


@SuppressWarnings("rawtypes")
public class BillServicePage extends Dialog{
	private ComboBox<Integer> bookingIDList;
	private TextArea summary;
	private TextField discount;
	private Label afterDiscount;
	public TextArea getSummary() {
		return summary;
	}
	public TextField getDiscount() {
		return discount;
	}
	public Label getAfterDiscount() {
		return afterDiscount;
	}
	public ComboBox<Integer> getBookingIDList() {
		return bookingIDList;
	}
	
	@SuppressWarnings("unchecked")
	public BillServicePage(String title , ArrayList<Booking> allBookings) {
		setTitle(title);
		// Ok
		ButtonType okButtonType = new ButtonType("Confirm Discount", ButtonBar.ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
		
		// Creating a grid pane
		GridPane gp = new GridPane();
		gp.setVgap(10);
		gp.setHgap(20);
		// Label to enter the booking id
		Label bookingIDLabel = new Label("Booking ID:");
		bookingIDList = new ComboBox<>();
		bookingIDList.setPromptText("Select ID");
		ArrayList <Integer> bookingIDs = new ArrayList<>();
		
		for(int i =0; i < allBookings.size(); i++) {
			bookingIDs.add(allBookings.get(i).getId().get());
			
		}
		// Creating a list 
		bookingIDList.setItems(FXCollections.observableArrayList(bookingIDs));
		
		// Hbox 
		HBox upperHbox = new HBox(10);
		upperHbox.getChildren().addAll(bookingIDLabel, bookingIDList);
		
		gp.add(upperHbox, 0, 0,2,1);
		
		// Text area
		summary = new TextArea();
		summary.setPrefHeight(300);
		summary.setEditable(false);
		gp.add(summary, 0, 1,2,1);
		// Discount
		Label discountLabel = new Label("Discount:");
		discount = new TextField();
		Label percent = new Label("%");
		
		HBox discountHbox = new HBox(10);
		discountHbox.getChildren().addAll(discountLabel, discount,percent);
		gp.add(discountHbox, 0, 2);
		
		// After discount
		Label afterDiscountLabel = new Label("After Discount: $");
		afterDiscount = new Label();
		
		HBox afterDiscountHbox = new HBox(10);
		afterDiscountHbox.getChildren().addAll(afterDiscountLabel, afterDiscount);
		gp.add(afterDiscountHbox, 1, 2);
		getDialogPane().setContent(gp);
		
		
		
		setResultConverter(new Callback<ButtonType, Bill>() {
			public Bill call(ButtonType b) {
				if (b == okButtonType) {
					for(int i = 0; i < allBookings.size();i++) {
						if(allBookings.get(i).getId().get() == bookingIDList.getValue()) {
							Bill bill = new Bill(allBookings.get(i), Double.parseDouble(discount.getText()));
							i = allBookings.size();
							return bill;
							
						} ; 
						
					}; 
				}
				return null;
			}
		});
		
		
	}
	
	public void populateTextArea(Booking booking) {
		summary.clear();
		String summaryText = "Booking ID: " + booking.getId().get() + "\n"
							 + "Guest Name: " + booking.getFullName().get() + "\n"
							 + "Number Rooms: " + booking.getNoRooms().get() + "\n\n"
							 + "Type of rooms: \n";
		summary.setText(summaryText);
		
		for(int i = 0; i < booking.getRoomTypes().get().size(); i++) {
			summary.appendText(booking.getRoomTypes().get().get(i) + "\n");
			
		} ;
		summary.appendText("\n");
		summary.appendText("Average Rate: " + booking.getAvgRate().get() + "per/day\n");
		summary.appendText("Total Amount: " + booking.getTotal().get());
		
	};
}
