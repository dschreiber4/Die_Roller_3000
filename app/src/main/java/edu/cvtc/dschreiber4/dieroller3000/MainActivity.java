package edu.cvtc.dschreiber4.dieroller3000;

import static edu.cvtc.dschreiber4.dieroller3000.R.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.cvtc.dschreiber4.dieroller3000.DieRoller3000DatabaseContract.CharacterInfoEntry;

public class MainActivity extends AppCompatActivity {

    
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
}