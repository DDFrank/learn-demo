package tupleDemo;

import io.vavr.Tuple;
import io.vavr.Tuple2;

/**
 * @author jinjunliang 2019-06-04 14:01
 **/
public class TupleDemo1 {

	public static void main(String[] args) {
		// 简单的构造和读取
		Tuple2<String, Integer> java8 = Tuple.of("java", 8);
		String s = java8._1;
		Integer i = java8._2;

		// 通过 函数构建
		Tuple2<String, Integer> that = java8.map(
				s1 -> s.substring(2) + "vr",
				i1 -> i / 8
		);

		Tuple2<String, Integer> that1 = java8.map(
				(s2, i2) -> Tuple.of(s.substring(2) + "vr", i/8)
		);

		// 通过元祖 构建 元祖
		String that2 = java8.apply(
				(s2, i2) -> s.substring(2) + "vr " + i/8
		);
	}
}
