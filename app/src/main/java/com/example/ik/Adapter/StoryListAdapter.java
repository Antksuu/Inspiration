package com.example.ik.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ik.Active.Story.StoryClickListener;
import com.example.ik.Models.Story;
import com.example.ik.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StoryListAdapter extends RecyclerView.Adapter<StoryViewHolder> {

    Context context;
    List<Story> list;

    public StoryListAdapter(Context context, List<Story> list, StoryClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    StoryClickListener listener;


    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryViewHolder(LayoutInflater.from(context).inflate(R.layout.story_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {

        holder.textView_title_story.setText(list.get(position).getTitle_story());
        holder.textView_title_story.setSelected(true);

        holder.textView_notes_story.setText(list.get(position).getNotes_story());


        holder.textView_date_story.setText(list.get(position).getDate_story());
        holder.textView_date_story.setSelected(true);

        if (list.get(position).isPinned_story()) {
            holder.imageView_pin_story.setImageResource(R.drawable.pin_icon);
        } else {
            holder.imageView_pin_story.setImageResource(0);
        }

        int color_code = getRandomColor();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.notes_container_story.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));
        }

        holder.notes_container_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container_story.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container_story);
                return true;
            }
        });
    }


    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Story> filteredList) {
        list = filteredList;
        notifyDataSetChanged();

    }
}

class StoryViewHolder extends RecyclerView.ViewHolder {

    CardView notes_container_story;
    TextView textView_title_story, textView_notes_story, textView_date_story;
    ImageView imageView_pin_story;


    public StoryViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_container_story = itemView.findViewById(R.id.notes_container_story);
        textView_title_story = itemView.findViewById(R.id.textView_title_story);
        textView_notes_story = itemView.findViewById(R.id.textView_notes_story);
        textView_date_story = itemView.findViewById(R.id.textView_date_story);
        imageView_pin_story = itemView.findViewById(R.id.imageView_pin_story);

    }
}
