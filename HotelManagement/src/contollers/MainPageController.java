package contollers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import views.MainPage;

public class MainPageController {
	MainPage view = new MainPage();
	public void setUp(Stage s ) {
		// Setting up the view
		Scene scene = new Scene(view.setUpView(),400,500);
		
		s.setScene(scene);
		
		// Setting up the actions
		view.getKiosk().setOnAction(event->{
			// Setting up the kiosk
			new RoomPageController(s, null);
		});
		
		
		view.getAdmin().setOnAction(event->{
			new LogInController(s);
		});
		
		
	};
	
	
}
