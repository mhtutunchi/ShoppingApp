package com.example.shoppingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.AllProductAdapter;
import com.example.shoppingapp.adapter.SimilarProductAdapter;
import com.example.shoppingapp.model.Product;

public class ShowProductFilterActivity extends AppCompatActivity {

    Bundle bundle;
    String category_id, brand_id;
    List<Product> listProduct = new ArrayList<>();
    AllProductAdapter adapter;
    RecyclerView recyclerView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_filter);


        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();
        category_id = bundle.getString(Key.category_id);
        brand_id = bundle.getString(Key.brand_id);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AllProductAdapter(this, listProduct);
        recyclerView.setAdapter(adapter);


        if (category_id != null && brand_id == null) {
            getCategory(category_id);
        } else if (brand_id != null && category_id == null) {
            getBrand(brand_id);
        } else {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }


    }

    private void getBrand(String brand_id) {


        String url = Link.LINK_PRODUCT_FILTER_BRAND;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString(), Product[].class);

                for (int i = 0; i < products.length; i++) {

                    listProduct.add(products[i]);
                    adapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                Log.d("Error : ", error.getMessage() + "");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(Key.brand_id, brand_id);
                return map;


            }
        };
        requestQueue.add(request);

    }


    private void getCategory(String category_id) {


        String url = Link.LINK_PRODUCT_FILTER_CATEGORY;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString(), Product[].class);

                for (int i = 0; i < products.length; i++) {

                    listProduct.add(products[i]);
                    adapter.notifyDataSetChanged();

                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                Log.d("Error : ", error.getMessage() + "");

            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put(Key.category_id, category_id);
                return map;


            }
        };
        requestQueue.add(request);

    }

}