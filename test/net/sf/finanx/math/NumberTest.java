package net.sf.finanx.math;

import static net.sf.finanx.math.Number.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.finanx.math.Number;

public class NumberTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void addTest() {
		assertEquals(n(4), n(2).add(n(2)));
	}

	@Test
	public void subtractTest() {
		assertEquals(n(1), n(3).subtract(n(2)));
	}
	
	@Test
	public void multiplyTest() {
		assertEquals(n(10), n(2).multiply(n(5)));
	}
	
	@Test
	public void divideTest() {
		assertEquals(n(4), n(12).divide(n(3)));
	}
	
	@Test
	public void remainderTest() {
		assertEquals(n(1), n(7).remainder(n(3)));
	}
	
	@Test
	public void integralPartTest(){
		assertEquals(n(3.75).integralPart(), n(3));
		assertEquals(n(-1.42).integralPart(), n(-1));
	}
	
	@Test
	public void fractionalPartTest(){
		assertEquals(n(0.75).round(), n(3.75).fractionalPart().round());
		assertEquals(n(-0.42).round(), n(-1.42).fractionalPart().round());
	}
	
	@Test
	public void floorTest(){
		assertEquals(n(5), n(5.5).floor());
		assertEquals(n(-6), n(-5.5).floor());
	}
	
	@Test
	public void ceilTest(){
		assertEquals(n(6), n(5.5).ceil());
		assertEquals(n(-5), n(-5.5).ceil());
	}
	
	@Test
	public void roundTest(){
		assertEquals(n(5), n(5.4).round(0));
		assertEquals(n(6), n(5.5).round(0));
		assertEquals(n(6), n(5.6).round(0));
		assertEquals(n(-6), n(-6.4).round(0));
		assertEquals(n(-7), n(-6.5).round(0));
		assertEquals(n(-7), n(-6.6).round(0));
	}
	
	@Test
	public void powTest(){
		assertEquals(n(2).pow(n(10)), n(1024));
		assertEquals(n(5).pow(n(3)), n(125));

		// 3^(-5) = 1 / 3^5
		assertEquals(n(3).pow(n(-5)).round(), n(3).pow(n(5)).reciprocal().round());
		
		// 3^(2.1) = 10.0451...
		assertEquals(n(3).pow(n(2.1)).round(), n(Math.pow(3, 2.1)).round());
	}
	
	@Test
	public void sqrtTest(){
		assertEquals(n(3), n(9).sqrt());
		assertEquals(n(Math.sqrt(2)).round(), n(2).sqrt().round());
	}
	
	@Test
	public void cbrtTest(){
		assertEquals(n(27).cbrt(), n(3));
		assertEquals(n(8).cbrt(), n(2));
	}

	@Test
	public void logTest(){
		assertEquals(n(3).log().round(), n(Math.log(3)).round());
	}
	
	@Test
	public void log10Test(){
		assertEquals(n(3).log10().round(), n(Math.log10(3)).round());
	}
	
	@Test
	public void sinTest() {
		assertEquals(n(90).toRadians().sin().round(), Number.ONE);
		assertEquals(n(30).toRadians().sin().round(), Number.HALF);
	}
	
	@Test
	public void cosTest() {
		assertEquals(n(30).toRadians().cos().round(), n(60).toRadians().sin().round());
		assertEquals(n(60).toRadians().cos().round(), Number.HALF);
	}
	
	@Test
	public void tanTest() {
		assertEquals(n(45).toRadians().tan().round(), ONE);
		assertEquals(n(60).toRadians().tan().round(), THREE.sqrt().round());
	}
}
