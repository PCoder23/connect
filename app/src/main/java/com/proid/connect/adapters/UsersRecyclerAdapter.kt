package com.proid.connect.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.proid.connect.R
import com.proid.connect.activities.UserChatScreenActivity
import com.proid.connect.models.ChatUserData

class UsersRecyclerAdapter(var arrChatUser:ArrayList<ChatUserData>): RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>() {

    lateinit var context: Context
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img = view.findViewById<ImageView>(R.id.img)
        var name = view.findViewById<TextView>(R.id.name)
        var message = view.findViewById<TextView>(R.id.message)
        var chatView = view.findViewById<CardView>(R.id.chatView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_view_recycler,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(arrChatUser[position].img)
        holder.name.text = arrChatUser[position].name
        holder.message.text = arrChatUser[position].lastMessage
        holder.chatView.setOnClickListener {
            var intent = Intent(context,UserChatScreenActivity::class.java)
            intent.putExtra("img",arrChatUser[position].img)
            intent.putExtra("name",arrChatUser[position].name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrChatUser.size
    }
}