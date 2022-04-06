package com.example.shoppingapp.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.R;
import com.example.shoppingapp.activity.ShowDetailProductActivity;
import com.example.shoppingapp.model.Amazing;
import com.example.shoppingapp.model.AmazingOfferProduct;
import com.example.shoppingapp.model.FirstAmazing;
import com.example.shoppingapp.model.Product;

public class WatchProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Amazing> data;

    public WatchProductAdapter(Context context, List<Amazing> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Item Amazing Offer
        if (viewType==0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_new_watch , parent , false);
            return new AmazingOfferViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_first_watch , parent , false);
            return new FirsAmazingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (getItemViewType(position)==0){
            Product product = (Product) data.get(position).getObject();
            ((AmazingOfferViewHolder)holder).setNewWatchProduct(product);


        }else {
            FirstAmazing firstAmazing = (FirstAmazing) data.get(position).getObject();
            ((FirsAmazingViewHolder)holder).setFirstAmazing(firstAmazing);

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    public static class FirsAmazingViewHolder extends RecyclerView.ViewHolder {

        ImageView img_first_amazing;
        TextView txt_first_title;

        public FirsAmazingViewHolder(@NonNull View itemView) {
            super(itemView);

            img_first_amazing = itemView.findViewById(R.id.img_first_amazing);
            txt_first_title = itemView.findViewById(R.id.txt_first_title);

        }

        public void setFirstAmazing(FirstAmazing firstAmazing){

            txt_first_title.setText(firstAmazing.getTitle());
            Picasso.get().load(firstAmazing.getLink_img()).into(img_first_amazing);

        }

    }

    public static class AmazingOfferViewHolder extends RecyclerView.ViewHolder {

        ImageView img_amazing_offer;
        TextView name_product , txt_price_off , value_off , txt_price;

        public AmazingOfferViewHolder(@NonNull View itemView) {
            super(itemView);

            img_amazing_offer = itemView.findViewById(R.id.img_amazing_offer);
            name_product = itemView.findViewById(R.id.name_product);
            // txt_price_off = itemView.findViewById(R.id.txt_price_off);
            // value_off = itemView.findViewById(R.id.value_off);
            txt_price = itemView.findViewById(R.id.txt_price);

        }

        public void setNewWatchProduct(Product product){

            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            String txt_price_deci = decimalFormat.format(Integer.valueOf(product.getPrice()));
            name_product.setText(product.getName());

            txt_price.setText(txt_price_deci+ "تومان ");

            Picasso.get().load(product.getLink_img()).into(img_amazing_offer);
        }

    }

}
