package net.sf.finanx.utils;

public interface SimpleStackInterface {

	public boolean isEmpty();
	public boolean isFull();
	public double pop();
	public double top();
	public void push(double x);
	public double[] getArray();
	public void setArray(double[] stk);
	
}
