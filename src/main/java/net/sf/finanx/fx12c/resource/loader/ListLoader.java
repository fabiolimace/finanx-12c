package net.sf.finanx.fx12c.resource.loader;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.sf.finanx.fx12c.resource.menu.MenuItem;
import net.sf.finanx.fx12c.resource.xml.DOMManager;
import net.sf.finanx.fx12c.resource.xml.XMLLoader;

public class ListLoader extends XMLLoader{
	
	public ListLoader(String dir){
		super.manager = DOMManager.getManager();
		super.eName = XMLLoader.ITEM;
		super.path = dir + "/" + XMLLoader.LIST + ".xml";
		super.dom = manager.getDOM(super.path, false);
	}
	
	public MenuItem getById(String id){
		Element e = getElementById(id);
		
		if(e!=null){
			MenuItem item = createItem(e);
			return item;
		}
		
		return null;	
	}
	
	public MenuItem[] getByCriteria(String criteria, String value){
		Element e[] = getElementsByCriteria(criteria, value);
		MenuItem itens[]  = new MenuItem[e.length];
		
		for(int i=0; i<e.length; i++){
			if(e[i]!=null){
				itens[i] = createItem(e[i]);
			}
		}
		
		return itens;	
	}
	
	public MenuItem[] getByCriteria(String criteria, String value, int max){
		Element e[] = getElementsByCriteria(criteria, value, max);
		MenuItem itens[]  = new MenuItem[e.length];
		
		for(int i=0; i<e.length; i++){
			if(e[i]!=null){
				itens[i] = createItem(e[i]);
			}
		}
		
		return itens;	
	}
	
	// Method that may overload if there's a too large file
	public MenuItem[] getAll(){
		Element e[] = getAllElements();
		MenuItem itens[]  = new MenuItem[e.length];
		
		 //System.out.println("Elementos: " + e.length);
		// System.out.println("Items: " + itens.length);
		
		for(int i=0; i<e.length; i++){

			if(e[i]!=null){
				itens[i] = createItem(e[i]);
			}
		}
		
		return itens;	
	}
	
	private Element createItemElement(MenuItem item){
		
		Element eItem = dom.createElement(XMLLoader.ITEM);
		
		Element eID = dom.createElement("id");
		eID.appendChild(dom.createTextNode(item.getId()));
		eItem.appendChild(eID);
		
		Element eDS =dom.createElement("ds");
		eDS.appendChild(dom.createTextNode(item.getDescription()));
		eItem.appendChild(eDS);

		return eItem;
	}
	
	private MenuItem createItem(Element e){
		
		NodeList nl = null;
		Element tag = null;
		String id=null, ds=null;
		
		nl = e.getElementsByTagName("id");
		tag = (Element)nl.item(0);
		if((tag!=null)&&(tag.getFirstChild()!=null)){
		id = tag.getFirstChild().getNodeValue();
		}
		
		nl = e.getElementsByTagName("ds");
		tag = (Element)nl.item(0);	
		if((tag!=null)&&(tag.getFirstChild()!=null)){
		ds = tag.getFirstChild().getNodeValue();
		}
		
		MenuItem item= new MenuItem(id, ds); 
		
		return item;
	}
	
	public void incluir(MenuItem item){
		
		Element eItem = this.createItemElement(item);
		
		if(eItem!=null){
			Element raiz = dom.getDocumentElement();		
			raiz.appendChild(eItem);
			manager.saveDOM(path);
		}else{
			System.out.println(" [!] Inclusion failed.");		
		}
	}
	
	public void modify(String id, MenuItem item){
		
		Element oldItem = super.getElementById(id);		
		Element newItem = this.createItemElement(item);	
		
		if((oldItem!=null) && (newItem!=null)){		
			Element de = dom.getDocumentElement();		
			de.replaceChild(newItem, oldItem);		
			manager.saveDOM(path);
		}else{
			System.out.println(" [!] Modification failed.");
		}

	}
	
	public MenuItem remove(String id){
		
		Element e = getElementById(id);
		
		if(e!=null){
			
			Element doc = dom.getDocumentElement();
			doc.removeChild(e);			
			manager.saveDOM(path);
			MenuItem item = createItem(e);
			return item;
		}else{
			System.out.println(" [!] Removal failed.");
		}
		
		return null;	
	}


}
