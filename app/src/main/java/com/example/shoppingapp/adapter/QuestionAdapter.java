package com.example.shoppingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.squareup.picasso.Picasso;

import java.util.List;
import com.example.shoppingapp.model.Question;
import com.example.shoppingapp.R;
import com.example.shoppingapp.model.Brand;
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    Context context;
    List<Question> data;

    public QuestionAdapter(Context context, List<Question> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_question , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_title.setText(data.get(position).getTitle());
        holder.txt_description.setText(data.get(position).getDescription());

        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.parent_description.getVisibility() == View.GONE){
                    holder.parent_description.setVisibility(View.VISIBLE);
                    holder.img_add.setImageResource(R.drawable.ic_baseline_close_24);
                    TransitionManager.beginDelayedTransition(holder.parent_title,new AutoTransition());
                }else {
                    holder.parent_description.setVisibility(View.GONE);
                    holder.img_add.setImageResource(R.drawable.ic_baseline_add_24);
                    TransitionManager.beginDelayedTransition(holder.parent_title,new AutoTransition());

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title , txt_description;
        RelativeLayout parent_title , parent_description;
        ImageView img_add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_description = itemView.findViewById(R.id.txt_description);
            img_add = itemView.findViewById(R.id.img_add);

            parent_title = itemView.findViewById(R.id.parent_title);
            parent_description = itemView.findViewById(R.id.parent_description);


        }
    }
}
