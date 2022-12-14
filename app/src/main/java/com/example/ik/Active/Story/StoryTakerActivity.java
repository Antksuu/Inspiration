package com.example.ik.Active.Story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ik.Models.Story;
import com.example.ik.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StoryTakerActivity extends AppCompatActivity {

    EditText editText_title_story, editText_notes_story;
    ImageView imageView_save_story;
    Story story;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_taker);

        editText_title_story = findViewById(R.id.editText_title_story);
        editText_notes_story = findViewById(R.id.editText_notes_story);

        imageView_save_story = findViewById(R.id.imageView_save_story);

        story = new Story();
        try {
            story = (Story) getIntent().getSerializableExtra("old_note");
            editText_title_story.setText(story.getTitle_story());
            editText_notes_story.setText(story.getNotes_story());
            isOldNote = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        imageView_save_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title_story.getText().toString();
                String description = editText_notes_story.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(StoryTakerActivity.this, "Добавте текст", Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                Date date = new Date();

                if (!isOldNote) {
                    story = new Story();
                }
                story.setTitle_story(title);
                story.setNotes_story(description);
                story.setDate_story(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("story", story);
                setResult(Activity.RESULT_OK, intent);
                finish();


            }
        });
    }
}
