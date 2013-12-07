package json;

import java.security.MessageDigest;

public class Utils {


	public static String sha256(String text) {
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-256");
			sha.update(text.getBytes("UTF-8"));
			byte[] buffer = sha.digest();
			StringBuffer hash = new StringBuffer();
	        for (int i = 0; i < buffer.length; i++) {
	        	hash.append(Integer.toString((buffer[i] & 0xff) + 0x100, 16).substring(1));
	        }	
	        return hash.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}			
	}
	
}
