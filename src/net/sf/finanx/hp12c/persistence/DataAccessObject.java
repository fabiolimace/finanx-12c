package net.sf.finanx.hp12c.persistence;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class DataAccessObject {

	public static final String HISTORY = "his";
	public static final String EVENT = "evt";
	public static final String ID = "id";
	public static final String DESCRIPTION = "ds";
	public static final String ALTERATION = "alt";
	public static final String VALUE = "vl";
	public static final String STACK = "stk";
	public static final String FINANCIAL = "fin";
	public static final String MEMORY = "mem";
	public static final String PROGRAMMING = "prg";
	public static final String FLAGS = "flg";
	public static final String PATH = "path";
	public static final String CONFIGURATION = "cfg";
	public static final String SKIN = "skn";
	public static final String LANGUAGE = "lng";
	public static final String LIST = "list";
	public static final String ITEM = "item";
	
	protected DOMManager manager;
	protected Document dom;
	protected String path;
	
	protected String eName;
	
	public Object getById(String id){return null;}
	public Object getByCriteria(String campo, String valor){return null;}
	
	protected Element getElementById(String id){
		
		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);
		
		Element e = null;
		String v = null;
		
		for(int i=0; i<nl.getLength(); i++){
			
			e = (Element)nl.item(i);
			NodeList lisID = e.getElementsByTagName(DataAccessObject.ID);
			if(lisID.getLength()>0){
				Element eID = (Element)lisID.item(0);
				v = eID.getTextContent();
			}

			if(v.equals(id)){
				return e;
			}			
		}	
		return null;
	}
	
	protected Element[] getElementsByCriteria(String criteria, String value){

		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);

		String str = "";
		
		NodeList el = null;
		Element e = null;
		Element lst = null;
		Element r[] = null;
		ArrayList<Element> al = new ArrayList<Element>();
		
		for (int g=0; g<nl.getLength(); g++){
			
			lst = (Element) nl.item(g);
			el = lst.getElementsByTagName(criteria);
			
			for(int i=0; i<el.getLength(); i++){
				
				e = (Element)el.item(i);
				if(e.getTextContent()!=null){
					str = e.getTextContent();
					str = str.toUpperCase();
				}
				value = value.toUpperCase();
				
				if (str.contains(value)){
					al.add(lst);
				}
			}
			
		}
		
		r = new Element[al.size()];
		for (int h=0; h<al.size(); h++){
			r[h] = (Element)al.get(h);
		}
		
		return r;
	}
	
	protected Element[] getElementByExactCriteria(String criteria, String value){
		
		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);

		
		NodeList elementos = null;
		Element elemento = null;
		Element ultimo = null;
		Element rtn[] = null;
		ArrayList<Element> lis = new ArrayList<Element>();
		
		for (int g=0; g<nl.getLength(); g++){
			
			ultimo = (Element) nl.item(g);
			elementos = ultimo.getElementsByTagName(criteria);
			
			for(int i=0; i<elementos.getLength(); i++){
				
				elemento = (Element)elementos.item(i);
				
				if (value.equals(elemento.getFirstChild().getTextContent())){
					lis.add(ultimo);
				}
			}
			
		}		
		
		rtn = new Element[lis.size()];
		for (int h=0; h<lis.size(); h++){
			rtn[h] = (Element)lis.get(h);
		}
		
		if(rtn.length == 0){
			System.out.println(" [!] getElementByExactCriteria() returned a null value.");
		}
		
		return rtn;
	}
	
	protected Element[] getElementsByCriteria(String criterio, String valor, int limite){
		
		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);

		NodeList el = null;
		Element e = null;
		Element lst = null;
		Element r[] = null;
		ArrayList<Element> al = new ArrayList<Element>();
		
		String str = "";
		
		for (int g=0; g<nl.getLength(); g++){
			
			lst = (Element) nl.item(g);
			el = lst.getElementsByTagName(criterio);
			
			for(int i=0; i<el.getLength(); i++){
				
				e = (Element)el.item(i);
				if(e.getFirstChild().getTextContent()!=null){
					str = e.getFirstChild().getTextContent();
					str = str.toUpperCase();
				}
				valor = valor.toUpperCase();
				
				if (str.contains(valor)){
					al.add(lst);
				}
			}
			
			if(al.size() == limite){
				break;
			}
			
		}
		
		r = new Element[al.size()];
		for (int h=0; h<al.size(); h++){
			r[h] = (Element)al.get(h);
		}
		
		return r;
	}
	
	protected Element[] getElementsByExactCriteria(String criteria, String value, int max){
		
		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);

		NodeList el = null;
		Element e = null;
		Element lst = null;
		Element r[] = null;
		ArrayList<Element> al = new ArrayList<Element>();
		
		for (int g=0; g<nl.getLength(); g++){
			
			lst = (Element) nl.item(g);
			el = lst.getElementsByTagName(criteria);
			
			for(int i=0; i<el.getLength(); i++){
				
				e = (Element)el.item(i);
				
				if (value.equals(e.getFirstChild().getTextContent())){
					al.add(lst);
				}
			}
			
			if(al.size() == max){
				break;
			}
			
		}	
		
		r = new Element[al.size()];
		for (int h=0; h<al.size(); h++){
			r[h] = (Element)al.get(h);
		}
		
		return r;
	}

	
	protected Element[] getAllElements(){
		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);
		
		Element[] e = new Element[nl.getLength()];

		for(int i=0; i<nl.getLength(); i++){
			e[i] = (Element)nl.item(i);
		}	

		return e;
	}

	public String[] createPathCriteriaArray(String path){
		
		String tokens[] = path.split("/");
		
		return tokens;
		
	}

	public void reloadDOM(String address){
		this.dom = manager.reloadDOM(address, false);		
	}
	
	public void saveDOM(String address){
		this.manager.saveDOM(address);
	}
	
	public void backupDOM(String address){
		this.manager.backupDOM(address);
	}
	
	public void restoreDOM(String address){
		this.manager.restoreDOM(address);
	}
	
	public File getFile(String path){

		File f = null;
		
		try {
			
		    f = new File(path);
		    
		    if(!f.exists()){
		    	f = new File(getClass().getResource("/resources/"+path).toURI());	
		    }
		    
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return f;
		
	}
}
