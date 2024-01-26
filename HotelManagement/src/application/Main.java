/**********************************************
#Final Project
Course: APD545 - Semester - 5
Last Name: Thapar
First Name: Ankit
ID: 125698217
Section: ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date: 10 December 2023
**********************************************/
package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import contollers.MainPageController;

import javafx.application.Application;
import javafx.stage.Stage;






public class Main extends Application {
	MainPageController mainPageController = new MainPageController();
	@Override
	public void start(Stage primaryStage) {
		try {
			dataBaseSetup();
			mainPageController.setUp(primaryStage);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Database setup
	// Filling the database with the rooms available
	public void dataBaseSetup() {
		roomsDataBaseSetUp();
		userTableSetUp();
		bookingAndrelationshipTablesSetUp();
	} ; 
	
	public void roomsDataBaseSetUp() {
		
		String connString = "jdbc:sqlite:database.db";
		try(Connection conn = DriverManager.getConnection(connString);
				Statement st = conn.createStatement();){
				
				// Single Room Table
				String singleRoomQuery = "CREATE TABLE IF NOT EXISTS SingleRoom ("
						+ "    RoomID INT PRIMARY KEY,"
						+ "    Rate DOUBLE,"
						+ "    RoomDesc VARCHAR(255)"
						+ ");";
				st.execute(singleRoomQuery); 
				
				// Populating the data into the table
				if(isTableEmpty(conn, "SingleRoom")) {
					String insertSingleRoom = "INSERT INTO SingleRoom (RoomID, Rate, RoomDesc)"
							+ "VALUES"
							+ "    (1, 120.00, '1st floor'),"
							+ "    (2, 150.50, '2nd floor'),"
							+ "    (3, 200.75, '3rd floor'),"
							+ "    (4, 230.25, '4th floor'),"
							+ "    (5, 310.00, '5th floor'),"
							+ "    (6, 440.80, '6th floor');";
					st.executeUpdate(insertSingleRoom);
				};
				
				// Double Room Table
				String doubleRoomQuery = "CREATE TABLE IF NOT EXISTS DoubleRoom ("
						+ "    RoomID INT PRIMARY KEY,"
						+ "    Rate DOUBLE,"
						+ "    RoomDesc VARCHAR(255)"
						+ ");";
				st.execute(doubleRoomQuery);
				
				if(isTableEmpty(conn, "DoubleRoom")) {
					String insertDoubleRoom = "INSERT INTO DoubleRoom (RoomID, Rate, RoomDesc)"
							+ "VALUES"
							+ "    (1, 180.00, '1st floor'),"
							+ "    (2, 200.50, '2nd floor'),"
							+ "    (3, 260.75, '3rd floor'),"
							+ "    (4, 290.25, '4th floor'),"
							+ "    (5, 370.00, '5th floor'),"
							+ "    (6, 510.80, '6th floor');" ; 
					st.executeUpdate(insertDoubleRoom);
				};
				// Delux Room Table
				String deluxRoomQuery = "CREATE TABLE IF NOT EXISTS DeluxRoom ("
						+ "    RoomID INT PRIMARY KEY,"
						+ "    Rate DOUBLE,"
						+ "    RoomDesc VARCHAR(255)"
						+ ");";
				st.execute(deluxRoomQuery);
				
				if(isTableEmpty(conn, "DeluxRoom")) {
					String insertDeluxRoom = "INSERT INTO DeluxRoom (RoomID, Rate, RoomDesc)"
							+ "VALUES"
							+ "    (1, 280.00, '1st floor'),"
							+ "    (2, 300.50, '2nd floor'),"
							+ "    (3, 460.75, '3rd floor'),"
							+ "    (4, 590.25, '4th floor'),"
							+ "    (5, 670.00, '5th floor'),"
							+ "    (6, 710.80, '6th floor');" ; 
					st.executeUpdate(insertDeluxRoom);
				};
				
				// Pent Houses
				String pentHouseQuery = "CREATE TABLE IF NOT EXISTS PentHouse ("
						+ "    RoomID INT PRIMARY KEY,"
						+ "    Rate DOUBLE,"
						+ "    RoomDesc VARCHAR(255)"
						+ ");";
				st.execute(pentHouseQuery);
				
				if(isTableEmpty(conn, "PentHouse")) {
					String insertPentHouse = "INSERT INTO PentHouse (RoomID, Rate, RoomDesc)"
							+ "VALUES"
							+ "    (1, 1280.00, 'General'),"
							+ "    (2, 2300.50, 'Delux');";
					st.executeUpdate(insertPentHouse);
				};
				
				
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	// user or guest table setup
	public void userTableSetUp() {
		
		String connString = "jdbc:sqlite:database.db";
		try(Connection conn = DriverManager.getConnection(connString);
				Statement st = conn.createStatement();){
				String userTableQuery = "CREATE TABLE IF NOT EXISTS Users ("
						+ "    UserID INT PRIMARY KEY,"
						+ "    Title VARCHAR(50),"
						+ "    FirstName VARCHAR(100),"
						+ "    LastName VARCHAR(100),"
						+ "    Address VARCHAR(255),"
						+ "    Phone VARCHAR(20),"
						+ "    Email VARCHAR(255)"
						+ ");";
				st.execute(userTableQuery);
				
				// Creating a table for the admin
				String adminTableQuery = "CREATE TABLE IF NOT EXISTS Admin ("
						+ "    AdminID INT PRIMARY KEY,"
						+ "    AdminUserName VARCHAR(50),"
						+ "    AdminPassword VARCHAR(50)"
						+ ");";
				st.execute(adminTableQuery);
				
				// Inserting values into the admin table
				if(isTableEmpty(conn, "Admin")) {
					String insertAdmin = "INSERT INTO Admin (AdminID, AdminUserName, AdminPassword)"
							+ "VALUES"
							+ "(1, 'admin1', 'password123'),"
							+ "(2, 'admin2', 'password456');"; 
					st.executeUpdate(insertAdmin);
				};
				
				
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		
	};
	
	public void bookingAndrelationshipTablesSetUp() {
		String connString = "jdbc:sqlite:database.db";
		try(Connection conn = DriverManager.getConnection(connString);
				Statement st = conn.createStatement();){
				// Creating the booking table
				String bookingQuery = "CREATE TABLE IF NOT EXISTS Booking ("
						+ "    BookID INT PRIMARY KEY,"
						+ "    Book_Date DATE,"
						+ "    Check_In DATE,"
						+ "    Check_Out DATE,"
						+ "    UserID INT,"
						+ "    Amount DOUBLE,"
						+ "    FOREIGN KEY (UserID) REFERENCES Users(UserID)"
						+ ");";
				st.execute(bookingQuery);
				
				// Relation ship booking single
				String book_single = "CREATE TABLE IF NOT EXISTS Booking_SingleRoom ("
						+ "    BookingID INT,"
						+ "    RoomID INT,"
						+ "    PRIMARY KEY (BookingID, RoomID),"
						+ "    FOREIGN KEY (BookingID) REFERENCES Booking(BookID),"
						+ "    FOREIGN KEY (RoomID) REFERENCES SingleRoom(RoomID)"
						+ ");";
				
				st.execute(book_single);
				
				String book_double = "CREATE TABLE IF NOT EXISTS Booking_DoubleRoom ("
						+ "    BookingID INT,"
						+ "    RoomID INT,"
						+ "    PRIMARY KEY (BookingID, RoomID),"
						+ "    FOREIGN KEY (BookingID) REFERENCES Booking(BookID),"
						+ "    FOREIGN KEY (RoomID) REFERENCES DoubleRoom(RoomID)"
						+ ");";
				
				st.execute(book_double);
				
				String book_delux = "CREATE TABLE IF NOT EXISTS Booking_DeluxRoom ("
						+ "    BookingID INT,"
						+ "    RoomID INT,"
						+ "    PRIMARY KEY (BookingID, RoomID),"
						+ "    FOREIGN KEY (BookingID) REFERENCES Booking(BookID),"
						+ "    FOREIGN KEY (RoomID) REFERENCES DeluxRoom(RoomID)"
						+ ");";
				
				st.execute(book_delux);
				String book_pent = "CREATE TABLE IF NOT EXISTS Booking_PentHouse ("
						+ "    BookingID INT,"
						+ "    RoomID INT,"
						+ "    PRIMARY KEY (BookingID, RoomID),"
						+ "    FOREIGN KEY (BookingID) REFERENCES Booking(BookID),"
						+ "    FOREIGN KEY (RoomID) REFERENCES PentHouse(RoomID)"
						+ ");";
				st.execute(book_pent);
				
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		
		
		
	};
	
	private static boolean isTableEmpty(Connection connection, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return rowCount == 0;
            }
        }

        // Return false if an exception occurs
        return false;
    }
	
	
}
