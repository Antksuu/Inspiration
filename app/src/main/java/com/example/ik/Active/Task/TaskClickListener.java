package com.example.ik.Active.Task;

import androidx.cardview.widget.CardView;

import com.example.ik.Models.Story;
import com.example.ik.Models.Task;

public interface TaskClickListener {

    void onClick(Task task);

    void onLongClick(Task task, CardView cardView);
}
