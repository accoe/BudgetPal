package json;

public class Main {

	public static void main(String[] args) {
		WebService ws = new WebService();
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
					System.out.println("Hurra! Dzia³a");
				if (ws.status.isError())
					System.out.println("No nie jakiœ b³¹d");
				if (ws.status.isInfo())
					System.out.println("Informacja");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		// Logowanie 
		try {

			if (ws.Login("krystek","trunde"))
				System.out.println("Zalogowano" + ws.status);
			else
				System.out.println("Niezalogowano" + ws.status);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		// Pobranie bilansu
		try {
			System.out.println("Bilans: " + ws.GetBudgetBilans(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

}
