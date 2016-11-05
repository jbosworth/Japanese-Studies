package com.gmail.jbosworth2.japanese_studies.xml;

import java.util.ArrayList;
import java.util.Random;

import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.sqlite.KanjiDbHelper;

import android.content.Context;


public class XMLReader {

	//Store kanji and vocab in lists to be retrieved by GUI through the use of special functions.
	//Originally made to hold xml data only- now data is put into SQLite tables and read back
	//into these lists as needed.
	private ArrayList<Item> kanji = new ArrayList<Item>();
	private static ArrayList<Item> vocab = new ArrayList<Item>();
		
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
	
	/* Reads from a given XML file. It parses and stores kanji and/or vocab within the XMLReader. 
	 * @param fn should be the name of the input file. For example, 
	 * "kanji.xml" is what should be passed to read in kanji.
	 */
	public void readFile(StringBuilder sb, String fn){
		//clear lists
		this.kanji.clear();
		this.vocab.clear();
		
		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
		XMLFileReader reader = XMLFileReader.getInstance();
		reader.read(sb);
		input = reader.getReadable();
		ArrayList<String> meaning = new ArrayList<String>();
		ArrayList<String> kana = new ArrayList<String>();
		
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
	
	public String writingReview(){
		String s = "";
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
