package com.example.shoppingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import com.example.shoppingapp.adapter.BrandProductAdapter;
import com.example.shoppingapp.adapter.CommentLimitAdapter;
import com.example.shoppingapp.model.Comment;
import com.example.shoppingapp.model.Product;

public class BrandProductActivity extends AppCompatActivity {

    RecyclerView recyclerView_brand_product;
    List<Product> listProduct= new ArrayList<>();
    BrandProductAdapter brandProductAdapter;
    RequestQueue requestQueue;
    Bundle bundle;
    String NAME_BRAND;
    TextView txt_name_brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_product);

        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();
        NAME_BRAND = bundle.getString(Key.name);
        txt_name_brand  = findViewById(R.id.name_brand);
        txt_name_brand.setText(NAME_BRAND);


        recyclerView_brand_product = findViewById(R.id.recyclerView_brand_product);
        recyclerView_brand_product.setHasFixedSize(true);
        recyclerView_brand_product.setLayoutManager(new LinearLayoutManager(this));
        brandProductAdapter = new BrandProductAdapter(this , listProduct);
        recyclerView_brand_product.setAdapter(brandProductAdapter);

        String url = Link.LINK_BRAND_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listProduct.add(products[i]);
                    brandProductAdapter.notifyDataSetChanged();

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
                map.put(Key.name , NAME_BRAND);
                return map;

            }
        };
        requestQueue.add(request);



    }
}