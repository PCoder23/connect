package com.proid.connect.daoInterfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.proid.connect.models.MessagesData
import com.proid.connect.models.UsersData

@Dao
interface UsersDao {
    @Query("SELECT * FROM connectedUsers")
    fun getConnectedUsers() : List<UsersData>

    @Query("SELECT * FROM connectedUsers WHERE userId = :userId")
    fun getUsersWithId(userId:String) : List<UsersData>

    @Insert
    fun addCu(usersData: UsersData)

    @Delete
    fun deleteCu(usersData: UsersData)


}