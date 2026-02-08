package net.sf.finanx.fx12c.calc;

import java.awt.Point;

import javax.swing.JTextField;

import net.sf.finanx.fx12c.math.Number;
import net.sf.finanx.fx12c.memory.FinanceMemory;
import net.sf.finanx.fx12c.memory.GeneralMemory;
import net.sf.finanx.fx12c.memory.ProgramMemory;
import net.sf.finanx.fx12c.memory.Stack;
import net.sf.finanx.fx12c.resource.lang.StringList;
import net.sf.finanx.fx12c.resource.loader.ConfigurationLoader;
import net.sf.finanx.fx12c.resource.loader.LanguageLoader;
import net.sf.finanx.fx12c.resource.loader.MemoryLoader;
import net.sf.finanx.fx12c.resource.loader.SkinLoader;
import net.sf.finanx.fx12c.resource.menu.MenuItem;
import net.sf.finanx.fx12c.resource.menu.MenuList;
import net.sf.finanx.fx12c.view.CustomKeyListener;
import net.sf.finanx.fx12c.view.CustomMenuListener;
import net.sf.finanx.fx12c.view.CustomMouseListener;
import net.sf.finanx.fx12c.view.CustomWindowListener;
import net.sf.finanx.fx12c.view.MainWindow;

public class Controller {

	protected Calculator executor;
	protected MainWindow window;

	protected MemoryLoader memd;

	protected ConfigurationLoader cfgd;
	protected Configuration cfg;

	protected SkinLoader sknd;
	protected MenuList sknl;
	protected Skin skin;
	protected MenuItem skinItems[];

	protected LanguageLoader lngd;
	protected StringList strl;

	protected String tmp[];
	protected String cmd, prm;

	JTextField clipboard = new JTextField();

	public Controller() {
		this.init();
	}

	public void init() {

		this.loadConfigs();
		this.loadMemory();

		this.initWindow();
		this.initExecutor();

		this.window.updateDisplay();

		this.welcomeMessage();
	}

	public void initWindow() {
		this.window = new MainWindow(cfg);
		this.window.setController(this);
		this.setWindowConfigs();

		this.setUpMouseListeners();
		this.setUpMenuListeners();
		this.setUpKeyListeners();
		this.setUpWindowListeners();
	}

	public void initExecutor() {
		this.executor = new Calculator();
		this.executor.setController(this);
		this.setExecutorConfigs();
		this.setExecutorMemory();
	}

	public void updateWindow() {
		this.window.destruct();
		this.initWindow();
		this.window.updateDisplay();
	}

	private void loadConfigs() {
		// Loading configurations
		cfgd = new ConfigurationLoader();
		cfg = cfgd.getConfiguration();
	}

	public void saveConfigs() {

		// Window position
		Point point = window.getWindowLocation();
		this.cfg.setXPos(point.x);
		this.cfg.setYPos(point.y);

		// Flags and display
		this.cfg.setAlg(executor.getFlags().getAlg());
		this.cfg.setBeg(executor.getFlags().getBegin());
		this.cfg.setC(executor.getFlags().getC());
		this.cfg.setCom(executor.getDisplay().getComma() ? 1 : 0);
		this.cfg.setDmy(executor.getFlags().getDmy());
		this.cfg.setFix(executor.getDisplay().getPrecision());
		this.cfg.setMode(executor.getDisplay().getMode());

		this.cfgd.save(cfg);
	}

	public void setWindowConfigs() {
		this.window.setConfigs(cfg);
	}

	public void setExecutorConfigs() {
		this.executor.setConfigs(cfg);
	}

	public void loadMemory() {

		this.memd = new MemoryLoader(this.cfg);
	}

	public void saveMemory() {

		this.memd.setStack((Stack) this.executor.getStack());
		this.memd.setFinanceMemory((FinanceMemory) this.executor.getFinanceMemory());
		this.memd.setGegeralMemory((GeneralMemory) this.executor.getGeneralMemory());
		this.memd.setProgramMemory((ProgramMemory) this.executor.getProgramMemory());

		this.memd.save();

	}

	public void setExecutorMemory() {
		this.executor.setStack(memd.getStack());
		this.executor.setFinanceMemory(memd.getFinanceMemory());
		this.executor.setGeneralMemory(memd.getGeneralMemory());
		this.executor.setProgramMemory(memd.getProgramMemory());

		this.executor.updateDisplay();
	}

	public void keyPressed(char key) {
		this.keyPressed(getKey(key));
	}

	public void keyReleased(char key) {
		this.keyReleased(this.getKey(key));
	}

	private Key getKey(char chr) {

		if (this.cfg.getCode(chr) == Key.KEY_00.getCode())
			return Key.KEY_00;
		else if (this.cfg.getCode(chr) == Key.KEY_01.getCode())
			return Key.KEY_01;
		else if (this.cfg.getCode(chr) == Key.KEY_02.getCode())
			return Key.KEY_02;
		else if (this.cfg.getCode(chr) == Key.KEY_03.getCode())
			return Key.KEY_03;
		else if (this.cfg.getCode(chr) == Key.KEY_04.getCode())
			return Key.KEY_04;
		else if (this.cfg.getCode(chr) == Key.KEY_05.getCode())
			return Key.KEY_05;
		else if (this.cfg.getCode(chr) == Key.KEY_06.getCode())
			return Key.KEY_06;
		else if (this.cfg.getCode(chr) == Key.KEY_07.getCode())
			return Key.KEY_07;
		else if (this.cfg.getCode(chr) == Key.KEY_08.getCode())
			return Key.KEY_08;
		else if (this.cfg.getCode(chr) == Key.KEY_09.getCode())
			return Key.KEY_09;
		else if (this.cfg.getCode(chr) == Key.KEY_10.getCode())
			return Key.KEY_10;
		else if (this.cfg.getCode(chr) == Key.KEY_11.getCode())
			return Key.KEY_11;
		else if (this.cfg.getCode(chr) == Key.KEY_12.getCode())
			return Key.KEY_12;
		else if (this.cfg.getCode(chr) == Key.KEY_13.getCode())
			return Key.KEY_13;
		else if (this.cfg.getCode(chr) == Key.KEY_14.getCode())
			return Key.KEY_14;
		else if (this.cfg.getCode(chr) == Key.KEY_15.getCode())
			return Key.KEY_15;
		else if (this.cfg.getCode(chr) == Key.KEY_16.getCode())
			return Key.KEY_16;
		else if (this.cfg.getCode(chr) == Key.KEY_21.getCode())
			return Key.KEY_21;
		else if (this.cfg.getCode(chr) == Key.KEY_22.getCode())
			return Key.KEY_22;
		else if (this.cfg.getCode(chr) == Key.KEY_23.getCode())
			return Key.KEY_23;
		else if (this.cfg.getCode(chr) == Key.KEY_24.getCode())
			return Key.KEY_24;
		else if (this.cfg.getCode(chr) == Key.KEY_25.getCode())
			return Key.KEY_25;
		else if (this.cfg.getCode(chr) == Key.KEY_26.getCode())
			return Key.KEY_26;
		else if (this.cfg.getCode(chr) == Key.KEY_31.getCode())
			return Key.KEY_31;
		else if (this.cfg.getCode(chr) == Key.KEY_32.getCode())
			return Key.KEY_32;
		else if (this.cfg.getCode(chr) == Key.KEY_33.getCode())
			return Key.KEY_33;
		else if (this.cfg.getCode(chr) == Key.KEY_34.getCode())
			return Key.KEY_34;
		else if (this.cfg.getCode(chr) == Key.KEY_35.getCode())
			return Key.KEY_35;
		else if (this.cfg.getCode(chr) == Key.KEY_36.getCode())
			return Key.KEY_36;
		else if (this.cfg.getCode(chr) == Key.KEY_41.getCode())
			return Key.KEY_41;
		else if (this.cfg.getCode(chr) == Key.KEY_42.getCode())
			return Key.KEY_42;
		else if (this.cfg.getCode(chr) == Key.KEY_43.getCode())
			return Key.KEY_43;
		else if (this.cfg.getCode(chr) == Key.KEY_44.getCode())
			return Key.KEY_44;
		else if (this.cfg.getCode(chr) == Key.KEY_45.getCode())
			return Key.KEY_45;
		else if (this.cfg.getCode(chr) == Key.KEY_48.getCode())
			return Key.KEY_48;
		else if (this.cfg.getCode(chr) == Key.KEY_49.getCode())
			return Key.KEY_49;
		else if (this.cfg.getCode(chr) == Key.KEY_20.getCode())
			return Key.KEY_20;
		else if (this.cfg.getCode(chr) == Key.KEY_30.getCode())
			return Key.KEY_30;
		else if (this.cfg.getCode(chr) == Key.KEY_40.getCode())
			return Key.KEY_40;
		else
			return null;
	}

	public void setUpMouseListeners() {

		CustomMouseListener listener = new CustomMouseListener(this);

		window.setMouseListener(Key.KEY_00, listener);
		window.setMouseListener(Key.KEY_01, listener);
		window.setMouseListener(Key.KEY_02, listener);
		window.setMouseListener(Key.KEY_03, listener);
		window.setMouseListener(Key.KEY_04, listener);
		window.setMouseListener(Key.KEY_05, listener);
		window.setMouseListener(Key.KEY_06, listener);
		window.setMouseListener(Key.KEY_07, listener);
		window.setMouseListener(Key.KEY_08, listener);
		window.setMouseListener(Key.KEY_09, listener);
		window.setMouseListener(Key.KEY_10, listener);
		window.setMouseListener(Key.KEY_11, listener);
		window.setMouseListener(Key.KEY_12, listener);
		window.setMouseListener(Key.KEY_13, listener);
		window.setMouseListener(Key.KEY_14, listener);
		window.setMouseListener(Key.KEY_15, listener);
		window.setMouseListener(Key.KEY_16, listener);
		window.setMouseListener(Key.KEY_21, listener);
		window.setMouseListener(Key.KEY_22, listener);
		window.setMouseListener(Key.KEY_23, listener);
		window.setMouseListener(Key.KEY_24, listener);
		window.setMouseListener(Key.KEY_25, listener);
		window.setMouseListener(Key.KEY_26, listener);
		window.setMouseListener(Key.KEY_31, listener);
		window.setMouseListener(Key.KEY_32, listener);
		window.setMouseListener(Key.KEY_33, listener);
		window.setMouseListener(Key.KEY_34, listener);
		window.setMouseListener(Key.KEY_35, listener);
		window.setMouseListener(Key.KEY_36, listener);
		window.setMouseListener(Key.KEY_41, listener);
		window.setMouseListener(Key.KEY_42, listener);
		window.setMouseListener(Key.KEY_43, listener);
		window.setMouseListener(Key.KEY_44, listener);
		window.setMouseListener(Key.KEY_45, listener);
		window.setMouseListener(Key.KEY_48, listener);
		window.setMouseListener(Key.KEY_49, listener);
		window.setMouseListener(Key.KEY_20, listener);
		window.setMouseListener(Key.KEY_30, listener);
		window.setMouseListener(Key.KEY_40, listener);

	}

	public void setUpKeyListeners() {
		this.window.setKeyListener(new CustomKeyListener(this));
	}

	public void setUpMenuListeners() {
		this.window.setMenuListener(new CustomMenuListener(this));
	}

	public void setUpWindowListeners() {
		this.window.setWindowListener(new CustomWindowListener(this));
	}

	public void doMenuCommand(String command, String param) {

		if (command == null)
			return;

		tmp = command.split("::");

		if (tmp.length > 1) {
			cmd = tmp[0];
			prm = tmp[1];
		} else {
			cmd = tmp[0];
		}

		if (cmd.equals(MainWindow.CMD_FILE_IMPORT)) {

		} else if (cmd.equals(MainWindow.CMD_FILE_EXPORT)) {

		} else if (cmd.equals(MainWindow.CMD_FILE_QUIT)) {

		} else if (cmd.equals(MainWindow.CMD_EDIT_MENU)) {

		} else if (cmd.equals(MainWindow.CMD_EDIT_COPY)) {
			copyFromDisplayValue();
		} else if (cmd.equals(MainWindow.CMD_EDIT_PASTE)) {
			pasteToDisplayValue();
		} else if (cmd.equals(MainWindow.CMD_EDIT_ERASE_DSP)) {
			this.executor.getDisplay().setValue(Number.ZERO);
			this.executor.getStack().set(0, Number.ZERO);
			this.executor.updateDisplay();
			this.window.updateDisplay();
		} else if (cmd.equals(MainWindow.CMD_EDIT_ERASE_STK)) {
			this.executor.getDisplay().setValue(Number.ZERO);
			this.executor.getStack().clear();
			this.executor.updateDisplay();
			this.window.updateDisplay();
		} else if (cmd.equals(MainWindow.CMD_EDIT_ERASE_FIN)) {
			this.executor.getFinanceMemory().clear();
		} else if (cmd.equals(MainWindow.CMD_EDIT_ERASE_STA)) {
			this.executor.getGeneralMemory().clearStats();
		} else if (cmd.equals(MainWindow.CMD_EDIT_ERASE_REG)) {
			this.executor.getGeneralMemory().clear();
		} else if (cmd.equals(MainWindow.CMD_EDIT_ERASE_PRG)) {
			this.executor.getProgramMemory().clear();
		} else if (cmd.equals(MainWindow.CMD_VIEW_MENU)) {

		} else if (cmd.equals(MainWindow.CMD_VIEW_SIZE)) {

		} else if (cmd.equals(MainWindow.CMD_VIEW_SIZE_VERY_SMALL)) {
			cfg.setSize(MainWindow.SIZE_VERY_SMALL);
			this.updateWindow();
		} else if (cmd.equals(MainWindow.CMD_VIEW_SIZE_SMALL)) {
			cfg.setSize(MainWindow.SIZE_SMALL);
			this.updateWindow();
		} else if (cmd.equals(MainWindow.CMD_VIEW_SIZE_MEDIUM)) {
			cfg.setSize(MainWindow.SIZE_MEDIUM);
			this.updateWindow();
		} else if (cmd.equals(MainWindow.CMD_VIEW_SIZE_LARGE)) {
			cfg.setSize(MainWindow.SIZE_LARGE);
			this.updateWindow();
		} else if (cmd.equals(MainWindow.CMD_VIEW_SIZE_HUGE)) {
			cfg.setSize(MainWindow.SIZE_HUGE);
			this.updateWindow();
		} else if (cmd.equals(MainWindow.CMD_VIEW_SKIN)) {
			cfg.setSkin(param);
			this.updateWindow();
		} else if (cmd.equals(MainWindow.CMD_VIEW_LANGUAGE)) {
			cfg.setLanguage(param);
			this.updateWindow();
		} else if (cmd.equals(MainWindow.CMD_OPTIONS_NUMBER_FORMAT)) {

		} else if (cmd.equals(MainWindow.CMD_OPTIONS_NUMBER_FORMAT_DOT)) {
			executor.getDisplay().setComma(false);
			window.updateDisplay();
		} else if (cmd.equals(MainWindow.CMD_OPTIONS_NUMBER_FORMAT_COMMA)) {
			executor.getDisplay().setComma(true);
			window.updateDisplay();
		} else if (cmd.equals(MainWindow.CMD_OPTIONS_DATE_FORMAT_DAY)) {
			executor.getFlags().setDmy(1);
			window.updateDisplay();
		} else if (cmd.equals(MainWindow.CMD_OPTIONS_DATE_FORMAT_MONTH)) {
			executor.getFlags().setDmy(0);
			window.updateDisplay();
		} else if (cmd.equals(MainWindow.CMD_OPTIONS_PAYMENT_MODE_BEGIN)) {
			executor.getFlags().setBegin(1);
			window.updateDisplay();
		} else if (cmd.equals(MainWindow.CMD_OPTIONS_PAYMENT_MODE_END)) {
			executor.getFlags().setBegin(0);
			window.updateDisplay();
		} else if (cmd.equals(MainWindow.CMD_TOOLS_REGISTERS_VIEW)) {

		} else if (cmd.equals(MainWindow.CMD_TOOLS_HISTORY)) {

		} else if (cmd.equals(MainWindow.CMD_ABOUT_AUTHOR)) {

		} else if (cmd.equals(MainWindow.CMD_ABOUT_CONTRIBUTORS)) {

		} else if (cmd.equals(MainWindow.CMD_ABOUT_SOFTWARE)) {

		}
	}

	public void setExecutor(Calculator executor) {
		this.executor = executor;
	}

	public Calculator getExecutor() {
		return this.executor;
	}

	public MainWindow getWindow() {
		return this.window;
	}

	public void setWindow(MainWindow window) {
		this.window = window;
	}

	public void keyPressed(Key key) {
		this.executor.keyPressed(key);
		this.window.keyPressed(key);
	}

	public void keyReleased(Key key) {
		this.executor.keyReleased(key);
		this.window.keyReleased(key);
	}

	public void copyFromDisplayValue() {

		String str = executor.getStack().get(0) + "";

		if (executor.getDisplay().getComma()) {
			str = str.replace(".", ",");
		}

		clipboard.setText(str);
		clipboard.selectAll();
		clipboard.copy();
	}

	public void pasteToDisplayValue() {

		String str = "";
		Number val = Number.ZERO;

		try {

			clipboard.selectAll();
			clipboard.paste();

			if (executor.getDisplay().getComma()) {
				str = clipboard.getText().replace(".", "").replace(",", ".");
			} else {
				str = clipboard.getText().replace(",", "");
			}

			val = Number.getInstance(Double.parseDouble(str));

//			executor.clearX();
//			executor.getDisplay().clear();
			executor.setX(val);
			executor.getDisplay().setValue(val);
			executor.updateDisplay();
			window.updateDisplay();

		} catch (Exception e) {
		}
	}

	private void welcomeMessage() {
		System.out.println("");
		System.out.println(this.window.getLanguageStringList().getValue("META_NAME") + " v" + Configuration.VERSION);
		System.out.println(this.window.getLanguageStringList().getValue("META_LICENCE"));
	}

	public void save() {
		this.saveConfigs();
		this.saveMemory();
	}

	public void quit() {
		this.save();
		System.exit(0);
	}

}
