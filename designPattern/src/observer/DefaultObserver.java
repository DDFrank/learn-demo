package observer;

/**
 * Package observer
 * Description: 具体的观察者
 * author 016039
 * date 2019/2/3上午8:30
 */
public class DefaultObserver implements  Observer {

  private String name;
  private int age;

  public DefaultObserver(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public void update() {
    System.out.println("name:" + name + ", age:" + age);
  }
}
