package net.sf.finanx.fx12c.memory;

import static net.sf.finanx.fx12c.math.Number.*;

import net.sf.finanx.fx12c.calc.CalculatorException;
import net.sf.finanx.fx12c.calc.Error;
import net.sf.finanx.fx12c.math.Number;

public class GeneralMemory {

	// IMPORTANT NOTE:
	// The registers from R0 to R6 are reserved
	// to store statistic data
	
	protected Number mem[][];
	protected int cur;
	
	public GeneralMemory(int size){
		mem = new Number[size][2];

		for(int i=0; i<mem.length; i++){
			this.mem[i][0]=Number.ZERO;
			this.mem[i][1]=Number.ONE;
		}
		
		this.cur=0;
		
		this.init();
	}
	
	public GeneralMemory(Number[][] mem) {
		this.mem = mem;
		this.init();
	}
	
	
	public GeneralMemory(){
		this(20);
	}

	public void init() { /* Does nothing */ }
	
	public int getSize(){
		return mem.length;
	}
	
	public int getUsedRegisters(){
		int cnt = 0;
		for(int i=0; i<mem.length; i++){
			if(!mem[i][0].equalTo(Number.ZERO)){
				cnt++;
			}
		}
		return cnt;
	}
	
	public int getAvailableRegisters(){
		return getSize() - getUsedRegisters();
	}
	
	public void set(int idx, Number value){
		if(idx<mem.length){ this.mem[idx][0] = value; }
	}
	
	public Number get(int idx){
		Number rtn=Number.ZERO;
		if(idx<mem.length){
		rtn = this.mem[idx][0];
		}
		return rtn;
	}
	
	public Number[] getWithTimes(int idx){
		Number rtn[]={Number.ZERO,Number.ZERO};
		if(idx<mem.length){
			rtn[0] = this.mem[idx][0];
			rtn[1] = this.mem[idx][1];
			}
			return rtn;
	}
	
	public void setTimes(int idx, Number times){
		if(idx<mem.length){
		this.mem[idx][1] = times;
		}
	}	
	
	public Number getTimes(int idx){
		if(idx<mem.length){	return this.mem[idx][1]; }
		else{ return Number.ONE; }
	}
	
	public void setWithTimes(int idx, Number value, Number times){
		if((idx<mem.length)&&(times.greaterThan(Number.ZERO))&&(times.lessThan(Number.HUNDRED))){
			this.mem[idx][0] = value;
			this.mem[idx][1] = times;
		}
	}
	
	public void setWithTimes(int idx, Number[] a){
		this.setWithTimes(idx, a[0], a[1]);
	}
	
	public int getCurrentIndex(){ return this.cur; }	
	
	public Number[][] getArray(){ return mem; }	
	public void setArray(Number[][] mem){ this.mem=mem; }
	
	// Inserts a new value in the memory
	public void put(Number value, Number times){
		this.setWithTimes(++this.cur, value, times);
	}
	
	// Inserts a new value in the memory
	// a[0]: value
	// a[1]: times
	public void put(Number[] a){
		this.setWithTimes(++this.cur, a);
	}
	
	public void clear(){
		for(int i=0; i<mem.length; i++){
			mem[i][0]=Number.ZERO;
			mem[i][1]=Number.ONE;
		}
		this.cur=0;
	}
	
	public void print(){
		System.out.println(this);
	}
	
	public String toString(){
		
		String str = "==[GENERAL MEMORY]==\n";
		for(int i=0; i<mem.length; i++){
			str += " - M"+i+": "+mem[i][0]+" x "+mem[i][1]+"\n";
		}
		return str;
	}
	
	// Increase statistical registers
	public void sumStats(Number x, Number y){
		this.mem[1][0]=this.mem[1][0].add(Number.ONE);     this.mem[1][1]=Number.ONE;
		this.mem[2][0]=this.mem[2][0].add(x);              this.mem[2][1]=Number.ONE;
		this.mem[3][0]=this.mem[3][0].add(x.multiply(x));  this.mem[3][1]=Number.ONE;
		this.mem[4][0]=this.mem[4][0].add(y);              this.mem[4][1]=Number.ONE;
		this.mem[5][0]=this.mem[5][0].add(y.multiply(y));  this.mem[5][1]=Number.ONE;
		this.mem[6][0]=this.mem[6][0].add(x.multiply(y));  this.mem[6][1]=Number.ONE;
	}
	
	// Decrease statistical registers
	public void subStats(Number x, Number y){
		this.mem[1][0]=this.mem[1][0].subtract(Number.ONE);     this.mem[1][1]=Number.ONE;
		this.mem[2][0]=this.mem[2][0].subtract(x);              this.mem[2][1]=Number.ONE;
		this.mem[3][0]=this.mem[3][0].subtract(x.multiply(x));  this.mem[3][1]=Number.ONE;
		this.mem[4][0]=this.mem[4][0].subtract(y);              this.mem[4][1]=Number.ONE;
		this.mem[5][0]=this.mem[5][0].subtract(y.multiply(y));  this.mem[5][1]=Number.ONE;
		this.mem[6][0]=this.mem[6][0].subtract(x.multiply(y));  this.mem[6][1]=Number.ONE;
	}
	
	public void setR1(Number R1){ this.mem[1][0]=R1; this.mem[1][1]=Number.ONE; }
	public void setR2(Number R2){ this.mem[2][0]=R2; this.mem[2][1]=Number.ONE; }
	public void setR3(Number R3){ this.mem[3][0]=R3; this.mem[3][1]=Number.ONE; }
	public void setR4(Number R4){ this.mem[4][0]=R4; this.mem[4][1]=Number.ONE; }
	public void setR5(Number R5){ this.mem[5][0]=R5; this.mem[5][1]=Number.ONE; }
	public void setR6(Number R6){ this.mem[6][0]=R6; this.mem[6][1]=Number.ONE; }
	
	public Number getR1(){ return this.mem[1][0]; }
	public Number getR2(){ return this.mem[2][0]; }
	public Number getR3(){ return this.mem[3][0]; }
	public Number getR4(){ return this.mem[4][0]; }
	public Number getR5(){ return this.mem[5][0]; }
	public Number getR6(){ return this.mem[6][0]; }

	// Clear statistical registers
	public void clearStats(){
		this.mem[1][0]=Number.ZERO;  this.mem[1][1]=Number.ONE;
		this.mem[2][0]=Number.ZERO;  this.mem[2][1]=Number.ONE;
		this.mem[3][0]=Number.ZERO;  this.mem[3][1]=Number.ONE;
		this.mem[4][0]=Number.ZERO;  this.mem[4][1]=Number.ONE;
		this.mem[5][0]=Number.ZERO;  this.mem[5][1]=Number.ONE;
		this.mem[6][0]=Number.ZERO;  this.mem[6][1]=Number.ONE;
	}
	
	public void printStatisticData(){

		String str = "-----Statistic------\n";
		for(int i=1; i<7; i++){
			str += " - Mem"+i+": "+mem[i][0]+"  x  "+mem[i][1]+"\n";
		}
		
		System.out.println(str);

	}

	/**
	 * Arithmetic average or just Mean (HP12C formula)
	 * 
	 * Calculates the means (arithmetic averages) of the x-values ( x ) and of the y-values ( y )
	 * 
	 * Key: [g][ẍ]
	 *  
	 * @return
	 */
	public Number[] mean(){
		Number ans[] = new Number[2];
		
		Number n = getR1();
		Number xSum= getR2();
		Number ySum = getR4();
		
		if (n.isZero())
			throw new CalculatorException(Error.ERROR_STAT,
			"Mean of empty list of values");
		
		ans[0] = xSum.divide(n); // mean of x values
		ans[1] = ySum.divide(n); // mean of x values
		
		return ans;
	}
	
	/**
	 * Weighted Average (HP12C Formula)
	 * 
	 * Calculate the weighted mean
	 * 
	 * Key: [g][ẅ]
	 * 
	 * @param xySum
	 * @param wSum
	 * @return
	 * @throws CalculatorException
	 */
	public Number weightedMean(){
		
		Number n = getR1();
		Number xSum = getR2();
		Number xySum = getR6();
		
		if (n.isZero())
			throw new CalculatorException(Error.ERROR_STAT,
			"Weighted mean of empty list of values");
		
		if (xSum.isZero())
			throw new CalculatorException(Error.ERROR_STAT,
			"Weight sum in weighted average is ZERO causing division by ZERO");
		
		return weightedMean(xySum, xSum); // weighted mean of x values
	}
	
	private Number weightedMean(Number xySum, Number wSum) throws CalculatorException{
		
		if (wSum.isZero())
			throw new CalculatorException(Error.ERROR_MATH,
			"Weight sum in weighted average is ZERO causing division by ZERO");
		
		return xySum.divide(wSum);
	}
	
	/**
	 * Standard deviation (HP12C formula)
	 * 
	 * Calculates the standard deviation of the x-values (sx) and of the y-values (sy)
	 * 
	 * Key: [g][s]
	 * 
	 * @return
	 */
	public Number[] standardDeviation(){
		Number ans[] = new Number[2];
		
		Number n = getR1();
		Number xSum= getR2();
		Number x2Sum = getR3();
		Number ySum= getR4();
		Number y2Sum = getR5();
		
		if (n.isZero())
			throw new CalculatorException(Error.ERROR_STAT,
			"Mean of empty list of values");
		
		ans[0] = standardDeviation(xSum, x2Sum, n);
		ans[1] = standardDeviation(ySum, y2Sum, n);
		
		return ans;
	}
	
	// TODO: Refactor
	private Number standardDeviation(Number _sum, Number _sqrSum, Number _count) throws CalculatorException{
		
		double sum = _sum.d();
		double sqr = _sqrSum.d();
		double count = _count.d();

		double p = count * sqr - Math.pow(sum, 2);
		double q = count * (count - 1);
			
		return n(Math.pow((p / q), 0.5));
	}
	
	public Number[] xLinearEstimation(Number value){
		Number ans[] = new Number[2];
		
		Number n = getR1();
		Number xSum= getR2();
		Number x2Sum = getR3();
		Number ySum= getR4();
		Number y2Sum = getR5();
		Number xySum = getR6();
		
		if (n.isZero())
			throw new CalculatorException(Error.ERROR_STAT,
			"Mean of empty list of values");
		
		ans[0] = xLinearEstimation(xSum, ySum, xySum, x2Sum, n, value);
		ans[1] = rLinearEstimation(xSum, ySum, xySum, x2Sum, y2Sum, n);
		
		return ans;
	}
	
	// TODO: Refactor
	private Number xLinearEstimation(Number _xSum, Number _ySum, Number _xySum, Number _x2Sum, Number _count, Number _yValue) throws CalculatorException{
		
		
		double xSum = _xSum.d();
		double ySum = _ySum.d();
		double xySum = _xySum.d();
		double x2Sum = _x2Sum.d();
		double count = _count.d();
		double yVal = _yValue.d();
		
		double a, b;
		
		b = (xySum - ( ( xSum * ySum ) / count ) ) / ( x2Sum - ( Math.pow(xSum,2.0) / count ) );
		
		a = ( ySum / count ) - ( b * ( xSum / count ) );
		
		return n( (yVal - a) / b );
	}

	public Number[] yLinearEstimation(Number value){
		Number ans[] = new Number[2];
		
		Number n = getR1();
		Number xSum= getR2();
		Number x2Sum = getR3();
		Number ySum= getR4();
		Number y2Sum = getR5();
		Number xySum = getR6();
		
		if (n.isZero())
			throw new CalculatorException(Error.ERROR_STAT,
			"Mean of empty list of values");
		
		ans[0] = yLinearEstimation(xSum, ySum, xySum, x2Sum, n, value);
		ans[1] = rLinearEstimation(xSum, ySum, xySum, x2Sum, y2Sum, n);
		
		return ans;
	}
	
	// TODO: Refactor
	private Number yLinearEstimation(Number _xSum, Number _ySum, Number _xySum, Number _x2Sum, Number _count, Number _xValue) throws CalculatorException{
		
		double xSum = _xSum.d();
		double ySum = _ySum.d();
		double xySum = _xySum.d();
		double x2Sum = _x2Sum.d();
		double count = _count.d();
		double xVal = _xValue.d();
		
		double a, b;
		
		b = (xySum - ( ( xSum * ySum ) / count ) ) / ( x2Sum - ( Math.pow(xSum,2.0) / count ) );
		
		a = ( ySum / count ) - ( b * ( xSum / count ) );

		return n(a + (b * xVal));
	}

	// TODO: Refactor
	private Number rLinearEstimation(Number _xSum, Number _ySum, Number _xySum, Number _x2Sum, Number _y2Sum, Number _count) throws CalculatorException{
		
		double xSum = _xSum.d();
		double ySum = _ySum.d();
		double xySum = _xySum.d();
		double x2Sum = _x2Sum.d();
		double y2Sum = _y2Sum.d();
		double count = _count.d();
	
		double p, q1, q2;
		
		p  = Math.abs( xySum - ( (xSum * ySum) / count) );
		
		q1 = Math.abs( x2Sum - ( Math.pow(xSum, 2.0) / count ));
		
		q2 = Math.abs( y2Sum - ( Math.pow(ySum, 2.0) / count ));
		
		return n(p / Math.sqrt(q1 * q2));
	}
}
