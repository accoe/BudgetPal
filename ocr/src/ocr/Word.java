package ocr;

public class Word {

	char [] letters;
	char [] vowels;		// samogloski
	char [] consonants; // spolgloski
	
	String word;
	int l;
	
	public Word(String word){
		this.word = word;
		l = word.length();
	}
	
	@Override
	public String toString(){
		return word;	
	}
	
	// Zamien ostatnia litere na replacement jezli ostatnia litera to conditon
	public void ReplaceLastIf(char replacement, char conditon){
		if (word.charAt(l-1) == conditon)
			this.word = this.word.substring(0, l-1) + replacement;
	}
	
}
