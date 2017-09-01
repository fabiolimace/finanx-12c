package net.sf.finanx.hp12c.persistence;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LanguageDAO extends DataAccessObject{
	
	private Element e, eItem, eId, eVl, eDs, eSc;
	StringList lang;
	StringItem langstr;
	

	public LanguageDAO(String lang){
		super.manager = DOMManager.getManager();
		super.eName=DataAccessObject.LANGUAGE;
		super.path = "langs/" +lang + ".xml";
		super.dom = manager.getDOM(path, false);
	}
	

	public StringItem getById(String id){
		e = getElementById(id);
		
		if(e!=null){
			langstr = createLanguageString(e);
			return langstr;
		}
		
		return null;
	}
	
	public StringItem[] getByCriteria(String criteria, String value){
		Element el[] = getElementsByCriteria(criteria, value);
		StringItem sl[]  = new StringItem[el.length];
		
		for(int i=0; i<el.length; i++){
			if(el[i]!=null){
				sl[i] = createLanguageString(el[i]);
			}
		}	
		return sl;	
	}
	
	public StringItem[] getByCriteria(String criteria, String value, int max){
		
		Element el[] = getElementsByCriteria(criteria, value, max);
		StringItem sl[]  = new StringItem[el.length];
		
		for(int i=0; i<el.length; i++){
			if(el[i]!=null){
				sl[i] = createLanguageString(el[i]);
			}
		}
		return sl;	
	}
	
	public StringItem[] getAll(){
		Element el[] = getAllElements();
		StringItem sl[]  = new StringItem[el.length];
		for(int i=0; i<el.length; i++){
			if(el[i]!=null){
				sl[i] = createLanguageString(el[i]);
			}
		}
		return sl;
	}
	
	private Element createElement(StringItem ls){
		
		eItem = dom.createElement("lng");
		
		eId = dom.createElement("id");
		eId.appendChild(dom.createTextNode(langstr.getId()));
		eItem.appendChild(eId);
		
		eDs =dom.createElement("ds");
		eDs.appendChild(dom.createTextNode(langstr.getDescription()));
		eItem.appendChild(eDs);
		
		eVl =dom.createElement("vl");
		eVl.appendChild(dom.createTextNode(langstr.getValue()));
		eItem.appendChild(eVl);
		
		eSc =dom.createElement("sc");
		eSc.appendChild(dom.createTextNode(langstr.getShortcut()));
		eItem.appendChild(eSc);
				
		return eItem;
	}
	
	private StringItem createLanguageString(Element elemento){
		
		NodeList nl = null;
		Element tag = null;
		StringItem sl = new StringItem();
		String id="", ds="", vl="", sc="";
		
		nl = elemento.getElementsByTagName("id");
		tag = (Element)nl.item(0);
		if((tag!=null)&&(tag.getFirstChild()!=null)){
		id = tag.getFirstChild().getNodeValue();
		}
		
		nl = elemento.getElementsByTagName("ds");
		tag = (Element)nl.item(0);	
		if((tag!=null)&&(tag.getFirstChild()!=null)){
		ds = tag.getFirstChild().getNodeValue();
		}
		
		nl = elemento.getElementsByTagName("vl");
		tag = (Element)nl.item(0);		
		if((tag!=null)&&(tag.getFirstChild()!=null)){
		vl = tag.getFirstChild().getNodeValue();
		}
		
		nl = elemento.getElementsByTagName("sc");
		tag = (Element)nl.item(0);		
		if((tag!=null)&&(tag.getFirstChild()!=null)){
		sc = tag.getFirstChild().getNodeValue();
		}

		sl = new StringItem(id, vl, ds, sc); 
		
		return sl;
	}
	
	public void add(StringItem ls){
		
		eItem = this.createElement(ls);
		
		if(eItem!=null){
			Element de = dom.getDocumentElement();		
			de.appendChild(eItem);
			manager.saveDOM(path);
		}else{
			System.out.println(" [!] Inclusion Failed.");		
		}
	}
	
	public void modify(String id, StringItem sl){
		
		Element oldPath = super.getElementById(id);		
		Element newPath = this.createElement(sl);
		
		if((oldPath!=null) && (newPath!=null)){		
			Element raiz = dom.getDocumentElement();		
			raiz.replaceChild(newPath, oldPath);		
			manager.saveDOM(path);
		}else{
			System.out.println(" [!] Modification failed.");
		}

	}
	
	public StringItem remove(String id){
		
		Element elemento = getElementById(id);
		
		if(elemento!=null){
			
			Element doc = dom.getDocumentElement();
			doc.removeChild(elemento);			
			manager.saveDOM(path);
			StringItem ls = createLanguageString(e);
			return ls;
		}else{
			System.out.println(" [!] Removal failed.");
		}
		return null;	
	}

}


