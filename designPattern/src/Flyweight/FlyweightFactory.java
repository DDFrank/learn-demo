package Flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * Package Flyweight
 * Description: 享元工厂
 * author 016039
 * date 2019/2/9上午11:26
 */
public class FlyweightFactory {
  private Map<String, Flyweight> flyweightMap = new HashMap<>();

  // 初始化工厂时，先放入三个实例
  public FlyweightFactory() {
    flyweightMap.put("X", new DefaultFlyweightImpl());
    flyweightMap.put("Y", new DefaultFlyweightImpl());
    flyweightMap.put("Z", new DefaultFlyweightImpl());
  }

  public Flyweight getFlyweight(String key) {
    return flyweightMap.get(key);
  }

}
