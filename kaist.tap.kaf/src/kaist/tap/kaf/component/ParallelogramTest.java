package kaist.tap.kaf.component;

import static org.junit.Assert.*;

import org.junit.Test;

import kaist.tap.kaf.component.Parallelogram;

public class ParallelogramTest {

	// @Test
	// public void test() {
	// fail("Not yet implemented");
	// }

	@Test
	public void testParallelogram() {
		Parallelogram para = new Parallelogram();
		assertEquals(true, para.fill);
	}

}
