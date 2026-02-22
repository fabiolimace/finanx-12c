package net.sf.finanx.fx12c.utils;

import java.awt.Color;

public class HexColor {

	public static Color getColor(String hex) {
		String hr, hg, hb;
		int r, g, b;

		hr = hex.substring(1, 3);
		hg = hex.substring(3, 5);
		hb = hex.substring(5, 7);

		r = Integer.parseInt(hr, 16);
		g = Integer.parseInt(hg, 16);
		b = Integer.parseInt(hb, 16);

		Color color = new Color(r, g, b);

		return color;
	}
}
