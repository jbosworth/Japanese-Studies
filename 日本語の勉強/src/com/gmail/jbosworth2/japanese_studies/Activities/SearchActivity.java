package com.gmail.jbosworth2.japanese_studies.Activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.gmail.jbosworth2.japanese_studies.ContextSentence;
import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.xml.XMLReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends Activity{
	private static final String kanji = "qk";
	private static final String vocab = "qv";
	private static final String context_sentences = "qcs";
	private static final String grammar = "qg";
	private static final String level = "ql";
	private static final String character = "qc";
	private static final String meaning = "qm";
	private static final String kana = "qn";
	private static final String random = "qr";
	private static final String all = "qa";
	
	private XMLReader reader;
	private InputStream in;
	private Resources res;
	
	private EditText et;
	private TextView tv;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.lang:
	            Intent i = new Intent(this, ChangeLocaleActivity.class);
	            startActivity(i);
	            return true;
	        case R.id.return_home:
	        	Intent j = new Intent(this, MainActivity.class);
	        	startActivity(j);
	        	return true;
	        case R.id.install:
	        	Intent k = new Intent(this, InstallationActivity.class);
	        	startActivity(k);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		et = (EditText) findViewById(R.id.set1);
		tv = (TextView) findViewById(R.id.stv);
		
		res = getResources();
		reader = XMLReader.getInstance();
		
		if(!MainActivity.isKanjiLoaded()){//Load kanji if it isn't already
			loadFile("kanji.xml");
			MainActivity.setKanjiLoaded(true);
		}
		if(!MainActivity.isVocabLoaded()){//Load vocab if it isn't already
			loadFile("vocab.xml");
			MainActivity.setVocabLoaded(true);
		}
		if(!MainActivity.isContextSentencesLoaded()){//Load context sentences if they aren't already
			loadFile("context_sentences.xml");
			MainActivity.setContextSentencesLoaded(true);
		}
		reader = XMLReader.getInstance();
	}
	
	public void search(View v){
		String input = et.getText().toString();
		String result = "";
		//Parse input for #kanji, #vocab, #grammar
		if(input.contains(kanji)){
			String type = "kanji";
			ArrayList<Item> list = reader.getKanji();
			String temp = input.replace(kanji, "");
			result = searchItem(temp.trim(), type, list);
		}else if(input.contains(vocab)){
			String type = "vocab";
			ArrayList<Item> list = reader.getVocab();
			String temp = input.replace(vocab, "");
			result = searchItem(temp.trim(), type, list);
		}else if(input.contains(context_sentences)){
			ArrayList<ContextSentence> list = reader.getContextSentences();
			String temp = input.replace(context_sentences, "");
			result = searchContextSentences(temp.trim(), list);
		}else if(input.contains(grammar)){
			String temp = input.replace(grammar, "");
			result = searchGrammar(temp.trim());
		}else{
			result = searchAll(input);
		}
		
		//Display result
		tv.setText(result);
	}
	
	public String searchItem(String input, String type, ArrayList<Item> list){
		String result = "";
		if(input.contains(level)){//Display all kanji/vocab of a specified level with its character, meaning(s), and kana
			String temp = input.replace(level, " ");
			try{
				int level = Integer.parseInt(temp.trim());
				if(1 <= level && level <= 60){
					result = reader.itemByLevel(level, type, list, res);
				}else{
					result = res.getString(R.string.sile2);
				}
			}catch(NumberFormatException n){
				result = res.getString(R.string.sile1) + "\n" + temp;
			}
		}else if(input.contains(character)){//Display a kanji/vocab's meaning(s) and kana
			String temp = input.replace(character, " ");
			result = reader.itemByCharacter(temp.trim(), type, list, res);
		}else if(input.contains(meaning)){//Display all kanji/vocab and their kana that have a specified meaning
			String temp = input.replace(meaning, " ");
			result = reader.itemByMeaning(temp.trim(), type, list, res);
		}else if(input.contains(kana)){//Display all kanji/vocab and their meanings that have a specified kana
			String temp = input.replace(kana, " ");
			result = reader.itemByKana(temp.trim(), type, list, res);
		}else if(input.contains(all)){//Display all kanji/vocab
			String temp = input.replace(all, "");
			result = reader.listAllItems(list);
		}else if(input.contains(random)){
			String temp = input.replace(random, "");
			result = reader.randomItem(list);
		}else{
			if(type.equals("kanji")){
				//searchallkanji
			}else if(type.equals("vocab")){
				//searchallvocab
			}
		}
		return result;
	}
	
	public String searchContextSentences(String input, ArrayList<ContextSentence> list){
		String result = "";
		if(input.contains(random)){
			String temp = input.replace(random, "");
			result = reader.randomSentence(list);
		}else{
			result = reader.sentenceByVocab(input.trim(), list, res);
		}
		return result;
	}
	
	public String searchGrammar(String input){
		String result = "";
		return result;
	}
	
	public String searchAll(String input){
		String result = "";
		//if number between 1 and 60, display items by level
		//if single word, vocab and kanji lookup (display which is which, e.g. 愛 k. love
		//	eventually grammar and dictionary, too. Could create a topic list for grammar.
		
		//Can create a series of checks, rate result, if "" go to next search.
		
		//else
		result = res.getString(R.string.sae1);
		return result;
	}
	
	public void loadFile(String fn){
		try {
			in = getAssets().open(fn);
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(in));
	        StringBuilder sb = new StringBuilder();
	        String inline = "";
	        while ((inline = inputReader.readLine()) != null) {
	          sb.append(inline);
	        }
			reader.readFile(sb, fn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
