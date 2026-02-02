package net.sf.finanx.fx12c.persistence;

/**
 * Represents a language string item.
 * @author Fabio Lima
 *
 */

public class StringItem {

	private String id;
	private String value;
	private String description;
	private String shortcut;
	
	public StringItem(){
		this.id="";
		this.value="";
		this.description="";
		this.shortcut="";
	}
	
	public StringItem(String id, String value){
		this.id=id;
		this.value=value;
		this.description="";
		this.shortcut="";
	}
	
	public StringItem(String id, String value, String description){
		this.id=id;
		this.value=value;
		this.description=description;
		this.shortcut="";
	}
	public StringItem(String id, String value, String description, String shortcut){
		this.id=id;
		this.value=value;
		this.description=description;
		this.shortcut=shortcut;
	}
	
	public String getId(){ return id; }
	public String getValue(){ return value;	}
	public String getDescription(){ return description; }
	public String getShortcut(){ return shortcut; }

	public void setId(String id){ this.id=id; }
	public void setValue(String value){ this.value=value; }
	public void setDescription(String description){ this.description=description; }
	public void setShortcut(String shortcut){ this.shortcut=shortcut; }
	
}
