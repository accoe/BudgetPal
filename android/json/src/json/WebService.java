package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

public class WebService {
	GetStatus status;
	URLConnection connection;
	String json;
	String wsPath = "http://mybudgetpal.com/ws/";
	
	private String getJsonFromUrl(String url) throws IOException
	{
		try {
			connection = new URL(wsPath + url).openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
		String json = "";
		String line;
		while ((line = in.readLine()) != null) {
			json += line.trim();
		}
		in.close();
		this.json = json;
		return json;
	}
	
	
	public Budgets GetBudgets() throws IOException
	{
		String url = "server2.php?a=getbudgets";
		this.getJsonFromUrl(url);
		this.status = new GetStatus(json); 
		if (this.status.isSet()){
			System.out.println(this.status);
			return null;
		}
		else
		{
			return new Gson().fromJson(json, Budgets.class);
		}
	}
	

}
