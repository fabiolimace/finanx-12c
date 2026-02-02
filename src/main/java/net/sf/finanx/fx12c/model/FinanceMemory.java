package net.sf.finanx.fx12c.model;

import static net.sf.finanx.fx12c.math.Number.*;

import net.sf.finanx.fx12c.controller.CalculatorException;
import net.sf.finanx.fx12c.controller.Error;
import net.sf.finanx.fx12c.math.Number;
import net.sf.finanx.fx12c.utils.Date;

public class FinanceMemory {
	
	protected Number[] fin;
	
	public static final Number YEAR_360 = n(360);
	public static final Number YEAR_365 = n(365);
	
	private boolean begin = false;
	private boolean c = false;
	
	public FinanceMemory(int size) {
		this.fin = new Number[size];
		clear();
		this.init();
	}
	
	public FinanceMemory(Number[] fin) {
		this.fin = fin;
		this.init();
	}
	
	public FinanceMemory(){
		this(5);		
	}
	
	public FinanceMemory(Number n, Number i, Number pv, Number pmt, Number fv){
		this(5);

		this.fin[0] = n;
		this.fin[1] = i;
		this.fin[2] = pv;
		this.fin[3] = pmt;
		this.fin[4] = fv;
	}

	public void init(){ /* Do Nothing */ }
	
	public Number get(int idx){ return this.fin[idx]; }
	public void set(int idx, Number val){ this.fin[idx] = val;}
	
	
	public int getSize(){
		return fin.length;
	}
	
	public void setArray(Number fin[]){
		this.fin = fin;
	}
	
	public Number [] getArray(){		
		return this.fin;
	}
	
	public void clear(){
		for(int i = 0; i < fin.length; i++){
			fin[i] = Number.ZERO;
		}
	}
	
	public void print() {
		System.out.println(this);
	}	
	
	public void setN(Number n){	this.fin[0] = n;}
	public void setI(Number i){	this.fin[1] = i;}
	public void setPv(Number pv){ this.fin[2] = pv;}
	public void setPmt(Number pmt){	this.fin[3] = pmt;}
	public void setFv(Number fv){ this.fin[4] = fv;}
	
	public Number getN(){ return this.fin[0]; }
	public Number getI(){ return this.fin[1]; }
	public Number getPv(){ return this.fin[2]; }
	public Number getPmt(){ return this.fin[3]; }
	public Number getFv(){ return this.fin[4]; }
	
	public boolean isBegin() {
		return begin;
	}

	public void setBegin(boolean begin) {
		this.begin = begin;
	}

	public boolean isC() {
		return c;
	}

	public void setC(boolean c) {
		this.c = c;
	}

	public String toString() {
		
		String str = "==[FINANCE MEMORY]==\n";
		
		str += " - n  : "+this.fin[0] +"\n";
		str += " - i  : "+this.fin[1] +"\n";
		str += " - PV : "+this.fin[2] +"\n";
		str += " - PMT: "+this.fin[3] +"\n";
		str += " - FV : "+this.fin[4] +"\n";
		
		return str;
	}
	
	/**
	 * Simple interest
	 * 
	 * Calculates the simple interest in financial problems.
	 * 
	 * Key: [f][INT]
	 * 
	 * Formula:
	 * ans =  -(pv * i/100.0 * n);
	 * 
	 * @return simple interest
	 * @throws CalculatorException
	 */
	private static Number simpleInterest(Number n, Number i, Number pv) throws CalculatorException{
		return ( pv.multiply( i.divide(HUNDRED) ).multiply(n) ).negate();
	}
	
	/**
	 * Simple interest
	 * 
	 * @see FinanceMemory#simpleInterest(Number, Number, Number)
	 * 
	 */
	public Number[] simpleInterest() throws CalculatorException{
		
		Number _n = getN();
		Number _i = getI();
		Number _pv = getPv();
		
		Number tmp[] = new Number[2];
		tmp[0] = simpleInterest(_n, _i.divide(YEAR_360), _pv);
		tmp[1] = simpleInterest(_n, _i.divide(YEAR_365), _pv);
		
		return tmp;
	}
	
	/**
	 * Compound future value (Traditional formula)
	 * 
	 * Calculates the future value of a financial problem.
	 * 
	 * Key: None
	 * 
	 * @return
	 */
	public Number simpleFutureValue() {
		double _n = getN().d();
		double _i = getI().d();
		double _pv = getPv().d();
		
		return n(simpleFutureValue(_n, _i, _pv));
	}
	
	// TODO: Refactor
	private static double simpleFutureValue(double n, double i, double pv) throws CalculatorException{
		return ( -( pv + pv * (i/100.0) * n ) );			
	}
	
	/**
	 * Compound future value (HP12C formula)
	 * 
	 * Calculates the future value of a financial problem.
	 * 
	 * Key: [PV]
	 * 
	 * @return
	 * @throws CalculatorException
	 */
	public Number futureValue() throws CalculatorException {
		
		double _n = getN().d();
		double _i = getI().d();
		double _pv = getPv().d();
		double _pmt = getPmt().d();
		double _begin = (begin ? 1 : 0);
		double _c = (c ? 1 : 0);
		
		return n(futureValue(_n, _i, _pv, _pmt, _begin, _c));
		
	}
	
	// TODO: Refactor
	private static double futureValue(double n, double i, double pv, double pmt, double beg, double c) throws CalculatorException{

		if (i <= (-100))
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: i <= -100");
		
		double fv    = 0.0;
		double tmp[] = new double[4];
		
		// Changing rate to decimal format
		i = i / 100.0;
		
		// Without odd period
		if(fracPart(n) == 0.0){
			
			tmp[0] = ( 1 + i * (beg));
			tmp[1] = ( 1 - Math.pow( 1 + i, 0 - n )) / i;
			tmp[2] = Math.pow( 1 + i, 0 - n );
			
			fv = -((pv + tmp[0] * pmt * tmp[1]) / tmp[2]) ;
		}
		// With odd period
		else{
			
			// Using simple interest
			if(c==0){
				tmp[0] = ( 1 + ( i * fracPart(n) ) );
			}
			// Using compound interest
			else{
				tmp[0] = Math.pow(1 + i, fracPart(n));
			}
			
			tmp[1] = ( 1 + i * (beg));
			tmp[2] = ( 1 - Math.pow( 1 + i, 0 - intPart(n) )) / i;
			tmp[3] =  Math.pow( 1 + i, 0 - intPart(n) );
			
			fv = -( (pv * tmp[0] + tmp[1] * pmt * tmp[2]) / tmp[3]) ;
		}
		
		return ( fv );
	}
	
	/**
	 * Conpound period (HP12C formula)
	 * 
	 * Calculates number of compounding periods in financial problems.
	 * 
	 * Key: [n]
	 * 
	 * @return
	 * @throws CalculatorException
	 */
	public Number period() throws CalculatorException{
		double _i = getI().d();
		double _pv = getPv().d();
		double _pmt = getPmt().d();
		double _fv = getFv().d();
		double _begin = (begin ? 1 : 0);
		double _c = (c ? 1 : 0);
		
		return n(period(_i, _pv, _pmt, _fv, _begin, _c));
	}
	
	// FIXME: some lines was commented to prevent complaints, but it should be fixed.
	// TODO: Refactor
	private static double period(double i, double pv, double pmt, double fv, double beg, double c) throws CalculatorException{
		
		double n = 0;
		double tmp[] = new double[3];

		// Variable used to detect error and throw it.
		// Defined in Appendix D of HP12C Users Guide.
		 double d = (i/100.0) / (1 + i * (beg/100.0));
		

		// FIXME: these lines was commented to prevent complaints
		/**
		// This causes complaints
		if (pmt <= -(pv) * i)
			throw new InputException(Error.ERROR_CI,
			"Compound Interest Error: pmt <= -(pv) * i"); 
		*/
		 
		if (pmt == fv * i)
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: pmt == fv * i");
		else if (i <= (-100))
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: i <= -100");
		else if ((i == 0) && (pmt == 0)) // Platinum
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: i == 0 and PMT == 0");		
		else if ( (pmt >= fv * d ) && ( pmt <= -pv * d ) ) // Platinum
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: PMT between (FV * d) and (-PV * d), inclusive.");

		// Changing rate to decimal format
		i = i / 100.0;
		
		tmp[0] = pmt - (i * fv) + (i * pmt * beg); 
		tmp[1] = pmt + (i * pv) + (i * pmt * beg);
		tmp[2] = Math.log(i+1);
		
		n = Math.log( tmp[0] / tmp[1] ) / tmp[2];

		// Information in a footnote of the Platinum Users Manual
		if (fracPart(n) < 0.005){
			// Round down the result to represent a full period.
			n = Math.floor(n);
		}
		else{
			// Round up the result to represent a full period.
			n = Math.ceil(n);
		}
		
		return ( n );

	}

	/**
	 * Compound future value (HP12C formula)
	 * 
	 * Calculates the present (the initial cash flow) value of a financial problem.
	 * 
	 * Key: [PV]
	 * 
	 * @return
	 * @throws CalculatorException
	 */
	public Number presentValue() throws CalculatorException {
		double _n = getN().d();
		double _i = getI().d();
		double _pmt = getPmt().d();
		double _fv = getFv().d();
		double _begin = (begin ? 1 : 0);
		double _c = (c ? 1 : 0);
		
		return n(presentValue(_n, _i, _pmt, _fv, _begin, _c));
	}

	// TODO: Refactor
	private static double presentValue(double n, double i, double pmt, double fv, double begin, double c) throws CalculatorException{
		if (i <= -100) 
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: i <= -100");

		double pv = 0.0;
		double tmp[] = new double[4];
		
		// Changing rate to decimal format
		i = i / 100.0;
		
		// Without odd period
		if(fracPart(n) == 0.0){
			tmp[0] = ( 1 + i * (begin));
			tmp[1] = ( 1 - Math.pow( 1 + i, 0 - n )) / i;
			tmp[2] = Math.pow( 1 +i, 0 - n );
			
			pv = -(tmp[0] * pmt * tmp[1] + fv * tmp[2]);
		}
		// With odd period
		else{
			// Using simple interest
			if(c==0){
				tmp[0] = ( 1 + ( i * fracPart(n) ) );
			}
			// Using compound interest
			else{
				tmp[0] = Math.pow(1 + i, fracPart(n));
			}
			
			tmp[1] = ( 1 + i * (begin));
			tmp[2] = ( 1 - Math.pow( 1 + i, 0 - intPart(n) )) / i;
			tmp[3] =  Math.pow( 1 + i, 0 - intPart(n) );
			
			pv = -( ( tmp[1] * pmt * tmp[2] + fv * tmp[3] ) / tmp[0] );
		}
		
		return ( pv );
	}
	
	/**
	 * Price payment (PMT)
	 * 
	 * Calculates the payment amount.
	 * 
	 * Key: [PMT]
	 * 
	 * @return
	 * @throws CalculatorException
	 */
	public Number pricePayment() throws CalculatorException {
		double _n = getN().d();
		double _i = getI().d();
		double _pv = getPv().d();
		double _fv = getFv().d();
		double _begin = (begin ? 1 : 0);
		double _c = (c ? 1 : 0);
		
		return n(pricePayment(_n, _i, _pv, _fv, _begin, _c));
	}

	// TODO: Refactor
	private static double pricePayment(double _n, double _i, double _pv, double _fv, double _begin, double _c) throws CalculatorException{
		
		double nFrac = n(_n).fractionalPart().doubleValue();
		
		if (_n == 0)
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: n == 0");
		else if (_i == 0)
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: i == 0");
		else if (_i <= (-100))
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: i <= -100");

		double pmt = 0.0;
		double tmp[] = new double[4];
		
		// Changing rate to decimal format
		_i = _i / 100.0;
		
		// Without odd period
		if(nFrac == 0.0){
			tmp[0] = ( 1 + _i * (_begin));
			tmp[1] = ( 1 - Math.pow( 1 + _i, 0 - _n )) / _i;
			tmp[2] = Math.pow( 1 + _i, 0 - _n );
			
			pmt =  -( (_pv + _fv * tmp[2]) / (tmp[0] * tmp[1]) );
		}
		// With odd period
		else{
			// With odd period using simple interest
			if(_c==0){
				
				tmp[0] = ( 1 + ( _i * nFrac ) );
			}
			// With odd period using compound interest
			else{
				tmp[0] = Math.pow(1 + _i, nFrac);
			}
			
			tmp[1] = ( 1 + _i * (_begin));
			tmp[2] = ( 1 - Math.pow( 1 + _i, 0 - nFrac )) / _i;
			tmp[3] =  Math.pow( 1 + _i, 0 - nFrac );
			
			pmt =  -( (_pv * tmp[0] + _fv * tmp[3]) / (tmp[1] * tmp[2]) );
		}
		
		return (pmt);
	}

	/**
	 * Compound rate
	 * 
	 * Computes interest rate per compounding period
	 * 
	 * Key: [i]
	 * 
	 * @return rate
	 * @throws CalculatorException
	 */
	public Number rate() throws CalculatorException{
		
		double _n = getN().d();
		double _pv = getPv().d();
		double _pmt = getPmt().d();
		double _fv = getFv().d();
		double _begin = (begin ? 1 : 0);
		double _c = (c ? 1 : 0);
		
		return n(rate(_n, _pv, _pmt, _fv, _begin, _c));
	}
	
	// TODO: Refactor
	public static double rate(double n, double pv, double pmt, double fv, double beg, double c) throws CalculatorException{
		
		if ((pmt == 0.0) && (n < 0))
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: no solution exists for N.");
		else if ( ( (pv > 0) && (fv > 0) ) || ( (pv < 0) && (fv < 0) ) )
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: both PV and FV are positive or negative.");
		

		double init_interest = -1;
		double final_interest = 99999.0;
		double suposed_interest = 0.0;
		double suposed_payment = 0.0;
		double suposed_difference = 0.0;
		
		int cnt = 1;
		boolean found = false;
		
		// PMT and FV cannot be negative here
		// This algorithm only works if the PMT and FV are positive
		pv = (-Math.abs(pv));
		pmt = Math.abs(pmt);
		fv = Math.abs(fv);
	
		while (true) {
			suposed_interest = (final_interest + init_interest)/2.0;
			suposed_payment = pricePayment(n, suposed_interest, pv, fv, beg, c);
			suposed_difference = Math.abs(pmt-suposed_payment);
			
			if (suposed_difference > 0.000000001) {
				if (suposed_payment > pmt) {
					final_interest = suposed_interest;
				}
				else {
					init_interest = suposed_interest;
				}
			}
			else {
				found = true;
				break;
			}
			if (cnt > 10000) {
				break;
			}
			cnt++;
		}
		
		if (!found)
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: no solution found.");
		
		return ( final_interest );

	}
	
	/**
	 * Internal Rate or Return
	 * 
	 * Calculates the internal rate of return (yield) for cash flow and initial investment.
	 * 
	 * Key: [f][IRR]
	 * 
	 * @param cf cash flow
	 * @return
	 * @throws CalculatorException
	 */
	public Number irr(Number[][] cf) throws CalculatorException{
		double _n = getN().d();
		
		Number numbers[][] = cf;
		double doubles[][] = new double[cf.length][cf[0].length];
		
		 
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[0].length; j++) {
				doubles[i][j] = numbers[i][j].d();
			}
		}
		
		return n(irr(_n, doubles));
		
	}
	
	// TODO: Refactor
	public static double irr(double n, double[][] cf) throws CalculatorException{

		double npv = 0;
		double irr = 0;
		double u = 0;		
		double cont = 0;
		double expo = 0;
		
		// Checks for safety
		if (cf == null) return 0;
		if (cf.length < n) return 0;
				
		// Flag that is used to sign
		// when an NPV passes from negative to positive 
		// and vice-versa.
		boolean signBefore = true; // true: positive;  false: negative
		boolean signAfter = true;  // true: positive;  false: negative
		
		for(int a=0; a<n; a++){
			cont += cf[a][1];
		}
		
		double IRRBegin = 100.0;
		
		irr = IRRBegin;			
							
		for(int m = 0; m < 100; m++){
		
			u = 1 + (irr / 100.0);

			expo = 0;
			
			if(npv >= 0){
				signBefore = true;
			}else{
				signBefore = false;
			}
							
			for(int j = 0;j <= n; j++){
				if(j == 0){
					npv = cf[0][0];
					
					// Test to correct problems with the loop
					if(npv >= 0){
						signBefore=true;
					}else{
						signBefore=false;
					}
					
					cont++;

				}else{
					for(int k = 0;k < cf[j][1]; k++){
						if(expo <= cont){
						   npv += cf[j][0] / Math.pow(u, ++expo);	
						}
					}					
				}
			}
							
			if(npv >= 0){
				signAfter = true;
			}else{
				signAfter = false;
			}	
			
			if(signBefore != signAfter){
				irr = irr + IRRBegin / Math.pow(2.0, m);

			}else{
				irr = irr - IRRBegin / Math.pow(2.0, m);

			}
			
			if(npv == 0){
				m = 100000;
			}
		}

		return ( irr );
	}
	
	/**
	 * Net Present Value
	 * 
	 * Calculates the net present value of cash flow and initial investment.
	 * 
	 * Key: [f][NPV]
	 * 
	 * @param cf cash flow
	 * @return
	 * @throws CalculatorException
	 */
	public Number npv(Number[][] cf) throws CalculatorException{
		double _n = getN().d();
		double _i = getI().d();
		
		Number numbers[][] = cf;
		double doubles[][] = new double[cf.length][cf[0].length];
		
		 
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[0].length; j++) {
				doubles[i][j] = numbers[i][j].d();
			}
		}
		
		return n(npv(_n, _i, doubles));
	}

	// TODO: Refactor
	private static double npv(double n, double i, double[][] cf) throws CalculatorException{

		if (i <= (-100))
			throw new CalculatorException(Error.ERROR_CI,
			"Compound Interest Error: i <= -100");
		
		double npv  = 0.0;
		double u    = 1 + (i / 100.0);		
		double cont = 0.0;
		double expo = 0.0;
		
		// Checks for safety
		if (cf == null) return 0;
		if (cf.length < n) return 0;
		
		for(int a = 0; a <= n; a++){
			cont += cf[a][1];
		}
		for(int j = 0; j <= n; j++){
			if(j == 0){
				npv = cf[0][0];
				cont++;
			}else{
				for(int k = 0; k < cf[j][1]; k++){
					if(expo <= cont){
						npv += cf[j][0] / Math.pow(u, ++expo);	
					}
				}					
			}				
		}
		
		return ( npv );	
	}
	
	/**
	 * Amortization (HP12C formula, returns 5 values, take a look at HP12C Users Guide to details)
	 * 
	 * Amortizes "x" number of periods in a financial problem. (Calculates the balance after "x" amortizations)
	 * 
	 * Key: [f][AMORT]
	 * 
	 * @param x
	 * @param precision
	 * @return
	 * @throws CalculatorException
	 */ 
	public Number[] amortization(Number x, int precision) throws CalculatorException {
		double _x = x.d();
		double _precision = precision;
		double _n = getN().d();
		double _i = getI().d();
		double _pv = getPv().d();
		double _pmt = getPmt().d();
		double _begin = (begin ? 1 : 0);
		
		double doubles[] = amortization(_x, _n, _i, _pv, _pmt, _begin, _precision);
		Number numbers[] = new Number[doubles.length];
		
		 
		for (int i = 0; i < doubles.length; i++) {
			numbers[i] = n(doubles[i]);
		}
		
		return numbers;
	}
	
	// TODO: Refactor 
	public static double[] amortization(double x, double n, double i, double pv, double pmt, double begin, double precision) throws CalculatorException{

		double tmp[] = new double[5];
		
		double INT = 0;
		double SumINT = 0;
		double PRN = 0;
		double SumPRN = 0;
		double PVj = pv;
		
		// Updating n;
		n += x;
		
		int j = 0;
		for(j=0; j<x; j++){
			if((j==0)&&(begin==1.0)){
				INT=0;
			}else{
				INT = Math.abs(PVj * i/100.0);
				
				// Rounding INT. 
				// HP12C Rounds internally to the current display precision
				INT = Math.round(INT * Math.pow(10, precision)) / Math.pow(10, precision); 
				
				if(pmt<0){
					INT = (-INT);
				}
			}
			
			SumINT += INT;
			
			PRN = pmt - INT;
			
			SumPRN += PRN;
			
			PVj = PVj + PRN;
			
		}

		tmp[0] = ( j );      //  Number of payment periods to be amortized
		tmp[1] = ( SumPRN ); //  Returns a sum of the amortizations in the informed period
		tmp[2] = ( SumINT ); //  Returns a sum of the interests paid in the informed period
		tmp[3] = ( PVj );    //  Returns the remaining balance
		tmp[4] = ( n );      //  Total number of payments amortized
		
		return ( tmp );
	}

	// Depreciation =============================================================
		
	/**
	 * Straight line Depreciation (HP12C Formula)
	 * 
	 * Calculates deprecation and remaining depreciable value using the straight-line method.
	 * 
	 * Key: [f][SL]
	 * 
	 * @param x
	 * @return
	 * @throws CalculatorException
	 */
	public Number[] slDepreciation(Number x) throws CalculatorException{
		double _n = getN().d();
		double _i = getI().d();
		double _pv = getPv().d();
		double _fv = getFv().d();
		double _x = x.d();
		
		double doubles[] = slDepreciation(_n, _i, _pv, _fv, _x);
		Number numbers[] = new Number[doubles.length];
		 
		for (int i = 0; i < doubles.length; i++) {
			numbers[i] = n(doubles[i]);
		}
		
		return numbers;
	}
	
	// TODO: Test Refactor	
	public static double[] slDepreciation(double n, double i, double pv, double fv, double x) throws CalculatorException{
		
		double cost = pv;
		double sell = fv;
		double life = n;
		double year = x;
		double depr = 0;
		double rest = cost - sell;
		
		double tmp[] = new double[2];
			
		if (year < 0)			
			throw new CalculatorException(Error.ERROR_CI,
			"year < 0");
		else if (year != Math.floor(year))			
			throw new CalculatorException(Error.ERROR_CI,
			"year is not integer");
		else if (life <= 0)			
			throw new CalculatorException(Error.ERROR_CI,
			"life <= 0");
		else if (life > Math.pow(10,10))		
			throw new CalculatorException(Error.ERROR_CI,
			"life > 10^10");

		while (--year >= 0) {
			depr = (cost - sell) / life;
			rest -= depr;
		}

		tmp[0] = (depr);
		tmp[1] = (rest);
		
		return tmp;
	}
	
	/**
	 * Sum Of The Years Digits Depreciation (HP12C Formula)
	 * 
	 * Calculates deprecation and remaining depreciable value using the sum of the years digits method.
	 * 
	 * Key: [f][SOYD]
	 * 
	 * @param x
	 * @return
	 * @throws CalculatorException
	 */
	public Number[] soydDepreciation(Number x) throws CalculatorException{
		double _n = getN().d();
		double _i = getI().d();
		double _pv = getPv().d();
		double _fv = getFv().d();
		double _x = x.d();
		
		double doubles[] = soydDepreciation(_n, _i, _pv, _fv, _x);
		Number numbers[] = new Number[doubles.length];
		 
		for (int i = 0; i < doubles.length; i++) {
			numbers[i] = n(doubles[i]);
		}
		
		return numbers;
	}
	
	// TODO: Test, Refactor	
	public static double[] soydDepreciation(double n, double i, double pv, double fv, double x) throws CalculatorException{
		
		double cost = pv;
		double sell = fv;
		double life = n;
		double year = x;
		double depr = 0;
		double rest = cost - sell;

		double tmp[] = new double[2];
		
		if (year < 0)			
			throw new CalculatorException(Error.ERROR_CI,
			"year < 0");
		else if (year != Math.floor(year))			
			throw new CalculatorException(Error.ERROR_CI,
			"year is not integer");
		else if (life <= 0)			
			throw new CalculatorException(Error.ERROR_CI,
			"life <= 0");
		else if (life > Math.pow(10,10))		
			throw new CalculatorException(Error.ERROR_CI,
			"life > 10^10");

		double year_up = 0;
		double soyd = life * ( life + 1.0 ) / 2.0;
		
		while (--year >= 0) {
			depr = ( cost - sell ) * ( life - ( ++year_up ) + 1 ) / soyd;
			rest -= depr;
		}
		
		tmp[0] = (depr);
		tmp[1] = (rest);
		
		return tmp;

	}
 
	/**
	 * Declining Balance Depreciation (HP12C Formula)
	 * 
	 * Calculates deprecation and remaining depreciable value using the declining balance method.
	 * 
	 * Key: [f][DB]
	 * 
	 * @param x
	 * @return
	 * @throws CalculatorException
	 */
	public Number[] dbDepreciation(Number x) throws CalculatorException{
		double _n = getN().d();
		double _i = getI().d();
		double _pv = getPv().d();
		double _fv = getFv().d();
		double _x = x.d();
		
		double doubles[] = dbDepreciation(_n, _i, _pv, _fv, _x);
		Number numbers[] = new Number[doubles.length];
		 
		for (int i = 0; i < doubles.length; i++) {
			numbers[i] = n(doubles[i]);
		}
		
		return numbers;
	}

	// TODO: Refactor
	private static double[] dbDepreciation(double n, double i, double pv, double fv, double x) throws CalculatorException{
		
		double cost = pv;
		double sell = fv;
		double life = n;
		double db = i/100.0;
		double year = x;
		double depr = 0;
		double rest = cost - sell;
		
		double tmp[] = new double[2];

		if (year < 0)			
			throw new CalculatorException(Error.ERROR_CI,
			"year < 0");
		else if (year != Math.floor(year))			
			throw new CalculatorException(Error.ERROR_CI,
			"year is not integer");
		else if (life <= 0)			
			throw new CalculatorException(Error.ERROR_CI,
			"life <= 0");
		else if (life > Math.pow(10,10))		
			throw new CalculatorException(Error.ERROR_CI,
			"life > 10^10");
		
		while (--year >= 0) {
			depr = ( rest + sell ) * db / life;
			rest -= depr;
		}

		tmp[0] = (depr);
		tmp[1] = (rest);
		
		return tmp;
	}

	// Bond Calculations ============================================
	 
	/**
	 * Bond Price
	 * 
	 * calculates bond price, given desired yield to maturity.
	 * 
	 * Key: [f][PRICE]
	 * 
	 * @param y
	 * @param x
	 * @return
	 */
	public Number[] bondPrice(Date y, Date x) {
		double _i = getI().d();
		double _pmt = getPmt().d();

		double doubles[] = bondPrice(_i, _pmt, y, x);
		Number numbers[] = new Number[doubles.length];
		 
		for (int i = 0; i < doubles.length; i++) {
			numbers[i] = n(doubles[i]);
		}
		
		return numbers;
	}
	
	public Number[] bondPriceOLD(Date y, Date x) {
		double _i = getI().d();
		double _pmt = getPmt().d();

		double doubles[] = bondPrice(_i, _pmt, y, x);
		Number numbers[] = new Number[doubles.length];
		 
		for (int i = 0; i < doubles.length; i++) {
			numbers[i] = n(doubles[i]);
		}
		
		return numbers;
	}
	
	// TODO: Refactor
	public static double[] bondPrice(double i, double pmt, Date y, Date x) throws CalculatorException{
		
		if ((x == null) || (!x.isValid()))
			throw new CalculatorException(Error.ERROR_CAL,
			"invalid x date (maturity)");
		else if ((y == null) || (!y.isValid()))
			throw new CalculatorException(Error.ERROR_CAL,
			"invalid y date (buy)");
		else if(i <= -100)
			throw new CalculatorException(Error.ERROR_CI,
			"i <= -100");
		
		double dim = 0; // days between issue date and maturity date
		double dsm = 0; // days between settlement date and maturity date
		double dcs = 0; // days between beginning of current coupon period and settlement date
		double e   = 0; // number of days in coupon period where settlement occurs
		double dsc = e - dcs; // days from settlement date to next 6-month coupon date
		double n   = 0; // number of semiannual coupon payable between settlement date and maturity date
		double cpn = 0; // annual coupon rate (as a percentage)
		double yield = 0; // annual yield (as percentage)
		double price = 0; // dollar price per $100 par value
		double rdv   = 0; // redemption value
		
		// Temporary array
		double tmp[] = new double[2];
		
		// annual yield to maturity
		yield = i;
		// annual coupon rate
		cpn = pmt;
		// redemption value
		rdv = 100;

		// settlement (purchase) date
		Date settlement = new Date(y);
		// maturity (redemption) date
		Date maturity   = new Date(x);
		
		// days between settlement and maturity date
		dsm = Date.diffDates(settlement, maturity); 
		
		// Begin auxiliary section
		Date a = new Date(maturity); 
		Date b = new Date(); 

		while ( a.getSerial() > settlement.getSerial()){
			b = new Date(a);
			++n;
			a.setDay(1);
			a.setMonth(a.getMonth()-6);
			a.setDay(maturity.getDay());
		}
		// End auxiliary section
		
		
		// number of days in coupon period where settlement occurs
		e = Date.diffDates(a, b); 
		//  days from settlement date to next 6-month coupon date
		dcs = Date.diffDates(settlement, b); 
		// days between beginning of current coupon period and settlement date
		dsc = e - dcs;

		
		// Begin HP12C bond price formula
		if(dsm <= e){
			
			tmp[0] = 100 * ( rdv + ( cpn / 2 ) );
			tmp[1] = 100 + ( ( dsm / e ) * ( yield / 2 ) );
			
			tmp[0] = (tmp[0] / tmp[1]);
		}
		else{
			tmp[0] = rdv / Math.pow( ( 1 + ( yield / 200 ) ),  ( n - 1 + ( dcs / e ) ) );
			
			for (int k = 1; k <= n; ++k){
				tmp[1] += ( cpn / 2 ) / Math.pow( ( 1 + ( yield / 200 ) ), ( k - 1 + ( dcs / e ) ) );
			}
			
			tmp[0] = tmp[0] + tmp[1];
		}
		
		tmp[1] = ( cpn / 2 ) * ( dsc / e );
		tmp[0] = tmp[0] - tmp[1];
		// End HP12C bond price formula
		
		// bond price (as a percent of par)
		tmp[0] = (tmp[0]);
		// accrued interest
		tmp[1] = (tmp[1]);
		
		return tmp;
	}

	// TODO: Implement, Test
	// Bond Yield
	// Key: [f][YTM]
	// Description: calculates yield to maturity, given bond price.
	public static double bondYield(double pv, double pmt, Date y, Date x) throws CalculatorException{
		// TODO
		return 0;
	}
	
	// Integer part
	// Description: Extracts only the integer portion of a number x.
	private static double intPart(double x) throws CalculatorException{
		return ( Math.floor(x) );
	}
	
	// Decimal part
	// Description: Extracts only the fractional portion of a number x, 
	// for example, 1.55 --> 1.00
	private static double fracPart(double x) throws CalculatorException{
		return ( x - intPart(x) );
	}
}
