package com.example.shoppingapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.shoppingapp.Global.MyPrefManager;
import com.example.shoppingapp.R;
import com.example.shoppingapp.api.ApiClient;
import com.example.shoppingapp.api.ApiInterface;
import com.example.shoppingapp.api.Message;
import com.example.shoppingapp.fragment.CategoryFragment;
import com.example.shoppingapp.fragment.HomeFragment;
import com.example.shoppingapp.fragment.ProfileFragment;
import com.example.shoppingapp.fragment.SearchFragment;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    String user_email;
    MyPrefManager myPrefManager;
    ApiInterface request;

    TextView txt_Count;
    ImageView img_shopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        request = ApiClient.getApiClient().create(ApiInterface.class);
        myPrefManager = new MyPrefManager(this);
        user_email = myPrefManager.getUserData().get(MyPrefManager.EMAIL);
        txt_Count = findViewById(R.id.txt_count);
        img_shopping = findViewById(R.id.img_shopping);



        bottomNavigationView = findViewById(R.id.bottom_navigation);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout , homeFragment);
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();



                switch (id){

                    case R.id.nav_home:
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout , homeFragment);
                        fragmentTransaction.commit();
                        break;

                    case R.id.nav_category:
                        CategoryFragment categoryFragment = new CategoryFragment();
                        FragmentTransaction fragmentTransaction_cat = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction_cat.replace(R.id.frameLayout , categoryFragment);
                        fragmentTransaction_cat.commit();
                        break;

                    case R.id.nav_search:
                        SearchFragment searchFragment = new SearchFragment();
                        FragmentTransaction fragmentTransaction_search = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction_search.replace(R.id.frameLayout , searchFragment);
                        fragmentTransaction_search.commit();
                        break;

                    case R.id.nav_profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentTransaction fragmentTransaction_profile = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction_profile.replace(R.id.frameLayout , profileFragment);
                        fragmentTransaction_profile.commit();
                        break;

                }

                return true;
            }
        });

        getCountCart(user_email);

        img_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this , CartActivity.class));

            }
        });

    }
    private void getCountCart(String user_email) {

        Call<Message> messageCall = request.getCountCart(user_email);

        messageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, retrofit2.Response<Message> response) {

                if (!response.body().getMessage().equals("0")){

                    txt_Count.setVisibility(View.VISIBLE);
                    txt_Count.setText(response.body().getMessage());

                }

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

                Toast.makeText(HomeActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onResume() {
        getCountCart(user_email);
        super.onResume();
    }
}