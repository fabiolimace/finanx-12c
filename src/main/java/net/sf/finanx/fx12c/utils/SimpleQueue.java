package net.sf.finanx.utils;

public class SimpleQueue implements SimpleQueueInterface{

	private int beg;
	private int end;
	private int size;
	private int max;
	private double que[];

	public SimpleQueue(int max){
		this.beg = 1;
		this.end = 1;
		this.size= 0;
		this.max = max;
		this.que = new double[max+1];
	}
	
	public boolean isEmpty(){
		if (this.size==0) return true;
		else return false;
	}
	public boolean isFull(){
		if (this.size==this.max) return true;
		else return false;		
	}
	public void enqueue(double x){
		if (!this.isFull()){
			this.que[this.end] = x;
			
			this.end++;
			if (this.end > this.max) this.end = 1;
			
			this.size++;
		}
		else {
			// TODO: OVERFLOW
		}
	}
	public double dequeue(){
		double ret = 0.0;
		if (!this.isFull()){
			ret = this.que[this.beg];
			
			this.beg++;
			if (this.beg > this.max) this.beg = 1;
			
			this.size--;
		}
		else {
			// TODO: ONDERFLOW
		}
		return ret;
	}
	public double[] getArray(){
		return this.que;
	}
	public void setArray(double[] que){
		this.que = que;
	}
	
	/*
	private void adc(){
		this.end++;
		if (this.end > this.max) this.end = 1;
	}
	*/
	
	// Temporary method to test stack
	public void print(){
		for (int i=0; i<=max; i++){
			System.out.println("R"+i+": "+this.que[i]);
		}
		System.out.println();
	}
}
