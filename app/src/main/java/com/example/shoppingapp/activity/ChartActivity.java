package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shoppingapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.shoppingapp.model.Chart;
import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.Global.Link;
import com.example.shoppingapp.model.OptionProduct;

public class ChartActivity extends AppCompatActivity {

    Bundle bundle ;
    String id;

    LineChart lineChart;
    List<Chart> listChart = new ArrayList<>();
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        requestQueue = Volley.newRequestQueue(this);

        bundle  = getIntent().getExtras();
        id = bundle.getString(Key.id);



        Toast.makeText(this, ""+ id, Toast.LENGTH_SHORT).show();

        lineChart = findViewById(R.id.lineChart);

        String url = Link.LINK_CHART_PRODUCT;

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                List<Entry> values = new ArrayList<>();

                Gson gson = new Gson();
                Chart[] charts = gson.fromJson(response.toString() ,  Chart[].class);

                for (int i = 0 ; i<charts.length ; i++){

                    listChart.add(charts[i]);
                    values.add(new Entry(i , Integer.valueOf(listChart.get(i).getPrice())));


                    LineDataSet lineDataSet = new LineDataSet(values , "قیمت محصول");
                    lineDataSet.setDrawFilled(true);
                    lineDataSet.setLineWidth(4f);
                    lineDataSet.setFillDrawable(ContextCompat.getDrawable(ChartActivity.this , R.drawable.bg_chart));

                    List<ILineDataSet> iLineDataSets = new ArrayList<>();
                    iLineDataSets.add(lineDataSet);

                    LineData lineData = new LineData(iLineDataSets);

                    lineChart.setData(lineData);
                    lineChart.animateXY(500 , 500);

                    XAxis xAxis = lineChart.getXAxis();
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            xAxis.setLabelCount(4 , true);
                            return listChart.get((int)value).getMonth();
                        }
                    });

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