package edu.cvtc.dschreiber4.dieroller3000;

import android.os.Parcel;
import android.os.Parcelable;

public class CharacterInfo implements Parcelable {

    //Attributes
    private String mName;
    private String mDescription;
    private int mId;

    //Overload Constructors
    public CharacterInfo(String name, String description) {
        mName = name;
        mDescription = description;
    }

    public CharacterInfo(int id, String name, String description) {
        mId = id;
        mName = name;
        mDescription = description;
    }

    //Getters and Setters
    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    //Get compare key method to concatenate title and description
    private String getCompareKey() {
        return mName + "|" + mDescription;
    }

    //Equals method to stop duplicate characters from being added
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterInfo that = (CharacterInfo) o;
        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode(){
        return getCompareKey().hashCode();
    }

    @Override
    public String toString(){
        return getCompareKey();
    }

    protected CharacterInfo(Parcel parcel) {
        mName = parcel.readString();
        mDescription = parcel.readString();
    }

    //Write the name and description to the parcel package
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mName);
        parcel.writeString(mDescription);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    public static final Creator<CharacterInfo> CREATOR = new Creator<CharacterInfo>() {
        @Override
        public CharacterInfo createFromParcel(Parcel parcel) {
            return new CharacterInfo(parcel);
        }

        @Override
        public CharacterInfo[] newArray(int size) {
            return new CharacterInfo[size];
        }
    };
}

