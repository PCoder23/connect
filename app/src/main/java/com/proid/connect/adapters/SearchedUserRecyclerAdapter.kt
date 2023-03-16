package com.proid.connect.adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proid.connect.R
import com.proid.connect.activities.UserChatScreenActivity
import com.proid.connect.models.UsersData
import com.proid.connect.utiils.DatabaseHelper

class SearchedUserRecyclerAdapter(var context:Context,var suArr:ArrayList<UsersData>) : RecyclerView.Adapter<SearchedUserRecyclerAdapter.ViewHolder>() {
   var databaseHelper = DatabaseHelper.getDb(context)
    val db = Firebase.firestore
    var pref = context.getSharedPreferences("login", AppCompatActivity.MODE_PRIVATE)
    var userId = pref.getString("userId","name").toString()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img = view.findViewById<ImageView>(R.id.ppImg)
        var searchedName  = view.findViewById<TextView>(R.id.searchedName)
        var connectBtn = view.findViewById<Button>(R.id.connectBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.searcher_user_item_view,parent,false))
    }

    override fun getItemCount(): Int {
        return  suArr.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(suArr[position].userImg)
        holder.searchedName.text = suArr[position].userName
        holder.connectBtn.setOnClickListener {
            db.collection("users").document(userId).collection("connectedUsers")
                .add(suArr[position])
                .addOnSuccessListener { documentReference ->
                    databaseHelper.usersDao().addCu(suArr[position])
                    var intent = Intent(context, UserChatScreenActivity::class.java)
                    intent.putExtra("img",suArr[position].userImg)
                    intent.putExtra("name",suArr[position].userName)
                    intent.putExtra("userId",suArr[position].userId)
                    context.startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }

        }
    }
}