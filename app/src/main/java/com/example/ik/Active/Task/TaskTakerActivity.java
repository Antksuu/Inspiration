package com.example.ik.Active.Task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ik.Active.Story.StoryTakerActivity;
import com.example.ik.Models.Story;
import com.example.ik.Models.Task;
import com.example.ik.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskTakerActivity extends AppCompatActivity {
    EditText editText_notes_task;
    ImageView imageView_save_task;
    Task task;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_taker);

        editText_notes_task = findViewById(R.id.editText_notes_task);

        imageView_save_task = findViewById(R.id.imageView_save_task);

        task = new Task();
        try {
            task = (Task) getIntent().getSerializableExtra("old_note");
            editText_notes_task.setText(task.getNotes_task());
            isOldNote = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        imageView_save_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = editText_notes_task.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(TaskTakerActivity.this, "Добавте текст", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!isOldNote) {
                    task = new Task();
                }

                task.setNotes_task(description);


                Intent intent = new Intent();
                intent.putExtra("task", task);
                setResult(Activity.RESULT_OK, intent);
                finish();


            }
        });
    }
}
