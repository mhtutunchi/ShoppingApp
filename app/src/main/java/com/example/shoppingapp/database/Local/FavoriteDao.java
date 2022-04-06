package com.example.shoppingapp.database.Local;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import com.example.shoppingapp.database.Model.Favorite;
import io.reactivex.Flowable;

@Dao
public interface FavoriteDao {

    @Query("Select * From Favorite WHERE add_to_favorite = 1 ")
    Flowable<List<Favorite>> getListFavoriteItem();

    @Query("Select Exists (Select 1 From Favorite Where id=:item_id)")
    int isFavorite(int item_id);

    @Insert
    void InsertFavorite(Favorite...favorites);

    @Delete
    void DeleteFavorite(Favorite favorite);


}
