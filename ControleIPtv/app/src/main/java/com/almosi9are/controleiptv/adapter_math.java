package com.almosi9are.controleiptv;

import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adapter_math extends RecyclerView.Adapter<adapter_math.ViewH> {
    ArrayList<math_info> li = new ArrayList<>();
    Context context;
    private static int ID = 0;
    public ArrayList<math_info> getList_maths() {
        return li;
    }

    match_click find;
    public adapter_math(Context context , ArrayList<math_info> li ,match_click find) {
        this.li = li;
        this.context = context;
        this.find = find;
        if(li.size() == 0){
            ID = 0;
        }
        else{
            ID = 1;
        }
    }

    public adapter_math(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public ViewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;

        if (viewType == 0) {
            v = inflater.inflate(R.layout.no_math, parent, false);
        } else {
            v = inflater.inflate(R.layout.math_item, parent, false);
        }
        return new ViewH(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewH holder, int position) {
        if (getItemViewType(position) == 0) {
            // If it's the 'no_history' layout, we don't need to bind any data here.
            // The layout itself handles displaying the "empty History" message and animation.
        } else {
            // It's the 'item_history' layout, so bind the data to the views.
            holder.txt_terrain.setText(li.get(position).getCommentaire());
            holder.txt_dirbi.setText(li.get(position).getDawri());
            holder.txt_channel.setText(li.get(position).getChannel());
            holder.txt_Date_debut.setText("Debut : "+li.get(position).getDate_debut());
            holder.txt_Date_fin.setText("Fin : "+li.get(position).getDate_fin());
            holder.TeamA.setText(li.get(position).getTeamA());
            holder.TeamB.setText(li.get(position).getTeamB());
            holder.textView_score.setText(li.get(position).getTeamA_score()+"    -    "+li.get(position).getTeamB_score());
            Glide.with(holder.TeamB_flag.getContext())
                    .load(li.get(position).getTeamB_flag())
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.TeamB_flag);

            Glide.with(holder.TeamB_flag_tr.getContext())
                    .load(li.get(position).getTeamB_flag())
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.TeamB_flag_tr);

            Glide.with(holder.TeamA_flag.getContext())
                    .load(li.get(position).getTeamA_flag())
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.TeamA_flag);

            Glide.with(holder.TeamA_flag_tr.getContext())
                    .load(li.get(position).getTeamA_flag())
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.TeamA_flag_tr);

        }
    }
    public void updateData(ArrayList<math_info> newList) {
        li.clear(); // Clear the existing data in the list_songs ArrayList
        this.li = newList ; // Add the new data to the list_songs ArrayList
        notifyDataSetChanged(); // Notify the adapter about the data change
    }

    @Override
    public int getItemCount() {
        // If the list is empty, return 1 to display the 'no_history' layout,
        // otherwise return the size of the list for 'item_history' layouts.
        return li.size() == 0 ? 1 : li.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Return 0 for 'no_history' layout when the list is empty, otherwise return 1 for 'item_history'.
        return li.size() == 0 ? 0 : 1;
    }

    public class ViewH extends RecyclerView.ViewHolder{
        TextView TeamA,TeamB,txt_Date_fin , txt_Date_debut,txt_channel,txt_dirbi,txt_terrain ,textView_score;
        ImageView TeamA_flag ,TeamB_flag ;
        ImageView TeamB_flag_tr ,TeamA_flag_tr ;
        public ViewH(@NonNull View itemView) {
            super(itemView);
            TeamA_flag = itemView.findViewById(R.id.TeamA_flag);
            TeamB_flag = itemView.findViewById(R.id.TeamB_flag);
            TeamA = itemView.findViewById(R.id.TeamA);
            TeamB = itemView.findViewById(R.id.TeamB);
            txt_Date_debut = itemView.findViewById(R.id.txt_Date_debut);
            txt_Date_fin = itemView.findViewById(R.id.txt_Date_fin);
            txt_channel = itemView.findViewById(R.id.txt_channel);
            txt_dirbi = itemView.findViewById(R.id.txt_dirbi);
            txt_terrain = itemView.findViewById(R.id.txt_terrain);
            TeamA_flag_tr = itemView.findViewById(R.id.TeamA_flag_tr);
            TeamB_flag_tr = itemView.findViewById(R.id.TeamB_flag_tr);
            textView_score = itemView.findViewById(R.id.textView_score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    find.click(getAdapterPosition() ,TeamA_flag,TeamB_flag,TeamA,TeamB,txt_channel,textView_score,txt_Date_debut,txt_Date_fin,txt_dirbi,TeamA_flag_tr,TeamB_flag_tr);
                }
            });
        }
    }
}