package net.sf.finanx.hp12c.model;

import static net.sf.finanx.math.Number.*;
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
import net.sf.finanx.hp12c.model.Stack;

/*
 * Examples extractes from the following books:
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
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void subtractTest() throws Exception {
		stk.put(n(579));
		stk.put(n(456));
		stk.subtract();
		assertEquals(n(123), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void multiplyTest() throws Exception {
		stk.put(n(200));
		stk.put(n(0.5));
		stk.multiply();
		assertEquals(n(100), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void divideTest() throws Exception {
		stk.put(n(100));
		stk.put(n(200));
		stk.divide();
		assertEquals(n(0.5), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void remainderTest() throws Exception {
		stk.put(n(151));
		stk.put(n(100));
		stk.remainder();
		assertEquals(n(51), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void negateTest() throws Exception {
		stk.put(n(123));
		stk.negate();
		assertEquals(n(-123), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void sqrtTest() throws Exception {
		stk.put(n(25));
		stk.sqrt();
		assertEquals(n(5), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void reciprocalTest() throws Exception {
		stk.put(n(4));
		stk.reciprocal();
		assertEquals(n(0.25), stk.pop().round(2));
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void integralPartTest() throws Exception {
		stk.put(n(6.78));
		stk.integralPart();
		assertEquals(n(6), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void fractionalPartTest() throws Exception {
		stk.put(n(6.78));
		stk.fractionalPart();
		assertEquals(n(0.78), stk.pop().round(2));
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void powTest() throws Exception {
		stk.put(n(2));
		stk.put(n(10));
		stk.pow();
		assertEquals(n(1024), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void squaredTest() throws Exception {
		stk.put(n(8));
		stk.squared();
		assertEquals(n(64), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void expTest() throws Exception {
		stk.put(n(2));
		stk.exp();
		assertEquals(n(7.39), stk.pop().round(2));
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void logTest() throws Exception {
		stk.put(n(2));
		stk.log();
		assertEquals(n(0.69), stk.pop().round(2));
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void factorialTest() throws Exception {
		stk.put(n(5));
		stk.factorial();
		assertEquals(n(120), stk.pop());
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void roundTest() throws Exception {
		stk.put(n(1.666));
		stk.round(2);
		assertEquals(n(1.67), stk.pop().round(2));
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void percentTest() throws Exception {
		// HP12C Manual
		stk.put(n(300));
		stk.put(n(14));
		stk.percent();
		assertEquals(n(42), stk.pop());
		assertEquals(n(300), stk.top());
	}
	
	@Test
	public void percentDifferenceTest() throws Exception {
		// HP12C Manual
		stk.put(n(58.50));
		stk.put(n(53.25));
		stk.percentDifference();
		assertEquals(n(-8.97), stk.pop().round(2));
		assertEquals(n(58.50), stk.top());
	}
	
	@Test
	public void percentOfTotalTest() throws Exception {
		// HP12C Manual
		stk.put(n(3.92).add(n(2.36).add(n(1.67))));
		stk.put(n(2.36));
		stk.percentOfTotal();
		assertEquals(n(29.69), stk.pop().round(2));
		assertEquals(n(7.95), stk.top());
	}
	
	@Test
	public void addDaysTest() throws Exception {
		// HP12C Manual
		stk.setDmy(true);
		stk.put(n(14.052004));
		stk.put(n(120));
		stk.addDaysToDate();
		assertEquals(n(11.092004), stk.pop());
		assertEquals(n(0), stk.top());
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
		assertEquals(n(0), stk.top());
	}
	
	@Test
	public void diffOfDaysBetweenDatesTest_YearOf365Days() throws Exception {
		
		LocalDateTime today = LocalDateTime.ofInstant(Instant.now().truncatedTo(ChronoUnit.DAYS), ZoneId.systemDefault());
		LocalDateTime then = null;
		
		int max = 1000;
		for(int i = -max; i < max; i++) {
			
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
