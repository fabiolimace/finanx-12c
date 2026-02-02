package net.sf.finanx.fx12c.view.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.sf.finanx.fx12c.controller.Controller;
import net.sf.finanx.fx12c.controller.Key;
import net.sf.finanx.fx12c.view.swing.ImageButton;

public class CustomMouseListener extends MouseAdapter {

	private Controller presenter;
	private ImageButton button;

	public CustomMouseListener(Controller presenter) {
		this.presenter = presenter;
		this.button = null;
	}

	public void mousePressed(MouseEvent e) {
		this.presenter.keyPressed(getKey(e));
	}

	public void mouseReleased(MouseEvent e) {
		this.presenter.keyReleased(getKey(e));
	}

	public void mouseClicked(MouseEvent e) {
	}

	private Key getKey(MouseEvent e) {
		button = (ImageButton) e.getSource();
		return button.getKey();
	}
}
