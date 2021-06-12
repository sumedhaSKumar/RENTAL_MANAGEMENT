
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("view/FXMLDocument.fxml"));
        
        Scene display = new Scene(parent);
       
        stage.setScene(display);
        stage.setTitle("CityLodge Rental Room Management");
       
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
