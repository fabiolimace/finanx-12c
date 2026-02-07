package net.sf.finanx.fx12c.calc;

public enum Key {

	// @formatter:off
	KEY_NULL(-1),
	KEY_0(0),
	KEY_1(1),
	KEY_2(2),
	KEY_3(3),
	KEY_4(4),
	KEY_5(5),
	KEY_6(6),
	KEY_7(7),
	KEY_8(8),
	KEY_9(9),
	KEY_DIV(10),
	KEY_MUL(20),
	KEY_SUB(30),
	KEY_ADD(40),
	KEY_N(11),
	KEY_I(12),
	KEY_PV(13),
	KEY_PMT(14),
	KEY_FV(15),
	KEY_CHS(16),
	KEY_POWER(21),
	KEY_RECIPROCAL(22),
	KEY_PERCENTT(23),
	KEY_DELTAPERCENT(24),
	KEY_PERCENT(25),
	KEY_EEX(26),
	KEY_RS(31),
	KEY_SST(32),
	KEY_RDOWN(33),
	KEY_SWAPXY(34),
	KEY_CLX(35),
	KEY_ENTER(36),
	KEY_ON(41),
	KEY_F(42),
	KEY_G(43),
	KEY_STO(44),
	KEY_RCL(45),
	KEY_DOT(48),
	KEY_SIGMAPLUS(49);
	// @formatter:on

	private int code;

	private Key(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public static Key valueOf(int code) {
		for (Key key : Key.values()) {
			if (key.getCode() == code)
				return key;
		}
		return KEY_NULL;
	}
}
