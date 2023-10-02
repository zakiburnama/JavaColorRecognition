package com.example.colorrecognitionjavaalpha;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Materi implements Parcelable {
    private String tittle;
    private String description;
    private Integer photo;
    private Boolean isReaded;

    public Materi() {

    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public Boolean getReaded() {
        return isReaded;
    }

    public void setReaded(Boolean readed) {
        isReaded = readed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(tittle);
        dest.writeString(description);
        if (photo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(photo);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(isReaded);
        }
    }

    protected Materi(Parcel in) {
        tittle = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            photo = null;
        } else {
            photo = in.readInt();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isReaded = in.readBoolean();
        }
    }

    public static final Creator<Materi> CREATOR = new Creator<Materi>() {
        @Override
        public Materi createFromParcel(Parcel in) {
            return new Materi(in);
        }

        @Override
        public Materi[] newArray(int size) {
            return new Materi[size];
        }
    };
}
