import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created By:  Kelvin S. Bryant for COMP167.
 * ####You can go to AboutGUI.fxml in the resources directory and customize
 * the information stored in the labels.
 */
public class AboutController {

    @FXML
    Button btnClose;

    @FXML
    protected void onBtnCloseClick() {

        //Close the window/stage
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

}

