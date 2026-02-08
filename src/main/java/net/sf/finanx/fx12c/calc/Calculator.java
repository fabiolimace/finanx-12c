package net.sf.finanx.fx12c.calc;

import static net.sf.finanx.fx12c.math.Number.*;

import net.sf.finanx.fx12c.math.Number;
import net.sf.finanx.fx12c.memory.FinanceMemory;
import net.sf.finanx.fx12c.memory.GeneralMemory;
import net.sf.finanx.fx12c.memory.ProgramMemory;
import net.sf.finanx.fx12c.memory.Stack;
import net.sf.finanx.fx12c.utils.Timer;

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

	private Configuration cfg;

	private boolean testing;

	private class Worker extends Thread {

		private Calculator calculator;

		public Worker(Calculator calculator) {
			super();
			this.calculator = calculator;
		}

		public void run() {
			calculator.executeProgramLoop();
		}
	}

	public Calculator() {
		this.init(false);
	}

	public Calculator(boolean testing) {
		this.init(testing);
	}

	protected void init(boolean testing) {

		// Transient data
		this.stp = new Step();
		this.flg = new Flags();
		this.dsp = new Display();

		// Persistent data
		this.stk = new Stack();
		this.fin = new FinanceMemory();
		this.mem = new GeneralMemory();
		this.prg = new ProgramMemory();

		// Persistent configurations
		this.cfg = new Configuration();

		// An independent thread that
		// executes steps sequentially.
		this.worker = new Worker(this);

		this.testing = testing;
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
		return this.stk.peek();
	}

	public void shiftUpIfOutputStatus() {
		if ((dsp.getStatus() == Display.STATUS_OUTPUT) || (dsp.getStatus() == Display.STATUS_OUTPUT2)) {
			stk.shiftDown();
		}
	}

	private void updateOneWayBindedFlags() {
		stk.setDmy(flg.getDmy() == 1 ? true : false);
		fin.setBegin(flg.getBegin() == 1 ? true : false);
		fin.setC(flg.getC() == 1 ? true : false);
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
		case KEY_00: {
			break;
		}
		case KEY_01: {
			break;
		}
		case KEY_02: {
			break;
		}
		case KEY_03: {
			break;
		}
		case KEY_04: {
			break;
		}
		case KEY_05: {
			break;
		}
		case KEY_06: {
			break;
		}
		case KEY_07: {
			break;
		}
		case KEY_08: {
			break;
		}
		case KEY_09: {
			if (flg.getG() == 1) {
				String tmp = "";

				tmp += "P" + Display.zeroPad(prg.getUsedSteps(), 3);
				tmp += " ";
				tmp += "r" + Display.zeroPad(mem.getAvailableRegisters(), 3);

				dsp.setMessage(tmp);
				dsp.setPause(true);
				controller.getWindow().updateDisplay();
			}
			break;
		}
		case KEY_10: {
			break;
		}
		case KEY_20: {
			break;
		}
		case KEY_30: {
			break;
		}
		case KEY_40: {
			break;
		}
		case KEY_11: {
			break;
		}
		case KEY_12: {
			break;
		}
		case KEY_13: {
			break;
		}
		case KEY_14: {
			break;
		}
		case KEY_15: {
			break;
		}
		case KEY_16: {
			break;
		}
		case KEY_21: {
			break;
		}
		case KEY_22: {
			break;
		}
		case KEY_23: {
			break;
		}
		case KEY_24: {
			break;
		}
		case KEY_25: {
			break;
		}
		case KEY_26: {
			break;
		}
		case KEY_31: {
			break;
		}
		case KEY_32: {
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

				dsp.inputProgramStep(prg.getCurrentIndex(), (Step) prg.get());
				dsp.setMode(Display.MODE_PROGRAM);
				controller.getWindow().updateDisplay();
				dsp.setMode(Display.MODE_NORMAL);
				dsp.setPause(true);
			}
			break;
		}
		case KEY_33: {
			break;
		}
		case KEY_34: {
			break;
		}
		case KEY_35: {
			break;
		}
		case KEY_36: {
			if (flg.getF() == 1) {
				dsp.setMessage(dsp.getMantissa());
				dsp.setPause(true);
				controller.getWindow().updateDisplay();
			}
			break;
		}
		case KEY_41: {
			break;
		}
		case KEY_42: {
			break;
		}
		case KEY_43: {
			break;
		}
		case KEY_44: {
			break;
		}
		case KEY_45: {
			break;
		}
		case KEY_48: {
			break;
		}
		case KEY_49: {
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
			case KEY_00: {
				this.doKey00();
				break;
			}
			case KEY_01: {
				this.doKey01();
				break;
			}
			case KEY_02: {
				this.doKey02();
				break;
			}
			case KEY_03: {
				this.doKey03();
				break;
			}
			case KEY_04: {
				this.doKey04();
				break;
			}
			case KEY_05: {
				this.doKey05();
				break;
			}
			case KEY_06: {
				this.doKey06();
				break;
			}
			case KEY_07: {
				this.doKey07();
				break;
			}
			case KEY_08: {
				this.doKey08();
				break;
			}
			case KEY_09: {
				this.doKey09();
				break;
			}
			case KEY_10: {
				this.doKey10();
				break;
			}
			case KEY_20: {
				this.doKey20();
				break;
			}
			case KEY_30: {
				this.doKey30();
				break;
			}
			case KEY_40: {
				this.doKey40();
				break;
			}
			case KEY_11: {
				this.doKey11();
				break;
			}
			case KEY_12: {
				this.doKey12();
				break;
			}
			case KEY_13: {
				this.doKey13();
				break;
			}
			case KEY_14: {
				this.doKey14();
				break;
			}
			case KEY_15: {
				this.doKey15();
				break;
			}
			case KEY_16: {
				// TODO: When [DATE] is executed as an instruction in a running
				// program, the calculator pauses for about 1 second to display
				// the result, then resumes program execution.
				this.doKey16();
				break;
			}
			case KEY_21: {
				this.doKey21();
				break;
			}
			case KEY_22: {
				this.doKey22();
				break;
			}
			case KEY_23: {
				this.doKey23();
				break;
			}
			case KEY_24: {
				this.doKey24();
				break;
			}
			case KEY_25: {
				this.doKey25();
				break;
			}
			case KEY_26: {
				this.doKey26();
				break;
			}
			case KEY_31: {
				this.doKey31();
				break;
			}
			case KEY_32: {
				this.doKey32();
				break;
			}
			case KEY_33: {
				this.doKey33();
				break;
			}
			case KEY_34: {
				this.doKey34();
				break;
			}
			case KEY_35: {
				this.doKey35();
				break;
			}
			case KEY_36: {
				this.doKey36();
				break;
			}
			case KEY_41: {
				// this.doKey41();
				controller.quit();
				break;
			}
			case KEY_42: {
				this.doKey42();
				break;
			}
			case KEY_43: {
				this.doKey43();
				break;
			}
			case KEY_44: {
				this.doKey44();
				break;
			}
			case KEY_45: {
				this.doKey45();
				break;
			}
			case KEY_48: {
				this.doKey48();
				break;
			}
			case KEY_49: {
				this.doKey49();
				break;
			}
			default: {
				break;
			}
			} // End Switch

			this.updateDisplay();
			this.printMemory();

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

	public void printMemory() {
		if ((dsp.getStatus() != Display.STATUS_INPUT) && flg.getF() == 0 && flg.getG() == 0 && flg.getSto() == 0
				&& flg.getRcl() == 0) {
			System.out.println("--------------------\n");
			System.out.println(stk);
			System.out.println(fin);
		}
	}

	/*
	 * Processes STO operations. It Receives integers to form the memory index. It
	 * uses the memory index to store the X value into a memory register. Note 1: If
	 * the calculator has only 20 registers the calculator will work like the HP12C
	 * Gold, on witch a point represents 10. Note 2: It stores values from 000 to
	 * 999.
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
				if (stp.getOp().getModifier() == Key.KEY_44) {
					if (stp.getOp().getKey() == Key.KEY_40) {
						mem.set(stp.getComplement(), stk.peek().add(mem.get(stp.getComplement())));
					} else if (stp.getOp().getKey() == Key.KEY_30) {
						mem.set(stp.getComplement(), stk.peek().subtract(mem.get(stp.getComplement())));
					} else if (stp.getOp().getKey() == Key.KEY_20) {
						mem.set(stp.getComplement(), stk.peek().multiply(mem.get(stp.getComplement())));
					} else if (stp.getOp().getKey() == Key.KEY_10) {
						mem.set(stp.getComplement(), stk.peek().divide(mem.get(stp.getComplement())));
					}
				} else {
					mem.set((int) (i(tmp[0]) * 100 + i(tmp[1]) * 10 + i(tmp[2])), stk.peek());
				}
				dsp.setStatus(Display.STATUS_OUTPUT);
				stp.setOp(Op.OP_STO);
			}
		}
	}

	/*
	 * Processes RCL operations. It Receives integers to form the memory index. It
	 * uses the memory index to store the X value into a memory register. Note 1: If
	 * the calculateor has only 20 registers the calculator will work like the HP12C
	 * Gold, on witch a point represents 10. Note 2: It recall from 000 to 999.
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
				stp.setOp(Op.OP_RCL);
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
				stp.setOp(Op.OP_GTO);
				stp.setComplement((int) (tmp[0].i() * 100 + tmp[1].i() * 10 + tmp[2].i()));
				dsp.setStatus(Display.STATUS_OUTPUT);
			} else {
				flg.setGto(0);
				prg.setCurrentIndex((int) (tmp[0].i() * 100 + tmp[1].i() * 10 + tmp[2].i()));
				stp.setOp(Op.OP_GTO);
				stp.setComplement((int) (tmp[0].i() * 100 + tmp[1].i() * 10 + tmp[2].i()));
				dsp.setStatus(Display.STATUS_OUTPUT);
			}
		}
	}

	// private boolean checkOddPeriod() {
	// try {
	// if(!fin.getN().fractionalPart().isZero())
	// return true;
	// else
	// return false;
	// } catch (CalculatorException e) {
	// showError(e);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return false;
	// }

	// protected void executeOddPeriodTasks() {
	// try {
	// // With odd period using simple interest
	// if (flg.getC() == 0) {
	// fin.setPv(fin.simpleFutureValue());
	// }
	// // With odd period using compound interest
	// else {
	// fin.setPv(fin.futureValue());
	// }
	//
	// // Fixing PV sign
	// fin.setPv(fin.getPv().negate());
	//
	// // Truncating the odd period
	// fin.setN(fin.getN().integralPart());
	//
	// } // End Try
	// catch (CalculatorException e) {
	// showError(e);
	// // e.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	//// }
	// }

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
		if (!testing) {
			controller.getWindow().updateDisplay();
		}
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
			case KEY_42: {
				this.doKey42();
				if (flg.getF() == 1) {
					stp.setOp(Op.OP_F);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					stp.clear();
					dsp.setStatus(Display.STATUS_READY);
				}
				break;
			}
			case KEY_43: {
				this.doKey43();
				if (flg.getG() == 1) {
					stp.setOp(Op.OP_G);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					stp.clear();
					dsp.setStatus(Display.STATUS_READY);
				}
				break;
			}
			case KEY_44: {
				this.doKey44();
				if (flg.getSto() > 0) {
					stp.setOp(Op.OP_STO);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					stp.clear();
					dsp.setStatus(Display.STATUS_READY);
				}
				break;
			}
			case KEY_45: {
				this.doKey45();
				if (flg.getRcl() > 0) {
					stp.setOp(Op.OP_RCL);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					stp.clear();
					dsp.setStatus(Display.STATUS_READY);
				}
				break;
			}
			case KEY_33: { // FIXME: should be inserted as a program instruction
				this.doKey33();

				if (flg.getGto() > 0) {
					stp.setOp(Op.OP_GTO);
					dsp.setStatus(Display.STATUS_INPUT);
				} else {
					dsp.setStatus(Display.STATUS_READY);
				}

				break;
			}
			case KEY_32: {
				if (flg.getF() == 1) {
					stp = new Step(Op.OP_SIGMA);
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
					if (k.getCode() == Key.KEY_36.getCode()) {
						doKey42();
						stp.clear();
						dsp.setStatus(Display.STATUS_READY);
					} else if (k.getCode() == Key.KEY_31.getCode()) {
						this.doKey31();
					} else {
						stp.setOp(Op.valueOf(Key.KEY_42, k));
						putStep(stp);
						flg.toggleF();
						dsp.setStatus(Display.STATUS_READY);
					}
				} else if (flg.getG() == 1) {
					if (k.getCode() == Key.KEY_09.getCode()) {
						doKey43();
						stp.clear();
						dsp.setStatus(Display.STATUS_READY);
					} else {
						stp.setOp(Op.valueOf(Key.KEY_43, k));
						putStep(stp);
						flg.toggleG();
						dsp.setStatus(Display.STATUS_READY);
					}
				} else if (flg.getSto() > 0) {
					if (k.getCode() <= Key.KEY_09.getCode()) {
						this.stoInput(k.getCode());
					} else if (k.getCode() == Key.KEY_48.getCode()) {
						this.stoInput(Key.KEY_NULL.getCode());
					} else if (k.getCode() == Key.KEY_11.getCode()) {
						stp.setOp(Op.OP_STO_N);
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_12.getCode()) {
						stp.setOp(Op.OP_STO_I);
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_13.getCode()) {
						stp.setOp(Op.OP_STO_PV);
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_14.getCode()) {
						stp.setOp(Op.OP_STO_PMT);
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_15.getCode()) {
						stp.setOp(Op.OP_STO_FV);
						flg.setSto(0);
					} else if (k.getCode() == Key.KEY_40.getCode()) {
						stp.setOp(Op.OP_STO_ADD);
					} else if (k.getCode() == Key.KEY_30.getCode()) {
						stp.setOp(Op.OP_STO_SUB);
					} else if (k.getCode() == Key.KEY_20.getCode()) {
						stp.setOp(Op.OP_STO_MUL);
					} else if (k.getCode() == Key.KEY_10.getCode()) {
						stp.setOp(Op.OP_STO_DIV);
					}

					if (flg.getSto() == 0) {
						putStep(stp);
						dsp.setStatus(Display.STATUS_READY);
					}
				} else if (flg.getRcl() > 0) {
					if (k.getCode() <= Key.KEY_09.getCode()) {
						this.rclInput(k.getCode());
					} else if (k.getCode() == Key.KEY_48.getCode()) {
						this.rclInput(Key.KEY_NULL.getCode());
					} else if (k.getCode() == Key.KEY_11.getCode()) {
						stp.setOp(Op.OP_RCL_N);
						flg.setRcl(0);
					} else if (k.getCode() == Key.KEY_12.getCode()) {
						stp.setOp(Op.OP_RCL_I);
						flg.setRcl(0);
					} else if (k.getCode() == Key.KEY_13.getCode()) {
						stp.setOp(Op.OP_RCL_PV);
						flg.setRcl(0);
					} else if (k.getCode() == Key.KEY_14.getCode()) {
						stp.setOp(Op.OP_RCL_PMT);
						flg.setRcl(0);
					} else if (k.getCode() == Key.KEY_15.getCode()) {
						stp.setOp(Op.OP_RCL_FV);
						flg.setRcl(0);
					}

					if (flg.getRcl() == 0) {
						putStep(stp);
						dsp.setStatus(Display.STATUS_READY);
					}
				} else if (flg.getGto() > 0) {
					if (k.getCode() <= Key.KEY_09.getCode())
						this.gtoInput(k.getCode());
					else if (k.getCode() == Key.KEY_48.getCode())
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
					stp.setOp(Op.valueOf(Key.KEY_NULL, k));
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
		if (stp.getOp() == Op.OP_NULL) {
			this.stopProgram();
		}
		// goes to line number ZERO and stops execution.
		else if (stp.getOp().getModifier() == Key.KEY_43 && stp.getOp().getKey() == Key.KEY_33
				&& stp.getComplement() == Key.KEY_00.getCode()) {
			System.out.println("executeStep stop 2: " + stp.getOp().getKey());
			this.stopProgram();
		}
		// goes to line number specified by the instruction [g][gto]xxx.
		else if (stp.getOp().getModifier() == Key.KEY_43 && stp.getOp().getKey() == Key.KEY_33) {

			prg.setCurrentIndex(stp.getComplement());
		} else if (stp.getOp().getModifier() == Key.KEY_43 && stp.getOp().getKey() == Key.KEY_34) {
			if (stk.get(0).lessThanOrEqualTo(stk.get(1))) {
				this.prg.next();
			} else {
				this.prg.next();
				this.prg.next();
			}
		} else if (stp.getOp().getModifier() == Key.KEY_43 && stp.getOp().getKey() == Key.KEY_35) {

			if (stk.get(0).isZero()) {
				this.prg.next();
			} else {
				this.prg.next();
				this.prg.next();
			}
		} else if (stp.getOp().getKey() == Key.KEY_44) {
			mem.set(stp.getComplement(), stk.peek());
			dsp.setStatus(Display.STATUS_OUTPUT);
			this.prg.next();
		} else if (stp.getOp().getKey() == Key.KEY_45) {
			this.setX(mem.get(stp.getComplement()));
			dsp.setStatus(Display.STATUS_OUTPUT);
			this.prg.next();
		} else {
			if (stp.getOp().getModifier().getCode() > (-1)) {
				keyReleased(stp.getOp().getModifier());
			}
			if (stp.getOp().getKey().getCode() > (-1)) {
				keyReleased(stp.getOp().getKey());
			}
			if (stp.getComplement() > (-1)) {
				keyReleased(Key.valueOf(stp.getComplement()));
			}
			this.prg.next();
		}

	}

	public void executeSingleStep() {

		if (!testing) {
			showDisplayMessage("running");
			getDisplay().setLock(false);
		}

		if (prg.getCurrentIndex() == 0)
			this.prg.next();

		System.out.println(prg.getCurrentIndex());
		this.executeStep((Step) this.prg.get());

		if (prg.getCurrentIndex() == prg.getSize() - 1) {
			this.stopProgram();
		}
	}

	public void executeProgram() {
		if (!testing) {
			this.worker = new Worker(this);
			this.worker.start();
		} else {
			this.executeProgramLoop();
		}
	}

	protected void executeProgramLoop() {
		while (this.getFlags().getRun() == 1) {
			this.executeSingleStep();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				this.getFlags().setRun(0);
				Thread.currentThread().interrupt();
			}
		}
	}

	public void stopProgram() {

		flg.setRun(0);
		prg.setCurrentIndex(0);

		if (!testing) {
			dsp.setLock(false);
			controller.getWindow().updateDisplay();
		}
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
			stp.setOp(Op.OP_F_0);
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
			stp.setOp(Op.OP_STAT_X);
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
			stp.setOp(Op.OP_0);
		}
	}

	protected void doKey01() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(1);
			this.flg.toggleF();
			stp.setOp(Op.OP_F_1);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {

			// Temporary variables
			tmp = new Number[2];

			tmp = mem.xLinearEstimation(stk.peek());

			stk.setLastX();

			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setOp(Op.OP_STAT_XR);
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
			stp.setOp(Op.OP_1);
		}
	}

	protected void doKey02() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(2);
			this.flg.toggleF();
			stp.setOp(Op.OP_F_2);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			// Temporary variables
			tmp = new Number[2];

			tmp = mem.yLinearEstimation(stk.peek());

			stk.setLastX();

			stk.set(0, tmp[1]);
			stk.put(tmp[0]);

			flg.toggleG();
			stp.setOp(Op.OP_STAT_YR);
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
			stp.setOp(Op.OP_2);
		}
	}

	protected void doKey03() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(3);
			this.flg.toggleF();
			stp.setOp(Op.OP_F_3);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			stk.factorial();
			flg.toggleG();
			stp.setOp(Op.OP_FACTORIAL);
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
			stp.setOp(Op.OP_3);
		}
	}

	protected void doKey04() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(4);
			this.flg.toggleF();
			stp.setOp(Op.OP_F_4);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setDmy(1);
			this.flg.toggleG();
			stp.setOp(Op.OP_DMY);
		} else if (flg.getSto() > 0) {
			this.stoInput(4);
		} else if (flg.getRcl() > 0) {
			this.rclInput(4);
		} else if (flg.getGto() > 0) {
			this.gtoInput(4);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('4');
			stp.setOp(Op.OP_4);
		}
	}

	protected void doKey05() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(5);
			this.flg.toggleF();
			stp.setOp(Op.OP_F_5);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setDmy(0);
			this.flg.toggleG();
			stp.setOp(Op.OP_MDY);
		} else if (flg.getSto() > 0) {
			this.stoInput(5);
		} else if (flg.getRcl() > 0) {
			this.rclInput(5);
		} else if (flg.getGto() > 0) {
			this.gtoInput(5);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('5');
			stp.setOp(Op.OP_5);
		}
	}

	protected void doKey06() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(6);
			this.flg.toggleF();
			stp.setOp(Op.OP_F_6);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			stk.put(mem.weightedMean());

			flg.toggleG();
			stp.setOp(Op.OP_STAT_XW);
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
			stp.setOp(Op.OP_6);
		}
	}

	protected void doKey07() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(7);
			this.flg.toggleF();
			stp.setOp(Op.OP_F_7);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setBegin(1);
			this.flg.toggleG();
			stp.setOp(Op.OP_BEG);
		} else if (flg.getSto() > 0) {
			this.stoInput(7);
		} else if (flg.getRcl() > 0) {
			this.rclInput(7);
		} else if (flg.getGto() > 0) {
			this.gtoInput(7);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('7');

			stp.setOp(Op.OP_7);
		}
	}

	protected void doKey08() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(8);
			this.flg.toggleF();
			stp.setOp(Op.OP_F_8);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			this.flg.setBegin(0);
			this.flg.toggleG();
			stp.setOp(Op.OP_END);
		} else if (flg.getSto() > 0) {
			this.stoInput(8);
		} else if (flg.getRcl() > 0) {
			this.rclInput(8);
		} else if (flg.getGto() > 0) {
			this.gtoInput(8);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('8');

			stp.setOp(Op.OP_8);
		}
	}

	protected void doKey09() throws CalculatorException {
		if (flg.getF() == 1) {
			this.dsp.setPrecision(9);
			this.flg.toggleF();
			stp.setOp(Op.OP_F_9);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			flg.toggleG();
			stp.setOp(Op.OP_MEM);
		} else if (flg.getSto() > 0) {
			this.stoInput(9);
		} else if (flg.getRcl() > 0) {
			this.rclInput(9);
		} else if (flg.getGto() > 0) {
			this.gtoInput(9);
		} else {
			this.shiftUpIfOutputStatus();
			this.dsp.inputChar('9');
			stp.setOp(Op.OP_9);
		}
	}

	protected void doKey10() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setOp(Op.OP_STO_DIV);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.divide();
			stp.setOp(Op.OP_DIV);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey11() throws CalculatorException {
		if (flg.getF() == 1) {
			tmp = fin.amortization(stk.peek(), dsp.getPrecision());

			// For details, see HP12Camortization(...)
			stk.put(tmp[0]);
			stk.put(tmp[1]);
			stk.put(tmp[2]);
			fin.setPv(tmp[3]);
			fin.setN(tmp[4]);

			flg.toggleF();
			stp.setOp(Op.OP_AMORT);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.put(stk.pop().multiply(TWELVE));
			fin.setN(stk.peek());
			stp.setOp(Op.OP_12_MUL);
			dsp.setStatus(Display.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			fin.setN(stk.peek());
			flg.setSto(0);
			stp.setOp(Op.OP_STO_N);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getN());
			flg.setRcl(0);
			stp.setOp(Op.OP_RCL_N);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY) || (dsp.getStatus() == Display.STATUS_OUTPUT2)) {

				stk.set(0, fin.period());

				fin.setN(stk.peek());
				dsp.setStatus(Display.STATUS_OUTPUT2);

			} else {
				fin.setN(stk.peek());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setOp(Op.OP_N);
		}
	}

	protected void doKey12() throws CalculatorException {
		if (flg.getF() == 1) {

			tmp = fin.simpleInterest();

			stk.set(0, tmp[0]);
			stk.set(1, (fin.getPv().negate()));
			stk.set(2, tmp[1]);

			flg.toggleF();
			stp.setOp(Op.OP_INT);
			dsp.setStatus(Display.STATUS_OUTPUT);

		} else if (flg.getG() == 1) {
			stk.put(stk.pop().divide(TWELVE));
			fin.setI(stk.peek());
			stp.setOp(Op.OP_12_DIV);
			dsp.setStatus(Display.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			fin.setI(stk.peek());
			flg.setSto(0);
			stp.setOp(Op.OP_STO_I);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getI());
			flg.setRcl(0);
			stp.setOp(Op.OP_RCL_I);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY) || (dsp.getStatus() == Display.STATUS_OUTPUT2)) {

				stk.set(0, fin.rate());

				// Here odd period check have to be done AFTER the calculus
				// if (checkOddPeriod())
				// executeOddPeriodTasks();

				fin.setI(stk.peek());
				dsp.setStatus(Display.STATUS_OUTPUT2);
			} else {
				fin.setI(stk.peek());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setOp(Op.OP_I);
		}
	}

	protected void doKey13() throws CalculatorException {
		if (flg.getF() == 1) {

			stk.set(0, fin.npv(mem.getArray()));

			flg.toggleF();
			stp.setOp(Op.OP_NPV);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			mem.set(0, stk.peek());

			flg.toggleG();
			stp.setOp(Op.OP_CFO);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			fin.setPv(stk.peek());
			flg.setSto(0);
			stp.setOp(Op.OP_STO_PV);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getPv());
			flg.setRcl(0);
			stp.setOp(Op.OP_RCL_PV);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY) || (dsp.getStatus() == Display.STATUS_OUTPUT2)) {

				// Here odd period check should not be done
				// if(checkOddPeriod()) executeOddPeriodTasks();

				stk.set(0, fin.presentValue());

				fin.setPv(stk.peek());
				dsp.setStatus(Display.STATUS_OUTPUT2);
			} else {
				fin.setPv(stk.peek());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setOp(Op.OP_PV);
		}
	}

	protected void doKey14() throws CalculatorException {
		if (flg.getF() == 1) {
			stk.round(dsp.getPrecision());
			flg.toggleF();
			stp.setOp(Op.OP_RND);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			mem.put(stk.peek(), ONE);
			fin.setN(fin.getN().add(ONE));
			flg.toggleG();
			stp.setOp(Op.OP_CFJ);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			fin.setPmt(stk.peek());
			flg.setSto(0);
			stp.setOp(Op.OP_STO_PMT);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getPmt());
			flg.setRcl(0);
			stp.setOp(Op.OP_RCL_PMT);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY) || (dsp.getStatus() == Display.STATUS_OUTPUT2)) {

				// // Here odd period check should be done BEFORE the calculus
				// if (checkOddPeriod())
				// executeOddPeriodTasks();

				stk.set(0, fin.pricePayment());

				fin.setPmt(stk.peek());
				dsp.setStatus(Display.STATUS_OUTPUT2);
			} else {
				fin.setPmt(stk.peek());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setOp(Op.OP_PMT);
		}
	}

	protected void doKey15() throws CalculatorException {
		if (flg.getF() == 1) {

			stk.set(0, fin.irr(mem.getArray()));

			flg.toggleF();
			stp.setOp(Op.OP_IRR);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			mem.setTimes(mem.getCurrentIndex(), stk.peek());
			flg.toggleG();
			stp.setOp(Op.OP_NJ);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			fin.setFv(stk.peek());
			flg.setSto(0);
			stp.setOp(Op.OP_STO_FV);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getRcl() > 0) {
			stk.put(fin.getFv());
			flg.setRcl(0);
			stp.setOp(Op.OP_RCL_FV);
			dsp.setStatus(Display.STATUS_READY);
		} else {
			if ((dsp.getStatus() == Display.STATUS_READY) || (dsp.getStatus() == Display.STATUS_OUTPUT2)) {

				// Here odd period check should be done BEFORE the calculus
				// if (checkOddPeriod())
				// executeOddPeriodTasks();

				stk.set(0, fin.futureValue());

				fin.setFv(stk.peek());
				dsp.setStatus(Display.STATUS_OUTPUT2);
			} else {
				fin.setFv(stk.peek());
				dsp.setStatus(Display.STATUS_READY);
			}
			stp.setOp(Op.OP_FV);
		}
	}

	protected void doKey16() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {

			tmp = new Number[2];

			stk.addDaysToDate();

			// Calculate week day and show it.
			dsp.setValue(stk.peek());
			stp.setOp(Op.OP_DATE);
			dsp.setStatus(Display.STATUS_READY);
			dsp.setMessage((dsp.getString() + "          ").substring(1, 11) + stk.dayOfWeek().i()); // TODO: use
																										// constant for
																										// spaces

			flg.toggleG();
			dsp.setLock(true);

		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			if ((dsp.getStatus() != Display.STATUS_INPUT)) {
				stk.negate();
			} else {
				this.dsp.inputChar('-');
			}
			stp.setOp(Op.OP_CHS);
		}
	}

	protected void doKey20() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setOp(Op.OP_STO_MUL);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.multiply();
			stp.setOp(Op.OP_MUL);
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
			stp.setOp(Op.OP_PRICE);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getG() == 1) {
			stk.sqrt();
			flg.toggleG();
			stp.setOp(Op.OP_SQRT);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.pow();
			stp.setOp(Op.OP_POWER);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey22() throws CalculatorException {
		if (flg.getF() == 1) {

			// Shows a message: "Not Available Sorry"
			this.showDisplayMessage(" N.A. Sorry ");
			flg.toggleF();
			stp.setOp(Op.OP_YTM);
			dsp.setStatus(Display.STATUS_READY);

			// TODO: This piece of code is ready to work
			// but the function bondYield is not yet.
			// A message like "N/A Sorry" will be shown in the
			// display until I can rewrite that function again.
			/*
			 * dt = new Date[2];
			 * 
			 * HP12CbondYield(fin.getPv(), fin.getPmt(), dt[1], dt[0]);
			 * stk.put(HP12CbondYield(fin.getPv(), fin.getPmt(), dt[1], dt[0]));
			 * 
			 * flg.toggleF(); stp.setStep(Combo.OP_F_RECIPROCAL);
			 * dsp.setStatus(Display.STATUS_OUTPUT);
			 */

		} else if (flg.getG() == 1) {
			stk.exp();
			flg.toggleG();
			stp.setOp(Op.OP_EX);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.reciprocal();
			stp.setOp(Op.OP_RECIPROCAL);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey23() throws CalculatorException {
		if (flg.getF() == 1) {

			tmp = new Number[2];
			tmp = fin.slDepreciation(stk.peek());
			stk.put(tmp[1]);
			stk.put(tmp[0]);

			flg.toggleF();
			stp.setOp(Op.OP_SL);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.log();
			flg.toggleG();
			stp.setOp(Op.OP_LN);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.percentOfTotal();
			stp.setOp(Op.OP_PERCENT_T);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey24() throws CalculatorException {
		if (flg.getF() == 1) {

			tmp = new Number[2];
			tmp = fin.soydDepreciation(stk.peek());
			stk.put(tmp[1]);
			stk.put(tmp[0]);

			flg.toggleF();
			stp.setOp(Op.OP_SOYD);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.fractionalPart();
			flg.toggleG();
			stp.setOp(Op.OP_FRAC);
			dsp.setStatus(Display.STATUS_OUTPUT);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.percentDifference();
			stp.setOp(Op.OP_DELTA_PERCENT);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey25() throws CalculatorException {
		if (flg.getF() == 1) {

			tmp = new Number[2];
			tmp = fin.dbDepreciation(stk.peek());
			stk.put(tmp[1]);
			stk.put(tmp[0]);

			flg.toggleF();
			stp.setOp(Op.OP_DB);
			dsp.setStatus(Display.STATUS_OUTPUT);
			;
		} else if (flg.getG() == 1) {
			stk.integralPart();
			stp.setOp(Op.OP_INTG);
			dsp.setStatus(Display.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.percent();
			stp.setOp(Op.OP_PERCENT);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey26() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			stk.diffOfDaysBetweenDates();
			flg.toggleG();
			stp.setOp(Op.OP_DELTA_DYS);
			dsp.setStatus(Display.STATUS_OUTPUT);

		} else if (flg.getSto() > 0) {
			flg.toggleC();
			flg.setSto(0);
			stp.setOp(Op.OP_STO_EEX);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {

			if ((dsp.getStatus() != Display.STATUS_INPUT)) {
				if ((dsp.getStatus() != Display.STATUS_READY)) {
					stk.shiftDown();
				}
				dsp.inputChar('1');
			}

			this.dsp.setMode(Display.MODE_EXPONENTIAL);
			stp.setOp(Op.OP_EEX);
		}
	}

	protected void doKey30() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setOp(Op.OP_STO_SUB);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.subtract();
			stp.setOp(Op.OP_SUB);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey31() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.togglePrgm();

			if (flg.getPrgm() == 1) {
				dsp.inputProgramStep(prg.getCurrentIndex(), (Step) prg.get());
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
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
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
			stp.setOp(Op.OP_PRGM);
		} else if (flg.getG() == 1) {
			flg.toggleGto();
			flg.toggleG();
			stp.setOp(Op.OP_GTO);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.rotateDown();
			stp.setOp(Op.OP_R_DOWN);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey34() throws CalculatorException {
		if (flg.getF() == 1) {
			fin.clear();
			stp.setOp(Op.OP_FIN);
			dsp.setStatus(Display.STATUS_READY);
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.swapXY();
			stp.setOp(Op.OP_SWAP_XY);
			dsp.setStatus(Display.STATUS_OUTPUT);
		}
	}

	protected void doKey35() throws CalculatorException {
		if (flg.getF() == 1) {
			mem.clear();
			fin.clear();
			stk.clear();

			flg.toggleF();
			stp.setOp(Op.OP_REG);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.set(0, ZERO);
			stp.setOp(Op.OP_CLX);
			dsp.setStatus(Display.STATUS_READY);
		}
	}

	protected void doKey36() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
			stp.setOp(Op.OP_PREFIX);
		} else if (flg.getG() == 1) {

			this.setX(stk.getLastX());

			stp.setOp(Op.OP_LASTX);
			dsp.setStatus(Display.STATUS_OUTPUT);
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.put(dsp.getValue());
			stp.setOp(Op.OP_ENTER);
			dsp.setStatus(Display.STATUS_READY);
		}
	}

	protected void doKey40() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			flg.toggleG();
		} else if (flg.getSto() > 0) {
			stp.setOp(Op.OP_STO_ADD);
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			stk.add();
			stp.setOp(Op.OP_ADD);
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
			stp.setOp(Op.OP_NULL);
		} else {
			this.clearFgsr();
			flg.toggleF();
			stp.setOp(Op.OP_F);
		}
	}

	protected void doKey43() throws CalculatorException {

		if (flg.getG() > 0) {
			this.clearFgsr();
			stp.setOp(Op.OP_NULL);
		} else {
			this.clearFgsr();
			flg.toggleG();
			stp.setOp(Op.OP_G);
		}
	}

	protected void doKey44() throws CalculatorException {

		if (flg.getSto() > 0) {
			this.clearFgsr();
			stp.setOp(Op.OP_NULL);
		} else {
			this.clearFgsr();
			flg.toggleSto();
			stp.setOp(Op.OP_STO);
		}
	}

	protected void doKey45() throws CalculatorException {

		if (flg.getRcl() > 0) {
			this.clearFgsr();
			stp.setOp(Op.OP_NULL);
		} else {
			this.clearFgsr();
			flg.toggleRcl();
			stp.setOp(Op.OP_RCL);
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
			stp.setOp(Op.OP_STAT_S);
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
			stp.setOp(Op.OP_DOT);
		}
	}

	protected void doKey49() throws CalculatorException {
		if (flg.getF() == 1) {
			flg.toggleF();
		} else if (flg.getG() == 1) {
			mem.subStats(stk.peek(), stk.get(1));
			stk.setLastX();
			stk.set(0, mem.getR1());
			flg.toggleG();
			stp.setOp(Op.OP_SIGMA_MINUS);
			dsp.setStatus(Display.STATUS_READY);
		} else if (flg.getSto() > 0) {
			flg.toggleSto();
		} else if (flg.getRcl() > 0) {
			flg.toggleRcl();
		} else {
			mem.sumStats(stk.peek(), stk.get(1));
			stk.setLastX();
			stk.set(0, mem.getR1());
			stp.setOp(Op.OP_SIGMA_PLUS);
			dsp.setStatus(Display.STATUS_READY);
		}
	}
}
