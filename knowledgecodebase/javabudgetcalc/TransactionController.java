import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created By:  Kelvin S. Bryant for COMP167
 */
public class TransactionController {

    @FXML
    TextField txtAmount;
    @FXML
    TextField txtDate;
    @FXML
    TextField txtDescription;
    @FXML
    Button btnSave;
    @FXML
    Button btnCancel;
    @FXML
    ComboBox<String> cmbCategory;
    Client client;

    /**
     * Enables the caller to pass the student grade data to the Add Assignment form.
     *
     * @param client the client object
     */
    public void initData(Client client) {
        this.client = client;
        int numBudgetCategories = client.getBudget().getCategoriesSize(); //#### write code that returns the # of budget categories
        String[] categories = new String[numBudgetCategories];

        //##### Add code to copy each category name into the categories array.
        for(int i =0;i<numBudgetCategories; i++){
            categories[i] = client.getBudget().getCategory(i).getCatName();
        }

        //Load the categories array items into the category ComboBox
        cmbCategory.getItems().addAll(FXCollections
                .observableArrayList(categories));
    }

    @FXML
    protected void onComboClick() {
        //No Action needed.
    }

    @FXML
    protected void onBtnCancelClick() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onBtnSaveClick() {
        //####
        //  Use the information in the Category ComboBox and the TextFields to populate a new Assignment Object.
        Transaction transaction = new Transaction();

        transaction.setAmount(Double.parseDouble(txtAmount.getText()));
        transaction.setDate(txtDate.getText());
        transaction.setDescription(txtDescription.getText());

        //  Add that new Transaction object to your client object
        client.getTracker().addTransaction(transaction);


        //Show save confirmation
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("BudgetTracker Alert");
        info.setHeaderText("Add Transaction");
        info.setContentText("The transaction has been added.");
        info.showAndWait();

        //Close the window/stage
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

}
