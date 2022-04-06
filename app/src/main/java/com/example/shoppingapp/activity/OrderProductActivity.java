package com.example.shoppingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.Global.MyPrefManager;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.OredrProductAdapter;
import com.example.shoppingapp.adapter.PayOffAdapter;
import com.example.shoppingapp.api.ApiClient;
import com.example.shoppingapp.api.ApiInterface;
import com.example.shoppingapp.model.Cart;
import com.example.shoppingapp.model.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProductActivity extends AppCompatActivity {

    RecyclerView recyclerView_order;
    ApiInterface request;
    MyPrefManager pref;
    String email ;
    List<Order> listOrder = new ArrayList<>();
    OredrProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);

        request = ApiClient.getApiClient().create(ApiInterface.class);
        pref = new MyPrefManager(this);
        recyclerView_order = findViewById(R.id.recycleRView_order_product);
        recyclerView_order.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_order.setHasFixedSize(true);

        email = pref.getUserData().get(MyPrefManager.EMAIL);

        Call<List<Order>> call = request.getListOrderProduct(email);

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {


                listOrder = response.body();
                adapter = new OredrProductAdapter(getApplicationContext() , listOrder);
                recyclerView_order.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

                Toast.makeText(OrderProductActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });
    }
}