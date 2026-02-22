package net.sf.finanx.fx12c.utils;

// TODO: Refactor FinanxStack to use Calendar instead of this Date
public class Date {

	private int day;
	private int month;
	private int year;
	private boolean valid;

	public Date() {
		this.day = 0;
		this.month = 0;
		this.year = 0;
	}

	public Date(Date date) {
		this.day = date.getDay();
		this.month = date.getMonth();
		this.year = date.getYear();
		this.validate();
	}

	public Date(int year, int month, int day) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.validate();
	}

	public void setDay(int day) {

		int y = this.year;
		int m = this.month;
		int d = 0;

		if (day < 1) {

			for (int i = 0; i < Math.abs(day); i++) {

				if (d <= 1) {
					if (m == 1) {
						d = 31;
						m = 13;
						y--;
					} else if (m == 2) {
						d = 31;
					} else if (m == 3) {
						if (!isGregorianLeapYear(y)) {
							d = 28;
						} else {
							d = 29;
						}
					} else if (m == 4) {
						d = 31;
					} else if (m == 5) {
						d = 30;
					} else if (m == 6) {
						d = 31;
					} else if (m == 7) {
						d = 30;
					} else if (m == 8) {
						d = 31;
					} else if (m == 9) {
						d = 31;
					} else if (m == 10) {
						d = 30;
					} else if (m == 11) {
						d = 31;
					} else if (m == 12) {
						d = 30;
					}
					m--;
				} else {
					d--;
				}
			}

			this.day = d;
			this.month = m;
			this.year = y;

		} else if (day > 27) {
			for (int i = 0; i < Math.abs(day) + 1; i++) {

				d++;

				if (m == 2) {
					if (!isGregorianLeapYear(y)) {
						if (d > 28) {
							d = 1;
							m++;
						}
					} else {
						if (d > 29) {
							d = 1;
							m++;
						}
					}

				} else if ((m == 1) || (m == 3) || (m == 5) || (m == 7) || (m == 8) || (m == 10)) {
					if (d > 31) {
						d = 1;
						m++;
					}
				} else if ((m == 4) || (m == 6) || (m == 9) || (m == 11)) {
					if (d > 30) {
						d = 1;
						m++;
					}
				} else if (m == 12) {
					if (d > 31) {
						d = 1;
						m = 1;
						y++;
					}
				}

			}

			this.day = d;
			this.month = m;
			this.year = y;

		} else {
			this.day = day;
		}

		this.validate();
	}

	public void setMonth(int month) {

		if (month < 1) {

			// Little fix :P
			month++;

			int tmp[] = new int[2];

			tmp[0] = Math.abs(month) % 12;
			tmp[1] = Math.abs(month) / 12;

			this.month = 12 - tmp[0];
			this.year = this.year - (tmp[1] + 1);

			if (this.month == 12) {
				this.month = 1;
				this.year++;
			} else {
				this.month++;
			}

		} else if (month > 12) {

			int tmp[] = new int[2];

			tmp[0] = month % 12;
			tmp[1] = month / 12;

			this.month = tmp[0] + 1;
			this.year = this.year + tmp[1];
		} else {
			this.month = month;
		}

		this.validate();
	}

	public void setYear(int year) {
		this.year = year;
		this.validate();
	}

	public int getDay() {
		return this.day;
	}

	public int getMonth() {
		return this.month;
	}

	public int getYear() {
		return this.year;
	}

	public boolean isValid() {
		return this.valid;
	}

	public void validate() {

		this.valid = true;

		double y = this.year;
		double m = this.month;
		double d = this.day;

		if ((y > 9999) || (y <= 0)) {
			this.valid = false;
		} else

		if ((m > 12) || (m <= 0)) {
			this.valid = false;
		} else

		if (d <= 0) {
			this.valid = false;
		} else

		if (m == 2) {
			if (!isGregorianLeapYear(y)) {
				if ((d > 28) || (d <= 0)) {
					this.valid = false;
				}
			} else {
				if ((d > 29) || (d <= 0)) {
					this.valid = false;
				}
			}
		} else if ((m == 1) || (m == 3) || (m == 5) || (m == 7) || (m == 8) || (m == 10) || (m == 12)) {
			if ((d > 31) || (d <= 0)) {
				this.valid = false;
			}
		} else if ((m == 4) || (m == 6) || (m == 9) || (m == 11)) {
			if ((d > 30) || (d <= 0)) {
				this.valid = false;
			}
		}
	}

	// getWeekDay
	// Returns the day of the week in integer value.
	// Valid return values: Monday=1, Tuesday=2, Thursday=3, Wednesday=4,
	// Saturday=6, Sun=7
	public int getWeekDay() {
		if (valid) {
			double serial = ((getSerial() - 1) % 7);
			return (int) (serial == 0 ? 7 : serial);
		}
		return 0;
	}

	public double gregorianToJulian(double d, double m, double y) {
		double GREGORIAN_EPOCH = 1721425.5;
		return (GREGORIAN_EPOCH - 1) + (365 * (y - 1)) + Math.floor((y - 1) / 4) + (-Math.floor((y - 1) / 100))
				+ Math.floor((y - 1) / 400)
				+ Math.floor((((367 * m) - 362) / 12) + ((m <= 2) ? 0 : (isGregorianLeapYear(y) ? -1 : -2)) + d);

	}

	public boolean isGregorianLeapYear(double year) {
		return ((year % 4) == 0) && (!(((year % 100) == 0) && ((year % 400) != 0)));
	}

	// Returns a serial number counting from December, 30th 1899
	// based in civil year (with all months having 28, 29, 30 or 31 days)
	public int getSerial() {

		double dd = this.day;
		double mm = this.month;
		double yyyy = this.year;

		double x = 0.0, z = 0.0;

		// Tests based on HP-12C user guide
		if (mm <= 2) {
			x = 0.0;
			z = (yyyy) - 1.0;
		} else {
			x = (int) (0.4 * (mm) + 2.3);
			z = (yyyy);
		}

		// Formula based on HP-12C user guide
		double dt = 365.0 * (yyyy) + 31.0 * (mm - 1.0) + dd + (int) (z / 4.0) - x;

		// Sets the counting begin to December, 30th 1899 (inclusive)
		dt = dt - 693973.0;

		// Additional tests executed to asure that the centure years wont be
		// considered as leap years, except the ones that are divisible by 400.
		for (int i = 1899; i < yyyy; i++) {
			if ((i % 100 == 0) && !(i % 400 == 0)) {
				dt = dt - 1;
			}
		}

		return (int) dt;
	}

	public static int diffDates(Date begDate, Date endDate) {
		int a = begDate.getSerial();
		int b = endDate.getSerial();
		return b - a;
	}

	@Override
	public boolean equals(Object object) {
		Date date = (Date) object;
		return this.year == date.year && this.month == date.month && this.day == date.day;
	}
}
