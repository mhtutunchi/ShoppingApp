package com.example.shoppingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.PropertiesProductAdapter;
import com.example.shoppingapp.model.OptionProduct;


public class CompareActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    //First Product
    String id_first, name_product_first, price_first, link_img_first;
    TextView txt_price, name_product;
    ImageView img_product;
    RecyclerView recyclerView_properties_product_first;
    //OptionProduct == Properties
    List<OptionProduct> listPropertiesProduct_first = new ArrayList<>();
    PropertiesProductAdapter propertiesProductAdapter_first;


    //Second
    Bundle bundle;
    String id_second, name_product_second, price_second, link_img_second;
    TextView txt_price_second, txt_name_product_second;
    ImageView img_product_second;
    RecyclerView recyclerView_properties_product_second;
    //OptionProduct == Properties
    List<OptionProduct> listPropertiesProduct_second = new ArrayList<>();
    PropertiesProductAdapter propertiesProductAdapter_second;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        requestQueue = Volley.newRequestQueue(this);

        id_first = ShowDetailProductActivity.id;

        getFirstProduct(id_first);

        bundle = getIntent().getExtras();

        id_second = bundle.getString(Key.id_second_product);
        name_product_second = bundle.getString(Key.title_second_product);
        price_second = bundle.getString(Key.price_second_product);
        link_img_second = bundle.getString(Key.link_img_second_product);

        txt_price_second = findViewById(R.id.txt_price_second);
        txt_price_second.setText(price_second);

        txt_name_product_second = findViewById(R.id.name_product_second);
        txt_name_product_second.setText(name_product_second);

        img_product_second = findViewById(R.id.img_product_second);
        Picasso.get().load(link_img_second).into(img_product_second);


        recyclerView_properties_product_second = findViewById(R.id.recyclerView_product_second);
        recyclerView_properties_product_second.setHasFixedSize(true);
        recyclerView_properties_product_second.setLayoutManager(new LinearLayoutManager(this));
        propertiesProductAdapter_second = new PropertiesProductAdapter(this, listPropertiesProduct_second);
        recyclerView_properties_product_second.setAdapter(propertiesProductAdapter_second);

        String url = Link.LINK_PROPERTIES_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                OptionProduct[] optionProducts = gson.fromJson(response.toString(), OptionProduct[].class);

                for (int i = 0; i < optionProducts.length; i++) {

                    listPropertiesProduct_second.add(optionProducts[i]);
                    propertiesProductAdapter_second.notifyDataSetChanged();

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
                map.put(Key.id, id_second);
                return map;

            }
        };
        requestQueue.add(request);


    }

    private void getFirstProduct(String id_first) {


        name_product_first = ShowDetailProductActivity.name_product;
        price_first = ShowDetailProductActivity.price;
        link_img_first = ShowDetailProductActivity.link_img;

        txt_price = findViewById(R.id.txt_price);
        name_product = findViewById(R.id.name_product);
        img_product = findViewById(R.id.img_product);

        name_product.setText(name_product_first);
        txt_price.setText(price_first);
        Picasso.get().load(link_img_first).into(img_product);

        recyclerView_properties_product_first = findViewById(R.id.recyclerView_product);
        recyclerView_properties_product_first.setHasFixedSize(true);
        recyclerView_properties_product_first.setLayoutManager(new LinearLayoutManager(this));
        propertiesProductAdapter_first = new PropertiesProductAdapter(this, listPropertiesProduct_first);
        recyclerView_properties_product_first.setAdapter(propertiesProductAdapter_first);

        String url = Link.LINK_PROPERTIES_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                OptionProduct[] optionProducts = gson.fromJson(response.toString(), OptionProduct[].class);

                for (int i = 0; i < optionProducts.length; i++) {

                    listPropertiesProduct_first.add(optionProducts[i]);
                    propertiesProductAdapter_first.notifyDataSetChanged();

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
                map.put(Key.id, id_first);
                return map;

            }
        };
        requestQueue.add(request);


    }
}