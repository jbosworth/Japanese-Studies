package com.gmail.jbosworth2.japanese_studies.xml;

import java.util.ArrayList;
import java.util.Random;

import com.gmail.jbosworth2.japanese_studies.ContextSentence;
import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.sqlite.KanjiDbHelper;

import android.content.Context;


public class XMLReader {

	//Store kanji and vocab in lists to be retrieved by GUI through the use of special functions.
	//Originally made to hold xml data only- now data is put into SQLite tables and read back
	//into these lists as needed.
	private ArrayList<Item> kanji = new ArrayList<Item>();
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
	
	/* Reads from a given XML file. It parses and stores kanji and/or vocab within the XMLReader. 
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
			//clear lists
			this.kanji.clear();
			this.vocab.clear();
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
			this.context_sentences.clear();
			for(int j=0; j<input.get(0).size(); j++){
				int ID = Integer.parseInt(input.get(0).get(j));
				int level = Integer.parseInt(input.get(1).get(j));
				
				ContextSentence s = new ContextSentence(ID, level, input.get(2).get(j), 
						input.get(3).get(j), input.get(4).get(j));
				context_sentences.add(s);
			}
		}
	}
	
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
		this.kanji = kanji;
	}
	
	public void setWKVocab(ArrayList<Item> vocab){
		this.vocab = vocab;
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
	
	public String kanjiByLevel(int level){
		String result = "";
		String temp = "";
		for(Item i : kanji){
			if(i.getLevel() == level){
				temp = i.getCharacter() + " " + i.getMeaning() + " " + i.getKana() + "\n";
				result += temp;
			}
		}
		if(result.equals("")){
			result = "There are no kanji at this level.";
		}
		return result;
	}
	
	public String kanjiByCharacter(String character){
		String result = "";
		for(Item i : kanji){
			if(i.getCharacter().equals(character)){
				result = i.getMeaning() + " " + i.getKana();
			}
		}
		if(result.equals("")){
			result = "There is no such kanji in this app.";
		}
		return result;
	}
	
	public String kanjiByMeaning(String meaning){
		String result = "";
		String temp = "";
		for(Item i : kanji){
			for(String s : i.getMeaning()){
				if(s.equals(meaning)){
					temp = i.getCharacter() + " " + i.getKana() + "\n";
					if(!result.contains(temp)){
						result += temp;
					}
				}
			}
		}
		if(result.equals("")){
			result = "There are no kanji in this app that use that translation.";
		}
		return result;
	}
	
	public String kanjiByKana(String kana){
		String result = "";
		String temp = "";
		for(Item i : kanji){
			for(String s : i.getKana()){
				if(s.equals(kana)){
					temp = i.getCharacter() + " " + i.getMeaning() + "\n";
					if(!result.contains(temp)){
						result += temp;
					}
				}
			}
		}
		if(result.equals("")){
			result = "There are no kanji in this app with those sounds.";
		}
		return result;
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
