package net.sf.finanx.fx12c.resource.loader;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.finanx.fx12c.calc.Skin;
import net.sf.finanx.fx12c.resource.xml.DOMManager;
import net.sf.finanx.fx12c.resource.xml.XMLLoader;

public class SkinLoader extends XMLLoader{
	
	public SkinLoader(String skin){
		super.manager = DOMManager.getManager();
		super.eName=XMLLoader.SKIN;
		super.path = "skins/" + skin + "/" + eName + ".xml";
		super.dom = manager.getDOM(path, false);
	}
	
	public Skin getSkin(){
		Element e = dom.getDocumentElement();
		
		if(e!=null){
			Skin Skin = createSkin(e);
			return Skin;
		}
		
		return null;	
	}
	
	private Skin createSkin(Element e){
		
		NodeList nl = null;
		Element tag = null;
		
		String v = "";
		Skin Skin= new Skin();
		

		nl = e.getElementsByTagName("name");
		if(nl.getLength()>0){
			tag = (Element)nl.item(0);
			v = tag.getTextContent();
			Skin.setName(v);
		}
		
		nl = e.getElementsByTagName("description");
		if(nl.getLength()>0){
			tag = (Element)nl.item(0);
			v = tag.getTextContent();
			Skin.setDescription(v);
		}
		
		nl = e.getElementsByTagName("bg-color");
		if(nl.getLength()>0){
			tag = (Element)nl.item(0);
			v = tag.getTextContent();
			Skin.setBgColor(v);
		}
		
		nl = e.getElementsByTagName("face-color");
		if(nl.getLength()>0){
			tag = (Element)nl.item(0);
			v = tag.getTextContent();
			Skin.setFaceColor(v);
		}
		
		nl = e.getElementsByTagName("display-bg-color");
		if(nl.getLength()>0){
			tag = (Element)nl.item(0);
			v = tag.getTextContent();
			Skin.setDisplayBgColor(v);
		}
		
		nl = e.getElementsByTagName("display-face-color");
		if(nl.getLength()>0){
			tag = (Element)nl.item(0);
			v = tag.getTextContent();
			Skin.setDisplayFaceColor(v);
		}
		
		nl = e.getElementsByTagName("button-bg-color");
		if(nl.getLength()>0){
			tag = (Element)nl.item(0);
			v = tag.getTextContent();
			Skin.setButtonBgColor(v);
		}
		
		nl = e.getElementsByTagName("button-face-color");
		if(nl.getLength()>0){
			tag = (Element)nl.item(0);
			v = tag.getTextContent();
			Skin.setButtonFaceColor(v);
		}
		
		return Skin;
	}
	
	public void save(Skin Skin){
		
		Element de = dom.getDocumentElement();
		
		NodeList elRm = de.getChildNodes();
		Node eRm = null;
		
		for(int i=0; i<elRm.getLength(); i++){
			eRm = elRm.item(i);
			if(eRm!=null){de.removeChild(eRm);}
		}
		
		elRm = de.getElementsByTagName("name");
		eRm = (Element)elRm.item(0);
		if(eRm!=null){de.removeChild(eRm);}
		
		elRm = de.getElementsByTagName("description");
		eRm = (Element)elRm.item(0);
		if(eRm!=null){de.removeChild(eRm);}
		
		elRm = de.getElementsByTagName("bg-color");
		eRm = (Element)elRm.item(0);
		if(eRm!=null){de.removeChild(eRm);}
		
		elRm = de.getElementsByTagName("face-color");
		eRm = (Element)elRm.item(0);
		if(eRm!=null){de.removeChild(eRm);}
		
		elRm = de.getElementsByTagName("display-bg-color");
		eRm = (Element)elRm.item(0);
		if(eRm!=null){de.removeChild(eRm);}
		
		elRm = de.getElementsByTagName("display-face-color");
		eRm = (Element)elRm.item(0);
		if(eRm!=null){de.removeChild(eRm);}
		
		elRm = de.getElementsByTagName("button-bg-color");
		eRm = (Element)elRm.item(0);
		if(eRm!=null){de.removeChild(eRm);}
		
		elRm = de.getElementsByTagName("button-face-color");
		eRm = (Element)elRm.item(0);
		if(eRm!=null){de.removeChild(eRm);}


		Element eAdd = dom.createElement("name");
		eAdd.setTextContent(""+Skin.getName());
		if(eAdd!=null){de.appendChild(eAdd);}
		
		eAdd = dom.createElement("description");
		eAdd.setTextContent(""+Skin.getDescription());
		if(eAdd!=null){de.appendChild(eAdd);}
		
		eAdd = dom.createElement("bg-color");
		eAdd.setTextContent(""+Skin.getBgColor());
		if(eAdd!=null){de.appendChild(eAdd);}
		
		eAdd = dom.createElement("face-color");
		eAdd.setTextContent(""+Skin.getFaceColor());
		if(eAdd!=null){de.appendChild(eAdd);}
		
		eAdd = dom.createElement("display-bg-color");
		eAdd.setTextContent(""+Skin.getDisplayBgColor());
		if(eAdd!=null){de.appendChild(eAdd);}
	
		eAdd = dom.createElement("diplay-face-color");
		eAdd.setTextContent(""+Skin.getDisplayFaceColor());
		if(eAdd!=null){de.appendChild(eAdd);}
		
		eAdd = dom.createElement("button-bg-color");
		eAdd.setTextContent(""+Skin.getButtonBgColor());
		if(eAdd!=null){de.appendChild(eAdd);}
		
		eAdd = dom.createElement("button-face-color");
		eAdd.setTextContent(""+Skin.getButtonFaceColor());
		if(eAdd!=null){de.appendChild(eAdd);}
	
		
		manager.saveDOM(path);

	}

}
