package com.example.shoppingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.MyPrefManager;
import com.example.shoppingapp.R;
import com.example.shoppingapp.adapter.CartAdapter;
import com.example.shoppingapp.api.ApiClient;
import com.example.shoppingapp.api.ApiInterface;
import com.example.shoppingapp.api.Message;
import com.example.shoppingapp.model.Cart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {


    RecyclerView recyclerView_cart;
    List<Cart> listCart = new ArrayList<>();
    CartAdapter adapter;
    ApiInterface request;
    String email;
    MyPrefManager pref;

    private int TOTAL_PRICE , ALL_PRODUCT_SIZE;
    Button btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        request = ApiClient.getApiClient().create(ApiInterface.class);
        pref = new MyPrefManager(this);
        email = pref.getUserData().get(MyPrefManager.EMAIL);

        recyclerView_cart = findViewById(R.id.recycleRView_cart);
        recyclerView_cart.setHasFixedSize(true);
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(this));

        btn_continue = findViewById(R.id.btn_continue);

        Call<List<Cart>> call = request.getListCart(email);
        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {

                listCart = response.body();
                adapter = new CartAdapter(getApplicationContext(), listCart, new CartAdapter.deleteProductForId() {
                    @Override
                    public void IItemDeleteProduct(Cart cart) {

                        Call<Message> callDelete = request.deleteCart(cart.getCart_id());

                        callDelete.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {

                                if (response.body().getMessage().equals("Ok")){
                                    adapter.deleteProduct(cart);
                                }

                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Toast.makeText(CartActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }, new CartAdapter.priceAndSizeProduct() {
                    @Override
                    public void IItemPriceAndSize(String price, String data) {

                        TOTAL_PRICE+=Integer.parseInt(price);


                        btn_continue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CartActivity.this);
                                View layout_bottom_sheet = LayoutInflater.from(getApplicationContext()).inflate(
                                        R.layout.layout_bottom_sheet_cart , findViewById(R.id.parent));


                                TextView txt_all_product_size = layout_bottom_sheet.findViewById(R.id.txt_all_product_size);
                                TextView txt_total_product = layout_bottom_sheet.findViewById(R.id.txt_total_price);
                                Button   btn_buy = layout_bottom_sheet.findViewById(R.id.btn_buy);

                                ALL_PRODUCT_SIZE = Integer.parseInt(data);

                                txt_total_product.setText(TOTAL_PRICE+"");
                                txt_all_product_size.setText(ALL_PRODUCT_SIZE+"");

                                btn_buy.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        preparePayOff(TOTAL_PRICE+"");

                                    }
                                });


                                bottomSheetDialog.setContentView(layout_bottom_sheet);
                                bottomSheetDialog.show();

                            }
                        });

                    }
                });
                recyclerView_cart.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

                Toast.makeText(CartActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void preparePayOff(String totalPrice) {

        ZarinPal purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment  = ZarinPal.getPaymentRequest();
        //If you will test on our sandbox, you can use it:
        //PaymentRequest payment  = ZarinPal.getSandboxPaymentRequest();


        payment.setMerchantID("71c705f8-bd37-11e6-aa0c-000c295eb8fc");
        payment.setAmount(Long.parseLong(totalPrice));
        payment.isZarinGateEnable(true);  // If you actived `ZarinGate`, can handle payment by `ZarinGate`
        payment.setDescription("In App Purchase Test SDK");
        payment.setCallbackURL("app://app");     /* Your App Scheme */
        payment.setMobile("09355106005");            /* Optional Parameters */
        payment.setEmail("imannamix@gmail.com");     /* Optional Parameters */

        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {

                if (status == 100) {
                   /*
                   When Status is 100 Open Zarinpal PG on Browser
                   */
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Your Payment Failure :(", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}