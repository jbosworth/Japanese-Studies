package com.gmail.jbosworth2.japanese_studies.Activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.gmail.jbosworth2.japanese_studies.R;
import com.gmail.jbosworth2.japanese_studies.Result;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchResultsFragment extends ListFragment {
	private ArrayList<HashMap<String, Result>> results;
	private SimpleAdapter adapter;
	private ListView lv;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity currentActivity = getActivity();
        results = new ArrayList<HashMap<String, Result>>();
		lv = (ListView) currentActivity.findViewById(R.id.srlist);
		if(currentActivity != null){
			adapter = new SimpleAdapter(currentActivity, results, R.layout.activity_search_results, null, null);
			setListAdapter(adapter);
		}
	}
	
	public void setResults(ArrayList<Result> input){
		HashMap<String, Result> temp = new HashMap<String, Result>();
		for(Result r : input){
			temp.put(r.getItem().getCharacter() + " (" + r.getType() + ")", r);
			results.add(temp);
		}
		adapter.notifyDataSetChanged();
	}

	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		
//		setListAdapter(new ArrayAdapter<String>(SearchActivity.class, R.layout.activity_search_results, test));
//
//		ListView listView = getListView();
//		listView.setTextFilterEnabled(true);
//
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//			    // When clicked, show a toast with the TextView text
//			    Toast.makeText(getApplicationContext(),
//				((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//			}
//		});
//	}
}
