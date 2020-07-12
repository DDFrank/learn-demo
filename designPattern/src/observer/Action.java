package observer;

/**
 * Package observer
 * Description: ${todo}
 * author 016039
 * date 2019/2/3上午8:31
 */
public class Action {
  public static void main(String[] args) {
    Subject subject = new DefaultSubject();

    Observer observer = new DefaultObserver("frank", 27);

    subject.attach(observer);

    subject.doNotify();
  }
}
