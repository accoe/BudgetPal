package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;


import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
public class WebService  {
    public GetStatus status;
    URLConnection connection;
    String json;
    String wsPath = "http://mybudgetpal.com/ws/";
    static String SSID = "myBudgetPal=a5021f40ba1a9a1492bc2ccb3b8e514f";    
    
    
	public static void setCookiePropertyInUrlConn(URLConnection uc, String cookieValue){
		uc.setRequestProperty("COOKIE",cookieValue); 
	}
	
	public static List<String> getCookiePropertyFtomUrlConn(URLConnection uc){
		ArrayList<String> result = new ArrayList<String>();
		String headerName=null;
		for (int i=1; (headerName = uc.getHeaderFieldKey(i))!=null; i++) {
		 	if (headerName.equals("Set-Cookie")) {                  
		 		result.add(uc.getHeaderField(i));  
		 	}
		 }
		return result;
	}
	
	public static String getCookie(URLConnection uc){
		ArrayList<String> result = new ArrayList<String>();
		String headerName=null;
		String d=null;
		for (int i=1; (headerName = uc.getHeaderFieldKey(i))!=null; i++) {
		 	if (headerName.equals("Set-Cookie")) {                  
		 		d = uc.getHeaderField(i);  
		 	}
		 }
		return d;
	}

	private String getJsonFromUrl(String url,String SSID) throws IOException {
        try {
            connection = new URL(wsPath + url + "&myBudgetPal=a5021f40ba1a9a1492bc2ccb3b8e514f&").openConnection();
            getCookiePropertyFtomUrlConn(connection);
            Log.e("get1",getCookie(connection));
            connection.setRequestProperty("myBudgetPal", "a5021f40ba1a9a1492bc2ccb3b8e514f");
            connection.connect();
            /*setCookiePropertyInUrlConn(connection,SSID);
            Log.e("cookie", SSID);*/
            getCookiePropertyFtomUrlConn(connection);
            Log.e("get2",getCookie(connection));
            
// + "PHPSESSID="+SSID
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String json = "";
        String line;
        while ((line = in .readLine()) != null) {
            json += line.trim();
        } in.close();
        this.json = json;
        return json;
    }
    public boolean Register(String login, String password, String email) throws Exception {
        String url = "server.php?a=register&login=" + login + "&password=" + password + "&email=" + email;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean Login(String user, String password) throws Exception {
        String url = "server.php?a=login&user=" + user + "&password=" + Utils.sha256(password);
        /*List<String> cookieStrings = getCookiePropertyFtomUrlConn(connection);
		String cookieString = "";
		for (String string : cookieStrings) {
			cookieString+=string;
			SSID = string.substring(12, 44);
		}*/
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean Logout() throws Exception {
        String url = "server.php?a=logout";
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Budgets GetBudgets() throws Exception {
        String url = "server.php?a=getbudgets";
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Budgets.class);
        }
    }
    public boolean AddBudget(String name, String description) throws Exception {
        String url = "server.php?a=addbudget&name=" + name + "&description=" + description;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean UpdateBudget(int budgetId, String name, String description) throws Exception {
        String url = "server.php?a=updatebudget&budgetId=" + budgetId + "&name=" + name + "&description=" + description;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean DeleteBudget(int budgetId) throws Exception {
        String url = "server.php?a=deletebudget&budgetId=" + budgetId;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public ProductsCategories GetProductCategories() throws Exception {
        String url = "server.php?a=getproductcategories";
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, ProductsCategories.class);
        }
    }
    public boolean AddProductCategory(String name) throws Exception {
        String url = "server.php?a=addproductcategory&name=" + name;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public IncomeCategories GetIncomeCategories() throws Exception {
        String url = "server.php?a=getincomecategories";
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, IncomeCategories.class);
        }
    }
    public boolean AddIncomeCategory(String name) throws Exception {
        String url = "server.php?a=addincomecategory&name=" + name;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean AddProduct(int product_cat, String name) throws Exception {
        String url = "server.php?a=addproduct&product_cat=" + product_cat + "&name=" + name;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Products GetProducts() throws Exception {
        String url = "server.php?a=getproducts";
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Products.class);
        }
    }
    public Expenses GetExpenses(int budgetId) throws Exception {
        String url = "server.php?a=getexpenses&budgetId=" + budgetId;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Expenses.class);
        }
    }
    public boolean AddExpense(int budgetId, String name, double amount, int purchaseId) throws Exception {
    	String url = "server.php?a=addexpense&budgetId=" + budgetId + "&name=" + name + "&amount=" + amount + "&purchaseId=" + purchaseId;
    	this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    
    public boolean AddExpense(int budgetId, String name, double amount) throws Exception {
        return AddExpense(budgetId, name, amount, -1);
    }
    
    public boolean UpdateExpense(int expenseId, String name, double amount, int purchaseId) throws Exception {
        String url = "server.php?a=updateexpense&expenseId=" + expenseId + "&name=" + name + "&amount=" + amount + "&purchaseId=" + purchaseId;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    
    public boolean UpdateExpense(int expenseId, String name, double amount) throws Exception {
    	return UpdateExpense(expenseId, name, amount, -1);
    }
    
    public boolean DeleteExpense(int expenseId) throws Exception {
        String url = "server.php?a=deleteexpense&expenseId=" + expenseId;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Incomes GetIncomes(int budgetId) throws Exception {
        String url = "server.php?a=getincomes&budgetId=" + budgetId;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Incomes.class);
        }
    }
    public boolean AddIncome(int budgetId, String name, double amount, int incomeCategory) throws Exception {
        String url = "server.php?a=addincome&budgetId=" + budgetId + "&name=" + name + "&amount=" + amount + "&incomeCategory=" + incomeCategory;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    
    public Activities GetRecentActivities(int budgetId, String order, int limit) throws Exception {
        String url = "server.php?a=getrecentactivities&budgetId=" + budgetId + "&order=DESC" + order + "&limit=" + limit;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Activities.class);
        }
    } 
    public Activities GetRecentActivities(int budgetId) throws Exception {
        return GetRecentActivities(budgetId, "DESC", 20);
    }
    
    public Activities GetRecentActivities(int budgetId, int limit) throws Exception {
        return GetRecentActivities(budgetId, "DESC", limit);
    }
    public Activities GetRecentActivities(int budgetId, String order) throws Exception {
        return GetRecentActivities(budgetId, order, 20);
    }
    public double GetIncomesSum(int budgetId) throws Exception {
        String url = "server.php?a=getincomessum&budgetId=" + budgetId;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return -1;
        } else {
            return new Gson().fromJson(json, double.class);
        }
    }
    public double GetExpensesSum(int budgetId) throws Exception {
        String url = "server.php?a=getexpensessum&budgetId=" + budgetId;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return -1;
        } else {
            return new Gson().fromJson(json, double.class);
        }
    }
    public double GetBudgetBilans(int budgetId) throws Exception {
        String url = "server.php?a=getbudgetbilans&budgetId=" + budgetId;
        this.getJsonFromUrl(url,SSID);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return -1;
        } else {
            return new Gson().fromJson(json, double.class);
        }
    }
}