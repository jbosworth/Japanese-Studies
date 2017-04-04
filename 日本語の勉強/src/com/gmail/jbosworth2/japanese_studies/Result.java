package com.gmail.jbosworth2.japanese_studies;

public class Result {
	private Item item;
	private String type;
	private String sentences;
	private String error;
	
	public Result(Item item, String type, String error){
		this.item = item;
		this.type = type;
		this.error = error;
	}
	
	public Item getItem(){
		return item;
	}
	
	public String getType(){
		return type;
	}
	
	public String getError(){
		return error;
	}
	
	public String getSentences(){
		gatherSentences();
		return sentences;
	}
	
	public void gatherSentences(){
		//XMLReader reader = XMLReader.getInstance();
		String temp = "";
		if(type.equals("vocab")){
			//temp = reader.sentenceByVocab(item.getCharacter(), reader.getContextSentences(), null);
			sentences = temp;
		}else if(type.equals("kanji")){
			//for(ContextSentence s : reader.getContextSentences()){
				//if(s.getJapanese_Sentence().contains(item.getCharacter())){
					//temp = s.getJapanese_Sentence();
					//if(!sentences.contains(temp)){
					//	sentences += temp;
					//	sentences += "\n";
					//}
				//}
			//}
		}
	}
}
