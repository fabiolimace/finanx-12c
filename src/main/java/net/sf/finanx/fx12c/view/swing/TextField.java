package net.sf.finanx.hp12c.view.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

public class TextField extends JTextField {
	
	private static final long serialVersionUID = 5537846439838203424L;
	
	public TextField(){
		super();
    	init();
	}
	
	public TextField(String text){
		super(text);
    	init();
	}
	
	private boolean aa;

	public void setAntiAlias(boolean aa){
		this.aa = aa;
	}

	public void paintComponent(Graphics g){
		if (aa)
		{
			((Graphics2D) g).addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
		}
		else
		{
			((Graphics2D) g).addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF));
		}
		super.paintComponent(g);
	}
	
    public void init(){
    	this.aa = false;
    }
}
