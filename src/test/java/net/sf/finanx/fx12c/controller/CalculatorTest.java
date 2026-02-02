package net.sf.finanx.hp12c.controller;

import static net.sf.finanx.math.Number.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculatorTest {

	private static Calculator calculator;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		calculator = new Calculator();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Clear all registers
		calculator.doKey42(); // [f]
		calculator.doKey35(); // [CLx]
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void divideInterestByTwelveTest() {
		
		calculator.getStack().put(n(36)); // 36 [ENTER]
		calculator.doKey43(); // [g]
		calculator.doKey12(); // [i] (divide by 12)
		
		assertEquals(n(3), calculator.getStack().get(0)); // x = 3.00
		assertEquals(n(3), calculator.getFinanceMemory().getI()); // i = 3.00
	}
	
	@Test
	public void netAmountTest() {
		
		calculator.getStack().put(n(13250)); // 13250 [ENTER]
		calculator.getStack().put(n(8)); // 8
		calculator.doKey25(); // [%] percent
		assertEquals(n(1060), calculator.getStack().get(0)); // x = 1,060.00
		
		calculator.doKey34(); // [xy] swap
		assertEquals(n(13250), calculator.getStack().get(0).round(2)); // x = 13250
		
		calculator.doKey34(); // [xy] swap
		calculator.doKey30(); // [-] subtract
		assertEquals(n(12190), calculator.getStack().get(0)); // x = 12,190.00
		
		calculator.getStack().put(n(6)); // 6
		calculator.doKey25(); // [%] percent
		assertEquals(n(731.40), calculator.getStack().get(0)); // x = 731.40
		
		calculator.doKey40(); // [+] add
		assertEquals(n(12921.40), calculator.getStack().get(0)); // x = 12,921.40.00
	}
	
	@Test
	public void percentDifferenceTest() {
		
		calculator.getStack().put(n(58.50)); // 58.50 [ENTER]
		calculator.getStack().put(n(53.25)); // 53.25
		calculator.doKey24(); // [A%] percent difference
		assertEquals(n(-8.97), calculator.getStack().get(0).round(2)); // x = -8.97
		
		calculator.doKey34(); // [xy] swap
		assertEquals(n(58.50), calculator.getStack().get(0).round(2)); // x = 58.50
		
	}
	
	@Test
	public void percentOfTotalTest() {
		
		calculator.getStack().put(n(3.92)); // 3.92 [ENTER]
		calculator.getStack().put(n(2.36)); // 2.36
		calculator.doKey40(); // [+] add
		assertEquals(n(6.28), calculator.getStack().get(0).round(2)); // x = 6.28
		
		calculator.getStack().put(n(1.67)); // 1.67
		calculator.doKey40(); // [+] add
		assertEquals(n(7.95), calculator.getStack().get(0).round(2)); // x = 7.95
		
		calculator.getStack().put(n(2.36)); // 2.36
		calculator.doKey23(); // [%T] percent of total
		assertEquals(n(29.69), calculator.getStack().get(0).round(2)); // x = 29.69
		
		calculator.doKey34(); // [xy] swap
		assertEquals(n(7.95), calculator.getStack().get(0).round(2)); // x = 7.95
		
	}
}
