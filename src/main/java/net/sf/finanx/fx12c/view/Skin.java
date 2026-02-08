package net.sf.finanx.fx12c.view;

public class Skin {

	private String name;
	private String description;
	private String bgColor;
	private String faceColor;
	private String displayBgColor;
	private String displayFaceColor;
	private String buttonBgColor;
	private String buttonFaceColor;

	public Skin() {

		this.name = "Default";
		this.description = "Finanx 12C default style";
		this.bgColor = "#333333";
		this.faceColor = "#EEEEEE";
		this.displayBgColor = "#AAAAAA";
		this.displayFaceColor = "#777777";
		this.buttonBgColor = "#333333";
		this.buttonFaceColor = "#DDDDDD";

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public void setFaceColor(String faceColor) {
		this.faceColor = faceColor;
	}

	public void setDisplayBgColor(String displayBgColor) {
		this.displayBgColor = displayBgColor;
	}

	public void setDisplayFaceColor(String displayFaceColor) {
		this.displayFaceColor = displayFaceColor;
	}

	public void setButtonBgColor(String buttonBgColor) {
		this.buttonBgColor = buttonBgColor;
	}

	public void setButtonFaceColor(String buttonFaceColor) {
		this.buttonFaceColor = buttonFaceColor;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getBgColor() {
		return this.bgColor;
	}

	public String getFaceColor() {
		return this.faceColor;
	}

	public String getDisplayBgColor() {
		return this.displayBgColor;
	}

	public String getDisplayFaceColor() {
		return this.displayFaceColor;
	}

	public String getButtonBgColor() {
		return this.buttonBgColor;
	}

	public String getButtonFaceColor() {
		return this.buttonFaceColor;
	}
}
