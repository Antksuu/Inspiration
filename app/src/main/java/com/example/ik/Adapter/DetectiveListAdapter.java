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

import com.example.ik.Active.Detective.DetectiveClickListener;
import com.example.ik.Active.Novel.NovelClickListener;
import com.example.ik.Models.Detective;
import com.example.ik.Models.Novel;
import com.example.ik.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DetectiveListAdapter extends RecyclerView.Adapter<DetectiveViewHolder> {
    Context context;
    List<Detective> list;

    public DetectiveListAdapter(Context context, List<Detective> list, DetectiveClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    DetectiveClickListener listener;


    @NonNull
    @Override
    public DetectiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetectiveViewHolder(LayoutInflater.from(context).inflate(R.layout.detective_list, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull DetectiveViewHolder holder, int position) {

        holder.textView_title_detective.setText(list.get(position).getTitle_detective());
        holder.textView_title_detective.setSelected(true);

        holder.textView_notes_detective.setText(list.get(position).getNotes_detective());


        holder.textView_date_detective.setText(list.get(position).getDate_detective());
        holder.textView_date_detective.setSelected(true);

        if (list.get(position).isPinned_detective()) {
            holder.imageView_pin_detective.setImageResource(R.drawable.pin_icon);
        } else {
            holder.imageView_pin_detective.setImageResource(0);
        }

        int color_code = getRandomColor();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.notes_container_detective.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));
        }

        holder.notes_container_detective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container_detective.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container_detective);
                return true;
            }
        });
    }


    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color10);
        colorCode.add(R.color.color11);
        colorCode.add(R.color.color12);


        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Detective> filteredList) {
        list = filteredList;
        notifyDataSetChanged();

    }
}

class DetectiveViewHolder extends RecyclerView.ViewHolder {

    CardView notes_container_detective;
    TextView textView_title_detective, textView_notes_detective, textView_date_detective;
    ImageView imageView_pin_detective;


    public DetectiveViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_container_detective = itemView.findViewById(R.id.notes_container_detective);
        textView_title_detective = itemView.findViewById(R.id.textView_title_detective);
        textView_notes_detective = itemView.findViewById(R.id.textView_notes_detective);
        textView_date_detective = itemView.findViewById(R.id.textView_date_detective);
        imageView_pin_detective = itemView.findViewById(R.id.imageView_pin_detective);

    }
}