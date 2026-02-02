package net.sf.finanx.fx12c.controller;

public class CalculatorException extends IllegalArgumentException {

	private static final long serialVersionUID = 5767867117308016246L;

	private Error error;
	private String detail;
	
	public CalculatorException(Error error){
		super(error.getMessage());
		this.error = error;
		this.detail = null;
	}
	
	public CalculatorException(Error error, String detail){
		super(error.getMessage());
		this.error = error;
		this.detail = detail;
	}

	public int getCode() {
		return this.error.getCode();
	}
	
	public String getName(){
		return this.error.getName();
	}
	
	public String getMessage(){
		return this.error.getMessage();
	}
	
	public Error getError() {
		return this.error;
	}
	
	public String getDetail(){
		return this.detail;
	}
	
	public void print() {
		System.out.println(this);
	}
	
	public String toString() {
		return this.getError() + (getDetail() != null ? ": " + getDetail() : "");
	}
}
