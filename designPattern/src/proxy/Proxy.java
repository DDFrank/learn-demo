package proxy;

/**
 * Package proxy
 * Description: 代理类
 * author 016039
 * date 2019/2/2上午10:35
 */
public class Proxy implements Subject {
  private Real real;

  public Proxy(Real real) {
    this.real = real;
  }

  @Override
  public void execute() {
    System.out.println("代理的前置操作");
    real.execute();
  }
}
