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

import com.example.shoppingapp.R;
import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.adapter.OptionProductAdapter;
import com.example.shoppingapp.adapter.PropertiesProductAdapter;
import com.example.shoppingapp.model.OptionProduct;

public class PropertiesProductActivity extends AppCompatActivity {

    Bundle bundle;
    String id, name_product;

    RequestQueue requestQueue;
    RecyclerView recyclerView_properties_product;
    //OptionProduct == Properties
    List<OptionProduct> listOptionProduct = new ArrayList<>();
    PropertiesProductAdapter propertiesProductAdapter;
    TextView txt_name_prodcut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties_product);

        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();
        id = bundle.getString(Key.id);
        name_product = bundle.getString(Key.title);

        txt_name_prodcut = findViewById(R.id.name_product);

        txt_name_prodcut.setText(name_product);

        recyclerView_properties_product = findViewById(R.id.recyclerView_properties_product);
        recyclerView_properties_product.setHasFixedSize(true);
        recyclerView_properties_product.setLayoutManager(new LinearLayoutManager(this));
        propertiesProductAdapter = new PropertiesProductAdapter(this, listOptionProduct);
        recyclerView_properties_product.setAdapter(propertiesProductAdapter);

        String url = Link.LINK_PROPERTIES_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                OptionProduct[] optionProducts = gson.fromJson(response.toString(), OptionProduct[].class);

                for (int i = 0; i < optionProducts.length; i++) {

                    listOptionProduct.add(optionProducts[i]);
                    propertiesProductAdapter.notifyDataSetChanged();

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
                map.put(Key.id, id);
                return map;

            }
        };
        requestQueue.add(request);


    }
}