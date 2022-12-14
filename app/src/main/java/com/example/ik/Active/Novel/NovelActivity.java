package com.example.ik.Active.Novel;

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
import com.example.ik.Adapter.NovelListAdapter;
import com.example.ik.DataBase.RoomDB;
import com.example.ik.Models.Novel;
import com.example.ik.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NovelActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView_novel;
    FloatingActionButton fab_add_novel,fab_add_home_novel;
    NovelListAdapter novelListAdapter;
    List<Novel> novels = new ArrayList<>();
    RoomDB database;
    SearchView searchView_home_novel;
    Novel selectedNovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);

        recyclerView_novel = findViewById(R.id.recycle_home_novel);
        fab_add_novel = findViewById(R.id.fab_add_novel);
        fab_add_home_novel = findViewById(R.id.fab_add_home_novel);
        searchView_home_novel = findViewById(R.id.searchView_home_novel);

        database = RoomDB.getInstance(this);
        novels = database.novelDAO().getAll();

        updateRecycler(novels);

        //Обработка нажатия на кнопку
        fab_add_novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NovelActivity.this, NovelTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        //Обработка нажатия на кнопку
        fab_add_home_novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NovelActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Метод поиска
        searchView_home_novel.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        List<Novel> filteredList = new ArrayList<>();
        //Создаем поиск по заголовку заметки
        for (Novel singleNote : novels) {
            if (singleNote.getTitle_novel().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(singleNote);
            }
        }
        novelListAdapter.filterList(filteredList);
    }

    //Метод для создание, добавление текста
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Novel new_novel = (Novel) data.getSerializableExtra("novel");
                database.novelDAO().insert(new_novel);
                novels.clear();
                novels.addAll(database.novelDAO().getAll());
                novelListAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 102) {
            Novel new_novel = (Novel) data.getSerializableExtra("novel");
            database.novelDAO().update(new_novel.getID(), new_novel.getTitle_novel(), new_novel.getNotes_novel());
            novels.clear();
            novels.addAll(database.novelDAO().getAll());
            novelListAdapter.notifyDataSetChanged();
        }
    }

    //Метод обновления текста
    private void updateRecycler(List<Novel> story) {
        recyclerView_novel.setHasFixedSize(true);
        recyclerView_novel.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        novelListAdapter = new NovelListAdapter(NovelActivity.this, story, novelClickListener);
        recyclerView_novel.setAdapter(novelListAdapter);
    }

    //Метод для обновления текста
    private final NovelClickListener novelClickListener = new NovelClickListener() {

        @Override
        public void onClick(Novel novel) {
            Intent intent = new Intent(NovelActivity.this, NovelTakerActivity.class);
            intent.putExtra("old_note", novel);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Novel novel, CardView cardView) {
            selectedNovel = new Novel();
            selectedNovel = novel;
            showPopUp(cardView);
        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_novel);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.pin_novel:
                if (selectedNovel.isPinned_novel()) {
                    database.novelDAO().pin(selectedNovel.getID(), false);
                    Toast.makeText(NovelActivity.this, "Метка удалена", Toast.LENGTH_SHORT).show();
                } else {
                    database.novelDAO().pin(selectedNovel.getID(), true);
                    Toast.makeText(NovelActivity.this, "Метка создана", Toast.LENGTH_SHORT).show();
                }
                novels.clear();
                novels.addAll(database.novelDAO().getAll());
                novelListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete_novel:
                database.novelDAO().delete(selectedNovel);
                novels.remove(selectedNovel);
                novelListAdapter.notifyDataSetChanged();
                Toast.makeText(NovelActivity.this, "Роман удален", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }

    }
}