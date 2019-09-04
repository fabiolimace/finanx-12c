package net.sf.finanx.hp12c.view.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.sf.finanx.hp12c.controller.Controller;

public class CustomKeyListener implements KeyListener {

	private Controller controller;
	
	public CustomKeyListener(Controller controller){
		this.controller = controller;
	}

    public void keyPressed(KeyEvent e) {
    	this.controller.keyPressed(e.getKeyChar());
    }
    
    public void keyReleased(KeyEvent e){
    	
    	int mod = e.getModifiers();
    	int key = e.getKeyCode();
    	
    	// ALT + F4 -> Quit
		if ((mod == KeyEvent.ALT_MASK || mod == KeyEvent.ALT_DOWN_MASK) && key == KeyEvent.VK_F4) {
			controller.quit();
			return;
		}
    	
    	this.controller.keyReleased(e.getKeyChar());
    }
    
    public void keyTyped(KeyEvent e){}
}
