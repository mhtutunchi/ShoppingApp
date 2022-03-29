package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.AllProductAdapter;
import com.example.shoppingapp.adapter.NewProductAdapter;
import com.example.shoppingapp.model.Product;

public class AllProductActivity extends AppCompatActivity {
    List<Product> listProduct = new ArrayList<>();
    RecyclerView recyclerViewAllProduct;
    AllProductAdapter allProductAdapter;
    RequestQueue requestQueue;

    LinearLayout layout_sort, layout_filter;


    public static int SET_CHECK_RDB = 1;

    public static final int RDB_NEW = 1;
    public static final int RDB_PRICE_DESC = 2;
    public static final int RDB_PRICE_ASC = 3;
    public static final int RDB_VIEW = 4;
    public static final int RDB_SOLD = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        requestQueue = Volley.newRequestQueue(this);
        layout_filter = findViewById(R.id.layout_filter);
        layout_sort  = findViewById(R.id.layout_sort);

        recyclerViewAllProduct = findViewById(R.id.recyclerView_product);
        recyclerViewAllProduct.setHasFixedSize(true);
        recyclerViewAllProduct.setLayoutManager(new LinearLayoutManager(this));
        allProductAdapter = new AllProductAdapter(this, listProduct);
        recyclerViewAllProduct.setAdapter(allProductAdapter);

        getAllProduct();

        layout_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AllProductActivity.this);
                View layout_bottom_sheet = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.layout_bottom_sheet_sort , findViewById(R.id.parent));

                RadioButton rdb_new = layout_bottom_sheet.findViewById(R.id.rdb_new);
                RadioButton rdb_price_desc = layout_bottom_sheet.findViewById(R.id.rdb_price_desc);
                RadioButton rdb_price_asc = layout_bottom_sheet.findViewById(R.id.rdb_price_asc);
                RadioButton rdb_view = layout_bottom_sheet.findViewById(R.id.rdb_view);
                RadioButton rdb_sold = layout_bottom_sheet.findViewById(R.id.rdb_sold);

                switch (SET_CHECK_RDB){

                    case RDB_NEW:
                        rdb_new.setChecked(true);
                        break;
                    case RDB_PRICE_DESC:
                        rdb_price_desc.setChecked(true);
                        break;
                    case RDB_PRICE_ASC:
                        rdb_price_asc.setChecked(true);
                        break;
                    case RDB_VIEW:
                        rdb_view.setChecked(true);
                        break;
                    case RDB_SOLD:
                        rdb_sold.setChecked(true);
                        break;
                }

                rdb_new.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        listProduct.clear();
                        getAllProduct();
                        bottomSheetDialog.dismiss();
                        SET_CHECK_RDB = RDB_NEW;

                    }
                });

                rdb_price_desc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        listProduct.clear();
                        getProductByPriceDesc();
                        bottomSheetDialog.dismiss();
                        SET_CHECK_RDB = RDB_PRICE_DESC;


                    }
                });

                rdb_price_asc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        listProduct.clear();
                        getProductByPriceASC();
                        bottomSheetDialog.dismiss();
                        SET_CHECK_RDB = RDB_PRICE_ASC;

                    }
                });

                rdb_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listProduct.clear();
                        getProductByViewDecs();
                        bottomSheetDialog.dismiss();
                        SET_CHECK_RDB = RDB_VIEW;

                    }
                });

                rdb_sold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listProduct.clear();
                        getProductBySoldDecs();
                        bottomSheetDialog.dismiss();
                        SET_CHECK_RDB = RDB_SOLD;
                    }
                });


                bottomSheetDialog.setContentView(layout_bottom_sheet);
                bottomSheetDialog.show();

            }
        });

        layout_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AllProductActivity.this , FilterActivity.class));

            }
        });


    }
    private void getProductByPriceASC() {

        String url = Link.LINK_PRODUCT_PRICE_ASC;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listProduct.add(products[i]);
                    allProductAdapter.notifyDataSetChanged();

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

    private void getProductByPriceDesc() {

        String url = Link.LINK_PRODUCT_PRICE_DESC;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listProduct.add(products[i]);
                    allProductAdapter.notifyDataSetChanged();

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

    private void getAllProduct() {

        String url = Link.LINK_ALL_PRODUCT;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString(), Product[].class);

                for (int i = 0; i < products.length; i++) {

                    listProduct.add(products[i]);
                    allProductAdapter.notifyDataSetChanged();

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

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        requestQueue.add(request);

    }

    private void getProductByViewDecs() {

        String url = Link.LINK_PRODUCT_VIEW_DECS;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listProduct.add(products[i]);
                    allProductAdapter.notifyDataSetChanged();

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

    private void getProductBySoldDecs() {

        String url = Link.LINK_PRODUCT_SOLD_DECS;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Gson gson = new Gson();
                Product[] products = gson.fromJson(response.toString() ,  Product[].class);

                for (int i = 0 ; i<products.length ; i++){

                    listProduct.add(products[i]);
                    allProductAdapter.notifyDataSetChanged();

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

}