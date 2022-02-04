package com.example.shoppingapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.shoppingapp.R;
import com.example.shoppingapp.model.OptionProduct;
import com.example.shoppingapp.model.ReviewProduct;

public class ReviewProductAdapter extends RecyclerView.Adapter<ReviewProductAdapter.MyViewHolder> {

    Context context;
    List<ReviewProduct> data;

    public ReviewProductAdapter(Context context, List<ReviewProduct> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_review_product, parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_header.setText(data.get(position).getHeader_text());
        holder.txt_final.setText(data.get(position).getFinal_text());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_header , txt_final ;
        ImageView img_product;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_header = itemView.findViewById(R.id.txt_header);
            txt_final = itemView.findViewById(R.id.txt_final);
            img_product = itemView.findViewById(R.id.img_product);

        }
    }
}
