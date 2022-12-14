package com.example.ik.Active.Task;

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

import com.example.ik.Active.DashboardActivity;
import com.example.ik.Active.Novel.NovelActivity;
import com.example.ik.Active.Story.StoryActivity;
import com.example.ik.Active.Story.StoryClickListener;
import com.example.ik.Active.Story.StoryTakerActivity;
import com.example.ik.Adapter.NovelListAdapter;
import com.example.ik.Adapter.StoryListAdapter;
import com.example.ik.Adapter.TaskListAdapter;
import com.example.ik.DataBase.RoomDB;
import com.example.ik.Models.Story;
import com.example.ik.Models.Task;
import com.example.ik.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    FloatingActionButton fab_add, fab_save_task;
    RecyclerView recycle_home_task;
    TaskListAdapter taskListAdapter;
    List<Task> tasks = new ArrayList<>();
    RoomDB database;
    Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_add = findViewById(R.id.fab_add);
        recycle_home_task = findViewById(R.id.recycle_home_task);
        fab_save_task = findViewById(R.id.fab_save_task);

        database = RoomDB.getInstance(this);
        tasks = database.taskDAO().getAll();
        updateRecycler(tasks);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        //Обработка нажатия на кнопку
        fab_save_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });
    }

    //Метод для создание, добавление текста
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Task new_task = (Task) data.getSerializableExtra("task");
                database.taskDAO().insert(new_task);
                tasks.clear();
                tasks.addAll(database.taskDAO().getAll());
                taskListAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 102) {
            Task new_task = (Task) data.getSerializableExtra("task");
            database.taskDAO().update(new_task.getID(), new_task.getNotes_task());
            tasks.clear();
            tasks.addAll(database.taskDAO().getAll());
            taskListAdapter.notifyDataSetChanged();
        }
    }

    //Метод обновления текста
    private void updateRecycler(List<Task> task) {
        recycle_home_task.setHasFixedSize(true);
        recycle_home_task.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        taskListAdapter = new TaskListAdapter(MainActivity.this, task, taskClickListener);
        recycle_home_task.setAdapter(taskListAdapter);
    }

    //Метод для обновления текста
    private final TaskClickListener taskClickListener = new TaskClickListener() {

        @Override
        public void onClick(Task task) {
            Intent intent = new Intent(MainActivity.this, TaskTakerActivity.class);
            intent.putExtra("old_note", task);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Task task, CardView cardView) {
            selectedTask = new Task();
            selectedTask = task;
            showPopUp(cardView);
        }
    };


    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu_task);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.delete_task:
                database.taskDAO().delete(selectedTask);
                tasks.remove(selectedTask);
                taskListAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Задача удалена", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }

    }

}