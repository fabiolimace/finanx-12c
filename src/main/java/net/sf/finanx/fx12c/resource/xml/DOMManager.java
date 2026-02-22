
/* DOMManager is a static class that manages many DOM objects.
 * It's generates a hashtable that stores all DOM objects.
 */

package net.sf.finanx.fx12c.resource.xml;

// For reading operations
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

// For writing operations
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// Managed object
import java.util.Hashtable;

// Object collected in the hashtable
import org.w3c.dom.Document;

public class DOMManager {

	// Here we can find a recursion inside the class.
	private static DOMManager manager;

	// Method that generates a DOMManager
	public synchronized static DOMManager getManager() {
		if (manager == null)
			manager = new DOMManager();
		return manager;
	}

	// Hashtable where all DOMs are stored.
	private Hashtable<String, Object> hash;
	private String basePath;

	// Private constructor to assure that there'll be only one DOMManager object.
	// Actually, the hashtable is the main attribute of the object. It controls
	// that hashtable and answers requests from users.
	private DOMManager() {
		this.hash = new Hashtable<>();
		this.basePath = System.getProperty("user.home") + "/.finanx12c/";
	}

	/*
	 * Returns a file from the file system or from the Jar file
	 */
	public InputStream loadByRelativePath(String path) {

		File f = new File(this.basePath + path);

		if (f.isFile()) {
			try {
				return new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			return this.getClass().getClassLoader().getResourceAsStream(path);
		}

		return null;
	}

	private Object loadXML(String path, boolean validate) {

		InputStream i = null;
		Document d = null;

		try {

			i = this.loadByRelativePath(path);

			// Code for creating a factory: provides a configured constructor.
			DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
			fac.setValidating(validate); // To validate, change it to "true".
			fac.setNamespaceAware(false); // To use namespaces, change it to "true".

			// Code for getting a constructor instance.
			// Used to parse an specific file.
			DocumentBuilder builder = fac.newDocumentBuilder();
			d = builder.parse(i);

			return d;

		} catch (SAXParseException spe) {
			Exception x = spe;
			if (spe.getException() != null)
				x = spe.getException();
			x.printStackTrace();

		} catch (SAXException sxe) {
			if (sxe.getException() != null) {
				Exception x = sxe;
				x = sxe.getException();
				x.printStackTrace();
			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return null;
	}

	// Returns a Document, or null if problems occur
	public synchronized Document getDOM(String path, boolean validate) {

		Object d = hash.get(path);

		if (d == null) {
			d = loadXML(path, validate);
			hash.put(path, d);
		}

		if (d instanceof Document) {
			return (Document) d;
		}

		return null;
	}

	public synchronized void saveDOM(String path) {

		Document d = (Document) hash.get(path);
		File out = new File(basePath + path);

		/**
		 * Checks if the requested file exists in the user's home directory. If true, it
		 * saves the rewrites the file. if false: 1. it creates a directory tree; 2. it
		 * writes a new file.
		 **/
		if (out.exists()) {
			transformDOM(d, out);
		} else {
			out.getParentFile().mkdirs();
			transformDOM(d, out);
		}
	}

	private synchronized void transformDOM(Document d, File outFile) {

		String er = null;

		try {
			TransformerFactory fabrica = TransformerFactory.newInstance();
			Transformer transformador = fabrica.newTransformer();
			DOMSource fonte = new DOMSource(d);

			StreamResult resultado = new StreamResult(outFile);

			transformador.setOutputProperty(OutputKeys.INDENT, "yes");
			transformador.transform(fonte, resultado);

		} catch (TransformerConfigurationException tce) {
			// Erro gerado pela Fabrica do Tranformador
			er = "\n [!] Erro na Fabrica de Tranformador";
			System.out.println(er);

			// Usar a excessao contida, se houver
			Throwable x = tce;
			if (tce.getException() != null) {
				x = tce.getException();
			}

			x.printStackTrace();
		} catch (TransformerException te) {
			// Erro gerado pelo Tranformador
			er = "\n [!] Erro de Transformacao";
			System.out.println(er);

			// Usar a excessao contida, se houver
			Throwable x = te;
			if (te.getException() != null) {
				x = te.getException();
			}

			x.printStackTrace();
		}
	}
}
