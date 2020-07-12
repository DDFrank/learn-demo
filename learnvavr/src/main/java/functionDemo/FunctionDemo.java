package functionDemo;

import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.control.Option;

/**
 * @author jinjunliang 2019-06-04 14:10
 **/
public class FunctionDemo {

	/**
	 * 使用 Composition 来组合函数
	 */
	private void testComposition() {
		// Composition 使用 andThen
		Function1<Integer, Integer> plusOne = a -> a + 1;
		Function1<Integer, Integer> multiplyByTwo = a -> a * 2;
		// 组合了两个函数， 先 + 1 然后 * 2
		Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);

		// Composition2, 使用 compose
		Function1<Integer, Integer> add1AndMultiply2else = multiplyByTwo.compose(plusOne);
	}

	/**
	 * Lift 的用法
	 *
	 */
	private void testLifting() {
		// 假如 b = 0 的话，这个函数就会抛出异常，也就是会失败
		Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
		// 用 lift 后，可以保证该函数不会失败(也就是不会抛出异常?)
		Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);
		// None, 不会抛出异常
		Option<Integer> i1 = safeDivide.apply(1, 0);
		// Some(2)
		Option<Integer> i2 = safeDivide.apply(4, 2);
	}

	/**
	 * 产生偏函数
	 * */
	private void testPartial() {
		Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
		// 填充了一个参数
		Function1<Integer, Integer> add2 = sum.apply(2);
		// 函数会执行
		add2.apply(4);
	}

	/**
	 * 柯里化
	 * */
	private void testCurrying() {
		Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
		// 将第一个参数固定为2
		Function1<Integer, Integer> add2 = sum.curried().apply(2);
		add2.apply(4);
	}

	/**
	 *  currying 和 partially function 的区别
	 *
	 * */
	private void differenceBetweenCurryingAndPartiallyFunction() {
		Function3<Integer, Integer, Integer, Integer> sum = (a, b, c) -> a + b + c;
		// 柯里化后的固定返回 Function1, 而不是 partial 的 参数 -1
		final Function1<Integer, Function1<Integer, Integer>> add2 = sum.curried().apply(2);
	}

	/**
	 * 存储记忆
	 * Memoization
	 * */
	private void testMemoization() {
		// 只会执行一次，之后就返回缓存的值
		Function0<Double> hashCache = Function0.of(Math::random).memoized();
		double randomValue1 = hashCache.apply();
		double randomValue2 = hashCache.apply();
	}



}
