package com.example.ik.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ik.Models.Detective;

import java.util.List;

@Dao
public interface detectiveDAO {
    @Insert(onConflict = REPLACE)
    void insert(Detective detective);

    @Query("SELECT * FROM detective ORDER BY id DESC")
    List<Detective> getAll();

    @Query("UPDATE detective SET title_detective=:title_detective, notes_detective=:notes_detective WHERE id=:id")
    void update(int id, String title_detective, String notes_detective);

    @Delete
    void delete(Detective detective);

    @Query("UPDATE detective SET pinned_detective=:pinned_detective WHERE id=:id")
    void pin(int id, boolean pinned_detective);
}
