import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class BudgetCategoryReportController {
    Client client = null;

    @FXML
    ComboBox<String> cmbCategory2;
    @FXML
    TextArea txtReport;
    @FXML
    Button btnClose;

    public void initData(Client client) {
        this.client = client;
        int numBudgetCategories = client.getBudget().getCategoriesSize();//####write code that returns the # of budget categories
        String[] categories = new String[numBudgetCategories];

        txtReport.setStyle("-fx-font-family: Consolas");
        txtReport.setEditable(false);

        //##### Add code to copy each category name into the categories array.
        for(int i =0;i<numBudgetCategories; i++){
            categories[i] = client.getBudget().getCategory(i).getCatName();
        }


        cmbCategory2.getItems().addAll(FXCollections
                .observableArrayList(categories));

    }

    /***
     * Event handler for a new comboBox selection.
     */
    @FXML
    protected void onComboClick2() {
        String selectedValue = cmbCategory2.getValue().trim();
        //####
        //Find the index of the BudgetCategory object that matches the
        //selected comboBox value -- selectedValue

        int index = client.getBudget().getCategories().indexOf(selectedValue);


        String sOutput = "";
        //####
        //Populate a string (sOutput) containing the transaction report for the selected category.

       StringBuilder sb = new StringBuilder();
        for(int i=0;i<client.getBudget().getCategoriesSize();i++) {
            double catBalance = 0;
            if (client.getBudget().getCategory(i).getCatName().equals(selectedValue)) {
                 sb.append(String.format("Budget Category:  %s\nAllocated Budget Amount: %.2f\n", client.getBudget().getCategory(i).getCatName(), client.getBudget().getCategory(i).getAmount()));
                catBalance = 0.0;
                for (int j = 0; j < client.getTracker().getTransactionsSize(); j++) {
                    if (client.getBudget().getCategory(i).getCatName().equals(client.getTracker().getTransaction(j).getCategory())) {
                        sb.append(String.format("   %-20s %10.2f %-50s\n", client.getTracker().getTransaction(j).getDate(), client.getTracker().getTransaction(j).getAmount(), client.getTracker().getTransaction(j).getDescription()));
                        catBalance += client.getTracker().getTransaction(j).getAmount();
                    }


                }
            }
            double totalBal = client.getBudget().getCategory(i).getAmount() - catBalance;
            sb.append(String.format("Category Balance: %.2f", totalBal));
        }
        sOutput = sb.toString();



        //your report should include the budget amount for the category, all the
        //transactions, and the current balance.  Use String.format to get neat output.
        txtReport.setText(sOutput);

        //You can also just create a new Client method that returns the transaction report
        //by passing it the category as a parameter.
        //txtReport.setText(client.getTransactionReport());



    }

    @FXML
    protected void onBtnCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}