package edu.cvtc.dschreiber4.dieroller3000;

import android.provider.BaseColumns;

public class DieRoller3000DatabaseContract {

    //Private Constructor
    private DieRoller3000DatabaseContract() {
    }

    public static final class CharacterInfoEntry implements BaseColumns {
        //Constants for tables and fields
        public static final  String TABLE_NAME = "character_info";
        public static final  String COLUMN_CHARACTER_NAME = "text_character_name";
        public static final  String COLUMN_CHARACTER_DESCRIPTION = "text_character_description";

        //Constants for index names and values
        public static final String INDEX1 = TABLE_NAME + "_index1";
        public static final String SQL_CREATE_INDEX1 =
                "CREATE INDEX " + INDEX1 + " ON " + TABLE_NAME +
                        "(" + COLUMN_CHARACTER_NAME + ")";

        //Constant to create the table using table name, fields, and id
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_CHARACTER_NAME + " TEXT NOT NULL, " +
                        COLUMN_CHARACTER_DESCRIPTION + " TEXT)";
    }
}
