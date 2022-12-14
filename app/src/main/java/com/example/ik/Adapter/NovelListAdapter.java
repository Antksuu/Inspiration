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

import com.example.ik.Active.Novel.NovelClickListener;
import com.example.ik.Active.Story.StoryClickListener;
import com.example.ik.Models.Novel;
import com.example.ik.Models.Story;
import com.example.ik.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NovelListAdapter extends RecyclerView.Adapter<NovelViewHolder> {

    Context context;
    List<Novel> list;

    public NovelListAdapter(Context context, List<Novel> list, NovelClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    NovelClickListener listener;


    @NonNull
    @Override
    public NovelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NovelViewHolder(LayoutInflater.from(context).inflate(R.layout.novel_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull NovelViewHolder holder, int position) {

        holder.textView_title_novel.setText(list.get(position).getTitle_novel());
        holder.textView_title_novel.setSelected(true);

        holder.textView_notes_novel.setText(list.get(position).getNotes_novel());


        holder.textView_date_novel.setText(list.get(position).getDate_novel());
        holder.textView_date_novel.setSelected(true);

        if (list.get(position).isPinned_novel()) {
            holder.imageView_pin_novel.setImageResource(R.drawable.pin_icon);
        } else {
            holder.imageView_pin_novel.setImageResource(0);
        }

        int color_code = getRandomColor();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.notes_container_novel.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));
        }

        holder.notes_container_novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container_novel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container_novel);
                return true;
            }
        });
    }


    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color6);
        colorCode.add(R.color.color7);
        colorCode.add(R.color.color8);
        colorCode.add(R.color.color9);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Novel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();

    }
}

class NovelViewHolder extends RecyclerView.ViewHolder {

    CardView notes_container_novel;
    TextView textView_title_novel, textView_notes_novel, textView_date_novel;
    ImageView imageView_pin_novel;


    public NovelViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_container_novel = itemView.findViewById(R.id.notes_container_novel);
        textView_title_novel = itemView.findViewById(R.id.textView_title_novel);
        textView_notes_novel = itemView.findViewById(R.id.textView_notes_novel);
        textView_date_novel = itemView.findViewById(R.id.textView_date_novel);
        imageView_pin_novel = itemView.findViewById(R.id.imageView_pin_novel);

    }
}
