package chainResponsibility;

/**
 * Package chainResponsibility
 * Description: ${todo}
 * author 016039
 * date 2019/2/9上午10:41
 */
public abstract class Handler {
  // 继任者
  protected Handler successor;

  public void setSuccessor(Handler successor) {
    this.successor = successor;
  }

  // 处理请求的抽象类
  public abstract void HandlerRequest(int request);
}
