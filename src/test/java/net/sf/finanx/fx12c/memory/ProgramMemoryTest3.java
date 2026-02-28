package net.sf.finanx.fx12c.memory;

import static net.sf.finanx.fx12c.math.Number.*;
import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.finanx.fx12c.calc.Op;

/**
 * This tests implement classic random number generators for HP12C.
 */
public class ProgramMemoryTest3 extends ProgramMemoryTest {

	@Test
	public void testGlibcRandProgram() throws Exception {

		for (long x : new long[] { 0, 1, 2, 3, 5, 7, 17, 37, 51 }) {
			setUp();
			assertEquals(glibcRand(x), glibcRandProgram(x), 0);
		}

		long x = 0;
		for (int i = 0; i < 10; i++) {
			setUp();
			assertEquals(glibcRand(x), glibcRandProgram(x));
			x = glibcRand(x);
		}
	}

	private long glibcRand(long seed) {
		// GLIBC constants: a = 1103515245, c = 12345, m = 2^31, and seed > 0
		return (((seed == 0 ? 1 : seed) * 1103515245) + 12345) & 0x7fffffffL;
	}

	private long glibcRandProgram(long x) {

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

		return (long) stk(0);
	}

	@Test
	public void testMinstdRandProgram() throws Exception {

		for (long x : new long[] { 0, 1, 2, 3, 5, 7, 17, 37, 51 }) {
			setUp();
			assertEquals(minstdRand(x), minstdRandProgram(x), 0);
		}

		long x = 0;
		for (int i = 0; i < 10; i++) {
			setUp();
			assertEquals(minstdRand(x), minstdRandProgram(x));
			x = glibcRand(x);
		}
	}

	private long minstdRand(long seed) {
		// Original 1988 MINSTD parameters: a = 7^5, m = 2^31-1
		// https://en.wikipedia.org/wiki/Lehmer_random_number_generator
		return Math.floorMod(((seed == 0 ? 1 : seed) * 16807), 2147483647);
	}

	private long minstdRandProgram(long x) {

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

		return (long) stk(0);
	}
}
