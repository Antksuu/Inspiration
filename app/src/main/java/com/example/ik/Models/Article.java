package com.example.ik.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "article")
public class Article implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "title_article")
    public
    String title_article = "";

    @ColumnInfo(name = "notes_article")
    public
    String notes_article = "";

    @ColumnInfo(name = "date_article")
    public
    String date_article = "";

    @ColumnInfo(name = "pinned_article")
    public
    boolean pinned_article = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title_article;
    }

    public void setTitle(String title_article) {
        this.title_article = title_article;
    }

    public String getNotes() {
        return notes_article;
    }

    public void setNotes(String notes_article) {
        this.notes_article = notes_article;
    }

    public String getDate() {
        return date_article;
    }

    public void setDate(String date_article) {
        this.date_article = date_article;
    }

    public boolean isPinned() {
        return pinned_article;
    }

    public void setPinned(boolean pinned_article) {
        this.pinned_article = pinned_article;
    }
}
