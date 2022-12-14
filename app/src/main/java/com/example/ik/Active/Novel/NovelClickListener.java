package com.example.ik.Active.Novel;

import androidx.cardview.widget.CardView;

import com.example.ik.Models.Novel;
import com.example.ik.Models.Story;

public interface NovelClickListener {
    void onClick(Novel novel);

    void onLongClick(Novel novel, CardView cardView);
}
