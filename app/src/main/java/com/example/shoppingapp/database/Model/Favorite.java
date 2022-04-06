package com.example.shoppingapp.database.Model;


import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "Favorite")
public class Favorite {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "id_product")
    public String id_product;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category_id")
    public String category_id;

    @ColumnInfo(name = "link_img")
    public String link_img;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "add_to_favorite")
    public int add_to_favorite;

}
