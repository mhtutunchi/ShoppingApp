package com.example.shoppingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.Global.MyPrefManager;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.PayOffAdapter;
import com.example.shoppingapp.api.ApiClient;
import com.example.shoppingapp.api.ApiInterface;
import com.example.shoppingapp.model.Cart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayOffActivity extends AppCompatActivity {

    RecyclerView recyclerView_pay_off;
    ApiInterface request;
    MyPrefManager pref;
    String email , token_pay_off;
    List<Cart> listCart = new ArrayList<>();
    PayOffAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_off);

        /**
         * When User Return to Application From IPG on Browser
         */

        request = ApiClient.getApiClient().create(ApiInterface.class);
        pref = new MyPrefManager(this);
        recyclerView_pay_off = findViewById(R.id.recycleRView_pay_off);
        recyclerView_pay_off.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_pay_off.setHasFixedSize(true);


        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {

                if (isPaymentSuccess) {
                    /* When Payment Request is Success :) */
                    String message = "Your Payment is Success :) " + refID;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    email = pref.getUserData().get(MyPrefManager.EMAIL);

                    Call<List<Cart>> call =request.updateCart(email , refID);

                    call.enqueue(new Callback<List<Cart>>() {
                        @Override
                        public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {

                            listCart = response.body();
                            adapter = new PayOffAdapter(getApplicationContext() , listCart);
                            recyclerView_pay_off.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<Cart>> call, Throwable t) {

                            Toast.makeText(PayOffActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    /* When Payment Request is Failure :) */
                    String message = "Your Payment is Failure :(";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


                }


            }
        });

    }
}