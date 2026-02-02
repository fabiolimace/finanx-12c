package net.sf.finanx.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import net.sf.finanx.hp12c.controller.CalculatorException;
import net.sf.finanx.hp12c.controller.Error;

/**
 * 
 * This class follows a simple strategy for immutable objects provided by The Java Tutorials.
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/imstrat.html">A Strategy for Defining Immutable Objects</a>
 * 
 */
final public class Number {

	final private BigDecimal value;

	public static final Number ZERO = getInstance(0.0);
	public static final Number ONE = getInstance(1.0);
	public static final Number TWO = getInstance(2.0);
	public static final Number THREE = getInstance(3.0);
	public static final Number FOUR = getInstance(4.0);
	public static final Number FIVE = getInstance(5.0);
	public static final Number SIX = getInstance(6.0);
	public static final Number SEVEN = getInstance(7.0);
	public static final Number EIGHT = getInstance(8.0);
	public static final Number NINE = getInstance(9.0);
	public static final Number TEN = getInstance(10.0);
	public static final Number TWELVE = getInstance(12.0);
	public static final Number HUNDRED = getInstance(100.0);
	public static final Number THOUSAND = getInstance(1000.0);

	public static final Number HALF = TWO.reciprocal();
	public static final Number THIRD = THREE.reciprocal();
	public static final Number FORTH = FOUR.reciprocal();
	public static final Number FITH = FIVE.reciprocal();
	public static final Number TENTH = TEN.reciprocal();
	public static final Number CENT = HUNDRED.reciprocal();
	public static final Number THOUSANDTH = THOUSAND.reciprocal();
	
	public static final Number PI = getInstance(Math.PI);
	public static final Number E = getInstance(Math.E);
	
	public static final int scale = 10;
	
	// Precision: 34 digits, Rounding mode: HALF_EVEN
	private final MathContext context = MathContext.DECIMAL128;
	
	public Number() {
		this.value = new BigDecimal(0.0, context);
	}

	private Number(BigDecimal value) {
		try {
			this.value = value.plus(context);
		} catch (Exception e) {
			throw new CalculatorException(Error.ERROR_MATH, "Invalid value");
		}
	}

	private Number(String value) {
		BigDecimal tmp = null;
		try {
			tmp = new BigDecimal(value, context);
		} catch (NumberFormatException e) {

			e.printStackTrace();
			throw new CalculatorException(Error.ERROR_MATH, "Invalid number format");
		}
		this.value = tmp;
	}

	private Number(double value) {
		if (Double.isNaN(value)) {
			throw new CalculatorException(Error.ERROR_MATH, "Value is not a number");
		}
		if (Double.isInfinite(value)) {
			throw new CalculatorException(Error.ERROR_MATH, "Value is double infinity");
		}
		// Use construtor that receives a string as parameter to prevent
		// problems related to double primitive type
		this.value = new Number(Double.toString(value)).copyValue();
	}
	
	public BigDecimal copyValue() {
		return value.plus(context);
	}
	
	public Number copy() {
		return Number.getInstance(copyValue());
	}
	
	public static Number getInstance(double value) {
		return new Number(value);
	}
	
	public static Number getInstance(String value) {
		return new Number(value);
	}
	
	public static Number getInstance(BigDecimal value) {
		return new Number(value);
	}
	
	public static Number random() {
		// TODO: replace java.math
		return getInstance(Math.random());
	}
	
	public boolean isZero() {
		return value.signum() == 0;
	}

	public boolean isPositive() {
		return value.signum() > 0;
	}

	public boolean isNegative() {
		return value.signum() < 0;
	}
	
	public boolean isDecimal() {
		return !this.fractionalPart().equalTo(ZERO);
	}
	
	public boolean isInteger() {
		return this.fractionalPart().equalTo(ZERO);
	}
	
	public boolean isNatural() {
		return this.isInteger() && (this.isPositive() || this.isZero());
	}

	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Number)) return false;
	    final Number otherNumber = (Number) other;
	    return value.compareTo(otherNumber.copyValue()) == 0;
	}
	
	@Override
	public String toString() {
		return this.value.toPlainString();
		//return this.value.toString();
	}

	public boolean equalTo(Number number) {
		return equals(number);
	}
	
	public boolean notEqualTo(Number number) {
		return !equals(number);
	}

	public boolean greaterThan(Number number) {
		return value.compareTo(number.copyValue()) > 0;
	}

	public boolean lessThan(Number number) {
		return value.compareTo(number.copyValue()) < 0;
	}

	public boolean greaterThanOrEqualTo(Number number) {
		return value.compareTo(number.copyValue()) >= 0;
	}

	public boolean lessThanOrEqualTo(Number number) {
		return value.compareTo(number.copyValue()) <= 0;
	}
	
	public boolean eq(Number number) {
		return this.equalTo(number);
	}

	public boolean ne(Number number) {
		return !this.equalTo(number);
	}
	
	public boolean gt(Number number) {
		return this.greaterThan(number);
	}

	public boolean lt(Number number) {
		return this.lessThan(number);
	}

	public boolean gte(Number number) {
		return this.greaterThanOrEqualTo(number);
	}

	public boolean lte(Number number) {
		return this.lessThanOrEqualTo(number);
	}
	
	public int intValue() {
		return value.intValueExact();
	}
	
	public long longValue() {
		return value.longValueExact();
	}

	public float floatValue() {
		return value.floatValue();
	}
	
	public double doubleValue() {
		return value.doubleValue();
	}

	public static Number n(double value) {
		return getInstance(value);
	}
	
	public static Number n(String value) {
		return getInstance(value);
	}
	
	public static Number n(BigDecimal value) {
		return getInstance(value);
	}
	
	public static int i(Number number) {
		return number.intValue();
	}
	
	public static long l(Number number) {
		return number.longValue();
	}
	
	public static float f(Number number) {
		return number.floatValue();
	}
	
	public static double d(Number number) {
		return number.doubleValue();
	}
	
	public int i() {
		return this.intValue();
	}
	
	public long l() {
		return this.longValue();
	}
	
	public float f() {
		return this.floatValue();
	}
	
	public double d() {
		return this.doubleValue();
	}
	
	public Number abs() {
		return getInstance(this.value.abs(context));
	}

	public Number negate() {
		if(this.isZero())
			return this.abs();
		else
			return getInstance(this.value.negate(context));
	}

	public Number max(Number number) {
		return getInstance(value.max(number.copyValue()));
	}

	public Number min(Number number) {
		return getInstance(value.min(number.copyValue()));
	}

	public Number add(Number number) {
		return getInstance(value.add(number.copyValue()));
	}

	public Number subtract(Number number) {
		return getInstance(value.subtract(number.copyValue(), context));
	}

	public Number multiply(Number number) {
		return getInstance(value.multiply(number.copyValue(), context));
	}

	public Number divide(Number number) {
		return getInstance(value.divide(number.copyValue(), context));
	}

	public Number remainder(Number number) {
		return getInstance(value.remainder(number.copyValue(), context));
	}
	
	public Number reciprocal() {
		return ONE.divide(this);
	}

	public Number signum() {
		return getInstance(value.signum());
	}
	
	public Number pow(Number exponent) {
		// TODO: replace java.math
		return getInstance(Math.pow(this.value.doubleValue(), exponent.copyValue().doubleValue()));
	}

	public Number nrt(Number number) {
		return pow(number.reciprocal());
	}

	public Number sqrt() {
		return nrt(TWO);
	}

	public Number cbrt() {
		return nrt(THREE);
	}

	public Number exp() {
		return E.pow(this);
	}

	public Number log() {
		// TODO: replace java.math
		return getInstance(Math.log(value.doubleValue()));
	}

	public Number log10() {
		return this.log().divide(TEN.log());
	}

	public Number fractionalPart() {
		return this.remainder(ONE);
	}

	public Number integralPart() {
		return this.subtract(this.fractionalPart());
	}

	public Number floor() {
		return this.integralPart().subtract(this.isNegative() ? ONE : ZERO);
	}

	public Number ceil() {
		return this.integralPart().add(this.isNegative() ? ZERO : ONE);
	}
	
	public Number round() {
		return this.round(scale);
	}
	
	public Number round(int scale) {
		return getInstance(this.copyValue().setScale(scale, RoundingMode.HALF_UP));
	}
	
	public Number factorial() {
		Number ans = ONE;
		Number n = this.copy();

		while (n.greaterThan(ZERO)) {
			ans = ans.multiply(n);
			n = n.subtract(ONE);
		}

		return ans;
	}
	
	public Number toDegrees() {
		Number k = PI.divide(getInstance(180));
		return this.divide(k);
	}

	public Number toRadians() {
		Number k = PI.divide(getInstance(180));
		return this.multiply(k);
	}
	
	public Number sin() {
		// TODO: replace java.math
		return getInstance(Math.sin(value.doubleValue()));
	}

	public Number cos() {
		return PI.multiply(HALF).subtract(this).sin();
	}

	public Number tan() {
		return this.sin().divide(this.cos());
	}

}
