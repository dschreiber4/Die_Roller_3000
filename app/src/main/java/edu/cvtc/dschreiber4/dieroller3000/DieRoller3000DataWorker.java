package edu.cvtc.dschreiber4.dieroller3000;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import edu.cvtc.dschreiber4.dieroller3000.DieRoller3000DatabaseContract.CharacterInfoEntry;

public class DieRoller3000DataWorker {
    //Attributes
    private SQLiteDatabase mDb;


    //Constructor
    public DieRoller3000DataWorker(SQLiteDatabase db) {
        mDb = db;
    }

    //Methods
    //Take in parameters name and description
    private void insertCharacter(String name, String description) {
        ContentValues values = new ContentValues();

        values.put(CharacterInfoEntry.COLUMN_CHARACTER_NAME, name);
        values.put(CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION, description);

        long newRowId = mDb.insert(CharacterInfoEntry.TABLE_NAME, null, values);
    }
//todo
    //Populate the db with initial data
    public void insertCharacter() {
        insertCharacter("Fatal Exception", "Lvl 9 Druid.");
        insertCharacter("Dead Magik", "Lvl 6 Necromancer");


    }

}
