package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shoppingapp.Global.Key;
import com.example.shoppingapp.R;

public class DetailCategoryActivity extends AppCompatActivity {

    Bundle bundle;
    String name_category , id;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);

        bundle = getIntent().getExtras();
        name_category = bundle.getString(Key.title);
        id = bundle.getString(Key.id);
        title = findViewById(R.id.title);


        title.setText(name_category);

    }
}