package valuesDemo;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import io.vavr.Lazy;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.util.Random;

/**
 * @author jinjunliang 2019-06-04 15:56
 **/
public class ValueDemo {

	/**
	 * Option 一元容器，是 Some 或者 None 的实例
	 */
	private void testOption() {
		Option<String> maybeFoo = Option.of("foo");
		Option<String> maybeFooBar = maybeFoo
				// 这一步之后变成了 Some(Null), 因为 Map 不会改变 Context
				.map(s -> (String) null)
				// 这一步之后变成了 None, flatMap 可以改变 Context
				.flatMap(s -> Option.of(s))
				.map(t -> t.toUpperCase() + "bar");
	}

	/**
	 * Try 是一个容器，表示会抛出异常或得到结果 是 Success 或 Failure 的实例
	 */
	private void testTry() {
		String result = Try.of(this::bunchOfWork)
				// 描述如何从异常中恢复
				.recover(x -> Match(x).of(
						// 判断是哪种类型的异常
						Case($(instanceOf(NullPointerException.class)), t -> t.getMessage())

				))
				.getOrElseGet(t -> t.getMessage());
	}

	/**
	 * Lazy
	 */
	private void testLazy() {
		Lazy<Double> lazy = Lazy.of(Math::random);
		lazy.isEvaluated(); // = false
		lazy.get();         // = 0.123 (random generated)
		lazy.isEvaluated(); // = true
		lazy.get();         // = 0.123 (memoized)
	}

	/**
	 * Either 表示可能有两种不同类型的值，分别称为左值或右值。
	 * 只能是其中的一种情况。Either 通常用来表示成功或失败两种情况。
	 * 惯例是把成功的值作为右值，而失败的值作为左值。可以在 Either 上添加应用于左值或右值的计算。
	 * 应用于右值的计算只有在 Either 包含右值时才生效，对左值也是同理
	 */
	private void testEither() {
		Either<String,Integer> value = compute().map(i -> i % 2);

	}

	/**
	 * Future
	 */
	private void testFuture() {
		// 略
	}

	/**
	 * Validation
	 *
	 */
	private void testValidation() {
		// 待补..
	}

	/**
	 * 模拟一大堆工作
	 */
	private String bunchOfWork() throws Exception {
		return "test";
	}

	private Either<String,Integer> compute() {
		return new Random().nextBoolean() ? Either.left("fail") : Either.right(1);
	}
}
