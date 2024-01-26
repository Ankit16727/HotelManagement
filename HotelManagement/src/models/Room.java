package models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
	private IntegerProperty id;  
	private DoubleProperty rate;  
	private StringProperty desc; 
	private StringProperty type;
	public Room(int id, double rate, String desc, String type) {
		this.id = new SimpleIntegerProperty(id); 
		this.rate = new SimpleDoubleProperty(rate);  
		this.desc = new SimpleStringProperty(desc);
		this.type = new SimpleStringProperty(type);
	}

	public int getId() {
		return id.get();
	}

	public double getRate() {
		return rate.get();
	}

	public String getDesc() {
		return desc.get();
	}

	public String getType() {
		return type.get();
	}; 
	
	public IntegerProperty getIdProperty() {
		return id;
	}
	
	public DoubleProperty getRateProperty() {
		return rate;
	}
	
	public StringProperty getDescProperty() {
		return desc;
	}
	
	public StringProperty getTypeProperty() {
		return type;
	}; 
	
	
	
}
