package com.example.shoppingapp.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.shoppingapp.R;
import com.example.shoppingapp.model.Brand;
import com.example.shoppingapp.model.Category;

public class FilterBrandAdapter extends RecyclerView.Adapter<FilterBrandAdapter.MyViewHolder> {


    Context context;
    List<Brand> data;
    IItemClickListener listener;
    private int LastCheckPosition = -1;

    public FilterBrandAdapter(Context context, List<Brand> data, IItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    public FilterBrandAdapter(Context context, List<Brand> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_fliter , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.rdb_filter.setText(data.get(position).getName());
        holder.rdb_filter.setChecked(LastCheckPosition==position);

        holder.rdb_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LastCheckPosition = position;
                notifyDataSetChanged();

                listener.onClickBrand(data.get(position).getId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RadioButton rdb_filter;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rdb_filter = itemView.findViewById(R.id.rdb_filter);

        }




    }
    public interface IItemClickListener{

        void onClickBrand(String id );

    }
}
