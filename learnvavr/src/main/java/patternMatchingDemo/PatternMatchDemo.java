package patternMatchingDemo;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static io.vavr.Predicates.is;
import static io.vavr.Predicates.isIn;

/**
 * 模式匹配
 *
 * @author jinjunliang 2019-06-04 17:40
 **/
public class PatternMatchDemo {
	public static void main(String[] args) {
		PatternMatchDemo p = new PatternMatchDemo();
		System.out.println(p.test2());
	}

	/**
	 * 三种基本类型 $() 关卡判断 $(value) equals $(predicate) 条件判断
	 */

	private void test1() {
		int i = 1;
		String s = Match(i).of(
				Case($(1), "one"),
				Case($(2), "two"),
				Case($(), "?")
		);
	}

	/**
	 * 条件匹配 和 多条件匹配
	 */
	private String test2() {
		int i = 2;
		return Match(i).of(
				Case($(is(1)), "one"),
				Case($(is(2)), "one"),
				Case($(isIn(3, 4)), "three"),
				Case($(), "?")
		);
	}

	/**
	 * 匹配成功时运行某个动作而不是返回某个值
	 * */
	private void test3() {
		String arg = "-h";
		Match(arg).of(
				Case($(isIn("-h", "--help")), o -> run(this::displayHelp)),
				Case($(isIn("-v", "--version")), o -> run(this::displayVersion)),
				Case($(), o -> run(() -> {
					throw new IllegalArgumentException(arg);
				}))
		);
	}

	private void displayHelp() {}

	private void displayVersion() {}
}
