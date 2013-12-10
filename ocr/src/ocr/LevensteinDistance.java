package ocr;

public class LevensteinDistance {
	
	static String model;
	
	public static void SetModel(String model){
		LevensteinDistance.model = model.toLowerCase();
	}
	
	
	private static int min(int a, int b, int c){
		return (a < b ? 
						(a < c ? a : c) :
						(b < c ? b : c));
	}
	
	// Zwraca odleglosc Levensteina
	public static int calculateDistance(String compared){
		int m = model.length();
		int n = compared.length();
		// Deklaracja macierzy m+1 na n+1
		int [][] d = new int[m+1][n+1]; 
		
		// Zainicjalizowanie macierzy danymi
		for (int i=0;i<= m;i++)
			d[i][0] = i;
		for (int i=1;i<= n;i++)
			d[0][i] = i;
			
		// Obliczenie długości
		for (int i=1;i<= m;i++)
		{
			for (int j=1;j<= n;j++)
			{
				d[i][j] = min(d[i-1][j] + 1, 		// usuwanie
							  d[i][j-1] +1, 		// wstawianie	
							  d[i-1][j-1] + 		// zamiana
							  (model.charAt(i-1) == compared.charAt(j-1) ? 0 : 1)); // Jezeli litery sa sobie rowne to koszt wynosi 0, wpp. 1
			}
		}
		return d[m][n] - (m == n ? 1 : 0); // Preferuj wyrazy o takiej samej długości
	}
	
}
