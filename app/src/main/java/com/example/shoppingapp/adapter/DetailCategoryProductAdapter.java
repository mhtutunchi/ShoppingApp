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
import com.example.shoppingapp.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class DetailCategoryProductAdapter extends RecyclerView.Adapter<DetailCategoryProductAdapter.MyViewHolder>{

    Context context;
    List<Product> data;

    public DetailCategoryProductAdapter(Context context, List<Product> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_product , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String txt_price_deci = decimalFormat.format(Integer.valueOf(data.get(position).getPrice()));
        //String txt_off_price_deci = decimalFormat.format(Integer.valueOf(amazingOfferProduct.getOffprice()));
        holder.name_product.setText(data.get(position).getName());
        //txt_price_off.setText(txt_off_price_deci + " تومان ");
        //value_off.setText(amazingOfferProduct.getValue_off() + " % ");
        holder.txt_price.setText(txt_price_deci);

        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product;
        TextView name_product , txt_price_off , value_off , txt_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            name_product = itemView.findViewById(R.id.name_product);
            //txt_price_off = itemView.findViewById(R.id.txt_price_off);
            //value_off = itemView.findViewById(R.id.value_off);
            txt_price = itemView.findViewById(R.id.txt_price);
        }
    }
}
