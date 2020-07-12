package dataAccessObject;

/**
 * @author 016039
 * @Package dataAccessObject
 * @Description: ${todo}
 * @date 2018/9/4下午4:28
 */
public class Customer {
    private int id;
    private String firstName;
    private String lastName;

    public int getId() {
        return id;
    }

    public Customer setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
