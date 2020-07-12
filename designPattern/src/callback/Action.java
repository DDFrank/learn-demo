package callback;

/**
 * @author 016039
 * @Package callback
 * @Description: 执行类
 * @date 2018/9/4下午1:23
 */
public class Action {

    public static void main(String[] args) {
        Callback callback = () -> System.out.println("回调执行了");
        SimpleTask simpleTask = new SimpleTask();
        simpleTask.executeWith(callback);
    }
}
