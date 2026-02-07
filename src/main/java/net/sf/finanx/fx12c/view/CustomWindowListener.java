package net.sf.finanx.fx12c.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.sf.finanx.fx12c.calc.Controller;

public class CustomWindowListener implements WindowListener {

	private Controller controller;

	public CustomWindowListener(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		controller.save();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
