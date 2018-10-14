package net.sf.finanx.hp12c.controller;

import static net.sf.finanx.math.Number.*;

import javax.swing.JOptionPane;

import net.sf.finanx.hp12c.model.Display;
import net.sf.finanx.hp12c.model.FinanceMemory;
import net.sf.finanx.hp12c.model.Flags;
import net.sf.finanx.hp12c.model.GeneralMemory;
import net.sf.finanx.hp12c.model.History;
import net.sf.finanx.hp12c.model.ProgramMemory;
import net.sf.finanx.hp12c.model.Stack;
import net.sf.finanx.hp12c.model.Step;
import net.sf.finanx.math.Number;
import net.sf.finanx.utils.Timer;

/*
 * TODO: Erros for:
 * [STO] [+] (0 to 4) | > MAXVALUE | THROW SRO_RROR
 * [STO] [-] (0 to 4) | > MAXVALUE | THROW SRO_RROR
 * [STO] [*] (0 to 4) | > MAXVALUE | THROW SRO_RROR
 * [STO] [/] (0 to 4) | > MAXVALUE | THROW SRO_RROR
 * [STO] [/] (0 to 4) | x < 0      | THROW MATHE_RROR
 * [n]                | The values in i, PV, and FV are such that no solution exists for n. | THROW CI_RROR
 * [i]                | Cash flows all have same sign. | THROW CI_RROR
 * [AMORT]            | x <= 0 THROW | CI_RROR 
 *  [SL], [SOYD], [DB] | n <= 0  | CI_RROR 
 *  [SL], [SOYD], [DB] | n > 10e10  | CI_RROR 
 *  [SL], [SOYD], [DB] | x <= 0  | CI_RROR 
 *  [SL], [SOYD], [DB] | x is not integer  | CI_RROR 
 */
public class Calculator {

	private Key k;
	private Step stp;
	private Number tmp[];

	private Controller controller;

	private Flags flg;
	private Display dsp;
	private Worker worker;

	private Stack stk;
	private FinanceMemory fin;
	private GeneralMemory mem;
	private ProgramMemory prg;
	private History hst;

	private Configuration cfg;

	private class Worker extends Thread {

		private Calculator calculator;

		public Worker(Calculator calculator) {
			super();
			this.calculator = calculator;
		}

		public void run() {
			while (calculator.getFlags().getRun() == 1) {
				calculator.executeSingleStep();
				try {
					sleep(100);
				} catch (InterruptedException e) {
					calculator.getFlags().setRun(0);
				}
			}
		}
	}

	
	public Calculator() {
		this.init();
	}

	protected void init() {

		// Not persistent data
		this.flg = new Flags();
		this.stp = new Step();
		this.hst = new History();
		this.dsp = new Display();

		// Persistent data
		this.stk = new Stack();
		this.fin = new FinanceMemory();
		this.mem = new GeneralMemory();
		this.prg = new ProgramMemory();

		// Persistent configurations
		this.cfg = new Configuration();

		// Program runner. An independent thread
		// that executes steps sequentially.
		this.worker = new Worker(this);
	}
	
	public Controller getController() {
		return this.controller;
	}

	public void setController(Controller presenter) {
		this.controller = presenter;
	}

	public Configuration getConfigs() {
		return this.cfg;
	}

	public void setConfigs(Configuration cfg) {
		this.cfg = cfg;

		this.setFlags();
		this.setDisplay();
	}

	public void setFlags() {

		this.getFlags().setBegin(cfg.getBeg());

		this.getFlags().setDmy(cfg.getDmy());
	}

	public void setDisplay() {

		if (cfg.getCom() == 1)
			this.getDisplay().setComma(true);
		else
			this.getDisplay().setComma(false);

		this.getDisplay().setPrecision(cfg.getFix());
	}

	public Stack getStack() {
		return this.stk;
	}

	public FinanceMemory getFinanceMemory() {
		return this.fin;
	}

	public GeneralMemory getGeneralMemory() {
		return this.mem;
	}

	public ProgramMemory getProgramMemory() {
		return this.prg;
	}

	public History getOperationHistory() {
		return this.hst;
	}

	public Flags getFlags() {
		return this.flg;
	}

	public Display getDisplay() {
		return this.dsp;
	}

	public void setStack(Stack stk) {
		this.stk = stk;
	}

	public void setFinanceMemory(FinanceMemory fin) {
		this.fin = fin;
	}

	public void setGeneralMemory(GeneralMemory mem) {
		this.mem = mem;
	}

	public void setProgramMemory(ProgramMemory prg) {
		this.prg = prg;
	}

	public void setOperationHistory(History hst) {
		this.hst = hst;
	}

	public void setFlags(Flags flg) {
		this.flg = flg;
	}

	public void setDisplay(Display dsp) {
		this.dsp = dsp;
	}

	// Sets the stacks x value.
	public void setX(Number x) {
		// If the current status is 'STATUS_INPUT' or 'STATUS_OUTPUT',
		// the stack is lifted up with the new value.
		if (dsp.getStatus() != Display.STATUS_READY)
			stk.put(x);
		else
			stk.set(0, x);
	}

	public Number getX() {
		return this.stk.top();
	}

	public void shiftUpIfOutputStatus() {
		if ((dsp.getStatus() == Display.STATUS_OUTPUT)
				|| (dsp.getStatus() == Display.STATUS_OUTPUT2)) {
			stk.shiftDown();
		}
	}
	
	private void updateOneWayBindedFlags() {
		stk.setDmy(flg.getDmy() == 1 ? true: false);
		fin.setBegin(flg.getBegin() == 1 ? true: false);
		fin.setC(flg.getC() == 1 ? true: false);
	}

	public void keyPressed(Key key) {
		
		updateOneWayBindedFlags();
		
		if (key == null)
			return;

		// Converting an KeyEnum interface to a KeyEnum Object
		Key k = key;

		// If the calculator is running a program and
		// a key is pressed, program execution stops.
		if (flg.getRun() == 1) {
			this.stopProgram();
			dsp.setLock(true);
		}

		switch (k) {
		case KEY_0: {
			break;
		}
		case KEY_1: {
			break;
		}
		case KEY_2: {
			break;
		}
		case KEY_3: {
			break;
		}
		case KEY_4: {
			break;
		}
		case KEY_5: {
			break;
		}
		case KEY_6: {
			break;
		}
		case KEY_7: {
			break;
		}
		case KEY_8: {
			break;
		}
		case KEY_9: {
			if (flg.getG() == 1) {
				String tmp = "";

				tmp += "P" + Display.zeroPad(prg.getUsedSteps(), 3);
				tmp += " ";
				tmp += "r"
						+ Display.zeroPad(mem.getAvailableRegisters(), 3);

				dsp.setMessage(tmp);
				dsp.setPause(true);
				controller.getWindow().updateDisplay();
			}
			break;
		}
		case KEY_DIV: {
			break;
		}
		case KEY_MUL: {
			break;
		}
		case KEY_SUB: {
			break;
		}
		case KEY_SUM: {
			break;
		}
		case KEY_N: {
			break;
		}
		case KEY_I: {
			break;
		}
		case KEY_PV: {
			break;
		}
		case KEY_PMT: {
			break;
		}
		case KEY_FV: {
			break;
		}
		case KEY_CHS: {
			break;
		}
		case KEY_POW: {
			break;
		}
		case KEY_RECIPROCAL: {
			break;
		}
		case KEY_PERC_TOT: {
			break;
		}
		case KEY_PERC_DELTA: {
			break;
		}
		case KEY_PERC: {
			break;
		}
		case KEY_EEX: {
			break;
		}
		case KEY_RS: {
			break;
		}
		case KEY_SST: {
			if (dsp.getMode() == Display.MODE_NORMAL) {

				if (flg.getG() == 1) {
					if (prg.getCurrentIndex() == 0)
						prg.setCurrentIndex(prg.getSize() - 1);
				} else {
					if (prg.getCurrentIndex() == prg.getSize() - 1)
						prg.setCurrentIndex(0);
					else if (prg.getCurrentIndex() == 0)
						prg.next();
				}

				dsp.inputProgramStep(prg.getCurrentIndex(),
						(Step) prg.get());
				dsp.setMode(Display.MODE_PROGRAM);
				controller.getWindow().updateDisplay();
				dsp.setMode(Display.MODE_NORMAL);
				dsp.setPause(true);
			}
			break;
		}
		case KEY_ROLL: {
			break;
		}
		case KEY_XY: {
			break;
		}
		case KEY_CLX: {
			break;
		}
		case KEY_ENTER: {
			if (flg.getF() == 1) {
				dsp.setMessage(dsp.getMantissa());
				dsp.setPause(true);
				controller.getWindow().updateDisplay();
			}
			break;
		}
		case KEY_ON: {
			break;
		}
		case KEY_F: {
			break;
		}
		case KEY_G: {
			break;
		}
		case KEY_STO: {
			break;
		}
		case KEY_RCL: {
			break;
		}
		case KEY_DOT: {
			break;
		}
		case KEY_TOT: {
			break;
		}
		default:
			break;
		}
	}

	public void keyReleased(Key key) {

		if (key == null)
			return;

		// Converting an KeyEnum interface to a KeyEnum implementation
		k = key;

		try {

			if (dsp.getPause()) {

				Timer t = new Timer(1);
				t.run();

				dsp.setPause(false);
			} else if (dsp.getLock()) {
				dsp.setLock(false);
				return;
			}

			// If the calculator is in program mode,
			// sends the key command to the appropriate method
			if (flg.getPrgm() == 1) {
				programInput(k);
				return;
			}

			switch (k) {
			case KEY_0: {
				this.doKey00();
				break;
			}
			case KEY_1: {
				this.doKey01();
				break;
			}
			case KEY_2: {
				this.doKey02();
				break;
			}
			case KEY_3: {
				this.doKey03();
				break;
			}
			case KEY_4: {
				this.doKey04();
				break;
			}
			case KEY_5: {
				this.doKey05();
				break;
			}
			case KEY_6: {
				this.doKey06();
				break;
			}
			case KEY_7: {
				this.doKey07();
				break;
			}
			case KEY_8: {
				this.doKey08();
				break;
			}
			case KEY_9: {
				this.doKey09();
				break;
			}
			case KEY_DIV: {
				this.doKey10();
				break;
			}
			case KEY_MUL: {
				this.doKey20();
				break;
			}
			case KEY_SUB: {
				this.doKey30();
				break;
			}
			case KEY_SUM: {
				this.doKey40();
				break;
			}
			case KEY_N: {
				this.doKey11();
				break;
			}
			case KEY_I: {
				this.doKey12();
				break;
			}
			case KEY_PV: {
				this.doKey13();
				break;
			}
			case KEY_PMT: {
				this.doKey14();
				break;
			}
			case KEY_FV: {
				this.doKey15();
				break;
			}
			case KEY_CHS: {
				// TODO: When [DATE] is executed as an instruction in a running program, the calculator pauses for about 1 second to display the result, then resumes program execution.
				this.doKey16();
				break;
			}
			case KEY_POW: {
				this.doKey21();
				break;
			}
			case KEY_RECIPROCAL: {
				this.doKey22();
				break;
			}
			case KEY_PERC_TOT: {
				this.doKey23();
				break;
			}
			case KEY_PERC_DELTA: {
				this.doKey24();
				break;
			}
			case KEY_PERC: {
				this.doKey25();
				break;
			}
			case KEY_EEX: {
				this.doKey26();
				break;
			}
			case KEY_RS: {
				this.doKey31();
				break;
			}
			case KEY_SST: {
				this.doKey32();
				break;
			}
			case KEY_ROLL: {
				this.doKey33();
				break;
			}
			case KEY_XY: {
				this.doKey34();
				break;
			}
			case KEY_CLX: {
				this.doKey35();
				break;
			}
			case KEY_ENTER: {
				this.doKey36();
				break;
			}
			case KEY_ON: {

				int r;
				String msg = controller.getWindow().getLanguageStringList()
						.getValue("DIALOG_QUIT");

				r = JOptionPane.showConfirmDialog(null, msg);

				if (r == JOptionPane.YES_OPTION) {
					controller.quit();
				} else if (r == JOptionPane.NO_OPTION) {
					this.doKey41();
				}

				break;
			}
			case KEY_F: {
				this.doKey42();
				break;
			}
			case KEY_G: {
				this.doKey43();
				break;
			}
			case KEY_STO: {
				this.doKey44();
				break;
			}
			case KEY_RCL: {
				this.doKey45();
				break;
			}
			case KEY_DOT: {
				this.doKey48();
				break;
			}
			case KEY_TOT: {
				this.doKey49();
				break;
			}
			default: {
				break;
			}
			} // End Switch

			this.updateDisplay();
			this.printRegisters();

		} // End Try
		catch (CalculatorException e) {
			this.clearFgsr();
			showError(e);
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Updates the display and the X values so that
	// both have the same value.
	public void updateDisplay() {
		try {
			if ((dsp.getStatus() != Display.STATUS_INPUT)) {
				dsp.setValue(this.getX());
			} else {
				stk.set(0, dsp.getValue());
			}
		} catch (CalculatorException e) {
			showError(e);
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printRegisters() {
		if ((dsp.getStatus() != Display.STATUS_INPUT) 
				&& flg.getF() == 0 
				&& flg.getG() == 0 
				&& flg.getSto() == 0
				&& flg.getRcl() == 0) {
			System.out.println("--------------------\n");
			System.out.println(stk);
			System.out.println(fin);
		}
	}
	
	/*
	 * Processes STO operations. It Receives integers to form the memory index.
	 * It uses the memory index to store the X value into a memory register.
	 * Note 1: If the calculator has only 20 registers the calculator will work
	 * like the HP12C Gold, on witch a point represents 10. Note 2: It stores
	 * values from 000 to 999.
	 */
	protected void stoInput(int i) throws CalculatorException {

		if (flg.getSto() == 1) {
			tmp = new Number[3];

			if (mem.getSize() <= 20) {
				if (i == -1) {
					tmp[0] = ZERO;
					tmp[1] = ONE;
					flg.setSto(3);
				} else {
					tmp[0] = ZERO;
					tmp[1] = ZERO;
					tmp[2] = n(i);
					flg.setSto(4);
				}
			} else if (mem.getSize() <= 100) {
				if (i == -1)
					return;
				tmp[0] = ZERO;
				tmp[1] = n(i);
				flg.setSto(3);
			} else {
				if (i == -1)
					return;
				tmp[0] = n(i);
				flg.setSto(2);
			}
		} else if (flg.getSto() == 2) {
			if (i == -1)
				return;
			tmp[1] = n(i);
			flg.setSto(3);
		} else if (flg.getSto() == 3) {
			if (i == -1)
				return;
			tmp[2] = n(i);
			flg.setSto(4);
		}

		if (flg.getSto() == 4) {
			flg.setSto(0);
			stp.setComplement((int) (tmp[0].intValue() * 100 + tmp[1].intValue() * 10 + tmp[2].intValue()));
		}

		if (flg.getSto() == 0) {
			if (dsp.getMode() == Display.MODE_NORMAL) {
				if (stp.getModifier() == Key.KEY_STO.getCode()) {
					if (stp.getKey() == Key.KEY_SUM.getCode()) {
						mem.set(stp.getComplement(), stk.top().add(mem.get(stp.getComplement())));
					} else if (stp.getKey() == Key.KEY_SUB.getCode()) {
						mem.set(stp.getComplement(), stk.top().subtract(mem.get(stp.getComplement())));
					} else if (stp.getKey() == Key.KEY_MUL.getCode()) {
						mem.set(stp.getComplement(), stk.top().multiply(mem.get(stp.getComplement())));
					} else if (stp.getKey() == Key.KEY_DIV.getCode()) {
						mem.set(stp.getComplement(), stk.top().divide(mem.get(stp.getComplement())));
					}
				} else {
					mem.set((int) (i(tmp[0]) * 100 + i(tmp[1]) * 10 + i(tmp[2])), stk.top());
				}
				dsp.setStatus(Display.STATUS_OUTPUT);
			}
		}
	}

	/*
	 * Processes RCL operations. It Receives integers to form the memory index.
	 * It uses the memory index to store the X value into a memory register.
	 * Note 1: If the calculateor has only 20 registers the calculator will work
	 * like the HP12C Gold, on witch a point represents 10. Note 2: It recall
	 * from 000 to 999.
	 */
	protected void rclInput(int i) {

		if (flg.getRcl() == 1) {
			tmp = new Number[3];

			if (mem.getSize() <= 20) {
				if (i == -1) {
					tmp[0] = ZERO;
					tmp[1] = ONE;
					flg.setRcl(3);
				} else {
					tmp[0] = ZERO;
					tmp[1] = ZERO;
					tmp[2] = n(i);
					flg.setRcl(4);
				}
			} else if (mem.getSize() <= 100) {
				if (i == -1)
					return;
				tmp[0] = ZERO;
				tmp[1] = n(i);
				flg.setRcl(3);
			} else {
				if (i == -1)
					return;
				tmp[0] = n(i);
				flg.setRcl(2);
			}
		} else if (flg.getRcl() == 2) {
			if (i == -1)
				return;
			tmp[1] = n(i);
			flg.setRcl(3);
		} else if (flg.getRcl() == 3) {
			if (i == -1)
				return;
			tmp[2] = n(i);
			flg.setRcl(4);
		}

		if (flg.getRcl() == 4) {
			flg.setRcl(0);
			stp.setComplement((int) (i(tmp[0]) * 100 + i(tmp[1]) * 10 + i(tmp[2])));
		}

		if (flg.getRcl() == 0) {
			if (dsp.getMode() == Display.MODE_NORMAL) {
				setX(mem.get((int) (i(tmp[0]) * 100 + i(tmp[1]) * 10 + i(tmp[2]))));
				dsp.setStatus(Display.STATUS_OUTPUT);
			}
		}
	}

	/*
	 * Processes GTO operations.
	 */
	protected void gtoInput(int i) {

		if (flg.getGto() == 1) {
			tmp = new Number[3];

			if (prg.getSize() <= 20) {
				if (i == -1) {
					tmp[0] = ZERO;
					tmp[1] = ONE;
					flg.setGto(3);
				} else {
					tmp[0] = ZERO;
					tmp[1] = ZERO;
					tmp[2] = n(i);
					flg.setGto(4);
				}
			} else if (prg.getSize() <= 100) {
				if (i == -1)
					return;
				tmp[0] = ZERO;
				tmp[1] = n(i);
				flg.setGto(3);
			} else {
				if (i == -1)
					return;
				tmp[0] = n(i);
				flg.setGto(2);
			}
		} else if (flg.getGto() == 2) {
			if (i == -1)
				return;
			tmp[1] = n(i);
			flg.setGto(3);
		} else if (flg.getGto() == 3) {
			if (i == -1)
				return;
			tmp[2] = n(i);
			flg.setGto(4);
		}

		if (flg.getGto() == 4) {
			if (flg.getPrgm() == 1) {
				flg.setGto(0);
				stp.setStep(Step.STP_G_ROLL);
				stp.setComplement((int) (tmp[0].i() * 100 + tmp[1].i() * 10 + tmp[2].i()));
				dsp.setStatus(Display.STATUS_OUTPUT);
			} else {
				flg.setGto(0);
				prg.setCurrentIndex((int) (tmp[0].i() * 100 + tmp[1].i() * 10 + tmp[2].i()));
				stp.setStep(Step.STP_G_ROLL);
				stp.setComplement((int) (tmp[0].i() * 100 + tmp[1].i() * 10 + tmp[2].i()));
				dsp.setStatus(Display.STATUS_OUTPUT);
			}
		}
	}

//	private boolean checkOddPeriod() {
//		try {
//			if(!fin.getN().fractionalPart().isZero())
//				return true;
//			else
//				return false;
//		} catch (CalculatorException e) {
//			showError(e);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

//	protected void executeOddPeriodTasks() {
//		try {
//			// With odd period using simple interest
//			if (flg.getC() == 0) {
//				fin.setPv(fin.simpleFutureValue());
//			}
//			// With odd period using compound interest
//			else {
//				fin.setPv(fin.futureValue());
//			}
//
//			// Fixing PV sign
//			fin.setPv(fin.getPv().negate());
//
//			// Truncating the odd period
//			fin.setN(fin.getN().integralPart());
//
//		} // End Try
//		catch (CalculatorException e) {
//			showError(e);
//			// e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
////		}
//	}

	public void showError(CalculatorException e) {
		// show erros on display, except magnitude errors
		if (e.getError() != Error.ERROR_MAG) {
			showDisplayMessage(" Error " + e.getCode());
		}
		e.print();
	}
	
	public void showDisplayMessage(String msg) {
		dsp.setMessage(msg);
		dsp.setLock(true);
		controller.getWindow().updateDisplay();
	}

	public void programInput(Key key) {

		if (key == null)
			return;

		if (dsp.getLock()) {
			dsp.setLock(false);
			return;
		}

		// Converting a KeyEnum interface to a KeyEnum implementation
		k = key;

		try {
			switch (k) {
			case KEY_F: {
				this.doKey42();
				if (flg.getF() == 1) {
					stp.setStep(Step.STP_F);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					stp.setUndefined();
					dsp.setStatus(Display.STATUS_READY);
				}
				break;
			}
			case KEY_G: {
				this.doKey43();
				if (flg.getG() == 1) {
					stp.setStep(Step.STP_G);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					stp.setUndefined();
					dsp.setStatus(Display.STATUS_READY);
				}
				break;
			}
			case KEY_STO: {
				this.doKey44();
				if (flg.getSto() > 0) {
					stp.setStep(Step.STP_STO);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					stp.setUndefined();
					dsp.setStatus(Display.STATUS_READY);
				}
				break;
			}
			case KEY_RCL: {
				this.doKey45();
				if (flg.getRcl() > 0) {
					stp.setStep(Step.STP_RCL);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					stp.setUndefined();
					dsp.setStatus(Display.STATUS_READY);
				}
				break;
			}
			case KEY_ROLL: {
				this.doKey33();

				if (flg.getGto() > 0) {
					stp.setStep(Step.STP_G_ROLL);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					dsp.setStatus(Display.STATUS_READY);
				}

				break;
			}
			case KEY_SST: {
				if (flg.getF() == 1) {
					stp = new Step(Step.STP_F_SST);
					putStep(stp);
					flg.toggleF();
					dsp.setStatus(Display.STATUS_READY);
				} else if (flg.getG() == 1) {
					if (!prg.back())
						prg.setCurrentIndex(prg.getSize() - 1);
					dsp.setStatus(Display.STATUS_READY);
					flg.toggleG();
				} else {
					if (!prg.next())
						prg.setCurrentIndex(0);
					dsp.setStatus(Display.STATUS_READY);
				}

				break;
			}
			default: {
				if (flg.getF() == 1) {
					if (k.getCode() == Key.KEY_ENTER.getCode()) {
						doKey42();
						stp.setUndefined();
						dsp.setStatus(Display.STATUS_READY);
					} else if (k.getCode() == Key.KEY_RS.getCode()) {
						this.doKey31();
					} else {
						stp.setModifier(Key.KEY_F.getCode());
						stp.setKey(k.getCode());
						putStep(stp);
						flg.toggleF();
						dsp.setStatus(Display.STATUS_READY);
					}
				} else if (flg.getG() == 1) {
					if (k.getCode() == Key.KEY_9.getCode()) {
						doKey43();
						stp.setUndefined();
						dsp.setStatus(Display.STATUS_READY);
					} else {
						stp.setModifier(Key.KEY_G.getCode());
						stp.setKey(k.getCode());
						putStep(stp);
						flg.toggleG();
						dsp.setStatus(Display.STATUS_READY);
					}
				} else if (flg.getSto() > 0) {
					if (k.getCode() <= Key.KEY_9.getCode()) {
						this.stoInput(k.getCode());
					} else if (k.getCode() == Key.KEY_DOT.getCode()) {
						this.stoInput(Key.KEY_NULL.getCode());
					} else if (k.getCode() == Key.KEY_N.getCode()) {
						stp.setModifier(Key.KEY_STO.getCode());
						stp.setKey(Key.KEY_N.getCode());
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_I.getCode()) {
						stp.setModifier(Key.KEY_STO.getCode());
						stp.setKey(Key.KEY_I.getCode());
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_PV.getCode()) {
						stp.setModifier(Key.KEY_STO.getCode());
						stp.setKey(Key.KEY_PV.getCode());
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_PMT.getCode()) {
						stp.setModifier(Key.KEY_STO.getCode());
						stp.setKey(Key.KEY_PMT.getCode());
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_FV.getCode()) {
						stp.setModifier(Key.KEY_STO.getCode());
						stp.setKey(Key.KEY_FV.getCode());
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_SUM.getCode()) {
						stp.setModifier(Key.KEY_STO.getCode());
						stp.setKey(Key.KEY_SUM.getCode());
					} else if (k.getCode() == Key.KEY_SUB.getCode()) {
						stp.setModifier(Key.KEY_STO.getCode());
						stp.setKey(Key.KEY_SUB.getCode());
					} else if (k.getCode() == Key.KEY_MUL.getCode()) {
						stp.setModifier(Key.KEY_STO.getCode());
						stp.setKey(Key.KEY_MUL.getCode());
					} else if (k.getCode() == Key.KEY_DIV.getCode()) {
						stp.setModifier(Key.KEY_STO.getCode());
						stp.setKey(Key.KEY_DIV.getCode());
					}

					if (flg.getSto() == 0) {
						putStep(stp);
						dsp.setStatus(Display.STATUS_READY);
					}
				} else if (flg.getRcl() > 0) {
					if (k.getCode() <= Key.KEY_9.getCode()) {
						this.rclInput(k.getCode());
					} else if (k.getCode() == Key.KEY_DOT.getCode()) {
						this.rclInput(Key.KEY_NULL.getCode());
					} else if (k.getCode() == Key.KEY_N.getCode()) {
						stp.setModifier(Key.KEY_RCL.getCode());
						stp.setKey(Key.KEY_N.getCode());
						flg.setRcl(0);
					} else if (k.getCode() == Key.KEY_I.getCode()) {
						stp.setModifier(Key.KEY_RCL.getCode());
						stp.setKey(Key.KEY_I.getCode());
						flg.setRcl(0);
					} else if (k.getCode() == Key.KEY_PV.getCode()) {
						stp.setModifier(Key.KEY_RCL.getCode());
						stp.setKey(Key.KEY_PV.getCode());
						flg.setRcl(0);
					} else if (k.getCode() == Key.KEY_PMT.getCode()) {
						stp.setModifier(Key.KEY_RCL.getCode());
						stp.setKey(Key.KEY_PMT.getCode());
						flg.setRcl(0);
					} else if (k.getCode() == Key.KEY_FV.getCode()) {
						stp.setModifier(Key.KEY_RCL.getCode());
						stp.setKey(Key.KEY_FV.getCode());
						flg.setRcl(0);
					}

					if (flg.getRcl() == 0) {
						putStep(stp);
						dsp.setStatus(Display.STATUS_READY);
					}
				} else if (flg.getGto() > 0) {
					if (k.getCode() <= Key.KEY_9.getCode())
						this.gtoInput(k.getCode());
					else if (k.getCode() == Key.KEY_DOT.getCode())
						flg.toggleWild();

					if (flg.getGto() == 0) {
						if (flg.getWild() == 1) {
							prg.setCurrentIndex(stp.getComplement());
							flg.toggleWild();
							dsp.setStatus(Display.STATUS_READY);
						} else {
							putStep(stp);
							dsp.setStatus(Display.STATUS_READY);
						}
					}
				} else {
					stp = new Step();
					stp.setKey(k.getCode());
					putStep(stp);
					dsp.setStatus(Display.STATUS_READY);
				}
			}
			}

			if (dsp.getStatus() == Display.STATUS_READY) {
				stp.setStep((Step) prg.get());
				dsp.inputProgramStep(prg.getCurrentIndex(), stp);
			}

		} catch (Exception e) {
			// TODO
		}

	}

	public void executeStep(Step stp) {

		// if the current instruction has a null key,
		// goes to line zero and stop executing
		// It acts the same way as [g][gto]000.
		// So it is not necessary to store that instruction
		// in each single line when [f][prg] is keyed.
		if (stp.getKey() == Key.KEY_NULL.getCode()) {
			this.stopProgram();
		}
		// goes to line number ZERO and stops execution.
		else if (stp.getModifier() == Key.KEY_G.getCode()
				&& stp.getKey() == Key.KEY_ROLL.getCode()
				&& stp.getComplement() == Key.KEY_0.getCode()) {

			this.stopProgram();
		}
		// goes to line number specified by the instruction [g][gto]xxx.
		else if (stp.getModifier() == Key.KEY_G.getCode()
				&& stp.getKey() == Key.KEY_ROLL.getCode()) {

			prg.setCurrentIndex(stp.getComplement());
		} else if (stp.getModifier() == Key.KEY_G.getCode()
				&& stp.getKey() == Key.KEY_XY.getCode()) {

			if (stk.get(0).lessThanOrEqualTo(stk.get(1))) {
				this.prg.next();
			} else {
				this.prg.next();
				this.prg.next();
			}
		} else if (stp.getModifier() == Key.KEY_G.getCode()
				&& stp.getKey() == Key.KEY_CLX.getCode()) {

			if (stk.get(0).isZero()) {
				this.prg.next();
			} else {
				this.prg.next();
				this.prg.next();
			}
		} else if (stp.getKey() == Key.KEY_STO.getCode()) {
			mem.set(stp.getComplement(), stk.top());
			dsp.setStatus(Display.STATUS_OUTPUT);
			this.prg.next();
		} else if (stp.getKey() == Key.KEY_RCL.getCode()) {
			this.setX(mem.get(stp.getComplement()));
			dsp.setStatus(Display.STATUS_OUTPUT);
			this.prg.next();
		} else {
			if (stp.getModifier() > (-1)) {
				keyReleased(Key.getKey(stp.getModifier()));
			}
			if (stp.getKey() > (-1)) {
				keyReleased(Key.getKey(stp.getKey()));
			}
			if (stp.getComplement() > (-1)) {
				keyReleased(Key.getKey(stp.getComplement()));
			}
			this.prg.next();
		}

	}

	public void executeSingleStep() {

		// Erases old content in display
		// and shows the message "running"
		showDisplayMessage("");
		showDisplayMessage("running");
		getDisplay().setLock(false);

		if (prg.getCurrentIndex() == 0)
			this.prg.next();

		this.executeStep((Step) this.prg.get());

		if (prg.getCurrentIndex() == prg.getSize() - 1) {
			this.stopProgram();
		}
	}

	public void executeProgram() {

		this.worker = null;
		this.worker = new Worker(this);
		this.worker.start();

	}

	public void stopProgram() {
		prg.setCurrentIndex(0);
		flg.setRun(0);
		dsp.setLock(false);
		controller.getWindow().updateDisplay();
	}

	public Step getStep() {
		return this.stp;
	}

	public Step getCurrentProgramStep() {
		return (Step) this.prg.get();
	}

	public void setStep(Step stp) {
		this.stp = new Step(stp);
	}

	public void setCurrentProgramStep(Step stp) {
		this.prg.set(new Step(stp));
	}

	public void putStep(Step stp) {
		System.out.println("putStep: " + stp);
		this.prg.put(new Step(stp));
	}

	private void clearFgsr() {
		if (flg.getF() == 1) {
			flg.toggleF();
		}
		if (flg.getG() == 1) {
			flg.toggleG();
		}
		if (flg.getSto() > 0) {
			flg.toggleSto();
		}
		if (flg.getRcl() > 0) {
			flg.toggleRcl();
		}
	}

	protected void doKey00() throws CalculatorException {
		if (flg.getF() == 1) {
			dsp.setPrecision(0);

			flg.toggleF();
			stp.setStep(Step.STP_F_0);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {

			// Temp variable
			tmp = new Number[2];

			// Calculates mean values of X and Y
			tmp = mem.mean();

			// Inserts y and x means
			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setStep(Step.STP_G_0);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getSto() > 0) {
			this.stoInput(0);
		} else if (flg.getRcl() > 0) {
			this.rclInput(0);
		} else if (flg.getGto() > 0) {
			this.gtoInput(0);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('0');
			stp.setStep(Step.STP_0);
		}
	}

	protected void doKey01() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(1);
			this.flg.toggleF();
			stp.setStep(Step.STP_F_1);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {

			// Temporary variables
			tmp = new Number[2];

			tmp = mem.xLinearEstimation(stk.top());

			stk.setLastTop();

			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setStep(Step.STP_G_1);
			dsp.setStatus(Display.STATUS_READY);

		} else if (flg.getSto() > 0) {
			this.stoInput(1);
		} else if (flg.getRcl() > 0) {
			this.rclInput(1);
		} else if (flg.getGto() > 0) {
			this.gtoInput(1);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('1');
			stp.setStep(Step.STP_1);
		}
	}

	protected void doKey02() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(2);
			this.flg.toggleF();
			stp.setStep(Step.STP_F_2);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			// Temporary variables
			tmp = new Number[2];

			tmp = mem.yLinearEstimation(stk.top());

			stk.setLastTop();

			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setStep(Step.STP_G_2);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getSto() > 0) {
			this.stoInput(2);
		} else if (flg.getRcl() > 0) {
			this.rclInput(2);
		} else if (flg.getGto() > 0) {
			this.gtoInput(2);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('2');
			stp.setStep(Step.STP_2);
		}
	}

	protected void doKey03() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(3);
			this.flg.toggleF();
			stp.setStep(Step.STP_F_3);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			stk.factorial();
			flg.toggleG();
			stp.setStep(Step.STP_F_3);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			this.stoInput(3);
		} else if (flg.getRcl() > 0) {
			this.rclInput(3);
		} else if (flg.getGto() > 0) {
			this.gtoInput(3);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('3');
			stp.setStep(Step.STP_3);
		}
	}

	protected void doKey04() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(4);
			this.flg.toggleF();
			stp.setStep(Step.STP_F_4);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setDmy(1);
			this.flg.toggleG();
			stp.setStep(Step.STP_G_4);
		} else if (flg.getSto() > 0) {
			this.stoInput(4);
		} else if (flg.getRcl() > 0) {
			this.rclInput(4);
		} else if (flg.getGto() > 0) {
			this.gtoInput(4);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('4');
			stp.setStep(Step.STP_4);
		}
	}

	protected void doKey05() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(5);
			this.flg.toggleF();
			stp.setStep(Step.STP_F_5);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setDmy(0);
			this.flg.toggleG();
			stp.setStep(Step.STP_G_5);
		} else if (flg.getSto() > 0) {
			this.stoInput(5);
		} else if (flg.getRcl() > 0) {
			this.rclInput(5);
		} else if (flg.getGto() > 0) {
			this.gtoInput(5);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('5');
			stp.setStep(Step.STP_5);
		}
	}

	protected void doKey06() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(6);
			this.flg.toggleF();
			stp.setStep(Step.STP_F_6);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			stk.put(mem.weightedMean());

			flg.toggleG();
			stp.setStep(Step.STP_G_6);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getSto() > 0) {
			this.stoInput(6);
		} else if (flg.getRcl() > 0) {
			this.rclInput(6);
		} else if (flg.getGto() > 0) {
			this.gtoInput(6);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('6');
			stp.setStep(Step.STP_6);
		}
	}

	protected void doKey07() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(7);
			this.flg.toggleF();
			stp.setStep(Step.STP_F_7);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setBegin(1);
			this.flg.toggleG();
			stp.setStep(Step.STP_G_7);
		} else if (flg.getSto() > 0) {
			this.stoInput(7);
		} else if (flg.getRcl() > 0) {
			this.rclInput(7);
		} else if (flg.getGto() > 0) {
			this.gtoInput(7);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('7');

			stp.setStep(Step.STP_7);
		}
	}

	protected void doKey08() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(8);
			this.flg.toggleF();
			stp.setStep(Step.STP_F_8);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setBegin(0);
			this.flg.toggleG();
			stp.setStep(Step.STP_G_8);
		} else if (flg.getSto() > 0) {
			this.stoInput(8);
		} else if (flg.getRcl() > 0) {
			this.rclInput(8);
		} else if (flg.getGto() > 0) {
			this.gtoInput(8);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('8');

			stp.setStep(Step.STP_8);
		}
	}

	protected void doKey09() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(9);
			this.flg.toggleF();
			stp.setStep(Step.STP_F_9);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			flg.toggleG();
			stp.setStep(Step.STP_G_9);
		} else if (flg.getSto() > 0) {
			this.stoInput(9);
		} else if (flg.getRcl() > 0) {
			this.rclInput(9);
		} else if (flg.getGto() > 0) {
			this.gtoInput(9);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('9');
			stp.setStep(Step.STP_9);
		}
	}

	protected void doKey10() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setStep(Step.STP_STO_DIV);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.divide();
			stp.setStep(Step.STP_DIV);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey11() throws CalculatorException {
		if (flg.getF() == 1) {
			tmp = fin.amortization(stk.top(), dsp.getPrecision());

			// For details, see HP12Camortization(...)
			stk.put(tmp[0]);
			stk.put(tmp[1]);
			stk.put(tmp[2]);
			fin.setPv(tmp[3]);
			fin.setN(tmp[4]);

			flg.toggleF();
			stp.setStep(Step.STP_F_N);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.put(stk.pop().multiply(TWELVE));
			fin.setN(stk.top());
			stp.setStep(Step.STP_G_N);
			dsp.setStatus(Display.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			fin.setN(stk.top());
			flg.setSto(0);
			stp.setStep(Step.STP_STO_N);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getN());
			flg.setRcl(0);
			stp.setStep(Step.STP_RCL_N);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY)
					|| (dsp.getStatus() == Display.STATUS_OUTPUT2)) {

				stk.set(0, fin.period());
				
				fin.setN(stk.top());
				dsp.setStatus(Display.STATUS_OUTPUT2);

			} else {
				fin.setN(stk.top());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setStep(Step.STP_N);
		}
	}

	protected void doKey12() throws CalculatorException {
		if (flg.getF() == 1) {

			tmp = fin.simpleInterest();

			stk.set(0, tmp[0]);
			stk.set(1, (fin.getPv().negate()));
			stk.set(2, tmp[1]);

			flg.toggleF();
			stp.setStep(Step.STP_F_I);
			dsp.setStatus(Display.STATUS_OUTPUT);

		} else if (flg.getG() == 1) {
			stk.put(stk.pop().divide(TWELVE));
			fin.setI(stk.top());
			stp.setStep(Step.STP_G_I);
			dsp.setStatus(Display.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			fin.setI(stk.top());
			flg.setSto(0);
			stp.setStep(Step.STP_STO_I);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getI());
			flg.setRcl(0);
			stp.setStep(Step.STP_RCL_I);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY)
					|| (dsp.getStatus() == Display.STATUS_OUTPUT2)) {


				stk.set(0, fin.rate());

				// Here odd period check have to be done AFTER the calculus
//				if (checkOddPeriod())
//					executeOddPeriodTasks();

				fin.setI(stk.top());
				dsp.setStatus(Display.STATUS_OUTPUT2);
			} else {
				fin.setI(stk.top());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setStep(Step.STP_I);
		}
	}

	protected void doKey13() throws CalculatorException {
		if (flg.getF() == 1) {

			stk.set(0, fin.npv(mem.getArray()));

			flg.toggleF();
			stp.setStep(Step.STP_F_PV);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			mem.set(0, stk.top());

			flg.toggleG();
			stp.setStep(Step.STP_G_PV);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			fin.setPv(stk.top());
			flg.setSto(0);
			stp.setStep(Step.STP_STO_PV);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getPv());
			flg.setRcl(0);
			stp.setStep(Step.STP_RCL_PV);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY)
					|| (dsp.getStatus() == Display.STATUS_OUTPUT2)) {

				// Here odd period check should not be done
				// if(checkOddPeriod()) executeOddPeriodTasks();

				stk.set(0, fin.presentValue());

				fin.setPv(stk.top());
				dsp.setStatus(Display.STATUS_OUTPUT2);
			} else {
				fin.setPv(stk.top());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setStep(Step.STP_PV);
		}
	}

	protected void doKey14() throws CalculatorException {
		if (flg.getF() == 1) {
			stk.round(dsp.getPrecision());
			flg.toggleF();
			stp.setStep(Step.STP_F_PMT);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			mem.put(stk.top(), ONE);
			fin.setN(fin.getN().add(ONE));
			flg.toggleG();
			stp.setStep(Step.STP_G_PMT);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			fin.setPmt(stk.top());
			flg.setSto(0);
			stp.setStep(Step.STP_STO_PMT);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getPmt());
			flg.setRcl(0);
			stp.setStep(Step.STP_RCL_PMT);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY)
					|| (dsp.getStatus() == Display.STATUS_OUTPUT2)) {

//				// Here odd period check should be done BEFORE the calculus
//				if (checkOddPeriod())
//					executeOddPeriodTasks();

				stk.set(0, fin.pricePayment());

				fin.setPmt(stk.top());
				dsp.setStatus(Display.STATUS_OUTPUT2);
			} else {
				fin.setPmt(stk.top());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setStep(Step.STP_PMT);
		}
	}

	protected void doKey15() throws CalculatorException {
		if (flg.getF() == 1) {

			stk.set(0, fin.irr(mem.getArray()));

			flg.toggleF();
			stp.setStep(Step.STP_F_FV);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			mem.setTimes(mem.getCurrentIndex(), stk.top());
			flg.toggleG();
			stp.setStep(Step.STP_G_FV);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			fin.setFv(stk.top());
			flg.setSto(0);
			stp.setStep(Step.STP_STO_FV);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getFv());
			flg.setRcl(0);
			stp.setStep(Step.STP_RCL_FV);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY)
					|| (dsp.getStatus() == Display.STATUS_OUTPUT2)) {

				// Here odd period check should be done BEFORE the calculus
//				if (checkOddPeriod())
//					executeOddPeriodTasks();

				stk.set(0, fin.futureValue());

				fin.setFv(stk.top());
				dsp.setStatus(Display.STATUS_OUTPUT2);
			} else {
				fin.setFv(stk.top());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setStep(Step.STP_FV);
		}
	}

	protected void doKey16() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			
			tmp = new Number[2];
			
			stk.addDaysToDate();
			
			// Calculate week day and show it.
			dsp.setValue(stk.top());
			stp.setStep(Step.STP_G_CHS);
			dsp.setStatus(Display.STATUS_READY);
			dsp.setMessage((dsp.getString() + "          ").substring(1, 11) + stk.dayOfWeek().i());

			flg.toggleG();
			dsp.setLock(true);

		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			if ((dsp.getStatus() != Display.STATUS_INPUT)) {
				stk.put((stk.pop()).negate());
			} else {
				this.dsp.inputChar('-');
			}
			stp.setStep(Step.STP_CHS);
		}
	}

	protected void doKey20() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			stk.squared();
			flg.toggleG();
			stp.setStep(Step.STP_G_MUL);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			stp.setStep(Step.STP_STO_MUL);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.multiply();
			stp.setStep(Step.STP_MUL);
			dsp.setStatus(Display.STATUS_OUTPUT);

		}
	}

	protected void doKey21() throws CalculatorException {
		if (flg.getF() == 1) {

			tmp = new Number[2];

			tmp = fin.bondPrice(null, null);

			stk.put(tmp[1]);
			stk.put(tmp[0]);

			flg.toggleF();
			stp.setStep(Step.STP_F_POW);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			stk.sqrt();
			flg.toggleG();
			stp.setStep(Step.STP_G_POW);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.pow();
			stp.setStep(Step.STP_POW);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey22() throws CalculatorException {
		if (flg.getF() == 1) {

			// Shows a message: "Not Available Sorry"
			this.showDisplayMessage(" N.A. Sorry ");
			flg.toggleF();
			stp.setStep(Step.STP_F_RECIPROCAL);
			dsp.setStatus(Display.STATUS_READY);

			// TODO: This piece of code is ready to work
			// but the function bondYield is not yet.
			// A message like "N/A Sorry" will be shown in the
			// display until I can rewrite that function again.
			/*
			 * dt = new Date[2];
			 * 
			 * HP12CbondYield(fin.getPv(), fin.getPmt(), dt[1],
			 * dt[0]); stk.put(HP12CbondYield(fin.getPv(),
			 * fin.getPmt(), dt[1], dt[0]));
			 * 
			 * flg.toggleF(); stp.setStep(Step.STP_F_RECIPROCAL);
			 * dsp.setStatus(Display.STATUS_OUTPUT);
			 */

		} else if (flg.getG() == 1) {
			stk.exp();
			flg.toggleG();
			stp.setStep(Step.STP_G_RECIPROCAL);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.reciprocal();
			stp.setStep(Step.STP_RECIPROCAL);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey23() throws CalculatorException {
		if (flg.getF() == 1) {

			tmp = new Number[2];
			tmp = fin.slDepreciation(stk.top());
			stk.put(tmp[1]);
			stk.put(tmp[0]);

			flg.toggleF();
			stp.setStep(Step.STP_F_PERC_TOT);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.log();
			flg.toggleG();
			stp.setStep(Step.STP_G_PERC_TOT);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.percentOfTotal();
			stp.setStep(Step.STP_PERC_TOT);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey24() throws CalculatorException {
		if (flg.getF() == 1) {
			
			tmp = new Number[2];
			tmp = fin.soydDepreciation(stk.top());
			stk.put(tmp[1]);
			stk.put(tmp[0]);

			flg.toggleF();
			stp.setStep(Step.STP_F_PERC_DELTA);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.fractionalPart();
			flg.toggleG();
			stp.setStep(Step.STP_G_PERC_DELTA);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.percentDifference();
			stp.setStep(Step.STP_PERC_DELTA);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey25() throws CalculatorException {
		if (flg.getF() == 1) {
			
			tmp = new Number[2];
			tmp = fin.dbDepreciation(stk.top());
			stk.put(tmp[1]);
			stk.put(tmp[0]);

			flg.toggleF();
			stp.setStep(Step.STP_F_PERC);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.integralPart();
			stp.setStep(Step.STP_G_PERC);
			dsp.setStatus(Display.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.percent();
			stp.setStep(Step.STP_PERC);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey26() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			stk.diffOfDaysBetweenDates();
			flg.toggleG();
			stp.setStep(Step.STP_F_EEX);
			dsp.setStatus(Display.STATUS_OUTPUT);

		} else if (flg.getSto() > 0) {
			flg.toggleC();
			flg.setSto(0);
			stp.setStep(Step.STP_G_EEX);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			if (!dsp.getValue().isZero()) {
				if ((dsp.getStatus() != Display.STATUS_INPUT)) {
					dsp.inputChar('1');
				}
				this.dsp.setMode(Display.MODE_EXPONENTIAL);
			}
			stp.setStep(Step.STP_EEX);
		}
	}

	protected void doKey30() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setStep(Step.STP_STO_SUB);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.subtract();
			stp.setStep(Step.STP_SUB);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey31() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.togglePrgm();

			if (flg.getPrgm() == 1) {
				dsp.inputProgramStep(prg.getCurrentIndex(),
						(Step) prg.get());
				dsp.setMode(Display.MODE_PROGRAM);
			} else {
				prg.setCurrentIndex(0);
				dsp.setMode(Display.MODE_NORMAL);
			}

			dsp.setStatus(Display.STATUS_READY);
			flg.toggleF();
		} else if (flg.getG() == 1) {

			// TODO: show partial result in display.
			if (flg.getRun() == 1) {
				dsp.setStatus(Display.STATUS_OUTPUT);
				this.updateDisplay();
				controller.getWindow().updateDisplay();
				try {
					Thread.sleep(1500);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			if (dsp.getMode() == Display.MODE_NORMAL) {
				flg.toggleRun();
				if (flg.getRun() == 1) {
					executeProgram();
				} else {
					flg.setRun(0);
					dsp.setLock(false);
					controller.getWindow().updateDisplay();
				}
			}
		}
	}

	protected void doKey32() throws CalculatorException {
		if (flg.getF() == 1) {
			mem.clearStats();
			stk.clear();
			flg.toggleF();
		} else if (flg.getG() == 1) {
			// Goes back until get to index 0,
			// then goes to the last index.
			if (!prg.back())
				prg.setCurrentIndex(0);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			this.executeSingleStep();
		}

	}

	protected void doKey33() throws CalculatorException {
		if (flg.getF() == 1) {
			if (dsp.getMode() == Display.MODE_NORMAL)
				prg.setCurrentIndex(0);
			else if (dsp.getMode() == Display.MODE_PROGRAM)
				prg.clear();
			flg.toggleF();
			stp.setStep(Step.STP_F_ROLL);
		} else if (flg.getG() == 1) {
			flg.toggleGto();
			flg.toggleG();
			stp.setStep(Step.STP_G_ROLL);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.rollDown();
			stp.setStep(Step.STP_ROLL);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey34() throws CalculatorException {
		if (flg.getF() == 1) {
			fin.clear();
			stp.setStep(Step.STP_F_XY);
			dsp.setStatus(Display.STATUS_READY);
			flg.toggleF();
		} else if (flg.getG() == 1) {
			stk.lowerTopPair();
			stp.setStep(Step.STP_G_XY);
			dsp.setStatus(Display.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.swapTopPair();
			stp.setStep(Step.STP_XY);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey35() throws CalculatorException {
		if (flg.getF() == 1) {
			mem.clear();
			fin.clear();
			stk.clear();
			stk.clearLastTop();

			flg.toggleF();
			stp.setStep(Step.STP_F_CLX);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.set(0, ZERO);
			stp.setStep(Step.STP_CLX);
			dsp.setStatus(Display.STATUS_READY);
		}
	}

	protected void doKey36() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
			stp.setStep(Step.STP_F_ENTER);
		} else if (flg.getG() == 1) {

			this.setX(stk.getLastTop());

			stp.setStep(Step.STP_G_ENTER);
			dsp.setStatus(Display.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.put(dsp.getValue());
			stp.setStep(Step.STP_ENTER);
			dsp.setStatus(Display.STATUS_READY);
		}
	}

	protected void doKey40() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setStep(Step.STP_STO_SUM);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.add();
			stp.setStep(Step.STP_SUM);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey41() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else if (flg.getOn() == 1) {
			flg.toggleOn();
		} else {
			flg.toggleOn();
		}
	}

	protected void doKey42() throws CalculatorException {

		if (flg.getF() > 0) {
			this.clearFgsr();
		} else {
			this.clearFgsr();
			flg.toggleF();
		}
	}

	protected void doKey43() throws CalculatorException {

		if (flg.getG() > 0) {
			this.clearFgsr();
		} else {
			this.clearFgsr();
			flg.toggleG();
		}

	}

	protected void doKey44() throws CalculatorException {

		if (flg.getSto() > 0) {
			this.clearFgsr();
		} else {
			this.clearFgsr();
			flg.toggleSto();
		}
	}

	protected void doKey45() throws CalculatorException {

		if (flg.getRcl() > 0) {
			this.clearFgsr();
		} else {
			this.clearFgsr();
			flg.toggleRcl();
		}
	}

	protected void doKey48() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			// Temp variable
			tmp = new Number[2];

			// Calculates mean values
			tmp = mem.standardDeviation();

			// Inserts y and x results
			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setStep(Step.STP_G_DOT);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getSto() > 0) {
			this.stoInput(-1);
		} else if (flg.getRcl() > 0) {
			this.rclInput(-1);
		} else if (flg.getGto() > 0) {
			this.gtoInput(-1);
		} else if (flg.getOn() == 1) {
			dsp.toggleComma();
			flg.toggleOn();
		} else {
			this.shiftUpIfOutputStatus();

			this.dsp.inputChar('.');
			stp.setStep(Step.STP_DOT);
		}
	}

	protected void doKey49() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			mem.subStats(stk.top(), stk.get(1));
			stk.setLastTop();
			stk.set(0, mem.getR1());
			flg.toggleG();
			stp.setStep(Step.STP_G_TOT);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			mem.sumStats(stk.top(), stk.get(1));
			stk.setLastTop();
			stk.set(0, mem.getR1());
			stp.setStep(Step.STP_TOT);
			dsp.setStatus(Display.STATUS_READY);
		}
	}
}
