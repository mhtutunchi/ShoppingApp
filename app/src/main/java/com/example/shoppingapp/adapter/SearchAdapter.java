package com.example.shoppingapp.adapter;


import android.content.Context;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import com.example.shoppingapp.Global.FilterProduct;
import com.example.shoppingapp.R;
import com.example.shoppingapp.model.AmazingOfferProduct;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {


    Context context;
    public List<AmazingOfferProduct> data;
    List<AmazingOfferProduct> data_filter;
    FilterProduct filterProduct;

    public SearchAdapter(Context context, List<AmazingOfferProduct> data) {
        this.context = context;
        this.data = data;
        this.data_filter = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name_product.setText(data.get(position).getName());
        Picasso.get().load(data.get(position).getLink_img()).into(holder.img_product);

        String price = data.get(position).getPrice();
        String price_off = data.get(position).getOffprice();

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String txt_price_deci = decimalFormat.format(Integer.valueOf(data.get(position).getPrice()));
        String txt_off_price_deci = decimalFormat.format(Integer.valueOf(data.get(position).getOffprice()));
        holder.name_product.setText(data.get(position).getName());


        SpannableString spannableString = new SpannableString(txt_price_deci);
        spannableString.setSpan(new StrikethroughSpan(), 0, data.get(position).getPrice().length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.txt_price.setText(spannableString);

        if (price.equals(price_off)) {

            holder.value_off.setVisibility(View.GONE);
            holder.txt_price_off.setVisibility(View.GONE);
            holder.txt_price.setText(txt_price_deci + " تومان ");

        } else {
            holder.value_off.setVisibility(View.VISIBLE);
            holder.txt_price_off.setVisibility(View.VISIBLE);
            holder.value_off.setText(data.get(position).getValue_off() + " % ");
            holder.txt_price_off.setText(txt_off_price_deci + " تومان ");
            holder.txt_price.setText(spannableString);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {

        if (filterProduct == null) {
            filterProduct = new FilterProduct(this, data);
        }
        return filterProduct;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView name_product, txt_price_off, value_off, txt_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            name_product = itemView.findViewById(R.id.name_product);
            txt_price_off = itemView.findViewById(R.id.txt_price_off);
            value_off = itemView.findViewById(R.id.value_off);
            txt_price = itemView.findViewById(R.id.txt_price);

        }

    }
}
