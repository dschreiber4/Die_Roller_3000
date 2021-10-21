package edu.cvtc.dschreiber4.dieroller3000;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import edu.cvtc.dschreiber4.dieroller3000.DieRoller3000DatabaseContract.CharacterInfoEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CharacterActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // Member variables
    private DieRoller3000OpenHelper mDbOpenHelper;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mCharacterLayoutManager;
    private CharacterRecyclerAdapter mCharacterRecyclerAdapter;

    //Constant
    public static final int LOADER_CHARACTER = 0;

    // Boolean to check if the 'onCreateLoader' method has run
    private boolean mIsCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_main);

        mDbOpenHelper = new DieRoller3000OpenHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(CharacterActivity.this, SavedCharacterActivity.class));
            }
        });
        initializeDisplayContent();
    }

    private void initializeDisplayContent() {

        // Retrieve the information from your database
        DataManager.loadFromDatabase(mDbOpenHelper);

        // Set a reference to your list of items layout
        mRecyclerItems = (RecyclerView) findViewById(R.id.list_items);
        mCharacterLayoutManager = new LinearLayoutManager(this);

        // Get your characters
        mCharacterRecyclerAdapter = new CharacterRecyclerAdapter(this, null);

        // Display the characters
        displayCharacter();
    }

    private void displayCharacter() {
        mRecyclerItems.setLayoutManager(mCharacterLayoutManager);
        mRecyclerItems.setAdapter(mCharacterRecyclerAdapter);
    }

    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Get the latest data from the DB
        loadCharacter();
    }

    private void loadCharacter() {
        //Open the DB in Read mode
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

        //Create a list of columns from the DB to return
        String[] characterColumns = {
                CharacterInfoEntry.COLUMN_CHARACTER_NAME,
                CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION,
                CharacterInfoEntry._ID
        };

        //Create the order by field with RecyclerAdapter
        String characterOrderBy = CharacterInfoEntry.COLUMN_CHARACTER_NAME;

        //Populate the cursor
        final Cursor characterCursor = db.query(CharacterInfoEntry.TABLE_NAME, characterColumns,
                null, null, null, null,
                characterOrderBy);

        //Associate the cursor with RecyclerAdapter
        mCharacterRecyclerAdapter.changeCursor(characterCursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        //match id with action bar icon
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Create new cursor loader
        CursorLoader loader = null;

        if (id == LOADER_CHARACTER) {
            loader = new CursorLoader(this){

                @Override
                public Cursor loadInBackground() {
                    mIsCreated = true;

                    //Open the DB in read mode
                    SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

                    // Create a list of columns you want to return.
                    String[] characterColumns = {
                            CharacterInfoEntry.COLUMN_CHARACTER_NAME,
                            CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION,
                            CharacterInfoEntry._ID};

                    // Create an order by field for sorting purposes
                    String characterOrderBy = CharacterInfoEntry.COLUMN_CHARACTER_NAME;

                    // Populate your character with the results of the query
                    return db.query(CharacterInfoEntry.TABLE_NAME, characterColumns,
                            null, null, null, null,
                            characterOrderBy);
                }
            };
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOADER_CHARACTER && mIsCreated) {
            // Associate the cursor with your RecyclerAdapter
            mCharacterRecyclerAdapter.changeCursor(data);
            mIsCreated = false;
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == LOADER_CHARACTER) {
            // Change the cursor to null
            mCharacterRecyclerAdapter.changeCursor(null);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onResume();

        //Use restart loader instead of initLoader to refresh each time instead of only the fist load
        LoaderManager.getInstance(this).restartLoader(LOADER_CHARACTER, null, this);

    }

}
