package com.example.ik.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ik.Models.Article;
import com.example.ik.Models.Detective;
import com.example.ik.Models.Novel;
import com.example.ik.Models.Story;
import com.example.ik.Models.Task;


@Database(entities = {Article.class, Story.class, Novel.class, Detective.class, Task.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static String DATABASE_NAME = "NoteApp";

    public synchronized static RoomDB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }


    public abstract articleDAO articleDAO();

    public abstract storyDAO storyDAO();

    public abstract novelDAO novelDAO();

    public abstract detectiveDAO detectiveDAO();

    public abstract taskDAO taskDAO();
}
