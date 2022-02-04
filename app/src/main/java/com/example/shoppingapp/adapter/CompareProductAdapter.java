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

import com.squareup.picasso.Picasso;

import java.util.List;
import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.CompareActivity;
import com.example.shoppingapp.model.Product;

public class CompareProductAdapter extends RecyclerView.Adapter<CompareProductAdapter.MyViewHolder> {

    Context context;
    List<Product> data;

    public CompareProductAdapter(Context context, List<Product> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_compare_product , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_name_product.setText(data.get(position).getName());
        holder.txt_price.setText(data.get(position).getPrice());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context , CompareActivity.class);
                intent.putExtra(Key.id_second_product , data.get(position).getId());
                intent.putExtra(Key.price_second_product , data.get(position).getPrice());
                intent.putExtra(Key.title_second_product , data.get(position).getName());
                intent.putExtra(Key.link_img_second_product , data.get(position).getLink_img());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txt_name_product , txt_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            txt_name_product = itemView.findViewById(R.id.name_product);
            txt_price = itemView.findViewById(R.id.txt_price);

        }
    }
}
