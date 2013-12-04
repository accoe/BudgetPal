package json;
import charts.*;

public class Main {

	public static void main(String[] args) {
		WebService ws = new WebService();
		
		// Logowanie 
		try {

			if (ws.Login("test","password"))
				System.out.println("Zalogowano" + ws.status);
			else
				System.out.println("Niezalogowano" + ws.status);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		// Wykresy
		Chart chart = new Chart(ws);
		chart.properties.setSize(500,200);
		// Kołowe
		//chart.ExpensesPieChart(1,2013,12);
		//chart.IncomesPieChart(1,2013,11);
		
		// Liniowe / słupkowe
		chart.properties.type = "Bar";
		String[] in_cat = {"praca","inne"};
		String[] ex_cat = {"jedzenie","inne"};
		//chart.properties.sizeX = 600;
		//chart.IncomesCategoryChart(1, 6, in_cat);
		chart.ExpenseCategoryChart(1, 6, ex_cat);
		chart.SaveChartToFile("/home/kris/chart");
		
		/*
		try {

			Budgets budgets = ws.GetBudgets();
			if (budgets != null)
				for (int i=0;i< budgets.count; i++)
					System.out.println(budgets.budgets.get(i));
			else
				System.out.println(ws.status);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		// albo
		try {

			Budgets budgets = ws.GetBudgets();
			if (budgets != null)
				for (int i=0;i< budgets.count; i++)
					System.out.println(budgets.budgets.get(i));
			else
				if (ws.status.isOk())
					System.out.println("Hurra! Dzia�a");
				if (ws.status.isError())
					System.out.println("No nie jaki� b��d");
				if (ws.status.isInfo())
					System.out.println("Informacja");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		*/

	}

}
