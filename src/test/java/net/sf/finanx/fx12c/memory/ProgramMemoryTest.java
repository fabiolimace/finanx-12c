package net.sf.finanx.fx12c.memory;

import static net.sf.finanx.fx12c.math.Number.*;
import static org.junit.Assert.*;

import org.junit.Before;

import net.sf.finanx.fx12c.calc.Calculator;
import net.sf.finanx.fx12c.calc.Op;
import net.sf.finanx.fx12c.calc.Flags;

public abstract class ProgramMemoryTest {

	protected Calculator cal;

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

	protected void executeProgram() {
		cal.getProgramMemory().setCurrentIndex(0);
		cal.getFlags().toggleRun();
		cal.executeProgram();
	}

	protected double stk(int i) {
		return cal.getStack().get(i).d();
	}

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
}
