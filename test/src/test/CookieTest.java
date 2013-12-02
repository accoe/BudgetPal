package test;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class CookieTest {
	
	public static void setCookiePropertyInUrlConn(URLConnection uc, String cookieValue){
		uc.setRequestProperty("COOKIE",cookieValue); 
	}
	
	public static List<String> getCookiePropertyFtomUrlConn(URLConnection uc){
		ArrayList<String> result = new ArrayList<String>();;
		String headerName=null;
		for (int i=1; (headerName = uc.getHeaderFieldKey(i))!=null; i++) {
		 	if (headerName.equals("Set-Cookie")) {                  
		 		result.add(uc.getHeaderField(i));  
		 	}
		 }
		
		return result;
	}
	
	public static void main(String[] args) {
		try {
			URL u = new URL("http://mybudgetpal.com/ws/server.php?a=login&user=test&password=ed5465b9220df9ce176d0bf30d6a317729bd9d37e4ae1cc015cb24c99af1df49");
			
			URLConnection uc = u.openConnection();
			
			uc.connect();
						
			List<String> cookieStrings = getCookiePropertyFtomUrlConn(uc);
			
			String cookieString = "";
			for (String string : cookieStrings) {
				cookieString+=string;
				System.out.println(string);
			}
			
			uc = u.openConnection();
			setCookiePropertyInUrlConn(uc, cookieString);
			
			
URL a = new URL("http://mybudgetpal.com/ws/server.php?a=getbudgets");
			
			URLConnection ac = u.openConnection();
			
			ac.connect();
						
			cookieStrings = getCookiePropertyFtomUrlConn(ac);
			
			
			for (String string : cookieStrings) {
				cookieString+=string;
				System.out.println(string);
			}
			
			ac = a.openConnection();
			setCookiePropertyInUrlConn(ac, cookieString);
			

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}