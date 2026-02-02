package net.sf.finanx.fx12c.model;

public class ProgramMemory {


	protected Step prg[];
	protected int idx; 
	
	public ProgramMemory(int size){

		this.prg = new Step[size];
		
		this.idx=0;
		
		this.init();
	}
	
	public ProgramMemory(Step[] prg) {
		
		this.prg = prg;
		
		this.idx=0;
		
		this.init();
	}
	
	public ProgramMemory() {
		this(1000);
	}

	public void init() {

		for (int i = 0; i < this.prg.length; i++)
			this.prg[i] = new Step();
	}
	
	public int getSize(){
		return prg.length;
	}
	
	public int getUsedSteps(){
		int i = 0;
		for (i=1; i<prg.length; i++){
			if(prg[i].isUndefined())
				break;
		}
		return i-1;
	}
	
	public int getAvailableSteps(){
		return getSize() - getUsedSteps() - 1;
	}
	
	public void set(int idx, Step stp){
		if(idx<prg.length){ 
			this.prg[idx] = stp; 
		}
	}
	
	public void set(Step stp){
		if(idx<prg.length){ 
			this.prg[idx] = stp;
		}
	}
	
	public Step get(int idx){
		if(idx<prg.length){
			return prg[idx];
		}
		return null;		
	}
	
	public Step get(){
		if(idx<prg.length){
			return prg[idx];
		}
		return null;
	}
	
	public boolean next(){
		if (this.idx < this.prg.length-1)
			this.idx++;
		else 
			return false;
		
		return true;
	}
	
	public boolean back(){
		if (this.idx > 0)
			this.idx--;
		else 
			return false;
		
		return true;
	}
	
	public void setModifier(int mod){
		if(idx<prg.length){
			this.prg[idx].setModifier(mod);
		}
	}
	
	public void setModifier(int idx, int mod){
		if(idx<prg.length){
			this.prg[idx].setModifier(mod);
		}
	}
	
	public void setKey(int key){
		if(idx<prg.length){
			this.prg[idx].setKey(key);
		}
	}	
	
	public void setKey(int idx, int key){
		if(idx<prg.length){
			this.prg[idx].setKey(key);
		}
	}	
	
	public void setComplement(int cpm){
		if(idx<prg.length){
			this.prg[idx].setComplement(cpm);
		}
	}	
	
	public void setComplement(int idx, int cpm){
		if(idx<prg.length){
			this.prg[idx].setComplement(cpm);
		}
	}	
	
	public int getModifier(){
		if(idx<prg.length){ return this.prg[idx].getModifier(); }
		else{ return -1; }
	}
	
	public int getModifier(int idx){
		if(idx<prg.length){ return this.prg[idx].getModifier(); }
		else{ return -1; }
	}
	
	public int getKey(){
		if(idx<prg.length){ return this.prg[idx].getKey(); }
		else{ return -1; }
	}
	
	public int getKey(int idx){
		if(idx<prg.length){ return this.prg[idx].getKey(); }
		else{ return -1; }
	}
	
	public int getComplement(){
		if(idx<prg.length){ return this.prg[idx].getComplement(); }
		else{ return -1; }
	}
	
	public int getComplement(int idx){
		if(idx<prg.length){ return this.prg[idx].getComplement(); }
		else{ return -1; }
	}
	
	public int getCurrentIndex(){ return this.idx; }	
	public void setCurrentIndex(int idx){	this.idx=idx; }
	
	public void put(Step stp){
		this.idx++;
		if(idx<prg.length){ 
			this.prg[idx] = stp; 
		}
	}
	
	public Step[] getArray(){ return prg; }	
	public void setArray(Step[] prg){ this.prg=prg; }
	
	public void clear(){
		for(int i=0; i<prg.length; i++){
			this.prg[i].clear();
		}
		this.idx=0;
	}
	
	public void print(){
		System.out.println(this);
	}
	
	public String toString(){
		String str = "==[PROGRAM MEMORY]==\n";
		for(int i=0; i<prg.length; i++){
			str += " - P"+i+": "+prg[i].getModifier()+", "+prg[i].getKey()+", "+prg[i].getComplement()+"\n";
		}
		return str;
	}
}