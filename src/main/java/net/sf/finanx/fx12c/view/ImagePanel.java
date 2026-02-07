package net.sf.finanx.fx12c.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements CustomImage{

	private static final long serialVersionUID = -6757359185307494466L;
	
	protected Image image;

    public ImagePanel(){ }
    
    public ImagePanel(String url){
    	this.setImage(url);
    	this.repaint();
    	this.init();
    }
    public ImagePanel(Image img){
    	if(img!=null){
    		this.image = img;
        	this.repaint();
        	this.init();
    	}
    }
    
    public ImagePanel(ImageIcon ico){
    	if(ico!=null) {
    		this.image = ico.getImage();
        	this.repaint();
    	}
    }
    
    public void init() {}
    
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
