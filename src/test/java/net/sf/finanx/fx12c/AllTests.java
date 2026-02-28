package net.sf.finanx.fx12c;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.sf.finanx.fx12c.calc.CalculatorTest;
import net.sf.finanx.fx12c.math.NumberTest;
import net.sf.finanx.fx12c.memory.FinanceMemoryTest;
import net.sf.finanx.fx12c.memory.GeneralMemoryTest;
import net.sf.finanx.fx12c.memory.ProgramMemoryTest1;
import net.sf.finanx.fx12c.memory.ProgramMemoryTest2;
import net.sf.finanx.fx12c.memory.ProgramMemoryTest3;
import net.sf.finanx.fx12c.memory.StackTest;

@RunWith(Suite.class)
@SuiteClasses({ NumberTest.class, FinanceMemoryTest.class, GeneralMemoryTest.class, ProgramMemoryTest1.class,
		ProgramMemoryTest2.class, ProgramMemoryTest3.class, StackTest.class, CalculatorTest.class })
public class AllTests {
}
