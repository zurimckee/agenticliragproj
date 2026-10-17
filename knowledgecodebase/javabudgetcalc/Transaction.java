/**
 * @author Zuri McKee
 * Mason section 4
 * 2/14/2025
 * The Transaction class represents a monthly expenditure with private variables category, date, amount, and description
 * embedded in the program for the user to adequetly document their transaction
 *  - the class contains a no-arg constructor that initializes the variables with default values
 *  - a constructor with parameters to initialize the attributes with specific values
 *  - getter and setter methods to access and modify private fields
 *  - a toString method to output a formatted string representation of the class
 *
 */
public class Transaction {
    private String category; //Will match a budgetCategory name.
    private String date; //Date of the transaction
    private double amount; //Amount of the transaction
    private String description; //Description of the transaction

    /**
     * Transaction no arg constructor initialized with java default values
     */
    public Transaction(){
        category = null;
        date = null;
        amount = 0.00;
        description = null;
    }

    /**
     *
     * @param dt
     * @param amt
     * @param desc
     * Transaction arg constructor that sets param values to Transaction class private helper values.
     */
    public Transaction(String dt, double amt, String desc){
        this.date = dt;
        this.amount = amt;
        this.description = desc;
    }

    /**
     *
     * accessor - accesses a private variable and returns it
     * @return String category
     *
     */
    public String getCategory() {
        return category;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param category
     * @return void
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * accessor - accesses a private variable and returns it
     * @return date
     *
     */
    public String getDate() {
        return date;
    }
    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param date
     * @return void
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * accessor - accesses a private variable and returns it
     * @return amount
     */
    public double getAmount() {
        return amount;
    }
    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param amount
     * @return void
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * accessor - accesses a private variable and returns it
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param description
     * @return void
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return formatted String of Transaction class members
     */

    public String toString(){
        return getCategory() + "\n" + getDate() + " " + getAmount() + "\n" + getDescription();
    }
}
