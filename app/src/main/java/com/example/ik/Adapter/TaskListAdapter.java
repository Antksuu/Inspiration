package com.example.ik.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ik.Active.Story.StoryActivity;
import com.example.ik.Active.Story.StoryClickListener;
import com.example.ik.Active.Task.MainActivity;
import com.example.ik.Active.Task.TaskClickListener;
import com.example.ik.DataBase.RoomDB;
import com.example.ik.Models.Novel;
import com.example.ik.Models.Story;
import com.example.ik.Models.Task;
import com.example.ik.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    Context context;
    List<Task> list;


    public TaskListAdapter(Context context, List<Task> list, TaskClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    TaskClickListener listener;


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.task_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.note_text_task.setText(list.get(position).getNotes_task());
        holder.note_text_task.setSelected(true);


        int color_code = getRandomColor();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.notes_container_task.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));
        }

        holder.notes_container_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container_task.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container_task);
                return true;
            }
        });


    }


    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.teal_701);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

 class TaskViewHolder extends RecyclerView.ViewHolder {

    CardView notes_container_task;
    TextView note_text_task;

    public TaskViewHolder(View itemView) {
        super(itemView);

        notes_container_task = itemView.findViewById(R.id.notes_container_task);
        note_text_task = itemView.findViewById(R.id.note_text_task);


    }
}
