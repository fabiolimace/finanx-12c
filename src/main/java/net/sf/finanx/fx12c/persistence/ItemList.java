package net.sf.finanx.fx12c.persistence;

import java.util.HashMap;

public class ItemList {

	private Item items[];
	private HashMap<String, String> hash;
	
	private ItemDAO dao; 
		
	public ItemList(String itemName){
		hash = new HashMap<String, String>();
		this.dao = new ItemDAO(itemName);
		this.loadList();
	}
	
	private void loadList(){
		items = dao.getAll();
		
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
	
	public Item get(int idx){
		if(idx>=0 && idx<items.length) 
			return items[idx];
		else 
			return null;
	}
	
	public Item[] getAll(){
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