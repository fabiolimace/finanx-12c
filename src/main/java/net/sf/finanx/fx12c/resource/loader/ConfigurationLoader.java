package net.sf.finanx.fx12c.resource.loader;

import java.io.IOException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.finanx.fx12c.calc.Configuration;
import net.sf.finanx.fx12c.resource.xml.DOMManager;
import net.sf.finanx.fx12c.resource.xml.XMLLoader;
import net.sf.finanx.fx12c.utils.StringEscape;;

public class ConfigurationLoader extends XMLLoader {

	private Configuration cfg;
	private Element de;
	
	
	public ConfigurationLoader() {
		this.cfg = Configuration.createConfiguration();
		super.manager = DOMManager.getManager();
		super.eName = XMLLoader.CONFIGURATION;
		super.path = "data/" + eName + ".xml";
		super.dom = manager.getDOM(super.path, false);
		this.de = dom.getDocumentElement();
		loadConfiguration(de);
	}
	
	public Configuration getConfiguration() {
		if(de!=null){
			return cfg;
		}
		return null;
	}
	
	private void loadConfiguration(Element e) {

		NodeList lis = null;
		Element tag = null;
		String v = "";

		try {
			
			lis = e.getElementsByTagName("version");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
			}

			// Check if the XML's version is the current one. 
			// The version "ALL" is used only in the embedded XML.
			if (!v.equals(Configuration.VERSION) && (!v.equals("ALL"))) {
				throw new IOException("[Version] Incompatible version");
			}
			
			lis = e.getElementsByTagName("size");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setSize(Double.parseDouble(v));
			}

			lis = e.getElementsByTagName("xpos");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setXPos(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("ypos");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setYPos(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("skin");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setSkin(v);
			}

			lis = e.getElementsByTagName("lang");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setLanguage(v);
			}

			lis = e.getElementsByTagName("stksize");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setStackSize(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("memsize");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setMemorySize(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("prgsize");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setProgramSize(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("c");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setC(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("dmy");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setDmy(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("com");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setCom(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("alg");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setAlg(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("beg");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setBeg(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("fix");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setFix(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("mode");
			if (lis.getLength() > 0) {
				tag = (Element) lis.item(0);
				v = tag.getTextContent();
				cfg.setMode(Integer.parseInt(v));
			}

			lis = e.getElementsByTagName("key");
			if (lis.getLength() > 0) {

				for (int i = 0; i < lis.getLength(); i++) {
					tag = (Element) lis.item(i);

					cfg.setChar(Integer.parseInt(tag.getAttribute("code")),
							StringEscape.unescape(tag.getAttribute("char")).charAt(0));
				}
			}
			
		} catch (Exception except) {
			System.out.println("[ParseError] An error ocurred while loading configurations. ");
			System.out.println("[ParseError] The default configurations will be assumed.");
			except.printStackTrace();
		}

	}
	
	public void save(Configuration conf) {

		Element raiz = dom.getDocumentElement();

		NodeList lisRm = raiz.getChildNodes();
		Node eRm = null;

		for (int i = 0; i < lisRm.getLength(); i++) {
			eRm = lisRm.item(i);
			if (eRm != null) {
				raiz.removeChild(eRm);
			}
		}

		// Removing old elements
		lisRm = raiz.getElementsByTagName("version");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}
		
		lisRm = raiz.getElementsByTagName("size");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("xpos");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("ypos");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("skin");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("lang");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("stksize");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("memsize");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("prgsize");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("c");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("dmy");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("com");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("alg");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("beg");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("fix");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("mode");
		eRm = (Element) lisRm.item(0);
		if (eRm != null) {
			raiz.removeChild(eRm);
		}

		lisRm = raiz.getElementsByTagName("key");
		while (lisRm.getLength() > 0) {
			eRm = (Element) lisRm.item(0);
			if (eRm != null) {
				raiz.removeChild(eRm);
			}
		}

		// Creating new elements
		Element eAdd = dom.createElement("version");
		eAdd.setTextContent("" + Configuration.VERSION);
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}
		
		eAdd = dom.createElement("size");
		eAdd.setTextContent("" + conf.getSize());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("xpos");
		eAdd.setTextContent("" + conf.getXPos());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("ypos");
		eAdd.setTextContent("" + conf.getYPos());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("skin");
		eAdd.setTextContent("" + conf.getSkin());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("lang");
		eAdd.setTextContent("" + conf.getLanguage());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("stksize");
		eAdd.setTextContent("" + conf.getStackSize());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("memsize");
		eAdd.setTextContent("" + conf.getMemorySize());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("prgsize");
		eAdd.setTextContent("" + conf.getProgramSize());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("c");
		eAdd.setTextContent("" + conf.getC());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("dmy");
		eAdd.setTextContent("" + conf.getDmy());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("com");
		eAdd.setTextContent("" + conf.getCom());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("alg");
		eAdd.setTextContent("" + conf.getAlg());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("beg");
		eAdd.setTextContent("" + conf.getBeg());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("fix");
		eAdd.setTextContent("" + conf.getFix());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		eAdd = dom.createElement("mode");
		eAdd.setTextContent("" + conf.getMode());
		if (eAdd != null) {
			raiz.appendChild(eAdd);
		}

		if (conf.getKeyMap() != null) {
			for (int i = 0; i < conf.getKeyMap().length; i++) {
				eAdd = dom.createElement("key");
				eAdd.setAttribute("code", "" + conf.getKeyMap()[i].getCode());
				eAdd.setAttribute("char", StringEscape.escape("" + conf.getKeyMap()[i].getChar()));
				if (eAdd != null) {
					raiz.appendChild(eAdd);
				}
			}
		}
		
		manager.saveDOM(path);

	}

}
