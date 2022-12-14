package com.example.ik.Active.Detective;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ik.Active.Novel.NovelTakerActivity;
import com.example.ik.Models.Detective;
import com.example.ik.Models.Novel;
import com.example.ik.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetectiveTakerActivity extends AppCompatActivity {
    EditText editText_title_detective, editText_notes_detective;
    ImageView imageView_save_detective;
    Detective detective;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detective_taker);

        editText_title_detective = findViewById(R.id.editText_title_detective);
        editText_notes_detective = findViewById(R.id.editText_notes_detective);

        imageView_save_detective = findViewById(R.id.imageView_save_detective);

        detective = new Detective();
        try {
            detective = (Detective) getIntent().getSerializableExtra("old_note");
            editText_title_detective.setText(detective.getTitle_detective());
            editText_notes_detective.setText(detective.getNotes_detective());
            isOldNote = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        imageView_save_detective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title_detective.getText().toString();
                String description = editText_notes_detective.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(DetectiveTakerActivity.this, "Добавте текст", Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                Date date = new Date();

                if (!isOldNote) {
                    detective = new Detective();
                }
                detective.setTitle_detective(title);
                detective.setNotes_detective(description);
                detective.setDate_detective(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("detective", detective);
                setResult(Activity.RESULT_OK, intent);
                finish();


            }
        });
    }
}
