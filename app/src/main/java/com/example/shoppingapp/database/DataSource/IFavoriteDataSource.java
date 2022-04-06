package com.example.shoppingapp.database.DataSource;

import java.util.List;

import com.example.shoppingapp.database.Model.Favorite;
import io.reactivex.Flowable;

public interface IFavoriteDataSource {

    Flowable<List<Favorite>> getListFavoriteItem();
    int isFavorite(int item_id);
    void InsertFavorite(Favorite...favorites);
    void DeleteFavorite(Favorite favorite);


}
