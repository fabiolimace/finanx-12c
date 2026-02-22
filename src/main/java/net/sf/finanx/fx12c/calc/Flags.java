package net.sf.finanx.fx12c.calc;

import java.util.HashMap;
import java.util.Map;

public class Flags {

	protected static final Map<String, Integer> flags = new HashMap<String, Integer>();
	{
		// @formatter:off
		flags.put("f",    0);	// 0: F off; 1: F on. Used to execute yellow functions.
		flags.put("g",    0);	// 0: G off; 1: G on. Used to execute blue functions.
		flags.put("sto",  0);	// 0: STO off; 1: STO on. Used to store a value into a memory register.
		flags.put("rcl",  0);	// 0: RCL off, 1: RCL on. Used to recall a value from a memory register.
		flags.put("gto",  0);	// 0: GTO off; 1: GTO on. Used to change position in program memory.
		flags.put("dmy",  0);	// 0: Date format set to mm/dd/yyyy; 1: Date format set to dd/mm/yyyy.
		flags.put("beg",  0);	// 0: Payment type set to END; 1: Payment type set to BEGIN.
		flags.put("c",    0);	// 0: Continuous calculations Off; 1: Continuous calculations On.
		flags.put("on",   0);	// 0: On button not pressed; 1: On button pressed.
		flags.put("brc",  0);	// 0: Braces off; 1: Braces on. Used in ALG mode only.
		flags.put("alg",  0);	// 0: RPN mode; 1: ALG mode. Used to alternate between RPN and Arithmetic modes.
		flags.put("prgm", 0);	// 0: Program mode off; 1: Program mode on. Used to switch between program edition mode and instructions typing mode.
		flags.put("run",  0);	// 0: User can type instructions; 1: Calculator is running a program.
		flags.put("wild", 0);	// 0: Something Off; 1: Something On. Wild card flag. Has different meanings depending the situation.
		// @formatter:on
	}

	private String displayStr;

	public Flags() {
	}

	protected int getFlag(String key) {
		return flags.get(key);
	}

	protected void setFlag(String key, int value) {
		flags.put(key, value);
	}

	public void clear() {
		for (String key : flags.keySet()) {
			flags.put(key, 0);
		}
	}

	public void toggle(String flg) {
		if (this.getFlag(flg) > 0)
			this.setFlag(flg, 0);
		else
			this.setFlag(flg, 1);
	}

	public void toggleF() {
		this.toggle("f");
	}

	public void toggleG() {
		this.toggle("g");
	}

	public void setSto(int sto) {
		this.setFlag("sto", sto);
	}

	public void toggleSto() {
		this.toggle("sto");
	}

	public void setRcl(int rcl) {
		this.setFlag("rcl", rcl);
	}

	public void toggleRcl() {
		this.toggle("rcl");
	}

	public void setGto(int gto) {
		this.setFlag("gto", gto);
	}

	public void toggleGto() {
		this.toggle("gto");
	}

	public void setDmy(int dmy) {
		this.setFlag("dmy", dmy);
	}

	public void setBegin(int beg) {
		this.setFlag("beg", beg);
	}

	public void toggleC() {
		this.toggle("c");
	}

	public void toggleOn() {
		this.toggle("on");
	}

	public void setWild(int wild) {
		this.setFlag("wild", wild);
	}

	public void toggleWild() {
		this.toggle("wild");
	}

	public void setBrc(int brc) {
		this.setFlag("brc", brc);
	}

	public void setAlg(int alg) {
		this.setFlag("alg", alg);
	}

	public void setRun(int run) {
		this.setFlag("run", run);
	}

	public void toggleRun() {
		this.toggle("run");
	}

	public void setPrgm(int prgm) {
		this.setFlag("prgm", prgm);
	}

	public void togglePrgm() {
		this.toggle("prgm");
	}

	public int getF() {
		return this.getFlag("f");
	}

	public int getG() {
		return this.getFlag("g");
	}

	public int getSto() {
		return this.getFlag("sto");
	}

	public int getRcl() {
		return this.getFlag("rcl");
	}

	public int getGto() {
		return this.getFlag("gto");
	}

	public int getDmy() {
		return this.getFlag("dmy");
	}

	public int getBegin() {
		return this.getFlag("beg");
	}

	public int getC() {
		return this.getFlag("c");
	}

	public int getOn() {
		return this.getFlag("on");
	}

	public int getWild() {
		return this.getFlag("wild");
	}

	public int getBrc() {
		return this.getFlag("brc");
	}

	public int getAlg() {
		return this.getFlag("alg");
	}

	public int getRun() {
		return this.getFlag("run");
	}

	public int getPrgm() {
		return this.getFlag("prgm");
	}

	public String getDisplayStr() {

		this.displayStr = (this.getAlg() == 1 ? "       ALG  " : "  RPN       ");
		this.displayStr += (this.getBrc() == 1 ? "( )  " : "     ");
		this.displayStr += (this.getF() == 1 ? "f  " : "   ");
		this.displayStr += (this.getG() == 1 ? "g  " : "   ");
		this.displayStr += (this.getBegin() == 1 ? "BEGIN  " : "       ");
		this.displayStr += (this.getDmy() == 1 ? "D.MY  " : "      ");
		this.displayStr += (this.getC() == 1 ? "C  " : "   ");
		this.displayStr += (this.getPrgm() == 1 ? "PRGM  " : "      ");

		return this.displayStr;
	}

}
