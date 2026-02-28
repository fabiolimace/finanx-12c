package net.sf.finanx.fx12c.memory;

import static net.sf.finanx.fx12c.math.Number.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sf.finanx.fx12c.calc.Calculator;
import net.sf.finanx.fx12c.calc.Op;
import net.sf.finanx.fx12c.calc.Flags;

/**
 * These tests are based on the video "Extending the HP-12C: Programming for
 * Scientific Applications ", by Edward Shore.
 * 
 * URL: https://www.youtube.com/watch?v=Q54UbDzpAIw
 * 
 * Paper:
 * https://hhuc.us/2017/files/speakers/Eddie_Shore/Expanding_the_HP_12C-Notes.pdf
 */
public class ProgramMemoryTest2 {

	private Calculator cal;

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

	private void executeProgram() {
		cal.getProgramMemory().setCurrentIndex(0);
		cal.getFlags().toggleRun();
		cal.executeProgram();
	}

	private double stk(int i) {
		return cal.getStack().get(i).d();
	}

	@Test
	public void testTemplateProgram() throws Exception {
		for (double x : new double[] { 1, 2, 3 }) {
			setUp();
			assertEquals(x, templateProgram(x), 1e-9);
		}
	}

	private double templateProgram(double x) {

//		X
//		ENTER

		cal.getStack().put(n(x));
		cal.getDisplay().setValue(n(x));

		cal.getProgramMemory().put(Op.OP_ENTER);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testMultiplyBy100Program() throws Exception {
		for (double x : new double[] { 0, 1, 2, 37, Math.E, Math.PI }) {
			setUp();
			assertEquals(x * 100, multiplyBy100Program(x), 1e-9);
		}
	}

	private double multiplyBy100Program(double x) {

//		X
//		ENTER
//		1
//		SWAP_XY
//		PERCENT_TOTAL

		cal.getStack().put(n(x));
		cal.getDisplay().setValue(n(x));

		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_SWAP_XY);
		cal.getProgramMemory().put(Op.OP_PERCENT_TOTAL);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testDivideyBy100Program() throws Exception {
		for (double x : new double[] { 0, 1, 2, 37, Math.E, Math.PI }) {
			setUp();
			assertEquals(x / 100.0, divideBy100Program(x), 1e-9);
		}
	}

	private double divideBy100Program(double x) {

//		X
//		ENTER
//		1
//		PERCENT

		cal.getStack().put(n(x));
		cal.getDisplay().setValue(n(x));

		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_1);
		cal.getProgramMemory().put(Op.OP_PERCENT);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testAbsoluteValueProgram() throws Exception {
		for (double x : new double[] { 0, -1, 2, -37, Math.E, -Math.PI }) {
			setUp();
			assertEquals(Math.abs(x), absoluteValueProgram(x), 1e-9);
		}
	}

	private double absoluteValueProgram(double x) {

//		X
//		ENTER
//		2
//		POWER
//		SQRT

		cal.getStack().put(n(x));
		cal.getDisplay().setValue(n(x));

		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_2);
		cal.getProgramMemory().put(Op.OP_POWER);
		cal.getProgramMemory().put(Op.OP_SQRT);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testSignFunctionProgram() throws Exception {
		for (double x : new double[] { 0, -1, 2, -37, Math.E, -Math.PI }) {
			setUp();
			assertEquals(Math.signum(x), signFunctionProgram(x), 1e-9);
		}
	}

	private double signFunctionProgram(double x) {

//		X
//		ENTER
//		ENTER
//		2
//		POWER
//		SQRT
//		DIV

		cal.getStack().put(n(x));
		cal.getDisplay().setValue(n(x));

		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_ENTER);
		cal.getProgramMemory().put(Op.OP_2);
		cal.getProgramMemory().put(Op.OP_POWER);
		cal.getProgramMemory().put(Op.OP_SQRT);
		cal.getProgramMemory().put(Op.OP_DIV);

		executeProgram();

		return stk(0);
	}

	@Test
	public void testModulosProgram() throws Exception {
		for (double x : new double[] { 41, 53, 61, 73, 83, 97 }) {
			for (double y : new double[] { 1, 2, 7, 13, 23, 37 }) {
				setUp();
				assertEquals(Math.floorMod((int) y, (int) x), (int) templateModulusProgram(y, x), 1e-9);
			}
		}
	}

	private double templateModulusProgram(double y, double x) {

//		Y
//		ENTER
//		X
//		DIV
//		LASTX
//		SWAP_XY
//		FRAC
//		MUL

		cal.getStack().put(n(y));
		cal.getStack().put(n(x));
		cal.getDisplay().setValue(n(x));

		cal.getProgramMemory().put(Op.OP_DIV);
		cal.getProgramMemory().put(Op.OP_LASTX);
		cal.getProgramMemory().put(Op.OP_SWAP_XY);
		cal.getProgramMemory().put(Op.OP_FRAC);
		cal.getProgramMemory().put(Op.OP_MUL);

		executeProgram();

		return stk(0);
	}
}
