package io.frank.test_junit.func;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author jinjunliang
 **/
public class CalculatorTest {
  private Calculator calculator = new Calculator();

  @Test
  void addition() {
    assertEquals(2, calculator.add(1, 1));
  }

  /**
   * 根据不同的参数运行测试多次
   * @param candidate 不同的参数
   */
  @ParameterizedTest
  @ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
  void palindromes(String candidate) {
    assertTrue(candidate.length() > 1);
  }
}
