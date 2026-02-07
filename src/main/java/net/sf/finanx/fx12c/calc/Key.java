package net.sf.finanx.fx12c.calc;

import java.util.Arrays;
import java.util.List;

public enum Key {
	
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
	
	private int code;
	
	private Key(int code){
		this.code = code;
	}
	
	public String getName() {
		return this.name();
	}

	public int getCode() {
		return this.code;
	}
	
	public static Key getKey(int code){
		Key k[] = Key.values();
		for (int i = 0; i< k.length; i++){
			if (k[i].getCode() == code)
				return k[i];
		}
		return null;
	}
	public static Key getKey(String name){
		Key k[] = Key.values();
		for (int i = 0; i< k.length; i++){
			if (k[i].getName().contentEquals(name))
				return k[i];
		}
		return null;
	}
	
	public void print(){
		System.out.println(this);
	}

	public static String getName(int code) {
		List<Key> keyList = Arrays.asList(Key.values());
		for(Key k : keyList) {
			if (k.code == code && code != -1) {
				return k.name();
			}
		}
		return "";
	}
	
	public String toString() {
		String r = "==[KEY]=============\n";
		
		r += this.name()+": "; 
		r += this.code+"\n";
		
		return r;
	}
}
