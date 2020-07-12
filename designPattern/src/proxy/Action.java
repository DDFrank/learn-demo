package proxy;

/**
 * Package proxy
 * Description: ${todo}
 * author 016039
 * date 2019/2/2上午10:34
 */
public class Action {
  public static void main(String[] args) {
    Real real = new Real();
    Proxy proxy = new Proxy(real);
    proxy.execute();
  }
}
