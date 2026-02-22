package net.sf.finanx.fx12c.memory;

import net.sf.finanx.fx12c.calc.Op;
import net.sf.finanx.fx12c.calc.Step;

public class ProgramMemory {

	public static final int SIZE = 100;

	protected int idx = 0;
	protected Step prg[] = new Step[SIZE];

	public ProgramMemory() {
		this.clear();
	}

	public void set(int idx, Step stp) {
		this.prg[idx] = stp;
	}

	public void set(Step stp) {
		this.prg[idx] = stp;
	}

	public Step get(int idx) {
		return prg[idx];
	}

	public Step get() {
		return prg[idx];
	}

	public boolean next() {
		if (this.idx < SIZE - 1)
			this.idx++;
		return true;
	}

	public boolean back() {
		if (this.idx > 0)
			this.idx--;
		return true;
	}

	public int getCurrentIndex() {
		return this.idx;
	}

	public void setCurrentIndex(int idx) {
		this.idx = idx;
	}

	public void put(Op op) {
		this.put(new Step(op));
	}

	public void put(Step stp) {
		this.idx++;
		if (idx < SIZE) {
			this.prg[idx] = stp;
		}
	}

	public int getUsedSteps() {
		int i = 0;
		for (i = 1; i < SIZE; i++) {
			if (prg[i].getOp() == Op.OP_NULL)
				break;
		}
		return i - 1;
	}

	public void clear() {
		for (int i = 0; i < SIZE; i++) {
			if (this.prg[i] == null) {
				this.prg[i] = new Step();
			} else {
				this.prg[i].clear();
			}
		}
		this.idx = 0;
	}
}