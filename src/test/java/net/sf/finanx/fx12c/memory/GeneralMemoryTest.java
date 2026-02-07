package net.sf.finanx.fx12c.memory;

import static net.sf.finanx.fx12c.math.Number.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.finanx.fx12c.math.Number;
import net.sf.finanx.fx12c.memory.GeneralMemory;

public class GeneralMemoryTest {

	private GeneralMemory mem;
	private Number xValues[];
	private Number yValues[];
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mem = new GeneralMemory();
		
		xValues = new Number[10];
		yValues = new Number[10];
		
		mem.clear();
		yValues[0] = n(32);
		xValues[0] = n(17000);
		yValues[1] = n(40);
		xValues[1] = n(25000);
		yValues[2] = n(45);
		xValues[2] = n(26000);
		yValues[3] = n(40);
		xValues[3] = n(20000);
		yValues[4] = n(38);
		xValues[4] = n(21000);
		yValues[5] = n(50);
		xValues[5] = n(28000);
		yValues[6] = n(35);
		xValues[6] = n(15000);
		
		setStatRegisters(xValues, yValues);
	}

	@After
	public void tearDown() throws Exception {
	}

	private void setStatRegisters(Number[] xValues, Number[] yValues) {
		
		Number reg[] = new Number[6];
		
		if(xValues.length != yValues.length) {
			return;
		}
		
		reg[0] = n(0);
		reg[1] = n(0);
		reg[2] = n(0);
		reg[3] = n(0);
		reg[4] = n(0);
		reg[5] = n(0);
		
		for (int i = 0; i < xValues.length; i++) {
			if (xValues[i] == null || yValues[i] == null) break;
			
			reg[0] = reg[0].add(n(1));
			
			reg[1] = reg[1].add(xValues[i]);
			reg[2] = reg[2].add(xValues[i].pow(n(2)));
			
			reg[3] = reg[3].add(yValues[i]);
			reg[4] = reg[4].add(yValues[i].pow(n(2)));
			
			reg[5] = reg[5].add(xValues[i].multiply(yValues[i]));
		}
		
		mem.setR1(reg[0]);
		mem.setR2(reg[1]);
		mem.setR3(reg[2]);
		mem.setR4(reg[3]);
		mem.setR5(reg[4]);
		mem.setR6(reg[5]);
	}
	
	@Test
	public void meanTest() {
		Number ans[] = mem.mean();
		assertEquals(n(21714.29), ans[0].round(2));
		assertEquals(n(40), ans[1]);
	}
	
	@Test
	public void standardDeviationTest() {
		Number ans[] = mem.standardDeviation();
		assertEquals(n(4820.59), ans[0].round(2));
		assertEquals(n(6.03), ans[1].round(2));
	}
	
	@Test
	public void xLinearEstimationTest() {
		Number yValue = n(48);
		Number ans[] = mem.xLinearEstimation(yValue);
		assertEquals(n(28818.93), ans[0].round(2));
		assertEquals(n(0.90), ans[1].round(2));
	}
	
	@Test
	public void yLinearEstimationTest() {		
		Number xValue = ZERO;
		Number ans[] = mem.yLinearEstimation(xValue);
		assertEquals(n(15.55), ans[0].round(2));
		assertEquals(n(0.90), ans[1].round(2));
	}
	
	
	@Test
	public void weightedMeanTest() {
		
		Number x[] = new Number[10];
		Number y[] = new Number[10];
		
		mem.clear();
		y[0] = n(1.16);
		x[0] = n(15);
		y[1] = n(1.24);
		x[1] = n(7);
		y[2] = n(1.20);
		x[2] = n(10);
		y[3] = n(1.18);
		x[3] = n(17);
		
		setStatRegisters(x, y);
		assertEquals(n(1.19), mem.weightedMean().round(2));
	}
}
