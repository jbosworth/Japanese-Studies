package com.gmail.jbosworth2.japanese_studies.sqlite;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.sqlite.WKVocabContract.WKVocab;
import com.gmail.jbosworth2.japanese_studies.xml.XMLReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WKVocabDbHelper extends SQLiteOpenHelper {
	//XMLReader
		XMLReader reader = XMLReader.getInstance();
		//WKVocab list to be passed to reader
		ArrayList<Item> temp = new ArrayList<Item>();
		ArrayList<Item> vocab = new ArrayList<Item>();
		
		// If you change the database schema, you must increment the database version.
	    public static final int DATABASE_VERSION = 2;
	    //Last time database was updated; to be compared to last modified date on the asset WKVocab.xml
	    private static Date last_update = null;
	    public static final String DATABASE_NAME = "WKVocab.db";
	    private static final String TEXT_TYPE = " TEXT";
	    private static final String COMMA_SEP = ",";
	    private static final String SQL_CREATE_ENTRIES =
	        "CREATE TABLE " + WKVocab.TABLE_NAME + " (" +
	        WKVocab._ID + " INTEGER PRIMARY KEY," +
	        WKVocab.COLUMN_NAME_LEVEL + TEXT_TYPE + COMMA_SEP +
	        WKVocab.COLUMN_NAME_CHARACTER + TEXT_TYPE + COMMA_SEP +
	        WKVocab.COLUMN_NAME_MEANING + TEXT_TYPE + COMMA_SEP +
	        WKVocab.COLUMN_NAME_KANA + TEXT_TYPE + " )";

	    private static final String SQL_DELETE_ENTRIES =
	        "DROP TABLE IF EXISTS " + WKVocab.TABLE_NAME;

	    public WKVocabDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_ENTRIES);
	    }
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	        db.execSQL(SQL_DELETE_ENTRIES);
	        onCreate(db);
	    }
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	    
	    public void insertVocab(String level, String character, String meaning, String kana){
	    	last_update = new Date();
	    	
	    	// Gets the data repository in write mode
	    	SQLiteDatabase db = this.getWritableDatabase();

	    	// Create a new map of values, where column names are the keys
	    	ContentValues values = new ContentValues();
	    	values.put(WKVocab.COLUMN_NAME_LEVEL, level);
	    	values.put(WKVocab.COLUMN_NAME_CHARACTER, character);
	    	values.put(WKVocab.COLUMN_NAME_MEANING, meaning);
	    	values.put(WKVocab.COLUMN_NAME_KANA, kana);

	    	// Insert the new row, returning the primary key value of the new row
	    	long newRowId = db.insert(WKVocab.TABLE_NAME, null, values);
	    }
	    
	    //Select all WKVocab from table
	    public void selectFromRange(int startLevel, int endLevel, int amount){
	    	SQLiteDatabase db = this.getReadableDatabase();

	    	// Define a projection that specifies which columns from the database
	    	// you will actually use after this query.
	    	String[] projection = {
	    	    WKVocab._ID,
	    	    WKVocab.COLUMN_NAME_LEVEL,
	    	    WKVocab.COLUMN_NAME_CHARACTER,
	    	    WKVocab.COLUMN_NAME_MEANING,
	    	    WKVocab.COLUMN_NAME_KANA
	    	    };

	    	// Filter results WHERE "level" = range ("2 OR 3 OR 4" for example)
	    	String selection = WKVocab.COLUMN_NAME_LEVEL + " = ?";
	    	String range = "";
	    	for(int i = startLevel; i<endLevel - 1; i++){
	    		range += i;
	    		range += " OR ";
	    	}
	    	range += endLevel;
	    	String[] selectionArgs = { range };

	    	// How you want the results sorted in the resulting Cursor
	    	String sortOrder =
	    	    WKVocab._ID + " DESC";

	    	Cursor c = db.query(
	    	    WKVocab.TABLE_NAME,                     // The table to query
	    	    projection,                               // The columns to return
	    	    selection,                                // The columns for the WHERE clause
	    	    selectionArgs,                            // The values for the WHERE clause
	    	    null,                                     // don't group the rows
	    	    null,                                     // don't filter by row groups
	    	    sortOrder                                 // The sort order
	    	    );
	    	
	    	//Read WKVocab data from table, record by record, column by column, into Items to be put into XMLReader.WKVocab list
	    	//variables for columns/item attributes
	    	long id;
	    	int int_id;
	    	String level;
	    	int int_level;
	    	String character;
	    	String meaning;
	    	ArrayList<String> meanings;
	    	String kana;
	    	ArrayList<String> kanas;
	    	//Item to hold data; be put into WKVocab list
	    	Item i;

	    	//Read first record
	    	if (c != null && c.moveToFirst()) {
		    	id = c.getLong(c.getColumnIndexOrThrow(WKVocab._ID));
		    	int_id = (int) id;
		    	level = c.getString(c.getColumnIndexOrThrow(WKVocab.COLUMN_NAME_LEVEL));
		    	int_level = Integer.parseInt(level);
		    	character = c.getString(c.getColumnIndexOrThrow(WKVocab.COLUMN_NAME_CHARACTER));
		    	meaning = c.getString(c.getColumnIndexOrThrow(WKVocab.COLUMN_NAME_MEANING));
		    	meanings = reader.parseMR(meaning);
		    	kana = c.getString(c.getColumnIndexOrThrow(WKVocab.COLUMN_NAME_KANA));
		    	kanas = reader.parseMR(kana);
		    	//Form item and add to list
		    	i = new Item(int_id, int_level, character, meanings, kanas);
		    	temp.add(i);
	    	}
	    	//Read in the rest of the records
	    	while(c.moveToNext()){
	    		id = c.getLong(c.getColumnIndexOrThrow(WKVocab._ID));
	        	int_id = (int) id;
	        	level = c.getString(c.getColumnIndexOrThrow(WKVocab.COLUMN_NAME_LEVEL));
	        	int_level = Integer.parseInt(level);
	        	character = c.getString(c.getColumnIndexOrThrow(WKVocab.COLUMN_NAME_CHARACTER));
	        	meaning = c.getString(c.getColumnIndexOrThrow(WKVocab.COLUMN_NAME_MEANING));
	        	meanings = reader.parseMR(meaning);
	        	kana = c.getString(c.getColumnIndexOrThrow(WKVocab.COLUMN_NAME_KANA));
	        	kanas = reader.parseMR(kana);
	        	//Form item and add to list
	        	i = new Item(int_id, int_level, character, meanings, kanas);
	        	temp.add(i);
	    	}
	    	
	    	//Choose a specific amount of items at random from list to be stored in another list
			Random r = new Random();
			if(amount > temp.size()){
				amount = temp.size();
			}
			for(int j=0; j<amount; j++){
				vocab.add(temp.remove(r.nextInt(temp.size())));
			}
	    	
			//Set list for use in activity
	    	reader.setWKVocab(vocab);
	    }
	    
	    public static Date getLastUpdate(){
	    	return last_update;
	    }
}
