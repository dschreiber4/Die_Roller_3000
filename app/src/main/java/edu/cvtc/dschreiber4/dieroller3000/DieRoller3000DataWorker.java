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
        insertCharacter("Web 1 - HTML & CSS", "Introductory HTML course");
        insertCharacter("Programming Fundamentals", "Introductory Programming Course");
        insertCharacter("Database 1", "Introductory Database Course");
        insertCharacter("Object Oriented Programming", "Second Semester Programming Course using Java");
        insertCharacter(".NET Application Development", "Second Semester Programming Course using .NET");
        insertCharacter("Database 2", "Intermediate Database Course");
        insertCharacter("Android Development", "Application Development Course with Android");


    }

}
