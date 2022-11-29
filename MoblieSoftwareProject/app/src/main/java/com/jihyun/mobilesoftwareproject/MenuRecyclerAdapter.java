package com.jihyun.mobilesoftwareproject;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {
    private ArrayList<Menudata> menudata;

    @NonNull
    @Override
    public MenuRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(menudata.get(position));
    }

    public void setmenulist(ArrayList<Menudata> list){
        this.menudata = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return menudata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clock_in;
        TextView mnn_in;
        TextView kcal_in;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clock_in = itemView.findViewById(R.id.clock_info);
            mnn_in = itemView.findViewById(R.id.mnn_info);
            kcal_in = itemView.findViewById(R.id.kcal_info);
        }

        void onBind(Menudata item) {
            clock_in.setText(item.gettime());
            mnn_in.setText(item.getmnn());
            kcal_in.setText(item.getkcal());
        }
    }
}