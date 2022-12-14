package com.example.ik.Active.Detective;

import androidx.cardview.widget.CardView;

import com.example.ik.Models.Detective;

public interface DetectiveClickListener {
    void onClick(Detective detective);

    void onLongClick(Detective detective, CardView cardView);
}
