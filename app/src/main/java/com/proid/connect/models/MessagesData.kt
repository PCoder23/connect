package com.proid.connect.models

import android.content.BroadcastReceiver
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "MessageData")
class MessagesData(
    id:String, msgContent: String, msgSender: String, msgReceiver: String,msgTime: Long
){
    @PrimaryKey(autoGenerate = false)
    var id = id


    @ColumnInfo(name="msgContent")
    var msgContent = msgContent


    @ColumnInfo(name="msgSender")
    var msgSender = msgSender


    @ColumnInfo(name="msgReceiver")
    var msgReceiver = msgReceiver


    @ColumnInfo(name="msgTime")
    var msgTime = msgTime



}