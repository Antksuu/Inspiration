package com.example.ik.Active.Article;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ik.Models.Article;
import com.example.ik.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleTakerActivity extends AppCompatActivity {
    EditText editText_title, editText_notes;
    ImageView imageView_save;
    Article article;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_taker);

        editText_title = findViewById(R.id.editText_title);
        editText_notes = findViewById(R.id.editText_notes);

        imageView_save = findViewById(R.id.imageView_save);

        article = new Article();
        try {
            article = (Article) getIntent().getSerializableExtra("old_note");
            editText_title.setText(article.getTitle());
            editText_notes.setText(article.getNotes());
            isOldNote = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(ArticleTakerActivity.this, "Добавте текст", Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                Date date = new Date();

                if (!isOldNote) {
                    article = new Article();
                }
                article.setTitle(title);
                article.setNotes(description);
                article.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("notes", article);
                setResult(Activity.RESULT_OK, intent);
                finish();


            }
        });
    }
}
