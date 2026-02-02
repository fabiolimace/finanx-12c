package net.sf.finanx.fx12c.utils;

public class StringEscape {

	public static String escape(String str) {
		char c[] = { '\\', 0 };
		String tmp = "";

		for (int i = 0; i < str.length(); i++) {

			if (str.charAt(i) == '\t')
				c[1] = 't';
			else if (str.charAt(i) == '\b')
				c[1] = 'b';
			else if (str.charAt(i) == '\n')
				c[1] = 'n';
			else if (str.charAt(i) == '\r')
				c[1] = 'r';
			else if (str.charAt(i) == '\f')
				c[1] = 'f';
			else if (str.charAt(i) == '\'')
				c[1] = '\'';
			else if (str.charAt(i) == '\"')
				c[1] = '\"';
			else if (str.charAt(i) == '\\')
				c[1] = '\\';

			if (c[1] != 0)
				tmp += String.copyValueOf(c);
			else
				tmp += str.charAt(i);
		}

		return tmp;
	}
	
	public static String unescape(String str){
		char c = 0;
		String tmp = "";
		
		for (int i = 0; i < str.length(); i++){
			if ((str.charAt(i) == '\\') && (i < str.length() - 1)) {
				
				if (str.charAt(i+1) == 't')
					c = '\t';
				else if (str.charAt(i+1) == 'b')
					c = '\b';
				else if (str.charAt(i+1) == 'n')
					c = '\n';
				else if (str.charAt(i+1) == 'r')
					c = '\r';
				else if (str.charAt(i+1) == 'f')
					c = '\f';
				else if (str.charAt(i+1) == '\'')
					c = '\'';
				else if (str.charAt(i+1) == '\"')
					c = '\"';
				else if (str.charAt(i+1) == '\\')
					c = '\\';
				
				tmp += c;
				i++;
			}
			else {
				tmp += str.charAt(i);
			}
		}
		
		return tmp;
	}

}
