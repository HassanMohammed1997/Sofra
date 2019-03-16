package com.internship.ipda3.semicolon.sofra.local.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void addToCart(Cart... newCart);

    @Update
    void updateCurrentCart(Cart cart);

    @Delete
    void deleteCart(Cart cart);

    @Query("SELECT * FROM Cart")
    List<Cart> getCart();


}
