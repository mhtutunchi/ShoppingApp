package com.example.shoppingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.BrandAdapter;
import com.example.shoppingapp.adapter.CategoryAdapter;
import com.example.shoppingapp.adapter.FilterBrandAdapter;
import com.example.shoppingapp.adapter.FilterCategoryAdapter;
import com.example.shoppingapp.model.Brand;
import com.example.shoppingapp.model.Category;
import com.example.shoppingapp.model.Intro;

public class FilterActivity extends AppCompatActivity {

    //Category
    List<Category> listCategory = new ArrayList<>();
    FilterCategoryAdapter categoryAdapter;
    RecyclerView recyclerviewCategory;
    RequestQueue requestQueue;
    RelativeLayout parent_title , parent_description;
    ImageView img_add;


    //Brand
    List<Brand> listBrand = new ArrayList<>();
    FilterBrandAdapter brandAdapter;
    RecyclerView recyclerView_brand;
    RelativeLayout parent_title_2 , parent_description_2;
    ImageView img_add_2;

    //
    static String category_id , brand_id;
    Button btn_filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        requestQueue = Volley.newRequestQueue(this);

        getCategory();
        getBrand();

        btn_filter = findViewById(R.id.btn_filter);

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (category_id != null || brand_id != null){

                    Intent intent = new Intent(FilterActivity.this ,ShowProductFilterActivity.class );
                    intent.putExtra(Key.category_id , category_id);
                    intent.putExtra(Key.brand_id , brand_id);
                    startActivity(intent);

                }else {
                    Toast.makeText(FilterActivity.this, "حتما یکی را انتخاب کنید", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void getBrand() {

        parent_title_2 = findViewById(R.id.parent_title_2);
        parent_description_2 = findViewById(R.id.parent_description_2);
        img_add_2 = findViewById(R.id.img_add_2);

        img_add_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (parent_description_2.getVisibility() == View.GONE){
                    parent_description_2.setVisibility(View.VISIBLE);
                    img_add_2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_left_24);
                    TransitionManager.beginDelayedTransition(parent_title_2,new AutoTransition());
                }else {
                    parent_description_2.setVisibility(View.GONE);
                    img_add_2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    TransitionManager.beginDelayedTransition(parent_title_2,new AutoTransition());

                }

            }
        });

        recyclerView_brand = findViewById(R.id.recyclerView_brand);
        recyclerView_brand.setHasFixedSize(true);
        recyclerView_brand.setLayoutManager(new LinearLayoutManager(this));
        brandAdapter = new FilterBrandAdapter(this, listBrand, new FilterBrandAdapter.IItemClickListener() {
            @Override
            public void onClickBrand(String id) {

                brand_id = id;

            }
        });
        recyclerView_brand.setAdapter(brandAdapter);

        String url = Link.LINK_FILTER_BRAND;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Brand[] brands = gson.fromJson(response.toString() ,  Brand[].class);

                for (int i = 0 ; i<brands.length ; i++){

                    listBrand.add(brands[i]);
                    brandAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);


    }

    private void getCategory() {

        parent_title = findViewById(R.id.parent_title);
        parent_description = findViewById(R.id.parent_description);
        img_add = findViewById(R.id.img_add);

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (parent_description.getVisibility() == View.GONE){
                    parent_description.setVisibility(View.VISIBLE);
                    img_add.setImageResource(R.drawable.ic_baseline_keyboard_arrow_left_24);
                    TransitionManager.beginDelayedTransition(parent_title,new AutoTransition());
                }else {
                    parent_description.setVisibility(View.GONE);
                    img_add.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    TransitionManager.beginDelayedTransition(parent_title,new AutoTransition());

                }

            }
        });

        recyclerviewCategory = findViewById(R.id.recyclerView_Category);
        recyclerviewCategory.setHasFixedSize(true);
        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(this ));
        categoryAdapter = new FilterCategoryAdapter(this, listCategory, new FilterCategoryAdapter.IItemClickListener() {
            @Override
            public void onClickCategory(String id) {

                category_id = id;

            }
        });
        recyclerviewCategory.setAdapter(categoryAdapter);

        String url = Link.LINK_FILTER_CATEGORY;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Category[] categories = gson.fromJson(response.toString() ,  Category[].class);

                for (int i = 0 ; i<categories.length ; i++){

                    listCategory.add(categories[i]);
                    categoryAdapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.d("Error : " , error.getMessage()+"");

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET , url ,null ,listener , errorListener );
        requestQueue.add(request);


    }

    @Override
    public void onBackPressed() {
        category_id = null;
        brand_id = null;
        super.onBackPressed();
    }
}