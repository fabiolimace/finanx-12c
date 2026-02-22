package net.sf.finanx.fx12c.resource.lang;

public class StringItem {

	private String id;
	private String value;
	private String description;
	private String shortcut;

	public StringItem(String id, String value, String description, String shortcut) {
		this.id = id;
		this.value = value;
		this.description = description;
		this.shortcut = shortcut;
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public String getShortcut() {
		return shortcut;
	}
}
