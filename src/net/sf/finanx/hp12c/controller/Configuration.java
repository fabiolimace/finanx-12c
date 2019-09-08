package net.sf.finanx.hp12c.controller;

import net.sf.finanx.hp12c.model.FinanceMemory;
import net.sf.finanx.hp12c.model.GeneralMemory;
import net.sf.finanx.hp12c.model.ProgramMemory;
import net.sf.finanx.hp12c.model.Stack;
import net.sf.finanx.hp12c.model.Step;

public class Configuration {

	private double size;
	private int xpos, ypos;
	private String skin;
	private String lang;
	private int stksize;
	private int memsize;
	private int prgsize;
	private int c;
	private int dmy;
	private int com;
	private int alg;
	private int beg;
	private int fix;
	private int mode;
	private KeyMapItem keymap[];

	private static final KeyMapItem[] DEFAULT_KEYMAP = {
		new KeyMapItem(0, '0'), // KEY_0, 0
		new KeyMapItem(1, '1'), // KEY_1, 1
		new KeyMapItem(2, '2'), // KEY_2, 2
		new KeyMapItem(3, '3'), // KEY_3, 3
		new KeyMapItem(4, '4'), // KEY_4, 4
		new KeyMapItem(5, '5'), // KEY_5, 5
		new KeyMapItem(6, '6'), // KEY_6, 6
		new KeyMapItem(7, '7'), // KEY_7, 7
		new KeyMapItem(8, '8'), // KEY_8, 8
		new KeyMapItem(9, '9'), // KEY_9, 9
		new KeyMapItem(10, '/'), // KEY_DIV, /
		new KeyMapItem(11, 'n'), // KEY_N, n
		new KeyMapItem(12, 'i'), // KEY_I, i
		new KeyMapItem(13, 'p'), // KEY_PV, p
		new KeyMapItem(14, 'm'), // KEY_PMT, m
		new KeyMapItem(15, 'v'), // KEY_FV, v
		new KeyMapItem(16, 'h'), // KEY_CHS, h
		new KeyMapItem(20, '*'), // KEY_MUL, *
		new KeyMapItem(21, '!'), // KEY_POW, !
		new KeyMapItem(22, '\\'), // KEY_RECIPROCAL, \
		new KeyMapItem(23, '#'), // KEY_PERC_TOT, #
		new KeyMapItem(24, '$'), // KEY_PERC_DELTA, $
		new KeyMapItem(25, '%'), // KEY_PERC, %
		new KeyMapItem(26, 'e'), // KEY_EEX, e
		new KeyMapItem(30, '-'), // KEY_SUB, -
		new KeyMapItem(31, '['), // KEY_RS, [
		new KeyMapItem(32, ']'), // KEY_SST, ]
		new KeyMapItem(33, 'd'), // KEY_ROLL, d
		new KeyMapItem(34, 'y'), // KEY_XY, y
		new KeyMapItem(35, 'c'), // KEY_CLX, c
		new KeyMapItem(36, '\n'), // KEY_ENTER, \n
		new KeyMapItem(40, '+'), // KEY_SUM, +
		new KeyMapItem(41, 'o'), // KEY_ON, o
		new KeyMapItem(42, 'f'), // KEY_F, f
		new KeyMapItem(43, 'g'), // KEY_G, g
		new KeyMapItem(44, 's'), // KEY_STO, s
		new KeyMapItem(45, 'r'), // KEY_RCL, r
		new KeyMapItem(48, '.'), // KEY_DOT, .
		new KeyMapItem(49, 'w')};// EY_TOT, w
	
	public static final Object VERSION = "0.2.8";
	public static final String DEFAULT_SKIN = "nigrum";

	public static class KeyMapItem {
		private char ch;
		private int cd;
		
		public KeyMapItem (int code, char ch) {
			this.cd = code;
			this.ch = ch;
		}
		
		public void setChar(char ch) {
			this.ch = ch;
		}
		
		public void setCode(int code){
			this.cd = code;
		}
		
		public char getChar(){
			return this.ch;
		}
		
		public int getCode(){
			return this.cd;
		}
	}
	
	public Configuration () {
		this.setDefaults();
	}

	public void setDefaults() {
		this.setSize(0.75);
		this.setFix(9);
		this.setSkin(DEFAULT_SKIN);
		this.setLanguage("en");
		this.setStackSize(4);
		this.setMemorySize(20);
		this.setProgramSize(100);
		this.keymap = DEFAULT_KEYMAP;
	}

	public KeyMapItem getKeyMapItem(int index){
		if (keymap.length > index)
			return keymap[index];
		return null;
	}

	public void setChar(int code, char ch) {

		if (getCodeIndex(code) != -1) {
			keymap[getCodeIndex(code)].setChar(ch);
		} else {
			KeyMapItem tmp[] = new KeyMapItem[keymap.length + 1];

			for (int i = 0; i < keymap.length - 1; i++) {
				tmp[i].setChar(keymap[i].getChar());
				tmp[i].setCode(keymap[i].getCode());
			}

			tmp[keymap.length].setChar(ch);
			tmp[keymap.length].setCode(code);
			
			this.keymap = tmp;
		}
	}

	public int getCodeIndex(int code) {
		for (int i = 0; i < keymap.length; i++) {
			if (keymap[i].getCode() == code) {
				return i;
			}
		}
		return -1;
	}

	public int getCode(char ch) {
		
		for (int i = 0; i < keymap.length; i++) {
			if (Character.toUpperCase(keymap[i].getChar()) == 
					Character.toUpperCase(ch)) {
				return keymap[i].getCode();
			}
		}
		return -1;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public void setXPos(int xpos) {
		this.xpos = xpos;
	}

	public void setYPos(int ypos) {
		this.ypos = ypos;
	}

	public void setSkin(String skinName) {
		this.skin = skinName;
	}
	
	public void setLanguage(String langCode) {
		this.lang = langCode;
	}

	public void setStackSize(int size) {
		this.stksize = size;
	}

	public void setMemorySize(int size) {
		this.memsize = size;
	}

	public void setProgramSize(int size) {
		this.prgsize = size;
	}

	public void setC(int bool) {
		this.c = bool;
	}

	public void setDmy(int bool) {
		this.dmy = bool;
	}

	public void setCom(int bool) {
		this.com = bool;
	}

	public void setAlg(int bool) {
		this.alg = bool;
	}

	public void setBeg(int bool) {
		this.beg = bool;
	}

	public void setFix(int fix) {
		this.fix = fix;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setKeyMap(KeyMapItem[] keyMap) {
		this.keymap = keyMap;
	}

	public double getSize() {
		return this.size;
	}

	public int getXPos() {
		return this.xpos;
	}

	public int getYPos() {
		return this.ypos;
	}

	public String getSkin() {
		return this.skin;
	}

	public String getLanguage() {
		return this.lang;
	}

	public int getStackSize() {
		return this.stksize;
	}

	public int getMemorySize() {
		return this.memsize;
	}

	public int getProgramSize() {
		return this.prgsize;
	}

	public int getC() {
		return this.c;
	}

	public int getDmy() {
		return this.dmy;
	}

	public int getCom() {
		return this.com;
	}

	public int getAlg() {
		return this.alg;
	}

	public int getBeg() {
		return this.beg;
	}

	public int getFix() {
		return this.fix;
	}

	public int getMode() {
		return this.mode;
	}

	public KeyMapItem[] getKeyMap() {
		return this.keymap;
	}
	
	public static Stack createStack(int size) {
		return new Stack(size);
	}
	
	public Stack createStack() {
		return Configuration.createStack(this.getStackSize());
	}
	
	public static GeneralMemory createGeneralMemory(int size){
		return new GeneralMemory(size);
	}
	
	public GeneralMemory createGeneralMemory(){
		return Configuration.createGeneralMemory(this.getMemorySize());
	}
	
	public static ProgramMemory createProgramMemory(int size) {
		return new ProgramMemory(size);
	}
	
	public ProgramMemory createProgramMemory() {
		return Configuration.createProgramMemory(this.getProgramSize());
	}
	
	public static FinanceMemory createFinanceMemory() {
		return new FinanceMemory();
	}

	public static Step createStep() {
		return new Step();
	}
	
	public static Configuration createConfiguration() {
		return new Configuration();
	}

}
