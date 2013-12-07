package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.google.gson.Gson;
public class WebService {
    GetStatus status;
    URLConnection connection;
    String json;
    String wsPath = "http://mybudgetpal.com/ws/";
    ArrayList<String> cookies;
    boolean isLogin = false;
    
    public WebService(){
    	cookies = new ArrayList<String>();
    }
    private void getCookies(String url) throws Exception{
    	connection = new URL(wsPath + url).openConnection();
		cookies.addAll(0, connection.getHeaderFields().get("Set-Cookie"));
    }
    private void setCookies(String url) throws Exception{
    	URL myUrl = new URL(wsPath + url);
    	connection = myUrl.openConnection();
    	for (String cookie : cookies) {
    	    connection.setRequestProperty("Cookie", cookie.split(";", 1)[0]);
    	}
    	connection.connect();    
    }
    private String getJsonFromUrl(String url) throws IOException {
    	
        try {
            if (cookies.isEmpty())
            	getCookies(url);
            else 
            	setCookies(url);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String json = "";
        String line;
        while ((line = in .readLine()) != null) {
            json += line.trim();
        } in .close();
        this.json = json;
        return json;
    }
    public boolean Register(String login, String password, String email) throws Exception {
        String url = "server.php?a=register&login=" + login + "&password=" + password + "&email=" + email;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean Login(String user, String password) throws Exception {
        String url = "server.php?a=login&user=" + user + "&password=" + Utils.sha256(password);
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean CheckStatus() {
    	return isLogin; 
    }
    public boolean SetStatus() {
    	isLogin = true;
    	return isLogin;
    }
    public boolean ResetStatus() {
    	isLogin = false;
    	return isLogin;
    }
    public boolean Logout() throws Exception {
        String url = "server.php?a=logout";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Budgets GetBudgets() throws Exception {
        String url = "server.php?a=getbudgets";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Budgets.class);
        }
    }
    public boolean AddBudget(String name, String description) throws Exception {
        String url = "server.php?a=addbudget&name=" + name + "&description=" + description;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean UpdateBudget(int budgetId, String name, String description) throws Exception {
        String url = "server.php?a=updatebudget&budgetId=" + budgetId + "&name=" + name + "&description=" + description;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean DeleteBudget(int budgetId) throws Exception {
        String url = "server.php?a=deletebudget&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public ProductsCategories GetProductCategories() throws Exception {
        String url = "server.php?a=getproductcategories";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, ProductsCategories.class);
        }
    }
    public boolean AddProductCategory(String name) throws Exception {
        String url = "server.php?a=addproductcategory&name=" + name;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public IncomeCategories GetIncomeCategories() throws Exception {
        String url = "server.php?a=getincomecategories";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, IncomeCategories.class);
        }
    }
    public boolean AddIncomeCategory(String name) throws Exception {
        String url = "server.php?a=addincomecategory&name=" + name;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean AddProduct(int product_cat, String name) throws Exception {
        String url = "server.php?a=addproduct&product_cat=" + product_cat + "&name=" + name;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Products GetProducts() throws Exception {
        String url = "server.php?a=getproducts";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Products.class);
        }
    }
    public Expenses GetExpenses(int budgetId) throws Exception {
        String url = "server.php?a=getexpenses&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Expenses.class);
        }
    }
    public boolean AddExpense(int budgetId, String name, double amount, int purchaseId) throws Exception {
    	String url = "server.php?a=addexpense&budgetId=" + budgetId + "&name=" + name + "&amount=" + amount + "&purchaseId=" + purchaseId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean AddExpense(int budgetId, String name, double amount) throws Exception {
        return AddExpense(budgetId, name, amount, -1);
    }
    public boolean UpdateExpense(int expenseId, String name, double amount, int purchaseId) throws Exception {
        String url = "server.php?a=updateexpense&expenseId=" + expenseId + "&name=" + name + "&amount=" + amount + "&purchaseId=" + purchaseId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean UpdateExpense(int expenseId, String name, double amount) throws Exception {
    	return UpdateExpense(expenseId, name, amount, -1);
    }
    public boolean DeleteExpense(int expenseId) throws Exception {
        String url = "server.php?a=deleteexpense&expenseId=" + expenseId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Incomes GetIncomes(int budgetId) throws Exception {
        String url = "server.php?a=getincomes&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Incomes.class);
        }
    }
    public boolean AddIncome(int budgetId, String name, double amount, int incomeCategory) throws Exception {
        String url = "server.php?a=addincome&budgetId=" + budgetId + "&name=" + name + "&amount=" + amount + "&incomeCategory=" + incomeCategory;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean AddIncome(int budgetId, String name, double amount) throws Exception {
        String url = "server.php?a=addincome&budgetId=" + budgetId + "&name=" + name + "&amount=" + amount + "&incomeCategory=1";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Activities GetRecentActivities(int budgetId, String order, int limit, int offset) throws Exception {
    	String url = "server.php?a=getrecentactivities&budgetId="+budgetId+"&order="+order+"&limit="+limit+"&offset="+offset;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null; 
    	} else {	
    		return new Gson().fromJson(json, Activities.class);
    	}
    }
    
    public Activities GetRecentActivities(int budgetId, String order, int limit) throws Exception {
    	String url = "server.php?a=getrecentactivities&budgetId="+budgetId+"&order="+order+"&limit="+limit+"&offset=0";
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null; 
    	} else {	
    		return new Gson().fromJson(json, Activities.class);
    	}
    }    
    public Activities GetRecentActivities(int budgetId, int limit, int offset) throws Exception {
    	String url = "server.php?a=getrecentactivities&budgetId="+budgetId+"&order=DESC"+"&limit="+limit+"&offset="+offset;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null; 
    	} else {	
    		return new Gson().fromJson(json, Activities.class);
    	}
    }
    public Activities GetRecentActivities(int budgetId, int limit) throws Exception {
    	String url = "server.php?a=getrecentactivities&budgetId="+budgetId+"&order=DESC&limit="+limit+"&offset=0";
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null; 
    	} else {	
    		return new Gson().fromJson(json, Activities.class);
    	}
    }
    public double GetIncomesSum(int budgetId) throws Exception {
        String url = "server.php?a=getincomessum&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return -1;
        } else {
            return new Gson().fromJson(json, double.class);
        }
    }
    public double GetExpensesSum(int budgetId) throws Exception {
        String url = "server.php?a=getexpensessum&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return -1;
        } else {
            return new Gson().fromJson(json, double.class);
        }
    }
    public double GetBudgetBilans(int budgetId) throws Exception {
        String url = "server.php?a=getbudgetbilans&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return -1;
        } else {
            return new Gson().fromJson(json, double.class);
        }
    }
    public Notificatons GetNotifications(boolean all) throws Exception {
    	String url = "server.php?a=getnotifications&all="+all;
    	this.getJsonFromUrl(url);this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null;
    	} else {	
    		return new Gson().fromJson(json, Notificatons.class);
    	}
    }
    public boolean MarkNotificationAsRead(int notificationId) throws Exception {
    	String url = "server.php?a=marknotificationasread&notificationId="+notificationId;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isError())	
    		return false;
    	else	
    		return true;
    }
    public Notificatons CheckNotifications() throws Exception {
    	String url = "server.php?a=checknotifications";
    	this.getJsonFromUrl(url);this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null;
    	}
    	else {	
    		return new Gson().fromJson(json, Notificatons.class);
    	}
    }
    public boolean AddScheduledExpense(int budgetId, String productName, double amount, String date) throws Exception {
    	String url = "server.php?a=addscheduledexpense&budgetId="+budgetId+"&productName="+productName+"&amount="+amount+"&date="+date;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isError())	
    		return false;
    	else	
    		return true;
    }
    public ScheduledExpenses GetScheduledExpenses(int budgetId) throws Exception {
    	String url = "server.php?a=getscheduledexpenses&budgetId="+budgetId;
    	this.getJsonFromUrl(url);this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null;
    	}
    	else {	
    		return new Gson().fromJson(json, ScheduledExpenses.class);
    	}
    }
    public boolean AddScheduledIncome(int budgetId, String name, String categoryName, double amount, String date) throws Exception {
    	String url = "server.php?a=addscheduledincome&budgetId="+budgetId+"&name="+name+"&categoryName="+categoryName+"&amount="+amount+"&date="+date;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isError())	
    		return false;
    	else	
    		return true;
    }
    public ScheduledIncomes GetScheduledIncomes(int budgetId) throws Exception {
    	String url = "server.php?a=getscheduledincomes&budgetId="+budgetId;
    	this.getJsonFromUrl(url);this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null;
    	}
    	else {	
    		return new Gson().fromJson(json, ScheduledIncomes.class);
    	}
   	}
    public PieChart GetExpensesPieChart(int budgetId, String date) throws Exception {
    	String url = "server.php?a=getexpensespiechart&budgetId="+budgetId+"&date="+date;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null;
    	}
    	else {	
    		return new Gson().fromJson(json, PieChart.class);
    	}
   	}
    public BarChart GetExpenseCategoryChart(int budgetId, int months, String categoryName) throws Exception {
    	String url = "server.php?a=getexpensecategorychart&budgetId="+budgetId+"&months="+months+"&categoryName="+categoryName;
    	this.getJsonFromUrl(url);this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null;
    	}
    	else {	
    		return new Gson().fromJson(json, BarChart.class);
    	}
    }
    public PieChart GetIncomesPieChart(int budgetId, String date) throws Exception {
    	String url = "server.php?a=getincomespiechart&budgetId="+budgetId+"&date="+date;
    	this.getJsonFromUrl(url);this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null;
    	}
    	else {	
    		return new Gson().fromJson(json, PieChart.class);
    	}
    }
    public BarChart GetIncomesCategoryChart(int budgetId, int months, String categoryName) throws Exception {
    	String url = "server.php?a=getincomescategorychart&budgetId="+budgetId+"&months="+months+"&categoryName="+categoryName;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null;
    	} else {	
    		return new Gson().fromJson(json, BarChart.class);
    	}
    }
    public Limits GetLimits(int budgetId) throws Exception {
    	String url = "server.php?a=getlimits&budgetId="+budgetId;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isSet()) {	
    		return null;
    	} else {	
    		return new Gson().fromJson(json, Limits.class);
    	}
    }
    public boolean AddLimit(int budgetId, int categoryId, double limit) throws Exception {
    	String url = "server.php?a=addlimit&budgetId="+budgetId+"&categoryId="+categoryId+"&limit="+limit;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isError())	
    		return false;
    	else	
    		return true;
    }
    public boolean CheckLimits(int budgetId) throws Exception {
    	String url = "server.php?a=checklimits&budgetId="+budgetId;
    	this.getJsonFromUrl(url);
    	this.status = new GetStatus(json); 
    	if (this.status.isError())	
    		return false;
    	else	
    		return true;
    }    
}