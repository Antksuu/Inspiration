package com.example.ik.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "story")
public class Story implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "title_story")
    public
    String title_story = "";

    @ColumnInfo(name = "notes_story")
    public
    String notes_story = "";

    @ColumnInfo(name = "date_story")
    public
    String date_story = "";

    @ColumnInfo(name = "pinned_story")
    public
    boolean pinned_story = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle_story() {
        return title_story;
    }

    public void setTitle_story(String title_story) {
        this.title_story = title_story;
    }

    public String getNotes_story() {
        return notes_story;
    }

    public void setNotes_story(String notes_story) {
        this.notes_story = notes_story;
    }

    public String getDate_story() {
        return date_story;
    }

    public void setDate_story(String date_story) {
        this.date_story = date_story;
    }

    public boolean isPinned_story() {
        return pinned_story;
    }

    public void setPinned_story(boolean pinned_story) {
        this.pinned_story = pinned_story;
    }
}
