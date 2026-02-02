package net.sf.finanx.fx12c.persistence;


import java.util.Hashtable;

/**
 * Represents a language pack. Keeps a list of language strings.
 * @author Fabio Lima
 *
 */

public class StringList {

	private Hashtable<String, StringItem> hash;
	private StringItem ls;
	
	private LanguageDAO dao;
		
	public StringList(String path){
		hash = new Hashtable<String, StringItem>();
		this.dao = new LanguageDAO(path);
		this.loadLanguage();		
	}
	
	private void loadLanguage(){
		StringItem list[] = dao.getAll();
		for(int i=0; i<list.length; i++){
			if((list[i]!=null)&&(list[i].getId()!=null) && (list[i]!=null)){
				this.addLangString(list[i].getId(), list[i]);
			}
		}
	}	
	public String getValue(String key){
		ls = (StringItem) hash.get(key);
		if (ls==null) return "";
		return ls.getValue();
	}
	public String getDescription(String key){
		ls = (StringItem) hash.get(key);
		if (ls==null) return "";
		return ls.getDescription();
	}
	public String getShortcut(String key){
		ls = (StringItem) hash.get(key);
		if (ls==null) return "0";
		return ls.getShortcut();
	}
	
	public void addLangString(String key, StringItem value){
		hash.put(key, value);
	}
	
	public void removeLangString(String key){
		hash.remove(key);
	}
	
	public void setLangStringHash(Hashtable<String, StringItem> hashStr){
		hash = hashStr;
	}
	
	public Hashtable<String, StringItem> getLangStringHashp(){
		return hash;
	}
	
}
