package ocr;

import java.util.ArrayList;
import java.util.List;

public class Sentence {

	String sentence;
	List<Word> words;
	
	public Sentence(String sentence){
		this.sentence = sentence;
		this.words = new ArrayList<Word>();
		splitToWords();
	} 
	
	private void splitToWords(){

		String [] splited = sentence.split(" ");
		for (String w : splited)
			words.add(new Word(w));
	}
	
	// Zamien ostatnia litere na replacement jezli ostatnia litera to conditon
	public void ReplaceLastIf(char replacement, char conditon){
		for (Word word : words)
			word.ReplaceLastIf(replacement, conditon);
	}
	
	@Override
	public String toString(){
		String output = "";
		for (Word word : words)
			output += word+" ";
		return output.substring(0,output.length() - 1);
	}
	
	
}
