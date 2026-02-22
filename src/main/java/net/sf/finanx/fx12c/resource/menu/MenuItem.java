package net.sf.finanx.fx12c.resource.menu;

public class MenuItem {

	private String id;
	private String des;

	public MenuItem(String id, String des) {
		this.id = id;
		this.des = des;
	}

	public String getId() {
		return this.id;
	}

	public String getDescription() {
		return this.des;
	}
}
