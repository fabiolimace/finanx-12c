package net.sf.finanx.fx12c.memory;

import static net.sf.finanx.fx12c.math.Number.*;
import static org.junit.Assert.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Examples extracted from the following books:
 *  - HP12C Financial Calculator User's Guide, 2005, refered here as "HP12C Manual"
 *  - Matemática Financeira e Suas Aplicações, Alexandre Assaf Neto, 12a Ed., 2012, refered here as "Neto 2012"
 */
public class StackTest {

	private Stack stk;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		stk = new Stack();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addTest() throws Exception {
		stk.put(n(123));
		stk.put(n(456));
		stk.add();
		assertEquals(n(579), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(456), stk.getLastX());

		stk.clear();
		stk.put(n(333));
		stk.put(n(222));
		stk.put(n(444));
		stk.add();
		assertEquals(n(666), stk.pop());
		assertEquals(n(333), stk.peek());
		assertEquals(n(444), stk.getLastX());
	}

	@Test
	public void subtractTest() throws Exception {
		stk.put(n(579));
		stk.put(n(456));
		stk.subtract();
		assertEquals(n(123), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(456), stk.getLastX());

		stk.clear();
		stk.put(n(666));
		stk.put(n(999));
		stk.put(n(777));
		stk.subtract();
		assertEquals(n(222), stk.pop());
		assertEquals(n(666), stk.peek());
		assertEquals(n(777), stk.getLastX());
	}

	@Test
	public void multiplyTest() throws Exception {
		stk.put(n(200));
		stk.put(n(0.5));
		stk.multiply();
		assertEquals(n(100), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(0.5), stk.getLastX());

		stk.clear();
		stk.put(n(111));
		stk.put(n(444));
		stk.put(n(0.5));
		stk.multiply();
		assertEquals(n(222), stk.pop());
		assertEquals(n(111), stk.peek());
		assertEquals(n(0.5), stk.getLastX());
	}

	@Test
	public void divideTest() throws Exception {
		stk.put(n(100));
		stk.put(n(200));
		stk.divide();
		assertEquals(n(0.5), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(200), stk.getLastX());

		stk.clear();
		stk.put(n(555));
		stk.put(n(777));
		stk.put(n(666));
		stk.divide();
		assertEquals(n(1.166666667), stk.pop().round(9));
		assertEquals(n(555), stk.peek());
		assertEquals(n(666), stk.getLastX());
	}

	@Test
	public void remainderTest() throws Exception {
		stk.put(n(151));
		stk.put(n(100));
		stk.remainder();
		assertEquals(n(51), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(100), stk.getLastX());

		stk.clear();
		stk.put(n(999));
		stk.put(n(444));
		stk.put(n(333));
		stk.remainder();
		assertEquals(n(111), stk.pop().round(9));
		assertEquals(n(999), stk.peek());
		assertEquals(n(333), stk.getLastX());
	}

	@Test
	public void negateTest() throws Exception {
		stk.put(n(123));
		stk.negate();
		assertEquals(n(-123), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(0), stk.getLastX());

		stk.clear();
		stk.put(n(222));
		stk.put(n(333));
		stk.negate();
		assertEquals(n(-333), stk.pop());
		assertEquals(n(222), stk.peek());
		assertEquals(n(0), stk.getLastX());
	}

	@Test
	public void sqrtTest() throws Exception {
		stk.put(n(25));
		stk.sqrt();
		assertEquals(n(5), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(25), stk.getLastX());

		stk.clear();
		stk.put(n(77));
		stk.put(n(88));
		stk.sqrt();
		assertEquals(n(9.380831520), stk.pop().round(9));
		assertEquals(n(77), stk.peek());
		assertEquals(n(88), stk.getLastX());
	}

	@Test
	public void reciprocalTest() throws Exception {
		stk.put(n(4));
		stk.reciprocal();
		assertEquals(n(0.25), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(4), stk.getLastX());

		stk.clear();
		stk.put(n(44));
		stk.put(n(55));
		stk.reciprocal();
		assertEquals(n(0.018181818), stk.pop().round(9));
		assertEquals(n(44), stk.peek());
		assertEquals(n(55), stk.getLastX());
	}

	@Test
	public void integralPartTest() throws Exception {

		/*
		 * 6.78 ENTER X = 6 Y = 0
		 */
		stk.put(n(6.78));
		stk.integralPart();
		assertEquals(n(6), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(6.78), stk.getLastX());

		/*
		 * 1.23 ENTER 4.56 INTG X = 4 Y = 1.23
		 */
		stk.clear();
		stk.put(n(1.23));
		stk.put(n(4.56));
		stk.integralPart();
		assertEquals(n(4), stk.pop());
		assertEquals(n(1.23), stk.peek());
		assertEquals(n(4.56), stk.getLastX());
	}

	@Test
	public void fractionalPartTest() throws Exception {

		/*
		 * 6.78 ENTER X = 0.78 Y = 0
		 */
		stk.put(n(6.78));
		stk.fractionalPart();
		assertEquals(n(0.78), stk.pop());
		assertEquals(n(0), stk.peek());

		/*
		 * 1.23 ENTER 4.56 FRAC X = 0.56 Y = 1.23
		 */
		stk.clear();
		stk.put(n(1.23));
		stk.put(n(4.56));
		stk.fractionalPart();
		assertEquals(n(0.56), stk.pop());
		assertEquals(n(1.23), stk.peek());
		assertEquals(n(4.56), stk.getLastX());
	}

	@Test
	public void powTest() throws Exception {
		stk.put(n(2));
		stk.put(n(10));
		stk.pow();
		assertEquals(n(1024), stk.pop());
		assertEquals(n(0), stk.peek());
		assertEquals(n(10), stk.getLastX());

		stk.clear();
		stk.put(n(11));
		stk.put(n(22));
		stk.put(n(3));
		stk.pow();
		assertEquals(n(10648), stk.pop());
		assertEquals(n(11), stk.peek());
		assertEquals(n(3), stk.getLastX());
	}

	@Test
	public void squaredTest() throws Exception {
		stk.put(n(8));
		stk.squared();
		assertEquals(n(64), stk.pop());
		assertEquals(n(0), stk.peek());

		stk.clear();
		stk.put(n(55));
		stk.put(n(9));
		stk.squared();
		assertEquals(n(81), stk.pop());
		assertEquals(n(55), stk.peek());
		assertEquals(n(9), stk.getLastX());
	}

	@Test
	public void expTest() throws Exception {
		stk.put(n(2));
		stk.exp();
		assertEquals(n(7.389056099), stk.pop().round(9));
		assertEquals(n(0), stk.peek());

		stk.clear();
		stk.put(n(45));
		stk.put(n(5));
		stk.exp();
		assertEquals(n(148.4131591), stk.pop().round(7));
		assertEquals(n(45), stk.peek());
		assertEquals(n(5), stk.getLastX());
	}

	@Test
	public void logTest() throws Exception {
		stk.put(n(2));
		stk.log();
		assertEquals(n(0.69), stk.pop().round(2));
		assertEquals(n(0), stk.peek());

		stk.clear();
		stk.put(n(11));
		stk.put(n(73));
		stk.log();
		assertEquals(n(4.290459441), stk.pop().round(9));
		assertEquals(n(11), stk.peek());
		assertEquals(n(73), stk.getLastX());
	}

	@Test
	public void factorialTest() throws Exception {
		stk.put(n(5));
		stk.factorial();
		assertEquals(n(120), stk.pop());
		assertEquals(n(0), stk.peek());

		stk.clear();
		stk.put(n(33));
		stk.put(n(7));
		stk.factorial();
		assertEquals(n(5040), stk.pop());
		assertEquals(n(33), stk.peek());
		assertEquals(n(7), stk.getLastX());
	}

	@Test
	public void roundTest() throws Exception {
		stk.put(n(1.666));
		stk.round(2);
		assertEquals(n(1.67), stk.pop().round(2));
		assertEquals(n(0), stk.peek());

		stk.clear();
		stk.put(n(1.111111111));
		stk.put(n(3.141592654));
		stk.put(n(3.141592654));
		stk.put(n(3.141592654));
		stk.round(2);
		assertEquals(n(3.14), stk.pop().round(2));
		stk.round(4);
		assertEquals(n(3.1416), stk.pop().round(4));
		stk.round(6);
		assertEquals(n(3.141593), stk.pop().round(6));
		assertEquals(n(1.111111111), stk.peek());
		assertEquals(n(3.141592654), stk.getLastX());
	}

	@Test
	public void percentTest() throws Exception {
		// HP12C Manual
		stk.put(n(300));
		stk.put(n(14));
		stk.percent();
		assertEquals(n(42), stk.pop());
		assertEquals(n(300), stk.peek());

		stk.clear();
		stk.put(n(666));
		stk.put(n(753));
		stk.put(n(42));
		stk.percent();
		assertEquals(n(316.26), stk.pop());
		assertEquals(n(753), stk.pop());
		assertEquals(n(666), stk.peek());
		assertEquals(n(42), stk.getLastX());
	}

	@Test
	public void percentDifferenceTest() throws Exception {
		// HP12C Manual
		stk.put(n(58.50));
		stk.put(n(53.25));
		stk.percentDifference();
		assertEquals(n(-8.97), stk.pop().round(2));
		assertEquals(n(58.50), stk.peek());

		stk.clear();
		stk.put(n(99.99));
		stk.put(n(12.34));
		stk.put(n(56.78));
		stk.percentDifference();
		assertEquals(n(360.1296596), stk.pop().round(7));
		assertEquals(n(12.34), stk.pop());
		assertEquals(n(99.99), stk.peek());
		assertEquals(n(56.78), stk.getLastX());
	}

	@Test
	public void percentOfTotalTest() throws Exception {
		// HP12C Manual
		stk.put(n(3.92).add(n(2.36).add(n(1.67))));
		stk.put(n(2.36));
		stk.percentOfTotal();
		assertEquals(n(29.69), stk.pop().round(2));
		assertEquals(n(7.95), stk.peek());

		stk.clear();
		stk.put(n(99.99));
		stk.put(n(12.34));
		stk.put(n(56.78));
		stk.percentOfTotal();
		assertEquals(n(460.1296596), stk.pop().round(7));
		assertEquals(n(12.34), stk.pop());
		assertEquals(n(99.99), stk.peek());
		assertEquals(n(56.78), stk.getLastX());
	}

	@Test
	public void addDaysTest() throws Exception {
		// HP12C Manual
		stk.setDmy(true);
		stk.put(n(14.052004));
		stk.put(n(120));
		stk.addDaysToDate();
		assertEquals(n(11.092004), stk.pop());
		assertEquals(n(0), stk.pop());
	}

	@Test
	public void diffOfDaysBetweenDatesTest() throws Exception {
		// HP12C Manual
		stk.setDmy(false);
		stk.put(n(06.032004));
		stk.put(n(10.142005));
		stk.diffOfDaysBetweenDates();
		assertEquals(n(498), stk.pop());
		assertEquals(n(491), stk.pop());
		assertEquals(n(06.032004), stk.peek());
	}

	@Test
	public void diffOfDaysBetweenDatesTest_YearOf365Days() throws Exception {

		LocalDateTime today = LocalDateTime.ofInstant(Instant.now().truncatedTo(ChronoUnit.DAYS),
				ZoneId.systemDefault());
		LocalDateTime then = null;

		int max = 1000;
		for (int i = -max; i < max; i++) {

			then = today.plusDays(i);

			String d1 = "";
			String d2 = "";

			d1 += String.format("%02d", today.get(ChronoField.MONTH_OF_YEAR));
			d1 += ".";
			d1 += String.format("%02d", today.get(ChronoField.DAY_OF_MONTH));
			d1 += String.format("%04d", today.get(ChronoField.YEAR));

			d2 += String.format("%02d", then.get(ChronoField.MONTH_OF_YEAR));
			d2 += ".";
			d2 += String.format("%02d", then.get(ChronoField.DAY_OF_MONTH));
			d2 += String.format("%04d", then.get(ChronoField.YEAR));

			stk.setDmy(false);
			stk.put(n(d1));
			stk.put(n(d2));
			stk.diffOfDaysBetweenDates();

			long diff365 = today.until(then, ChronoUnit.DAYS);

			assertEquals(n(diff365), stk.pop());
		}
	}

	@Test
	public void dayOfWeekTest() throws Exception {
		// SF Bugs#4
		// Display (D.MY) [7] [.] [091822] [ENTER] [0] [g] [DATE]
		stk.setDmy(true);
		stk.put(n(7.091822));
		assertEquals(n(6), stk.dayOfWeek());
	}
}
