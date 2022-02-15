package com.example.shoppingapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    ApiInterface request;

    EditText edt_phone, edt_password, edt_email;
    Button btn_register;
    TextView txt_login;

    MyPrefManager myPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myPrefManager = new MyPrefManager(RegisterActivity.this);

        if (myPrefManager.isLoggedIn()) {
            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
            finish();
        }

        request = ApiClient.getApiClient().create(ApiInterface.class);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_phone = findViewById(R.id.edt_phone);

        btn_register = findViewById(R.id.btn_register);
        txt_login = findViewById(R.id.txt_login);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                String phone = edt_phone.getText().toString();

                if (email.equals("") || password.equals("") || phone.equals("")) {
                    Toast.makeText(RegisterActivity.this, "لطفا تمامی فیلدها را پر کنید", Toast.LENGTH_SHORT).show();
                } else {

                    registerNewAccount(email, phone, password);
                }


            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }

    private void registerNewAccount(String email, String phone, String password) {

        Call<Users> usersCall = request.sendRegisterForUsers(email, phone, password);
        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                if (response.isSuccessful() && response.body().isStatus()) {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    UserData userData = response.body().getUserData();
                    myPrefManager.saveUserData(userData);

                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

                Toast.makeText(RegisterActivity.this, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });


    }
}