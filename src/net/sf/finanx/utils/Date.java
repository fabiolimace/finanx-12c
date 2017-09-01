package net.sf.finanx.utils;

import java.util.Calendar;

// TODO: Refactor FinanxStack to use Calendar instead of this Date
public class Date {

	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;
	public static final int SATURDAY = 6;
	public static final int SUNDAY = 7;
	
	public static final int JANUARY = 1;
	public static final int FEBRUARY = 2;
	public static final int MARCH = 3;
	public static final int APRIL = 4;
	public static final int MAY = 5;
	public static final int JUNE = 6;
	public static final int JULY = 7;
	public static final int AUGUST = 8;
	public static final int SEPTEMBER = 9;
	public static final int OCTOBER = 10;
	public static final int NOVEMBER = 11;
	public static final int DECEMBER = 12;
	
	public static final String WEEK_DAYS[] = {"","Monday","Tuesday","Wednesday","Thursday","Triday","Saturday","Sunday",};
	public static final String MONTHS[] = {"","January","February","March","April","May","June","July","August","September","Octuber","November","December"};
		
	private int day;
	private int month;
	private int year;
	private String error;
	private boolean valid;	
	
	public Date(){
		this.day=0;
		this.month=0;
		this.year=0;
		this.error="No errors found";
	}
	
	public Date(int serial){
		setDate(serial);
		this.error="No errors found";
	}
	
	public Date(Date date){
		
		this.day=date.getDay();
		this.month=date.getMonth();
		this.year=date.getYear();
		this.error="No errors found";
		this.validate();
	}
	
	public Date(int year, int month, int day){
		
		this.day=day;
		this.month=month;
		this.year=year;
		this.error="No errors found";
		this.validate();
	}
	
	public static Date getInstance(int year, int month, int day) {
		return new Date(year, month, day);
	}
	
	// This method is too slow
	public void setDate(int serial){
		Date dt = serialToDate(serial);
		this.day=dt.getDay();
		this.month=dt.getMonth();
		this.year=dt.getYear();
	}
	
	public void setDate(Date date){
		this.day=date.getDay();
		this.month=date.getMonth();
		this.year=date.getYear();
	}
	
	public void setDay(int day){

		int y = this.year;
		int m = this.month;
		int d = 0;

		
		if (day < 1){

			for(int i = 0; i < Math.abs(day); i++){
				
				if(d<=1){
					if (m==1){
							d = 31;
							m = 13;
							y--;
					}
					else if (m==2){
						d = 31;
					}
					else if (m==3){
						if(!isGregorianLeapYear(y)){
							d = 28;
						}else{
							d = 29;
						}
					}
					else if (m==4){
						d = 31;
					}
					else if (m==5){
						d = 30;
					}
					else if (m==6){
						d = 31;
					}
					else if (m==7){
						d = 30;
					}
					else if (m==8){
						d = 31;
					}
					else if (m==9){
						d = 31;
					}
					else if (m==10){
						d = 30;
					}
					else if (m==11){
						d = 31;
					}
					else if (m==12){
						d = 30;
					}
					m--;
				}
				else{
					d--;
				}				
			}
			
			this.day   = d;
			this.month = m;
			this.year  = y;
			
		}
		else if (day > 27){
			for(int i = 0; i < Math.abs(day) + 1; i++){
				
				d++;
				
				if (m==2){
					if(!isGregorianLeapYear(y)){
						if(d>28){
							d = 1;
							m++;
						}
					}else{
						if(d>29){
							d = 1;
							m++;
						}
					}

				}else if ((m==1) || (m==3) || (m==5) || (m==7) || (m==8) || (m==10)){
					if(d>31){
						d = 1;
						m++;
					}
				}else if ((m==4) || (m==6) || (m==9) || (m==11)){
					if(d>30){
						d = 1;
						m++;
					}
				}
				else if (m==12){
					if(d>31){
						d = 1;
						m = 1;
						y++;
					}
				}
				
			}
			
			this.day = d;
			this.month = m;
			this.year = y;
			
		}
		else{
			this.day = day;
		}
		
		this.validate();
	}
	public void setMonth(int month){
		
		if(month < 1){
			
			// Little fix :P
			month++;
			
			int tmp[] = new int[2];
			
			tmp[0] = Math.abs(month) % 12;
			tmp[1] = Math.abs(month) / 12;
			
			this.month = 12 - tmp[0] ;
			this.year  = this.year - (tmp[1] + 1);
			
			if(this.month==12){
				this.month = 1;
				this.year++;
			}
			else{
				this.month++;
			}

		}
		else if(month > 12){
			
			int tmp[] = new int[2];
			
			tmp[0] = month % 12;
			tmp[1] = month / 12;
			
			this.month = tmp[0] + 1;
			this.year  = this.year + tmp[1];
		}
		else{
			this.month = month;
		}
		
		this.validate();
	}
	public void setYear(int year){
		this.year=year;
		this.validate();
	}
	
	public Date getDate(){
		return this;
	}
	
	public int getDay(){
		return this.day;
	}
	public int getMonth(){
		return this.month;
	}
	public int getYear(){
		return this.year;
	}
	public String getError(){
		return this.error;
	}
	
	public boolean isValid(){
		
		return this.valid;
		
	}
	
	public void validate(){
		
		this.valid=true;
		
		double y = this.year;
		double m = this.month;
		double d = this.day;
		
		if ((y>9999) || (y<=0)){
			this.error = "Invalid year";
			this.valid = false;
		}else
		
		if ((m>12) || (m<=0)){
			this.error = "Invalid month";
			this.valid = false;
		}else 
			
		if(d<=0){
			this.error = "Invalid day";
			this.valid = false;
		}else 
			
		if (m==2){
			if(!isGregorianLeapYear(y)){
				if((d>28) || (d<=0)){
					this.error = "February with more than 28 days in a not leapyear";
					this.valid = false;
				}
			}else{
				if((d>29) || (d<=0)){
					this.error = "February with more than 29 days in a leapyear";
					this.valid = false;
				}
			}
		}else if ((m==1) || (m==3) || (m==5) || (m==7) || (m==8) || (m==10) || (m==12)){
			if((d>31) || (d<=0)){
				this.error = "Month of "+MONTHS[(int)m]+" with more than 31 days";
				this.valid = false;
			}
		}else if ((m==4) || (m==6) || (m==9) || (m==11)){
			if((d>30) || (d<=0)){
				this.error = "Month of "+MONTHS[(int)m]+" with more than 30 days";
				this.valid = false;
			}
		}
		
	}

	// getWeekDay
	// Returns the day of the week in integer value.
	// Valid return values: Monday=1, Tuesday=2, Thursday=3, Wednesday=4, Saturday=6, Sun=7
	public int getWeekDay(){
		
		if(valid){
			double serial = ((getSerial()-1)%7);
			
			return (int)( serial==0 ? 7 : serial );
		}
		return 0;
	}
	
	public String getWeekDayString(){
		
		if(valid){
			return WEEK_DAYS[getWeekDay()];
		}
		return "";
	}
	
	public double gregorianToJulian(double d, double m, double y){
		
		double GREGORIAN_EPOCH = 1721425.5;
		
		return  (GREGORIAN_EPOCH-1) + (365 * (y - 1)) +
		Math.floor((y - 1) / 4) +
           (-Math.floor((y - 1) / 100)) +
           Math.floor((y - 1) / 400) +
           Math.floor((((367 * m) - 362) / 12) +
           ((m <= 2) ? 0 : (isGregorianLeapYear(y) ? -1 : -2) ) + d);	

	}
	
	public boolean isGregorianLeapYear(double year)
	{
	    return ((year % 4) == 0) &&
	            (!(((year % 100) == 0) && ((year % 400) != 0)));
	}
	
	public double JulianWeekDay(double day)
	{
		double retorno;
		double w = (Math.floor((day+ 1.5))% 7);

		retorno = w;
		
	    return retorno; 
	}
	
	private boolean isNumber(String number){
		
		String valids = "0123456789";
		
		char caracteres[] = number.toCharArray();
		String caracter = "";
		
		for(int i=0; i<caracteres.length; i++){
			caracter = ""+caracteres[i];
			
			if(!valids.contains(caracter)) return false;			
		}
		return true;
	}
	
	// Returns a serial number counting from December, 30th 1899
	// based in civil year (with all months having 28, 29, 30 or 31 days)
	public int getSerial(){

		double dd = this.day;
		double mm = this.month;
		double yyyy = this.year;
		
		double x=0.0, z=0.0;
		
		// Tests based on HP-12C user guide
		if(mm<=2){
			x = 0.0;
			z = (yyyy) - 1.0;
		}else{
			x = (int)(0.4 * (mm) + 2.3);
			z = (yyyy);
		}

		//  Formula based on HP-12C user guide
		double dt = 365.0*(yyyy) + 31.0*(mm - 1.0) + dd + (int)(z/4.0) - x;
		
		// Sets the counting begin to December, 30th 1899 (inclusive)
		dt = dt-693973.0;
		
		
		// Additional tests executed to asure that the centure years wont be 
		// considered as leap years, except the ones that are divisible by 400.
		for(int i=1899; i<yyyy; i++){
			if((i%100==0)&&!(i%400==0)){
				dt = dt - 1;
			}
		}
		
		return (int)dt;
	}
	
	public static Date serialToDate(int valor){

		Date dt = new Date();
		int result = 0;

		double beginLoop = 8192.0; //Math.pow(2, 13) /* = 8192 */;
		double y = beginLoop;
			for(int a=0; a<100; a++){
				for(int m=1; m<13; m++){
					for(int d=1; d<32; d++){
						
						dt.setDay(d);
						dt.setMonth(m);
						dt.setYear((int)Math.round(y));

						result = dt.getSerial();

						if(result > valor)
							y=  y - beginLoop / Math.pow(2.0, a);
						else if(result < valor)
							y= y + beginLoop / Math.pow(2.0, a);			
						else {
							if(dt.isValid()){
								d=32;
								m=13;
								a=100;	
							}
							else{
								continue;
							}
						}
					} // for d
				} // for m	
			} // for a

		return dt;
	}
	
	// Returns a serial number counting from December, 30th 1899
	// based in commercial year (with all months having 30 days only)
	public int getCommercialSerial(){
		int d1 = 29;
		int m1 = 11;
		int a1 = 1899;
		double z1 = 0;
		
		int d2 = day;
		int m2 = month;
		int a2 = year;
		double z2 = 0;
		

		double fDT1 = 0;
		double fDT2 = 0;
		
		double retorno = 0;
		
		// Tests based on HP-12C user guide
		if(d1==30){
			z1=30; 
		}else if(d1!=31){
			z1=d1;
		}
		
		// Tests based on HP-12C user guide
		if((d2==31) && ((d1 == 30) || (d1 == 31))){
			z2=30;
		}else if((d2==31) && (d1<30)){
			z2=d2;
		}else if(d2<31){
			z2=d2;
		}		
		
		try{
			//  Formula based on HP-12C user guide
			fDT1=360*a1+30*m1+z1;
			fDT2=360*a2+30*m2+z2;
	
		retorno = fDT2 - fDT1;
		
		}catch(Exception e){
			retorno = 0;
		}
		
		return (int)retorno;
	}

	public static Calendar addDays(final Calendar begDate, int days){

		Calendar calendar = Calendar.getInstance();
		calendar.set(begDate.get(Calendar.YEAR), begDate.get(Calendar.MONTH), begDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		
		return calendar;
	}
	
	public void addDays(int days){

		int i=0;
		int f=0;

		i = getSerial();
		f = i + days;

		Date dt = serialToDate(f);
		
		this.day=dt.getDay();
		this.month=dt.getMonth();
		this.year=dt.getYear();
	}
	
	public static Date addCommercialDays(Date date, int days){
		
		int ldDay = date.day;
		int ldMonth = date.month;
		int ldYear = date.year;
		
		int ltDay = ldDay;
		int ltMonth = ldMonth;
		int ltYear = ldYear;

		// Commercial Calendar Months have 30 days
		int monthDays=30;
		
		if ((ldDay + days)<=monthDays)
		{
			ltDay= ldDay + days;
		}
		else
		{
			ltDay = ((ldDay+days)%monthDays);

			if ((ldMonth +((ldDay+days)/monthDays))<=12)
			{
				ltMonth = (ldMonth +((ldDay+days)/monthDays));
			}
			else
			{
				ltMonth =((ldMonth +((ldDay+days)/monthDays)) %12);
				ltYear =(ldYear + ((ldMonth + ((ldDay+days)/monthDays))/12));
			}
		}
		
		return new Date(ltDay,ltMonth,ltYear);
		
	}
	
	public void addCommercialDays(int days){
		
		int ldDay = this.day;
		int ldMonth = this.month;
		int ldYear = this.year;
		
		int ltDay = ldDay;
		int ltMonth = ldMonth;
		int ltYear = ldYear;

		// Commercial Calendar Months have 30 days
		int monthDays=30;
		
		if ((ldDay + days)<=monthDays)
		{
			ltDay= ldDay + days;
		}
		else
		{
			ltDay = ((ldDay+days)%monthDays);

			if ((ldMonth +((ldDay+days)/monthDays))<=12)
			{
				ltMonth = (ldMonth +((ldDay+days)/monthDays));
			}
			else
			{
				ltMonth =((ldMonth +((ldDay+days)/monthDays)) %12);
				ltYear =(ldYear + ((ldMonth + ((ldDay+days)/monthDays))/12));
			}
		}
		
		this.day  = ltDay;
		this.month= ltMonth;
		this.year = ltYear;
	}
	
	public static int diffDates(Date begDate, Date endDate){

		int a = begDate.getSerial();
		int b = endDate.getSerial();

		return b - a;
	}
	
	public int diffDates(Date endDate){

		int a = this.getSerial();
		int b = endDate.getSerial();

		return b - a;
	}
	
	public static int diffDates360(Date beginDate, Date endDate){

		int a = beginDate.getCommercialSerial();
		int b = endDate.getCommercialSerial();
		
		return b - a;
	}
	
	public int diffCommercialDates(Date endDate){
	
		int a = this.getCommercialSerial();
		int b = endDate.getCommercialSerial();
		
		return b - a;
	}
	
	public String toString(){
		return this.year+"-"+this.month+"-"+this.day;
	}
	
	public void print(){
		System.out.println(this);
	}
	
	@Override
	public boolean equals(Object object) {
		Date date = (Date) object;
		return this.year == date.year && this.month == date.month && this.day == date.day;
	}
	
}
