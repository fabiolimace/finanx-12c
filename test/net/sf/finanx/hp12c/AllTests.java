package net.sf.finanx.hp12c;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.sf.finanx.hp12c.model.FinanceMemoryTest;
import net.sf.finanx.hp12c.model.GeneralMemoryTest;
import net.sf.finanx.hp12c.model.StackTest;
import net.sf.finanx.math.Number;

@RunWith(Suite.class)
@SuiteClasses({ Number.class, FinanceMemoryTest.class, GeneralMemoryTest.class, StackTest.class })
public class AllTests {

}
