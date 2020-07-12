package observer;

/**
 * Package observer
 * Description: 观察者，为所有的具体观察者提供一个接口，在得到主题的通知时更新自己
 * author 016039
 * date 2019/2/3上午8:22
 */
public interface Observer {
  // 更新自己的状态
  void update();
}
