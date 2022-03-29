package com.example.shoppingapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.shoppingapp.R;
import com.example.shoppingapp.model.Comment;

public class CommentLimitAdapter extends RecyclerView.Adapter<CommentLimitAdapter.MyViewHolder> {

    Context context;
    List<Comment> data;

    public CommentLimitAdapter(Context context, List<Comment> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_limit , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_date.setText(data.get(position).getDate());
        holder.txt_description.setText(data.get(position).getDescription());
        holder.txt_title.setText(data.get(position).getTitle());
        holder.txt_user_email.setText(data.get(position).getUser_email());

        String offer = data.get(position).getOffer();

        if (offer.equals("1")){
            holder.layout_offer_positive.setVisibility(View.VISIBLE);
        }else if (offer.equals("2")){
            holder.layout_offer_negative.setVisibility(View.VISIBLE);
        }else {
            holder.layout_offer_negative.setVisibility(View.GONE);
            holder.layout_offer_positive.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView   txt_title , txt_date , txt_user_email , txt_description  ;
        LinearLayout layout_offer_negative , layout_offer_positive;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_user_email = itemView.findViewById(R.id.txt_user_email);
            txt_description = itemView.findViewById(R.id.txt_description);
            layout_offer_negative = itemView.findViewById(R.id.layout_offer_negative);
            layout_offer_positive = itemView.findViewById(R.id.layout_offer_positive);



        }
    }
}
