package contollers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Bill;
import models.Booking;
import models.Room;
import views.AllBookingPage;
import views.AvailabeRoom;
import views.BillServicePage;
import views.OptionsPage;
import javafx.beans.property.*;

public class OptionsPageController {
	OptionsPage view = new OptionsPage();
	private int noBookings = 0; 
	ObservableList <Room> availableRoomsList = FXCollections.observableArrayList();
	@SuppressWarnings("unchecked")
	public OptionsPageController(Stage s) {
		Scene scene = new Scene(view.setUpView(),400,500);
		s.setScene(scene);
		
		
		// Setting up actions
		view.getSubmit().setOnAction(event->{
			int selectedIndex = view.getOptionsListView().getSelectionModel().getSelectedIndex();
			if(selectedIndex == 0) {
				new RoomPageController(s, "admin");
			}else if(selectedIndex == 1) {
				// Get details about a booking.
				noBookings = 0;
				BillServicePage billService = new BillServicePage("Booking Details",getAllBookings());
				// Setting up actions
				billService.getBookingIDList().setOnAction(event1->{
					int bookingID = billService.getBookingIDList().getValue();
					Booking booking = getDetailsBooking(bookingID);
					billService.populateTextArea(booking);
				});
				
				billService.getDiscount().textProperty().addListener((observable,oldValue,newValue)->{
					String text = billService.getDiscount().getText();
					int bookingID = billService.getBookingIDList().getValue();
					Booking booking = getDetailsBooking(bookingID);
					if(text != null && text != "") {
						double discount = Double.parseDouble(text);
						double amountAfterTax = booking.getTotal().get() - (discount/100) * booking.getTotal().get() ;
						double amountAfterTaxFinal = Math.round(amountAfterTax * 100.0 /100.0);
						billService.getAfterDiscount().setText(String.valueOf(amountAfterTaxFinal));
					}else{
						billService.getAfterDiscount().setText("");
					};
				});
				Optional <Bill> result = billService.showAndWait();
				if(result.isPresent()) {
					Bill bill = result.get();
					double discount = bill.getBooking().getTotal().get() - ((bill.getDiscount()/100) * bill.getBooking().getTotal().get());
					double discountPrice = Math.round(discount * 100/100);
					// Save the change to the database
					String connString = "jdbc:sqlite:database.db";
					try {
			            // Establish a connection
			            Connection connection = DriverManager.getConnection(connString);

			            // Prepare the update statement
			            String updateQuery = "UPDATE Booking SET Amount = ? WHERE BookID = ?";
			            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
			                // Set parameters
			                preparedStatement.setDouble(1, discountPrice);
			                preparedStatement.setInt(2, bill.getBooking().getId().get());

			                // Execute the update statement
			                preparedStatement.executeUpdate();
			                showSuccessAlert("Success","The amount of the guest has been adjusted.");
			                
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			        }
;				}; 
			}else if(selectedIndex == 2) {
				noBookings = 0;
				ArrayList<Booking> allBookingsList = getAllBookings();
				// Show the current bookings
				AllBookingPage allBookings = new AllBookingPage("Total Bookings:",allBookingsList, noBookings);
				allBookings.showAndWait();
				
			}else if(selectedIndex == 3) {
				
				AvailabeRoom availableRooms = new AvailabeRoom("Total Bookings:");
				availableRooms.getRoomIDColumn().setCellValueFactory(cellValue-> cellValue.getValue().getIdProperty().asObject());
				availableRooms.getRoomDescColumn().setCellValueFactory(cellValue-> cellValue.getValue().getDescProperty());
				availableRooms.getRoomTypeColumn().setCellValueFactory(cellValue->cellValue.getValue().getTypeProperty());
				availableRooms.getRoomRateColumn().setCellValueFactory(cellValue -> {
				    double rate = cellValue.getValue().getRate();
				    return new SimpleStringProperty("$" + String.valueOf(rate));
				});
				availableRooms.getAvailableRoomsTabel().setItems(availableRoomsList);
				
				availableRooms.getStartDate().valueProperty().addListener((observable, oldValue, newValue) -> {
					if(availableRoomsList != null) {
						availableRoomsList.clear();
					};
					LocalDate startDate = availableRooms.getStartDate().getValue(); 
					LocalDate endDate = availableRooms.getEndDate().getValue(); 
					if(startDate != null && endDate != null) {
						getAvailableRooms(Date.valueOf(startDate), Date.valueOf(endDate));
					};
		        });
				
				availableRooms.getEndDate().valueProperty().addListener((observable, oldValue, newValue) -> {
					if(availableRoomsList != null) {
						availableRoomsList.clear();
					};
					LocalDate startDate = availableRooms.getStartDate().getValue(); 
					LocalDate endDate = availableRooms.getEndDate().getValue(); 
					if(startDate != null && endDate != null) {
						getAvailableRooms(Date.valueOf(startDate), Date.valueOf(endDate));
					};
					
		        });
				
				
				availableRooms.showAndWait();
				
			}else {
				// 4 Closing the application
				Platform.exit();
				
			};
			
		});
		
	} ; 
	
	
	
	public ArrayList<Booking> getAllBookings(){
		ArrayList<Booking> allBookings = new ArrayList<>() ; 
		
		
		String connString = "jdbc:sqlite:database.db";
		// Getting all the bookings
		String sql = "SELECT BookID , Check_In, Check_Out, Amount FROM Booking";
		String getUserName = "SELECT u.FirstName, u.LastName "
				+ "FROM Users u "
				+ "JOIN Booking b ON u.UserID = b.UserID "
				+ "WHERE b.BookID = ?;";
		
		String getSingleRoom = "SELECT sr.Rate "
				+ "FROM Booking b "
				+ "JOIN Booking_SingleRoom bs ON b.BookID = bs.BookingID "
				+ "JOIN SingleRoom sr ON bs.RoomID = sr.RoomID "
				+ "WHERE b.BookID = ?;";
		
		String getDoubleRoom = "SELECT sr.Rate "
				+ "FROM Booking b "
				+ "JOIN Booking_DoubleRoom bs ON b.BookID = bs.BookingID "
				+ "JOIN DoubleRoom sr ON bs.RoomID = sr.RoomID "
				+ "WHERE b.BookID = ?;";
		
		String getDeluxRoom = "SELECT sr.Rate "
				+ "FROM Booking b "
				+ "JOIN Booking_DeluxRoom bs ON b.BookID = bs.BookingID "
				+ "JOIN DeluxRoom sr ON bs.RoomID = sr.RoomID "
				+ "WHERE b.BookID = ?;";
		
		String getPentHouse = "SELECT sr.Rate "
				+ "FROM Booking b "
				+ "JOIN Booking_PentHouse bs ON b.BookID = bs.BookingID "
				+ "JOIN PentHouse sr ON bs.RoomID = sr.RoomID "
				+ "WHERE b.BookID = ?;";

        try (
            // Establish a connection
            Connection connection = DriverManager.getConnection(connString);

            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        	PreparedStatement preparedStatement2 = connection.prepareStatement(getUserName);
        	PreparedStatement preparedStatement3 = connection.prepareStatement(getSingleRoom);
        	PreparedStatement preparedStatement4 = connection.prepareStatement(getDoubleRoom);
        	PreparedStatement preparedStatement5 = connection.prepareStatement(getDeluxRoom);
        	PreparedStatement preparedStatement6 = connection.prepareStatement(getPentHouse);
            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            // Iterate through the result set
            while (resultSet.next()) {
                // Retrieve the booking ID
                int bookingId = resultSet.getInt("BookID");
                LocalDate checkIn = resultSet.getDate(2).toLocalDate();
                LocalDate checkOut = resultSet.getDate(3).toLocalDate();
                double total = resultSet.getDouble(4);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String checkInDate = checkIn.format(formatter);
                String checkOutDate = checkOut.format(formatter);
                String fullName = ""; 
                int noRooms = 0;
                
                ArrayList<String> roomTypes = new ArrayList<>(); 
                noBookings++;
                
                // Getting the user name
                preparedStatement2.setInt(1, bookingId);
                
                ResultSet getUser = preparedStatement2.executeQuery();
                while(getUser.next()) {
                	fullName = getUser.getString(1) + " " + getUser.getString(2);
                };
                
                // Getting the single rooms associated to a booking.
                preparedStatement3.setInt(1, bookingId);
                ResultSet getSingle = preparedStatement3.executeQuery();
                
                while(getSingle.next()) {
                	roomTypes.add("Single");
                	noRooms++;
                	
                };
                
                // Get the double
                preparedStatement4.setInt(1, bookingId);
                ResultSet getDouble = preparedStatement4.executeQuery();
                
                while(getDouble.next()) {
                	roomTypes.add("Double");
                	noRooms++;
                	
                };
                
                
                
                // Get the dexlux
                preparedStatement5.setInt(1, bookingId);
                ResultSet getDelux = preparedStatement5.executeQuery();
                
                while(getDelux.next()) {
                	roomTypes.add("Delux");
                	noRooms++;
                	
                };
                
                
                
                
                // Get the pent houses
                preparedStatement6.setInt(1, bookingId);
                ResultSet getPent = preparedStatement6.executeQuery();
                
                while(getPent.next()) {
                	roomTypes.add("Pent House");
                	noRooms++;
                	
                };
                
                double avg = Math.round((total/noRooms) * 100.0) / 100.0;
                double totalPrice = Math.round(total * 100/100);
                
                Booking booking = new Booking(bookingId, fullName,roomTypes, checkInDate,checkOutDate , noRooms, avg, totalPrice);
                allBookings.add(booking);
                
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		
		
		
		return allBookings; 
		
	};
	
	
	
	
	private void getAvailableRooms(Date checkIn, Date checkOut){
			
			
			String connString = "jdbc:sqlite:database.db";
			
			String getSingleRoom = "SELECT t.RoomID, t.RoomDesc, t.Rate "
	        		+ "FROM SingleRoom t LEFT OUTER JOIN Booking_SingleRoom" 
	        		+ " bs ON t.RoomID = bs.RoomID "
	        		+ "LEFT OUTER JOIN Booking b ON bs.BookingID = b.BookID "
	        		+ "GROUP BY t.RoomID, t.RoomDesc, t.Rate "
	        		+ "HAVING IFNULL(SUM(? <= b.Check_Out AND ? >= b.Check_In), 0) == 0; ";
			
			String getDoubleRoom = "SELECT t.RoomID, t.RoomDesc, t.Rate "
	        		+ "FROM DoubleRoom t LEFT OUTER JOIN Booking_DoubleRoom" 
	        		+ " bs ON t.RoomID = bs.RoomID "
	        		+ "LEFT OUTER JOIN Booking b ON bs.BookingID = b.BookID "
	        		+ "GROUP BY t.RoomID, t.RoomDesc, t.Rate "
	        		+ "HAVING IFNULL(SUM(? <= b.Check_Out AND ? >= b.Check_In), 0) == 0; ";
			
			String getDeluxRoom = "SELECT t.RoomID, t.RoomDesc, t.Rate "
	        		+ "FROM DeluxRoom t LEFT OUTER JOIN Booking_DeluxRoom" 
	        		+ " bs ON t.RoomID = bs.RoomID "
	        		+ "LEFT OUTER JOIN Booking b ON bs.BookingID = b.BookID "
	        		+ "GROUP BY t.RoomID, t.RoomDesc, t.Rate "
	        		+ "HAVING IFNULL(SUM(? <= b.Check_Out AND ? >= b.Check_In), 0) == 0; ";
			
			String getPentHouse = "SELECT t.RoomID, t.RoomDesc, t.Rate "
	        		+ "FROM PentHouse t LEFT OUTER JOIN Booking_PentHouse" 
	        		+ " bs ON t.RoomID = bs.RoomID "
	        		+ "LEFT OUTER JOIN Booking b ON bs.BookingID = b.BookID "
	        		+ "GROUP BY t.RoomID, t.RoomDesc, t.Rate "
	        		+ "HAVING IFNULL(SUM(? <= b.Check_Out AND ? >= b.Check_In), 0) == 0; ";
			
		   try (
		       // Establish a connection
		       Connection connection = DriverManager.getConnection(connString);
		
		       // Create a prepared statement
		       PreparedStatement preparedStatement = connection.prepareStatement(getSingleRoom);
			   PreparedStatement preparedStatement2 = connection.prepareStatement(getDoubleRoom);
			   PreparedStatement preparedStatement3 = connection.prepareStatement(getDeluxRoom);
			   PreparedStatement preparedStatement4 = connection.prepareStatement(getPentHouse);
		   ) {
		       
			   
			   // Getting the single rooms
			   preparedStatement.setDate(1,checkIn);
	           preparedStatement.setDate(2, checkOut);
	           // Execute the query
	           try (ResultSet resultSet = preparedStatement.executeQuery()) {
	               while (resultSet.next()) {
	                   // Process the result set
	                   // Example: String columnValue = resultSet.getString("column_name");
	               	int id = resultSet.getInt(1);
	               	String desc = resultSet.getString(2);
	               	double rate = resultSet.getDouble(3);
	               	Room room = new Room(id, rate,desc, "Single");
	               	availableRoomsList.add(room);
	               }
	           }
	           
			   preparedStatement2.setDate(1,checkIn);
	           preparedStatement2.setDate(2, checkOut);
	           try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
	               while (resultSet2.next()) {
	                   // Process the result set
	                   // Example: String columnValue = resultSet.getString("column_name");
	               	int id = resultSet2.getInt(1);
	               	String desc = resultSet2.getString(2);
	               	double rate = resultSet2.getDouble(3);
	               	Room room = new Room(id, rate,desc, "Double");
	               	availableRoomsList.add(room);
	               }
	           }
	           
			   preparedStatement3.setDate(1,checkIn);
	           preparedStatement3.setDate(2, checkOut);
	           try (ResultSet resultSet3 = preparedStatement3.executeQuery()) {
	               while (resultSet3.next()) {
	                   // Process the result set
	                   // Example: String columnValue = resultSet.getString("column_name");
	               	int id = resultSet3.getInt(1);
	               	String desc = resultSet3.getString(2);
	               	double rate = resultSet3.getDouble(3);
	               	Room room = new Room(id, rate,desc, "Delux");
	               	availableRoomsList.add(room);
	               }
	           }
	           
			   preparedStatement4.setDate(1,checkIn);
	           preparedStatement4.setDate(2, checkOut);
	           try (ResultSet resultSet4 = preparedStatement4.executeQuery()) {
	               while (resultSet4.next()) {
	                   // Process the result set
	                   // Example: String columnValue = resultSet.getString("column_name");
	               	int id = resultSet4.getInt(1);
	               	String desc = resultSet4.getString(2);
	               	double rate = resultSet4.getDouble(3);
	               	Room room = new Room(id, rate,desc, "Pent House");
	               	availableRoomsList.add(room);
	               }
	           }
		       
		   } catch (SQLException e) {
		       e.printStackTrace();
		   }
	   
	
		
		
		
	}; 
	
	private Booking getDetailsBooking(int bookID) {
		Booking booking = null ; 
		
		
		String connString = "jdbc:sqlite:database.db";
		// Getting all the bookings
		String sql = "SELECT BookID , Check_In, Check_Out, Amount FROM Booking WHERE BookID = ?";
		String getUserName = "SELECT u.FirstName, u.LastName "
				+ "FROM Users u "
				+ "JOIN Booking b ON u.UserID = b.UserID "
				+ "WHERE b.BookID = ?;";
		
		String getSingleRoom = "SELECT sr.Rate "
				+ "FROM Booking b "
				+ "JOIN Booking_SingleRoom bs ON b.BookID = bs.BookingID "
				+ "JOIN SingleRoom sr ON bs.RoomID = sr.RoomID "
				+ "WHERE b.BookID = ?;";
		
		String getDoubleRoom = "SELECT sr.Rate "
				+ "FROM Booking b "
				+ "JOIN Booking_DoubleRoom bs ON b.BookID = bs.BookingID "
				+ "JOIN DoubleRoom sr ON bs.RoomID = sr.RoomID "
				+ "WHERE b.BookID = ?;";
		
		String getDeluxRoom = "SELECT sr.Rate "
				+ "FROM Booking b "
				+ "JOIN Booking_DeluxRoom bs ON b.BookID = bs.BookingID "
				+ "JOIN DeluxRoom sr ON bs.RoomID = sr.RoomID "
				+ "WHERE b.BookID = ?;";
		
		String getPentHouse = "SELECT sr.Rate "
				+ "FROM Booking b "
				+ "JOIN Booking_PentHouse bs ON b.BookID = bs.BookingID "
				+ "JOIN PentHouse sr ON bs.RoomID = sr.RoomID "
				+ "WHERE b.BookID = ?;";

        try (
            // Establish a connection
            Connection connection = DriverManager.getConnection(connString);

            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        	PreparedStatement preparedStatement2 = connection.prepareStatement(getUserName);
        	PreparedStatement preparedStatement3 = connection.prepareStatement(getSingleRoom);
        	PreparedStatement preparedStatement4 = connection.prepareStatement(getDoubleRoom);
        	PreparedStatement preparedStatement5 = connection.prepareStatement(getDeluxRoom);
        	PreparedStatement preparedStatement6 = connection.prepareStatement(getPentHouse);
            // Execute the query and get the result set
           
        ) {
        	preparedStatement.setInt(1, bookID);
        	 ResultSet resultSet = preparedStatement.executeQuery();
            // Iterate through the result set
            while (resultSet.next()) {
                // Retrieve the booking ID
                int bookingId = resultSet.getInt("BookID");
                LocalDate checkIn = resultSet.getDate(2).toLocalDate();
                LocalDate checkOut = resultSet.getDate(3).toLocalDate();
                double total = resultSet.getDouble(4);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String checkInDate = checkIn.format(formatter);
                String checkOutDate = checkOut.format(formatter);
                String fullName = ""; 
                int noRooms = 0;
               
                ArrayList<String> roomTypes = new ArrayList<>(); 
                noBookings++;
                
                // Getting the user name
                preparedStatement2.setInt(1, bookingId);
                
                ResultSet getUser = preparedStatement2.executeQuery();
                while(getUser.next()) {
                	fullName = getUser.getString(1) + " " + getUser.getString(2);
                };
                
                // Getting the single rooms associated to a booking.
                preparedStatement3.setInt(1, bookingId);
                ResultSet getSingle = preparedStatement3.executeQuery();
                
                while(getSingle.next()) {
                	roomTypes.add("Single");
                	noRooms++;
                	
                };
                
                // Get the double
                preparedStatement4.setInt(1, bookingId);
                ResultSet getDouble = preparedStatement4.executeQuery();
                
                while(getDouble.next()) {
                	roomTypes.add("Double");
                	noRooms++;
                	
                };
                
                
                
                // Get the dexlux
                preparedStatement5.setInt(1, bookingId);
                ResultSet getDelux = preparedStatement5.executeQuery();
                
                while(getDelux.next()) {
                	roomTypes.add("Delux");
                	noRooms++;
                	
                };
                
                
                
                
                // Get the pent houses
                preparedStatement6.setInt(1, bookingId);
                ResultSet getPent = preparedStatement6.executeQuery();
                
                while(getPent.next()) {
                	roomTypes.add("Pent House");
                	noRooms++;
                	
                };
                
                double avg = Math.round((total/noRooms) * 100.0) / 100.0;
                double totalPrice = Math.round(total * 100.0/100.0);
                
                booking = new Booking(bookingId, fullName,roomTypes, checkInDate,checkOutDate , noRooms, avg, totalPrice);
                
                
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		
        return booking;
		
		
	};
	
	private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    };
	
	
	
	
}
