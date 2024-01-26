package models;

public class User {
	private int id; 
	private String title;  
	private String firstName;  
	private String lastName; 
	private String phone;  
	private String address;  
	private String email; 
	
	public User(int id,String firstName, String lastName, String phone, String address, String email, String title) {
		this.id = id;
		this.firstName = firstName;  
		this.lastName = lastName;  
		this.phone = phone;  
		this.address = address;  
		this.email = email ;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}; 

	
}
