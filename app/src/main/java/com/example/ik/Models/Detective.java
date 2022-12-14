package com.example.ik.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "detective")
public class Detective implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "title_detective")
    public
    String title_detective = "";

    @ColumnInfo(name = "notes_detective")
    public
    String notes_detective = "";

    @ColumnInfo(name = "date_detective")
    public
    String date_detective = "";

    @ColumnInfo(name = "pinned_detective")
    public
    boolean pinned_detective = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle_detective() {
        return title_detective;
    }

    public void setTitle_detective(String title_detective) {
        this.title_detective = title_detective;
    }

    public String getNotes_detective() {
        return notes_detective;
    }

    public void setNotes_detective(String notes_detective) {
        this.notes_detective = notes_detective;
    }

    public String getDate_detective() {
        return date_detective;
    }

    public void setDate_detective(String date_detective) {
        this.date_detective = date_detective;
    }

    public boolean isPinned_detective() {
        return pinned_detective;
    }

    public void setPinned_detective(boolean pinned_detective) {
        this.pinned_detective = pinned_detective;
    }
}
