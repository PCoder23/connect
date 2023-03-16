package com.proid.connect.adapters

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.proid.connect.R
import com.proid.connect.models.MessagesData

class MessagesRecyclerAdapter(var context : Context, var msgArray: ArrayList<MessagesData>, var name:String) : RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var msgLayout = view.findViewById<LinearLayoutCompat>(R.id.MsgLayout)
        var msg = view.findViewById<TextView>(R.id.Msg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.messages_item_view_recycler,parent,false))
    }

    override fun getItemCount(): Int {
    return  msgArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (msgArray[position].msgSender == name){
//            holder.rightMessage.text = msgArray[position].msgContent
//
//            holder.leftMessagesLayout.visibility =LinearLayout.GONE
            holder.msg.setBackgroundResource(R.drawable.right_message_background)
            holder.msgLayout.gravity = Gravity.RIGHT
            holder.msg.text = msgArray[position].msgContent
        }else{
            holder.msg.setBackgroundResource(R.drawable.left_message_background)
            holder.msgLayout.gravity = Gravity.LEFT
            holder.msg.text = msgArray[position].msgContent
        }
    }
}