package kaist.tap.kaf.component;

import static org.junit.Assert.*;
import kaist.tap.kaf.component.Component.Selection;

import org.eclipse.swt.graphics.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LineTest {

	public Line line;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		line = new Line();
		line.setStartPosition(10, 10);
		line.setEndPosition(20, 20);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMove() {
		Point[] expected = new Point[2];
		Point start = line.getPosition();
		Point end = line.getEndPosition();
		
		expected[0] = new Point(start.x+20, start.y+10);
		expected[1] = new Point(end.x+20, end.y+10);
		
		line.move(20, 10);
		Point[] actual = new Point[2];
		actual[0] = line.getPosition();
		actual[1] = line.getEndPosition();
		
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
	}
	
	
	@Test
	public void testContainSelection() {
		Point start = line.getPosition();
		
		Selection expected = Selection.START;
		Selection actual = line.containSelection(start.x, start.y);
				
		assertEquals(expected, actual);
	}

	@Test
	public void testResize() {
		Point[] expected = new Point[2];
		Point start = line.getPosition();
		Point end = line.getEndPosition();
		
		expected[0] = new Point(0, 0);
		expected[1] = new Point(end.x, end.y);	
		
		Selection sel = line.containSelection(start.x, start.y);
		line.setSelection(sel);
		line.resize(0, 0);
		Point[] actual = new Point[2];
		actual[0] = line.getPosition();
		actual[1] = line.getEndPosition();
		
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
	}

	@Test
	public void testGetBounds() {
		Point[] expected = new Point[2];
		
		expected[0] = new Point(10,10);
		expected[1] = new Point(20,20);
		
		Point[] actual = line.getBounds();
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
	}

}
