package ocr;

public class Main {

	public static void main(String[] args) {
		
		
		String [] dict = {"Mielunka", "mienso mielone","Gurnh wildh","Dolina krzemowh","Kmlosze","Kwoth","Kmlosze kwoth"};
		
		
		WordCorection w = new WordCorection();
		for (String word : dict)
		{
			System.out.printf("Poprawiany wyraz \"%s\" poprawiono na \"%s\"\n\r",word,w.CorrectWord(word));
		}

	}

}
