package com.example.shoppingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.util.List;
import com.example.shoppingapp.R;
import com.example.shoppingapp.model.ImageProduct;
import com.example.shoppingapp.model.Intro;

public class IntroAdapter extends PagerAdapter {

    Context context;
    List<Intro> data;

    public IntroAdapter(Context context, List<Intro> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_intro , container , false);

        TextView txt_description = view.findViewById(R.id.txt_description);
        LottieAnimationView lottie = view.findViewById(R.id.lottie);

        txt_description.setText(data.get(position).getDescription());
        lottie.setAnimationFromUrl(data.get(position).getLink_lottie());


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