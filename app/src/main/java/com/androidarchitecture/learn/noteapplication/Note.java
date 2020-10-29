package com.androidarchitecture.learn.noteapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "notes")
public class Note {

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getNote() {
        return this.mNote;
    }

    @NonNull
    public String getDescription() {
        return this.mDescription;
    }

    @NonNull
    public byte[] getImage() {
        return image;
    }


    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    @ColumnInfo(name = "note")
    private String mNote;

    @NonNull
    @ColumnInfo(name = "desc")
    private String mDescription;

    @NonNull
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Note(String id, String note, String description, byte[] image) {
        this.id = id;
        this.mNote = note;
        this.mDescription = description;
        this.image = image;
    }

}
