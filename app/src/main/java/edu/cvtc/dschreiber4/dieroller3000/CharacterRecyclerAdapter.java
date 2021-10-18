package edu.cvtc.dschreiber4.dieroller3000;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cvtc.dschreiber4.dieroller3000.DieRoller3000DatabaseContract.CharacterInfoEntry;

public class CharacterRecyclerAdapter extends RecyclerView.Adapter<CharacterRecyclerAdapter.ViewHolder> {

    // Member variables
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private Cursor mCursor;
    private int mCharacterNamePosition;
    private int mCharacterDescriptionPosition;
    private int mIdPosition;

    //Constructor
    public CharacterRecyclerAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mLayoutInflater = LayoutInflater.from(context);

        //Retrieve the positions of columns we are interested in
        populateColumnPositions();
    }

    private void populateColumnPositions() {

        if (mCursor != null) {
            //Get column indexes from the cursor
            mCharacterNamePosition = mCursor.getColumnIndex(
                    CharacterInfoEntry.COLUMN_CHARACTER_NAME);
            mCharacterDescriptionPosition = mCursor.getColumnIndex(
                    CharacterInfoEntry.COLUMN_CHARACTER_DESCRIPTION);
            mIdPosition = mCursor.getColumnIndex(
                    CharacterInfoEntry._ID);
        }
    }

    public void changeCursor(Cursor cursor) {
        // close cursor
        if (mCursor != null) {
            mCursor.close();
        }

        //Create new cursor
        mCursor = cursor;

        //Get Positions of columns
        populateColumnPositions();

        //Tell Activity there has been a change
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Move the cursor to the correct position
        mCursor.moveToPosition(position);

        //Get the actual values
        String  characterName = mCursor.getString(mCharacterNamePosition);
        String  characterDescription = mCursor.getString(mCharacterDescriptionPosition);
        int id = mCursor.getInt(mIdPosition);

        //Pass in the information to holder
        holder.mCharacterName.setText(characterName);
        holder.mCharacterDescription.setText(characterDescription);
        holder.mId = id;
    }

    @Override
    public int getItemCount() {
        //Return 0 if null or return the record count
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Attributes for inner class
        public final TextView mCharacterName;
        public final TextView mCharacterDescription;
        public int mId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCharacterName = (TextView)itemView.findViewById(R.id.text_character_name);
            mCharacterDescription = (TextView)itemView.findViewById(R.id.text_character_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CharacterActivity.class);
                    intent.putExtra(CharacterActivity.CHARACTER_ID, mId);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
