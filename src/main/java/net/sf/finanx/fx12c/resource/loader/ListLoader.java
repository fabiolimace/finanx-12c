package net.sf.finanx.fx12c.resource.loader;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.sf.finanx.fx12c.resource.menu.MenuItem;
import net.sf.finanx.fx12c.resource.xml.DOMManager;
import net.sf.finanx.fx12c.resource.xml.XMLLoader;

public class ListLoader extends XMLLoader {

	public ListLoader(String dir) {
		super.manager = DOMManager.getManager();
		super.eName = XMLLoader.ITEM;
		super.path = dir + "/" + XMLLoader.LIST + ".xml";
		super.dom = manager.getDOM(super.path, false);
	}

	public MenuItem[] getAll() {
		Element e[] = getAllElements();
		MenuItem itens[] = new MenuItem[e.length];

		for (int i = 0; i < e.length; i++) {

			if (e[i] != null) {
				itens[i] = createItem(e[i]);
			}
		}

		return itens;
	}

	private MenuItem createItem(Element e) {

		NodeList nl = null;
		Element tag = null;
		String id = null, ds = null;

		nl = e.getElementsByTagName("id");
		tag = (Element) nl.item(0);
		if ((tag != null) && (tag.getFirstChild() != null)) {
			id = tag.getFirstChild().getNodeValue();
		}

		nl = e.getElementsByTagName("ds");
		tag = (Element) nl.item(0);
		if ((tag != null) && (tag.getFirstChild() != null)) {
			ds = tag.getFirstChild().getNodeValue();
		}

		MenuItem item = new MenuItem(id, ds);

		return item;
	}
}
