package net.sf.finanx.fx12c.utils;


public class Timer extends Thread {

	private double sec;
	
	public Timer(double sec){
		super();
		this.sec = sec;
	}
	
	public void run(){

		try{
			sleep((int)(sec*1000));
		}
		catch(InterruptedException e){
			
		}
	}

	public void setTime(double sec){
		this.sec = sec;
	}
}
