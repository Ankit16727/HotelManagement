package contollers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;

import javafx.stage.Stage;
import models.Room;
import models.User;
import views.DetailsDialog;
import views.RoomPage;
import views.UserInfoDialog;

public class RoomPageController {
	RoomPage view = new RoomPage();
	
	ArrayList <Room> singleRoom = new ArrayList<>(); 
	ArrayList <Room> doubleRoom = new ArrayList<>(); 
	ArrayList <Room> deluxRoom = new ArrayList<>(); 
	ArrayList <Room> pentHouse = new ArrayList<>(); 
	ArrayList <Room> availableRooms ; 
	
	private SimpleDoubleProperty totalPrice = new SimpleDoubleProperty(0.0);
	private String roomType;
	// Need to change it
	private int userID ;
	private int bookingID;
	private User user;
	private LocalDate checkIn;
	private LocalDate checkOut;
	// Need to chane it.
	
	
	// Socket variables
	/*
	private DataOutputStream dout;
    @SuppressWarnings("unused")
	private DataInputStream din;
	*/
    
    
	
	public RoomPageController(Stage s, String admin) {
		
		// Setting up the scene
		Scene scene = new Scene(view.setUpView(),400,500);
		s.setScene(scene);
		
		// setting up the actions
		setUpActions(s,admin);
	} ;
	
	
	
	
	
	@SuppressWarnings({ "unchecked" })
	public void setUpActions(Stage s,String admin) {
		// Socket
				
		/*
				Socket clientSocket = null;
				try {
					clientSocket = new Socket("localhost", 4000);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
		            dout = new DataOutputStream(clientSocket.getOutputStream());
		            din = new DataInputStream(clientSocket.getInputStream());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        */
		        
		        
				
		userID= maxUserID() + 1;
		bookingID = maxBookingID() + 1;
		
		
		
		// Creating a binfing
		view.getTotal().textProperty().bind(Bindings.createStringBinding(() ->
        Double.toString(totalPrice.get()), totalPrice));
		
		
		
		
		// Setting up the event handler for the combo box
		view.getRoomType().setOnAction(event -> {
			checkIn = view.getCheckInDate().getValue();  
			checkOut = view.getCheckOutDate().getValue();
            roomType = view.getRoomType().getValue();
            if(roomType == "Pent House") {
            	availableRooms = roomsAvailable("PentHouse", Date.valueOf(checkIn), Date.valueOf(checkOut));
            	populateListView(availableRooms, pentHouse);
            }else {
            	availableRooms = roomsAvailable(roomType+ "Room", Date.valueOf(checkIn), Date.valueOf(checkOut));
            	if(roomType.equals("Single")) {
            		populateListView(availableRooms, singleRoom);
            	}else if(roomType.equals("Double")) {
            		populateListView(availableRooms, doubleRoom);
            	}else {
            		// Delux room
            		populateListView(availableRooms, deluxRoom);
            	};
            }; 
            
            
        });
		
		
		view.getCheckInDate().valueProperty().addListener((observable, oldValue, newValue)->{
			checkIn = view.getCheckInDate().getValue();  
			checkOut = view.getCheckOutDate().getValue();
            roomType = view.getRoomType().getValue();
            if(checkIn != null && checkOut != null && roomType != null) {
	            if(roomType == "Pent House") {
	            	availableRooms = roomsAvailable("PentHouse", Date.valueOf(checkIn), Date.valueOf(checkOut));
	            	populateListView(availableRooms, pentHouse);
	            }else {
	            	availableRooms = roomsAvailable(roomType+ "Room", Date.valueOf(checkIn), Date.valueOf(checkOut));
	            	if(roomType.equals("Single")) {
	            		populateListView(availableRooms, singleRoom);
	            	}else if(roomType.equals("Double")) {
	            		populateListView(availableRooms, doubleRoom);
	            	}else {
	            		// Delux room
	            		populateListView(availableRooms, deluxRoom);
	            	};
	            }; 
            };
		});
		
		view.getCheckOutDate().valueProperty().addListener((observable, oldValue, newValue)->{
			checkIn = view.getCheckInDate().getValue();  
			checkOut = view.getCheckOutDate().getValue();
            roomType = view.getRoomType().getValue();
            if(checkIn != null && checkOut != null && roomType != null) {
	            if(roomType == "Pent House") {
	            	availableRooms = roomsAvailable("PentHouse", Date.valueOf(checkIn), Date.valueOf(checkOut));
	            	populateListView(availableRooms, pentHouse);
	            }else {
	            	availableRooms = roomsAvailable(roomType+ "Room", Date.valueOf(checkIn), Date.valueOf(checkOut));
	            	if(roomType.equals("Single")) {
	            		populateListView(availableRooms, singleRoom);
	            	}else if(roomType.equals("Double")) {
	            		populateListView(availableRooms, doubleRoom);
	            	}else {
	            		// Delux room
	            		populateListView(availableRooms, deluxRoom);
	            	};
	            }; 
            };
		});
		
		
		
		
		
		
		
		view.getSubmitButton().setOnAction(event->{
			// Checking if the rooms are sufficient for the people
			int selectRoomsCapacity = singleRoom.size() * 2 + doubleRoom.size() * 3 + deluxRoom.size() * 3 + pentHouse.size() * 5;
			if(selectRoomsCapacity >= Integer.parseInt(view.getNoGuests().getText())) {
				
				
				/*
				String reservationDetails = generateReservationDetails();
		        try {
					dout.writeUTF(reservationDetails);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
				// Sufficient People, getting the user details.
				UserInfoDialog userInfo = new UserInfoDialog("User Details", userID);
				Optional <User> result = userInfo.showAndWait();
				if(result.isPresent()) {
					user = result.get();
					// Showing all the details
					DetailsDialog details = new DetailsDialog("Booking Details",user, singleRoom, doubleRoom, deluxRoom, pentHouse, totalPrice.get());
					Optional <User> result2 = details.showAndWait();
					if(result2.isPresent()) {
						// User confirmed the booking
						// Saving the data to the database
						
						addDataDatabase();
						bookingID++ ;  
						userID++; 
						
						showSuccessAlert("Registeration Successful", "Your booking has been confirmed.");
						if(admin == "admin") {
							new OptionsPageController(s);
						}else {
							new RoomPageController(s, null);
						};
						
						
					} ; 
					
				}else {
					
				}
			}else {
				// Not Sufficient...
				// Showing an alert box
				showAlert("Rooms not sufficient","The rooms you have selected are not enough to accomodate the number of guests. Please select more rooms");
			}
			
			
		});
		
		
		
		
	}; 
	
	
	
	
	
	
	public ArrayList <Room> roomsAvailable(String room_type, Date check_In, Date check_Out){
		ArrayList <Room> roomsAvailable = new ArrayList<>();
		String connectionString = "jdbc:sqlite:database.db"; 
		try (Connection connection = DriverManager.getConnection(connectionString)) {
            String sql = "SELECT t.RoomID, t.RoomDesc, t.Rate "
            		+ "FROM " + room_type +" t LEFT OUTER JOIN Booking_" + room_type 
            		+ " bs ON t.RoomID = bs.RoomID "
            		+ "LEFT OUTER JOIN Booking b ON bs.BookingID = b.BookID "
            		+ "GROUP BY t.RoomID, t.RoomDesc, t.Rate "
            		+ "HAVING IFNULL(SUM(? <= b.Check_Out AND ? >= b.Check_In), 0) == 0; ";
            
           
            
            // Create a PreparedStatement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set parameters
                preparedStatement.setDate(1,check_In);
                preparedStatement.setDate(2, check_Out);
                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Process the result set
                        // Example: String columnValue = resultSet.getString("column_name");
                    	int id = resultSet.getInt(1);
                    	String desc = resultSet.getString(2);
                    	double rate = resultSet.getDouble(3);
                    	Room room = new Room(id, rate,desc, roomType);
                    	roomsAvailable.add(room);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        };
		
		return roomsAvailable;
	}
	
	
	public void populateListView(ArrayList<Room> availableRooms, ArrayList<Room> selectedRooms) {
		view.getRoomsList().getItems().clear();
		for(int i = 0; i < availableRooms.size() ; i++) {
			CheckBox checkBox = new CheckBox(availableRooms.get(i).getDesc() + "  Rate: $" + availableRooms.get(i).getRate() + " per day");
			for(int i2 = 0; i2 < selectedRooms.size(); i2++) {
				if(availableRooms.get(i).getId() == selectedRooms.get(i2).getId()) {
					checkBox.setSelected(true);
					i2 = selectedRooms.size();
				};
			}; 
			view.getRoomsList().getItems().add(checkBox);
			int currentIndex = i;
			checkBox.selectedProperty().addListener((observable, oldValue, newValue)->{
				if(newValue) {
					
					// If the checkbox is selected, currentIndex
					totalPrice.set(totalPrice.get() + availableRooms.get(currentIndex).getRate());
					addToTheList(currentIndex);
					
				}else {
					// If the checkbox is deselcted
					totalPrice.set(totalPrice.get() - availableRooms.get(currentIndex).getRate());
					removeFromTheList(currentIndex);
				};
			});
			
		}; 
	}
	
	public void addToTheList(int index) {
		if(roomType.equals("Single")) {
			singleRoom.add(availableRooms.get(index));
		}else if(roomType.equals("Double")) {
			doubleRoom.add(availableRooms.get(index));
		}else if(roomType.equals("Delux")) {
			deluxRoom.add(availableRooms.get(index));
		}else {
			// Pent House
			pentHouse.add(availableRooms.get(index));
			
		}; 
	};
	
	public void removeFromTheList(int index) {
		if(roomType.equals("Single")) {
			for(int i = 0; i < singleRoom.size(); i++) {
				if(singleRoom.get(i).getId() == availableRooms.get(index).getId()) {
					singleRoom.remove(i);
					i = singleRoom.size();
				} ; 
			}; 
		}else if(roomType.equals("Double")) {
			for(int i = 0; i < doubleRoom.size(); i++) {
				if(doubleRoom.get(i).getId() == availableRooms.get(index).getId()) {
					doubleRoom.remove(i);
					i = doubleRoom.size();
				} ; 
			};
			
		}else if(roomType.equals("Delux")) {
			for(int i = 0; i < deluxRoom.size(); i++) {
				if(deluxRoom.get(i).getId() == availableRooms.get(index).getId()) {
					deluxRoom.remove(i);
					i = deluxRoom.size();
				} ; 
			};
		}else {
			// PentHouse
			for(int i = 0; i < pentHouse.size(); i++) {
				if(pentHouse.get(i).getId() == availableRooms.get(index).getId()) {
					pentHouse.remove(i);
					i = pentHouse.size();
				} ; 
			};
		};
		
	};
	
	private void addDataDatabase() {
		String connString = "jdbc:sqlite:database.db";
		// Queries
		String insertUserTable = "INSERT INTO Users (UserID, Title, FirstName, LastName, Address, Phone, Email) VALUES (?, ?, ?, ?, ?, ?, ?)"; 
		String insertBookingTable = "INSERT INTO Booking (BookID, Book_Date, Check_In, Check_Out, UserID, Amount) VALUES (?, ?, ?, ?, ?, ?)";
		String insertSingle = "INSERT INTO Booking_SingleRoom (BookingID, RoomID) VALUES (?, ?)";
		String insertDouble = "INSERT INTO Booking_DoubleRoom (BookingID, RoomID) VALUES (?, ?)";
		String insertDelux = "INSERT INTO Booking_DeluxRoom (BookingID, RoomID) VALUES (?, ?)";
		String insertPent = "INSERT INTO Booking_PentHouse (BookingID, RoomID) VALUES (?, ?)";
		
		try (
	            // Establish a connection
	            Connection connection = DriverManager.getConnection(connString);
				
	            // Create a prepared statement
	            PreparedStatement preparedStatement = connection.prepareStatement(insertUserTable); 
				PreparedStatement preparedStatement2 = connection.prepareStatement(insertBookingTable);
				PreparedStatement preparedStatement3 = connection.prepareStatement(insertSingle);
				PreparedStatement preparedStatement4 = connection.prepareStatement(insertDouble);
				PreparedStatement preparedStatement5 = connection.prepareStatement(insertDelux);
				PreparedStatement preparedStatement6 = connection.prepareStatement(insertPent);
				
	        ) {
	            // Set values for the parameters
	            preparedStatement.setInt(1, user.getId()); // UserID
	            preparedStatement.setString(2, user.getTitle()); // Title
	            preparedStatement.setString(3, user.getFirstName()); // FirstName
	            preparedStatement.setString(4, user.getLastName()); // LastName
	            preparedStatement.setString(5, user.getAddress()); // Address
	            preparedStatement.setString(6, user.getPhone()); // Phone
	            preparedStatement.setString(7, user.getEmail()); // Email

	            preparedStatement.executeUpdate();
	            
	            // Excuting the second prepared statement
	            preparedStatement2.setInt(1, bookingID);
	            preparedStatement2.setDate(2, Date.valueOf(LocalDate.now()));
	            preparedStatement2.setDate(3, Date.valueOf(checkIn));
	            preparedStatement2.setDate(4, Date.valueOf(checkOut));
	            preparedStatement2.setInt(5, userID);
	            preparedStatement2.setDouble(6, totalPrice.get());
	            
	            preparedStatement2.executeUpdate();
	            
	            // Executing the 3rd query
	            for (int i = 0 ; i < singleRoom.size();i++) {
	            	preparedStatement3.setInt(1, bookingID);
	            	preparedStatement3.setInt(2, singleRoom.get(i).getId());
	            	preparedStatement3.executeUpdate();
	            }; 
	            
	         // Executing the 4th query
	            for (int i = 0 ; i < doubleRoom.size();i++) {
	            	preparedStatement4.setInt(1, bookingID);
	            	preparedStatement4.setInt(2, doubleRoom.get(i).getId());
	            	preparedStatement4.executeUpdate();
	            }; 
	            
	            // Executing the 5th query
	            for (int i = 0 ; i < deluxRoom.size();i++) {
	            	preparedStatement5.setInt(1, bookingID);
	            	preparedStatement5.setInt(2, deluxRoom.get(i).getId());
	            	preparedStatement5.executeUpdate();
	            }; 
	            
	            // Executing the 6th query
	            for (int i = 0 ; i < pentHouse.size();i++) {
	            	preparedStatement6.setInt(1, bookingID);
	            	preparedStatement6.setInt(2, pentHouse.get(i).getId());
	            	preparedStatement6.executeUpdate();
	            }; 
	            
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
	};
	
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    };
    
    private int maxUserID() {
    	int userID = 0; 
    	String sql = "SELECT MAX(UserID) AS MaxUserID FROM Users";
    	String connString = "jdbc:sqlite:database.db";
        try (
            // Establish a connection
            Connection connection = DriverManager.getConnection(connString);

            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            // Check if there is a result
            if (resultSet.next()) {
            	
                // Retrieve the value of MaxUserID
                userID =  resultSet.getInt("MaxUserID");
                
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;
    };
    
    private int maxBookingID() {
    	int bookingID = 0; 
    	String sql = "SELECT MAX(BookID) AS MaxBookID FROM Booking";
    	String connString = "jdbc:sqlite:database.db";
        try (
            // Establish a connection
            Connection connection = DriverManager.getConnection(connString);

            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            // Check if there is a result
            if (resultSet.next()) {
                // Retrieve the value of MaxUserID
                bookingID =  resultSet.getInt("MaxBookID");
               
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingID;
    };
    
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    };
    

    
    
}
