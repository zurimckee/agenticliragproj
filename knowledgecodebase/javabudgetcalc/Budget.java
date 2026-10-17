/**
 *
 *  @author Zuri McKee
 *  Mason section 4
 *  2/19/2025
 * the Budget class stores the total monthly amount of all budget categories, and also the client's total income.
 *  - the class contains a no-arg constructor that initializes the variables with default values
 *  - getter and setter methods to access and modify private fields
 *  - 5 associated arrayList methods that allow the user to manipulate the categories arrayList
 *  - a toString method to output a formatted string representation of the class
 *  - a print categories method that adds each element in the categories list to a string to make it easier to print
 */
import java.util.ArrayList;

public class Budget {
    private ArrayList<BudgetCategory> categories; //Collection of all budget categories
    private double totalAllocated; //Total monthly amount of all budget categories.
    private double totalIncome; //Total client income


    /**
     *
     * Budget no arg constructor initialized with java default values
     */
    public Budget(){
        categories = new ArrayList<>();
        totalAllocated = 0.00;
        totalIncome = 0.00;
    }


    /**
     *
     *
     * accessor - accesses a private variable and returns it
     * @return double totalAllocated
     *
     */
    public double getTotalAllocated(){
        return totalAllocated;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param totalAllocated
     */
    public void setTotalAllocated(double totalAllocated){
        this.totalAllocated = totalAllocated;
    }

    /**
     * accessor - accesses a private variable and returns it
     * @return double totalIncome
     *
     */
    public double getTotalIncome(){
        return totalIncome;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param totalIncome
     */
    public void setTotalIncome(double totalIncome){
        this.totalIncome = totalIncome;
    }


    public ArrayList<BudgetCategory> getCategories(){
        return categories;
    }

    /**
     * calculates the client's allocation balance by subtracting the client's total income
     * from the total montly amount of all budget categories
     * @return double allocationBalance
     */
    public double getAllocationBalance(){
        double allocationBalance;

        allocationBalance = totalIncome - totalAllocated;

        return allocationBalance;
    }


    //array list accessor/mutators

    /**
     * gets the size of the arrayList categories.
     * @return int size of category
     */
    public int getCategoriesSize(){
        return categories.size();
    }

    /**
     * searches the categories array list for an element at a specific index inputted
     * by the user and returns the element at that index
     * @param index
     * @return element at requested index in categories array list
     */
    public BudgetCategory getCategory(int index){
        return categories.get(index);
    }


    /**
     * the if statement sees if the index actual parameter is not within the bounds of the size of the categories array list
     * if the index actual parameter is within the bounds of the size the BudgetCategory actual parameter inputted by the user is placed
     * at the specified index in the array list. if the index is not within the bounds the program returns "Index not found, out of range"
     * @param index
     * @param category
     * @return category param
     */
    public BudgetCategory setCategories(int index, BudgetCategory category){
        if(index < getCategoriesSize()){
            categories.set(index, category);
        } else {
            System.out.println("Index not found, out of range");
        }
        return category;
    }

    /**
     * adds a category from the BudgetCategory class into the categories array list using the .add arraylist method
     * @param category
     */
    public void addCategory(BudgetCategory category){
        categories.add(category);
    }

    /**
     * removes an element from the categories array list using the .remove method
     * @param index
     * @return the element removed from the categories arraylist
     */
    public BudgetCategory removeCategory(int index){
        return categories.remove(index);
    }



    //string related things

    /**
     *
     * @return formatted String of Budget class members
     */
    @Override
    public String toString(){
        return getTotalAllocated() + "\n" + getTotalIncome() + "\n" + printCategories();
    }

    /**
     * uses StringBuilder to add the toString of each element in categories of the BudgetCategory class, it then adds the toString of each element to the
     * larger StringBuilder, then it returns the String version of our StringBuilder string because it must be turned into a string to work correctly in the
     * larger toString
     * @return String version of the StringBuilder/sb
     */

    public String printCategories() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<categories.size(); i++){
            sb.append(categories.get(i).toString()).append("\n");
        }

        return sb.toString();


    }



}
