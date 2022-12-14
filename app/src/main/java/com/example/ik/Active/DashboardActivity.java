package com.example.ik.Active;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ik.Active.Article.ArticleActivity;
import com.example.ik.Active.Detective.DetectiveActivity;
import com.example.ik.Active.Novel.NovelActivity;
import com.example.ik.Active.Story.StoryActivity;
import com.example.ik.R;


public class DashboardActivity extends AppCompatActivity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ArticleActivity.class);
                Toast.makeText(DashboardActivity.this, "Выбран жанр: статья", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, StoryActivity.class);
                Toast.makeText(DashboardActivity.this, "Выбран жанр: сказка", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, NovelActivity.class);
                Toast.makeText(DashboardActivity.this, "Выбран жанр: роман", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, DetectiveActivity.class);
                Toast.makeText(DashboardActivity.this, "Выбран жанр: детектив", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}
