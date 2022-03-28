package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingapp.adapter.IntroAdapter;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.R;
import com.example.shoppingapp.model.Intro;
import com.example.shoppingapp.model.Chart;
import com.example.shoppingapp.model.OptionProduct;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabs ;
    RequestQueue requestQueue;
    List<Intro> listIntro = new ArrayList<>();
    IntroAdapter introAdapter;

    Button btn_go;
    TextView txt_after , txt_before;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        requestQueue = Volley.newRequestQueue(this);

        btn_go = findViewById(R.id.btn_go);
        txt_after = findViewById(R.id.txt_after);
        txt_before = findViewById(R.id.txt_before);

        viewPager = findViewById(R.id.viewPager);
        tabs = findViewById(R.id.tabs);
        introAdapter = new IntroAdapter(this , listIntro);
        viewPager.setAdapter(introAdapter);
        tabs.setupWithViewPager(viewPager , true);

        viewPager.setRotationY(180);

        String url = Link.LINK_INTRO;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Intro[] intros = gson.fromJson(response.toString() ,  Intro[].class);

                for (int i = 0 ; i<intros.length ; i++){

                    listIntro.add(intros[i]);
                    introAdapter.notifyDataSetChanged();

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

        StringRequest request = new StringRequest(Request.Method.GET , url  ,listener , errorListener );
        requestQueue.add(request);




        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition()==listIntro.size()-1){

                    tabs.setVisibility(View.INVISIBLE);
                    btn_go.setVisibility(View.VISIBLE);
                    txt_after.setText("تمام");
                }else {

                    tabs.setVisibility(View.VISIBLE);
                    btn_go.setVisibility(View.INVISIBLE);
                    txt_after.setText("بعدی");
                }

                if (tab.getPosition()>0){
                    txt_before.setVisibility(View.VISIBLE);
                }else {
                    txt_before.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

btn_go.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(IntroActivity.this , LoginActivity.class));
    }
});
    }
}