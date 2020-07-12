package converter;

/**
 * @author 016039
 * @Package converter
 * @Description: 假设是用户的DTO
 * @date 2018/9/4下午1:47
 */
public class UserDto {
    private String firstName;
    private String lastName;
    private boolean isActive;
    private String email;

    public UserDto(String firstName, String lastName, boolean isActive, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserDto setActive(boolean active) {
        isActive = active;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }


}
