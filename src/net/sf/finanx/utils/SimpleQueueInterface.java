package net.sf.finanx.utils;

public interface SimpleQueueInterface {

	public boolean isEmpty();
	public boolean isFull();
	public void enqueue(double x);
	public double dequeue();
	public double[] getArray();
	public void setArray(double[] a);
	
}
