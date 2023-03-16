package com.proid.connect.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.proid.connect.R
import com.proid.connect.activities.SearchUserActivity
import com.proid.connect.adapters.UsersRecyclerAdapter
import com.proid.connect.models.ChatUserData
import com.proid.connect.utiils.DatabaseHelper

class ChatFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        var searchBtn = view.findViewById<FloatingActionButton>(R.id.srchBtn)


        searchBtn.setOnClickListener{
            startActivity(Intent(requireContext(),SearchUserActivity::class.java))
        }

        var arrChatUser: ArrayList<ChatUserData> = ArrayList()
        var databaseHelper = DatabaseHelper.getDb(activity)
        var pref = requireActivity().getSharedPreferences("timestamp", AppCompatActivity.MODE_PRIVATE)

        for (user in databaseHelper.usersDao().getConnectedUsers()){
            var timestamp = pref.getLong("lastMessageTimestamp${user.userId}",0)
            if (!timestamp.equals(0)){
                arrChatUser.add(ChatUserData(user.userImg,user.userName,user.userId,databaseHelper.messagesDao().getMessageWithTimeStamp(timestamp)[0].msgContent,timestamp))
            }

        }

//        arrChatUser.add(ChatUserData(R.drawable.baseline_person_24,"Pranav","First Message"))
//        arrChatUser.add(ChatUserData(R.drawable.baseline_person_24,"Abhinav","Second Message"))
//        arrChatUser.add(ChatUserData(R.drawable.baseline_person_24,"Rishu","Third Message"))
//        arrChatUser.add(ChatUserData(R.drawable.baseline_person_24,"Sarbasis","Fourth Message"))
//        arrChatUser.add(ChatUserData(R.drawable.baseline_person_24,"Rajeev","Fifth Message"))
//        arrChatUser.add(ChatUserData(R.drawable.baseline_person_24,"Surya","Sixth Message"))

        var chatRecycler = view.findViewById<RecyclerView>(R.id.chatRecycler)
        chatRecycler.layoutManager = LinearLayoutManager(activity)

        chatRecycler.adapter = UsersRecyclerAdapter(arrChatUser)

        return view
    }

}