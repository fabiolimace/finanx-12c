package net.sf.finanx.fx12c.calc;

public class Step {

	private Op op;
	private int complement;

	public Step() {
		this(Op.OP_NULL);
	}

	public Step(Step stp) {
		this(stp.getOp());
	}

	public Step(Op op) {
		this.op = op;
		this.complement = -1;
	}

	public Op getOp() {
		return this.op;
	}

	public int getComplement() {
		return this.complement;
	}

	public void setComplement(int complement) {
		this.complement = complement;
	}

	public void setOp(Op op) {
		this.op = op;
	}

	public void setStep(Step stp) {
		this.op = stp.op;
		this.complement = stp.complement;
	}

	public void clear() {
		this.op = Op.OP_NULL;
		this.complement = -1;
	}
}
