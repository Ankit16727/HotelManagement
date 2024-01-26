package contollers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import views.Login;

public class LogInController {
	Login view = new Login();
	public LogInController(Stage s) {
		// Setting up view
		Scene scene = new Scene(view.setUpScene(),400,500);
		s.setScene(scene);
		
		// Setting up actions
		setUpActions(s);
	};
	
	
	public void setUpActions(Stage s) {
		view.getSubmit().setOnAction(event->{
			if(view.getEmailInput().getText().isEmpty() || view.getPasswordInput().getText().isEmpty()) {
				showAlert("Cridentials Incomplete","Both the fields are required.");
				
			}else {
				// Trying to find the admin in the database
				String sql = "SELECT * FROM Admin WHERE AdminUserName = ? AND AdminPassword = ?";
				String connString = "jdbc:sqlite:database.db";
		        try (
		            // Establish a connection
		            Connection connection = DriverManager.getConnection(connString);

		            // Create a prepared statement
		            PreparedStatement preparedStatement = connection.prepareStatement(sql)
		        ) {
		            // Set values for the parameters
		            preparedStatement.setString(1, view.getEmailInput().getText());
		            preparedStatement.setString(2, view.getPasswordInput().getText());

		            // Execute the query and get the result set
		            ResultSet resultSet = preparedStatement.executeQuery();

		            // Check if there is a result
		            if (resultSet.next()) {
		                // Admin found, retrieve other details if needed
		                new OptionsPageController(s);
		            } else {
		                showAlert("Wrong Cridentials","The user name or the password is incorrect. Please try again.");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
				
				
			} ;
			
		});
		
	};
	
	
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    };
}
