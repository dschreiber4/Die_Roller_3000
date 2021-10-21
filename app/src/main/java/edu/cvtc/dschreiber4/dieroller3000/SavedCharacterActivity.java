package edu.cvtc.dschreiber4.dieroller3000;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import edu.cvtc.dschreiber4.dieroller3000.DieRoller3000DatabaseContract.CharacterInfoEntry;

public class SavedCharacterActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    //Constants
    public static final String CHARACTER_ID =
            "edu.cvtc.dschreiber4.dieroller3000.CHARACTER_ID";
    public static final String ORIGINAL_CHARACTER_NAME =
            "edu.cvtc.dschreiber4.dieroller3000.ORIGINAL_CHARACTER_NAME";
    public static final String ORIGINAL_CHARACTER_DESCRIPTION =
            "edu.cvtc.dschreiber4.dieroller3000.ORIGINAL_CHARACTER_DESCRIPTION";
    public static final int ID_NOT_SET = -1;
    public static final int LOADER_CHARACTER = 0;

    //Initialize the new CharacterInfo to empty
    private CharacterInfo mCharacter = new CharacterInfo(0, "", "");

    //Attributes
    private boolean mIsNewCharacter;
    private boolean mIsCancelling;
    private int mCharacterId;
    private int mCharacterNamePosition;
    private int mCharacterDescriptionPosition;
    private String mOriginalCharacterName;
    private String mOriginalCharacterDescription;

    //Objects
    private TextView mTextCharacterName;
    private TextView mTextCharacterDescription;
    private DieRoller3000OpenHelper mDbOpenHelper;
    private Cursor mCharacterCursor;


    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_character);


        //DBOpenHelper instance
        mDbOpenHelper = new DieRoller3000OpenHelper(this);

        readDisplayStateValues();

        //Check the state for null and save the value or restore the original value
        if (savedInstanceState == null) {
            saveOriginalCharacterValues();
        }else {
            restoreOriginalCharacterValues(savedInstanceState);
        }

        //Object references
        mTextCharacterName = findViewById(R.id.text_character_name);
        mTextCharacterDescription = findViewById(R.id.text_character_description);

        // If it is not a new character, load the course into the layout
        if (!mIsNewCharacter) {
            LoaderManager.getInstance(this).initLoader(LOADER_CHARACTER, null, this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Action bar items
        int id = item.getItemId();
        //No action if id exists
        if (id == R.id.action_cancel) {
            mIsCancelling = true;
            finish();
        }return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Did the user cancel the process?
        if (mIsCancelling) {
            // Is this a new character?
            if (mIsNewCharacter) {
                // Delete the new character.
                deleteCharacterFromDatabase();
            } else {
                // Put the original values on the screen.
                storePreviousCharacterValues();
            }
        } else {
            // Save the data when leaving the activity.
            saveCharacter();
        }
    }

    private void deleteCharacterFromDatabase() {
        //Create selections
        final String selection = DieRoller3000DatabaseContract.CharacterInfoEntry._ID + " = ? ";
        final String[] selectionArgs = {Integer.toString(mCharacterId)};

        AsyncTaskLoader<String> task = new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override public String loadInBackground() {
                // Get connection to the database. Use the writable
                // method since we are changing the data.
                SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
                // Call the delete method
                db.delete(CharacterInfoEntry.TABLE_NAME, selection, selectionArgs);
                return null;
            }
        };    task.loadInBackground();
    }

    private void saveCharacter() {
        //Get values from the layout
        String characterName = mTextCharacterName.getText().toString();
        String characterDescription = mTextCharacterDescription.getText().toString();

        //Call the method to write to the DB
        saveCharacterToDatabase(characterName, characterDescription);
    }

    private void saveCharacterToDatabase(String characterName, String characterDescription) {
        //Create selections
        final String selection = CharacterInfoEntry._ID + " = ? ";
        final String[] selectionArgs = {Integer.toString(mCharacterId)};

        //Use a ContentValues object to put info into
        ContentValues values = new ContentValues();
        values.put(CharacterInfoEntry.COLUMN_CHARACTER_NAME, characterName);
        values.put(CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION, characterDescription);

        AsyncTaskLoader<String> task = new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override public String loadInBackground() {
                // Get connection to the database. Use the writable
                // method since we are changing the data.
                SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
                // Call the delete method
                db.update(CharacterInfoEntry.TABLE_NAME, values, selection, selectionArgs);
                return null;
            }
        };    task.loadInBackground();
    }

    private void displayCharacter() {
        //Get the values based on column positions
        String characterName = mCharacterCursor.getString(mCharacterNamePosition);
        String characterDescription = mCharacterCursor.getString(mCharacterDescriptionPosition);

        //Use the info to populate the layout
        mTextCharacterName.setText(characterName);
        mTextCharacterDescription.setText(characterDescription);
    }

    private void restoreOriginalCharacterValues(Bundle savedInstanceState) {
        //Get original values for existing characters
        mOriginalCharacterName = savedInstanceState.getString(ORIGINAL_CHARACTER_NAME);
        mOriginalCharacterDescription = savedInstanceState.getString(ORIGINAL_CHARACTER_DESCRIPTION);
    }

    private void saveOriginalCharacterValues() {
        //Save values if character is new
        if (!mIsNewCharacter) {
            mOriginalCharacterName = mCharacter.getName();
            mOriginalCharacterDescription = mCharacter.getDescription();
        }
    }

    private void readDisplayStateValues() {
        //Pass the intent into the activity
        Intent intent = getIntent();

        //Get the character id passed into the intent
        mCharacterId = intent.getIntExtra(CHARACTER_ID, ID_NOT_SET);

        //Create a new id if none set
        mIsNewCharacter = mCharacterId == ID_NOT_SET;
        if(mIsNewCharacter) {
            createNewCharacter();
        }
    }

    private void createNewCharacter() {
        //Create the ContentValues object to hold the DB fields
        ContentValues values = new ContentValues();

        //set the values for a new character to empty
        values.put(CharacterInfoEntry.COLUMN_CHARACTER_NAME, "");
        values.put(CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION, "");

        //Connect to the DB
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();

        //Insert the new row into the DB and assign the new id.
        mCharacterId = (int)db.insert(CharacterInfoEntry.TABLE_NAME, null, values);
    }


    private void storePreviousCharacterValues() {
        mCharacter.setName(mOriginalCharacterName);
        mCharacter.setDescription(mOriginalCharacterDescription);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Create a local cursor loader
        CursorLoader loader = null;

        //Check if the id is in loader
        if (id == LOADER_CHARACTER) {
            loader = createLoaderCharacters();
        }
        return loader;
    }

    private CursorLoader createLoaderCharacters() {
        return new CursorLoader(this) {
            @Override
            public Cursor loadInBackground() {
                //Open DB connection
                SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

                //Set the new id
                String selection = CharacterInfoEntry._ID + " = ? ";
                String[] selectionArgs = {Integer.toString(mCharacterId)};

                //Create column lists you are pulling from the DB
                String[] characterColumns = {
                        CharacterInfoEntry.COLUMN_CHARACTER_NAME,
                        CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION
                };
                //Fill the cursor
                return db.query(CharacterInfoEntry.TABLE_NAME, characterColumns,
                        selection, selectionArgs,null, null, null);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // Check to see if this is your cursor for your loader
        if (loader.getId() == LOADER_CHARACTER) {
            loadFinishedCourses(data);
        }
    }

    private void loadFinishedCourses(Cursor data) {
        // Populate cursor with the data
        mCharacterCursor = data;
        // Get the positions of the fields in the cursor for the layout
        mCharacterNamePosition = mCharacterCursor.getColumnIndex(CharacterInfoEntry.COLUMN_CHARACTER_NAME);
        mCharacterDescriptionPosition = mCharacterCursor.getColumnIndex(CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION);

        // Make sure that you have moved to the correct record.
        mCharacterCursor.moveToNext();

        // Display the course.
        displayCharacter();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Check to see if this is your cursor for your loader
        if (loader.getId() == LOADER_CHARACTER) {
            // If the cursor is not null, close it
            if (mCharacterCursor != null) {
                mCharacterCursor.close();
            }
        }
    }
/*
    @Override
    public void populateSpinnerNumDie() {

    }

 */
}

