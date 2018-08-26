package net.sf.finanx.hp12c.model;

import static net.sf.finanx.math.Number.*;

import java.util.Calendar;

import net.sf.finanx.hp12c.controller.CalculatorException;
import net.sf.finanx.hp12c.controller.Error;
import net.sf.finanx.math.Number;

public class Stack {
	
	public static final Number MAX_MAGNITUDE = n(9.999999999).multiply(n(10).pow(n(99)));
	public static final Number MIN_MAGNITUDE = n(10E-99);

	protected Number stk[];
	
	protected Number swp;
	private Number lastTop;
	private Number lastBottom;
	
	private boolean dmy = false;
	
	public Stack() {
		this(4);
	}
	
	public Stack(int size) {
		this.stk = new Number[size];
		clear();
		init();
	}
	
	public Stack(Number[] stk) {
		this.stk = new Number[stk.length];
		setArray(stk);
		this.init();
	}
	
	public Stack(Stack stk) {
		this(stk.getArray());
		this.lastTop = stk.getLastTop();
	}
	
	public void init() {
		this.lastTop = ZERO;
		this.lastBottom = ZERO;
	}
	
	public Number get(int idx)
	{
		return this.stk[idx];	
	}
	
	/**
	 * Returns the same number if it is between minimum to maximum magnitudes. 
	 *  
	 * If the number is too big, an exception is raised to indicate overflow.
	 * If the number is too small, ZERO is returned and no exception is raised.
	 * 
	 * @param number the number to be verified
	 * @return the value fixed, if its the case
	 * @throws CalculatorException
	 */
	public static Number fitMagnitude(Number number) throws CalculatorException {	
		
		if(number.abs().greaterThanOrEqualTo(MAX_MAGNITUDE)) {
			/*
			If a calculation results in a number whose magnitude is greater than 9.999999999 × 10E99, 
			the calculation is halted and the calculator displays 9.999999 99 (if the number is positive) 
			or –9.999999 99 (if the number is negative).
			*/
			throw new CalculatorException(Error.ERROR_MAG, "Number larger than or equal to " + MAX_MAGNITUDE.doubleValue());
		} else if (number.abs().lt(MIN_MAGNITUDE)){
			/*
			If a calculation results in a number whose magnitude is less than 10E–99 , the
			calculation is not halted, but the value 0 is used for that number in subsequent
			calculations.
			*/
			return ZERO;
		}
		
		return number;

	}

	public void set(int idx, Number val) {
		try {
			this.stk[idx] = fitMagnitude(val);
		} catch (CalculatorException e) {
			// Magnitude error
			if (e.getError().equals(Error.ERROR_MAG)) {
				this.stk[idx] = (val.isNegative()? MAX_MAGNITUDE.negate() : MAX_MAGNITUDE);
			}
		}
	}
	
	public void put(Number val)
	{		
		this.shiftDown();
		set(0, val);
	}
	
	public Number[] getArray()
	{
		return this.stk;
	}
	
	public void setArray(Number[] stk)
	{
		for(int i=0; i<stk.length; i++)
			set(i, stk[i]);
	}
		
	public Number pop(){
		this.swp = stk[0];
		this.shiftUp();
		return this.swp;
	}

	public Number top(){
		return this.stk[0];
	}
	
	public Number bottom(){
		return this.stk[getSize()-1];
	}

	public int getSize(){
		return stk.length;
	}
	
	public void clear()
	{
		for(int i=0; i<this.stk.length; i++)
			this.stk[i] = ZERO;
	}
	
	public void shiftDown()
	{
		setLastBottom();
		for (int i=stk.length-1; i>0; i--)
		{
			this.stk[i]=this.stk[i-1];
		}
	}	
	
	public void shiftUp()
	{
		for (int i=0; i<stk.length-1; i++)
		{
			this.stk[i]=this.stk[i+1];
		}
	}
	
	public void rollUp()
	{
		this.swp = this.stk[stk.length-1];
		this.shiftDown();
		this.stk[0] = this.swp;
	}
	
	public void rollDown()
	{
		this.swp = this.stk[0];
		this.shiftUp();
		this.stk[stk.length-1] = this.swp;
	}
	
	public void print(){
		System.out.println(this);
	}
	
	public String toString(){
		String str = "==[STACK]===========\n";
		for (int i=0; i<this.stk.length; i++){
			str += " - S"+i+": "+this.stk[i]+"\n";
		}
		return str;
	}
	
	public void swapTopPair()
	{	
		this.swp = this.stk[1];
		this.stk[1] = this.stk[0];
		this.stk[0] = this.swp;		
	}
	
	public void lowerTopPair()
	{
		if(this.stk[0].greaterThan(this.stk[1]))
	    {
	        this.swp = this.stk[1];
	        this.stk[1] = this.stk[0];
	        this.stk[0] = this.swp;
	    }
	}

	public void setLastTop(Number top){
		this.lastTop = top;
	}
	
	public void setLastTop(){
		this.lastTop = this.stk[0];
	}

	public Number getLastTop(){
		return this.lastTop;
	}
	
	public void clearLastTop(){
		this.lastTop = ZERO;
	}
	
	public void setLastBottom(Number bottom){
		this.lastBottom = bottom;
	}
	
	public void setLastBottom(){
		this.lastBottom = this.stk[getSize()-1];
	}

	public Number getLastBottom(){
		return this.lastBottom;
	}
	
	public void clearLastBottom(){
		this.lastBottom = ZERO;
	}

	public boolean isDmy() {
		return dmy;
	}

	public void setDmy(boolean dmy) {
		this.dmy = dmy;
	}
	
	/**
	 * Addition
	 * 
	 * Calculates the sum of two numbers x and y.
	 * 
	 * Key: [+]
	 * 
	 * @throws CalculatorException
	 */
	public void add() throws CalculatorException {
		Number x = pop();
		Number y = pop();
		put(y.add(x));
		setLastTop(x);
	}
	
	/**
	 * Subtraction
	 * 
	 * Calculates the subtraction of two numbers x and y (y minus x).
	 * 
	 * Key: [-]
	 * 
	 * @throws CalculatorException
	 */
	public void subtract() throws CalculatorException {
		Number x = pop();
		Number y = pop();
		put(y.subtract(x));
		setLastTop(x);
	}
	
	/**
	 * Multiplication
	 * 
	 * Calculates the multiplication of two numbers x and y.
	 * 
	 * Key: [*]
	 * 
	 * @throws CalculatorException
	 */
	public void multiply() throws CalculatorException {
		Number x = pop();
		Number y = pop();
		put(y.multiply(x));
		setLastTop(x);
	}
	
	/**
	 * Division
	 * 
	 * Calculates the division of two numbers x and y (y divided by x).
	 * 
	 * Key: [/]
	 * 
	 * @throws CalculatorException
	 */
	public void divide() throws CalculatorException {
		Number x = pop();
		Number y = pop();
		if (x.isZero())
			throw new CalculatorException(Error.ERROR_MATH, "Division by ZERO");
		put(y.divide(x));
		setLastTop(x);
	}
	
	/**
	 * Remainder
	 * 
	 * Calculates the remainder of a division of two numbers x and y (y divided by x).
	 * 
	 * Key: None
	 * 
	 * @throws CalculatorException
	 */
	public void remainder() throws CalculatorException {
		Number x = pop();
		Number y = pop();
		put(y.remainder(x));
		setLastTop(x);
	}
	
	/**
	 * Negation or additive inverse
	 * 
	 * Changes sign or negate a number of a number, for example, 2 --> (-2)
	 * 
	 * Key: [CHS]
	 * 
	 * @throws CalculatorException
	 */
	public void negate() throws CalculatorException {
		Number x = pop();
		put(x.negate());
		setLastTop(x);
	}
	
	/**
	 * Squared
	 * 
	 * Calculates the square root of a number x.
	 * 
	 * Key: [g][Root x]
	 * 
	 * @throws CalculatorException
	 */
	public void squared() throws CalculatorException {
		Number x = pop();
		put(x.pow(TWO));
		setLastTop(x);
	}
	
	/**
	 * Square root
	 * 
	 * Calculates the square root of a number x.
	 * 
	 * Key: [g][Root x]
	 * 
	 * @throws CalculatorException
	 */
	public void sqrt() throws CalculatorException {
		Number x = pop();
		put(x.sqrt());
		setLastTop(x);
	}
	
	/**
	 * Reciprocal or multiplicative inverse
	 * 
	 * Calculates the reciprocal of the number displayed in x, for example, 2 --> 1/2
	 * 
	 * Key: [1/x]
	 * 
	 * @throws CalculatorException
	 */
	public void reciprocal() throws CalculatorException {
		Number x = pop();
		put(x.reciprocal());
		setLastTop(x);
	}
	
	/**
	 * Integer part
	 * 
	 * Extracts only the integer portion of a number x.
	 * 
	 * Key: [g][INTG]
	 * 
	 * @throws CalculatorException
	 */
	public void integralPart() throws CalculatorException {
		Number x = pop();
		set(0, x.integralPart());
		setLastTop(x);
	}
	
	/**
	 * Fractional part
	 * 
	 * Extracts only the fractional portion of a number x,
	 * 
	 * Key: [g][FRAC]
	 * 
	 * @throws CalculatorException
	 */
	public void fractionalPart() throws CalculatorException {
		Number x = pop();
		set(0, x.fractionalPart());
		setLastTop(x);
	}
	
	/**
	 * Power function (y^x)
	 * 
	 * Raises the number y to the power of the number x,
	 * 
	 * Key: y^x
	 * 
	 * @throws CalculatorException
	 */
	public void pow() throws CalculatorException {
		Number exponent = pop();
		Number base = pop();
		put(base.pow(exponent));
		setLastTop(exponent);
	}
	
	/**
	 * Natural antilogarithm (e^x)
	 * 
	 * Raises e (Euler Number) to power of the a number x
	 * 
	 * Key: [g][e^x]
	 * 
	 * @throws CalculatorException
	 */
	public void exp() throws CalculatorException {
		Number exponent = pop();
		put(exponent.exp());
		setLastTop(exponent);
	}
	
	/**
	 * Natural Log of X
	 * 
	 * Calculates natural logarithm (base e) of a number x.
	 * 
	 * Key: [g][LN]
	 * 
	 * @throws CalculatorException
	 */
	public void log() throws CalculatorException {
		Number x = pop();
		put(x.log());
		setLastTop(x);
	}
	
	/**
	 * Factorial
	 * 
	 * Calculates factorial of a number x.
	 * 
	 * Key: [g][n!]
	 * 
	 */
	public void factorial() throws CalculatorException {
		Number base = pop();
		put(base.factorial());
		setLastTop(base);
	}
	
	/**
	 * Round
	 * 
	 * Rounds a value (x) to a given precision (prec)
	 * 
	 * Key: [f][RND]
	 * 
	 * @throws CalculatorException
	 */
	public void round(int scale) throws CalculatorException {
		Number x = pop();
		put(x.round(scale));
		setLastTop(x);
	}
	
	/**
	 * Returns the amount corresponding to a percentage of a number.
	 * 
	 * Formula:
	 * ans = (base * rate) / 100
	 */
	public void percent() throws CalculatorException {
		Number rate = pop();
		Number base = pop();
		
		Number ans = ( base.multiply(rate) ).divide(HUNDRED);
		
		set(0, ans);
		setLastTop(rate);
	}
	
	/**
	 * Returns the percent difference between two numbers.
	 * 
	 * Formula:
	 * ans = 100 * ((other - base) / base)
	 */
	public void percentDifference() throws CalculatorException {
		Number other = pop();
		Number base = pop();
		
		Number ans = HUNDRED.multiply( ( ( other.subtract(base) ).divide(base) ) );
		
		set(0, ans);
		setLastTop(other);
	}
	
	/**
	 * Returns what percentage one number is of another.
	 * 
	 * Formula:
	 * ans = 100 * (other / total)
	 */
	public void percentOfTotal() throws CalculatorException {
		Number other = pop();
		Number total = pop();
		
		Number ans = HUNDRED.multiply( other.divide(total) );
		
		set(0, ans);
		setLastTop(other);
	}
	
	/**
	 * Returns a Date object interpreted from a double value.
	 * 
	 * Available date formats:
	 * 
	 * 0 = MM.DDYYYY, 1 = DD.MMYYYY
	 * 
	 * The date format is used to interpret dates inserted as double values.
	 * 
	 * @return The date object
	 * @throws CalculatorException
	 */
	public static Calendar numberToDate(Number number, boolean dmy) throws CalculatorException {

		int m = number.round(0).intValue();
		int d = number.fractionalPart().multiply(HUNDRED).round(0).intValue();
		int y = number.fractionalPart().multiply(HUNDRED).fractionalPart().multiply(n(10000)).round(0).intValue();

		if (dmy) {
			int tmp = d;
			d = m;
			m = tmp;
		}
		
		Calendar calendar = Calendar.getInstance();
		//calendar.setLenient(false);
		
		try{
			calendar.set(y, m-1, d, 0, 0, 0);
		} catch (Exception e) {
			throw new CalculatorException(Error.ERROR_CAL, "Invalid date");
		}
		
		return calendar;
	}
	
	/**
	 * Returns a Date object interpreted from a double value.
	 * 
	 * Available date formats:
	 * 
	 * 0 = MM.DDYYYY, 1 = DD.MMYYYY
	 * 
	 * The date format is used to interpret dates inserted as double values.
	 * 
	 * @return The date object
	 * @throws CalculatorException
	 */
	public static Number dateToNumber(Calendar date, boolean dmy) throws CalculatorException {

		String strNumber = "";
		String day = "";
		String month = "";
		String year = "";
		
		day = String.format("%02d", date.get(Calendar.DAY_OF_MONTH));
		month = String.format("%02d", date.get(Calendar.MONTH) + 1);
		year = String.format("%04d", date.get(Calendar.YEAR));

		if (dmy) {
			strNumber = day + "." + month + "" + year;
		} else {
			strNumber = month + "." + day + "" + year;
		}

		return n(strNumber);
	}
	
	/**
	 * Returns the date that is a given number of days from a given date.
	 * 
	 * @see Stack#numberToDate(Number, Number);
	 * @return Number object corresponding to the obtained date
	 * @throws CalculatorException
	 */
	private static Calendar addDays(Number number, Number days, boolean dmy) throws CalculatorException {
		Calendar calendar = numberToDate(number, dmy);
		calendar.add(Calendar.DAY_OF_MONTH, days.i());
		return calendar;
	}
	
	/**
	 * 
	 * Key: [g][DeltaDYS]
	 * 
	 * @param begDate
	 * @param endDate
	 * @return
	 * @throws CalculatorException
	 */
	private static Number diffDates365(Calendar begDate, Calendar endDate) throws CalculatorException {
		long millisPerDay = 1000 * 60 * 60 * 24;
		long endDays = endDate.getTimeInMillis() / millisPerDay;
		long begDays = begDate.getTimeInMillis() / millisPerDay;
		long diffDays = endDays - begDays;
		return n(diffDays);
	}
	
	/**
	 * 
	 * Key: [g][DeltaDYS]
	 * 
	 * @param begDate
	 * @param endDate
	 * @return
	 * @throws CalculatorException
	 */
	private static Number diffDates360(Calendar begDate, Calendar endDate) throws CalculatorException{
		
		int dd1 = begDate.get(Calendar.DAY_OF_MONTH);
		int mm1 = begDate.get(Calendar.MONTH) + 1;
		int yyyy1 = begDate.get(Calendar.YEAR);
		
		int dd2 = endDate.get(Calendar.DAY_OF_MONTH);
		int mm2 = endDate.get(Calendar.MONTH) + 1;
		int yyyy2 = endDate.get(Calendar.YEAR);
		
		int z1 = 0;
		int z2 = 0;
		
		// HP12C Manual
		if(dd1==31){
			z1=30;
		} else {
			z1 = dd1;
		}

		// HP12C Manual
		if(dd2 == 31 && (dd1 >= 30)) {
			z2 = 30;
		} else if (dd2 == 31 && (dd1 < 30)) {
			z2 = dd2;
		} else if (dd2 < 31) {
			z2 = dd2;
		}
		
		long date1 = yyyy1 * 360 + mm1 * 30 + z1;
		long date2 = yyyy2 * 360 + mm2 * 30 + z2;
				
		return n(date2 - date1);
	}
	
	/**
	 * Calculates the date and day of week that is a given number of days from a given date.
	 * 
	 * Keys: [g][DATE]
	 * 
	 * @see Stack#addDays(Number, Number, Number)
	 * @see Stack#numberToDate(Number, Number);
	 * @see Stack#weekDay(Date);
	 * 
	 * @throws CalculatorException
	 */
	public void addDaysToDate() throws CalculatorException {
		Number days = pop();
		Number number = pop();
		
		Calendar calendar = addDays(number, days, dmy);
		Number dateNumber = dateToNumber(calendar, dmy);
		
		put(dateNumber);
		setLastTop(days);
	}
	
	/**
	 * Calculates the number of days between two given dates.
	 * 
	 * Keys: [g][DATE]
	 * 
	 * @see Stack#addDaysToDate(Number) 
	 * @see Stack#numberToDate(Number, Number);
	 * @see Stack#weekDay(Date);
	 * 
	 * @throws CalculatorException
	 */
	public void diffOfDaysBetweenDates() throws CalculatorException {
		Number x = pop();
		Number y = pop();
		
		Calendar begDate = numberToDate(y, dmy);
		Calendar endDate = numberToDate(x, dmy);
		
		Number diff360 = diffDates360(begDate, endDate);
		Number diff365 = diffDates365(begDate, endDate);
		
		put(diff360);
		put(diff365);
		setLastTop(x);
	}
	
	public Number dayOfWeek() {
		
		Number date = top();
		
		Calendar calendar = numberToDate(date, dmy);
		
		int dow = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		if(dow == 0) dow = 7;
		
		return n(dow);
		
	}
}
