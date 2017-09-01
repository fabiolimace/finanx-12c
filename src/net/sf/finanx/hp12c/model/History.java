package net.sf.finanx.hp12c.model;

public class History {

	protected Instruction instr[];
	protected Instruction swp;
	
	public History(){
		this.instr = new Instruction[100];
	}
	
	public History(int size){
		this.instr = new Instruction[size];
	}
	
	public History(Instruction hst[]){
		this.instr = new Instruction[hst.length];
		for(int i=0; i<hst.length; i++)
			this.instr[i] = (Instruction) hst[i];
	}

	public void init(){/* Does nothing */}
	
	public Instruction get(int index){
		return this.instr[index];
	}
	
	public void set(int index, Instruction instr){
		this.instr[index] = instr;
	}
	
	public void shiftUp()
	{
		for (int i=instr.length-1; i>0; i--)
		{
			this.instr[i]=this.instr[i-1];
		}
	}	
	
	public void shiftDown()
	{
		for (int i=0; i<instr.length-1; i++)
		{
			this.instr[i]=this.instr[i+1];
		}
	}
	
	public void put(Instruction instr)
	{		
		this.shiftUp();
		this.instr[0] = instr;
	}
	
	public Instruction pop(){
		
		this.swp = instr[0];
		this.shiftDown();
		return this.swp;
	}

	public Instruction top(){
		return this.instr[0];
	}
	
	public int getSize(){
		return this.instr.length;
	}

	public void print(){
		System.out.println(this);
	}
	
	public String toString(){
		String str = "---Program Memory---\n";
		for(int i=0; i<instr.length; i++){
			str += " - H"+i+": "+instr[i].getStep().getModifier()+", "+instr[i].getStep().getKey()+", "+instr[i].getStep().getComplement()+", "+instr[i].getStack().get(0)+"\n";
		}
		return str;
	}
	
	public void clear(){
		for(int i=0; i<instr.length; i++){
			instr[0].clear();
		}
	}
		
}