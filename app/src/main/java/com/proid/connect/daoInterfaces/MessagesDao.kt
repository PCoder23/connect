package com.proid.connect.daoInterfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.proid.connect.models.MessagesData

@Dao
interface MessagesDao {
    @Query("SELECT * FROM MessageData")
    fun getAllMessages() : List<MessagesData>

    @Query("SELECT * FROM MessageData WHERE msgSender = :userId OR msgReceiver = :userId")
    fun getMessages(userId:String) : List<MessagesData>

    @Query("SELECT * FROM MessageData WHERE msgTime = :msgTime")
    fun getMessageWithTimeStamp(msgTime:Long) : List<MessagesData>

    @Insert
    fun addTm(message:MessagesData)

    @Delete
    fun deleteTm(message:MessagesData )

}