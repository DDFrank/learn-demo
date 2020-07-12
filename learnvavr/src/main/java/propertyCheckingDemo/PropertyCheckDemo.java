package propertyCheckingDemo;

import io.vavr.test.Arbitrary;
import io.vavr.test.Property;

/**
 * @author jinjunliang 2019-06-04 17:33
 **/
public class PropertyCheckDemo {

	public static void main(String[] args) {
		Arbitrary<Integer> ints = Arbitrary.integer();

		Property
				.def("square(int) >= 0")
				.forAll(ints)
				.suchThat(i -> i* i >= 0)
				.check()
				.assertIsSatisfied();
	}
}
