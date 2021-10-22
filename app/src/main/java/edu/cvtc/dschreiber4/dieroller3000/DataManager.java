package edu.cvtc.dschreiber4.dieroller3000;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import edu.cvtc.dschreiber4.dieroller3000.DieRoller3000DatabaseContract.CharacterInfoEntry;

public class DataManager {

    //Attributes
    private static DataManager myInstance = null;
    private List<CharacterInfo> mCharacter = new ArrayList<>();

    //Methods
    //Get instance
    public static DataManager getInstance(){
        if (myInstance == null) {
            myInstance = new DataManager();
        }
        return myInstance;
    }

    //Return the list array of characters
    public List<CharacterInfo> getCharacters() {
        return mCharacter;
    }

    //Use the cursor as an object to loop through the table and records
    private static void loadCharactersFromDatabase(Cursor cursor) {

        //Retrieve the field locations of of items in the db
        int listNamePosition = cursor.getColumnIndex(CharacterInfoEntry.COLUMN_CHARACTER_NAME);
        int listDescriptionPosition = cursor.getColumnIndex(CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION);
        int idPosition = cursor.getColumnIndex(CharacterInfoEntry._ID);

        //Create and clear a DataManager instance
        DataManager dm = getInstance();
        dm.mCharacter.clear();

        //Loop through the cursor rows and add new object to the array.
        while (cursor.moveToNext()) {
            String listName = cursor.getString(listNamePosition);
            String listDescription = cursor.getString(listDescriptionPosition);
            int id = cursor.getInt(idPosition);

            CharacterInfo list = new CharacterInfo(id, listName, listDescription);

            dm.mCharacter.add(list);
        }
        //Close cursor
        cursor.close();
    }

    static void loadFromDatabase(DieRoller3000OpenHelper dbHelper) {
        //Open the db in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Create a list array to return
        String[] characterColumns = {

                CharacterInfoEntry.COLUMN_CHARACTER_NAME,
                CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION,
                CharacterInfoEntry._ID

        };

        //Create an order field
        String characterOrderBy = CharacterInfoEntry.COLUMN_CHARACTER_NAME;

        //Populate the cursor with the query results
        final Cursor characterCursor = db.query(CharacterInfoEntry.TABLE_NAME,
                characterColumns, null, null, null, null,
                characterOrderBy);

        //Load the array list
        loadCharactersFromDatabase(characterCursor);
    }

    public int createNewCharacter() {
        //Create and empty object for use on the activity screen.
        CharacterInfo character = new CharacterInfo(null ,null);

        //Add to course object
        mCharacter.add(character);

        //return the size of the array.
        return mCharacter.size();
    }

    public void removeCharacter(int index) {
        mCharacter.remove(index);
    }
}
