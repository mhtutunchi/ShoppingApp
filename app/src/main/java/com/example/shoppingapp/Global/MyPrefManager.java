package com.example.shoppingapp.Global;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

import com.example.shoppingapp.api.UserData;
public class MyPrefManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String ID = "ID";
    public static final String PHONE = "PHONE";
    public static final String EMAIL = "EMAIL";



    public MyPrefManager(Context context) {
        this.context = context;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();

    }

    public void saveUserData(UserData userData){

        editor.putString(ID , userData.getId());
        editor.putString(PHONE , userData.getPhone());
        editor.putString(EMAIL , userData.getEmail());
        editor.putBoolean(IS_LOGGED_IN , true);
        editor.commit();

    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

    public HashMap<String , String> getUserData(){

        HashMap<String , String> userData = new HashMap<>();
        userData.put(ID , sharedPreferences.getString(ID , null));
        userData.put(PHONE , sharedPreferences.getString(PHONE , null));
        userData.put(EMAIL , sharedPreferences.getString(EMAIL , null));
        return userData;

    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN , false);
    }




}
