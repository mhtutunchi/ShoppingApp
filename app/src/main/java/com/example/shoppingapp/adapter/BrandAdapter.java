package com.example.shoppingapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.model.Brand;
import com.example.shoppingapp.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {

    Context context;
    List<Brand> data;

    public BrandAdapter(Context context, List<Brand> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_brand , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_name_brand.setText(data.get(position).getName());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_brand);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_brand;
        TextView txt_name_brand;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_brand = itemView.findViewById(R.id.img_brand);
            txt_name_brand = itemView.findViewById(R.id.name_brand);


        }
    }
}
