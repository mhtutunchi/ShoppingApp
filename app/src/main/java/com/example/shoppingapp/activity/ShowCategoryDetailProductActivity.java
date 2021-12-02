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
import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.DetailCategoryProductAdapter;
import com.example.shoppingapp.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowCategoryDetailProductActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    Bundle bundle;
    String name_category , id;
    TextView title;

    //Detail Product
    List<Product> listProduct = new ArrayList<>();
    DetailCategoryProductAdapter adapter;
    RecyclerView recyclerView_detail_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_category_detail_product);

        requestQueue = Volley.newRequestQueue(this);

        bundle = getIntent().getExtras();
        name_category = bundle.getString(Key.title);
        id = bundle.getString(Key.id);
        title = findViewById(R.id.title);
        title.setText(name_category);


        recyclerView_detail_product  = findViewById(R.id.recyclerView_product);
        recyclerView_detail_product.setHasFixedSize(true);
        recyclerView_detail_product.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailCategoryProductAdapter(this , listProduct);
        recyclerView_detail_product.setAdapter(adapter);

        String url = Link.LINK_SHOW_DETAIL_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listProduct.add(products[i]);
                    adapter.notifyDataSetChanged();
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
                map.put(Key.id , id);
                return map;


            }
        };
        requestQueue.add(request);


    }
}
