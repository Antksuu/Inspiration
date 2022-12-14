package com.example.ik.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ik.Models.Story;
import com.example.ik.Models.Task;

import java.util.List;

@Dao
public interface taskDAO {
    @Insert(onConflict = REPLACE)
    void insert(Task task);

    @Query("SELECT * FROM task ORDER BY id DESC")
    List<Task> getAll();

    @Query("UPDATE task SET notes_task=:notes_task WHERE id=:id")
    void update(int id, String notes_task);

    @Delete
    void delete(Task task);
}
