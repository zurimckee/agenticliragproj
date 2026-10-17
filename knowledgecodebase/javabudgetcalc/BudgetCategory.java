/**
 * @author Zuri McKee
 * Mason section 4
 * 2/14/2025
 * - The BudgetCategory class represents a single budget category in the client’s budget. It can be an expendure category or an income source.
 * the private members are catName, catDescription, amount, and categoryType, all representing characteristics of a category in the client's budget
 * - the class contains a no-arg constructor that initializes the variables with default values
 * - a constructor with parameters to initialize the attributes with specific values
 * - getter and setter methods to access and modify private fields
 * - a toString method to output a formatted string representation of the class
 */
public class BudgetCategory {
    private String catName; //Name of budget category (e.g. “Housing”)
    private String catDescription; //detailed description of the category.
    private double amount; //Amount allocated to the category per month.
    private int categoryType; //0 = expense, 1 = income

    /**
     *
     * BudgetCategory no arg constructor initialized with java default values
     */
    public BudgetCategory(){
        catName = null;
        catDescription = null;
        amount = 0.00;
        categoryType = 0;
    }

    /**
     *
     * @param cn
     * @param cd
     * @param amt
     * @param type
     * BudgetCategory arg constructor that sets param values to BudgetCategory class private helper values.
     */
    public BudgetCategory(String cn, String cd, double amt, int type){
        this.catName = cn;
        this.catDescription = cd;
        this.amount = amt;
        this.categoryType = type;
    }

    /**
     *
     *
     * accessor - accesses a private variable and returns it
     * @return String catName
     *
     */
    public String getCatName() {
        return catName;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param catName
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     * accessor - accesses a private variable and returns it
     * @return catDescription
     */
    public String getCatDescription() {
        return catDescription;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param catDescription
     * @return void
     */
    public void setCatDescription(String catDescription) {
        this.catDescription = catDescription;
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
     * @return categoryType
     */
    public int getCategoryType() {
        return categoryType;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param categoryType
     * @return void
     */
    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    /**
     *
     * @return formatted String of BudgetCategory class members
     */

    public String toString(){
        return amount + " " + categoryType + " " + catName + "\n" + catDescription;
    }
}
