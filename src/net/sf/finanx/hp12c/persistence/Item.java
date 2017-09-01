package net.sf.finanx.hp12c.persistence;

public class Item {

	private String id;
	private String des;
	
	public Item(){
		this.id="";
		this.des="";
	}
	
	public Item(String id, String des){
		this.id=id;
		this.des=des;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public void setDescription(String des){
		this.des=des;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getDescription(){
		return this.des;
	}
}
