package ocr;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		WordCorection w = new WordCorection();
		String src = "Turba T-Short 34,124A  >< 0,08 Nda  Niegaz 1,5l a 1235 , x 0,65";
		
		w.Split(src);
		w.CorrectProducts();
		
		ArrayList<String> p = (ArrayList<String>) w.getProducts();
		ArrayList<Double> d = (ArrayList<Double>) w.getAmmounts();
		
		for (int i =0; i<p.size() ;i++ )
			System.out.println(p.get(i)+"   "+d.get(i));
		

	}

}
