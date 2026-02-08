package net.sf.finanx.fx12c.calc;

public enum Op {

	// @formatter:off
	// Commands without modifiers
	 OP_0(null, Key.KEY_0),
	 OP_1(null, Key.KEY_1),
	 OP_2(null, Key.KEY_2),
	 OP_3(null, Key.KEY_3),
	 OP_4(null, Key.KEY_4),
	 OP_5(null, Key.KEY_5),
	 OP_6(null, Key.KEY_6),
	 OP_7(null, Key.KEY_7),
	 OP_8(null, Key.KEY_8),
	 OP_9(null, Key.KEY_9),

	 OP_DIV(null, Key.KEY_DIV),
	 OP_N(null, Key.KEY_N),
	 OP_I(null, Key.KEY_I),
	 OP_PV(null, Key.KEY_PV),
	 OP_PMT(null, Key.KEY_PMT),
	 OP_FV(null, Key.KEY_FV),
	 OP_CHS(null, Key.KEY_CHS),

	 OP_MUL(null, Key.KEY_MUL),
	 OP_POWER(null, Key.KEY_POWER),
	 OP_RECIPROCAL(null, Key.KEY_RECIPROCAL),
	 OP_PERCENT_T(null, Key.KEY_PERCENT_T),
	 OP_DELTA_PERCENT(null, Key.KEY_DELTA_PERCENT),
	 OP_PERCENT(null, Key.KEY_PERCENT),
	 OP_EEX(null, Key.KEY_EEX),

	 OP_SUB(null, Key.KEY_SUB),
	 OP_RS(null, Key.KEY_RS),
	 OP_SST(null, Key.KEY_SST),
	 OP_R_DOWN(null, Key.KEY_R_DOWN),
	 OP_SWAP_XY(null, Key.KEY_SWAP_XY),
	 OP_CLX(null, Key.KEY_CLX),
	 OP_ENTER(null, Key.KEY_ENTER),

	 OP_ADD(null, Key.KEY_ADD),
	 OP_ON(null, Key.KEY_ON),
	 OP_F(null, Key.KEY_F),
	 OP_G(null, Key.KEY_G),
	 OP_STO(null, Key.KEY_STO),
	 OP_RCL(null, Key.KEY_RCL),
	 OP_DOT(null, Key.KEY_DOT),
	 OP_SIGMA_PLUS(null, Key.KEY_SIGMA_PLUS),

	// Commands with 'f' modifier
	 OP_F_0(Key.KEY_F, Key.KEY_0),
	 OP_F_1(Key.KEY_F, Key.KEY_1),
	 OP_F_2(Key.KEY_F, Key.KEY_2),
	 OP_F_3(Key.KEY_F, Key.KEY_3),
	 OP_F_4(Key.KEY_F, Key.KEY_4),
	 OP_F_5(Key.KEY_F, Key.KEY_5),
	 OP_F_6(Key.KEY_F, Key.KEY_6),
	 OP_F_7(Key.KEY_F, Key.KEY_7),
	 OP_F_8(Key.KEY_F, Key.KEY_8),
	 OP_F_9(Key.KEY_F, Key.KEY_9),

	 OP_AMORT(Key.KEY_F, Key.KEY_N),
	 OP_INT(Key.KEY_F, Key.KEY_I),
	 OP_NPV(Key.KEY_F, Key.KEY_PV),
	 OP_RND(Key.KEY_F, Key.KEY_PMT),
	 OP_IRR(Key.KEY_F, Key.KEY_FV),

	 OP_PRICE(Key.KEY_F, Key.KEY_POWER),
	 OP_YTM(Key.KEY_F, Key.KEY_RECIPROCAL),
	 OP_SL(Key.KEY_F, Key.KEY_PERCENT),
	 OP_SOYD(Key.KEY_F, Key.KEY_DELTA_PERCENT),
	 OP_DB(Key.KEY_F, Key.KEY_PERCENT),

	 OP_PR(Key.KEY_F, Key.KEY_RS),
	 OP_SIGMA(Key.KEY_F, Key.KEY_SST),
	 OP_PRGM(Key.KEY_F, Key.KEY_R_DOWN),
	 OP_FIN(Key.KEY_F, Key.KEY_SWAP_XY),
	 OP_REG(Key.KEY_F, Key.KEY_CLX),
	 OP_PREFIX(Key.KEY_F, Key.KEY_ENTER),

	 // Commands with 'g' modifier
	 OP_STAT_X(Key.KEY_G, Key.KEY_0),
	 OP_STAT_XR(Key.KEY_G, Key.KEY_1),
	 OP_STAT_YR(Key.KEY_G, Key.KEY_2),
	 OP_FACTORIAL(Key.KEY_G, Key.KEY_3),
	 OP_DMY(Key.KEY_G, Key.KEY_4),
	 OP_MDY(Key.KEY_G, Key.KEY_5),
	 OP_STAT_XW(Key.KEY_G, Key.KEY_6),
	 OP_BEG(Key.KEY_G, Key.KEY_7),
	 OP_END(Key.KEY_G, Key.KEY_8),
	 OP_MEM(Key.KEY_G, Key.KEY_9),

	 OP_12_MUL(Key.KEY_G, Key.KEY_N),
	 OP_12_DIV(Key.KEY_G, Key.KEY_I),
	 OP_CFO(Key.KEY_G, Key.KEY_PV),
	 OP_CFJ(Key.KEY_G, Key.KEY_PMT),
	 OP_NJ(Key.KEY_G, Key.KEY_FV),
	 OP_DATE(Key.KEY_G, Key.KEY_CHS),

	 OP_SQRT(Key.KEY_G, Key.KEY_POWER),
	 OP_EX(Key.KEY_G, Key.KEY_RECIPROCAL),
	 OP_LN(Key.KEY_G, Key.KEY_PERCENT_T),
	 OP_FRAC(Key.KEY_G, Key.KEY_DELTA_PERCENT),
	 OP_INTG(Key.KEY_G, Key.KEY_PERCENT),
	 OP_DELTA_DYS(Key.KEY_G, Key.KEY_EEX),

	 OP_PSE(Key.KEY_G, Key.KEY_RS),
	 OP_BST(Key.KEY_G, Key.KEY_SST),
	 OP_GTO(Key.KEY_G, Key.KEY_R_DOWN),
	 OP_XLEY(Key.KEY_G, Key.KEY_SWAP_XY),
	 OP_XEQO(Key.KEY_G, Key.KEY_CLX),
	 OP_LASTX(Key.KEY_G, Key.KEY_ENTER),

	 OP_STAT_S(Key.KEY_G, Key.KEY_DOT),
	 OP_SIGMA_MINUS(Key.KEY_G, Key.KEY_SIGMA_PLUS),

	// Commands with 'sto' modifier
	 OP_STO_0(Key.KEY_STO, Key.KEY_0),
	 OP_STO_1(Key.KEY_STO, Key.KEY_1),
	 OP_STO_2(Key.KEY_STO, Key.KEY_2),
	 OP_STO_3(Key.KEY_STO, Key.KEY_3),
	 OP_STO_4(Key.KEY_STO, Key.KEY_4),
	 OP_STO_5(Key.KEY_STO, Key.KEY_5),
	 OP_STO_6(Key.KEY_STO, Key.KEY_6),
	 OP_STO_7(Key.KEY_STO, Key.KEY_7),
	 OP_STO_8(Key.KEY_STO, Key.KEY_8),
	 OP_STO_9(Key.KEY_STO, Key.KEY_9),

	 OP_STO_DIV(Key.KEY_STO, Key.KEY_DIV),
	 OP_STO_N(Key.KEY_STO, Key.KEY_N),
	 OP_STO_I(Key.KEY_STO, Key.KEY_I),
	 OP_STO_PV(Key.KEY_STO, Key.KEY_PV),
	 OP_STO_PMT(Key.KEY_STO, Key.KEY_PMT),
	 OP_STO_FV(Key.KEY_STO, Key.KEY_FV),
	 OP_STO_CHS(Key.KEY_STO, Key.KEY_CHS),

	 OP_STO_MUL(Key.KEY_STO, Key.KEY_MUL),
	 OP_STO_POWER(Key.KEY_STO, Key.KEY_POWER),
	 OP_STO_RECIPROCAL(Key.KEY_STO, Key.KEY_RECIPROCAL),
	 OP_STO_PERCENT_T(Key.KEY_STO, Key.KEY_PERCENT_T),
	 OP_STO_DELTA_PERCENT(Key.KEY_STO, Key.KEY_DELTA_PERCENT),
	 OP_STO_PERCENT(Key.KEY_STO, Key.KEY_PERCENT),
	 OP_STO_EEX(Key.KEY_STO, Key.KEY_EEX),

	 OP_STO_SUB(Key.KEY_STO, Key.KEY_SUB),
	 OP_STO_RS(Key.KEY_STO, Key.KEY_RS),
	 OP_STO_SST(Key.KEY_STO, Key.KEY_SST),
	 OP_STO_R_DOWN(Key.KEY_STO, Key.KEY_R_DOWN),
	 OP_STO_SWAP_XY(Key.KEY_STO, Key.KEY_SWAP_XY),
	 OP_STO_CLX(Key.KEY_STO, Key.KEY_CLX),
	 OP_STO_ENTER(Key.KEY_STO, Key.KEY_ENTER),

	 OP_STO_ADD(Key.KEY_STO, Key.KEY_ADD),

	// Commands with 'rcl' modifier
	 OP_RCL_0(Key.KEY_RCL, Key.KEY_0),
	 OP_RCL_1(Key.KEY_RCL, Key.KEY_1),
	 OP_RCL_2(Key.KEY_RCL, Key.KEY_2),
	 OP_RCL_3(Key.KEY_RCL, Key.KEY_3),
	 OP_RCL_4(Key.KEY_RCL, Key.KEY_4),
	 OP_RCL_5(Key.KEY_RCL, Key.KEY_5),
	 OP_RCL_6(Key.KEY_RCL, Key.KEY_6),
	 OP_RCL_7(Key.KEY_RCL, Key.KEY_7),
	 OP_RCL_8(Key.KEY_RCL, Key.KEY_8),
	 OP_RCL_9(Key.KEY_RCL, Key.KEY_9),

	 OP_RCL_DIV(Key.KEY_RCL, Key.KEY_DIV),
	 OP_RCL_N(Key.KEY_RCL, Key.KEY_N),
	 OP_RCL_I(Key.KEY_RCL, Key.KEY_I),
	 OP_RCL_PV(Key.KEY_RCL, Key.KEY_PV),
	 OP_RCL_PMT(Key.KEY_RCL, Key.KEY_PMT),
	 OP_RCL_FV(Key.KEY_RCL, Key.KEY_FV),
	 OP_RCL_CHS(Key.KEY_RCL, Key.KEY_CHS),

	 OP_RCL_MUL(Key.KEY_RCL, Key.KEY_MUL),
	 OP_RCL_POWER(Key.KEY_RCL, Key.KEY_POWER),
	 OP_RCL_RECIPROCAL(Key.KEY_RCL, Key.KEY_RECIPROCAL),
	 OP_RCL_PERCENT_T(Key.KEY_RCL, Key.KEY_PERCENT_T),
	 OP_RCL_DELTA_PERCENT(Key.KEY_RCL, Key.KEY_DELTA_PERCENT),
	 OP_RCL_PERCENT(Key.KEY_RCL, Key.KEY_PERCENT),
	 OP_RCL_EEX(Key.KEY_RCL, Key.KEY_EEX),

	 OP_RCL_SUB(Key.KEY_RCL, Key.KEY_SUB),
	 OP_RCL_RS(Key.KEY_RCL, Key.KEY_RS),
	 OP_RCL_SST(Key.KEY_RCL, Key.KEY_SST),
	 OP_RCL_R_DOWN(Key.KEY_RCL, Key.KEY_R_DOWN),
	 OP_RCL_SWAP_XY(Key.KEY_RCL, Key.KEY_SWAP_XY),
	 OP_RCL_CLX(Key.KEY_RCL, Key.KEY_CLX),
	 OP_RCL_ENTER(Key.KEY_RCL, Key.KEY_ENTER),

	 OP_RCL_ADD(Key.KEY_RCL, Key.KEY_ADD),	// TODO: implement [1] [2] [STO] [ADD] => X = 3

	 OP_NULL(null, null);
	// @formatter:on

	private Key modifier;
	private Key key;

	private Op(Key modifier, Key key) {
		this.modifier = (modifier == null ? Key.KEY_NULL : modifier);
		this.key = (key == null ? Key.KEY_NULL : key);
	}

	public Key getModifier() {
		return this.modifier;
	}

	public Key getKey() {
		return this.key;
	}

	public static Op valueOf(Key modifier, Key key) {
		for (Op op : Op.values()) {
			if (op.modifier == modifier && op.key == key)
				return op;
		}
		return OP_NULL;
	}
}
