package com.example.ik.Active.Story;

import androidx.cardview.widget.CardView;
import com.example.ik.Models.Story;

public interface StoryClickListener {

    void onClick(Story story);

    void onLongClick(Story story, CardView cardView);
}
