/**
 * @author Zuri McKee
 *  Mason section 4
 *  2/14/2025
 *  the Main class intantiates references to the BudgetCategory, Transaction, Contact, Client, Budget, and Tracker classes
 *  it assigns the references to reference objects and then prints the class member's values in the default
 *  constructor using the toString methods in each class. the Main class also loads files into the Client class's methods
 *  allowing the Client class to manipulate said files. It also prints out the client's budget and transaction reports.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        /**
         * these print statements print out references to the new class objects clientContact, clientBudgetCategory, clientTransactionclientBudget, and clientClient
         * the print statements output the default values because no values were inputted by the user.
         */
        Scanner scnr = new Scanner(System.in);

        //contact class
        Contact clientContact = new Contact("zm@gmail.com", "1308 salem st", "336-777-9311");
        System.out.println("Contact class: ");
        System.out.println(clientContact.toString());


        String slash = String.valueOf("-").repeat(175);
        System.out.println(slash);

        //budget category class
        System.out.println("Client budget category info: ");
        BudgetCategory clientBudgetCategory = new BudgetCategory("school", "spend on educational things", 25.00, 0);
        System.out.println(clientBudgetCategory.toString());


        String slash2 = String.valueOf("-").repeat(175);
        System.out.println(slash2);

        //transaction class
        Transaction clientTransaction = new Transaction("2/26/25", 35.00, "bought a textbook");
        clientTransaction.setCategory(clientBudgetCategory.getCatName());
        System.out.println("Client transaction: ");
        System.out.println(clientTransaction.toString());

        String slash3 = String.valueOf("-").repeat(175);
        System.out.println(slash3);

        //budget class
        Budget clientBudget = new Budget();
        clientBudget.addCategory(clientBudgetCategory);
        clientBudget.setTotalAllocated(3000);
        clientBudget.setTotalIncome(4500);
        clientBudget.getTotalAllocated();
        System.out.println("Client Budget: ");
        System.out.println(clientBudget.toString());


        String slash4 = String.valueOf("-").repeat(175);
        System.out.println(slash4);

        //tracker class
        Tracker clientTracker = new Tracker();

        clientTracker.addTransaction(clientTransaction);
        clientTracker.setBalance(100);
        System.out.println("Client tracker: ");
        System.out.println(clientTracker.toString());

        String slash5 = String.valueOf("-").repeat(175);
        System.out.println(slash5);

        //client class
        Client clientClient = new Client("zuri", "mckee");
        clientClient.setContact(clientContact);
        clientClient.setBudget(clientBudget);
        clientClient.setTracker(clientTracker);

        System.out.println("Client:  ");

        System.out.println(clientClient.getFirstName() + " " +  clientClient.getLastName());
        System.out.println(clientClient.getContact().toString());

        clientClient.loadClientFile(new File("budgetdatanew.txt")); //loads a file into the class fields

        //if no file is inputted in commandline
        File inputFile;
        if (args.length > 0) {
            inputFile = new File(args[0]); // Use command-line argument
        } else {
            // Prompt the user for input if no argument is given
            System.out.print("Enter the file name: ");
            String fileName = scnr.nextLine();
            inputFile = new File(fileName);
        }
        // Call method to process the file
        clientClient.loadClientFile(inputFile);
        //saves file information loaded in the class to a new output file
        clientClient.saveClientFile(new File("budgetdata.txt"));

        String slash7 = String.valueOf("-").repeat(175);
        System.out.println(slash7);

        //prints client budget report
        System.out.println(clientClient.getBudgetReport());

        String slash8 = String.valueOf("-").repeat(175);
        System.out.println(slash8);

        //prints client transaction report
        System.out.println(clientClient.getTransactionReport());



    }
}