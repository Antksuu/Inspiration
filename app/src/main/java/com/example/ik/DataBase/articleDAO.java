package com.example.ik.DataBase;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ik.Models.Article;

import java.util.List;


@Dao
public interface articleDAO {

    @Insert(onConflict = REPLACE)
    void insert(Article article);

    @Query("SELECT * FROM Article ORDER BY id DESC")
    List<Article> getAll();

    @Query("UPDATE article SET title_article=:title_article, notes_article=:notes_article WHERE ID=:id")
    void update(int id, String title_article, String notes_article);

    @Delete
    void delete(Article article);

    @Query("UPDATE article SET pinned_article=:pin_article WHERE ID=:id")
    void pin(int id, boolean pin_article);
}

