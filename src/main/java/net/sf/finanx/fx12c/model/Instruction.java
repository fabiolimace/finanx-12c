package net.sf.finanx.fx12c.model;

public class Instruction {	
	

	protected Step stp;
	protected Stack stk;


	public Instruction() {
		super();
		this.stp = new Step();
		this.stk = new Stack();
	}
	
	public Instruction(Step stp, Stack stk) {
		this.stp = new Step(stp);
		this.stk = new Stack(stk);
	}
	
	protected void init() {/* Does Nothing */}
	
	public void setStep(Step stp){
		this.stp.setModifier(stp.getModifier());
		this.stp.setKey(stp.getKey());
		this.stp.setComplement(stp.getComplement());
	}
	
	public void setStack(Stack stk){
		this.stk = new Stack(stk);
	}	
	
	public Step getStep(){
		return this.stp;
	}
	
	public Stack getStack(){
		return this.stk;
	}
	
	public void clear() {
		this.stp.clear();
		this.stk.clear();
	}
	
	public void print() {
		System.out.println(this);
	}

	public String toString() {
		String rtn = "==[INSTRUCTION]=====\n";
		
		rtn += " - Instr: ";
		rtn += this.stp.getModifier()+", ";
		rtn += this.stp.getKey()+", ";
		rtn += this.stp.getModifier()+", ";
		rtn += this.stk.get(0)+"\n";
		
		// - Instr: -1, 36, -1 (Enter)
		return rtn;
	}
	
}
