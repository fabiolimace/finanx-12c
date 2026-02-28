package net.sf.finanx.fx12c.memory;

import static net.sf.finanx.fx12c.math.Number.*;
import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.finanx.fx12c.calc.Op;

/**
 * This tests implement basic math functions not present in HP12C.
 */
public class ProgramMemoryTest1 extends ProgramMemoryTest {

	@Test
	public void testSquareProgram() throws Exception {
		for (double x : new double[] { 0, 1, 2, 37, Math.E, Math.PI }) {
			setUp();
			assertEquals(Math.pow(x, 2), squareProgram(x), 0);
		}
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
		for (double x : new double[] { 0, 1, 2, 37, Math.E, Math.PI }) {
			for (double y : new double[] { 0, 1, 2, 37, Math.E, Math.PI }) {
				setUp();
				assertEquals(Math.min(y, x), minProgram(y, x), 0);
			}
		}
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
		for (double x : new double[] { 0, 1, 2, 37, Math.E, Math.PI }) {
			for (double y : new double[] { 0, 1, 2, 37, Math.E, Math.PI }) {
				setUp();
				assertEquals(Math.max(y, x), maxProgram(y, x), 0);
			}
		}
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
		for (double x : new double[] { 0, -1, 2, -37, Math.E, -Math.PI }) {
			setUp();
			assertEquals(Math.abs(x), absProgram(x), 1e-9);
		}
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
		for (double x : new double[] { 1, 2, 3, 5, 7, 17, 21, 37, 99, 999, 9999 }) {
			setUp();
			assertEquals(Math.log10(x), log10Program(x), 1e-9);
		}
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
		for (long x : new long[] { 41, 53, 61, 73, 83, 97, 249 }) {
			for (long y : new long[] { 1, 2, 7, 9, 13, 23, 37 }) {
				setUp();
				assertEquals(Math.floorMod(y, x), modProgram(y, x), 0);
			}
		}
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
}
