package net.sf.finanx.hp12c.view.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import net.sf.finanx.hp12c.controller.Key;

public class ImageButton extends JButton implements CustomImage {
	
	private static final long serialVersionUID = 7805147263610450678L;
	
	protected Image image;
   	protected Key key;

    public ImageButton(){ }
    
    public ImageButton(String url){
    	this.setImage(url);
    	this.repaint();
    	this.init();    	
    }
    public ImageButton(Image img){
    	if(img!=null){
    		this.image = img;
        	this.repaint();
        	this.init();
    	}
    }
    public ImageButton(ImageIcon ico){
    	if(ico!=null) this.image = ico.getImage();
    	this.repaint();
    	this.init();    	
    }
    
    public void init() {
    }
    
    public void setImage(String url){
    	if(url!=null){
    		this.image = new ImageIcon(url).getImage();
        	this.repaint();
    	}
    }
    
    public void setImage(Image img){ 
    	if(img!=null) {
    		this.image = img;
        	this.repaint(); 
    	}
    }
    
    public void setIcon(ImageIcon ico){ 
    	if(ico!=null){
    		this.image = ico.getImage();
        	this.repaint();
    	}
    }
    
    public void setKey(Key key){
    	this.key = key;
    }
    
    public Key getKey(){
    	return this.key;
    }
  
    public void fitImage(){
        if(image != null) {
	          Dimension dim = this.getSize();
	     
	          double width = dim.getWidth();
	          double height = dim.getHeight();
	          
	          image = new ImageIcon(this.image.getScaledInstance( (int) width, (int) height, Image.SCALE_DEFAULT)).getImage();  
        }
    }
    
     public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(image != null) {
	          g.drawImage(image, 0, 0, this);
        }
	} 
}
