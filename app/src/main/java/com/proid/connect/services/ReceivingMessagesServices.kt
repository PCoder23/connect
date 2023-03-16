package com.proid.connect.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proid.connect.models.MessagesData
import com.proid.connect.models.UsersData
import com.proid.connect.utiils.DatabaseHelper

class ReceivingMessagesServices: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        val db = Firebase.firestore
        var msgSender = getSharedPreferences("login", MODE_PRIVATE).getString("userId","Name").toString()
        val docRefMsgSender = db.collection("users").document(msgSender).collection("messages")
        var databaseHelper =DatabaseHelper.getDb(this)

        docRefMsgSender
            .orderBy("msgTime", Query.Direction.DESCENDING)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (doc in value!!) {
                    var docu =doc.data
                    var message = MessagesData(doc.id,docu.get("msgContent").toString(),docu.get("msgSender").toString(),docu.get("msgReceiver").toString(),docu.get("msgTime").toString().toLong())
                    if (message.msgSender != msgSender && databaseHelper.usersDao().getUsersWithId(message.msgSender).isEmpty()){
                        db.collection("users").document(msgSender)
                            .get()
                            .addOnSuccessListener { document->
                                var d = document
                                db.collection("users").document(msgSender).collection("connectedUsers")
                                    .add(UsersData(document.id,d.get("userId").toString(),d.get("userName").toString(),d.get("userImg").toString().toInt()))
                                    .addOnSuccessListener {
                                        databaseHelper.usersDao().addCu(UsersData(document.id,d.get("userId").toString(),d.get("userName").toString(),d.get("userImg").toString().toInt()))
                                    }

                            }
                    }

                    try {
                        databaseHelper.messagesDao().addTm(message)
                    }catch (e:java.lang.Exception){
                        Log.d("error","error in adding message",e)
                    }

                }

            }
    }
}