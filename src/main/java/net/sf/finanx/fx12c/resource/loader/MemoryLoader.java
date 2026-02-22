package net.sf.finanx.fx12c.resource.loader;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.finanx.fx12c.calc.Op;
import net.sf.finanx.fx12c.calc.Configuration;
import net.sf.finanx.fx12c.calc.Key;
import net.sf.finanx.fx12c.calc.Step;
import net.sf.finanx.fx12c.math.Number;
import net.sf.finanx.fx12c.memory.FinanceMemory;
import net.sf.finanx.fx12c.memory.GeneralMemory;
import net.sf.finanx.fx12c.memory.ProgramMemory;
import net.sf.finanx.fx12c.memory.Stack;
import net.sf.finanx.fx12c.resource.xml.DOMManager;
import net.sf.finanx.fx12c.resource.xml.XMLLoader;

public class MemoryLoader extends XMLLoader {

	private Stack stk;
	private FinanceMemory fin;
	private GeneralMemory mem;
	private ProgramMemory prg;

	private Element de;

	public MemoryLoader(Configuration cfg) {
		super.manager = DOMManager.getManager();
		super.eName = XMLLoader.MEMORY;
		super.path = "data/" + eName + ".xml";
		super.dom = manager.getDOM(path, false);

		this.stk = Configuration.createStack(cfg.getStackSize());
		this.fin = Configuration.createFinanceMemory();
		this.mem = Configuration.createGeneralMemory();
		this.prg = Configuration.createProgramMemory();

		de = dom.getDocumentElement();
		createMemory(de);
	}

	public Stack getStack() {
		if (de != null) {
			return stk;
		}
		return null;
	}

	public FinanceMemory getFinanceMemory() {
		if (de != null) {
			return fin;
		}
		return null;
	}

	public GeneralMemory getGeneralMemory() {
		if (de != null) {
			return mem;
		}
		return null;
	}

	public ProgramMemory getProgramMemory() {
		if (de != null) {
			return prg;
		}
		return null;
	}

	public void setStack(Stack stk) {
		this.stk = stk;
	}

	public void setFinanceMemory(FinanceMemory fin) {
		this.fin = fin;
	}

	public void setGegeralMemory(GeneralMemory mem) {
		this.mem = mem;
	}

	public void setProgramMemory(ProgramMemory prg) {
		this.prg = prg;
	}

	private void createMemory(Element e) {

		NodeList nl = null;
		Element tag = null;

		String p0 = "";
		String p1 = "";
		String p2 = "";

		try {
			for (int i = 0; i < this.stk.getSize(); i++) {
				nl = e.getElementsByTagName("stk" + i);

				if ((nl.getLength() > 0) && (nl != null)) {
					tag = (Element) nl.item(0);

					if ((tag.getAttribute("p0") != null) && (tag != null)) {
						p0 = tag.getAttribute("p0");
						stk.set(i, Number.getInstance(Double.parseDouble(p0)));
					}
				}
			}

			for (int i = 0; i < FinanceMemory.SIZE; i++) {
				nl = e.getElementsByTagName("fin" + i);

				if ((nl.getLength() > 0) && (nl != null)) {
					tag = (Element) nl.item(0);

					if ((tag.getAttribute("p0") != null) && (tag != null)) {
						p0 = tag.getAttribute("p0");
						fin.set(i, Number.getInstance(Double.parseDouble(p0)));
					}
				}
			}

			for (int i = 0; i < GeneralMemory.SIZE; i++) {
				nl = e.getElementsByTagName("mem" + i);

				if ((nl.getLength() > 0) && (nl != null)) {
					tag = (Element) nl.item(0);

					if ((tag.getAttribute("p0") != null) && (tag != null)) {
						p0 = tag.getAttribute("p0");
						mem.set(i, Number.getInstance(Double.parseDouble(p0)));
					}
					if ((tag.getAttribute("p1") != null) && (tag != null)) {
						p1 = tag.getAttribute("p1");
						mem.setTimes(i, Number.getInstance(Double.parseDouble(p1)));
					}
				}
			}

			for (int i = 0; i < ProgramMemory.SIZE; i++) {
				nl = e.getElementsByTagName("prg" + i);

				if ((nl.getLength() > 0) && (nl != null)) {
					tag = (Element) nl.item(0);

					Key modifier = null;
					Key key = null;
					int complement = -1;

					if ((tag.getAttribute("p0") != null) && (tag != null)) {
						p0 = tag.getAttribute("p0");
						modifier = Key.valueOf(Integer.parseInt(p0));
					}
					if ((tag.getAttribute("p1") != null) && (tag != null)) {
						p1 = tag.getAttribute("p1");
						key = Key.valueOf(Integer.parseInt(p0));
					}
					if ((tag.getAttribute("p2") != null) && (tag != null)) {
						p2 = tag.getAttribute("p2");
						complement = Integer.parseInt(p2);
					}

					Step step = new Step(Op.valueOf(modifier, key));
					step.setComplement(complement);
					prg.set(i, step);
				}
			}
		} catch (Exception except) {
			System.out.println("[ParseError] An error ocurred while loading configurations.");
			System.out.println("[ParseError] The default configurations will be assumed.");
			except.printStackTrace();
		}

	}

	public void save() {

		Element de = dom.getDocumentElement();

		NodeList lisRm = de.getChildNodes();
		Node eRm = null;

		Element eAdd = null;

		for (int i = 0; i < lisRm.getLength(); i++) {
			eRm = lisRm.item(i);
			if (eRm != null) {
				de.removeChild(eRm);
			}
		}

		for (int i = 0; i < this.stk.getSize(); i++) {
			lisRm = de.getElementsByTagName("stk" + i);
			eRm = (Element) lisRm.item(0);
			if (eRm != null) {
				de.removeChild(eRm);
			}
		}

		for (int i = 0; i < FinanceMemory.SIZE; i++) {
			lisRm = de.getElementsByTagName("fin" + i);
			eRm = (Element) lisRm.item(0);
			if (eRm != null) {
				de.removeChild(eRm);
			}
		}

		for (int i = 0; i < GeneralMemory.SIZE; i++) {
			lisRm = de.getElementsByTagName("mem" + i);
			eRm = (Element) lisRm.item(0);
			if (eRm != null) {
				de.removeChild(eRm);
			}
		}

		for (int i = 0; i < ProgramMemory.SIZE; i++) {
			lisRm = de.getElementsByTagName("prg" + i);
			eRm = (Element) lisRm.item(0);
			if (eRm != null) {
				de.removeChild(eRm);
			}
		}

		for (int i = 0; i < this.stk.getSize(); i++) {
			eAdd = dom.createElement("stk" + i);
			eAdd.setAttribute("p0", stk.get(i) + "");
			if (eAdd != null) {
				de.appendChild(eAdd);
			}
		}

		for (int i = 0; i < FinanceMemory.SIZE; i++) {
			eAdd = dom.createElement("fin" + i);
			eAdd.setAttribute("p0", fin.get(i) + "");
			if (eAdd != null) {
				de.appendChild(eAdd);
			}
		}

		for (int i = 0; i < GeneralMemory.SIZE; i++) {
			eAdd = dom.createElement("mem" + i);
			eAdd.setAttribute("p0", mem.get(i) + "");
			eAdd.setAttribute("p1", mem.getTimes(i) + "");
			if (eAdd != null) {
				de.appendChild(eAdd);
			}
		}

		for (int i = 0; i < ProgramMemory.SIZE; i++) {
			Step step = prg.get(i);
			eAdd = dom.createElement("prg" + i);
			eAdd.setAttribute("p0", step.getOp().getModifier().getCode() + "");
			eAdd.setAttribute("p1", step.getOp().getKey().getCode() + "");
			eAdd.setAttribute("p2", step.getComplement() + "");
			if (eAdd != null) {
				de.appendChild(eAdd);
			}
		}

		manager.saveDOM(path);

	}
}
