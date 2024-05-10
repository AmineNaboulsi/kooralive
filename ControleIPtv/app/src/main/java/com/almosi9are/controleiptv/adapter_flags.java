package com.almosi9are.controleiptv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adapter_flags extends RecyclerView.Adapter<adapter_flags.ViewH> {
    ArrayList<flagmodel> li = new ArrayList<>();
    Context context;
    static String team = "A";
    flag_click flag_click;
    private static int ID = 0;
    public ArrayList<flagmodel> getList_maths() {
        return li;
    }

    public void setList_songs(ArrayList<flagmodel> list_songs) {
        this.li = list_songs;
    }

    public adapter_flags(Context context , ArrayList<flagmodel> li ,flag_click flag_click ,String team) {
        this.li = li;
        this.context = context;
        this.flag_click = flag_click;
        this.team = team;
        if(li.size() == 0){
            ID = 0;
        }
        else{
            ID = 1;
        }
    }

    public adapter_flags(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public ViewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;

        if (viewType == 0) {
            v = inflater.inflate(R.layout.no_flag, parent, false);
        } else {
            v = inflater.inflate(R.layout.flag, parent, false);
        }
        return new ViewH(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewH holder, int position) {
        if (getItemViewType(position) == 0) {
            // If it's the 'no_history' layout, we don't need to bind any data here.
            // The layout itself handles displaying the "empty History" message and animation.
        }else{
            Glide.with(holder.img_flage.getContext())
                    .load(li.get(position).getUrl())
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.img_flage);
            holder.txt_flag_name.setText(li.get(position).getName());
            holder.img_flage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    flag_click.click(position,team);
                }
            });
        }
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
    public void updateData(ArrayList<String> newList) {
// Add the new data to the list_songs ArrayList
        notifyDataSetChanged(); // Notify the adapter about the data change
    }


    public class ViewH extends RecyclerView.ViewHolder{
        ImageView img_flage;
        TextView txt_flag_name;
        public ViewH(@NonNull View itemView) {
            super(itemView);
            img_flage = itemView.findViewById(R.id.img_flage);
            txt_flag_name = itemView.findViewById(R.id.txt_flag_name);

        }
    }
}