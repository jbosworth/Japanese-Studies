package com.gmail.jbosworth2.japanese_studies.sqlite;

import android.provider.BaseColumns;

public class WKVocabContract {
	// To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private WKVocabContract() {}

    /* Inner class that defines the table contents */
    public static class WKVocab implements BaseColumns {
        public static final String TABLE_NAME = "wkvocab";
        public static final String COLUMN_NAME_LEVEL = "level";
        public static final String COLUMN_NAME_CHARACTER = "character";
        public static final String COLUMN_NAME_MEANING = "meaning";
        public static final String COLUMN_NAME_KANA = "kana";
    }
}
