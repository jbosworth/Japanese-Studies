package com.gmail.jbosworth2.japanese_studies;

import java.util.ArrayList;

//Item represents a single Kanji or Vocab word
public class Item {
	private int ID;
	private int Level;
	private String Character;
	private ArrayList<String> Meaning;
	private String Kana;
	
	public Item (int ID, int Level, String Character, ArrayList<String> Meaning, String Kana){
		this.ID = ID;
		this.Level = Level;
		this.Character = Character;
		this.Meaning = Meaning;
		this.Kana = Kana;
	}

	public int getID() {
		return ID;
	}

	public int getLevel() {
		return Level;
	}

	public String getCharacter() {
		return Character;
	}

	public ArrayList<String> getMeaning() {
		return Meaning;
	}

	public String getKana() {
		return Kana;
	}
}
