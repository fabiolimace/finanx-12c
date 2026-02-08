package net.sf.finanx.fx12c.memory;

import static net.sf.finanx.fx12c.math.Number.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.finanx.fx12c.calc.Calculator;
import net.sf.finanx.fx12c.calc.Op;
import net.sf.finanx.fx12c.calc.Flags;

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
	public void testSquareProgram() throws Exception {
		testSquareProgram(1);
		testSquareProgram(2);
	}

	private void testSquareProgram(double x) throws Exception {
		setUp();
		assertEquals(Math.pow(x, 2), squareProgram(x), 0);
	}

	private double squareProgram(double x) {

//		X
//		ENTER
//		2	POWER

		cal.getStack().put(n(x));
		cal.getDisplay().setValue(n(x));

		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_2);
		cal.getProgramMemory().put(Op.OP_POWER);

		executeProgram();

		return stk(0);
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

		cal.getProgramMemory().put(Op.OP_XLEY);
		cal.getProgramMemory().put(Op.OP_SWAP_XY);
		cal.getProgramMemory().put(Op.OP_CLX);
		cal.getProgramMemory().put(Op.OP_ADD);

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

		cal.getProgramMemory().put(Op.OP_XLEY);
		cal.getProgramMemory().put(Op.OP_SWAP_XY);
		cal.getProgramMemory().put(Op.OP_SWAP_XY);
		cal.getProgramMemory().put(Op.OP_CLX);
		cal.getProgramMemory().put(Op.OP_ADD);

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

		cal.getProgramMemory().put(Op.OP_SWAP_XY);
		cal.getProgramMemory().put(Op.OP_XLEY);
		cal.getProgramMemory().put(Op.OP_CHS);
		cal.getProgramMemory().put(Op.OP_ADD);

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

		cal.getProgramMemory().put(Op.OP_LN);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_0);
		cal.getProgramMemory().put(Op.OP_LN);
		cal.getProgramMemory().put(Op.OP_DIV);

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

		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_DIV);
		cal.getProgramMemory().put(Op.OP_LASTX);
		cal.getProgramMemory().put(Op.OP_SWAP_XY);
		cal.getProgramMemory().put(Op.OP_INTG);
		cal.getProgramMemory().put(Op.OP_MUL);
		cal.getProgramMemory().put(Op.OP_SUB);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testGlibcRandProgram() throws Exception {
		testGlibcRandProgram(51);
		testGlibcRandProgram(37);
		testGlibcRandProgram(17);
		testGlibcRandProgram(7);
		testGlibcRandProgram(5);
		testGlibcRandProgram(3);
		testGlibcRandProgram(2);
		testGlibcRandProgram(1);
		testGlibcRandProgram(0);
	}

	private void testGlibcRandProgram(long x) throws Exception {
		setUp();
		assertEquals(glibcRand(x), glibcRandProgram(x), 0);
	}

	private double glibcRand(long seed) {
		// GLIBC constants: a = 1103515245, c = 12345, m = 2^31, and seed > 0
		return (((seed == 0 ? 1 : seed) * 1103515245) + 12345) & 0x7fffffffL;
	}

	private double glibcRandProgram(long x) {

		/*
		 * NOTE: this program is not suitable for physical HP12C due to precision loss.
		 * We use the GLIBC's constants here only to test the program memory execution.
		 * In physical HP12C the constants of the random generator should be different.
		 * Check the test for MINSTD random generator, also known as Lehmer RNG (1988).
		 */

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
		cal.getProgramMemory().put(Op.OP_XEQO);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_ENTER);

		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_0);
		cal.getProgramMemory().put(Op.OP_3);
		cal.getProgramMemory().put(Op.OP_5);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_5);
		cal.getProgramMemory().put(Op.OP_2);
		cal.getProgramMemory().put(Op.OP_4);
		cal.getProgramMemory().put(Op.OP_5);
		cal.getProgramMemory().put(Op.OP_MUL);

		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_2);
		cal.getProgramMemory().put(Op.OP_3);
		cal.getProgramMemory().put(Op.OP_4);
		cal.getProgramMemory().put(Op.OP_5);
		cal.getProgramMemory().put(Op.OP_ADD);

		cal.getProgramMemory().put(Op.OP_2);
		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_3);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_POWER);

		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_DIV);
		cal.getProgramMemory().put(Op.OP_LASTX);
		cal.getProgramMemory().put(Op.OP_SWAP_XY);
		cal.getProgramMemory().put(Op.OP_INTG);
		cal.getProgramMemory().put(Op.OP_MUL);
		cal.getProgramMemory().put(Op.OP_SUB);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testMinstdRandProgram() throws Exception {
		testMinstdRandProgram(51);
		testMinstdRandProgram(37);
		testMinstdRandProgram(17);
		testMinstdRandProgram(7);
		testMinstdRandProgram(5);
		testMinstdRandProgram(3);
		testMinstdRandProgram(2);
		testMinstdRandProgram(1);
		testMinstdRandProgram(0);
	}

	private void testMinstdRandProgram(long x) throws Exception {
		setUp();
		assertEquals(minstdRand(x), minstdRandProgram(x), 0);
	}

	private double minstdRand(long seed) {
		// Original 1988 MINSTD parameters: a = 7^5, m = 2^31-1
		// https://en.wikipedia.org/wiki/Lehmer_random_number_generator
		return Math.floorMod(((seed == 0 ? 1 : seed) * 16807), 2147483647);
	}

	private double minstdRandProgram(long x) {

		/*
		 * This is the original Lehmer random number generator (1988), known as MINSTD.
		 * Later the author suggested the use of the multiplier 48271 in place of 16807.
		 */

//		X
//		XEQ0
//		1	 		ENTER
//		7			ENTER
//		5			POWER
//		MUL
//		2			ENTER
//		31			POWER
//		1			SUB
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
		cal.getProgramMemory().put(Op.OP_XEQO);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_ENTER);

		cal.getProgramMemory().put(Op.OP_7);
		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_5);
		cal.getProgramMemory().put(Op.OP_POWER);
		cal.getProgramMemory().put(Op.OP_MUL);

		cal.getProgramMemory().put(Op.OP_2);
		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_3);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_POWER);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_SUB);

		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_R_DOWN);
		cal.getProgramMemory().put(Op.OP_DIV);
		cal.getProgramMemory().put(Op.OP_LASTX);
		cal.getProgramMemory().put(Op.OP_SWAP_XY);
		cal.getProgramMemory().put(Op.OP_INTG);
		cal.getProgramMemory().put(Op.OP_MUL);
		cal.getProgramMemory().put(Op.OP_SUB);

		executeProgram();

		return stk(0);
	}
}
