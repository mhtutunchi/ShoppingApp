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

import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.R;

import com.example.shoppingapp.adapter.CompareProductAdapter;
import com.example.shoppingapp.adapter.SimilarProductAdapter;
import com.example.shoppingapp.model.Product;

public class CompareProductActivity extends AppCompatActivity {

    Bundle bundle;
    String category_id;

    List<Product> listProduct = new ArrayList<>();
    RecyclerView recyclerView_compare_product;
    RequestQueue requestQueue;
    CompareProductAdapter compareProductAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_product);

        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();

        category_id = bundle.getString(Key.category_id);

        Toast.makeText(this, ""+category_id, Toast.LENGTH_SHORT).show();

        recyclerView_compare_product = findViewById(R.id.recycleRView_Compare_product);
        recyclerView_compare_product.setHasFixedSize(true);
        recyclerView_compare_product.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        compareProductAdapter = new CompareProductAdapter(this , listProduct);
        recyclerView_compare_product.setAdapter(compareProductAdapter);

        String url = Link.LINK_COMPARE_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listProduct.add(products[i]);
                    compareProductAdapter.notifyDataSetChanged();

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

        StringRequest request = new StringRequest(Request.Method.POST , url  ,listener , errorListener ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> map = new HashMap<>();
                map.put(Key.category_id , category_id);
                return map;


            }
        };
        requestQueue.add(request);


    }
}