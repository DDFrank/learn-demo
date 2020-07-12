package strategy;

/**
 * Package strategy
 * Description: ${todo}
 * author 016039
 * date 2019/2/2上午9:04
 */
public class CashContext {
  // 需要执行的策略
  private CashExecutor cashExecutor;

  // 选择策略，可以将该策略的选择过程放到这里或者客户端
  CashContext(CashExecutor cashExecutor) {
    this.cashExecutor = cashExecutor;
  }

  // 执行策略
  public Double execute(Double cash) {
    return cashExecutor.calculate(cash);
  }

}
