package net.sf.finanx.fx12c.persistence;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ItemDAO extends DataAccessObject{
	
	public ItemDAO(String dir){
		super.manager = DOMManager.getManager();
		super.eName = DataAccessObject.ITEM;
		super.path = dir + "/" + DataAccessObject.LIST + ".xml";
		super.dom = manager.getDOM(super.path, false);
	}
	
	public Item getById(String id){
		Element e = getElementById(id);
		
		if(e!=null){
			Item item = createItem(e);
			return item;
		}
		
		return null;	
	}
	
	public Item[] getByCriteria(String criteria, String value){
		Element e[] = getElementsByCriteria(criteria, value);
		Item itens[]  = new Item[e.length];
		
		for(int i=0; i<e.length; i++){
			if(e[i]!=null){
				itens[i] = createItem(e[i]);
			}
		}
		
		return itens;	
	}
	
	public Item[] getByCriteria(String criteria, String value, int max){
		Element e[] = getElementsByCriteria(criteria, value, max);
		Item itens[]  = new Item[e.length];
		
		for(int i=0; i<e.length; i++){
			if(e[i]!=null){
				itens[i] = createItem(e[i]);
			}
		}
		
		return itens;	
	}
	
	// Method that may overload if there's a too large file
	public Item[] getAll(){
		Element e[] = getAllElements();
		Item itens[]  = new Item[e.length];
		
		 //System.out.println("Elementos: " + e.length);
		// System.out.println("Items: " + itens.length);
		
		for(int i=0; i<e.length; i++){

			if(e[i]!=null){
				itens[i] = createItem(e[i]);
			}
		}
		
		return itens;	
	}
	
	private Element createItemElement(Item item){
		
		Element eItem = dom.createElement(DataAccessObject.ITEM);
		
		Element eID = dom.createElement("id");
		eID.appendChild(dom.createTextNode(item.getId()));
		eItem.appendChild(eID);
		
		Element eDS =dom.createElement("ds");
		eDS.appendChild(dom.createTextNode(item.getDescription()));
		eItem.appendChild(eDS);

		return eItem;
	}
	
	private Item createItem(Element e){
		
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
		
		Item item= new Item(id, ds); 
		
		return item;
	}
	
	public void incluir(Item item){
		
		Element eItem = this.createItemElement(item);
		
		if(eItem!=null){
			Element raiz = dom.getDocumentElement();		
			raiz.appendChild(eItem);
			manager.saveDOM(path);
		}else{
			System.out.println(" [!] Inclusion failed.");		
		}
	}
	
	public void modify(String id, Item item){
		
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
	
	public Item remove(String id){
		
		Element e = getElementById(id);
		
		if(e!=null){
			
			Element doc = dom.getDocumentElement();
			doc.removeChild(e);			
			manager.saveDOM(path);
			Item item = createItem(e);
			return item;
		}else{
			System.out.println(" [!] Removal failed.");
		}
		
		return null;	
	}


}
