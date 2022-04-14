package com.example.shoppingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;

import org.json.JSONObject;

import com.example.shoppingapp.R;

public class VerifyPhoneActivity extends AppCompatActivity {

    EditText edt_phone;
    Button btn_verify , btn_send_code;
    CardView card_phone , card_verify;

    static  int random_token;
    static String phone;
    RequestQueue requestQueue;

    PinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        requestQueue = Volley.newRequestQueue(this);

        edt_phone = findViewById(R.id.edt_phone);
        card_verify = findViewById(R.id.card_verify);
        card_phone = findViewById(R.id.card_phone);
        pinView = findViewById(R.id.pinView);
        btn_send_code = findViewById(R.id.btn_send_Code);
        btn_verify = findViewById(R.id.btn_verify);

        btn_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone = edt_phone.getText().toString();
                card_phone.setVisibility(View.GONE);
                card_verify.setVisibility(View.VISIBLE);

                sendCode();

            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int code = Integer.parseInt(pinView.getText().toString());
                if (code==random_token){
                    Toast.makeText(VerifyPhoneActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    pinView.setLineColor(Color.GREEN);
                }else {
                    Toast.makeText(VerifyPhoneActivity.this, "No", Toast.LENGTH_SHORT).show();
                    pinView.setLineColor(Color.RED);
                }

            }
        });



    }

    private void sendCode() {

        //0 , 1
        random_token = (int) (Math.random()*1000000);

        Log.i("Token" , random_token+"");
        Toast.makeText(this, ""+random_token, Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.kavenegar.com/v1/4968" +
                "314856627A5942494C582B536C3732596550" +
                "73313448345A7646666869522B7A47397568753" +
                "77634493D/verify/lookup.json?receptor=" + phone + "&token=" + random_token + "&template=testshopping", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(VerifyPhoneActivity.this, response+"", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(VerifyPhoneActivity.this, error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });


        requestQueue.add(jsonObjectRequest);

    }
}