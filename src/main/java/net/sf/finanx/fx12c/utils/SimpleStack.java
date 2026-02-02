package net.sf.finanx.utils;

public class SimpleStack {

	// Variables
	private int top;
	private int max;
	protected double stk[];
	
	// Constructors
	public SimpleStack(){
		this.top = 0;
		this.max = 4;
		this.stk = new double[max+1];	
	}

	public SimpleStack(int max){
		this.top = 0;
		this.max = max;
		this.stk = new double[max+1];
	}
	
	// Checks if the stack is empty
	public boolean isEmpty(){
	
		if (this.top==0) return true;
		else return false;
		
	}
	
	// Checks if the stack is full
	public boolean isFull(){

		if (this.top==max) return true;
		else return false;
		
	}
	
	// Pushes a new value into the stack
	public void push(double x){
		
		if (!this.isFull()) {
			this.stk[++this.top] = x;
		}
		else { 
			this.shiftUp();
			this.stk[this.top] = x;
		}
	}
	
	// Returns the value in the stack's top
	// This method removes the value returned 
	public double pop(){
		
		if(!isEmpty()){
			return this.stk[this.top--];
		}
		else {
			return 0; // TODO
		}
	}
	
	// Returns the value in the stack's top
	// This method keeps the value in the stack
	public double top(){
		
		if(!isEmpty()){
			return this.stk[this.top];
		}
		else {
			return 0; // TODO
		}
	}
	
	public void shiftUp(){
		for (int i=0+1; i<max; i++){
			this.stk[i]=this.stk[i+1];
		}
	}
	
	public void shiftDown(){
		for (int i=max; i>0+1; i--){
			this.stk[i]=this.stk[i-1];
		}
	}


	public void rollUp(){
		this.stk[0]=this.stk[this.top];
		this.shiftUp();
		this.stk[this.top]=this.stk[0];
	}
	
	public void rollDown(){
		this.stk[0]=this.stk[this.top];
		for (int i=max; i>0; i--){
			this.stk[i]=this.stk[i-1];
		}	
	}
	
	public double[] getArray(){
		return this.stk;
	}
	
	public void setArray(double stk[]){
		this.stk = stk;
	}
	
	// Temporary method to test stack
	public void print(){
		for (int i=0; i<=max; i++){
			System.out.println("R"+i+": "+this.stk[i]);
		}
		System.out.println();
	}
}
