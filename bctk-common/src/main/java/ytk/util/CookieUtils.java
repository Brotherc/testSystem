package ytk.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {
	public static String getCookValue(HttpServletRequest request, String name) {
		Cookie[] cs = request.getCookies();
		if(cs == null) {
			return null;
		}
		for(Cookie c : cs) {
			if(c.getName().equals(name)) {
				return c.getValue();
			}
		}
		return null;
	}
	
	public static Cookie getCook(HttpServletRequest request, String name){
		Cookie[] cs = request.getCookies();
		if(cs == null) {
			return null;
		}
		for(Cookie c : cs) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

}
