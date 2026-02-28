package net.sf.finanx.fx12c.calc;

public enum Op {

	// @formatter:off
	// Commands without modifiers
	 OP_0(null, Key.KEY_00),
	 OP_1(null, Key.KEY_01),
	 OP_2(null, Key.KEY_02),
	 OP_3(null, Key.KEY_03),
	 OP_4(null, Key.KEY_04),
	 OP_5(null, Key.KEY_05),
	 OP_6(null, Key.KEY_06),
	 OP_7(null, Key.KEY_07),
	 OP_8(null, Key.KEY_08),
	 OP_9(null, Key.KEY_09),

	 OP_DIV(null, Key.KEY_10),
	 OP_N(null, Key.KEY_11),
	 OP_I(null, Key.KEY_12),
	 OP_PV(null, Key.KEY_13),
	 OP_PMT(null, Key.KEY_14),
	 OP_FV(null, Key.KEY_15),
	 OP_CHS(null, Key.KEY_16),

	 OP_MUL(null, Key.KEY_20),
	 OP_POWER(null, Key.KEY_21),
	 OP_RECIPROCAL(null, Key.KEY_22),
	 OP_PERCENT_TOTAL(null, Key.KEY_23),
	 OP_DELTA_PERCENT(null, Key.KEY_24),
	 OP_PERCENT(null, Key.KEY_25),
	 OP_EEX(null, Key.KEY_26),

	 OP_SUB(null, Key.KEY_30),
	 OP_RS(null, Key.KEY_31),
	 OP_SST(null, Key.KEY_32),
	 OP_R_DOWN(null, Key.KEY_33),
	 OP_SWAP_XY(null, Key.KEY_34),
	 OP_CLX(null, Key.KEY_35),
	 OP_ENTER(null, Key.KEY_36),

	 OP_ADD(null, Key.KEY_40),
	 OP_ON(null, Key.KEY_41),
	 OP_F(null, Key.KEY_42),
	 OP_G(null, Key.KEY_43),
	 OP_STO(null, Key.KEY_44),
	 OP_RCL(null, Key.KEY_45),
	 OP_DOT(null, Key.KEY_48),
	 OP_SIGMA_PLUS(null, Key.KEY_49),

	// Commands with 'f' modifier
	 OP_F_0(Key.KEY_42, Key.KEY_00),
	 OP_F_1(Key.KEY_42, Key.KEY_01),
	 OP_F_2(Key.KEY_42, Key.KEY_02),
	 OP_F_3(Key.KEY_42, Key.KEY_03),
	 OP_F_4(Key.KEY_42, Key.KEY_04),
	 OP_F_5(Key.KEY_42, Key.KEY_05),
	 OP_F_6(Key.KEY_42, Key.KEY_06),
	 OP_F_7(Key.KEY_42, Key.KEY_07),
	 OP_F_8(Key.KEY_42, Key.KEY_08),
	 OP_F_9(Key.KEY_42, Key.KEY_09),

	 OP_AMORT(Key.KEY_42, Key.KEY_11),
	 OP_INT(Key.KEY_42, Key.KEY_12),
	 OP_NPV(Key.KEY_42, Key.KEY_13),
	 OP_RND(Key.KEY_42, Key.KEY_14),
	 OP_IRR(Key.KEY_42, Key.KEY_15),

	 OP_PRICE(Key.KEY_42, Key.KEY_21),
	 OP_YTM(Key.KEY_42, Key.KEY_22),
	 OP_SL(Key.KEY_42, Key.KEY_25),
	 OP_SOYD(Key.KEY_42, Key.KEY_24),
	 OP_DB(Key.KEY_42, Key.KEY_25),

	 OP_PR(Key.KEY_42, Key.KEY_31),
	 OP_SIGMA(Key.KEY_42, Key.KEY_32),
	 OP_PRGM(Key.KEY_42, Key.KEY_33),
	 OP_FIN(Key.KEY_42, Key.KEY_34),
	 OP_REG(Key.KEY_42, Key.KEY_35),
	 OP_PREFIX(Key.KEY_42, Key.KEY_36),

	 // Commands with 'g' modifier
	 OP_STAT_X(Key.KEY_43, Key.KEY_00),
	 OP_STAT_XR(Key.KEY_43, Key.KEY_01),
	 OP_STAT_YR(Key.KEY_43, Key.KEY_02),
	 OP_FACTORIAL(Key.KEY_43, Key.KEY_03),
	 OP_DMY(Key.KEY_43, Key.KEY_04),
	 OP_MDY(Key.KEY_43, Key.KEY_05),
	 OP_STAT_XW(Key.KEY_43, Key.KEY_06),
	 OP_BEG(Key.KEY_43, Key.KEY_07),
	 OP_END(Key.KEY_43, Key.KEY_08),
	 OP_MEM(Key.KEY_43, Key.KEY_09),

	 OP_12_MUL(Key.KEY_43, Key.KEY_11),
	 OP_12_DIV(Key.KEY_43, Key.KEY_12),
	 OP_CFO(Key.KEY_43, Key.KEY_13),
	 OP_CFJ(Key.KEY_43, Key.KEY_14),
	 OP_NJ(Key.KEY_43, Key.KEY_15),
	 OP_DATE(Key.KEY_43, Key.KEY_16),

	 OP_SQRT(Key.KEY_43, Key.KEY_21),
	 OP_EX(Key.KEY_43, Key.KEY_22),
	 OP_LN(Key.KEY_43, Key.KEY_23),
	 OP_FRAC(Key.KEY_43, Key.KEY_24),
	 OP_INTG(Key.KEY_43, Key.KEY_25),
	 OP_DELTA_DYS(Key.KEY_43, Key.KEY_26),

	 OP_PSE(Key.KEY_43, Key.KEY_31),
	 OP_BST(Key.KEY_43, Key.KEY_32),
	 OP_GTO(Key.KEY_43, Key.KEY_33),
	 OP_XLEY(Key.KEY_43, Key.KEY_34),
	 OP_XEQO(Key.KEY_43, Key.KEY_35),
	 OP_LASTX(Key.KEY_43, Key.KEY_36),

	 OP_STAT_S(Key.KEY_43, Key.KEY_48),
	 OP_SIGMA_MINUS(Key.KEY_43, Key.KEY_49),

	// Commands with 'sto' modifier
	 OP_STO_0(Key.KEY_44, Key.KEY_00),
	 OP_STO_1(Key.KEY_44, Key.KEY_01),
	 OP_STO_2(Key.KEY_44, Key.KEY_02),
	 OP_STO_3(Key.KEY_44, Key.KEY_03),
	 OP_STO_4(Key.KEY_44, Key.KEY_04),
	 OP_STO_5(Key.KEY_44, Key.KEY_05),
	 OP_STO_6(Key.KEY_44, Key.KEY_06),
	 OP_STO_7(Key.KEY_44, Key.KEY_07),
	 OP_STO_8(Key.KEY_44, Key.KEY_08),
	 OP_STO_9(Key.KEY_44, Key.KEY_09),

	 OP_STO_DIV(Key.KEY_44, Key.KEY_10),
	 OP_STO_N(Key.KEY_44, Key.KEY_11),
	 OP_STO_I(Key.KEY_44, Key.KEY_12),
	 OP_STO_PV(Key.KEY_44, Key.KEY_13),
	 OP_STO_PMT(Key.KEY_44, Key.KEY_14),
	 OP_STO_FV(Key.KEY_44, Key.KEY_15),
	 OP_STO_CHS(Key.KEY_44, Key.KEY_16),

	 OP_STO_MUL(Key.KEY_44, Key.KEY_20),
	 OP_STO_POWER(Key.KEY_44, Key.KEY_21),
	 OP_STO_RECIPROCAL(Key.KEY_44, Key.KEY_22),
	 OP_STO_PERCENT_TOTAL(Key.KEY_44, Key.KEY_23),
	 OP_STO_DELTA_PERCENT(Key.KEY_44, Key.KEY_24),
	 OP_STO_PERCENT(Key.KEY_44, Key.KEY_25),
	 OP_STO_EEX(Key.KEY_44, Key.KEY_26),

	 OP_STO_SUB(Key.KEY_44, Key.KEY_30),
	 OP_STO_RS(Key.KEY_44, Key.KEY_31),
	 OP_STO_SST(Key.KEY_44, Key.KEY_32),
	 OP_STO_R_DOWN(Key.KEY_44, Key.KEY_33),
	 OP_STO_SWAP_XY(Key.KEY_44, Key.KEY_34),
	 OP_STO_CLX(Key.KEY_44, Key.KEY_35),
	 OP_STO_ENTER(Key.KEY_44, Key.KEY_36),

	 OP_STO_ADD(Key.KEY_44, Key.KEY_40),

	// Commands with 'rcl' modifier
	 OP_RCL_0(Key.KEY_45, Key.KEY_00),
	 OP_RCL_1(Key.KEY_45, Key.KEY_01),
	 OP_RCL_2(Key.KEY_45, Key.KEY_02),
	 OP_RCL_3(Key.KEY_45, Key.KEY_03),
	 OP_RCL_4(Key.KEY_45, Key.KEY_04),
	 OP_RCL_5(Key.KEY_45, Key.KEY_05),
	 OP_RCL_6(Key.KEY_45, Key.KEY_06),
	 OP_RCL_7(Key.KEY_45, Key.KEY_07),
	 OP_RCL_8(Key.KEY_45, Key.KEY_08),
	 OP_RCL_9(Key.KEY_45, Key.KEY_09),

	 OP_RCL_DIV(Key.KEY_45, Key.KEY_10),
	 OP_RCL_N(Key.KEY_45, Key.KEY_11),
	 OP_RCL_I(Key.KEY_45, Key.KEY_12),
	 OP_RCL_PV(Key.KEY_45, Key.KEY_13),
	 OP_RCL_PMT(Key.KEY_45, Key.KEY_14),
	 OP_RCL_FV(Key.KEY_45, Key.KEY_15),
	 OP_RCL_CHS(Key.KEY_45, Key.KEY_16),

	 OP_RCL_MUL(Key.KEY_45, Key.KEY_20),
	 OP_RCL_POWER(Key.KEY_45, Key.KEY_21),
	 OP_RCL_RECIPROCAL(Key.KEY_45, Key.KEY_22),
	 OP_RCL_PERCENT_TOTAL(Key.KEY_45, Key.KEY_23),
	 OP_RCL_DELTA_PERCENT(Key.KEY_45, Key.KEY_24),
	 OP_RCL_PERCENT(Key.KEY_45, Key.KEY_25),
	 OP_RCL_EEX(Key.KEY_45, Key.KEY_26),

	 OP_RCL_SUB(Key.KEY_45, Key.KEY_30),
	 OP_RCL_RS(Key.KEY_45, Key.KEY_31),
	 OP_RCL_SST(Key.KEY_45, Key.KEY_32),
	 OP_RCL_R_DOWN(Key.KEY_45, Key.KEY_33),
	 OP_RCL_SWAP_XY(Key.KEY_45, Key.KEY_34),
	 OP_RCL_CLX(Key.KEY_45, Key.KEY_35),
	 OP_RCL_ENTER(Key.KEY_45, Key.KEY_36),

	 OP_RCL_ADD(Key.KEY_45, Key.KEY_40),	// TODO: implement [1] [2] [STO] [ADD] => X = 3

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
