/**
 * @author Zuri McKee
 * Mason section 4
 * 2/26/2025
 * The Tracker class keeps track of all monthly transactions for all of the budget categories, with private variables transactions, an array list and balance
 * embedded in the program for the user to adequetly track their transaction
 *  - the class contains a no-arg constructor that initializes the variables with default values
 *  - getter and setter methods to access and modify private fields
 *  - 5 associated arrayList methods that allow the user to manipulate the transactions arrayList
 *  - a toString method to output a formatted string representation of the class
 * -  a print categories method that adds each element in the transactions list to a string to make it easier to print
 */
import java.util.ArrayList;

public class Tracker {
    private ArrayList<Transaction> transactions; //collection of all transactions
    private double balance; //Total monthly budget amount minus total of transactions.

    /**
     * no arg constructor - initializes array list as empty and initializes balance
     * to java default 0.0
     */
    public Tracker(){
        transactions = new ArrayList<>();
        balance = 0.0;
    }

    /**
     * accessor - accesses a private variable and returns it
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param balance
     * @return void
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    //arraylist methods

    /**
     * gets the size of the arrayList transactions.
     * @return int size of transactions arraylist
     */
    public int getTransactionsSize(){
        return transactions.size();
    }

    /**
     * searches the transactions array list for an element at a specific index inputted
     * by the user and returns the element at that index
     * @param index
     * @return element at requested index in transactions array list
     */
    public Transaction getTransaction(int index){
        return transactions.get(index);
    }



    /**
     * the if statement sees if the index actual parameter is not within the bounds of the size of the transactions array list
     * if the index actual parameter is within the bounds of the size the Transaction actual parameter inputted by the user is placed
     * at the specified index in the array list. if the index is not within the bounds the program returns "Index not found, out of range"
     * @param index
     * @param transaction
     * @return transaction param
     */
    public Transaction setTransactions(int index, Transaction transaction){
        if(index < getTransactionsSize()){
            transactions.set(index, transaction);
        } else {
            System.out.println("Index not found, out of range");
        }
        return transaction;
    }


    /**
     * adds a transaction from the Transaction class into the transactions array list using the .add arraylist method
     * @param transaction
     */
    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }


    /**
     * removes an element from the transactions array list using the .remove method
     * @param index
     * @return the element removed from the transactions arraylist
     */
    public Transaction removeTransaction(int index){
        transactions.remove(index);
        return transactions.get(index);
    }


    /**
     *
     * @return formatted String of Tracker class members
     */
    public String toString(){
        return getBalance() + "\n" + printTransactions();
    }


    /**
     * uses StringBuilder to add the toString of each element in transactions of the Transaction class, it then adds the toString of each element to the
     * larger StringBuilder, then it returns the String version of our StringBuilder string because it must be turned into a string to work correctly in the
     * larger toString
     * @return String version of the sb
     */

    public String printTransactions() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<transactions.size(); i++){
            sb.append(transactions.get(i).toString()).append("\n");
        }
        return sb.toString();


    }



}
