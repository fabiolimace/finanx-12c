package net.sf.finanx.fx12c.utils;

import java.awt.Color;

public class HexColor{

	private String hex;
	
	public HexColor(){
		this.hex="#000000";
	}
	public HexColor(String hex){
		if((hex.length()==7) && (hex.charAt(0)=='#')){
			this.hex=hex;
		}
	}
	
	public HexColor(int r, int g, int b){
		setHexColor(r, g, b);
	}
	
	public void setHexColor(int r, int g, int b){
		String hr = "00", hg = "00", hb = "00";
		
		hr = Integer.toHexString(r);  
		hg = Integer.toHexString(g);  
		hb = Integer.toHexString(b);  
		
		if(hr.length()==1){ hr = "0"+hr; }
		if(hg.length()==1){ hg = "0"+hg; }
		if(hb.length()==1){ hb = "0"+hb; }
		
		this.hex = "#"+hr+hg+hb;
		
	}
	
	public String getHexColor(){
		return this.hex;
	}
	
	public Color getColor(){
		String hr, hg, hb;
		int r, g, b;
		
		hr = this.hex.substring(1, 3);
		hg = this.hex.substring(3, 5);
		hb = this.hex.substring(5, 7);
		
		r = Integer.parseInt(hr, 16);
		g = Integer.parseInt(hg, 16);  
		b = Integer.parseInt(hb, 16);
		
		Color color = new Color(r, g, b);
		
		return color;
	}
	
	public static Color getColor(String hex){
		String hr, hg, hb;
		int r, g, b;
		
		hr = hex.substring(1, 3);
		hg = hex.substring(3, 5);
		hb = hex.substring(5, 7);
		
		r = Integer.parseInt(hr, 16);
		g = Integer.parseInt(hg, 16);  
		b = Integer.parseInt(hb, 16);
		
		Color color = new Color(r, g, b);
		
		return color;
	}
	
}
