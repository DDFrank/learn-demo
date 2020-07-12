package callback;

/**
 * @author 016039
 * @Package callback
 * @Description: ${todo}
 * @date 2018/9/4下午12:37
 */
public class SimpleTask extends Task {
    @Override
    public void execute() {
        System.out.println("执行了一个简单的任务");
    }
}
