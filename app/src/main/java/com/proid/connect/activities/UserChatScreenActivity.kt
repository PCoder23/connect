package com.proid.connect.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proid.connect.R
import com.proid.connect.adapters.MessagesRecyclerAdapter
import com.proid.connect.models.MessagesData
import com.proid.connect.utiils.DatabaseHelper
import java.util.*
import kotlin.collections.ArrayList

class UserChatScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_chat_screen)

        val db = Firebase.firestore

        var databaseHelper : DatabaseHelper = DatabaseHelper.getDb(this)

        var imgData = intent.getIntExtra("img",R.drawable.baseline_person_24)
        var msgReceiverName = intent.getStringExtra("name").toString()
        var msgReceiver = intent.getStringExtra("userId").toString()

        var img = findViewById<ImageView>(R.id.img)
        var name = findViewById<TextView>(R.id.name)

        var sendBtn = findViewById<Button>(R.id.sendBtn)
        var msgBox = findViewById<EditText>(R.id.msgBox)

        var messagesArr:kotlin.collections.ArrayList<MessagesData>  = databaseHelper.messagesDao().getMessages(msgReceiver) as ArrayList<MessagesData>

        img.setImageResource(imgData)
        name.text = msgReceiverName


        var pref =getSharedPreferences("login", MODE_PRIVATE)
        var msgSender:String = pref.getString("userId","pranav").toString()

        var spref =getSharedPreferences("timestamp", MODE_PRIVATE)
        var editorSpref = spref.edit()

        val docRefMsgSender = db.collection("users").document(msgSender).collection("messages")
        val docRefMsgReceiver = db.collection("users").document(msgReceiver).collection("messages")

        var messagesRecycler = findViewById<RecyclerView>(R.id.messagesRecycler)
        val adapterRecycler = MessagesRecyclerAdapter(this,messagesArr,msgSender)
        messagesRecycler.adapter = adapterRecycler

        fun addData(messageData :MessagesData){
                databaseHelper.messagesDao().addTm(messageData)
        }

        var lastMessageTimestamp: Long = spref.getLong("lastMessageTimestamp$msgReceiver",System.currentTimeMillis())

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
                    editorSpref.putLong("lastMessageTimestamp$msgReceiver",message.msgTime)
                    editorSpref.apply()


                    if (msgSender == message.msgSender && msgReceiver == message.msgReceiver  && message.msgTime>lastMessageTimestamp){
                        messagesArr.add(message)
                        adapterRecycler.notifyItemChanged(messagesArr.size - 1)
                        lastMessageTimestamp = message.msgTime
                        messagesRecycler.scrollToPosition(messagesArr.size-1)
                    } else{
                        if(msgSender == message.msgReceiver && msgReceiver ==message.msgSender && message.msgTime>lastMessageTimestamp){
                            messagesArr.add(message)
                            adapterRecycler.notifyItemChanged(messagesArr.size-1)
                            lastMessageTimestamp = message.msgTime
                            messagesRecycler.scrollToPosition(messagesArr.size-1)
                        }
                    }

                    try {
                        addData(message)
                    }catch (e:java.lang.Exception){
                        Log.d("error","error in adding message",e)
                    }

                }

            }


//for (messages in messagesArr){
//    databaseHelper.messagesDao().deleteTm(messages)
//}

        sendBtn.setOnClickListener{
            var msg = msgBox.text.toString()
            if(msg==""){
                Toast.makeText(this,"Messge Box Is Empty",Toast.LENGTH_LONG)
            }else{


            msgBox.setText("")

            docRefMsgSender.add(MessagesData(System.currentTimeMillis().toString(),msg,msgSender,msgReceiver,System.currentTimeMillis()))
            docRefMsgReceiver.add(MessagesData(System.currentTimeMillis().toString(),msg,msgSender,msgReceiver,System.currentTimeMillis()))
        }
            }




        messagesRecycler.layoutManager = LinearLayoutManager(this)
        messagesRecycler.scrollToPosition(messagesArr.size-1)



    }

}