/**
 *
 *  @author Zuri McKee
 *  Mason section 4
 *  2/14/2025
 * the Contact class stores the contact information for the client in private variables homeAddress, phone, and email
 *  - the class contains a no-arg constructor that initializes the variables with default values
 *  - a constructor with parameters to initialize the attributes with specific values
 *  - getter and setter methods to access and modify private fields
 *  - a toString method to output a formatted string representation of the class
 */
public class Contact {
    private String homeAddress;
    private String phone;
    private String email;

    /**
     * Contact no arg constructor initialized with java default values
     */
    public Contact(){
        homeAddress = null;
        phone = null;
        email = null;
    }

    /**
     *
     * @param hmAddress
     * @param phone
     * @param email
     * Contact arg constructor that sets param values to Contact class private helper values.
     */
    public Contact(String hmAddress, String phone, String email){
        this.homeAddress = hmAddress;
        this.phone = phone;
        this.email = email;
    }

    /**
     * accessor - accesses a private variable and returns it
     *  @return String homeAddress
     *
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param homeAddress
     * @return void
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }


    /**
     * accessor - accesses a private variable and returns it
     *  @return String phone
     *
     */
    public String getPhone() {
        return phone;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param phone
     * @return void
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * accessor - accesses a private variable and returns it
     *  @return String email
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * mutator - takes the accessed private variable and sets it's value to the parameter passed in the method
     * @param email
     * @return void
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return formatted String of Contact class variables
     */

    public String toString(){
        return getHomeAddress() + "\n" + getPhone() + "\n" + getEmail();
    }
}