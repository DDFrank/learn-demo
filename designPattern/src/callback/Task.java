package callback;

/**
 * @author 016039
 * @Package callback
 * @Description: ${todo}
 * @date 2018/9/4下午12:35
 */
public abstract class Task {

    public void executeWith(Callback callback) {
        execute();
        if(callback != null) {
            callback.call();
        }
    }

    public abstract  void execute();
}
