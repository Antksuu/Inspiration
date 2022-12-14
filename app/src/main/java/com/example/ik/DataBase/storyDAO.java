package com.example.ik.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ik.Models.Article;
import com.example.ik.Models.Story;

import java.util.List;

@Dao
public interface storyDAO {
    @Insert(onConflict = REPLACE)
    void insert(Story story);

    @Query("SELECT * FROM story ORDER BY id DESC")
    List<Story> getAll();

    @Query("UPDATE story SET title_story=:title_story, notes_story=:notes_story WHERE id=:id")
    void update(int id, String title_story, String notes_story);

    @Delete
    void delete(Story story);

    @Query("UPDATE story SET pinned_story=:pinned_story WHERE id=:id")
    void pin(int id, boolean pinned_story);
}


