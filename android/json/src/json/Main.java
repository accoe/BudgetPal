package json;

public class Main {

	public static void main(String[] args) {
		try {

			WebService ws = new WebService();
			
			Budgets budgets = ws.GetBudgets();
			if (budgets != null)
			for (int i=0;i< budgets.count; i++)
				System.out.println(budgets.budgets.get(i));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

}