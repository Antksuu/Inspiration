package com.example.ik.Active.Story;

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

import com.example.ik.Active.Article.ArticleActivity;
import com.example.ik.Active.Task.MainActivity;
import com.example.ik.Adapter.NovelListAdapter;
import com.example.ik.Adapter.StoryListAdapter;
import com.example.ik.DataBase.RoomDB;
import com.example.ik.Models.Story;
import com.example.ik.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StoryActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView_story;
    FloatingActionButton fab_add_story, fab_add_home_story;
    StoryListAdapter storyListAdapter;
    List<Story> stories = new ArrayList<>();
    RoomDB database;
    SearchView searchView_home_story;
    Story selectedStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        recyclerView_story = findViewById(R.id.recycle_home_story);
        fab_add_story = findViewById(R.id.fab_add_story);
        fab_add_home_story = findViewById(R.id.fab_add_home_story);
        searchView_home_story = findViewById(R.id.searchView_home_story);

        database = RoomDB.getInstance(this);
        stories = database.storyDAO().getAll();

        updateRecycler(stories);

        //Обработка нажатия на кнопку
        fab_add_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoryActivity.this, StoryTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        fab_add_home_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Метод поиска
        searchView_home_story.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        List<Story> filteredList = new ArrayList<>();
        //Создаем поиск по заголовку заметки
        for (Story singleNote : stories) {
            if (singleNote.getTitle_story().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(singleNote);
            }
        }
        storyListAdapter.filterList(filteredList);
    }

    //Метод для создание, добавление текста
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Story new_story = (Story) data.getSerializableExtra("story");
                database.storyDAO().insert(new_story);
                stories.clear();
                stories.addAll(database.storyDAO().getAll());
                storyListAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 102) {
            Story new_story = (Story) data.getSerializableExtra("story");
            database.storyDAO().update(new_story.getID(), new_story.getTitle_story(), new_story.getNotes_story());
            stories.clear();
            stories.addAll(database.storyDAO().getAll());
            storyListAdapter.notifyDataSetChanged();
        }
    }

    //Метод обновления текста
    private void updateRecycler(List<Story> story) {
        recyclerView_story.setHasFixedSize(true);
        recyclerView_story.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        storyListAdapter = new StoryListAdapter(StoryActivity.this, story, storyClickListener);
        recyclerView_story.setAdapter(storyListAdapter);
    }

    //Метод для обновления текста
    private final StoryClickListener storyClickListener = new StoryClickListener() {

        @Override
        public void onClick(Story story) {
            Intent intent = new Intent(StoryActivity.this, StoryTakerActivity.class);
            intent.putExtra("old_note", story);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Story story, CardView cardView) {
            selectedStory = new Story();
            selectedStory = story;
            showPopUp(cardView);
        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_story);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.pin_story:
                if (selectedStory.isPinned_story()) {
                    database.storyDAO().pin(selectedStory.getID(), false);
                    Toast.makeText(StoryActivity.this, "Метка удалена", Toast.LENGTH_SHORT).show();
                } else {
                    database.storyDAO().pin(selectedStory.getID(), true);
                    Toast.makeText(StoryActivity.this, "Метка создана", Toast.LENGTH_SHORT).show();
                }
                stories.clear();
                stories.addAll(database.storyDAO().getAll());
                storyListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete_story:
                database.storyDAO().delete(selectedStory);
                stories.remove(selectedStory);
                storyListAdapter.notifyDataSetChanged();
                Toast.makeText(StoryActivity.this, "Сказка удалена", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }

    }
}