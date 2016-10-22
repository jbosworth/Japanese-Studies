package com.gmail.jbosworth2.japanese_studies.sqlite;

import java.util.Date;

import com.gmail.jbosworth2.japanese_studies.sqlite.KanjiContract.Kanji;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KanjiDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    //Last time database was updated; to be compared to last modified date on the asset kanji.xml
    private Date date;
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
    	date = new Date();
    	
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
}
