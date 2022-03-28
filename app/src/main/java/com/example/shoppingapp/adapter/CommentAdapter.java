package com.example.shoppingapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.shoppingapp.R;
import com.example.shoppingapp.model.Brand;
import com.example.shoppingapp.model.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    Context context;
    List<Comment> data;

    public CommentAdapter(Context context, List<Comment> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_comment , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_rating.setText(data.get(position).getRating());
        holder.txt_date.setText(data.get(position).getDate());
        holder.txt_description.setText(data.get(position).getDescription());
        holder.txt_title.setText(data.get(position).getTitle());
        holder.txt_user_email.setText(data.get(position).getUser_email());

        String rating = data.get(position).getRating();

        if (rating.startsWith("3")){
            holder.txt_rating.setBackgroundResource(R.drawable.bg_txt_rating_orange);
        }else if (rating.startsWith("4")){
            holder.txt_rating.setBackgroundResource(R.drawable.bg_txt_rating_blue);
        }else if (rating.startsWith("5")){
            holder.txt_rating.setBackgroundResource(R.drawable.bg_txt_rating_green);
        }else {
            holder.txt_rating.setBackgroundResource(R.drawable.bg_txt_rating);
        }

        String txt_po = data.get(position).getPositive();
        if (!txt_po.isEmpty()){
            holder.layout_txt_positive.setVisibility(View.VISIBLE);
            holder.txt_positive.setText(data.get(position).getPositive());
        }else {
            holder.layout_txt_positive.setVisibility(View.GONE);
        }

        String txt_ne = data.get(position).getNegative();
        if (!txt_ne.isEmpty()){
            holder.layout_txt_negative.setVisibility(View.VISIBLE);
            holder.txt_negative.setText(data.get(position).getNegative());
        }else {
            holder.layout_txt_negative.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_rating , txt_title , txt_date , txt_user_email , txt_description , txt_positive , txt_negative;
        LinearLayout layout_txt_positive , layout_txt_negative;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_rating = itemView.findViewById(R.id.txt_rating);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_user_email = itemView.findViewById(R.id.txt_user_email);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_positive = itemView.findViewById(R.id.txt_positive);
            txt_negative = itemView.findViewById(R.id.txt_negative);
            layout_txt_positive = itemView.findViewById(R.id.layout_txt_positive);
            layout_txt_negative = itemView.findViewById(R.id.layout_txt_negative);



        }
    }
}
