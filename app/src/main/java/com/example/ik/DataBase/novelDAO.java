package com.example.ik.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ik.Models.Novel;

import java.util.List;

@Dao
public interface novelDAO {
    @Insert(onConflict = REPLACE)
    void insert(Novel novel);

    @Query("SELECT * FROM novel ORDER BY id DESC")
    List<Novel> getAll();

    @Query("UPDATE novel SET title_novel=:title_novel, notes_novel=:notes_novel WHERE id=:id")
    void update(int id, String title_novel, String notes_novel);

    @Delete
    void delete(Novel novel);

    @Query("UPDATE novel SET pinned_novel=:pinned_novel WHERE id=:id")
    void pin(int id, boolean pinned_novel);
}
