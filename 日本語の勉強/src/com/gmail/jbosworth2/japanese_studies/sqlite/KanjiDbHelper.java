package com.gmail.jbosworth2.japanese_studies.sqlite;

import java.util.ArrayList;
import java.util.Date;

import com.gmail.jbosworth2.japanese_studies.Item;
import com.gmail.jbosworth2.japanese_studies.sqlite.KanjiContract.Kanji;
import com.gmail.jbosworth2.japanese_studies.xml.XMLReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KanjiDbHelper extends SQLiteOpenHelper {
	//XMLReader
	XMLReader reader = XMLReader.getInstance();
	//Kanji list to be passed to reader
	ArrayList<Item> kanji = new ArrayList<Item>();
	
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    //Last time database was updated; to be compared to last modified date on the asset kanji.xml
    private static Date last_update = null;
    public static final String DATABASE_NAME = "Kanji.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + Kanji.TABLE_NAME + " (" +
        Kanji._ID + " INTEGER PRIMARY KEY," +
        Kanji.COLUMN_NAME_LEVEL + TEXT_TYPE + COMMA_SEP +
        Kanji.COLUMN_NAME_CHARACTER + TEXT_TYPE + COMMA_SEP +
        Kanji.COLUMN_NAME_MEANING + TEXT_TYPE + COMMA_SEP +
        Kanji.COLUMN_NAME_KANA + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + Kanji.TABLE_NAME;

    public KanjiDbHelper(Context context) {
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
    
    public void insertKanji(String level, String character, String meaning, String kana){
    	last_update = new Date();
    	
    	// Gets the data repository in write mode
    	SQLiteDatabase db = this.getWritableDatabase();

    	// Create a new map of values, where column names are the keys
    	ContentValues values = new ContentValues();
    	values.put(Kanji.COLUMN_NAME_LEVEL, level);
    	values.put(Kanji.COLUMN_NAME_CHARACTER, character);
    	values.put(Kanji.COLUMN_NAME_MEANING, meaning);
    	values.put(Kanji.COLUMN_NAME_KANA, kana);

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId = db.insert(Kanji.TABLE_NAME, null, values);
    }
    
    //Select all kanji from table
    public void selectAll(){
    	SQLiteDatabase db = this.getReadableDatabase();

    	// Define a projection that specifies which columns from the database
    	// you will actually use after this query.
    	String[] projection = {
    	    Kanji._ID,
    	    Kanji.COLUMN_NAME_LEVEL,
    	    Kanji.COLUMN_NAME_CHARACTER,
    	    Kanji.COLUMN_NAME_MEANING,
    	    Kanji.COLUMN_NAME_KANA
    	    };

    	// Filter results WHERE "title" = 'My Title'
    	//String selection = Kanji.COLUMN_NAME_TITLE + " = ?";
    	//String[] selectionArgs = { "My Title" };

    	// How you want the results sorted in the resulting Cursor
    	String sortOrder =
    	    Kanji._ID + " DESC";

    	Cursor c = db.query(
    	    Kanji.TABLE_NAME,                     // The table to query
    	    projection,                               // The columns to return
    	    //selection,                                // The columns for the WHERE clause
    	    //selectionArgs,                            // The values for the WHERE clause
    	    null,
    	    null,
    	    null,                                     // don't group the rows
    	    null,                                     // don't filter by row groups
    	    sortOrder                                 // The sort order
    	    );
    	
    	//Read kanji data from table, record by record, column by column, into Items to be put into XMLReader.kanji list
    	//variables for columns/item attributes
    	long id;
    	int int_id;
    	String level;
    	int int_level;
    	String character;
    	String meaning;
    	ArrayList<String> meanings;
    	String kana;
    	//Item to hold data; be put into kanji list
    	Item i;

    	//Read first record
    	c.moveToFirst();
    	id = c.getLong(c.getColumnIndexOrThrow(Kanji._ID));
    	int_id = (int) id;
    	level = c.getString(c.getColumnIndexOrThrow(Kanji.COLUMN_NAME_LEVEL));
    	int_level = Integer.parseInt(level);
    	character = c.getString(c.getColumnIndexOrThrow(Kanji.COLUMN_NAME_CHARACTER));
    	meaning = c.getString(c.getColumnIndexOrThrow(Kanji.COLUMN_NAME_MEANING));
    	meanings = reader.parseMeaning(meaning);
    	kana = c.getString(c.getColumnIndexOrThrow(Kanji.COLUMN_NAME_KANA));
    	//Form item and add to list
    	i = new Item(int_id, int_level, character, meanings, kana);
    	kanji.add(i);
    	//Read in the rest of the records
    	while(c.moveToNext()){
    		id = c.getLong(c.getColumnIndexOrThrow(Kanji._ID));
        	int_id = (int) id;
        	level = c.getString(c.getColumnIndexOrThrow(Kanji.COLUMN_NAME_LEVEL));
        	int_level = Integer.parseInt(level);
        	character = c.getString(c.getColumnIndexOrThrow(Kanji.COLUMN_NAME_CHARACTER));
        	meaning = c.getString(c.getColumnIndexOrThrow(Kanji.COLUMN_NAME_MEANING));
        	meanings = reader.parseMeaning(meaning);
        	kana = c.getString(c.getColumnIndexOrThrow(Kanji.COLUMN_NAME_KANA));
        	//Form item and add to list
        	i = new Item(int_id, int_level, character, meanings, kana);
        	kanji.add(i);
    	}
    	reader.setKanji(kanji);
    }
    
    public static Date getLastUpdate(){
    	return last_update;
    }
}
