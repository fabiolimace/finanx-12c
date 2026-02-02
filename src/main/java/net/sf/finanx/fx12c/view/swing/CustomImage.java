package net.sf.finanx.fx12c.view.swing;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public interface CustomImage {
    public void init();
	
	// Sets the image that will be displayed over the button.
	// It receives the "address" of the image in the file system.
    public void setImage(String url);

	// Sets the image that will be displayed over the button.
	// It receives an instance of an image class.
    public void setImage(Image img);
    
	// Sets the icon that will be displayed over the button.
	// It receives an instance of an ImageIcon class.
    public void setIcon(ImageIcon ico);
    
    // Fits image to panel's dimensions.
    // It can decrease performance if used without moderation.
    public void fitImage();
    
    // Renders the image button.
    public void paintComponent(Graphics g);
}
