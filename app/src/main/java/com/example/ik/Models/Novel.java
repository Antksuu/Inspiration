package com.example.ik.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "novel")
public class Novel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "title_novel")
    public
    String title_novel = "";

    @ColumnInfo(name = "notes_novel")
    public
    String notes_novel = "";

    @ColumnInfo(name = "date_novel")
    public
    String date_novel = "";

    @ColumnInfo(name = "pinned_novel")
    public
    boolean pinned_novel = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle_novel() {
        return title_novel;
    }

    public void setTitle_novel(String title_novel) {
        this.title_novel = title_novel;
    }

    public String getNotes_novel() {
        return notes_novel;
    }

    public void setNotes_novel(String notes_novel) {
        this.notes_novel = notes_novel;
    }

    public String getDate_novel() {
        return date_novel;
    }

    public void setDate_novel(String date_novel) {
        this.date_novel = date_novel;
    }

    public boolean isPinned_novel() {
        return pinned_novel;
    }

    public void setPinned_novel(boolean pinned_novel) {
        this.pinned_novel = pinned_novel;
    }
}
