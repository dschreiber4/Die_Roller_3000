package edu.cvtc.dschreiber4.dieroller3000;


import android.os.Bundle;
import androidx.lifecycle.ViewModel;

public class CharacterActivityViewModel extends ViewModel {

    //Constants
    public static final String ORIGINAL_CHARACTER_NAME = "edu.cvtc.dschreiber4.dieroller3000.ORIGINAL_CHARACTER_NAME";
    public static final String ORIGINAL_CHARACTER_DESCRIPTION = "edu.cvtc.dschreiber4.dieroller3000.ORIGINAL_CHARACTER_DESCRIPTION";

    //Attributes
    public String mOriginalCharacterName;
    public String mOriginalCharacterDescription;
    public boolean mIsNewlyCreated = true;

    //Methods
    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_CHARACTER_NAME, mOriginalCharacterName);
        outState.putString(ORIGINAL_CHARACTER_DESCRIPTION, mOriginalCharacterDescription);
    }

    public void restoreState(Bundle inState) {
        mOriginalCharacterName = inState.getString(ORIGINAL_CHARACTER_NAME);
        mOriginalCharacterDescription = inState.getString(ORIGINAL_CHARACTER_DESCRIPTION);
    }

}
