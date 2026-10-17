/**
 * @author Zuri McKee
 *  Mason section 4
 *  4/22/2025
 *  the Main class intantiates references to the Card and GamePane classes
 *  The Card class composes the smaller card components of the memory game, and the GamePane
 *  class is a BorderPane that holds the Cards and the other buttons and UI components. The
 *  CardGridPane class isn't instantiated in main because it's a field of the GamePane class
 **/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.FileNotFoundException;

public class Main extends Application {

    //funny thing is, in the lower levels since I shuffled the images sometimes it ends in a draw because
    //there are no matches

    //sets the scene with the card objects and the other ui objects in the gridpane
    @Override
    public void start(Stage appStage) throws FileNotFoundException {
        Card card = new Card();
        card.setCardAndImageSize(64, 64);
        GamePane gp = new GamePane();
        Scene scene = new Scene(gp);
        appStage.setScene(scene);
        appStage.setTitle("Concentration");
        appStage.show();

    }

    //launches javafx application
    public static void main(String[] args) {
        launch(args);
    }

}
