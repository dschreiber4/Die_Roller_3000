package edu.cvtc.dschreiber4.dieroller3000;

import static edu.cvtc.dschreiber4.dieroller3000.R.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import edu.cvtc.dschreiber4.dieroller3000.DieRoller3000DatabaseContract.CharacterInfoEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    
    //Attributes
    private RecyclerView mCharacterItems;
    private LinearLayoutManager mCharacterLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        // Set a reference to your list of items layout
        mCharacterItems = (RecyclerView) findViewById(R.id.list_items);
        mCharacterLayoutManager = new LinearLayoutManager(this);


    }

    public void DicePage(View view) {
        //Create intent and navigate to new activity
        Intent intent = new Intent(getApplicationContext(), DicePage.class);
        startActivity(intent);
    }

    public void Characters(View view) {
        //Create intent and navigate to new activity
        Intent intent = new Intent(getApplicationContext(), CharacterActivity.class);
        startActivity(intent);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}