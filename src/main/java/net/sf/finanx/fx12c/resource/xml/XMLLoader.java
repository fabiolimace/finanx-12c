package net.sf.finanx.fx12c.resource.xml;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLLoader {

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

	public Object getById(String id) {
		return null;
	}

	public Object getByCriteria(String campo, String valor) {
		return null;
	}

	protected Element getElementById(String id) {

		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);

		Element e = null;
		String v = null;

		for (int i = 0; i < nl.getLength(); i++) {

			e = (Element) nl.item(i);
			NodeList lisID = e.getElementsByTagName(XMLLoader.ID);
			if (lisID.getLength() > 0) {
				Element eID = (Element) lisID.item(0);
				v = eID.getTextContent();
			}

			if (v.equals(id)) {
				return e;
			}
		}
		return null;
	}

	protected Element[] getElementsByCriteria(String criteria, String value) {

		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);

		String str = "";

		NodeList el = null;
		Element e = null;
		Element lst = null;
		Element r[] = null;
		ArrayList<Element> al = new ArrayList<Element>();

		for (int g = 0; g < nl.getLength(); g++) {

			lst = (Element) nl.item(g);
			el = lst.getElementsByTagName(criteria);

			for (int i = 0; i < el.getLength(); i++) {

				e = (Element) el.item(i);
				if (e.getTextContent() != null) {
					str = e.getTextContent();
					str = str.toUpperCase();
				}
				value = value.toUpperCase();

				if (str.contains(value)) {
					al.add(lst);
				}
			}

		}

		r = new Element[al.size()];
		for (int h = 0; h < al.size(); h++) {
			r[h] = (Element) al.get(h);
		}

		return r;
	}

	protected Element[] getElementsByCriteria(String criterio, String valor, int limite) {

		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);

		NodeList el = null;
		Element e = null;
		Element lst = null;
		Element r[] = null;
		ArrayList<Element> al = new ArrayList<Element>();

		String str = "";

		for (int g = 0; g < nl.getLength(); g++) {

			lst = (Element) nl.item(g);
			el = lst.getElementsByTagName(criterio);

			for (int i = 0; i < el.getLength(); i++) {

				e = (Element) el.item(i);
				if (e.getFirstChild().getTextContent() != null) {
					str = e.getFirstChild().getTextContent();
					str = str.toUpperCase();
				}
				valor = valor.toUpperCase();

				if (str.contains(valor)) {
					al.add(lst);
				}
			}

			if (al.size() == limite) {
				break;
			}

		}

		r = new Element[al.size()];
		for (int h = 0; h < al.size(); h++) {
			r[h] = (Element) al.get(h);
		}

		return r;
	}

	protected Element[] getAllElements() {
		Element de = dom.getDocumentElement();
		NodeList nl = de.getElementsByTagName(eName);

		Element[] e = new Element[nl.getLength()];

		for (int i = 0; i < nl.getLength(); i++) {
			e[i] = (Element) nl.item(i);
		}

		return e;
	}
}
