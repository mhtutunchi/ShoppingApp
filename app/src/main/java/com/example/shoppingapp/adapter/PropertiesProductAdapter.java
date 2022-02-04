package com.example.shoppingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.shoppingapp.R;
import com.example.shoppingapp.model.OptionProduct;

public class PropertiesProductAdapter extends RecyclerView.Adapter<PropertiesProductAdapter.MyViewHolder> {

    Context context;
    List<OptionProduct> data;

    public PropertiesProductAdapter(Context context, List<OptionProduct> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_properties_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_description.setText(data.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_description = itemView.findViewById(R.id.txt_description);

        }
    }
}
