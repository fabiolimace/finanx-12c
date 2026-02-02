package net.sf.finanx.fx12c;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.sf.finanx.fx12c.controller.CalculatorTest;
import net.sf.finanx.fx12c.math.NumberTest;
import net.sf.finanx.fx12c.model.FinanceMemoryTest;
import net.sf.finanx.fx12c.model.GeneralMemoryTest;
import net.sf.finanx.fx12c.model.StackTest;

@RunWith(Suite.class)
@SuiteClasses({ NumberTest.class, FinanceMemoryTest.class, GeneralMemoryTest.class, StackTest.class, CalculatorTest.class })
public class AllTests {

}
