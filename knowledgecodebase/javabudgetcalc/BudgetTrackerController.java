import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class BudgetTrackerController {

    FXMLLoader fxmlLoader = null;
    Client client = new Client();
    String[] params;
    File file = null;

    public void initData(String[] params) {
        if (params.length > 0) {
            file = new File(params[0]);
            client.loadClientFile(file);
        }
    }

    private Stage createDialog(String FXMLFile) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource(FXMLFile));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    @FXML
    protected void onAddTransactionMenuItemClick() throws IOException {


        Stage stage = createDialog("TransactionGUI.fxml");
        TransactionController transactionController = fxmlLoader.getController();
        transactionController.initData(client);
        stage.showAndWait();

    }

    @FXML
    protected void onHelpAboutMenuItemClick() throws IOException {
        Stage stage = createDialog("AboutGUI.fxml");
        stage.setMaxHeight(200);
        stage.setMaxWidth(200);
        stage.showAndWait();
    }

    @FXML
    protected void onDisplayBudgetClick() throws IOException {
        Stage stage = createDialog("BudgetOnlyReportGUI.fxml");
        BudgetReportController budgetReportController = fxmlLoader.getController();
        budgetReportController.initData(client, 0);

        stage.setMaxHeight(600);
        stage.setMaxWidth(500);
        stage.showAndWait();
   }

    @FXML
    protected void onFullBudgetClick() throws IOException {
        Stage stage = createDialog("BudgetOnlyReportGUI.fxml");
        BudgetReportController budgetReportController = fxmlLoader.getController();
        budgetReportController.initData(client, 1);
//
        stage.setMaxHeight(600);
        stage.setMaxWidth(500);
        stage.showAndWait();
    }

    @FXML
    protected void onCategoryReportMenuItemClick() throws IOException {
        Stage stage = createDialog("BudgetCategoryReportGUI.fxml");
        BudgetCategoryReportController budgetCategoryReportController = fxmlLoader.getController();
        budgetCategoryReportController.initData(client);

        stage.setMaxHeight(600);
        stage.setMaxWidth(500);
        stage.showAndWait();
    }

    @FXML
    public void onFileExitMenuItemClick() {
        Platform.exit();
    }

    @FXML
    protected void onFileOpenMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Client File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            client.loadClientFile(selectedFile);
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("BudgetTracker Alert");
            info.setHeaderText("Open File");
            info.setContentText("The budget for " + client.getFirstName() + " " +
                    client.getLastName() + " has been loaded.");
            info.showAndWait();
        }
    }

    @FXML
    protected void onFileSaveMenuItemClick() {
        if (file == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Client File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            file = fileChooser.showOpenDialog(null);
        }
        if (file != null) {
            client.saveClientFile(file);
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("BudgetTracker Alert");
            info.setHeaderText("Save File");
            info.setContentText("The budget for " + client.getFirstName() + " " +
                    client.getLastName() + " has been saved.");
            info.showAndWait();
        }

    }

    @FXML

    protected void onEditClientInfoClick() throws IOException {
        Stage stage = createDialog("ClientInfoGUI.fxml");
        ClientInfoController clientInfoController = fxmlLoader.getController();
        clientInfoController.initData(client);

        stage.setMaxHeight(600);
        stage.setMaxWidth(500);
        stage.showAndWait();
    }

}
