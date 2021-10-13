package com.example.shoppingapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.model.Banner;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerSecondAdapter extends RecyclerView.Adapter<BannerSecondAdapter.MyViewHolder> {

    Context context;
    List<Banner> data;

    public BannerSecondAdapter(Context context, List<Banner> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_second , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_banner);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_banner;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_banner = itemView.findViewById(R.id.img_banner);

        }
    }
}
