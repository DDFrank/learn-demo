package chainResponsibility;

/**
 * Package chainResponsibility
 * Description: ${todo}
 * author 016039
 * date 2019/2/9上午10:49
 */
public class Action {
  public static void main(String[] args) {
    Handler h1 = new FirstHandlerImpl();
    Handler h2 = new SecondHandlerImpl();

    h1.setSuccessor(h1);

    h1.HandlerRequest(10);
  }
}
