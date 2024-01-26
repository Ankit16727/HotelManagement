package models;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Booking {
	private IntegerProperty id; 
	private StringProperty fullName; 
	private SimpleObjectProperty<ArrayList<String>> roomTypes;  
	private StringProperty checkIn ; 
	private StringProperty checkOut;  
	private IntegerProperty noRooms; 
	private DoubleProperty avgRate;
	private DoubleProperty total;
	
	public DoubleProperty getTotal() {
		return total;
	}

	public Booking(int id, String fullName, ArrayList<String> roomTypes, String checkIn, String checkOut, int noRooms, double avgRate, double total) {
		this.id = new SimpleIntegerProperty(id)  ;
		this.fullName = new SimpleStringProperty(fullName);  
		this.roomTypes = new SimpleObjectProperty<>(new ArrayList<>(roomTypes));
		
		this.checkIn = new SimpleStringProperty(checkIn);  
		this.checkOut = new SimpleStringProperty(checkOut);  
		this.noRooms = new SimpleIntegerProperty(noRooms);  
		this.avgRate = new SimpleDoubleProperty(avgRate);  
		this.total = new SimpleDoubleProperty(total);
		
	}

	public IntegerProperty getId() {
		return id;
	}

	public StringProperty getFullName() {
		return fullName;
	}

	public SimpleObjectProperty<ArrayList<String>> getRoomTypes() {
		return roomTypes;
	}

	public StringProperty getCheckIn() {
		return checkIn;
	}

	public StringProperty getCheckOut() {
		return checkOut;
	}

	public IntegerProperty getNoRooms() {
		return noRooms;
	}

	public DoubleProperty getAvgRate() {
		return avgRate;
	}; 
	
	
}
