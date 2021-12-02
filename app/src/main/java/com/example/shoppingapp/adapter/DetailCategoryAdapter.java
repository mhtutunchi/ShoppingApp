package com.example.shoppingapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.DetailCategoryActivity;
import com.example.shoppingapp.activity.ShowCategoryDetailProductActivity;
import com.example.shoppingapp.model.Category;
import com.example.shoppingapp.model.DetailCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailCategoryAdapter extends RecyclerView.Adapter<DetailCategoryAdapter.MyViewHolder> {


    Context context;
    List<DetailCategory> data;

    public DetailCategoryAdapter(Context context, List<DetailCategory> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_all_category , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.name_category.setText(data.get(position).getName());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , ShowCategoryDetailProductActivity.class);
                intent.putExtra(Key.id , data.get(position).getId());
                intent.putExtra(Key.title , data.get(position).getName());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  name_category;
        ImageView img_category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name_category = itemView.findViewById(R.id.name_category);
            img_category = itemView.findViewById(R.id.img_category);

        }

    }
}