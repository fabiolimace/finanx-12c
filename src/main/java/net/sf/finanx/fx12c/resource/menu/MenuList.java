package net.sf.finanx.fx12c.resource.menu;

import java.util.HashMap;

import net.sf.finanx.fx12c.resource.loader.ListLoader;

public class MenuList {

	private MenuItem items[];
	private HashMap<String, String> hash;
	
	private ListLoader loader; 
		
	public MenuList(String itemName){
		hash = new HashMap<String, String>();
		this.loader = new ListLoader(itemName);
		this.loadList();
	}
	
	private void loadList(){
		items = loader.getAll();
		
		for(int i=0; i<items.length; i++){
			if((items[i]!=null)&&(items[i].getId()!=null) && (items[i].getDescription()!=null)){
				this.addItem(items[i].getId(), items[i].getDescription());
			}
		}
	}
	public String getItem(String key){
		return (String) hash.get(key);
	}
	
	public int getSize(){
		return items.length;
	}
	
	public MenuItem get(int idx){
		if(idx>=0 && idx<items.length) 
			return items[idx];
		else 
			return null;
	}
	
	public MenuItem[] getAll(){
		return items;
	}
	
	public void addItem(String key, String value){
		hash.put(key, value);
	}
	
	public void removeItem(String key){
		hash.remove(key);
	}
	
	public void setHashMap(HashMap<String, String> hash){
		this.hash = hash;
	}
	
	public HashMap<String, String> getHashMap(){
		return hash;
	}
	
}