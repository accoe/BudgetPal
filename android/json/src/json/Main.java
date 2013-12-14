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

		
		try {

			Report report= ws.GetReport(1, 6);
			if (report != null){
				System.out.println("Tytul: "+report.title);
				System.out.println("Bilans: ("+report.incomes_sum+"-"+report.expenses_sum+")="+report.general_balance);
				for (int i=0;i< report.months.size(); i++){
					
					System.out.println("--------------"+report.months.get(i).month+"-"+report.months.get(i).year+"---------------");
					for (int j=0;j<report.months.get(i).activities.size();j++)
						System.out.println(report.months.get(i).activities.get(j).data+"  "+report.months.get(i).activities.get(j).nazwa+"\t"+report.months.get(i).activities.get(j).kwota+" "+report.months.get(i).activities.get(j).rodzaj);
					System.out.println("--------------SUMA: "+report.months.get(i).balance+" ---------");
					System.out.println();
				}
			}
			else
				System.out.println(ws.status);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 

		// Wykresy
		/*
		Chart chart = new Chart(ws);
		chart.properties.setSize(400,200);
		// Kołowe
		if (chart.notEmpty)
			chart.ExpensesPieChart(1,2013,12);
		else
			System.out.println("Nie ma danych");
		//chart.IncomesPieChart(1,2013,11);
		
		// Liniowe / słupkowe
		chart.properties.type = "Bar";
		chart.properties.type = "dfa";
		String[] in_cat = {"praca","inne"};
		String[] ex_cat = {"jedzenie","inne"};
		//chart.properties.sizeX = 600;
		chart.IncomesCategoryChart(1, 6, in_cat);
		//chart.ExpenseCategoryChart(1, 6, ex_cat);
		chart.SaveChartToFile("/home/kris/chart");
		*/
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
					System.out.println("Hurra! Dziala");
				if (ws.status.isError())
					System.out.println("No nie jakis blad");
				if (ws.status.isInfo())
					System.out.println("Informacja");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		*/

	}

}
