package com.gmail.jbosworth2.japanese_studies.Activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.gmail.jbosworth2.japanese_studies.ContextSentence;
import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.Result;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends FragmentActivity {
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
	
	private InputStream in;
	public Resources res;
	
	private EditText et;
	private TextView tv;
	private ListView lv;
	private FragmentManager fm;
	private SearchResultsFragment list;
	
	private ArrayList<Result> results;
	private ArrayList<Result> results2;
	private String sentence_result;
	
	
	
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
		
		results = new ArrayList<Result>();
		results2 = new ArrayList<Result>();
		sentence_result = "";
		
		// Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        //if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            //if (savedInstanceState != null) {
            //    return;
            //}
            
            
        //}
		
		//fm = getFragmentManager();
       // if (fm.findFragmentById(R.id.fragment_container) == null) {
       // 	list = new SearchResultsFragment();
        	//list.setArguments(getIntent().getExtras());
       // 	fm.beginTransaction().add(R.id.fragment_container, list).commit();
       // }
		
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
	}
	
	public void displayResults(){
		String result = "";
		Result temp;
		Random r = new Random();
		
		if(sentence_result != ""){//Sentence(s) found
			result = sentence_result;
		}else if(!(results.size() == 0) && (results2.size() == 0)){//Specified search results (using keys)
			if(results.get(0).getError() == null){
				if(results.size() == 1){
					result = "1 result.\n\n";
				}else{
					result = results.size() + " results.\n\n";
				}
				while(results.size() > 0){
					temp = results.remove(r.nextInt(results.size()));
					result += temp.getItem().getCharacter();//Display text, later make buttons
					result += "\n";
				}
			}
		}else if(!(results.size() == 0) && !(results2.size() == 0)){//Display searchAll
			if(results.get(0).getError() == null){//Kanji match
				if(results.size() == 1){
					result = "1 kanji result.\n\n";
				}else{
					result = results.size() + " kanji results.\n\n";
				}
				while(results.size() > 0){
					temp = results.remove(r.nextInt(results.size()));
					result += temp.getItem().getCharacter();//Display text, later make buttons
					result += "\n";
				}
				if(results2.get(0).getError() == null){//Both matched
					if(results2.size() == 1){
						result += "\n\n";
						result += "1 vocab result.\n\n";
					}else{
						result += "\n";
						result += results2.size() + " vocab results.\n\n";
					}
					while(results2.size() > 0){
						temp = results2.remove(r.nextInt(results2.size()));
						result += temp.getItem().getCharacter();//Display text, later make buttons
						result += "\n";
					}
				}
			}else{
				result = results.get(0).getError();
				if(results2.get(0).getError() != null){//Neither matched
					result += "\n";
					result += results2.get(0).getError();
				}else{//No kanji match, but vocab match
					if(results2.size() == 1){
						result = "1 vocab result.\n\n";
					}else{
						result = results.size() + " vocab results.\n\n";
					}
					while(results2.size() > 0){
						temp = results2.remove(r.nextInt(results2.size()));
						result += temp.getItem().getCharacter();//Display text, later make buttons
						result += "\n";
					}
				}
			}
		}
		tv.setText(result);
	}
	
	public void search(View v){
		results.clear();
		results2.clear();
		sentence_result = "";
		String input = et.getText().toString();
		//Parse input for kanji, vocab, grammar, and context sentences
		if(input.contains(kanji)){
			String type = "kanji";
			//ArrayList<Item> list = reader.getKanji();
			String temp = input.replace(kanji, "");
			//results = searchItem(temp.trim(), type, list);
		}else if(input.contains(vocab)){
			String type = "vocab";
			//ArrayList<Item> list = reader.getVocab();
			String temp = input.replace(vocab, "");
			//results = searchItem(temp.trim(), type, list);
		}else if(input.contains(context_sentences)){
			//ArrayList<ContextSentence> list = reader.getContextSentences();
			String temp = input.replace(context_sentences, "");
			//searchContextSentences(temp.trim(), list);
		}//else if(input.contains(grammar)){
		 //	String temp = input.replace(grammar, "");
		 //	result = searchGrammar(temp.trim());
		//}
		else{
			searchAll(input);
		}
		
		displayResults();
		//ArrayList<Result> list_input = new ArrayList<Result>();
		//list_input.addAll(results);
		//list_input.addAll(results2);
		//list.setResults(list_input);
		//updateFragment();
	}
	
	public ArrayList<Result> searchItem(String input, String type, ArrayList<Item> list){
		ArrayList<Result> rs = new ArrayList<Result>();
		Result result;
		if(input.contains(level)){//Display all kanji/vocab of a specified level with its character, meaning(s), and kana
			String temp = input.replace(level, " ");
			try{
				int level = Integer.parseInt(temp.trim());
				if(1 <= level && level <= 60){
					//rs = reader.itemByLevel(level, type, list, res);
				}else{
					result = new Result(null, type, res.getString(R.string.sile2));
					rs.add(result);
				}
			}catch(NumberFormatException n){
				result = new Result(null, type, res.getString(R.string.sile1));
				rs.add(result);
			}
		}else if(input.contains(character)){//Display a kanji/vocab's meaning(s) and kana
			String temp = input.replace(character, " ");
			//rs = reader.itemByCharacter(temp.trim(), type, list, res);
		}else if(input.contains(meaning)){//Display all kanji/vocab and their kana that have a specified meaning
			String temp = input.replace(meaning, " ");
			//rs = reader.itemByMeaning(temp.trim(), type, list, res);
		}else if(input.contains(kana)){//Display all kanji/vocab and their meanings that have a specified kana
			String temp = input.replace(kana, " ");
			//rs = reader.itemByKana(temp.trim(), type, list, res);
		}else if(input.contains(all)){//Display all kanji/vocab
			//rs = reader.listAllItems(list, type);
		}else if(input.contains(random)){
			//rs = reader.randomItem(list, type);
		}else{
			searchAll(input);
		}
		return rs;
	}
	
	public void searchContextSentences(String input, ArrayList<ContextSentence> list){
		if(input.contains(random)){
			//sentence_result = reader.randomSentence(list);
		}else{
			//sentence_result = reader.sentenceByVocab(input.trim(), list, res);
		}
	}
	
	public String searchGrammar(String input){
		String result = "";
		return result;
	}
	
	public void searchAll(String input){
		Result result;
		//ArrayList<Item> kanji_list = reader.getKanji();
		//ArrayList<Item> vocab_list = reader.getVocab();
		
		//Exact match search
		//results = searchItem(input+"ql", "kanji", kanji_list);//First check kanji level
		if(results.get(0).getError() != null){
			//results = reader.itemByCharacter(input, "kanji", kanji_list, res);//Check kanji character
			if(results.get(0).getError() != null){
				//results = reader.itemByMeaning(input, "kanji", kanji_list, res);//Check kanji meaning
				if(results.get(0).getError() != null){
					//results = reader.itemByKana(input, "kanji", kanji_list, res);// Check kanji reading
					if(results.get(0).getError() != null){//No kanji matches
						result = new Result(null, null, res.getString(R.string.sae2));
						results.clear();
						results.add(result);
						//results2 = reader.itemByCharacter(input, "vocab", vocab_list, res);//Check vocab character
						if(results2.get(0).getError() != null){
							//results2 = reader.itemByMeaning(input, "vocab", vocab_list, res);//Check vocab meaning
							if(results2.get(0).getError() != null){
								//results2 = reader.itemByKana(input, "vocab", vocab_list, res);// Check vocab reading
								if(results2.get(0).getError() != null){//No vocab matches either
									result = new Result(null, null, res.getString(R.string.sae3));
									results2.clear();
									results2.add(result);
								}
							}
						}
					}else{
						//results2 = reader.itemByKana(input, "vocab", vocab_list, res);//Check vocab reading, too
						if(results2.get(0).getError() != null){
							result = new Result(null, null, res.getString(R.string.sae3));
							results2.clear();
							results2.add(result);
						}
					}
				}else{
					//results2 = reader.itemByMeaning(input, "vocab", vocab_list, res);//Check vocab meaning, too
					if(results2.get(0).getError() != null){
						result = new Result(null, null, res.getString(R.string.sae3));
						results2.clear();
						results2.add(result);
					}
				}
			}else{
				//results2 = reader.itemByCharacter(input, "vocab", vocab_list, res);//Check vocab character, too
				if(results2.get(0).getError() != null){
					result = new Result(null, null, res.getString(R.string.sae3));
					results2.clear();
					results2.add(result);
				}
			}
		}else{//If it worked for kanji level, it will work for vocab level
			//results2 = searchItem(input+"ql", "vocab", vocab_list);
		}
		
		
		
		if(results.size() == 0 && results2.size() == 0){
			result = new Result(null, null, res.getString(R.string.sae1));
			results.add(result);
		}
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
			//reader.readFile(sb, fn);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//public void updateFragment(){
		//if (fm.findFragmentById(R.id.fragment_container) == null) {
        	//list = new SearchResultsFragment();
        	//list.setArguments(getIntent().getExtras());
	//		fm.beginTransaction().remove(list).commit();
    //    	fm.beginTransaction().add(R.id.fragment_container, list).commit();
        //}
	//}
}
