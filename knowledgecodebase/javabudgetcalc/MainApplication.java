import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Get the command line arguments so that they can be passed to the Budget GUI.
        String[] params = getParameters().getRaw().toArray(new String[0]);

        //Load the main Budget GUI FXML file and create the GUI window.
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("BudgetTrackerGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,350 ); //, 600, 500);

        //####  Create your own application title
        stage.setTitle("Money Handler");
        stage.setScene(scene);
        BudgetTrackerController budgetTrackerController = fxmlLoader.getController();
        budgetTrackerController.initData(params);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
