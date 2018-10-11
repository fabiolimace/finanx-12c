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
		
		assertEquals(n(3), calculator.getStack().get(0)); // check X register
		assertEquals(n(3), calculator.getFinanceMemory().getI()); // check I register
	}
}
