package chainResponsibility;

import java.util.Objects;

/**
 * Package chainResponsibility
 * Description: ${todo}
 * author 016039
 * date 2019/2/9上午10:45
 */
public class SecondHandlerImpl extends Handler {
  @Override
  public void HandlerRequest(int request) {
    // 请求数在 10 到 20 的时候有权处理，否则移交给下一位
    if (request >= 10 && request < 20) {
      System.out.println("处理请求:" + request);
    } else if (!Objects.isNull(successor)) {
      successor.HandlerRequest(request);
    }
  }
}
