package Flyweight;

/**
 * Package Flyweight
 * Description: 不需要共享的 Flyweight 子类
 * author 016039
 * date 2019/2/9上午11:25
 */
public class UnsharedFlyweightImpl implements Flyweight {
  @Override
  public void operation(int extrinsicstate) {
    System.out.println("不共享具体的 Flyweight:" + extrinsicstate);
  }
}
