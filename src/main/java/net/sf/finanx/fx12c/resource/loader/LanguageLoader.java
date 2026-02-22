package net.sf.finanx.fx12c.resource.loader;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.sf.finanx.fx12c.resource.lang.StringItem;
import net.sf.finanx.fx12c.resource.lang.StringList;
import net.sf.finanx.fx12c.resource.xml.DOMManager;
import net.sf.finanx.fx12c.resource.xml.XMLLoader;

public class LanguageLoader extends XMLLoader {

	StringList lang;
	StringItem langstr;

	public LanguageLoader(String lang) {
		super.manager = DOMManager.getManager();
		super.eName = XMLLoader.LANGUAGE;
		super.path = "langs/" + lang + ".xml";
		super.dom = manager.getDOM(path, false);
	}

	public StringItem[] getAll() {
		Element el[] = getAllElements();
		StringItem sl[] = new StringItem[el.length];
		for (int i = 0; i < el.length; i++) {
			if (el[i] != null) {
				sl[i] = createLanguageString(el[i]);
			}
		}
		return sl;
	}

	private StringItem createLanguageString(Element elemento) {

		StringItem sl;
		NodeList nl = null;
		Element tag = null;
		String id = "", ds = "", vl = "", sc = "";

		nl = elemento.getElementsByTagName("id");
		tag = (Element) nl.item(0);
		if ((tag != null) && (tag.getFirstChild() != null)) {
			id = tag.getFirstChild().getNodeValue();
		}

		nl = elemento.getElementsByTagName("ds");
		tag = (Element) nl.item(0);
		if ((tag != null) && (tag.getFirstChild() != null)) {
			ds = tag.getFirstChild().getNodeValue();
		}

		nl = elemento.getElementsByTagName("vl");
		tag = (Element) nl.item(0);
		if ((tag != null) && (tag.getFirstChild() != null)) {
			vl = tag.getFirstChild().getNodeValue();
		}

		nl = elemento.getElementsByTagName("sc");
		tag = (Element) nl.item(0);
		if ((tag != null) && (tag.getFirstChild() != null)) {
			sc = tag.getFirstChild().getNodeValue();
		}

		sl = new StringItem(id, vl, ds, sc);

		return sl;
	}
}
