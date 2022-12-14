package com.example.ik.Active.Detective;

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
import com.example.ik.Adapter.DetectiveListAdapter;
import com.example.ik.DataBase.RoomDB;
import com.example.ik.Models.Detective;
import com.example.ik.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DetectiveActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView_detective;
    FloatingActionButton fab_add_detective;
    DetectiveListAdapter detectiveListAdapter;
    List<Detective> detectives = new ArrayList<>();
    RoomDB database;
    SearchView searchView_home_detective;
    Detective selectedDetective;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detective);

        recyclerView_detective = findViewById(R.id.recycle_home_detective);
        fab_add_detective = findViewById(R.id.fab_add_detective);
        searchView_home_detective = findViewById(R.id.searchView_home_detective);

        database = RoomDB.getInstance(this);
        detectives = database.detectiveDAO().getAll();

        updateRecycler(detectives);

        //Обработка нажатия на кнопку
        fab_add_detective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetectiveActivity.this, DetectiveTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        //Метод поиска
        searchView_home_detective.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        List<Detective> filteredList = new ArrayList<>();
        //Создаем поиск по заголовку заметки
        for (Detective singleNote : detectives) {
            if (singleNote.getTitle_detective().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(singleNote);
            }
        }
        detectiveListAdapter.filterList(filteredList);
    }

    //Метод для создание, добавление текста
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Detective new_detective = (Detective) data.getSerializableExtra("detective");
                database.detectiveDAO().insert(new_detective);
                detectives.clear();
                detectives.addAll(database.detectiveDAO().getAll());
                detectiveListAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 102) {
            Detective new_detective = (Detective) data.getSerializableExtra("detective");
            database.detectiveDAO().update(new_detective.getID(), new_detective.getTitle_detective(), new_detective.getNotes_detective());
            detectives.clear();
            detectives.addAll(database.detectiveDAO().getAll());
            detectiveListAdapter.notifyDataSetChanged();
        }
    }

    //Метод обновления текста
    private void updateRecycler(List<Detective> detective) {
        recyclerView_detective.setHasFixedSize(true);
        recyclerView_detective.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        detectiveListAdapter = new DetectiveListAdapter(DetectiveActivity.this, detective, detectiveClickListener);
        recyclerView_detective.setAdapter(detectiveListAdapter);
    }

    //Метод для обновления текста
    private final DetectiveClickListener detectiveClickListener = new DetectiveClickListener() {

        @Override
        public void onClick(Detective detective) {
            Intent intent = new Intent(DetectiveActivity.this, DetectiveTakerActivity.class);
            intent.putExtra("old_note", detective);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Detective detective, CardView cardView) {
            selectedDetective = new Detective();
            selectedDetective = detective;
            showPopUp(cardView);
        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_detective);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.pin_detective:
                if (selectedDetective.isPinned_detective()) {
                    database.detectiveDAO().pin(selectedDetective.getID(), false);
                    Toast.makeText(DetectiveActivity.this, "Метка удалена", Toast.LENGTH_SHORT).show();
                } else {
                    database.detectiveDAO().pin(selectedDetective.getID(), true);
                    Toast.makeText(DetectiveActivity.this, "Метка создана", Toast.LENGTH_SHORT).show();
                }
                detectives.clear();
                detectives.addAll(database.detectiveDAO().getAll());
                detectiveListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete_detective:
                database.detectiveDAO().delete(selectedDetective);
                detectives.remove(selectedDetective);
                detectiveListAdapter.notifyDataSetChanged();
                Toast.makeText(DetectiveActivity.this, "Детектив удален", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }

    }
}