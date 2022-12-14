package com.example.ik.Active.Article;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.ik.Active.Task.MainActivity;
import com.example.ik.Adapter.ArticleListAdapter;
import com.example.ik.DataBase.RoomDB;
import com.example.ik.Models.Article;
import com.example.ik.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    FloatingActionButton fab_add, fab_add_home;
    ArticleListAdapter articleListAdapter;
    List<Article> articles = new ArrayList<>();
    RoomDB database;
    SearchView searchView_home;
    Article selectedArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        recyclerView = findViewById(R.id.recycle_home);
        fab_add = findViewById(R.id.fab_add);
        fab_add_home = findViewById(R.id.fab_add_home);
        searchView_home = findViewById(R.id.searchView_home);

        database = RoomDB.getInstance(this);
        articles = database.articleDAO().getAll();

        updateRecycler(articles);

        //Обработка нажатия на кнопку
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleActivity.this, ArticleTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        fab_add_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Метод поиска
        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    //Осуществление поиска по заголовку
    private void filter(String newText) {
        List<Article> filteredList = new ArrayList<>();
        //Создаем поиск по заголовку заметки
        for (Article singleNote : articles) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(singleNote);
            }
        }
        articleListAdapter.filterList(filteredList);
    }

    //Метод для создание, добавление текста
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Article new_notes = (Article) data.getSerializableExtra("notes");
                database.articleDAO().insert(new_notes);
                articles.clear();
                articles.addAll(database.articleDAO().getAll());
                articleListAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 102) {
            Article new_notes = (Article) data.getSerializableExtra("notes");
            database.articleDAO().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
            articles.clear();
            articles.addAll(database.articleDAO().getAll());
            articleListAdapter.notifyDataSetChanged();
        }
    }

    //Метод обновления текста
    private void updateRecycler(List<Article> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        articleListAdapter = new ArticleListAdapter(ArticleActivity.this, notes, articleClickListener);
        recyclerView.setAdapter(articleListAdapter);
    }

    //Метод для обновления текста
    private final ArticleClickListener articleClickListener = new ArticleClickListener() {

        @Override
        public void onClick(Article notes) {
            Intent intent = new Intent(ArticleActivity.this, ArticleTakerActivity.class);
            intent.putExtra("old_note", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Article article, CardView cardView) {
            selectedArticle = new Article();
            selectedArticle = article;
            showPopUp(cardView);
        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.pin:
                if (selectedArticle.isPinned()) {
                    database.articleDAO().pin(selectedArticle.getID(), false);
                    Toast.makeText(ArticleActivity.this, "Метка удалена", Toast.LENGTH_SHORT).show();
                } else {
                    database.articleDAO().pin(selectedArticle.getID(), true);
                    Toast.makeText(ArticleActivity.this, "Метка создана", Toast.LENGTH_SHORT).show();
                }
                articles.clear();
                articles.addAll(database.articleDAO().getAll());
                articleListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.articleDAO().delete(selectedArticle);
                articles.remove(selectedArticle);
                articleListAdapter.notifyDataSetChanged();
                Toast.makeText(ArticleActivity.this, "Статья удалена", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }

    }
}