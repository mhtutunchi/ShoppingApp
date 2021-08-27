package com.example.shoppingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.SliderAdapter;
import com.example.shoppingapp.model.Banner;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }
    View view;
    //Slider
    List<Banner> listBanner = new ArrayList<>();
    SliderAdapter sliderAdapter;
    ViewPager viewPager;
    TabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        tabs = view.findViewById(R.id.tabs);
        sliderAdapter = new SliderAdapter(getContext() , listBanner);
        viewPager.setAdapter(sliderAdapter);
        tabs.setupWithViewPager(viewPager , true);


        return view;
    }
}
 