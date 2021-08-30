package com.example.shoppingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.SliderAdapter;
import com.example.shoppingapp.model.Banner;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONArray;

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
    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        viewPager = view.findViewById(R.id.viewPager);
        tabs = view.findViewById(R.id.tabs);
        sliderAdapter = new SliderAdapter(getContext(), listBanner);
        viewPager.setAdapter(sliderAdapter);
        tabs.setupWithViewPager(viewPager, true);

        viewPager.setRotationY(180);

        final int paddingPx = 120;
        final float MIN_SCALE = 0.8f;
        final float MAX_SCALE = 1f;

        viewPager.setClipToPadding(false);
        viewPager.setPadding(paddingPx, 0, paddingPx, 0);

        ViewPager.PageTransformer transformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

                float pagerWidthPx = ((ViewPager) page.getParent()).getWidth();
                float pageWidthPx = pagerWidthPx - 2 * paddingPx;

                float maxVisiblePages = pagerWidthPx / pageWidthPx;
                float center = maxVisiblePages / 2f;

                float scale;
                if (position + 0.5f < center - 0.5f || position > center) {
                    scale = MIN_SCALE;
                } else {
                    float coef;
                    if (position + 0.5f < center) {
                        coef = (position + 1 - center) / 0.5f;
                    } else {
                        coef = (center - position) / 0.5f;
                    }
                    scale = coef * (MAX_SCALE - MIN_SCALE) + MIN_SCALE;
                }
                page.setScaleX(scale);
                page.setScaleY(scale);
            }
        };
        viewPager.setPageTransformer(false, transformer);

        String url = Link.LINK_BANNER_SLIDER;


        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Banner[] banners = gson.fromJson(response.toString(), Banner[].class);
                for (int i = 0; i < banners.length; i++) {
                    listBanner.add(banners[i]);
                    sliderAdapter.notifyDataSetChanged();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage() + "hello", Toast.LENGTH_SHORT).show();
                Log.d("Error : ", error.getMessage() + "");
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        requestQueue.add(request);

        return view;
    }
}
 