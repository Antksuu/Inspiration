package com.example.ik.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "task")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "notes_task")
    public
    String notes_task = "";

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNotes_task() {
        return notes_task;
    }

    public void setNotes_task(String notes_task) {
        this.notes_task = notes_task;
    }
}
