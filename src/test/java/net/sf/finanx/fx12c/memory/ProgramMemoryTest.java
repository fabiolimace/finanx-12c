package net.sf.finanx.fx12c.memory;

import static net.sf.finanx.fx12c.math.Number.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.finanx.fx12c.calc.Calculator;
import net.sf.finanx.fx12c.calc.Flags;
import net.sf.finanx.fx12c.calc.Step;

/**
 *
 * @see StackTest
 * 
 */
public class ProgramMemoryTest {

	private Calculator cal;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		Flags flg = new Flags();
		Stack stk = new Stack();
		ProgramMemory prg = new ProgramMemory();

		cal = new Calculator(/* testing = */ true);
		cal.setFlags(flg);
		cal.setStack(stk);
		cal.setProgramMemory(prg);
	}

	@After
	public void tearDown() throws Exception {
	}

	private void executeProgram() {
		cal.getProgramMemory().setCurrentIndex(0);
		cal.getFlags().toggleRun();
		cal.executeProgram();
	}

	private double stk(int i) {
		return cal.getStack().get(i).d();
	}

	@Test
	public void testMinProgram() throws Exception {
		testMinProgram(1, 1);
		testMinProgram(1, 2);
		testMinProgram(2, 1);
		testMinProgram(2, 2);
	}

	private void testMinProgram(double y, double x) throws Exception {
		setUp();
		assertEquals(Math.min(y, x), minProgram(y, x), 0);
	}

	private double minProgram(double y, double x) {

//		Y	ENTER
//		X
//		XLEY
//		SWAPXY
//		CLX
//		ADD

		cal.getStack().put(n(y));
		cal.getStack().put(n(x));

		cal.getProgramMemory().put(Step.STP_G_XLEY);
		cal.getProgramMemory().put(Step.STP_SWAPXY);
		cal.getProgramMemory().put(Step.STP_CLX);
		cal.getProgramMemory().put(Step.STP_ADD);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testMaxProgram() throws Exception {
		testMaxProgram(1, 1);
		testMaxProgram(1, 2);
		testMaxProgram(2, 1);
		testMaxProgram(2, 2);
	}

	private void testMaxProgram(double y, double x) throws Exception {
		setUp();
		assertEquals(Math.max(y, x), maxProgram(y, x), 0);
	}

	private double maxProgram(double y, double x) {

//		Y	ENTER
//		X
//		XLEY
//		SWAPXY
//		SWAPXY
//		CLX
//		ADD

		cal.getStack().put(n(y));
		cal.getStack().put(n(x));

		cal.getProgramMemory().put(Step.STP_G_XLEY);
		cal.getProgramMemory().put(Step.STP_SWAPXY);
		cal.getProgramMemory().put(Step.STP_SWAPXY);
		cal.getProgramMemory().put(Step.STP_CLX);
		cal.getProgramMemory().put(Step.STP_ADD);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testAbsProgram() throws Exception {
		testAbsProgram(0);
		testAbsProgram(1);
		testAbsProgram(-1);
		testAbsProgram(31);
		testAbsProgram(-57);
	}

	private void testAbsProgram(double x) throws Exception {
		setUp();
		assertEquals(Math.abs(x), absProgram(x), 0);
	}

	private double absProgram(double x) {

//		x	ENTER
//		0
//		SWAPXY
//		XLEY
//		CHS
//		ADD

		cal.getStack().put(n(x));
		cal.getStack().put(n(0));

		cal.getProgramMemory().put(Step.STP_SWAPXY);
		cal.getProgramMemory().put(Step.STP_G_XLEY);
		cal.getProgramMemory().put(Step.STP_CHS);
		cal.getProgramMemory().put(Step.STP_ADD);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testLog10Program() throws Exception {
		testLog10Program(3);
		testLog10Program(5);
		testLog10Program(17);
		testLog10Program(31);
		testLog10Program(37);
	}

	private void testLog10Program(double x) throws Exception {
		setUp();
		assertEquals(Math.log10(x), log10Program(x), 1e-10);
	}

	private double log10Program(double x) {

//		X	LN
//		10	LN
//		DIV

		cal.getStack().put(n(x));

		cal.getProgramMemory().put(Step.STP_G_LN);
		cal.getProgramMemory().put(Step.STP_1);
		cal.getProgramMemory().put(Step.STP_0);
		cal.getProgramMemory().put(Step.STP_G_LN);
		cal.getProgramMemory().put(Step.STP_DIV);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testModProgram() throws Exception {
		testModProgram(249, 51);
		testModProgram(73, 37);
		testModProgram(37, 17);
		testModProgram(9, 5);
	}

	private void testModProgram(long y, long x) throws Exception {
		setUp();
		assertEquals(Math.floorMod(y, x), modProgram(y, x), 0);
	}

	private double modProgram(long y, long x) {

//		y	ENTER
//		x	ENTER
//		RDOWN
//		RDOWN
//		ENTER
//		RDOWN
//		RDOWN
//		RDOWN
//		DIV
//		LASTX
//		SWAPXY
//		INTG
//		MUL
//		SUB

		cal.getStack().put(n(y));
		cal.getStack().put(n(x));
		cal.getDisplay().setValue(n(x));

		cal.getProgramMemory().put(Step.STP_ENTER);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_ENTER);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_DIV);
		cal.getProgramMemory().put(Step.STP_G_LASTX);
		cal.getProgramMemory().put(Step.STP_SWAPXY);
		cal.getProgramMemory().put(Step.STP_G_INTG);
		cal.getProgramMemory().put(Step.STP_MUL);
		cal.getProgramMemory().put(Step.STP_SUB);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testRandProgram() throws Exception {
		testRandProgram(51);
		testRandProgram(37);
		testRandProgram(17);
		testRandProgram(7);
		testRandProgram(5);
		testRandProgram(3);
		testRandProgram(2);
		testRandProgram(1);
		testRandProgram(0);
	}

	private void testRandProgram(long x) throws Exception {
		setUp();
		assertEquals(glibcRand(x), randProgram(x), 0);
	}

	private double glibcRand(long seed) {
		// GLIBC constants: a = 1103515245, c = 12345, m = 2^31, and seed > 0
		return (((seed == 0 ? 1 : seed) * 1103515245) + 12345) & 0x7fffffffL;
	}

	private double randProgram(long x) {

//		X
//		XEQ0
//		1	 		ENTER
//		1103515245	MUL
//		12345		ADD
//		2			ENTER
//		31			POW
//		ENTER
//		RDOWN
//		RDOWN
//		ENTER
//		RDOWN
//		RDOWN
//		RDOWN
//		DIV
//		LASTX
//		SWAPXY
//		INTG
//		MUL
//		SUB

		cal.getStack().put(n(x));
		cal.getDisplay().setValue(n(x));
		cal.getProgramMemory().put(Step.STP_G_XEQO);
		cal.getProgramMemory().put(Step.STP_1);
		cal.getProgramMemory().put(Step.STP_ENTER);

		cal.getProgramMemory().put(Step.STP_1);
		cal.getProgramMemory().put(Step.STP_1);
		cal.getProgramMemory().put(Step.STP_0);
		cal.getProgramMemory().put(Step.STP_3);
		cal.getProgramMemory().put(Step.STP_5);
		cal.getProgramMemory().put(Step.STP_1);
		cal.getProgramMemory().put(Step.STP_5);
		cal.getProgramMemory().put(Step.STP_2);
		cal.getProgramMemory().put(Step.STP_4);
		cal.getProgramMemory().put(Step.STP_5);
		cal.getProgramMemory().put(Step.STP_MUL);

		cal.getProgramMemory().put(Step.STP_1);
		cal.getProgramMemory().put(Step.STP_2);
		cal.getProgramMemory().put(Step.STP_3);
		cal.getProgramMemory().put(Step.STP_4);
		cal.getProgramMemory().put(Step.STP_5);
		cal.getProgramMemory().put(Step.STP_ADD);

		cal.getProgramMemory().put(Step.STP_2);
		cal.getProgramMemory().put(Step.STP_ENTER);
		cal.getProgramMemory().put(Step.STP_3);
		cal.getProgramMemory().put(Step.STP_1);
		cal.getProgramMemory().put(Step.STP_POWER);

		cal.getProgramMemory().put(Step.STP_ENTER);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_ENTER);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_RDOWN);
		cal.getProgramMemory().put(Step.STP_DIV);
		cal.getProgramMemory().put(Step.STP_G_LASTX);
		cal.getProgramMemory().put(Step.STP_SWAPXY);
		cal.getProgramMemory().put(Step.STP_G_INTG);
		cal.getProgramMemory().put(Step.STP_MUL);
		cal.getProgramMemory().put(Step.STP_SUB);

		executeProgram();

		return stk(0);
	}
}
