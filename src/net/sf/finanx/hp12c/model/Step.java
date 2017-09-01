package net.sf.finanx.hp12c.model;

public class Step {

	// Commands without modifiers
	public static final Step STP_0 = new Step(-1, 0, -1);
	public static final Step STP_1 = new Step(-1, 1, -1);
	public static final Step STP_2 = new Step(-1, 2, -1);
	public static final Step STP_3 = new Step(-1, 3, -1);
	public static final Step STP_4 = new Step(-1, 4, -1);
	public static final Step STP_5 = new Step(-1, 5, -1);
	public static final Step STP_6 = new Step(-1, 6, -1);
	public static final Step STP_7 = new Step(-1, 7, -1);
	public static final Step STP_8 = new Step(-1, 8, -1);
	public static final Step STP_9 = new Step(-1, 9, -1);

	public static final Step STP_DIV = new Step(-1, 10, -1);
	public static final Step STP_N = new Step(-1, 11, -1);
	public static final Step STP_I = new Step(-1, 12, -1);
	public static final Step STP_PV = new Step(-1, 13, -1);
	public static final Step STP_PMT = new Step(-1, 14, -1);
	public static final Step STP_FV = new Step(-1, 15, -1);
	public static final Step STP_CHS = new Step(-1, 16, -1);

	public static final Step STP_MUL = new Step(-1, 20, -1);
	public static final Step STP_POW = new Step(-1, 21, -1);
	public static final Step STP_RECIPROCAL = new Step(-1, 22, -1);
	public static final Step STP_PERC_TOT = new Step(-1, 23, -1);
	public static final Step STP_PERC_DELTA = new Step(-1, 24, -1);
	public static final Step STP_PERC = new Step(-1, 25, -1);
	public static final Step STP_EEX = new Step(-1, 26, -1);

	public static final Step STP_SUB = new Step(-1, 30, -1);
	public static final Step STP_RS = new Step(-1, 31, -1);
	public static final Step STP_SST = new Step(-1, 32, -1);
	public static final Step STP_ROLL = new Step(-1, 33, -1);
	public static final Step STP_XY = new Step(-1, 34, -1);
	public static final Step STP_CLX = new Step(-1, 35, -1);
	public static final Step STP_ENTER = new Step(-1, 36, -1);

	public static final Step STP_SUM = new Step(-1, 40, -1);
	public static final Step STP_ON = new Step(-1, 41, -1);
	public static final Step STP_F = new Step(-1, 42, -1);
	public static final Step STP_G = new Step(-1, 43, -1);
	public static final Step STP_STO = new Step(-1, 44, -1);
	public static final Step STP_RCL = new Step(-1, 45, -1);
	public static final Step STP_DOT = new Step(-1, 48, -1);
	public static final Step STP_TOT = new Step(-1, 49, -1);

	// Commands with 'f' modifier
	public static final Step STP_F_0 = new Step(42, 0, -1);
	public static final Step STP_F_1 = new Step(42, 1, -1);
	public static final Step STP_F_2 = new Step(42, 2, -1);
	public static final Step STP_F_3 = new Step(42, 3, -1);
	public static final Step STP_F_4 = new Step(42, 4, -1);
	public static final Step STP_F_5 = new Step(42, 5, -1);
	public static final Step STP_F_6 = new Step(42, 6, -1);
	public static final Step STP_F_7 = new Step(42, 7, -1);
	public static final Step STP_F_8 = new Step(42, 8, -1);
	public static final Step STP_F_9 = new Step(42, 9, -1);

	public static final Step STP_F_DIV = new Step(42, 10, -1);
	public static final Step STP_F_N = new Step(42, 11, -1);
	public static final Step STP_F_I = new Step(42, 12, -1);
	public static final Step STP_F_PV = new Step(42, 13, -1);
	public static final Step STP_F_PMT = new Step(42, 14, -1);
	public static final Step STP_F_FV = new Step(42, 15, -1);
	public static final Step STP_F_CHS = new Step(42, 16, -1);

	public static final Step STP_F_MUL = new Step(42, 20, -1);
	public static final Step STP_F_POW = new Step(42, 21, -1);
	public static final Step STP_F_RECIPROCAL = new Step(42, 22, -1);
	public static final Step STP_F_PERC_TOT = new Step(42, 23, -1);
	public static final Step STP_F_PERC_DELTA = new Step(42, 24, -1);
	public static final Step STP_F_PERC = new Step(42, 25, -1);
	public static final Step STP_F_EEX = new Step(42, 26, -1);

	public static final Step STP_F_SUB = new Step(42, 30, -1);
	public static final Step STP_F_RS = new Step(42, 31, -1);
	public static final Step STP_F_SST = new Step(42, 32, -1);
	public static final Step STP_F_ROLL = new Step(42, 33, -1);
	public static final Step STP_F_XY = new Step(42, 34, -1);
	public static final Step STP_F_CLX = new Step(42, 35, -1);
	public static final Step STP_F_ENTER = new Step(42, 36, -1);

	public static final Step STP_F_SUM = new Step(42, 40, -1);
	public static final Step STP_F_ON = new Step(42, 41, -1);
	public static final Step STP_F_F = new Step(42, 42, -1);
	public static final Step STP_F_G = new Step(42, 43, -1);
	public static final Step STP_F_STO = new Step(42, 44, -1);
	public static final Step STP_F_RCL = new Step(42, 45, -1);
	public static final Step STP_F_DOT = new Step(42, 48, -1);
	public static final Step STP_F_TOT = new Step(42, 49, -1);

	// Commands with 'g' modifier
	public static final Step STP_G_0 = new Step(43, 0, -1);
	public static final Step STP_G_1 = new Step(43, 1, -1);
	public static final Step STP_G_2 = new Step(43, 2, -1);
	public static final Step STP_G_3 = new Step(43, 3, -1);
	public static final Step STP_G_4 = new Step(43, 4, -1);
	public static final Step STP_G_5 = new Step(43, 5, -1);
	public static final Step STP_G_6 = new Step(43, 6, -1);
	public static final Step STP_G_7 = new Step(43, 7, -1);
	public static final Step STP_G_8 = new Step(43, 8, -1);
	public static final Step STP_G_9 = new Step(43, 9, -1);

	public static final Step STP_G_DIV = new Step(43, 10, -1);
	public static final Step STP_G_N = new Step(43, 11, -1);
	public static final Step STP_G_I = new Step(43, 12, -1);
	public static final Step STP_G_PV = new Step(43, 13, -1);
	public static final Step STP_G_PMT = new Step(43, 14, -1);
	public static final Step STP_G_FV = new Step(43, 15, -1);
	public static final Step STP_G_CHS = new Step(43, 16, -1);

	public static final Step STP_G_MUL = new Step(43, 20, -1);
	public static final Step STP_G_POW = new Step(43, 21, -1);
	public static final Step STP_G_RECIPROCAL = new Step(43, 22, -1);
	public static final Step STP_G_PERC_TOT = new Step(43, 23, -1);
	public static final Step STP_G_PERC_DELTA = new Step(43, 24, -1);
	public static final Step STP_G_PERC = new Step(43, 25, -1);
	public static final Step STP_G_EEX = new Step(43, 26, -1);

	public static final Step STP_G_SUB = new Step(43, 30, -1);
	public static final Step STP_G_RS = new Step(43, 31, -1);
	public static final Step STP_G_SST = new Step(43, 32, -1);
	public static final Step STP_G_ROLL = new Step(43, 33, -1);
	public static final Step STP_G_XY = new Step(43, 34, -1);
	public static final Step STP_G_CLX = new Step(43, 35, -1);
	public static final Step STP_G_ENTER = new Step(43, 36, -1);

	public static final Step STP_G_SUM = new Step(43, 40, -1);
	public static final Step STP_G_ON = new Step(43, 41, -1);
	public static final Step STP_G_F = new Step(43, 42, -1);
	public static final Step STP_G_G = new Step(43, 43, -1);
	public static final Step STP_G_STO = new Step(43, 44, -1);
	public static final Step STP_G_RCL = new Step(43, 45, -1);
	public static final Step STP_G_DOT = new Step(43, 48, -1);
	public static final Step STP_G_TOT = new Step(43, 49, -1);

	// Commands with 'sto' modifier
	public static final Step STP_STO_0 = new Step(44, 0, -1);
	public static final Step STP_STO_1 = new Step(44, 1, -1);
	public static final Step STP_STO_2 = new Step(44, 2, -1);
	public static final Step STP_STO_3 = new Step(44, 3, -1);
	public static final Step STP_STO_4 = new Step(44, 4, -1);
	public static final Step STP_STO_5 = new Step(44, 5, -1);
	public static final Step STP_STO_6 = new Step(44, 6, -1);
	public static final Step STP_STO_7 = new Step(44, 7, -1);
	public static final Step STP_STO_8 = new Step(44, 8, -1);
	public static final Step STP_STO_9 = new Step(44, 9, -1);

	public static final Step STP_STO_DIV = new Step(44, 10, -1);
	public static final Step STP_STO_N = new Step(44, 11, -1);
	public static final Step STP_STO_I = new Step(44, 12, -1);
	public static final Step STP_STO_PV = new Step(44, 13, -1);
	public static final Step STP_STO_PMT = new Step(44, 14, -1);
	public static final Step STP_STO_FV = new Step(44, 15, -1);
	public static final Step STP_STO_CHS = new Step(44, 16, -1);

	public static final Step STP_STO_MUL = new Step(44, 20, -1);
	public static final Step STP_STO_POW = new Step(44, 21, -1);
	public static final Step STP_STO_RECIPROCAL = new Step(44, 22, -1);
	public static final Step STP_STO_PERC_TOT = new Step(44, 23, -1);
	public static final Step STP_STO_PERC_DELTA = new Step(44, 24, -1);
	public static final Step STP_STO_PERC = new Step(44, 25, -1);
	public static final Step STP_STO_EEX = new Step(44, 26, -1);

	public static final Step STP_STO_SUB = new Step(44, 30, -1);
	public static final Step STP_STO_RS = new Step(44, 31, -1);
	public static final Step STP_STO_SST = new Step(44, 32, -1);
	public static final Step STP_STO_ROLL = new Step(44, 33, -1);
	public static final Step STP_STO_XY = new Step(44, 34, -1);
	public static final Step STP_STO_CLX = new Step(44, 35, -1);
	public static final Step STP_STO_ENTER = new Step(44, 36, -1);

	public static final Step STP_STO_SUM = new Step(44, 40, -1);
	public static final Step STP_STO_ON = new Step(44, 41, -1);
	public static final Step STP_STO_F = new Step(44, 42, -1);
	public static final Step STP_STO_G = new Step(44, 43, -1);
	public static final Step STP_STO_STO = new Step(44, 44, -1);
	public static final Step STP_STO_RCL = new Step(44, 45, -1);
	public static final Step STP_STO_DOT = new Step(44, 48, -1);
	public static final Step STP_STO_TOT = new Step(44, 49, -1);

	// Commands with 'rcl' modifier
	public static final Step STP_RCL_0 = new Step(45, 0, -1);
	public static final Step STP_RCL_1 = new Step(45, 1, -1);
	public static final Step STP_RCL_2 = new Step(45, 2, -1);
	public static final Step STP_RCL_3 = new Step(45, 3, -1);
	public static final Step STP_RCL_4 = new Step(45, 4, -1);
	public static final Step STP_RCL_5 = new Step(45, 5, -1);
	public static final Step STP_RCL_6 = new Step(45, 6, -1);
	public static final Step STP_RCL_7 = new Step(45, 7, -1);
	public static final Step STP_RCL_8 = new Step(45, 8, -1);
	public static final Step STP_RCL_9 = new Step(45, 9, -1);

	public static final Step STP_RCL_DIV = new Step(45, 10, -1);
	public static final Step STP_RCL_N = new Step(45, 11, -1);
	public static final Step STP_RCL_I = new Step(45, 12, -1);
	public static final Step STP_RCL_PV = new Step(45, 13, -1);
	public static final Step STP_RCL_PMT = new Step(45, 14, -1);
	public static final Step STP_RCL_FV = new Step(45, 15, -1);
	public static final Step STP_RCL_CHS = new Step(45, 16, -1);

	public static final Step STP_RCL_MUL = new Step(45, 20, -1);
	public static final Step STP_RCL_POW = new Step(45, 21, -1);
	public static final Step STP_RCL_RECIPROCAL = new Step(45, 22, -1);
	public static final Step STP_RCL_PERC_TOT = new Step(45, 23, -1);
	public static final Step STP_RCL_PERC_DELTA = new Step(45, 24, -1);
	public static final Step STP_RCL_PERC = new Step(45, 25, -1);
	public static final Step STP_RCL_EEX = new Step(45, 26, -1);

	public static final Step STP_RCL_SUB = new Step(45, 30, -1);
	public static final Step STP_RCL_RS = new Step(45, 31, -1);
	public static final Step STP_RCL_SST = new Step(45, 32, -1);
	public static final Step STP_RCL_ROLL = new Step(45, 33, -1);
	public static final Step STP_RCL_XY = new Step(45, 34, -1);
	public static final Step STP_RCL_CLX = new Step(45, 35, -1);
	public static final Step STP_RCL_ENTER = new Step(45, 36, -1);

	public static final Step STP_RCL_SUM = new Step(45, 40, -1);
	public static final Step STP_RCL_ON = new Step(45, 41, -1);
	public static final Step STP_RCL_F = new Step(45, 42, -1);
	public static final Step STP_RCL_G = new Step(45, 43, -1);
	public static final Step STP_RCL_STO = new Step(45, 44, -1);
	public static final Step STP_RCL_RCL = new Step(45, 45, -1);
	public static final Step STP_RCL_DOT = new Step(45, 48, -1);
	public static final Step STP_RCL_TOT = new Step(45, 49, -1);


	private int mod, key, cpm;

	public Step() {
		this.setUndefined();
		this.init();
	}

	public Step(int mod, int key, int cpm) {
		this.mod = mod;
		this.key = key;
		this.cpm = cpm;

		this.init();
	}

	public Step(Step stp) {
		this.mod = stp.getModifier();
		this.key = stp.getKey();
		this.cpm = stp.getComplement();

		this.init();
	}

	public Step(int[] stp) {
		this(stp[0], stp[1], stp[2]);
	}
	
	protected void init() {/* Does Nothing */}
	
	public void setStep(int mod, int key, int cpm) {
		this.mod = mod;
		this.key = key;
		this.cpm = cpm;

		this.init();
	}

	public int getModifier() {
		return this.mod;
	}

	public int getKey() {
		return this.key;
	}

	public int getComplement() {
		return this.cpm;
	}

	public void setModifier(int mod) {
		this.mod = mod;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public void setComplement(int cpm) {
		this.cpm = cpm;
	}
	
	public int[] getArray() {
		int[] rtn = { this.getModifier(), this.getKey(), this.getComplement() };
		return rtn;
	}

	public void clear() {
		this.mod = -1;
		this.key = -1;
		this.cpm = -1;
	}

	public boolean isUndefined() {
		return isUndefined(this);
	}
	
	public static boolean isUndefined(Step step) {
		return step.mod == -1 || step.key == -1 || step.cpm == -1;
	}
	
	public void setUndefined() {
		setUndefined(this);
	}
	
	public static void setUndefined(Step step) {
		step.clear();
	}
	
	public static Step getUndefined() {
		return new Step();
	}
	
	public void print() {
		System.out.println(this);
	}

	public String toString() {

		String rtn = "==[STEP]============\n";
		rtn += " - Stp: ";
		rtn += this.mod + ", ";
		rtn += this.key + ", ";
		rtn += this.cpm + "\n";

		return rtn;
	}

	public boolean equals(Step stp) {

		return (this.getModifier() == stp.getModifier())
				&& (this.getKey() == stp.getKey())
				&& (this.getComplement() == stp.getComplement());

	}

	public void setStep(Step stp) {
		this.setModifier(stp.getModifier());
		this.setKey(stp.getKey());
		this.setComplement(stp.getComplement());
	}

	public void setArray(int[] step) {
		this.setModifier(step[0]);
		this.setKey(step[1]);
		this.setComplement(step[2]);
	}

	public boolean isFinancialStep() {
		return (this.equals(STP_N) || this.equals(STP_I) || this.equals(STP_PV)
				|| this.equals(STP_PMT) || this.equals(STP_FV));
	}

	// TODO: Function used to make translation easier.
	public String getStepId(Step stp) {
		String str = "";

		if (this.getModifier() == -1) { // Modifier: none
			if (this.getKey() == 0)
				str = "STP_0";
			else if (this.getKey() == 1)
				str = "STP_1";
			else if (this.getKey() == 2)
				str = "STP_2";
			else if (this.getKey() == 3)
				str = "STP_3";
			else if (this.getKey() == 4)
				str = "STP_4";
			else if (this.getKey() == 5)
				str = "STP_5";
			else if (this.getKey() == 6)
				str = "STP_6";
			else if (this.getKey() == 7)
				str = "STP_7";
			else if (this.getKey() == 8)
				str = "STP_8";
			else if (this.getKey() == 9)
				str = "STP_9";
			else if (this.getKey() == 10)
				str = "STP_DIV";
			else if (this.getKey() == 11)
				str = "STP_N";
			else if (this.getKey() == 12)
				str = "STP_I";
			else if (this.getKey() == 13)
				str = "STP_PV";
			else if (this.getKey() == 14)
				str = "STP_PMT";
			else if (this.getKey() == 15)
				str = "STP_FV";
			else if (this.getKey() == 16)
				str = "STP_CHS";
			else if (this.getKey() == 20)
				str = "STP_MUL";
			else if (this.getKey() == 21)
				str = "STP_POW";
			else if (this.getKey() == 22)
				str = "STP_RECIPROCAL";
			else if (this.getKey() == 23)
				str = "STP_PERC_TOT";
			else if (this.getKey() == 24)
				str = "STP_PERC_DELTA";
			else if (this.getKey() == 25)
				str = "STP_PERC";
			else if (this.getKey() == 26)
				str = "STP_EEX";
			else if (this.getKey() == 30)
				str = "STP_SUB";
			else if (this.getKey() == 31)
				str = "STP_RS";
			else if (this.getKey() == 32)
				str = "STP_SST";
			else if (this.getKey() == 33)
				str = "STP_ROLL";
			else if (this.getKey() == 34)
				str = "STP_XY";
			else if (this.getKey() == 35)
				str = "STP_CLX";
			else if (this.getKey() == 36)
				str = "STP_ENTER";
			else if (this.getKey() == 40)
				str = "STP_SUM";
			else if (this.getKey() == 41)
				str = "STP_ON";
			else if (this.getKey() == 42)
				str = "STP_F";
			else if (this.getKey() == 43)
				str = "STP_G";
			else if (this.getKey() == 44)
				str = "STP_STO";
			else if (this.getKey() == 45)
				str = "STP_RCL";
			else if (this.getKey() == 48)
				str = "STP_DOT";
			else if (this.getKey() == 49)
				str = "STP_TOT";
		} else if (this.getModifier() == 42) { // Modifier: f
			if (this.getKey() == 0)
				str = "STP_F_0";
			else if (this.getKey() == 1)
				str = "STP_F_1";
			else if (this.getKey() == 2)
				str = "STP_F_2";
			else if (this.getKey() == 3)
				str = "STP_F_3";
			else if (this.getKey() == 4)
				str = "STP_F_4";
			else if (this.getKey() == 5)
				str = "STP_F_5";
			else if (this.getKey() == 6)
				str = "STP_F_6";
			else if (this.getKey() == 7)
				str = "STP_F_7";
			else if (this.getKey() == 8)
				str = "STP_F_8";
			else if (this.getKey() == 9)
				str = "STP_F_9";
			else if (this.getKey() == 10)
				str = "STP_F_DIV";
			else if (this.getKey() == 11)
				str = "STP_F_N";
			else if (this.getKey() == 12)
				str = "STP_F_I";
			else if (this.getKey() == 13)
				str = "STP_F_PV";
			else if (this.getKey() == 14)
				str = "STP_F_PMT";
			else if (this.getKey() == 15)
				str = "STP_F_FV";
			else if (this.getKey() == 16)
				str = "STP_F_CHS";
			else if (this.getKey() == 20)
				str = "STP_F_MUL";
			else if (this.getKey() == 21)
				str = "STP_F_POW";
			else if (this.getKey() == 22)
				str = "STP_F_RECIPROCAL";
			else if (this.getKey() == 23)
				str = "STP_F_PERC_TOT";
			else if (this.getKey() == 24)
				str = "STP_F_PERC_DELTA";
			else if (this.getKey() == 25)
				str = "STP_F_PERC";
			else if (this.getKey() == 26)
				str = "STP_F_EEX";
			else if (this.getKey() == 30)
				str = "STP_F_SUB";
			else if (this.getKey() == 31)
				str = "STP_F_RS";
			else if (this.getKey() == 32)
				str = "STP_F_SST";
			else if (this.getKey() == 33)
				str = "STP_F_ROLL";
			else if (this.getKey() == 34)
				str = "STP_F_XY";
			else if (this.getKey() == 35)
				str = "STP_F_CLX";
			else if (this.getKey() == 36)
				str = "STP_F_ENTER";
			else if (this.getKey() == 40)
				str = "STP_F_SUM";
			else if (this.getKey() == 41)
				str = "STP_F_ON";
			else if (this.getKey() == 42)
				str = "STP_F_F";
			else if (this.getKey() == 43)
				str = "STP_F_G";
			else if (this.getKey() == 44)
				str = "STP_F_STO";
			else if (this.getKey() == 45)
				str = "STP_F_RCL";
			else if (this.getKey() == 48)
				str = "STP_F_DOT";
			else if (this.getKey() == 49)
				str = "STP_F_TOT";
		} else if (this.getModifier() == 43) { // Modifier: g
			if (this.getKey() == 0)
				str = "STP_G_0";
			else if (this.getKey() == 1)
				str = "STP_G_1";
			else if (this.getKey() == 2)
				str = "STP_G_2";
			else if (this.getKey() == 3)
				str = "STP_G_3";
			else if (this.getKey() == 4)
				str = "STP_G_4";
			else if (this.getKey() == 5)
				str = "STP_G_5";
			else if (this.getKey() == 6)
				str = "STP_G_6";
			else if (this.getKey() == 7)
				str = "STP_G_7";
			else if (this.getKey() == 8)
				str = "STP_G_8";
			else if (this.getKey() == 9)
				str = "STP_G_9";
			else if (this.getKey() == 10)
				str = "STP_G_DIV";
			else if (this.getKey() == 11)
				str = "STP_G_N";
			else if (this.getKey() == 12)
				str = "STP_G_I";
			else if (this.getKey() == 13)
				str = "STP_G_PV";
			else if (this.getKey() == 14)
				str = "STP_G_PMT";
			else if (this.getKey() == 15)
				str = "STP_G_FV";
			else if (this.getKey() == 16)
				str = "STP_G_CHS";
			else if (this.getKey() == 20)
				str = "STP_G_MUL";
			else if (this.getKey() == 21)
				str = "STP_G_POW";
			else if (this.getKey() == 22)
				str = "STP_G_RECIPROCAL";
			else if (this.getKey() == 23)
				str = "STP_G_PERC_TOT";
			else if (this.getKey() == 24)
				str = "STP_G_PERC_DELTA";
			else if (this.getKey() == 25)
				str = "STP_G_PERC";
			else if (this.getKey() == 26)
				str = "STP_G_EEX";
			else if (this.getKey() == 30)
				str = "STP_G_SUB";
			else if (this.getKey() == 31)
				str = "STP_G_RS";
			else if (this.getKey() == 32)
				str = "STP_G_SST";
			else if (this.getKey() == 33)
				str = "STP_G_ROLL";
			else if (this.getKey() == 34)
				str = "STP_G_XY";
			else if (this.getKey() == 35)
				str = "STP_G_CLX";
			else if (this.getKey() == 36)
				str = "STP_G_ENTER";
			else if (this.getKey() == 40)
				str = "STP_G_SUM";
			else if (this.getKey() == 41)
				str = "STP_G_ON";
			else if (this.getKey() == 42)
				str = "STP_G_F";
			else if (this.getKey() == 43)
				str = "STP_G_G";
			else if (this.getKey() == 44)
				str = "STP_G_STO";
			else if (this.getKey() == 45)
				str = "STP_G_RCL";
			else if (this.getKey() == 48)
				str = "STP_G_DOT";
			else if (this.getKey() == 49)
				str = "STP_G_TOT";
		} else
			str = "";

		return str;
	}
}
