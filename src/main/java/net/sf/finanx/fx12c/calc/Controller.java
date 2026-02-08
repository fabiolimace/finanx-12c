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

		if (this.cfg.getCode(chr) == Key.KEY_0.getCode())
			return Key.KEY_0;
		else if (this.cfg.getCode(chr) == Key.KEY_1.getCode())
			return Key.KEY_1;
		else if (this.cfg.getCode(chr) == Key.KEY_2.getCode())
			return Key.KEY_2;
		else if (this.cfg.getCode(chr) == Key.KEY_3.getCode())
			return Key.KEY_3;
		else if (this.cfg.getCode(chr) == Key.KEY_4.getCode())
			return Key.KEY_4;
		else if (this.cfg.getCode(chr) == Key.KEY_5.getCode())
			return Key.KEY_5;
		else if (this.cfg.getCode(chr) == Key.KEY_6.getCode())
			return Key.KEY_6;
		else if (this.cfg.getCode(chr) == Key.KEY_7.getCode())
			return Key.KEY_7;
		else if (this.cfg.getCode(chr) == Key.KEY_8.getCode())
			return Key.KEY_8;
		else if (this.cfg.getCode(chr) == Key.KEY_9.getCode())
			return Key.KEY_9;
		else if (this.cfg.getCode(chr) == Key.KEY_DIV.getCode())
			return Key.KEY_DIV;
		else if (this.cfg.getCode(chr) == Key.KEY_N.getCode())
			return Key.KEY_N;
		else if (this.cfg.getCode(chr) == Key.KEY_I.getCode())
			return Key.KEY_I;
		else if (this.cfg.getCode(chr) == Key.KEY_PV.getCode())
			return Key.KEY_PV;
		else if (this.cfg.getCode(chr) == Key.KEY_PMT.getCode())
			return Key.KEY_PMT;
		else if (this.cfg.getCode(chr) == Key.KEY_FV.getCode())
			return Key.KEY_FV;
		else if (this.cfg.getCode(chr) == Key.KEY_CHS.getCode())
			return Key.KEY_CHS;
		else if (this.cfg.getCode(chr) == Key.KEY_POWER.getCode())
			return Key.KEY_POWER;
		else if (this.cfg.getCode(chr) == Key.KEY_RECIPROCAL.getCode())
			return Key.KEY_RECIPROCAL;
		else if (this.cfg.getCode(chr) == Key.KEY_PERCENT_T.getCode())
			return Key.KEY_PERCENT_T;
		else if (this.cfg.getCode(chr) == Key.KEY_DELTA_PERCENT.getCode())
			return Key.KEY_DELTA_PERCENT;
		else if (this.cfg.getCode(chr) == Key.KEY_PERCENT.getCode())
			return Key.KEY_PERCENT;
		else if (this.cfg.getCode(chr) == Key.KEY_EEX.getCode())
			return Key.KEY_EEX;
		else if (this.cfg.getCode(chr) == Key.KEY_RS.getCode())
			return Key.KEY_RS;
		else if (this.cfg.getCode(chr) == Key.KEY_SST.getCode())
			return Key.KEY_SST;
		else if (this.cfg.getCode(chr) == Key.KEY_R_DOWN.getCode())
			return Key.KEY_R_DOWN;
		else if (this.cfg.getCode(chr) == Key.KEY_SWAP_XY.getCode())
			return Key.KEY_SWAP_XY;
		else if (this.cfg.getCode(chr) == Key.KEY_CLX.getCode())
			return Key.KEY_CLX;
		else if (this.cfg.getCode(chr) == Key.KEY_ENTER.getCode())
			return Key.KEY_ENTER;
		else if (this.cfg.getCode(chr) == Key.KEY_ON.getCode())
			return Key.KEY_ON;
		else if (this.cfg.getCode(chr) == Key.KEY_F.getCode())
			return Key.KEY_F;
		else if (this.cfg.getCode(chr) == Key.KEY_G.getCode())
			return Key.KEY_G;
		else if (this.cfg.getCode(chr) == Key.KEY_STO.getCode())
			return Key.KEY_STO;
		else if (this.cfg.getCode(chr) == Key.KEY_RCL.getCode())
			return Key.KEY_RCL;
		else if (this.cfg.getCode(chr) == Key.KEY_DOT.getCode())
			return Key.KEY_DOT;
		else if (this.cfg.getCode(chr) == Key.KEY_SIGMA_PLUS.getCode())
			return Key.KEY_SIGMA_PLUS;
		else if (this.cfg.getCode(chr) == Key.KEY_MUL.getCode())
			return Key.KEY_MUL;
		else if (this.cfg.getCode(chr) == Key.KEY_SUB.getCode())
			return Key.KEY_SUB;
		else if (this.cfg.getCode(chr) == Key.KEY_ADD.getCode())
			return Key.KEY_ADD;
		else
			return null;
	}

	public void setUpMouseListeners() {

		CustomMouseListener listener = new CustomMouseListener(this);

		window.setMouseListener(Key.KEY_0, listener);
		window.setMouseListener(Key.KEY_1, listener);
		window.setMouseListener(Key.KEY_2, listener);
		window.setMouseListener(Key.KEY_3, listener);
		window.setMouseListener(Key.KEY_4, listener);
		window.setMouseListener(Key.KEY_5, listener);
		window.setMouseListener(Key.KEY_6, listener);
		window.setMouseListener(Key.KEY_7, listener);
		window.setMouseListener(Key.KEY_8, listener);
		window.setMouseListener(Key.KEY_9, listener);
		window.setMouseListener(Key.KEY_DIV, listener);
		window.setMouseListener(Key.KEY_N, listener);
		window.setMouseListener(Key.KEY_I, listener);
		window.setMouseListener(Key.KEY_PV, listener);
		window.setMouseListener(Key.KEY_PMT, listener);
		window.setMouseListener(Key.KEY_FV, listener);
		window.setMouseListener(Key.KEY_CHS, listener);
		window.setMouseListener(Key.KEY_POWER, listener);
		window.setMouseListener(Key.KEY_RECIPROCAL, listener);
		window.setMouseListener(Key.KEY_PERCENT_T, listener);
		window.setMouseListener(Key.KEY_DELTA_PERCENT, listener);
		window.setMouseListener(Key.KEY_PERCENT, listener);
		window.setMouseListener(Key.KEY_EEX, listener);
		window.setMouseListener(Key.KEY_RS, listener);
		window.setMouseListener(Key.KEY_SST, listener);
		window.setMouseListener(Key.KEY_R_DOWN, listener);
		window.setMouseListener(Key.KEY_SWAP_XY, listener);
		window.setMouseListener(Key.KEY_CLX, listener);
		window.setMouseListener(Key.KEY_ENTER, listener);
		window.setMouseListener(Key.KEY_ON, listener);
		window.setMouseListener(Key.KEY_F, listener);
		window.setMouseListener(Key.KEY_G, listener);
		window.setMouseListener(Key.KEY_STO, listener);
		window.setMouseListener(Key.KEY_RCL, listener);
		window.setMouseListener(Key.KEY_DOT, listener);
		window.setMouseListener(Key.KEY_SIGMA_PLUS, listener);
		window.setMouseListener(Key.KEY_MUL, listener);
		window.setMouseListener(Key.KEY_SUB, listener);
		window.setMouseListener(Key.KEY_ADD, listener);

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
