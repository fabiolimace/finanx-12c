package net.sf.finanx.fx12c.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.sf.finanx.fx12c.calc.Controller;

public class CustomKeyListener implements KeyListener {

	private Controller controller;

	private boolean ignoreNextEvent = false;
	private long ignoreNextEventTime = 0;

	public static final String ALT = "ALT";
	public static final String ALT_ALT = "ALT+ALT";
	public static final String ALT_F4 = "ALT+F4";

	public static final String CTRL = "CTRL";
	public static final String CTRL_CTRL = "CTRL+CTRL";
	public static final String CTRL_C = "CTRL+C";
	public static final String CTRL_Q = "CTRL+Q";
	public static final String CTRL_V = "CTRL+V";

	public CustomKeyListener(Controller controller) {
		this.controller = controller;
	}

	public void keyPressed(KeyEvent e) {

		if (this.ignoreCurrentEvent()) {
			this.ignoreNextEvent();
			return;
		}

		this.controller.keyPressed(e.getKeyChar());
	}

	public void keyReleased(KeyEvent e) {

		if (this.ignoreCurrentEvent()) {
			return;
		}

		if (isShortcut(e)) {
			if (match(e, ALT_F4) || match(e, CTRL_Q)) {
				controller.quit();
			} else if (match(e, CTRL_C)) {
				controller.copyFromDisplayValue();
			} else if (match(e, CTRL_V)) {
				controller.pasteToDisplayValue();
			} else if (match(e, ALT) || match(e, ALT_ALT) || match(e, CTRL) || match(e, CTRL_CTRL)) {
				this.ignoreNextEvent();
			}
		}

		this.controller.keyReleased(e.getKeyChar());
	}

	public void keyTyped(KeyEvent e) {
		// Nothing
	}

	public void ignoreNextEvent() {
		this.ignoreNextEvent = true;
		this.ignoreNextEventTime = System.currentTimeMillis();
	}
	
	private boolean ignoreCurrentEvent() {
		long currentTime = System.currentTimeMillis();
		long interval = currentTime - ignoreNextEventTime;
		this.ignoreNextEventTime = currentTime;

		this.ignoreNextEvent = this.ignoreNextEvent && interval < 300;
		return this.ignoreNextEvent;
	}

	private boolean isShortcut(KeyEvent e) {
		String shortcut = shortcut(e);
		return shortcut.startsWith(ALT) || shortcut.startsWith(CTRL);
	}

	private boolean match(KeyEvent e, String shortcut) {
		return shortcut(e).equals(shortcut);
	}

	private String shortcut(KeyEvent e) {

		String modifiers = KeyEvent.getKeyModifiersText(e.getModifiers());
		modifiers = (modifiers != null && modifiers.length() > 0) ? modifiers + "+" : "";

		String key = KeyEvent.getKeyText(e.getKeyCode());
		key = (key != null && key.length() > 0) ? key : "";

		return String.format("%s%s", modifiers, key).toUpperCase();
	}
}
