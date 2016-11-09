package com.gmail.jbosworth2.japanese_studies;

public class ContextSentence {
	private int ID;
	private int Level;
	private String Vocab;
	private String Japanese_Sentence;
	private String English_Sentence;
	
	public ContextSentence (int ID, int Level, String Vocab, String Japanese_Sentence, String English_Sentence){
		this.ID = ID;
		this.Level = Level;
		this.Vocab = Vocab;
		this.Japanese_Sentence = Japanese_Sentence;
		this.English_Sentence = English_Sentence;
	}

	public int getID() {
		return ID;
	}

	public int getLevel() {
		return Level;
	}

	public String getVocab() {
		return Vocab;
	}

	public String getJapanese_Sentence() {
		return Japanese_Sentence;
	}

	public String getEnglish_Sentence() {
		return English_Sentence;
	}
}
