package com.example.shoppingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.shoppingapp.R;
import com.example.shoppingapp.model.Banner;
import com.squareup.picasso.Picasso;

import java.util.List;
public class SliderAdapter extends PagerAdapter {

    Context context;
    List<Banner> data;

    public SliderAdapter(Context context, List<Banner> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_slider , container , false);
        ImageView img_slider = view.findViewById(R.id.img_slider);

        Picasso.get().load(data.get(position).getLink_img()).into(img_slider);

        view.setRotationY(-180);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
