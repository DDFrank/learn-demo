package strategy;

/**
 * Package strategy
 * Description: 现金结算的策略
 * author 016039
 * date 2019/2/2上午9:02
 */
public interface CashExecutor {

  /**
  * @param cash 现金
  * @return 计算后的现金
  * jinjunliang
  * 2019/2/2 上午9:03
  */
  Double calculate(Double cash);
}
