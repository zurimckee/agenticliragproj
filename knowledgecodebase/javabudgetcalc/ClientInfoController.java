import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientInfoController {
    Client client = null;
    @FXML
    Button btnCancel;
    @FXML
    TextField txtFirstName;
    @FXML
    TextField txtLastName;
    @FXML
    TextField txtHomeAddress;
    @FXML
    TextField txtPhoneNumber;
    @FXML
    TextField txtEmail;

    /***
     * initialize any properties needed by event handlers or controls.
     * @param client  Passed by the caller to give this GUI window access to th
     *                client data.
     */
    public void initData(Client client) {
        //link the client parameter with the class client property so
        //that it will be around when the event handlers are called.
        this.client = client;


        //load values into TextFields for editing
        txtFirstName.setText(client.getFirstName());
        txtLastName.setText(client.getLastName());
        //#### Add Code to populate the Contact part of the client
        txtHomeAddress.setText(client.getContact().getHomeAddress());
        txtPhoneNumber.setText(client.getContact().getPhone());
        txtEmail.setText(client.getContact().getEmail());
        



    }
    /***
     * Handler for the save button.
     */
    @FXML
    public void onBtnSaveClick() {
        //#### Copy the other TextField values back to the client object
        client.setFirstName(txtFirstName.getText());
        client.setLastName(txtLastName.getText());
        client.getContact().setHomeAddress(txtHomeAddress.getText());
        client.getContact().setPhone(txtPhoneNumber.getText());
        client.getContact().setEmail(txtEmail.getText());


        //Show save confirmation
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("BudgetTracker Alert");
        info.setHeaderText("Update Client");
        info.setContentText("The client info has been updated.");
        info.showAndWait();

        //Close the window/stage Note: btnCancel is used just get a handle on the enclosing stage.
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();

    }

    /***
     * Handler for the Cancel Button
     */
    @FXML
    public void onBtnCancelClick() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
