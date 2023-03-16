package com.proid.connect.utiils;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.proid.connect.daoInterfaces.MessagesDao;
import com.proid.connect.daoInterfaces.UsersDao;
import com.proid.connect.models.MessagesData;
import com.proid.connect.models.UsersData;

@Database(entities = {MessagesData.class, UsersData.class},exportSchema = false,version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    public static final String DB_NAME = "connectDB";
    public static DatabaseHelper instance ;

    public static synchronized DatabaseHelper getDb(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,DatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;

    }

    public abstract MessagesDao messagesDao();


    public abstract UsersDao usersDao();


}
