package com.internship.ipda3.semicolon.sofra.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Cart.class}, version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();

}
