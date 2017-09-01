package net.sf.finanx.hp12c.model;

import static net.sf.finanx.math.Number.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import net.sf.finanx.hp12c.model.FinanceMemory;
import net.sf.finanx.math.Number;
import net.sf.finanx.utils.Date;

/**
*
* @see StackTest
*  
*/
public class FinanceMemoryTest {

	private FinanceMemory fin;
	
	Number cf1[][];
	Number cf2[][];
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		fin = new FinanceMemory();
		initCashFlow();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void simpleInterestTest() throws Exception {
		
		// HP12C Manual
		fin.clear();
		fin.setN(n(60)); // number of days
		fin.setI(n(7)); // annual interest rate
		fin.setPv(n(-450)); // principal amount
		Number INT_360 = n(5.25); // expected accrued simple interest on a 360-day basis
		Number INT_365 = n(5.18); // expected accrued simple interest on a 365-day basis
		Number tmp[] = fin.simpleInterest();
		assertEquals(INT_360, tmp[0].round(2));
		assertEquals(INT_365, tmp[1].round(2));
	}
	
	@Test
	public void simpleFutureValueTest() throws Exception {
		
		// Neto 2012
		fin.clear();
		fin.setN(n(8)); // number of days
		fin.setI(n(1.5)); // annual interest rate
		fin.setPv(n(-18000)); // principal amount
		assertEquals(n(20160), fin.simpleFutureValue().round(2));
	}
	
	@Test
	public void rateTest() throws Exception {
		
		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setN(n(8).multiply(n(4)));
		fin.setPv(n(-6000));
		fin.setPmt(n(0));
		fin.setFv(n(10000));
		Number i = n(1.61);
		assertEquals(i, fin.rate().round(2));
	}
	
	@Test
	public void periodTest() throws Exception {

		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setI(n(10.5).divide(TWELVE));
		fin.setPv(n(35000));
		fin.setPmt(n(-325));
		assertEquals(n(328), fin.period());
		
		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setI(n(6.25).divide(n(24)));
		fin.setPv(n(-775));
		fin.setPmt(n(-50));
		fin.setFv(n(4000));
		assertEquals(n(58), fin.period());
		
		// SF Bugs#3
		// [150] [CHS] [PV] [50000] [FV] [2] [i] [n]
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setI(n(2));
		fin.setPv(n(-150));
		fin.setFv(n(50000));
		assertEquals(n(294), fin.period());
	}
	
	@Test
	public void presentValueTest() throws Exception {

		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setN(n(4).multiply(TWELVE));
		fin.setI(n(15).divide(TWELVE));
		fin.setPmt(n(-150));
		assertEquals(n(5389.72), fin.presentValue().round(2));
		
		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setN(n(5));
		fin.setI(n(12));
		fin.setPmt(n(17500));
		fin.setFv(n(540000));
		assertEquals(n(-369494.09), fin.presentValue().round(2));
	}
	
	@Test
	public void pricePaymentTest() throws Exception {
		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setN(n(29).multiply(TWELVE));
		fin.setI(n(14.25).divide(TWELVE));
		fin.setPv(n(43400));
		assertEquals(n(-523.99), fin.pricePayment().round(2));
		
		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setN(n(15).multiply(TWO));
		fin.setI(n(9.75).divide(TWO));
		fin.setPv(n(-3200));
		fin.setFv(n(60000));
		assertEquals(n(-717.44), fin.pricePayment().round(2));
	}
	
	
	@Test
	public void futureValueTest() throws Exception {
		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setN(n(5).multiply(TWELVE));
		fin.setI(n(14.25).divide(TWELVE));
		fin.setPv(n(43400));
		fin.setPmt(n(-523.99));
		assertEquals(n(-42652.37), fin.futureValue().round(2));
		
		// HP12C Manual
		fin.clear();
		fin.setBegin(true);
		fin.setC(false);
		fin.setN(n(2).multiply(TWELVE));
		fin.setI(n(6.25).divide(TWELVE));
		fin.setPmt(n(-50));
		assertEquals(n(1281.34), fin.futureValue().round(2));
	}
	
	@Test
	public void amortizationTest() throws Exception {
		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setI(n(13.25).divide(TWELVE));
		fin.setPv(n(50000));
		fin.setPmt(n(-573.35));
		Number x = n(12);
		Number tmp[] = fin.amortization(x, 2);
		assertEquals(n(12), tmp[0]); // Number of payment periods to be amortized
		assertEquals(n(-271.31), tmp[1].round(2)); // Returns a sum of the amortizations in the informed period
		assertEquals(n(-6608.89), tmp[2].round(2)); // Returns a sum of the interests paid in the informed period
		assertEquals(n(49728.69), tmp[3].round(2)); // Returns the remaining balance
		assertEquals(n(12), tmp[4]); // Total number of payments amortized
	}
	
	private void initCashFlow() {
		
		// HP12C Manual
		cf1 = new Number[6][2]; // cash flow
		cf1[0][0] = n(-80000);
		cf1[1][0] = n(-500);
		cf1[2][0] = n(4500);
		cf1[3][0] = n(5500);
		cf1[4][0] = n(4500);
		cf1[5][0] = n(130000);
		cf1[0][1] = n(1);
		cf1[1][1] = n(1);
		cf1[2][1] = n(1);
		cf1[3][1] = n(1);
		cf1[4][1] = n(1);
		cf1[5][1] = n(1);
		
		// HP12C Manual
		cf2 = new Number[8][2]; // cash flow
		cf2[0][0] = n(-79000);
		cf2[1][0] = n(14000);
		cf2[2][0] = n(11000);
		cf2[3][0] = n(10000);
		cf2[4][0] = n(9100);
		cf2[5][0] = n(9000);
		cf2[6][0] = n(4500);
		cf2[7][0] = n(100000);
		cf2[0][1] = n(1);
		cf2[1][1] = n(1);
		cf2[2][1] = n(1);
		cf2[3][1] = n(3);
		cf2[4][1] = n(1);
		cf2[5][1] = n(2);
		cf2[6][1] = n(1);
		cf2[7][1] = n(1);
	}
	
	@Test
	public void ntvTest() throws Exception {
		// HP12C Manual
		fin.clear();
		fin.setN(n(5));
		fin.setI(n(13));
		assertEquals(n(212.18), fin.npv(cf1).round(2));
		
		// HP12C Manual
		fin.clear();
		fin.setN(n(7));
		fin.setI(n(13.50));
		assertEquals(n(907.77), fin.npv(cf2).round(2));
	}
	
	@Test
	public void irrTest() throws Exception {
		// HP12C Manual
		fin.clear();
		fin.setN(n(7));
		fin.setI(n(13.50));
		assertEquals(n(13.72), fin.irr(cf2).round(2));
	}
	
	@Test
	public void bontPriceTest() throws Exception {
		
		// HP12C Manual
		fin.clear();
		fin.setI(n(8.25));
		fin.setPmt(n(6.75));
		Date date1 = new Date(2004, 04, 28);
		Date date2 = new Date(2018, 06, 04);
		Number tmp[] = fin.bondPrice(date1, date2);
		assertEquals(n(87.62), tmp[0].round(2)); // Bond price (as a percent of par).
		assertEquals(n(90.31), tmp[0].add(tmp[1]).round(2)); // Total price, including accrued interest.
	}
	
	@Test
	public void dbDepreciationTest() throws Exception {
		
		// HP12C Manual
		fin.clear();
		fin.setBegin(false);
		fin.setC(false);
		fin.setN(n(5));
		fin.setI(n(200));
		fin.setPv(n(10000));
		fin.setFv(n(500));
		Number x = n(1);
		Number ans[] = fin.dbDepreciation(x);
		assertEquals(n(4000), ans[0].round(2));
		assertEquals(n(5500), ans[1].round(2));
	}
	
	@Ignore
	@Test
	public void Test() throws Exception {
		
	}
}
