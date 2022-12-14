package com.example.ik.Active.Novel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ik.Models.Novel;
import com.example.ik.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NovelTakerActivity extends AppCompatActivity {
    EditText editText_title_novel, editText_notes_novel;
    ImageView imageView_save_novel;
    Novel novel;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_taker);

        editText_title_novel = findViewById(R.id.editText_title_novel);
        editText_notes_novel = findViewById(R.id.editText_notes_novel);

        imageView_save_novel = findViewById(R.id.imageView_save_novel);

        novel = new Novel();
        try {
            novel = (Novel) getIntent().getSerializableExtra("old_note");
            editText_title_novel.setText(novel.getTitle_novel());
            editText_notes_novel.setText(novel.getNotes_novel());
            isOldNote = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        imageView_save_novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title_novel.getText().toString();
                String description = editText_notes_novel.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(NovelTakerActivity.this, "Добавте текст", Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                Date date = new Date();

                if (!isOldNote) {
                    novel = new Novel();
                }
                novel.setTitle_novel(title);
                novel.setNotes_novel(description);
                novel.setDate_novel(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("novel", novel);
                setResult(Activity.RESULT_OK, intent);
                finish();


            }
        });
    }
}
