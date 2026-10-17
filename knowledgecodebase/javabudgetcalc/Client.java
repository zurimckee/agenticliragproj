/**
 *
 *  @author Zuri McKee
 *  Mason section 4
 *  2/19/2025
 * the Client class stores the total monthly amount of all budget categories, and also the client's total income.
 *  - the class contains a no-arg constructor that initializes the variables with default values
 *  - a constructor with parameters to initialize the attributes with specific values
 *  - getter and setter methods to access and modify private fields
 *  - a loadClientFile method that 
 *  - a toString method to output a formatted string representation of the class
 */
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.jar.JarFile;

public class Client {
    private String firstName;
    private String lastName;
    private Contact contact;
    private Budget budget;
    private Tracker tracker;

    /**
     *
     * Client no arg constructor initialized with java default values
     */
    public Client() {
        firstName = null;
        lastName = null;
        contact = new Contact();
        budget = new Budget();
        tracker = new Tracker();
    }


    /**
     * @param fname
     * @param lname
     * BudgetCategory arg constructor that sets param values to BudgetCategory class private helper values.
     */
    public Client(String fname, String lname){
        this.firstName = fname;
        this.lastName = lname;
        this.contact = new Contact();
        this.budget = new Budget();
        this.tracker = new Tracker();
    }

    /**
     *
     * accessor - accesses a private variable and returns it
     * @return String firstName
     *
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     *
     *
     * accessor - accesses a private variable and returns it
     * @return String lastName
     *
     */
    public String getLastName() {
        return lastName;
    }



    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     *
     *
     * accessor - accesses a private variable and returns it
     * @return contact
     *
     */
    public Contact getContact() {
        return this.contact;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     *
     *
     * accessor - accesses a private variable and returns it
     * @return budget
     *
     */
    public Budget getBudget() {
        return budget;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param budget
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    /**
     *
     *
     * accessor - accesses a private variable and returns it
     * @return tracker
     *
     */
    public Tracker getTracker() {
        return tracker;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param tracker
     */
    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    /**
     * loads the client's data from the file inputted through the command line or through JFileChooser if no file is inputted, reads the file containing
     * the client's data, and assigns elements in the file to class members.
     * @param fileName
     */
    public void loadClientFile(File fileName)  {
        try {
            if (fileName == null || !fileName.exists()) {
                JFileChooser filechooser = new JFileChooser();
                filechooser.setDialogTitle("Select a file");
                filechooser.showOpenDialog(null);
                fileName = filechooser.getSelectedFile();
            }

            Scanner scnr = new Scanner(fileName);
            firstName = scnr.nextLine();
            lastName = scnr.nextLine();
            String address = scnr.nextLine();
            String pN = scnr.nextLine();
            String email = scnr.nextLine();
            setContact(new Contact(address, pN, email));
            //first half of file, handles budget/budget category

            budget = new Budget();
            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();
                if (line.equals("0")) {
                    break;
                } else {
                    String[] budLine = line.split(" ");
                    double amount = Double.parseDouble(budLine[0]);
                    int catType = Integer.parseInt(budLine[1]);
                    String catName = budLine[2];
                    String catDesc = scnr.nextLine();

                    BudgetCategory bc = new BudgetCategory(catName, catDesc, amount, catType);
                    budget.addCategory(bc);
                }
            }
            setBudget(budget);

            //transaction and tracker part of the file starts at the 0
            tracker = new Tracker();
            while (scnr.hasNextLine()) {
                String category = scnr.nextLine();
                if (scnr.hasNextLine()) {
                    String line2 = scnr.nextLine();
                    String[] lineDos = line2.split(" ");
                    String date = lineDos[0];
                    double amount = Double.parseDouble(lineDos[1]);

                    if (scnr.hasNextLine()) {
                        String descr = scnr.nextLine();
                        Transaction trsc = new Transaction(date, amount, descr);
                        trsc.setCategory(category);
                        tracker.addTransaction(trsc);
                    }

                }
            }
            setTracker(tracker);
            scnr.close();
        } catch (FileNotFoundException e){
            System.out.println(e);
        }

    }


    /**
     * saves the clients data from a file given through the actual parameter, saves the client's data in the file into the
     * class
     * @param fileName
     */
    public void saveClientFile(File fileName) {
        //read file, save data in assigned fields
        //everything before the zero
        try {
            FileOutputStream fileStream = new FileOutputStream(fileName);
            PrintWriter pw = new PrintWriter(fileStream);

            pw.println(getFirstName());
            pw.println(getLastName());

            pw.println(contact.toString());

            for(int i=0; i< budget.getCategoriesSize(); i++){
                pw.println(budget.getCategory(i).toString());
            }

            pw.println("0");
            for (int i=0; i < tracker.getTransactionsSize();i++){
                pw.println(tracker.getTransaction(i).toString());
            }
            pw.flush();
            pw.close();

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }


    }


    /**
     * returns the client's budget information, contact information, budget expenditure information
     * total expenditure amount, the income sources, source totals, and then the balance of expenditures and income
     * @return a formatted string with all of that information
     */
    public String getBudgetReport(){
        StringBuilder sb = new StringBuilder();

        sb.append("\n" + firstName + " " + lastName + "\n" + contact.toString() + "\n" + "\n");
        double totalIncome = 0.0;
        double totalAllo = 0.0;
        sb.append("Expenses" + "\n");
        for(int i = 0; i < budget.getCategoriesSize(); i++) {
            BudgetCategory bc = budget.getCategory(i);
            if (bc.getCategoryType() == 0) { //expenses
                String format1 = String.format("   %-20s %10.2f %-50s\n", bc.getCatName(), bc.getAmount(), bc.getCatDescription());
                sb.append(format1);
            } else {
                //sb.append("Maintenence" + "\n");
                String format2 = String.format("   %-20s %10.2f %-50s\n", bc.getCatName(), bc.getAmount(), bc.getCatDescription());
                sb.append(format2);
                totalAllo += bc.getAmount();
            }

            budget.setTotalAllocated(totalAllo);

        }


        sb.append("\n" + "\n");


        sb.append("Unallocated income: " + budget.getAllocationBalance() + "\n" + "\n" + "\n");
        sb.append("Total Allocated: " + budget.getTotalAllocated() + "\n"+ "\n");

        sb.append("Income" + "\n");
        for(int j = 0;j < budget.getCategoriesSize();j++) {
            BudgetCategory bc = budget.getCategory(j);
            if (bc.getCategoryType() == 1) { //income
                String format3 = String.format("   %-20s %10.2f %-50s\n", bc.getCatName(), bc.getAmount(), bc.getCatDescription());
                sb.append(format3);
                totalIncome += bc.getAmount();


            }
        }
        sb.append("\n" + "\n");
        budget.setTotalIncome(totalIncome);
        sb.append("Total income: " + budget.getTotalIncome() + "\n");


        return sb.toString();


    }

    /**
     *  returns the client's contact information along with their monthly expenditure amount with details such as the
     *  transaction category, the date of the transaction, the amount, and description.
     * @return a formatted string version of all of that information
     */
    public String getTransactionReport(){
        StringBuilder sb = new StringBuilder();
        sb.append("Transaction Report" + "\n" + "\n");


        for (int i=0;i<budget.getCategoriesSize();i++){ //iterates thru the budget categoris to access the types
            if(budget.getCategory(i).getCategoryType() == 0){ //filters out the budget categories with type income bc that's
                sb.append("Budget Category: " + budget.getCategory(i).getCatName() + "\n"); // adds the budget category name
                String format2 = String.format("Allocated Budget Amount: %.2f\n", budget.getCategory(i).getAmount()); //adds the amount of each budget category
                sb.append(format2);
                double catBalance = 0.0;
                for(int j=0;j<tracker.getTransactionsSize();j++){ //iterates thru each transaction in transactions arraylist
                    if (budget.getCategory(i).getCatName().equals(tracker.getTransaction(j).getCategory())){ //checks to see if budget category name is equal to transaction name
                        String format = String.format("   %-20s %10.2f %-50s\n", tracker.getTransaction(j).getDate(), tracker.getTransaction(j).getAmount(), tracker.getTransaction(j).getDescription()); //formats the string with info
                        sb.append(format); //adds formatted string to string builder
                        catBalance += tracker.getTransaction(j).getAmount(); //adds transaction amounts together


                    }
                     //adds each amount for transactions to catBalance
                    //subtract transactions from budget amount
                }
                double totalBal = budget.getCategory(i).getAmount() - catBalance;
                String fs = String.format("Category Balance: %.2f", totalBal);
                sb.append(fs + System.lineSeparator() + System.lineSeparator());
            }
        }
        return sb.toString();
    }

    /*
    * creates a new Contact object called clientContact and uses member access operators to access the getHomeAddress method
    * then does the same thing to get the client's phone number
    * @return formatted String of Budget class members
     */
    @Override
    public String toString(){
        Contact clientContact = new Contact();
        return getFirstName() + System.lineSeparator() + getLastName() + System.lineSeparator() + clientContact.toString();
    }



}
