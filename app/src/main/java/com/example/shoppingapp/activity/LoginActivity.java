package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.Global.MyPrefManager;
import com.example.shoppingapp.R;
import com.example.shoppingapp.api.ApiClient;
import com.example.shoppingapp.api.ApiInterface;
import com.example.shoppingapp.api.UserData;
import com.example.shoppingapp.api.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    EditText edt_phone, edt_password;
    Button btn_login;
    TextView txt_register;

    ApiInterface request;

    MyPrefManager myPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myPrefManager = new MyPrefManager(LoginActivity.this);

        if (myPrefManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        request = ApiClient.getApiClient().create(ApiInterface.class);


        edt_password = findViewById(R.id.edt_password);
        edt_phone = findViewById(R.id.edt_phone);


        txt_register = findViewById(R.id.txt_register);

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String password = edt_password.getText().toString();
                String phone = edt_phone.getText().toString();

                if (password.equals("") || phone.equals("")) {
                    Toast.makeText(LoginActivity.this, "لطفا تمامی فیلد ها را پر کنید", Toast.LENGTH_SHORT).show();
                } else {

                    loginAccount(phone, password);
                }


            }
        });

    }

    private void loginAccount(String phone, String password) {

        Call<Users> usersCall = request.sendLoginForUsers(phone, password);
        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                if (response.isSuccessful() && response.body().isStatus()) {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    UserData userData = response.body().getUserData();
                    myPrefManager.saveUserData(userData);

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();

                } else {

                    Toast.makeText(LoginActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

                Toast.makeText(LoginActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });

    }
}