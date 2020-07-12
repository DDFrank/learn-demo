package converter;


/**
 * @author 016039
 * @Package converter
 * @Description: ${todo}
 * @date 2018/9/4下午2:10
 */
public class UserConverter extends Converter<UserDto, User> {
    public UserConverter() {
        super(userDto -> new User(userDto.getFirstName(), userDto.getLastName(), userDto.isActive(),
                        userDto.getEmail()),
                user -> new UserDto(user.getFirstName(), user.getLastName(), user.isActive(),
                        user.getUserId()));
    }
}
