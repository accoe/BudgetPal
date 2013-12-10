package ocr;

import java.util.List;
import java.util.TreeSet;

public class WordCorection {
	// Zawiera posortowane wyrazy
	TreeSet<String> dictionary;
	int threshold;
	String corrected;
	
	public WordCorection(int thresh){
		this.threshold = thresh;
		this.dictionary = new TreeSet<String>();
		FillDictionary();
		
		corrected = "";
	}
	
	public WordCorection(){
		this(5);
	} 
	
	
	public void DictionaryAppend(List<String> list){
		this.dictionary.addAll(list);		
	}
	
	private void FillDictionary(){
		this.dictionary.add("mięso mielone");
		this.dictionary.add("mielone");
		this.dictionary.add("mielonka");
		this.dictionary.add("Górna Wilda");
	}
	
	
	// Zwraca true w przypadku ustawienia nowego poprawnego wyrazu
	private boolean SetCorrected(String word){
		this.corrected = word;
		return true;
	}
	
	private boolean IsInDictionary(String word){
		// Jeżeli słowo jest w słowniku - to nie wymaga poprawy?
		if (this.dictionary.contains(word)){
			this.corrected = word;
			return true;
		}
		return false;
	}
	
	
	private boolean CorrectUsingDictionary(String word){
		String corrected = "";
		int distance = 5;
		
		// Znajdz slowo najbardziej podobne do zadanego
		LevensteinDistance.SetModel(word);
		for (String compared : dictionary){
			int computed = LevensteinDistance.calculateDistance(compared);
			if (computed < distance){
				distance = computed;
				corrected = compared;
			}
		}
		
		// Jeżeli uznamy, że znaleźliśmy wystarczajaco dobre dopasowanie
		if (distance < this.threshold){
			return SetCorrected(corrected);
		}
		return false;
	}
	
	
	
	private boolean CorrectUsingRules(String word){
		// Zamien wszystkie litery h na końcu wyrazu na literę a
		Sentence sentence = new Sentence(word.toLowerCase());
		
		sentence.ReplaceLastIf('a', 'h');
		sentence.ReplaceBetweenConsonantsIf('a', 'm');
		
		return SetCorrected(sentence.toString());
	}
	
	
	public String CorrectWord(String word){
		this.corrected = word;
		
		if (IsInDictionary(word))
			return this.corrected;
		
		if (CorrectUsingDictionary(word))
			return this.corrected;
		
		if (CorrectUsingRules(word))
			return this.corrected;		
		
		return this.corrected;
	}
	

}
