package com.example.shoppingapp.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.DetailCategoryActivity;
import com.example.shoppingapp.model.Category;

public class FilterCategoryAdapter extends RecyclerView.Adapter<FilterCategoryAdapter.MyViewHolder> {


    Context context;
    List<Category> data;
    IItemClickListener listener;
    private int LastCheckPosition = -1;

    public FilterCategoryAdapter(Context context, List<Category> data, IItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    public FilterCategoryAdapter(Context context, List<Category> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_fliter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.rdb_filter.setText(data.get(position).getTitle());
        holder.rdb_filter.setChecked(LastCheckPosition == position);

        holder.rdb_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LastCheckPosition = position;
                notifyDataSetChanged();

                listener.onClickCategory(data.get(position).getId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        RadioButton rdb_filter;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rdb_filter = itemView.findViewById(R.id.rdb_filter);


        }

    }

    public interface IItemClickListener {

        void onClickCategory(String id);

    }
}
