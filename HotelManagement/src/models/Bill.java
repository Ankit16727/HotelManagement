package models;

public class Bill {
	private Booking booking ;  
	private double discount ;  
	
	public Bill(Booking booking , double discount) {
		this.booking = booking;  
		this.discount = discount ; 
		
	}

	public Booking getBooking() {
		return booking;
	}

	public double getDiscount() {
		return discount;
	};
}
