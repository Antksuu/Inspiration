package com.example.ik.Active.Article;

import androidx.cardview.widget.CardView;

import com.example.ik.Models.Article;


public interface ArticleClickListener {

    void onClick(Article article);

    void onLongClick(Article article, CardView cardView);
}
