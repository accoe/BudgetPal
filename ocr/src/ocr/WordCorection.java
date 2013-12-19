package ocr;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class WordCorection {
	// Zawiera posortowane wyrazy
	TreeSet<String> dictionary;
	int threshold;
	String corrected;
	List<String> products;
	List<Double> ammounts;

	public WordCorection(int thresh) {
		this.threshold = thresh;
		this.dictionary = new TreeSet<String>();
		FillDictionary();
		products = new ArrayList<String>();
		ammounts = new ArrayList<Double>();
		corrected = "";
	}

	public WordCorection() {
		this(5);
	}

	private int countOccurrence(String text, String fragment) {
		int count = 0;
		int len = fragment.length();
		for (int i = 0; i < text.length() - len + 1; i++) {
			if (text.substring(i, i + len).equals(fragment))
				count++;
		}
		return count;
	}

	private int indexOfNextLetter(String text) {
		char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'y', 'z', 'ó', 'ą', 'ć', 'ę', 'ł', 'ń', 'ś', 'ż', 'ź' };
		for (int i = 0; i < text.length(); i++)
			for (char ch : letters) {
				String character = "";
				character += ch;
				if (character.equals(text.substring(i, i + 1)))
					return i;
			}
		return -1;
	}

	private String ClearProduct(String str) {
		str = str.replaceAll("[^\\p{L} -.]", "");
		str = str.replace(',', ' ');
		String[] strarr = str.split(" ");
		String out = "";
		for (String s : strarr)
			if (s.length() > 1)
				out += s + " ";
		return out;
	}

	private String ClearAmmount(String str) {
		str = str.replaceAll("[^\\d,.]", "");
		str = str.replace(',', '.');
		return str;
	}

	public void Split(String text) {
		text = text.toLowerCase();
		int count = 0;
		count += countOccurrence(text, "x");
		count += countOccurrence(text, "><");

		for (int i = 0; i < count; i++) {

			int index = text.indexOf("><");
			if (index < 0)
				index = text.indexOf("x");
			if (index > -1) {
				String product = text.substring(0, index);
				int nextLetter = indexOfNextLetter(text.substring(index + 1));
				String ammount = "";
				if (nextLetter > -1)
					ammount = text.substring(index + 1, index + nextLetter);
				else
					ammount = text.substring(index + 1);
				text = text.substring(index + nextLetter);
				products.add(ClearProduct(product));
				try {
					ammounts.add(Double.parseDouble(ClearAmmount(ammount)));
				} catch (Exception e) {

				}
			}
		}
	}

	public void DictionaryAppend(List<String> list) {
		this.dictionary.addAll(list);
	}

	public void FillDictionary(List<String> dict){
		for (String str : dict)
			this.dictionary.add(str.toLowerCase());	
	}
	
	private void FillDictionary() {
		this.dictionary.add("mięso mielone");
		this.dictionary.add("mielone");
		this.dictionary.add("pragon");
		this.dictionary.add("fiskalny");
		this.dictionary.add("Górna Wilda");
		this.dictionary.add("torba t-shirt");
		this.dictionary.add("woda niegaz");
		this.dictionary.add("jogurt fruv");
		this.dictionary.add("ser camembert");
		this.dictionary.add("marchew z groszkiem");
		this.dictionary.add("ryż suprem");
		this.dictionary.add("filet z piersi kurczaka");
		this.dictionary.add("pasztet profi");
		this.dictionary.add("pączek");
		this.dictionary.add("czekolada do picia");
		this.dictionary.add("mleko");
		this.dictionary.add("orzeszki nutto");
	}

	// Zwraca true w przypadku ustawienia nowego poprawnego wyrazu
	private boolean SetCorrected(String word) {
		this.corrected = word;
		return true;
	}

	private boolean IsInDictionary(String word) {
		// Jeżeli słowo jest w słowniku - to nie wymaga poprawy?
		if (this.dictionary.contains(word)) {
			this.corrected = word;
			return true;
		}
		return false;
	}

	private boolean CorrectUsingDictionary(String word) {
		String corrected = "";
		int distance = 5;

		// Znajdz slowo najbardziej podobne do zadanego
		LevensteinDistance.SetModel(word);
		for (String compared : dictionary) {
			int computed = LevensteinDistance.calculateDistance(compared);
			if (computed < distance) {
				distance = computed;
				corrected = compared;
			}
		}

		// Jeżeli uznamy, że znaleźliśmy wystarczajaco dobre dopasowanie
		if (distance < this.threshold) {
			return SetCorrected(corrected);
		}
		return false;
	}

	private boolean CorrectUsingRules(String word) {
		// Zamien wszystkie litery h na końcu wyrazu na literę a
		Sentence sentence = new Sentence(word.toLowerCase());

		sentence.ReplaceLastIf('a', 'h');
		sentence.ReplaceBetweenConsonantsIf('a', 'm');

		return SetCorrected(sentence.toString());
	}

	public String CorrectWord(String word) {
		this.corrected = word;

		if (IsInDictionary(word))
			return this.corrected;

		if (CorrectUsingDictionary(word))
			return this.corrected;

		if (CorrectUsingRules(word))
			return this.corrected;

		return this.corrected;
	}

	public void CorrectProducts() {
		for (int i = 0; i < products.size(); i++) {
			products.set(i, CorrectWord(products.get(i)));
		}
	}

	public List<String> getProducts() {
		return this.products;
	}

	public List<Double> getAmmounts() {
		return this.ammounts;
	}
}
