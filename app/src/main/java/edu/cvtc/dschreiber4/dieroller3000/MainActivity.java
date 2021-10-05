package edu.cvtc.dschreiber4.dieroller3000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void DicePage(View view) {
        //Create intent and navigate to new activity
        Intent intent = new Intent(getApplicationContext(), DicePage.class);
        startActivity(intent);
    }

    public void Characters(View view) {
        //Create intent and navigate to new activity
        Intent intent = new Intent(getApplicationContext(), Characters.class);
        startActivity(intent);
    }
}