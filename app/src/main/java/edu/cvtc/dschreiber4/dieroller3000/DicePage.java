package edu.cvtc.dschreiber4.dieroller3000;

import static java.lang.Math.random;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DicePage extends AppCompatActivity {


    private int dieResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_page);


        final Button rollBtn = findViewById(R.id.roll_button);
        rollBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioGroup RG = findViewById(R.id.dice_RGroup);
                //Spinner mySpinner=(Spinner) findViewById(R.id.dice_spinner);
                //Integer numDie = Integer.valueOf(findViewById(R.id.numDie_editText).toString());
                TextView result = findViewById(R.id.roll_result_textView);


//TODO spinner value to loop

                //int numDie = Integer.parseInt(mySpinner.getSelectedItem().toString());
               // for (int i = 0; i < numDie; i++) {

                    switch (RG.getCheckedRadioButtonId()) {
                        case R.id.rb_4_sided:
                            dieResult = (int) (4 * random()) + 1;
                            result.setText(String.valueOf(dieResult));

                            break;
                        case R.id.rb_6_sided:
                            dieResult = (int) (6 * random()) + 1;
                            result.setText(String.valueOf(dieResult));

                            break;
                        case R.id.rb_8_sided:
                            dieResult = (int) (8 * random()) + 1;
                            result.setText(String.valueOf(dieResult));
                            dieResult++;
                            break;
                        case R.id.rb_10_sided:
                            dieResult = (int) (10 * random()) + 1;
                            result.setText(String.valueOf(dieResult));

                            break;
                        case R.id.rb_12_sided:
                            dieResult = (int) (12 * random()) + 1;
                            result.setText(String.valueOf(dieResult));
                            dieResult++;
                            break;
                        case R.id.rb_20_sided:
                            dieResult = (int) (20 * random()) + 1;
                            result.setText(String.valueOf(dieResult));

                            break;
                        case R.id.rb_100_sided:
                            dieResult = (int) (100 * random()) + 1;
                            result.setText(String.valueOf(dieResult));

                            break;
                        default:
                            dieResult = 0;
                            result.setText("Choose a Die");



                    }
                    dieResult++;
                }
                //result.setText(dieResult);
           // }

        });
    }
}