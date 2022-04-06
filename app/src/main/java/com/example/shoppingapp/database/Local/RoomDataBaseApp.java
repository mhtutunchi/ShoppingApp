package com.example.shoppingapp.database.Local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shoppingapp.database.DataSource.FavoriteRepository;
import com.example.shoppingapp.database.Model.Favorite;

@Database(entities = {Favorite.class} , version = 1)
public abstract class RoomDataBaseApp extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();
    public static   RoomDataBaseApp instance;

    public static RoomDataBaseApp getInstance(Context context){

        if (instance==null){

            instance = Room.databaseBuilder(context , RoomDataBaseApp.class , "Shopping Database")
                    .allowMainThreadQueries()
                    .build();

        }

        return instance;

    }


}
