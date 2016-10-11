package com.gmail.jbosworth2.japanese_studies;

//Item represents a single Kanji or Vocab word
public class Item {
	private int ID;
	private int Level;
	private String Character;
	private String Meaning;
	private String Kana;
	
	public Item (int ID, int Level, String Character, String Meaning, String Kana){
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

	public String getMeaning() {
		return Meaning;
	}

	public String getKana() {
		return Kana;
	}
	
	public void setID(int iD) {
		ID = iD;
	}

	public void setLevel(int level) {
		Level = level;
	}

	public void setCharacter(String character) {
		Character = character;
	}

	public void setMeaning(String meaning) {
		Meaning = meaning;
	}

	public void setKana(String kana) {
		Kana = kana;
	}
}
