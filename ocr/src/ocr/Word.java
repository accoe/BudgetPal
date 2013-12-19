package ocr;

public class Word {

	char [] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','y','z','ó','ą','ć','ę','ł','ń','ś','ż','ź'};
	// samogloski
	char [] vowels = {'a','e','i','o','u','ó','ą','ę'};
	// spolgloski 
	char [] consonants = {'b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','y','z','ć','ł','ń','ś','ż','ź'};
	
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
	
	private boolean isConsonant(char letter){
		for (char c : this.consonants)
			if (c == letter)
				return true;
		return false;
	}
	
	
	// Zamien ostatnia litere na replacement jezli ostatnia litera to conditon
	public void ReplaceLastIf(char replacement, char conditon){
		if (word.charAt(l-1) == conditon)
			this.word = this.word.substring(0, l-1) + replacement;
	}
	
	// Zamien litere na replacement jezli conditon znajduje sie miedzy spolgloskami
	public void ReplaceBetweenConsonantsIf(char replacement, char conditon){
		for (int i=1;i<l-1;i++)
		{
			if (word.charAt(i) == conditon)
				if (isConsonant(word.charAt(i - 1)) && isConsonant(word.charAt(i + 1)))
					this.word = this.word.substring(0,i) + replacement + this.word.substring(i+1);
		}
	}
	
	
}
