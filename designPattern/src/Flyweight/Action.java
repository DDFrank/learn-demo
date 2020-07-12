package Flyweight;

/**
 * Package Flyweight
 * Description: ${todo}
 * author 016039
 * date 2019/2/9上午11:31
 */
public class Action {
  public static void main(String[] args) {
    // 外部的某个状态
    int extrinsicstate = 22;
    FlyweightFactory flyweightFactory = new FlyweightFactory();

    Flyweight flyweightX = flyweightFactory.getFlyweight("X");
    flyweightX.operation(--extrinsicstate);
    // 不需要共享的对象
    UnsharedFlyweightImpl unsharedFlyweight = new UnsharedFlyweightImpl();
    unsharedFlyweight.operation(--extrinsicstate);
  }
}
