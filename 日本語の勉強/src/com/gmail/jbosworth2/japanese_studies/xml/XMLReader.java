package com.gmail.jbosworth2.japanese_studies.xml;

import java.util.ArrayList;
import java.util.Random;

import com.gmail.jbosworth2.japanese_studies.ContextSentence;
import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.Result;
import com.gmail.jbosworth2.japanese_studies.sqlite.KanjiDbHelper;

import android.content.Context;
import android.content.res.Resources;


public class XMLReader {

	//Store kanji, vocab, and context sentences in lists to be retrieved by GUI through the use of functions.
	private static ArrayList<Item> kanji = new ArrayList<Item>();
	private static ArrayList<Item> vocab = new ArrayList<Item>();
	private static ArrayList<ContextSentence> context_sentences = new ArrayList<ContextSentence>();
		
	//Singleton XMLReader- only one reader necessary
	private static final XMLReader INSTANCE = new XMLReader();
	private XMLReader() {}
	public static XMLReader getInstance(){
		return INSTANCE;
	}
	
	//Getters
	public ArrayList<Item> getKanji(){
		return kanji;
	}
	
	public ArrayList<Item> getVocab(){
		return vocab;
	}
	
	public ArrayList<ContextSentence> getContextSentences(){
		return context_sentences;
	}
	
	/* Reads from a given XML file. It parses and stores kanji, vocab, and/or context sentences within the XMLReader. 
	 * @param fn should be the name of the input file. For example, 
	 * "kanji.xml" is what should be passed to read in kanji.
	 */
	public void readFile(StringBuilder sb, String fn){
		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		XMLFileReader reader = XMLFileReader.getInstance();
		reader.read(sb, fn);
		input = reader.getReadable();
		ArrayList<String> meaning = new ArrayList<String>();
		ArrayList<String> kana = new ArrayList<String>();
		
		if(fn == "kanji.xml" || fn == "vocab.xml"){
			for(int j=0; j<input.get(0).size(); j++){
				int ID = Integer.parseInt(input.get(0).get(j));
				int level = Integer.parseInt(input.get(1).get(j));
				meaning = parseMR(input.get(3).get(j));
				kana = parseMR(input.get(4).get(j));
				
				Item i = new Item(ID, level, input.get(2).get(j), 
						meaning, kana);
	
				if(fn == "kanji.xml"){
					kanji.add(i);
				}else if(fn == "vocab.xml"){
					vocab.add(i);
				}
			}
		}else if(fn == "context_sentences.xml"){
			for(int j=0; j<input.get(0).size(); j++){
				int ID = Integer.parseInt(input.get(0).get(j));
				int level = Integer.parseInt(input.get(1).get(j));
				
				ContextSentence s = new ContextSentence(ID, level, input.get(2).get(j), 
						input.get(3).get(j), input.get(4).get(j));
				context_sentences.add(s);
			}
		}
	}
	
	//Takes a comma-separated string of values from the kanji/vocab meanings/readings
	//and returns an ArrayList of the values.
	public ArrayList<String> parseMR(String s){
		ArrayList<String> result = new ArrayList<String>();
		if(s != null){
			String[] split = s.split(",");
			for(int i=0; i<split.length; i++){
				result.add(split[i].trim());
			}
		}
		return result;
	}
	
	public void setKanji(ArrayList<Item> kanji){
		XMLReader.kanji = kanji;
	}
	
	public void setWKVocab(ArrayList<Item> vocab){
		XMLReader.vocab = vocab;
	}
	

	//-----SearchActivity Functions------
	
	//Retrieves items (kanji or vocab) by Wanikani level and displays the
	//item's character, meaning(s), and reading(s)
	//Parameters: level is the level to be retrieved (1-60),
	//  type indicates whether items are kanji or vocab, list is the list of kanji or vocab,
	//  and res is the app's resources- used to retrieve predefined strings for error messages.
	public ArrayList<Result> itemByLevel(int level, String type, ArrayList<Item> list, Resources res){
		ArrayList<Result> results = new ArrayList<Result>();
		Result result;
		for(Item i : list){
			if(i.getLevel() == level){
				result = new Result(i, type, null);
				results.add(result);
			}
		}
		if(results.size() == 0){
			if(type.equals("kanji")){
				result = new Result(null, type, res.getString(R.string.skle));
				results.add(result);
			}else if(type.equals("vocab")){
				result = new Result(null, type, res.getString(R.string.svle));
				results.add(result);
			}
		}
		return results;
	}
	
	//Retrieves items (kanji or vocab) by character(s) and displays the
	//item's meaning(s) and reading(s)
	//Parameters: character is the character to be matched and retrieved,
	//  type indicates whether items are kanji or vocab, list is the list of kanji or vocab,
	//  and res is the app's resources- used to retrieve predefined strings for error messages.
	public ArrayList<Result> itemByCharacter(String character, String type, ArrayList<Item> list, Resources res){
		ArrayList<Result> results = new ArrayList<Result>();
		Result result;
		for(Item i : list){
			if(i.getCharacter().equals(character)){
				result = new Result(i, type, null);
				results.add(result);
			}
		}
		if(results.size() == 0){
			if(type.equals("kanji")){
				result = new Result(null, type, res.getString(R.string.skce));
				results.add(result);
			}else if(type.equals("vocab")){
				result = new Result(null, type, res.getString(R.string.svce));
				results.add(result);
			}
		}
		return results;
	}
	
	//Retrieves items (kanji or vocab) by English meaning and displays the
	//item's character and reading(s)
	//Parameters: meaning is the meaning to be matched and retrieved,
	//  type indicates whether items are kanji or vocab, list is the list of kanji or vocab,
	//  and res is the app's resources- used to retrieve predefined strings for error messages.
	public ArrayList<Result> itemByMeaning(String meaning, String type, ArrayList<Item> list, Resources res){
		ArrayList<Result> results = new ArrayList<Result>();
		Result result;
		for(Item i : list){
			for(String s : i.getMeaning()){
				if(s.equals(meaning)){
					result = new Result(i, type, null);
					if(!results.contains(result)){
						results.add(result);
					}
				}
			}
		}
		if(results.size() == 0){
			if(type.equals("kanji")){
				result = new Result(null, type, res.getString(R.string.skme));
				results.add(result);
			}else if(type.equals("vocab")){
				result = new Result(null, type, res.getString(R.string.svme));
				results.add(result);
			}
		}
		return results;
	}
	
	//Retrieves items (kanji or vocab) by kana reading and displays the
	//item's character and meaning(s)
	//Parameters: kana is the kana to be matched and retrieved,
	//  type indicates whether items are kanji or vocab, list is the list of kanji or vocab,
	//  and res is the app's resources- used to retrieve predefined strings for error messages.
	public ArrayList<Result> itemByKana(String kana, String type, ArrayList<Item> list, Resources res){
		ArrayList<Result> results = new ArrayList<Result>();
		Result result;
		for(Item i : list){
			for(String s : i.getKana()){
				if(s.equals(kana)){
					result = new Result(i, type, null);
					if(!results.contains(result)){
						results.add(result);
					}
				}
			}
		}
		if(results.size() == 0){
			if(type.equals("kanji")){
				result = new Result(null, type, res.getString(R.string.skke));
				results.add(result);
			}else if(type.equals("vocab")){
				result = new Result(null, type, res.getString(R.string.svke));
				results.add(result);
			}
		}
		return results;
	}
	//Retrieves all kanji or vocab
	//Parameters: list is the list of kanji or vocab, type indicates whether items are kanji or vocab
	public ArrayList<Result> listAllItems(ArrayList<Item> list, String type){
		ArrayList<Result> results = new ArrayList<Result>();
		Result result;
		
		Random r = new Random();
		for(int i=0; i<list.size(); i++){
			result = new Result(list.remove(r.nextInt(list.size())), type, null);
			results.add(result);
		}
		return results;
	}
	
	//Retrieves a random kanji or vocab
	//Parameters: list is the list of kanji or vocab, type indicates whether items are kanji or vocab
	public ArrayList<Result> randomItem(ArrayList<Item> list, String type){
		ArrayList<Result> results = new ArrayList<Result>();
		Result result;
		
		Random r = new Random();
		result = new Result(list.get(r.nextInt(list.size())), type, null);
		results.add(result);
		return results;
	}
	
	//Retrieves context sentences by vocab
	//Parameters: input is the vocab to be matched and retrieved,
	//  list is the list of context sentences,
	//  and res is the app's resources- used to retrieve predefined strings for error messages.
	public String sentenceByVocab(String input, ArrayList<ContextSentence> list, Resources res){
		String result = "";
		String temp = "";
		for(ContextSentence s : list){
			if(input.equals(s.getVocab())){
				temp = s.getJapanese_Sentence() + "\n";
				if(!result.contains(temp)){
					result += temp;
				}
			}
		}
		
		if(result.equals("")){ //If kana is used instead of the proper writing of its characters
			for(Item i : vocab){
				for(String s : i.getKana()){
					if(input.equals(s)){
						result += sentenceByVocab(i.getCharacter(), list, res);
					}
				}
			}
		}
		
		if(result.equals("") && res != null){
			result = res.getString(R.string.ssve);
		}
		return result;
	}
	
	//Retrieves a random context sentence
	//Parameters: list is the list of context sentences
	public String randomSentence(ArrayList<ContextSentence> list){
		String result = "";
		Random r = new Random();
		ContextSentence s = list.get(r.nextInt(list.size()));
		result += s.getJapanese_Sentence();
		return result;
	}
	
	
	
	//-----------Kanji Functions
	public String listKanji(Context context){
		//KanjiDbHelper kdb = new KanjiDbHelper(context);
		//kdb.selectAll();
		String s = "";
		for(Item i : kanji){
			s += i.getCharacter();
		}
		return s;
	}
	
	public Item getRandomKanji(){
		Random r1 = new Random();
		Item i = kanji.remove(r1.nextInt(kanji.size()));
		return i;
	}
	
	
	//------------Vocab Functions
	public String listVocab(){
		String s = "Vocab\tMeaning\tKana\n";
		for(Item i : vocab){
			s += i.getCharacter();
			s += "\t";
			s += i.getMeaning();
			s += "\t";
			s += i.getKana();
			s += "\n";
		}
		return s;
	}
}
