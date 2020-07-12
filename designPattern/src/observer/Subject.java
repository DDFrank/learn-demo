package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Package observer
 * Description: 抽象主题，通知者
 * author 016039
 * date 2019/2/3上午8:24
 */
public abstract class Subject {
  // 观察者列表
  private List<Observer> observerList = new ArrayList<>();

  public void attach(Observer observer) {
    this.observerList.add(observer);
  }

  public void detach(Observer observer) {
    this.observerList.remove(observer);
  }

  // 发出通知
  public void doNotify() {
    observerList.forEach(Observer::update);
  }

}
