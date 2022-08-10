package com.ganipohan.mymovieapp.models.favorite;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite")
public class FavoriteModel implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    public int fId;

    @ColumnInfo(name = "f_title")
    public String title;
    @ColumnInfo(name = "f_desc")
    public String descrb;
    @ColumnInfo(name = "f_images")
    public String images;
    @ColumnInfo(name = "f_vote")
    public String vote;

    public FavoriteModel() {
    }


    protected FavoriteModel(Parcel in) {
        fId = in.readInt();
        title = in.readString();
        descrb = in.readString();
        images = in.readString();
        vote = in.readString();
    }

    public static final Creator<FavoriteModel> CREATOR = new Creator<FavoriteModel>() {
        @Override
        public FavoriteModel createFromParcel(Parcel in) {
            return new FavoriteModel(in);
        }

        @Override
        public FavoriteModel[] newArray(int size) {
            return new FavoriteModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(fId);
        parcel.writeString(title);
        parcel.writeString(descrb);
        parcel.writeString(images);
        parcel.writeString(vote);
    }
}
