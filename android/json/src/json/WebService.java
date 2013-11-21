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
    private String getJsonFromUrl(String url) throws IOException {
        try {
            connection = new URL(wsPath + url).openConnection();
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
    public boolean Register(String login, String password, String email) {
        String url = "server.php?a=register&login=" + login + "&password=" + password + "&email=" + email;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean Login(String user, String password) {
        String url = "server.php?a=login&user=" + user + "&password=" + Utils.sha256(password);
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean Logout() {
        String url = "server.php?a=logout";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Budget GetBudgets() {
        String url = "server.php?a=getbudgets";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Budget.class);
        }
    }
    public boolean AddBudget(String name, String description) {
        String url = "server.php?a=addbudget&name=" + name + "&description=" + description;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean UpdateBudget(int budgetId, String name, String description) {
        String url = "server.php?a=updatebudget&budgetId=" + budgetId + "&name=" + name + "&description=" + description;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean DeleteBudget(int budgetId) {
        String url = "server.php?a=deletebudget&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public ProductsCategories GetProductCategories() {
        String url = "server.php?a=getproductcategories";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, ProductsCategories.class);
        }
    }
    public boolean AddProductCategory(String name) {
        String url = "server.php?a=addproductcategory&name=" + name;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public IncomeCategories GetIncomeCategories() {
        String url = "server.php?a=getincomecategories";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, IncomeCategories.class);
        }
    }
    public boolean AddIncomeCategory(String name) {
        String url = "server.php?a=addincomecategory&name=" + name;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean AddProduct(int product_cat, String name) {
        String url = "server.php?a=addproduct&product_cat=" + product_cat + "&name=" + name;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Products GetProducts() {
        String url = "server.php?a=getproducts";
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Products.class);
        }
    }
    public Expenses GetExpenses(int budgetId) {
        String url = "server.php?a=getexpenses&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Expenses.class);
        }
    }
    public boolean AddExpense(int budgetId, String name, double amount, int purchaseId = -1) {
        String url = "server.php?a=addexpense&budgetId=" + budgetId + "&name=" + name + "&amount=" + amount + "&purchaseId=" + purchaseId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public boolean UpdateExpense(int expenseId, String name, double amount, int purchaseId) {
        String url = "server.php?a=updateexpense&expenseId=" + expenseId + "&name=" + name + "&amount=" + amount + "&purchaseId=" + purchaseId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    
    public boolean UpdateExpense(int expenseId, String name, double amount) {
    	return UpdateExpense(expenseId, name, amount, -1);
    }
    
    public boolean DeleteExpense(int expenseId) {
        String url = "server.php?a=deleteexpense&expenseId=" + expenseId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Incomes GetIncomes(int budgetId) {
        String url = "server.php?a=getincomes&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Incomes.class);
        }
    }
    public boolean AddIncome(int budgetId, String name, double amount, int incomeCategory) {
        String url = "server.php?a=addincome&budgetId=" + budgetId + "&name=" + name + "&amount=" + amount + "&incomeCategory=" + incomeCategory;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isError()) return false;
        else return true;
    }
    public Activities GetRecentActivities(int budgetId, String order = "DESC", int limit = 20) {
        String url = "server.php?a=getrecentactivities&budgetId=" + budgetId + "&order=" + order + "&limit=" + limit;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return null;
        } else {
            return new Gson().fromJson(json, Activities.class);
        }
    }
    
    
    public double GetIncomesSum(int budgetId) {
        String url = "server.php?a=getincomessum&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return -1;
        } else {
            return new Gson().fromJson(json, double.class);
        }
    }
    public double GetExpensesSum(int budgetId) {
        String url = "server.php?a=getexpensessum&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return -1;
        } else {
            return new Gson().fromJson(json, double.class);
        }
    }
    public double GetBudgetBilans(int budgetId) {
        String url = "server.php?a=getbudgetbilans&budgetId=" + budgetId;
        this.getJsonFromUrl(url);
        this.status = new GetStatus(json);
        if (this.status.isSet()) {
            return -1;
        } else {
            return new Gson().fromJson(json, double.class);
        }
    }
}