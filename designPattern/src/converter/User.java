package converter;

/**
 * @author 016039
 * @Package converter
 * @Description: 假设是用户的实体类
 * @date 2018/9/4下午1:47
 */
public class User {
    private String firstName;
    private String lastName;
    private boolean isActive;
    private String userId;

    public User(String firstName, String lastName, boolean isActive, String userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public User setActive(boolean active) {
        isActive = active;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
