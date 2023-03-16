package com.proid.connect.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "connectedUsers")
class UsersData (id : String, userId: String, userName : String, userImg:Int){
    @PrimaryKey(autoGenerate = false)
    var id = id;


    @ColumnInfo(name = "userId")
    var userId = userId;


    @ColumnInfo(name ="userName")
    var userName = userName


    @ColumnInfo(name ="userImg")
    var userImg = userImg


}