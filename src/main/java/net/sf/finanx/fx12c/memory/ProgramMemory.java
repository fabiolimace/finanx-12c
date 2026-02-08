package net.sf.finanx.fx12c.memory;

import net.sf.finanx.fx12c.calc.Key;
import net.sf.finanx.fx12c.calc.Op;
import net.sf.finanx.fx12c.calc.Step;

public class ProgramMemory {

	protected Step prg[];
	protected int idx;

	public ProgramMemory(int size) {

		this.prg = new Step[size];

		this.idx = 0;

		this.init();
	}

	public ProgramMemory(Step[] prg) {

		this.prg = prg;

		this.idx = 0;

		this.init();
	}

	public ProgramMemory() {
		this(1000);
	}

	public void init() {

		for (int i = 0; i < this.prg.length; i++)
			this.prg[i] = new Step();
	}

	public int getSize() {
		return prg.length;
	}

	public int getUsedSteps() {
		int i = 0;
		for (i = 1; i < prg.length; i++) {
			if (prg[i].getOp() == Op.OP_NULL)
				break;
		}
		return i - 1;
	}

	public int getAvailableSteps() {
		return getSize() - getUsedSteps() - 1;
	}

	public void set(int idx, Step stp) {
		if (idx < prg.length) {
			this.prg[idx] = stp;
		}
	}

	public void set(Step stp) {
		if (idx < prg.length) {
			this.prg[idx] = stp;
		}
	}

	public Step get(int idx) {
		if (idx < prg.length) {
			return prg[idx];
		}
		return null;
	}

	public Step get() {
		if (idx < prg.length) {
			return prg[idx];
		}
		return null;
	}

	public boolean next() {
		if (this.idx < this.prg.length - 1)
			this.idx++;
		else
			return false;

		return true;
	}

	public boolean back() {
		if (this.idx > 0)
			this.idx--;
		else
			return false;

		return true;
	}

	public Key getModifier(int idx) {
		if (idx < prg.length) {
			return this.prg[idx].getOp().getModifier();
		} else {
			return Key.KEY_NULL;
		}
	}

	public Key getKey() {
		if (idx < prg.length) {
			return this.prg[idx].getOp().getKey();
		} else {
			return Key.KEY_NULL;
		}
	}

	public Key getKey(int idx) {
		if (idx < prg.length) {
			return this.prg[idx].getOp().getKey();
		} else {
			return Key.KEY_NULL;
		}
	}

	public int getComplement() {
		if (idx < prg.length) {
			return this.prg[idx].getComplement();
		} else {
			return -1;
		}
	}

	public int getComplement(int idx) {
		if (idx < prg.length) {
			return this.prg[idx].getComplement();
		} else {
			return -1;
		}
	}

	public int getCurrentIndex() {
		return this.idx;
	}

	public void setCurrentIndex(int idx) {
		this.idx = idx;
	}

	public void put(Op combo) {
		this.put(new Step(combo));
	}

	public void put(Step stp) {
		this.idx++;
		if (idx < prg.length) {
			this.prg[idx] = stp;
		}
	}

	public Step[] getArray() {
		return prg;
	}

	public void setArray(Step[] prg) {
		this.prg = prg;
	}

	public void clear() {
		for (int i = 0; i < prg.length; i++) {
			this.prg[i].clear();
		}
		this.idx = 0;
	}

	public void print() {
		System.out.println(this);
	}

	public String toString() {
		String str = "==[PROGRAM MEMORY]==\n";
		for (int i = 0; i < prg.length; i++) {
			str += " - P" + i + ": " + prg[i].getOp().getModifier() + ", " + prg[i].getOp().getKey() + ", "
					+ prg[i].getComplement() + "\n";
		}
		return str;
	}
}