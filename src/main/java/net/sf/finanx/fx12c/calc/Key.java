package net.sf.finanx.fx12c.calc;

public enum Key {

	// @formatter:off
	KEY_00(0),
	KEY_01(1),
	KEY_02(2),
	KEY_03(3),
	KEY_04(4),
	KEY_05(5),
	KEY_06(6),
	KEY_07(7),
	KEY_08(8),
	KEY_09(9),
	KEY_10(10),
	KEY_11(11),
	KEY_12(12),
	KEY_13(13),
	KEY_14(14),
	KEY_15(15),
	KEY_16(16),
	KEY_20(20),
	KEY_21(21),
	KEY_22(22),
	KEY_23(23),
	KEY_24(24),
	KEY_25(25),
	KEY_26(26),
	KEY_30(30),
	KEY_31(31),
	KEY_32(32),
	KEY_33(33),
	KEY_34(34),
	KEY_35(35),
	KEY_36(36),
	KEY_40(40),
	KEY_41(41),
	KEY_42(42),
	KEY_43(43),
	KEY_44(44),
	KEY_45(45),
	KEY_48(48),
	KEY_49(49),
	KEY_NULL(-1);
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
