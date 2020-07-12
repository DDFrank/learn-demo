package Flyweight;

/**
 * Package Flyweight
 * Description: ${todo}
 * author 016039
 * date 2019/2/9上午11:24
 */
public class DefaultFlyweightImpl implements Flyweight {
  @Override
  public void operation(int extrinsicstate) {
    System.out.println("具体的 Flyweight:" + extrinsicstate);
  }
}
